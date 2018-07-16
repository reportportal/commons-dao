package com.epam.ta.reportportal;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author Pavel Bortnik
 */
@Configuration
@EnableConfigurationProperties
@EnableAutoConfiguration
//@Import(DatabaseConfiguration.class)
@PropertySource("classpath:test-application.properties")
public class TestConfig {
	//	@Bean
	//	public DataSource dataSource() {
	//		return new EmbeddedDatabaseBuilder().generateUniqueName(true)
	////				.setType(H2)
	//				.setScriptEncoding("UTF-8")
	//				.ignoreFailedDrops(true)
	//				//.addScript("schema.sql")
	//				//				.addScripts("user_data.sql", "country_data.sql")
	//				.build();
	//	}
}
