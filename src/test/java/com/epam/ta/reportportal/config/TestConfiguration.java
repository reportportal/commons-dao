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
import com.opentable.db.postgres.embedded.EmbeddedPostgres;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.quartz.QuartzAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.*;

import javax.sql.DataSource;
import java.io.IOException;

@Configuration
@EnableConfigurationProperties
@EnableAutoConfiguration(exclude = QuartzAutoConfiguration.class)
@ComponentScan(basePackages = "com.epam.ta.reportportal")
@PropertySource("classpath:test-application.properties")
public class TestConfiguration {

	@Value("${embedded.data.dir}")
	private String dataDir;

	@Value("${embedded.data.dir.clean}")
	private boolean clean;

	// A port number of 0 means that the port number is automatically allocated.
	@Value("${embedded.port}")
	private int port;

	@Bean
	@Profile("test")
	public DataSource dataSource() throws IOException {
		final EmbeddedPostgres.Builder builder = EmbeddedPostgres.builder()
				.setPort(port)
				.setDataDirectory(System.getProperty("user.dir") + dataDir)
				.setCleanDataDirectory(clean);
		return builder.start().getPostgresDatabase();
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
