package com.epam.ta.reportportal.elastic.dao;

import com.epam.ta.reportportal.entity.log.LogMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.repository.support.AbstractElasticsearchRepository;
import org.springframework.data.elasticsearch.repository.support.ElasticsearchEntityInformationCreatorImpl;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Implement rewritten methods to be able to use separated indexes for single entity based on their fields.
 *
 */
@Repository
public class LogMessageRepositoryCustomImpl extends AbstractElasticsearchRepository<LogMessage, Long>
        implements LogMessageRepositoryCustom<LogMessage> {

    @Autowired
    public LogMessageRepositoryCustomImpl(ElasticsearchOperations elasticsearchOperations) {
        super(new ElasticsearchEntityInformationCreatorImpl(
                    elasticsearchOperations.getElasticsearchConverter().getMappingContext()
                ).getEntityInformation(LogMessage.class),
                elasticsearchOperations);
    }

    @Override
    // for now without force refreshing index
    public <S extends LogMessage> S save(S logMessage) {
        Assert.notNull(logMessage, "Cannot save 'null' entity.");

        elasticsearchOperations.index(createIndexQuery(logMessage));

        return logMessage;
    }

    @Override
    // for now without force refreshing indexes
    public <S extends LogMessage> Iterable<S> saveAll(Iterable<S> logMessageIterable) {
        Assert.notNull(logMessageIterable, "Cannot insert 'null' as a List.");

        List<IndexQuery> queries = new ArrayList<>();
        for (S s : logMessageIterable) {
            queries.add(createIndexQuery(s));
        }
        elasticsearchOperations.bulkIndex(queries);

        return logMessageIterable;
    }

    @Override
    protected String stringIdRepresentation(Long id) {
        return Objects.toString(id, null);
    }

    private IndexQuery createIndexQuery(LogMessage logMessage) {

        IndexQuery query = new IndexQuery();
        query.setObject(logMessage);
        query.setIndexName(getFullIndexName(logMessage));
        query.setId(stringIdRepresentation(extractIdFromBean(logMessage)));
        query.setVersion(extractVersionFromBean(logMessage));
        query.setParentId(extractParentIdFromBean(logMessage));
        return query;
    }

    private String getFullIndexName(LogMessage logMessage) {
        return entityInformation.getIndexName() + logMessage.getProjectId();
    }

    private Long extractVersionFromBean(LogMessage logMessage) {
        return entityInformation.getVersion(logMessage);
    }

    private String extractParentIdFromBean(LogMessage logMessage) {
        return entityInformation.getParentId(logMessage);
    }
}
