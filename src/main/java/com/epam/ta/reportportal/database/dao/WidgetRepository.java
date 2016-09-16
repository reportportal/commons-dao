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

import com.epam.ta.reportportal.database.entity.widget.Widget;

import java.util.List;

/**
 * 
 * Repository interface for {@link Widget} instances. Provides basic CRUD
 * operations due to the extension of {@link CrudRepository}
 * 
 * @author Aliaksei_Makayed
 * 
 */

public interface WidgetRepository extends ShareableRepository<Widget, String>, WidgetRepositoryCustom {

	String SELECT_BY_USER_NAME_AND_ID = "{'acl.ownerUserId': ?0, '_id' : ?1}";
	String SELECT_BY_PROJECT_AND_USER = "{'projectName': ?0, 'acl.ownerUserId' : ?1}";

	/**
	 * Find {@link Widget} by user and id, load all fields
	 * 
	 * @param user
	 * @param id
	 * @return {@link Widget}
	 */
	@Query(value = SELECT_BY_USER_NAME_AND_ID)
	Widget findByUserAndId(String userName, String id);

	/**
	 * Find {@link com.epam.ta.reportportal.database.entity.widget.Widget} by
	 * project and user
	 * 
	 * @param projectName
	 * @param userName
	 * @return
	 */
	@Query(value = SELECT_BY_PROJECT_AND_USER, fields = "{'name' : 1}")
	List<Widget> findByProjectAndUser(String projectName, String userName);

}