package com.epam.ta.reportportal;

import com.epam.ta.reportportal.config.DatabaseConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import javax.sql.DataSource;

import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.H2;

/**
 * @author Pavel Bortnik
 */
@Configuration
@EnableConfigurationProperties
@EnableAutoConfiguration
@Import(DatabaseConfiguration.class)
@PropertySource("classpath:test-application.properties")
public class TestConfig {
		@Bean
		public DataSource dataSource() {
			return new EmbeddedDatabaseBuilder().generateUniqueName(true)
					.setType(H2)
					.setScriptEncoding("UTF-8")
					.ignoreFailedDrops(true)
					.addScript("schema.sql")
					//				.addScripts("user_data.sql", "country_data.sql")
					.build();
		}
}
