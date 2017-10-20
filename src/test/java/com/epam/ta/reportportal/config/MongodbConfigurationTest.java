/*
 * Copyright 2016 EPAM Systems
 *
 *
 * This file is part of EPAM Report Portal.
 * https://github.com/reportportal/commons-dao
 *
 * Report Portal is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Report Portal is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Report Portal.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.epam.ta.reportportal.config;

import com.github.fakemongo.Fongo;
import com.mongodb.MockMongoClient;
import com.mongodb.WriteConcern;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Andrei Varabyeu
 */
@ContextConfiguration(classes = { MongodbConfigurationTest.TestConfig.class,
		MongodbConfiguration.class }, initializers = ConfigFileApplicationContextInitializer.class)
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("unittest")
public class MongodbConfigurationTest {

	@Autowired
	private MongodbConfiguration.MongoProperties mongoProperies;

	/**
	 * Tests backward-compatibility between old-style and new-style mongo configuration parameters
	 */
	@Test
	public void testContext() {
		Assert.assertThat(mongoProperies.getDatabase(), CoreMatchers.equalTo("test-db-name"));
		Assert.assertThat(mongoProperies.getAuthenticationDatabase(), CoreMatchers.equalTo("test-auth-db-name"));
		Assert.assertThat(mongoProperies.getUsername(), CoreMatchers.equalTo("test-user"));
	}

	@Configuration
	@PropertySource("classpath:mongodb-test.properties")
	public static class TestConfig {

		@Bean
		public static PropertySourcesPlaceholderConfigurer propertiesResolver() {
			return new PropertySourcesPlaceholderConfigurer();
		}

		@Bean
		@Primary
		public MongoDbFactory mongoDbFactory() {
			final Fongo fongo = new Fongo("InMemoryMongo");
			SimpleMongoDbFactory mongoDbFactory = new SimpleMongoDbFactory(MockMongoClient.create(fongo), "reportportal");
			mongoDbFactory.setWriteConcern(WriteConcern.ACKNOWLEDGED);
			return mongoDbFactory;
		}
	}

}