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

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.epam.ta.reportportal.database.entity.filter.UserFilter;

/**
 * 
 * Repository interface for {@link UserFilter} instances. Provides basic CRUD operations due to the
 * extension of {@link CrudRepository}
 * 
 * @author Aliaksei_Makayed
 * 
 */

public interface UserFilterRepository extends UserFilterRepositoryCustom,
		ShareableRepository<UserFilter, String> {

	String TYPE_ACL_FIELDS = "{'filter.target' : 1, 'acl' : 1, 'isLink' : 1}";

	String ID_FIELD = "{'_id' : 1}";

	String SELECT_BY_USER_ID_PROJECT = "{'acl.ownerUserId': ?0, '_id' : ?1, 'projectName': ?2}";

	/**
	 * Find {@link UserFilter} by userName, projectName and id, load only id field
	 * 
	 * @param user
	 * @param projectName
	 * @param id
	 * @return {@link UserFilter}
	 */
	@Query(value = SELECT_BY_USER_ID_PROJECT, fields = ID_FIELD)
	UserFilter findOneLoadId(String userName, String id, String projectName);

	/**
	 * Find {@link UserFilter} by user, project and id, load all fields
	 * 
	 * @param userName
	 * @param projectName
	 * @param id
	 * @return {@link UserFilter}
	 */
	@Query(value = SELECT_BY_USER_ID_PROJECT)
	UserFilter findOne(String userName, String id, String projectName);

	/**
	 * Find {@link UserFilter} by userName and id, load only type field
	 * 
	 * @param user
	 * @param projectName
	 * @param id
	 * @return {@link UserFilter}
	 */
	@Query(value = SELECT_BY_USER_ID_PROJECT, fields = TYPE_ACL_FIELDS)
	UserFilter findOneLoadACLAndType(String userName, String id, String projectName);
}