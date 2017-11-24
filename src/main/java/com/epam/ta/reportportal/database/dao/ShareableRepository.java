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

import com.epam.ta.reportportal.database.search.Filter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * Extension of {@link ReportPortalRepository} which provide functionality for working with sharable
 * objects.
 *
 * @param <T>
 * @param <ID>
 * @author Aliaksei_Makayed
 */
@NoRepositoryBean
public interface ShareableRepository<T, ID extends Serializable> extends ReportPortalRepository<T, ID> {

	/**
	 * Find shared entities for specified project, load
	 * specified fields if no one field specified all fields will be loaded.
	 *
	 * @param projectName Project Name
	 * @param fields      Fields to load
	 * @param sort        Sorting details
	 * @return Found entities
	 */
	Page<T> findSharedEntities(String projectName, List<String> fields, Sort sort, Pageable pageable);

	/**
	 * Find shared entities for specified project with names that contain search criteria
	 *
	 * @param projectName Project Name
	 * @return Found entities
	 */
	List<T> findSharedEntitiesByName(String projectName, String term);

	/**
	 * Find all entities(shared to project and owned by user) by filter and pageable
	 *
	 * @param filter      Filtering details
	 * @param pageable    Paging details
	 * @param projectName Project Name
	 * @param owner       Item Owner
	 * @return Found entities
	 */
	Page<T> findAllByFilter(Filter filter, Pageable pageable, String projectName, String owner);

	/**
	 * Find specified by ids owner entities. If ids is null or empty method return all owned by
	 * specified user entities
	 *
	 * @param ids   IDs list to filter
	 * @param owner Item Owner
	 * @return Found Entities
	 */
	List<T> findOnlyOwnedEntities(Set<String> ids, String owner);

	/**
	 * Find entries by project.
	 *
	 * @param projectName Project name
	 * @return Found Entities
	 */
	List<T> findByProject(String projectName);

	/**
	 * Find entry by id load only id and acl fields
	 *
	 * @param id ID of entity
	 * @return Found entity
	 */
	T findOneLoadACL(ID id);

	/**
	 * Find all non shared entities owned by specified user
	 *
	 * @param owner Entity owner
	 * @return Found Entities
	 */
	List<T> findNonSharedEntities(String owner);

}