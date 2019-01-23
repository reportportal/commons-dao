/*
 * Copyright 2018 EPAM Systems
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

import com.opentable.db.postgres.embedded.EmbeddedPostgres;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;
import java.io.IOException;

/**
 * @author <a href="mailto:ihar_kahadouski@epam.com">Ihar Kahadouski</a>
 */
@Configuration
public class DataSourceConfig {

	@Value("${embedded.data.dir}")
	private String dataDir;

	@Value("${embedded.data.dir.clean}")
	private boolean clean;

	// A port number of 0 means that the port number is automatically allocated.
	@Value("${embedded.port}")
	private int port;

	@Autowired
	private DataSourceProperties properties;

	@Profile("!unittest")
	@Bean
	public DataSource dataSource() {
		return properties.initializeDataSourceBuilder().build();
	}

	@Bean
	@Profile("unittest")
	public DataSource testDataSource() throws IOException {
		final EmbeddedPostgres.Builder builder = EmbeddedPostgres.builder()
				.setPort(port)
				.setDataDirectory(System.getProperty("user.dir") + dataDir)
				.setCleanDataDirectory(clean);
		return builder.start().getPostgresDatabase();
	}
}
