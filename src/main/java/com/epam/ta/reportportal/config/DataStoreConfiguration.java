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
import com.epam.ta.reportportal.filesystem.distributed.minio.MinioDataStore;
import io.minio.MinioClient;
import io.minio.errors.InvalidEndpointException;
import io.minio.errors.InvalidPortException;
import org.springframework.beans.factory.annotation.Autowired;
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
	public DataStore localDataStore(@Value("${datastore.default.path:/data/store}") String storagePath) {
		return new LocalDataStore(storagePath);
	}

	@Bean
	@ConditionalOnProperty(name = "datastore.type", havingValue = "minio")
	public MinioClient minioClient(@Value("${datastore.minio.endpoint}") String endpoint,
			@Value("${datastore.minio.accessKey}") String accessKey, @Value("${datastore.minio.secretKey}") String secretKey,
			@Value("${datastore.minio.region}") String region) throws InvalidPortException, InvalidEndpointException {
		return new MinioClient(endpoint, accessKey, secretKey, region);
	}

	@Bean
	@ConditionalOnProperty(name = "datastore.type", havingValue = "minio")
	public DataStore minioDataStore(@Autowired MinioClient minioClient,
			@Value("${datastore.minio.bucketPrefix}") String bucketPrefix,
			@Value("${datastore.minio.defaultBucketName}") String defaultBucketName) {
		return new MinioDataStore(minioClient, bucketPrefix, defaultBucketName);
	}

	@Bean("attachmentThumbnailator")
	public Thumbnailator attachmentThumbnailator(@Value("${datastore.thumbnail.attachment.width}") int width,
			@Value("${datastore.thumbnail.attachment.height}") int height) {
		return new ThumbnailatorImpl(width, height);
	}

	@Bean("userPhotoThumbnailator")
	public Thumbnailator userPhotoThumbnailator(@Value("${datastore.thumbnail.avatar.width}") int width,
			@Value("${datastore.thumbnail.avatar.height}") int height) {
		return new ThumbnailatorImpl(width, height);
	}

	@Bean
	public ContentTypeResolver contentTypeResolver() {
		return new TikaContentTypeResolver();
	}
}