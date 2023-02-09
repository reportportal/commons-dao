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
import com.google.common.base.Optional;
import com.google.common.base.Supplier;
import com.google.common.cache.CacheLoader;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.inject.Module;
import java.util.Set;
import org.jclouds.ContextBuilder;
import org.jclouds.aws.s3.config.AWSS3HttpApiModule;
import org.jclouds.blobstore.BlobStore;
import org.jclouds.blobstore.BlobStoreContext;
import org.jclouds.blobstore.ContainerNotFoundException;
import org.jclouds.rest.ConfiguresHttpApi;
import org.jclouds.s3.S3Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for work with blob storages(MiniO, S3, Filesystem).
 *
 * @author Dzianis_Shybeka
 */
@Configuration
public class DataStoreConfiguration {

  /**
   * Amazon has a general work flow they publish that allows clients to always find the correct URL
   * endpoint for a given bucket:
   * 1) ask s3.amazonaws.com for the bucket location
   * 2) use the url returned to make the container specific request (get/put, etc)
   * Jclouds cache the results from the first getBucketLocation call and use that region-specific
   * URL, as needed.
   * In this custom implementation of {@link AWSS3HttpApiModule} we are providing location from
   * environment variable, so that we don't need to make getBucketLocation call
   */
  @ConfiguresHttpApi
  private static class CustomBucketToRegionModule extends AWSS3HttpApiModule {
    private final String region;

    public CustomBucketToRegionModule(String region) {
      this.region = region;
    }

    @Override
    @SuppressWarnings("Guava")
    protected CacheLoader<String, Optional<String>> bucketToRegion(
        Supplier<Set<String>> regionSupplier, S3Client client) {
      Set<String> regions = regionSupplier.get();
      if (regions.isEmpty()) {
        return new CacheLoader<>() {

          @Override
          @SuppressWarnings({ "Guava", "NullableProblems" })
          public Optional<String> load(String bucket) {
            if (CustomBucketToRegionModule.this.region != null) {
              return Optional.of(CustomBucketToRegionModule.this.region);
            }
            return Optional.absent();
          }

          @Override
          public String toString() {
            return "noRegions()";
          }
        };
      } else if (regions.size() == 1) {
        final String onlyRegion = Iterables.getOnlyElement(regions);
        return new CacheLoader<>() {
          @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
          final Optional<String> onlyRegionOption = Optional.of(onlyRegion);

          @Override
          @SuppressWarnings("NullableProblems")
          public Optional<String> load(String bucket) {
            if (CustomBucketToRegionModule.this.region != null) {
              return Optional.of(CustomBucketToRegionModule.this.region);
            }
            return onlyRegionOption;
          }

          @Override
          public String toString() {
            return "onlyRegion(" + onlyRegion + ")";
          }
        };
      } else {
        return new CacheLoader<>() {
          @Override
          @SuppressWarnings("NullableProblems")
          public Optional<String> load(String bucket) {
            if (CustomBucketToRegionModule.this.region != null) {
              return Optional.of(CustomBucketToRegionModule.this.region);
            }
            try {
              return Optional.fromNullable(client.getBucketLocation(bucket));
            } catch (ContainerNotFoundException e) {
              return Optional.absent();
            }
          }

          @Override
          public String toString() {
            return "bucketToRegion()";
          }
        };
      }
    }
  }

  @Bean
  @ConditionalOnProperty(name = "datastore.type", havingValue = "filesystem")
  public DataStore localDataStore(
      @Value("${datastore.default.path:/data/store}") String storagePath) {
    return new LocalDataStore(storagePath);
  }

  /**
   * Creates BlobStore to work with Minio blob storage.
   *
   * @param accessKey Access key
   * @param secretKey Secret Key
   * @param endpoint  Endpoint to connect
   * @return Created BlobStore
   */
  @Bean
  @ConditionalOnProperty(name = "datastore.type", havingValue = "minio")
  public BlobStore minioBlobStore(@Value("${datastore.minio.accessKey}") String accessKey,
      @Value("${datastore.minio.secretKey}") String secretKey,
      @Value("${datastore.minio.endpoint}") String endpoint) {

    BlobStoreContext blobStoreContext =
        ContextBuilder.newBuilder("s3").endpoint(endpoint).credentials(accessKey, secretKey)
            .buildView(BlobStoreContext.class);

    return blobStoreContext.getBlobStore();
  }

  @Bean
  @ConditionalOnProperty(name = "datastore.type", havingValue = "minio")
  public DataStore minioDataStore(@Autowired BlobStore blobStore,
      @Value("${datastore.minio.bucketPrefix}") String bucketPrefix,
      @Value("${datastore.minio.bucketPostfix}") String bucketPostfix,
      @Value("${datastore.minio.defaultBucketName}") String defaultBucketName,
      @Value("${datastore.minio.region}") String region) {
    return new S3DataStore(blobStore, bucketPrefix, bucketPostfix, defaultBucketName, region);
  }

  /**
   * Creates BlobStore to work with S3 blob storage.
   *
   * @param accessKey Access key
   * @param secretKey Secret Key
   * @param region    AWS region where buckets are laying
   * @return Created BlobStore
   */
  @Bean
  @ConditionalOnProperty(name = "datastore.type", havingValue = "s3")
  public BlobStore s3BlobStore(@Value("${datastore.s3.accessKey}") String accessKey,
      @Value("${datastore.s3.secretKey}") String secretKey,
      @Value("${datastore.s3.region}") String region) {
    Iterable<Module> modules = ImmutableSet.of(new CustomBucketToRegionModule(region));

    BlobStoreContext blobStoreContext =
        ContextBuilder.newBuilder("aws-s3").modules(modules).credentials(accessKey, secretKey)
            .buildView(BlobStoreContext.class);

    return blobStoreContext.getBlobStore();
  }

  @Bean
  @ConditionalOnProperty(name = "datastore.type", havingValue = "s3")
  public DataStore s3DataStore(@Autowired BlobStore blobStore,
      @Value("${datastore.s3.bucketPrefix}") String bucketPrefix,
      @Value("${datastore.s3.bucketPostfix}") String bucketPostfix,
      @Value("${datastore.s3.defaultBucketName}") String defaultBucketName,
      @Value("${datastore.s3.region}") String region) {
    return new S3DataStore(blobStore, bucketPrefix, bucketPostfix, defaultBucketName, region);
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
}