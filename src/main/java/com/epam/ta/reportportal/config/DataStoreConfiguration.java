/*
 * Copyright 2019 EPAM Systems
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.epam.ta.reportportal.config;

import com.epam.reportportal.commons.ContentTypeResolver;
import com.epam.reportportal.commons.Thumbnailator;
import com.epam.reportportal.commons.ThumbnailatorImpl;
import com.epam.reportportal.commons.TikaContentTypeResolver;
import com.epam.ta.reportportal.filesystem.DataStore;
import com.epam.ta.reportportal.filesystem.LocalDataStore;
import com.epam.ta.reportportal.filesystem.distributed.s3.S3DataStore;
import com.epam.ta.reportportal.util.FeatureFlagHandler;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import org.apache.commons.lang3.StringUtils;
import org.apache.opendal.Operator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Dzianis_Shybeka
 */
@Configuration
public class DataStoreConfiguration {

  @Bean
  @ConditionalOnProperty(name = "datastore.type", havingValue = "filesystem")
  public DataStore localDataStore(
      @Value("${datastore.path:/data/store}") String baseDirectory,
      FeatureFlagHandler featureFlagHandler,
      @Value("${datastore.bucketPrefix}") String bucketPrefix,
      @Value("${datastore.bucketPostfix}") String bucketPostfix,
      @Value("${datastore.defaultBucketName}") String defaultBucketName) {

    Map<String, String> config = new HashMap<>();
    config.put("root", baseDirectory);
    Operator operator = Operator.of("fs", config);
    return new LocalDataStore(operator, featureFlagHandler, bucketPrefix, bucketPostfix,
        defaultBucketName);
  }

  /**
   * Creates a DataStore bean for MinIO (and other S3-compatible stores with a custom endpoint).
   * <p>
   * Uses OpenDAL's {@code s3} service with path-style addressing and AWS Signature Version 4.
   * A per-bucket operator cache avoids re-initialising the connection for every operation.
   * </p>
   */
  @Bean
  @ConditionalOnProperty(name = "datastore.type", havingValue = "minio")
  public DataStore minioDataStore(
      @Value("${datastore.accessKey}") String accessKey,
      @Value("${datastore.secretKey}") String secretKey,
      @Value("${datastore.endpoint}") String endpoint,
      @Value("${datastore.region}") String region,
      @Value("${datastore.bucketPrefix}") String bucketPrefix,
      @Value("${datastore.bucketPostfix}") String bucketPostfix,
      @Value("${datastore.defaultBucketName}") String defaultBucketName,
      FeatureFlagHandler featureFlagHandler) {

    Function<String, Operator> factory = cachedOperatorFactory(
        bucket -> s3CompatibleConfig(bucket, endpoint, accessKey, secretKey, region));
    return new S3DataStore(factory, bucketPrefix, bucketPostfix, defaultBucketName,
        featureFlagHandler);
  }

  /**
   * Creates a DataStore bean for SeaweedFS.
   * <p>
   * OpenDAL's {@code s3} service uses the configured region directly for signing, eliminating the
   * {@code getBucketLocation} round-trip that jclouds required.
   * </p>
   */
  @Bean
  @ConditionalOnProperty(name = "datastore.type", havingValue = "seaweedfs")
  public DataStore seaweedFsDataStore(
      @Value("${datastore.accessKey}") String accessKey,
      @Value("${datastore.secretKey}") String secretKey,
      @Value("${datastore.endpoint}") String endpoint,
      @Value("${datastore.region}") String region,
      @Value("${datastore.bucketPrefix}") String bucketPrefix,
      @Value("${datastore.bucketPostfix}") String bucketPostfix,
      @Value("${datastore.defaultBucketName}") String defaultBucketName,
      FeatureFlagHandler featureFlagHandler) {

    Function<String, Operator> factory = cachedOperatorFactory(
        bucket -> s3CompatibleConfig(bucket, endpoint, accessKey, secretKey, region));
    return new S3DataStore(factory, bucketPrefix, bucketPostfix, defaultBucketName,
        featureFlagHandler);
  }

  /**
   * Creates a DataStore bean for native AWS S3.
   * <p>
   * When {@code accessKey} / {@code secretKey} are blank, OpenDAL resolves credentials through the
   * standard AWS chain (environment variables, {@code ~/.aws/credentials}, EC2 instance profile,
   * ECS task role, etc.) without requiring an explicit IAM credential supplier.
   * </p>
   */
  @Bean
  @ConditionalOnProperty(name = "datastore.type", havingValue = "s3")
  public DataStore s3DataStore(
      @Value("${datastore.accessKey:}") String accessKey,
      @Value("${datastore.secretKey:}") String secretKey,
      @Value("${datastore.region}") String region,
      @Value("${datastore.bucketPrefix}") String bucketPrefix,
      @Value("${datastore.bucketPostfix}") String bucketPostfix,
      @Value("${datastore.defaultBucketName}") String defaultBucketName,
      FeatureFlagHandler featureFlagHandler) {

    Function<String, Operator> factory = cachedOperatorFactory(bucket -> {
      Map<String, String> config = new HashMap<>();
      config.put("bucket", bucket);
      config.put("region", region);
      config.put("enable_virtual_host_style", "true");
      if (StringUtils.isNotEmpty(accessKey) && StringUtils.isNotEmpty(secretKey)) {
        config.put("access_key_id", accessKey);
        config.put("secret_access_key", secretKey);
      }
      return config;
    });
    return new S3DataStore(factory, bucketPrefix, bucketPostfix, defaultBucketName,
        featureFlagHandler);
  }

  @Bean("attachmentThumbnailator")
  public Thumbnailator attachmentThumbnailator(
      @Value("${datastore.thumbnail.attachment.width}") int width,
      @Value("${datastore.thumbnail.attachment.height}") int height) {
    return new ThumbnailatorImpl(width, height);
  }

  @Bean("userPhotoThumbnailator")
  public Thumbnailator userPhotoThumbnailator(
      @Value("${datastore.thumbnail.avatar.width}") int width,
      @Value("${datastore.thumbnail.avatar.height}") int height) {
    return new ThumbnailatorImpl(width, height);
  }

  @Bean
  public ContentTypeResolver contentTypeResolver() {
    return new TikaContentTypeResolver();
  }

  private static Map<String, String> s3CompatibleConfig(String bucket, String endpoint,
      String accessKey, String secretKey, String region) {
    Map<String, String> config = new HashMap<>();
    config.put("bucket", bucket);
    config.put("endpoint", endpoint);
    config.put("access_key_id", accessKey);
    config.put("secret_access_key", secretKey);
    config.put("region", region);
    return config;
  }

  /**
   * Wraps a config-supplier in a thread-safe, per-bucket operator cache.
   */
  private static Function<String, Operator> cachedOperatorFactory(
      Function<String, Map<String, String>> configSupplier) {
    ConcurrentHashMap<String, Operator> cache = new ConcurrentHashMap<>();
    return bucket -> cache.computeIfAbsent(bucket,
        b -> Operator.of("s3", configSupplier.apply(b)));
  }
}
