package com.epam.ta.reportportal.elastic.dao;

import com.epam.ta.reportportal.entity.log.LogMessage;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
@ConditionalOnProperty(prefix = "rp.elasticsearchLogmessage", name = "host")
@ConditionalOnBean(name = "elasticsearchTemplate")
public interface LogMessageRepository extends ElasticsearchRepository<LogMessage, Long> {
}
