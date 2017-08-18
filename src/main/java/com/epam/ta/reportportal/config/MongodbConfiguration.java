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
import com.google.common.base.Strings;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.WriteConcern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
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
import java.util.Collections;
import java.util.List;

import static java.util.Optional.ofNullable;

/**
 * MongoDB configuration
 *
 * @author Andrei Varabyeu
 */
@EnableMongoRepositories(basePackageClasses = UserRepository.class, repositoryImplementationPostfix = "CustomImpl", repositoryFactoryBeanClass = ReportPortalRepositoryFactoryBean.class)
@EnableMongoAuditing
@Configuration
@EnableConfigurationProperties(MongodbConfiguration.MongoProperies.class)
@ComponentScan(basePackageClasses = { PersonalProjectService.class, CascadeDeleteDashboardTrigger.class })
public class MongodbConfiguration {

    @Autowired
    private MongoProperies mongoProperties;

    @Bean
    @Profile("!unittest")
    MongoDbFactory mongoDbFactory() throws UnknownHostException {

        SimpleMongoDbFactory mongoDbFactory = new SimpleMongoDbFactory(mongo(), mongoProperties.getDbName());

        mongoDbFactory.setWriteConcern(WriteConcern.ACKNOWLEDGED);

        return mongoDbFactory;
    }

    @Bean
    @Profile("!unittest")
    MongoClient mongo() throws UnknownHostException {
        MongoClientOptions.Builder mongoClientBuilder = MongoClientOptions.builder();
        mongoClientBuilder.connectionsPerHost(mongoProperties.getConnectionsPerHost())
                .threadsAllowedToBlockForConnectionMultiplier(
                        mongoProperties.getThreadsAllowedToBlockForConnectionMultiplier())
                .connectTimeout(mongoProperties.getConnectTimeout()).socketTimeout(mongoProperties.getSocketTimeout())
                .maxWaitTime(mongoProperties.getMaxWaitTime()).socketKeepAlive(mongoProperties.getSocketKeepAlive());

        List<MongoCredential> credentials = Collections.emptyList();
        if (!Strings.isNullOrEmpty(mongoProperties.getUser()) && !Strings
                .isNullOrEmpty(mongoProperties.getPassword())) {
            credentials = Collections.singletonList(MongoCredential.createCredential(mongoProperties.getUser(),
                    ofNullable(mongoProperties.getAuthDbName()).orElse(mongoProperties.getDbName()),
                    mongoProperties.getPassword().toCharArray()));
        }

        return new MongoClient(new ServerAddress(mongoProperties.getHost(), mongoProperties.getPort()), credentials,
                mongoClientBuilder.build());
    }

    @Bean
    public MappingMongoConverter mappingMongoConverter() throws UnknownHostException {
        MappingMongoConverter converter = new MappingMongoConverter(new DefaultDbRefResolver(mongoDbFactory()),
                mongoMappingContext());
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
        return new CustomConversions(
                Arrays.asList(CustomMongoConverters.LogLevelToIntConverter.INSTANCE,
                        CustomMongoConverters.IntToLogLevelConverter.INSTANCE,
                        CustomMongoConverters.ClassToWrapperConverter.INSTANCE,
                        CustomMongoConverters.WrapperToClassConverter.INSTANCE,
                        CustomMongoConverters.ActivityEventTypeToStringConverter.INSTANCE,
                        CustomMongoConverters.StringToActivityEventTypeConverter.INSTANCE,
                        CustomMongoConverters.ActivityObjectTypeToStringConverter.INSTANCE,
                        CustomMongoConverters.StringToActivityObjectTypeConverter.INSTANCE));
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
    public static class MongoProperies {
        private String dbName;
        private String authDbName;
        private String host;
        private Integer port;
        private String user;
        private String password;
        private Integer connectionsPerHost;
        private Integer threadsAllowedToBlockForConnectionMultiplier;
        private Integer connectTimeout;
        private Integer socketTimeout;
        private Integer maxWaitTime;
        private Boolean socketKeepAlive;

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getDbName() {
            return dbName;
        }

        public void setDbName(String dbName) {
            this.dbName = dbName;
        }

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public Integer getPort() {
            return port;
        }

        public void setPort(Integer port) {
            this.port = port;
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

        public void setThreadsAllowedToBlockForConnectionMultiplier(
                Integer threadsAllowedToBlockForConnectionMultiplier) {
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

        public String getAuthDbName() {
            return authDbName;
        }

        public void setAuthDbName(String authDbName) {
            this.authDbName = authDbName;
        }
    }
}
