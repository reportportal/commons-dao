package com.epam.ta.reportportal.config;

import org.jooq.SQLDialect;
import org.jooq.impl.DataSourceConnectionProvider;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.DefaultDSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

/**
 * @author Pavel Bortnik
 */
//@Configuration
//@EnableConfigurationProperties
public class DatabaseConfiguration {

	@Autowired
	private DataSourceProperties properties;

	@Bean
	public DataSource dataSource() {
		return properties.initializeDataSourceBuilder().build();
	}

	@Bean
	public EntityManagerFactory entityManagerFactory() {

		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setGenerateDdl(true);

		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setJpaVendorAdapter(vendorAdapter);
		factory.setPackagesToScan("com.epam.ta.reportportal.jooq.pojos");
		factory.setDataSource(dataSource());
		factory.afterPropertiesSet();

		return factory.getObject();
	}

	@Bean
	public TransactionAwareDataSourceProxy transactionAwareDataSource() {
		return new TransactionAwareDataSourceProxy(dataSource());
	}

	@Bean
	public DataSourceTransactionManager transactionManager() {
		return new DataSourceTransactionManager(dataSource());
	}

	@Bean
	public DataSourceConnectionProvider connectionProvider() {
		return new DataSourceConnectionProvider(transactionAwareDataSource());
	}

	@Bean
	public DefaultConfiguration configuration() {
		DefaultConfiguration jooqConfiguration = new DefaultConfiguration();
		jooqConfiguration.set(SQLDialect.POSTGRES);
		jooqConfiguration.setConnectionProvider(connectionProvider());
		return jooqConfiguration;
	}

	@Bean
	public DefaultDSLContext dsl() {
		return new DefaultDSLContext(configuration());
	}
}
