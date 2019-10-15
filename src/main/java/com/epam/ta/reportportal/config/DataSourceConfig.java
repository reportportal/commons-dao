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

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.zonky.test.db.postgres.embedded.EmbeddedPostgres;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.*;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;

/**
 * @author <a href="mailto:ihar_kahadouski@epam.com">Ihar Kahadouski</a>
 */
@Configuration
@ConfigurationProperties(prefix = "rp.datasource")
public class DataSourceConfig extends HikariConfig {

	@Primary
	@Bean
	@Profile("!unittest")
	public DataSource dataSource() {
		return new HikariDataSource(this);
	}

	@Primary
	@Bean
	@Profile("unittest")
	public DataSource testDataSource(@Value("${embedded.datasource.dir}") String dataDir,
			@Value("${embedded.datasource.clean}") Boolean clean, @Value("${embedded.datasource.port}") Integer port) throws IOException {
		final EmbeddedPostgres.Builder builder = EmbeddedPostgres.builder()
				.setPort(port)
				.setDataDirectory(new File(dataDir))
				.setCleanDataDirectory(clean);
		return builder.start().getPostgresDatabase();
	}
}
