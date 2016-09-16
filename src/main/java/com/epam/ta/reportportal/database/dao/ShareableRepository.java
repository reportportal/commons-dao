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

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.NoRepositoryBean;

import com.epam.ta.reportportal.database.search.Filter;

/**
 * Extension of {@link ReportPortalRepository} which provide functionality for working with sharable
 * objects.
 * 
 * @author Aliaksei_Makayed
 *
 * @param <T>
 * @param <ID>
 */
@NoRepositoryBean
public interface ShareableRepository<T, ID extends Serializable> extends ReportPortalRepository<T, ID> {

	/**
	 * Find shared entities for specified project which is not owned by specified user, load
	 * specified fields if no one field specified all fields will be loaded.
	 * 
	 * @param projectName
	 * @param fields
	 * @return
	 */
	List<T> findSharedEntities(String owner, String projectName, List<String> fields, Sort sort);

	/**
	 * Find all entities(shared to project and owned by user) by filter and pageable
	 * 
	 * @return
	 */
	Page<T> findAllByFilter(Filter filter, Pageable pageable, String projectName, String owner);

	/**
	 * Find specified by ids owner entities. If ids is null or empty method return all owned by
	 * specified user entities
	 * 
	 * @param ids
	 * @param owner
	 * @return
	 */
	List<T> findOnlyOwnedEntities(Set<String> ids, String owner);

	/**
	 * Find entries by project.
	 * 
	 * @param projectName
	 * @return
	 */
	List<T> findByProject(String projectName);

	/**
	 * Find entry by id load only id and acl fields
	 * 
	 * @param id
	 * @return
	 */
	T findOneLoadACL(ID id);

	/**
	 * Find all non shared entities owned by specified user
	 * 
	 * @param owner
	 * @return
	 */
	List<T> findNonSharedEntities(String owner);

}