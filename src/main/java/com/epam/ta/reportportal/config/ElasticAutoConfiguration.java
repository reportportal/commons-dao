package com.epam.ta.reportportal.config;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.epam.ta.reportportal.elastic.dao")
@ConditionalOnProperty(prefix = "rp.elasticsearchLogmessage", name = "host")
public class ElasticAutoConfiguration {

	@Bean
	public RestHighLevelClient client(@Value("${rp.elasticsearchLogmessage.host}") String host,
			@Value("${rp.elasticsearchLogmessage.port}") int port, @Value("${rp.elasticsearchLogmessage.username}") String username,
			@Value("${rp.elasticsearchLogmessage.password}") String password) {

		ClientConfiguration clientConfiguration = ClientConfiguration.builder()
				.connectedTo(host + ":" + port)
				.withBasicAuth(username, password)
				.build();

		return RestClients.create(clientConfiguration).rest();
	}

	@Bean
	public ElasticsearchOperations elasticsearchTemplate(RestHighLevelClient restHighLevelClient) {
		return new ElasticsearchRestTemplate(restHighLevelClient);
	}

}
