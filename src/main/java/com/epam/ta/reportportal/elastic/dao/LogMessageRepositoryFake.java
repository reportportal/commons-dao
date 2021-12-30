package com.epam.ta.reportportal.elastic.dao;

import com.epam.ta.reportportal.entity.log.LogMessage;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/**
 * Fake repository, need in case if configuration for elastic with logs doesn't exist.
 */
@Repository
@ConditionalOnMissingBean(value = { ElasticsearchOperations.class, RestHighLevelClient.class })
public class LogMessageRepositoryFake implements LogMessageRepository {
    private final Iterable<LogMessage> logMessageIterable = () -> null;

    @Override
    public <S extends LogMessage> S index(S entity) {
        return null;
    }

    @Override
    public <S extends LogMessage> S indexWithoutRefresh(S entity) {
        return null;
    }

    @Override
    public Iterable<LogMessage> search(QueryBuilder query) {
        return null;
    }

    @Override
    public Page<LogMessage> search(QueryBuilder query, Pageable pageable) {
        return null;
    }

    @Override
    public Page<LogMessage> search(SearchQuery searchQuery) {
        return null;
    }

    @Override
    public Page<LogMessage> searchSimilar(LogMessage entity, String[] fields, Pageable pageable) {
        return null;
    }

    @Override
    public void refresh() {

    }

    @Override
    public Class<LogMessage> getEntityClass() {
        return null;
    }

    @Override
    public Iterable<LogMessage> findAll(Sort sort) {
        return logMessageIterable;
    }

    @Override
    public Page<LogMessage> findAll(Pageable pageable) {
        return new Page<LogMessage>() {
            @Override
            public int getTotalPages() {
                return 0;
            }

            @Override
            public long getTotalElements() {
                return 0;
            }

            @Override
            public <U> Page<U> map(Function<? super LogMessage, ? extends U> converter) {
                return Page.empty();
            }

            @Override
            public int getNumber() {
                return 0;
            }

            @Override
            public int getSize() {
                return 0;
            }

            @Override
            public int getNumberOfElements() {
                return 0;
            }

            @Override
            public List<LogMessage> getContent() {
                return new ArrayList<>();
            }

            @Override
            public boolean hasContent() {
                return false;
            }

            @Override
            public Sort getSort() {
                return Sort.unsorted();
            }

            @Override
            public boolean isFirst() {
                return false;
            }

            @Override
            public boolean isLast() {
                return false;
            }

            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public boolean hasPrevious() {
                return false;
            }

            @Override
            public Pageable nextPageable() {
                return Pageable.unpaged();
            }

            @Override
            public Pageable previousPageable() {
                return Pageable.unpaged();
            }

            @Override
            public Iterator<LogMessage> iterator() {
                return null;
            }
        };
    }

    @Override
    public <S extends LogMessage> S save(S entity) {
        return entity;
    }

    @Override
    public <S extends LogMessage> Iterable<S> saveAll(Iterable<S> entities) {
        return entities;
    }

    @Override
    public Optional<LogMessage> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public Iterable<LogMessage> findAll() {
        return logMessageIterable;
    }

    @Override
    public Iterable<LogMessage> findAllById(Iterable<Long> longs) {
        return logMessageIterable;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(LogMessage entity) {

    }

    @Override
    public void deleteAll(Iterable<? extends LogMessage> entities) {

    }

    @Override
    public void deleteAll() {

    }
}
