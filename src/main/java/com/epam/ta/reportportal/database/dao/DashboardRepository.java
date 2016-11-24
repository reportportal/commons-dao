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

import java.util.List;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.epam.ta.reportportal.database.entity.Dashboard;

/**
 * Repository interface for {@link Dashboard} instances. Provides basic CRUD operations due to the
 * extension of {@link CrudRepository}
 * 
 * @author Aliaksei_Makayed
 * 
 */
public interface DashboardRepository extends DashboardRepositoryCustom, ShareableRepository<Dashboard, String> {

	String SELECT_BY_ID = "{ '_id' : ?0 }";

	String ID_FIELD = "{'_id' : 1}";

	String SELECT_BY_USER = "{'acl.ownerUserId':?0}";

	String SELECT_BY_USER_PROJECT_DASHNAME = "{'acl.ownerUserId': ?0, 'projectName' : ?1, 'name' : ?2}";

	String SELECT_BY_USER_ID_PROJECT = "{'acl.ownerUserId': ?0, '_id' : ?1, 'projectName' : ?2}";

	/**
	 * Finds entry by IDs.
	 * 
	 * @param id
	 * @return
	 */
	Dashboard findDashboardById(String id);

	/**
	 * Find dashboard by user,projectName and id, load all fields
	 * 
	 * @param userName
	 * @param projectName
	 * @param id
	 * @return {@link Dashboard}
	 */
	// TODO consider to move this method to root repository
	@Query(value = SELECT_BY_USER_ID_PROJECT)
	Dashboard findOne(String userName, String id, String projectName);

	/**
	 * Find dashboard by user and projectName, load only id field
	 * 
	 * @param userName
	 * @param projectName
	 * @param id
	 * @return {@link Dashboard}
	 */
	@Query(value = SELECT_BY_USER_PROJECT_DASHNAME, fields = ID_FIELD)
	Dashboard findOneByUserProject(String userName, String projectName, String dashName);

	/**
	 * Find dashboard by user, projectName and id, load only id field
	 * 
	 * @param userName
	 * @param projectName
	 * @param id
	 * @return {@link Dashboard}
	 */
	@Query(value = SELECT_BY_USER_ID_PROJECT, fields = ID_FIELD)
	Dashboard findOneLoadId(String userName, String id, String projectName);

	@Override
	@Query(value = "{'projectName' : ?0}")
	List<Dashboard> findByProject(String projectRef);
}