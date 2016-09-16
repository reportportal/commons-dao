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

import com.epam.ta.reportportal.database.search.Filter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.DocumentCallbackHandler;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * Interface with set of custom operations to all ReportPortal Mongo Repositories
 *
 * @param <T>  - Entity Type
 * @param <ID> - Entity ID Type
 * @author Andrei Varabyeu
 */
@NoRepositoryBean
public interface ReportPortalRepository<T, ID extends Serializable> extends MongoRepository<T, ID> {

	/**
	 * Finds entry wrapper with null-safe wrapper {@link com.google.common.base.Optional}
	 *
	 * @param id
	 * @return
	 */
	Optional<T> findOneNullSafe(ID id);

	/**
	 * Finds entities list according provided filter
	 *
	 * @param q
	 * @return
	 */
	List<T> findByFilter(Filter filter);

	/**
	 * Find entries via specified filter and sorting field
	 *
	 * @param filter
	 * @param sorting
	 * @return
	 */
	List<T> findByFilterWithSorting(Filter filter, Sort sorting);

	/**
	 * Finds entities list according provided filter
	 *
	 * @param q
	 * @return
	 */
	Page<T> findByFilter(Filter filter, Pageable pageable);

	/**
	 * Find entry by id, load only id field
	 *
	 * @param id
	 * @return
	 */
	T findEntryById(ID id);

	/**
	 * Finds object by id, but doesn't load joined entities (
	 * {@link org.springframework.data.mongodb.core.mapping.DBRef})
	 *
	 * @param id
	 * @return
	 */
	T findOneNoJoin(ID id);

	/**
	 * Load chart data according specified input parameters. Result should be returned from
	 * {@link DocumentCallbackHandler} object
	 *
	 * @param filter
	 * @param sorting
	 * @param chartFields
	 * @param quantity
	 * @param callback
	 * @param collectionName
	 */
	void loadWithCallback(Filter filter, Sort sorting, int quantity, List<String> chartFields, DocumentCallbackHandler callback,
			String collectionName);

	/**
	 * Partial update. Updates only non-null fields from provided object. DOES NOT removes NULL fields from DB object
	 *
	 * @param t Object to be updated
	 */
	void partialUpdate(T t);

}