package com.epam.ta.reportportal.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@EnableConfigurationProperties
@EnableAutoConfiguration
@ComponentScan(basePackages = "com.epam.ta.reportportal.dao")
@PropertySource("classpath:test-application.properties")
public class TestConfiguration {

}
