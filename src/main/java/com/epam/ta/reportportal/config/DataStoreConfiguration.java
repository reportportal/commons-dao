
/*
 * Copyright (C) 2018 EPAM Systems
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
import com.epam.ta.reportportal.exception.ReportPortalException;
import com.epam.ta.reportportal.filesystem.DataStore;
import com.epam.ta.reportportal.filesystem.LocalDataStore;
import com.epam.ta.reportportal.filesystem.distributed.SeaweedDataStore;
import com.epam.ta.reportportal.filesystem.distributed.minio.MinioDataStore;
import io.minio.MinioClient;
import io.minio.errors.InvalidEndpointException;
import io.minio.errors.InvalidPortException;
import org.lokra.seaweedfs.core.FileSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.io.IOException;

/**
 * @author Dzianis_Shybeka
 */
@Configuration
@PropertySource(value = { "classpath:datastore.properties" })
public class DataStoreConfiguration {

	private static final Logger LOGGER = LoggerFactory.getLogger(DataStoreConfiguration.class);

	@Bean
	@ConditionalOnProperty(name = "datastore.type", havingValue = "seaweed")
	public FileSource fileSource(@Value("${datastore.seaweed.master.host}") String masterHost,
			@Value("${datastore.seaweed.master.port}") Integer masterPort) {

		FileSource fileSource = new FileSource();
		fileSource.setHost(masterHost);
		fileSource.setPort(masterPort);

		try {
			fileSource.startup();
		} catch (IOException e) {

			throw new ReportPortalException("Cannot connect to seaweed fs");
		}

		LOGGER.debug("Connected to seaweed fs !");

		return fileSource;
	}

	@Bean
	@ConditionalOnProperty(name = "datastore.type", havingValue = "seaweed")
	public DataStore seaweedDataStore(@Autowired FileSource fileSource) {

		return new SeaweedDataStore(fileSource);
	}

	@Bean
	@ConditionalOnProperty(name = "datastore.type", havingValue = "filesystem")
	public DataStore localDataStore(@Value("${datastore.default.path:/data/store}") String storagePath) {

		return new LocalDataStore(storagePath);
	}

	@Bean
	@ConditionalOnProperty(name = "datastore.type", havingValue = "minio")
	public MinioClient minioClient(@Value("${datastore.minio.endpoint}") String endpoint,
			@Value("${datastore.minio.accessKey}") String accessKey, @Value("${datastore.minio.secretKey}") String secretKey)
			throws InvalidPortException, InvalidEndpointException {
		return new MinioClient(endpoint, accessKey, secretKey);
	}

	@Bean
	@ConditionalOnProperty(name = "datastore.type", havingValue = "minio")
	public DataStore minioDataStore(@Autowired MinioClient minioClient) {
		return new MinioDataStore(minioClient);
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
