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
import com.epam.ta.reportportal.filesystem.DataEncoder;
import com.opentable.db.postgres.junit.EmbeddedPostgresRules;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.*;

import javax.sql.DataSource;

@Configuration
@EnableConfigurationProperties
@EnableAutoConfiguration
@Import(DatabaseConfiguration.class)
@ComponentScan(basePackages = "com.epam.ta.reportportal")
@PropertySource("classpath:test-application.properties")
public class TestConfiguration {

	@Bean
	@Profile("test")
	public DataSource dataSource() {
		return EmbeddedPostgresRules.singleInstance().getEmbeddedPostgres().getPostgresDatabase();
	}

	@Bean
	public Thumbnailator thumbnailator() {
		return new ThumbnailatorImpl();
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
