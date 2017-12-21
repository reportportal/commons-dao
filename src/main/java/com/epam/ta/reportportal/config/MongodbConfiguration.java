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

import com.epam.ta.reportportal.database.CustomMongoConverters;
import com.epam.ta.reportportal.database.DataStorage;
import com.epam.ta.reportportal.database.GridFSDataStorage;
import com.epam.ta.reportportal.database.dao.LaunchMetaInfoRepository;
import com.epam.ta.reportportal.database.dao.ReportPortalRepositoryFactoryBean;
import com.epam.ta.reportportal.database.dao.UserRepository;
import com.epam.ta.reportportal.database.personal.PersonalProjectService;
import com.epam.ta.reportportal.database.support.RepositoriesFactoryBean;
import com.epam.ta.reportportal.database.support.RepositoryProvider;
import com.epam.ta.reportportal.database.support.impl.DefaultRepositoryProviderImpl;
import com.epam.ta.reportportal.triggers.CascadeDeleteDashboardTrigger;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.WriteConcern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.CustomConversions;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.repository.support.Repositories;

import java.net.UnknownHostException;
import java.util.Arrays;

/**
 * MongoDB configuration
 *
 * @author Andrei Varabyeu
 */
@EnableMongoRepositories(basePackageClasses = UserRepository.class, repositoryImplementationPostfix = "CustomImpl", repositoryFactoryBeanClass = ReportPortalRepositoryFactoryBean.class)
@EnableMongoAuditing
@Configuration
@EnableConfigurationProperties(MongodbConfiguration.MongoProperties.class)
@ComponentScan(basePackageClasses = { PersonalProjectService.class, CascadeDeleteDashboardTrigger.class })
public class MongodbConfiguration {

	@Autowired
	private MongoProperties mongoProperties;

	@Autowired
	private Environment environment;

	@Bean
	@Profile("!unittest")
	MongoDbFactory mongoDbFactory() throws UnknownHostException {

		SimpleMongoDbFactory mongoDbFactory = new SimpleMongoDbFactory(mongo(), mongoProperties.getDatabase());
		mongoDbFactory.setWriteConcern(WriteConcern.ACKNOWLEDGED);

		return mongoDbFactory;
	}

	@Bean
	@Profile("!unittest")
	MongoClient mongo() throws UnknownHostException {
		return mongoProperties.createMongoClient(mongoProperties.createMongoClientOptions(), environment);
	}

	@Bean
	public MappingMongoConverter mappingMongoConverter() throws UnknownHostException {
		MappingMongoConverter converter = new MappingMongoConverter(new DefaultDbRefResolver(mongoDbFactory()), mongoMappingContext());
		converter.setCustomConversions(customConversions());
		return converter;
	}

	@Bean
	MongoTemplate mongoTemplate() throws UnknownHostException {
		MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory(), mappingMongoConverter());
		mongoTemplate.setWriteConcern(WriteConcern.ACKNOWLEDGED);

		return mongoTemplate;
	}

	@Bean
	GridFsTemplate gridFsTemplate() throws UnknownHostException {
		return new GridFsTemplate(mongoDbFactory(), mappingMongoConverter());
	}

	@Bean
	DataStorage dataStorage() throws UnknownHostException {
		return new GridFSDataStorage(gridFsTemplate());
	}

	@Bean
	CustomConversions customConversions() {
		return new CustomConversions(Arrays.asList(CustomMongoConverters.LogLevelToIntConverter.INSTANCE,
				CustomMongoConverters.IntToLogLevelConverter.INSTANCE,
				CustomMongoConverters.ClassToWrapperConverter.INSTANCE,
				CustomMongoConverters.WrapperToClassConverter.INSTANCE,
				CustomMongoConverters.ActivityEventTypeToStringConverter.INSTANCE,
				CustomMongoConverters.StringToActivityEventTypeConverter.INSTANCE,
				CustomMongoConverters.ActivityObjectTypeToStringConverter.INSTANCE,
				CustomMongoConverters.StringToActivityObjectTypeConverter.INSTANCE
		));
	}

	@Bean
	MongoMappingContext mongoMappingContext() {
		return new MongoMappingContext();
	}

	@Bean
	public RepositoriesFactoryBean repositoriesFactoryBean() {
		return new RepositoriesFactoryBean();
	}

	@Bean
	public RepositoryProvider repositoryProvider(Repositories repositories) {
		return new DefaultRepositoryProviderImpl(repositories);
	}

	@Bean
	public LaunchMetaInfoRepository launchMetaInfoRepository() {
		return new LaunchMetaInfoRepository.LaunchMetaInfoRepositoryImpl();
	}

	@ConfigurationProperties("rp.mongo")
	public static class MongoProperties extends org.springframework.boot.autoconfigure.mongo.MongoProperties {

		private Integer connectionsPerHost;
		private Integer threadsAllowedToBlockForConnectionMultiplier;
		private Integer connectTimeout;
		private Integer socketTimeout;
		private Integer maxWaitTime;
		private Boolean socketKeepAlive;

		public void setUser(String user) {
			setUsername(user);
		}

		public void setDbName(String dbName) {
			setDatabase(dbName);
		}

		public void setAuthDbName(String authDbName) {
			setAuthenticationDatabase(authDbName);
		}

		public Integer getConnectionsPerHost() {
			return connectionsPerHost;
		}

		public void setConnectionsPerHost(Integer connectionsPerHost) {
			this.connectionsPerHost = connectionsPerHost;
		}

		public Integer getThreadsAllowedToBlockForConnectionMultiplier() {
			return threadsAllowedToBlockForConnectionMultiplier;
		}

		public void setThreadsAllowedToBlockForConnectionMultiplier(Integer threadsAllowedToBlockForConnectionMultiplier) {
			this.threadsAllowedToBlockForConnectionMultiplier = threadsAllowedToBlockForConnectionMultiplier;
		}

		public Integer getConnectTimeout() {
			return connectTimeout;
		}

		public void setConnectTimeout(Integer connectTimeout) {
			this.connectTimeout = connectTimeout;
		}

		public Integer getSocketTimeout() {
			return socketTimeout;
		}

		public void setSocketTimeout(Integer socketTimeout) {
			this.socketTimeout = socketTimeout;
		}

		public Integer getMaxWaitTime() {
			return maxWaitTime;
		}

		public void setMaxWaitTime(Integer maxWaitTime) {
			this.maxWaitTime = maxWaitTime;
		}

		public Boolean getSocketKeepAlive() {
			return socketKeepAlive;
		}

		public void setSocketKeepAlive(Boolean socketKeepAlive) {
			this.socketKeepAlive = socketKeepAlive;
		}

		MongoClientOptions createMongoClientOptions() {
			return MongoClientOptions.builder()
					.connectionsPerHost(this.connectionsPerHost)
					.connectTimeout(this.connectTimeout)
					.socketTimeout(this.socketTimeout)
					.threadsAllowedToBlockForConnectionMultiplier(this.threadsAllowedToBlockForConnectionMultiplier)
					.socketKeepAlive(this.socketKeepAlive)
					.maxWaitTime(this.maxWaitTime)
					.build();
		}

	}
}
