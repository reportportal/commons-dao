package com.epam.ta.reportportal.elastic.dao;

import com.epam.ta.reportportal.entity.log.LogMessage;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
@ConditionalOnExpression("!T(org.springframework.util.StringUtils).isEmpty('${rp.elasticsearchLogmessage.host}')")
public interface LogMessageRepository extends ElasticsearchRepository<LogMessage, Long> {
}
