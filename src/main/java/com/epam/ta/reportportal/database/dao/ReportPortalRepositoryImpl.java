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

package com.epam.ta.reportportal.database.dao;

import com.epam.ta.reportportal.commons.accessible.Accessible;
import com.epam.ta.reportportal.database.search.CriteriaMap;
import com.epam.ta.reportportal.database.search.QueryBuilder;
import com.epam.ta.reportportal.database.search.Queryable;
import com.epam.ta.reportportal.exception.ReportPortalException;
import com.epam.ta.reportportal.ws.model.ErrorType;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.mongodb.WriteResult;
import org.bson.types.ObjectId;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mapping.PropertyHandler;
import org.springframework.data.mongodb.core.DocumentCallbackHandler;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.convert.QueryMapper;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.MongoPersistentEntity;
import org.springframework.data.mongodb.core.mapping.MongoPersistentProperty;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;
import org.springframework.data.mongodb.repository.support.SimpleMongoRepository;
import org.springframework.data.repository.support.PageableExecutionUtils;

import javax.annotation.Nonnull;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

import static com.epam.ta.reportportal.database.dao.aggregation.AggregationUtils.matchOperationFromFilter;
import static com.google.common.collect.Iterables.toArray;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

/**
 * Implementation of ReportPortal Repository. Adds possibility to search
 * documents/tables using custom queries
 *
 * @param <T>  - Entity Type
 * @param <ID> - Entity ID Type
 * @author Andrei Varabyeu
 */
class ReportPortalRepositoryImpl<T, ID extends Serializable> extends SimpleMongoRepository<T, ID>
        implements ReportPortalRepository<T, ID> {

    private static final String ID_FIELD = "_id";
    private MongoOperations mongoOperations;
    private MongoEntityInformation<T, ID> mongoEntityInformation;
    private QueryMapper queryMapper;

    ReportPortalRepositoryImpl(MongoEntityInformation<T, ID> metadata, MongoOperations mongoOperations) {
        super(metadata, mongoOperations);
        this.mongoEntityInformation = metadata;
        this.mongoOperations = mongoOperations;
        this.queryMapper = new QueryMapper(mongoOperations.getConverter());
    }

    MongoOperations getMongoOperations() {
        return mongoOperations;
    }

    MongoEntityInformation<T, ID> getEntityInformation() {
        return mongoEntityInformation;
    }

    /**
     * Included fields list. Contains cache Class (collection to be queried) ->
     * DbRef fields mapping To improve performance we do not need to load refs
     * in some cases
     */
    private static LoadingCache<Class<?>, List<String>> INCLUDED_FIELDS = CacheBuilder.newBuilder()
            .build(new CacheLoader<Class<?>, List<String>>() {
                @Override
                public List<String> load(@Nonnull Class<?> clazz) throws Exception {
                    List<String> included = new ArrayList<>();
                    for (Field f : clazz.getDeclaredFields()) {
                        if (!f.isAnnotationPresent(DBRef.class) && !Modifier.isStatic(f.getModifiers())
                                && !f.isAnnotationPresent(Transient.class)) {
                            included.add(CriteriaMap.getQueryCriteria(f));
                        }
                    }
                    return included;
                }
            });

    @Override
    public void loadWithCallback(Queryable filter, Sort sorting, int quantity, List<String> chartFields,
            DocumentCallbackHandler callback,
            String collectionName) {
        if (filter == null || sorting == null || chartFields == null || callback == null || collectionName == null) {
            return;
        }
        Query query = QueryBuilder.newBuilder().with(filter).with(sorting).with(quantity).build();
        for (String field : chartFields) {
            query.fields().include(field);
        }

        getMongoOperations().executeQuery(query, collectionName, callback);
    }

    @Override
    public void partialUpdate(T t) {
        ID id = getEntityInformation().getId(t);
        if (null == id) {
            throw new IllegalArgumentException("ID property should not be null");
        }

        Update update = new Update();
        final MongoPersistentEntity<?> persistentEntity = mongoOperations
                .getConverter().getMappingContext().getPersistentEntity(getEntityInformation().getJavaType());
        persistentEntity.doWithProperties((PropertyHandler<MongoPersistentProperty>) persistentProperty -> {
            if (!persistentEntity.isIdProperty(persistentProperty)) {
                Object value = Accessible.on(t).field(persistentProperty.getField()).getValue();
                if (null != value) {
                    update.set(persistentProperty.getFieldName(), value);
                }
            }
        });

        WriteResult writeResult = mongoOperations
                .updateFirst(query(where(persistentEntity.getIdProperty().getFieldName()).is(id)), update,
                        getEntityInformation().getCollectionName());
        if (1 != writeResult.getN()) {
            throw new IncorrectResultSizeDataAccessException(1, writeResult.getN());
        }
    }

    @Override
    public Page<T> findByFilter(Queryable filter, Pageable pageable) {
        return findPage(QueryBuilder.newBuilder().with(filter).with(pageable).build(), pageable);
    }

    @Override
    public long countByFilter(Queryable filter) {
        return getMongoOperations().count(QueryBuilder.newBuilder()
                .with(filter).build(), getEntityInformation().getJavaType());
    }

    @Override
    public Page<T> findByFilterExcluding(Queryable filter, Pageable pageable, String... exclude) {
        Query query = QueryBuilder.newBuilder().with(filter).with(pageable).build();
        org.springframework.data.mongodb.core.query.Field fields = query.fields();
        if (null != exclude) {
            Arrays.stream(exclude).forEach(fields::exclude);
        }
        return findPage(query, pageable);
    }

    /**
     * Finds page by provided {@link Query} and {@link Pageable} criterias
     *
     * @param q Query for entity
     * @param p Pageable descriptor
     * @return Page of entities
     */
    Page<T> findPage(Query q, Pageable p) {
        Class<T> entityType = getEntityInformation().getJavaType();
        List<T> content = getMongoOperations().find(q, entityType);
        return PageableExecutionUtils.getPage(content, p, () -> getMongoOperations().count(q, entityType));
    }

    @Override
    public T findEntryById(ID id) {
        return this.findById(id, Lists.newArrayList(getEntityInformation().getIdAttribute()));
    }

    @Override
    @SuppressWarnings("unchecked")
    public T findOneNoJoin(ID id) {
        Class<T> entityType = getEntityInformation().getJavaType();
        Query q = query(where(getEntityInformation().getIdAttribute()).is(id));
        org.springframework.data.mongodb.core.query.Field fields = q.fields();
        for (String include : INCLUDED_FIELDS.getUnchecked(entityType)) {
            fields.include(include);
        }
        return getMongoOperations().findOne(q, entityType);
    }

    @Override
    public Optional<T> findOneNullSafe(ID id) {
        return Optional.ofNullable(findOne(id));
    }

    @Override
    public List<T> findByFilter(Queryable filter) {
        Class<T> entityType = getEntityInformation().getJavaType();
        Query query = QueryBuilder.newBuilder().with(filter).build();
        return getMongoOperations().find(query, entityType);
    }

    @Override
    public List<T> findByFilterWithSorting(Queryable filter, Sort sorting) {
        Class<T> entityType = getEntityInformation().getJavaType();
        Query query = QueryBuilder.newBuilder().with(filter).with(sorting).build();
        return getMongoOperations().find(query, entityType);
    }

    @Override
    public void delete(Collection<String> ids) {
        getMongoOperations().remove(query(where(ID_FIELD).in(ids)), getEntityInformation().getJavaType());
    }

    @Override
    public List<T> find(Collection<String> ids) {
        return getMongoOperations().find(query(where(ID_FIELD).in(ids)), getEntityInformation().getJavaType());
    }

    @Override
    public boolean exists(Queryable filter) {
        Class<T> entityType = getEntityInformation().getJavaType();
        Query query = QueryBuilder.newBuilder().with(filter).build();
        return getMongoOperations().exists(query, entityType);
    }

    @Override
    public long getPageNumber(String entityId, Queryable filterable, Pageable pageable) {
        ImmutableList.Builder<AggregationOperation> pipelineBuilder = ImmutableList.<AggregationOperation>builder()
                .add(matchOperationFromFilter(filterable, mongoOperations, this.getEntityInformation().getJavaType()));

        if (null != pageable.getSort()){
            pipelineBuilder.add(
                    /* sort results as requested */
                    sort(pageable.getSort())
            );
        }

        pipelineBuilder.add(
                /* group items into one field pushing all results into one array */
                group("result").push("$_id").as("array"),
                /* unwind array adding index to each result */
                unwind("array", "ind", false),
                /* find needed item. How we know its index in query result */
                match(where("array").is(ObjectId.isValid(entityId) ? new ObjectId(entityId) : entityId)),
                /* grab index only */
                project("ind"));

        /* find items matching an provided filter */
        Aggregation a = Aggregation
                .newAggregation(toArray(pipelineBuilder.build(), AggregationOperation.class));

        final AggregationResults<Map> aggregate =
                mongoOperations.aggregate( a, getEntityInformation().getCollectionName(), Map.class);

        if (!aggregate.getUniqueMappedResult().containsKey("ind")) {
            throw new ReportPortalException(ErrorType.INCORRECT_FILTER_PARAMETERS, "Unable to calculate page number. Check your input parameters");
        }

        /* result returned as long. Calculate page number */
        return (long) Math.ceil ((((Long) aggregate.getUniqueMappedResult().get("ind")).doubleValue() + 1d) / (double) pageable.getPageSize());
    }

    /**
     * Find entry by id load specified fields
     *
     * @param id           Entity ID
     * @param fieldsToLoad Fields to load
     * @return Found entity
     */
    T findById(ID id, List<String> fieldsToLoad) {
        Class<T> entityType = getEntityInformation().getJavaType();
        String idField = getEntityInformation().getIdAttribute();
        Query q = query(where(idField).is(id));
        for (String field : fieldsToLoad) {
            q.fields().include(field);
        }
        return getMongoOperations().findOne(q, entityType);
    }
}
