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
import com.epam.ta.reportportal.filesystem.DataEncoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.quartz.QuartzAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@EnableConfigurationProperties
@EnableAutoConfiguration(exclude = QuartzAutoConfiguration.class)
@ComponentScan(basePackages = "com.epam.ta.reportportal")
@PropertySource({ "classpath:test-application.properties" })
public class TestConfiguration {

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

	@Bean
	public DataEncoder dataEncoder() {
		return new DataEncoder();
	}

}
