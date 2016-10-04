/*
 * Copyright 2016 EPAM Systems
 * 
 * 
 * This file is part of EPAM Report Portal.
 * https://github.com/epam/ReportPortal
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

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mapping.PersistentEntity;
import org.springframework.data.mapping.PropertyHandler;
import org.springframework.data.mongodb.core.DocumentCallbackHandler;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.MongoPersistentProperty;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;
import org.springframework.data.mongodb.repository.support.SimpleMongoRepository;

import com.epam.ta.reportportal.commons.accessible.Accessible;
import com.epam.ta.reportportal.database.search.CriteriaMap;
import com.epam.ta.reportportal.database.search.Filter;
import com.epam.ta.reportportal.database.search.QueryBuilder;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Lists;
import com.mongodb.WriteResult;

/**
 * Implementation of ReportPortal Repository. Adds possibility to search
 * documents/tables using custom queries
 *
 * @param <T>
 *            - Entity Type
 * @param <ID>
 *            - Entity ID Type
 * @author Andrei Varabyeu
 */
class ReportPortalRepositoryImpl<T, ID extends Serializable> extends SimpleMongoRepository<T, ID> implements ReportPortalRepository<T, ID> {
	Logger log = LoggerFactory.getLogger(ReportPortalRepositoryImpl.class);

	private MongoOperations mongoOperations;

	private MongoEntityInformation<T, ID> mongoEntityInformation;

	public ReportPortalRepositoryImpl(MongoEntityInformation<T, ID> metadata, MongoOperations mongoOperations) {
		super(metadata, mongoOperations);
		this.mongoEntityInformation = metadata;
		this.mongoOperations = mongoOperations;
	}

	protected MongoOperations getMongoOperations() {
		return mongoOperations;
	}

	protected MongoEntityInformation<T, ID> getEntityInformation() {
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
				public List<String> load(Class<?> clazz) throws Exception {
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
	public void loadWithCallback(Filter filter, Sort sorting, int quantity, List<String> chartFields, DocumentCallbackHandler callback,
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
		PersistentEntity<T, MongoPersistentProperty> persistentEntity = (PersistentEntity<T, MongoPersistentProperty>) mongoOperations
				.getConverter().getMappingContext().getPersistentEntity(getEntityInformation().getJavaType());
		persistentEntity.doWithProperties((PropertyHandler<MongoPersistentProperty>) persistentProperty -> {
			Object value = Accessible.on(t).field(persistentProperty.getField()).getValue();
			if (null != value) {
				update.set(persistentProperty.getFieldName(), value);
			}
		});

		WriteResult writeResult = mongoOperations.updateFirst(query(where(persistentEntity.getIdProperty().getFieldName()).is(id)), update,
				getEntityInformation().getCollectionName());
		if (1 != writeResult.getN()) {
			throw new IncorrectResultSizeDataAccessException(1, writeResult.getN());
		}
	}

	@Override
	public Page<T> findByFilter(Filter filter, Pageable pageable) {
		return findPage(QueryBuilder.newBuilder().with(filter).with(pageable).build(), pageable);
	}

	/**
	 * Finds page by provided {@link Query} and {@link Pageable} criterias
	 *
	 * @param q
	 * @param p
	 * @return
	 */
	protected Page<T> findPage(Query q, Pageable p) {
		Class<T> entityType = getEntityInformation().getJavaType();
		long size = getMongoOperations().count(q, entityType);
		List<T> content = getMongoOperations().find(q, entityType);
		return new PageImpl<>(content, p, size);
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
	public List<T> findByFilter(Filter filter) {
		Class<T> entityType = getEntityInformation().getJavaType();
		Query query = QueryBuilder.newBuilder().with(filter).build();
		return getMongoOperations().find(query, entityType);
	}

	@Override
	public List<T> findByFilterWithSorting(Filter filter, Sort sorting) {
		Class<T> entityType = getEntityInformation().getJavaType();
		Query query = QueryBuilder.newBuilder().with(filter).with(sorting).build();
		return getMongoOperations().find(query, entityType);
	}

	@Override
	public void delete(Collection<String> ids) {
		getMongoOperations().remove(query(where("_id").in(ids)), getEntityInformation().getJavaType());
	}

	@Override
	public List<T> find(Collection<String> ids) {
		return getMongoOperations().find(query(where("_id").in(ids)), getEntityInformation().getJavaType());
	}

	@Override
	public boolean exists(Filter filter) {
		Class<T> entityType = getEntityInformation().getJavaType();
		Query query = QueryBuilder.newBuilder().with(filter).build();
		return getMongoOperations().exists(query, entityType);
	}

	/**
	 * Find entry by id load specified fields
	 *
	 * @param id
	 * @param fieldsToLoad
	 * @return
	 */
	protected T findById(ID id, List<String> fieldsToLoad) {
		Class<T> entityType = getEntityInformation().getJavaType();
		String idField = getEntityInformation().getIdAttribute();
		Query q = query(where(idField).is(id));
		for (String field : fieldsToLoad) {
			q.fields().include(field);
		}
		return getMongoOperations().findOne(q, entityType);
	}
}