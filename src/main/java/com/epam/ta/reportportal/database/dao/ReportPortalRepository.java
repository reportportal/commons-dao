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

import com.epam.ta.reportportal.database.search.Queryable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.DocumentCallbackHandler;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Interface with set of custom operations to all ReportPortal Mongo
 * Repositories
 *
 * @param <T>  - Entity Type
 * @param <ID> - Entity ID Type
 * @author Andrei Varabyeu
 */
@NoRepositoryBean
public interface ReportPortalRepository<T, ID extends Serializable> extends MongoRepository<T, ID> {

	/**
	 * Finds entry wrapper with null-safe wrapper
	 * {@link com.google.common.base.Optional}
	 *
	 * @param id Entity ID
	 * @return Optional of object
	 */
	Optional<T> findOneNullSafe(ID id);

	/**
	 * Finds entities list according provided filter
	 *
	 * @param filter Query representation
	 * @return Found Objects
	 */
	List<T> findByFilter(Queryable filter);

	/**
	 * Find entries via specified filter and sorting field
	 *
	 * @param filter  Query representation
	 * @param sorting Sorting Representation
	 * @return Found objects
	 */
	List<T> findByFilterWithSorting(Queryable filter, Sort sorting);

	/**
	 * Finds entities list according provided filter
	 *
	 * @param filter   Filter - Query representation
	 * @param pageable Page Representation
	 * @return Found Paged objects
	 */
	Page<T> findByFilter(Queryable filter, Pageable pageable);

	/**
	 * Finds entities list according provided filter
	 *
	 * @param filter   Filter - Query representation
	 * @param pageable Page Representation
	 * @param exclude  Fields to exclude from query
	 * @return Found Paged objects
	 */
	Page<T> findByFilterExcluding(Queryable filter, Pageable pageable, String... exclude);

	/**
	 * Find entry by id, load only id field
	 *
	 * @param id Entity ID
	 * @return Found Entity
	 */
	T findEntryById(ID id);

	/**
	 * Finds object by id, but doesn't load joined entities (
	 * {@link org.springframework.data.mongodb.core.mapping.DBRef})
	 *
	 * @param id Entity ID
	 * @return Found ID
	 */
	T findOneNoJoin(ID id);

	/**
	 * Load chart data according specified input parameters. Result should be
	 * returned from {@link DocumentCallbackHandler} object
	 *
	 * @param filter         Query filter
	 * @param sorting        Sorting
	 * @param chartFields    Chart fields to load
	 * @param quantity       Quantity
	 * @param callback       Results callback
	 * @param collectionName Name of collection to load data from
	 */
	void loadWithCallback(Queryable filter, Sort sorting, int quantity, List<String> chartFields, DocumentCallbackHandler callback,
			String collectionName);

	/**
	 * Partial update. Updates only non-null fields from provided object. DOES
	 * NOT removes NULL fields from DB object
	 *
	 * @param t Object to be updated
	 */
	void partialUpdate(T t);

	void delete(Collection<String> ids);

	List<T> find(Collection<String> ids);

	/**
	 * @param filter Query representation
	 * @return TRUE if entity exists in database
	 */
	boolean exists(Queryable filter);

	/**
	 * Counts entities by provided filter
	 *
	 * @param filter Filter
	 * @return Count of entities
	 */
	long countByFilter(Queryable filter);

	/**
	 * Calculates number of page for given entityID with provided filter and page request
	 *
	 * @param entityId   ID of entity
	 * @param filterable Filter to be applied
	 * @param pageable   Paging details to be applied
	 * @return Number of page if select with provided filter and paging
	 */
	long getPageNumber(String entityId, Queryable filterable, Pageable pageable);

	/**
	 * Find entry by id load specified fields
	 *
	 * @param id           Entity ID
	 * @param fieldsToLoad Fields to load
	 * @return Found entity
	 */
	T findById(ID id, List<String> fieldsToLoad);

	/**
	 * Find entries by ids load specified fields
	 *
	 * @param ids          Entities IDs
	 * @param fieldsToLoad Fields to load
	 * @return Found entities
	 */
	List<T> findByIds(Collection<ID> ids, List<String> fieldsToLoad);

}