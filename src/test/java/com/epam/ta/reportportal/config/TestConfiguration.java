package com.epam.ta.reportportal.config;

import com.epam.reportportal.commons.ContentTypeResolver;
import com.epam.reportportal.commons.Thumbnailator;
import com.epam.reportportal.commons.ThumbnailatorImpl;
import com.epam.reportportal.commons.TikaContentTypeResolver;
import com.epam.ta.reportportal.filesystem.DataEncoder;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.*;

@Configuration
@EnableConfigurationProperties
@EnableAutoConfiguration
@Import(DatabaseConfiguration.class)
@ComponentScan(basePackages = "com.epam.ta.reportportal")
@PropertySource("classpath:test-application.properties")
public class TestConfiguration {

	@Bean
	public Thumbnailator thumbnailator() {
		return new ThumbnailatorImpl();
	}

	@Bean
	public DataEncoder dataEncoder() {
		return new DataEncoder();
	}

	@Bean
	public ContentTypeResolver contentTypeResolver() {
		return new TikaContentTypeResolver();
	}


}
