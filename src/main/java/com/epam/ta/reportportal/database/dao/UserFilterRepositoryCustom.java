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

import org.springframework.data.domain.Sort;

import com.epam.ta.reportportal.database.entity.filter.UserFilter;

public interface UserFilterRepositoryCustom {

	/**
	 * Find {@link UserFilter} by name, userName, projectName load id field
	 * 
	 * @param userName
	 * @param name
	 * @param projectName
	 * @return
	 */
	UserFilter findOneByName(String userName, String name, String projectName);

	/**
	 * Find filters(Not links) names
	 * 
	 * @param userName
	 * @param isShared
	 *            true - load only shared to project, false - load only owned by user
	 * @param projectName
	 * @return
	 */
	List<UserFilter> findFilters(String userName, String projectName, Sort sort, boolean isShared);

	/**
	 * Find user filter by owner(or shared), id and project name
	 * 
	 * @param userName
	 * @param id
	 * @param projectName
	 * @return acl.entries and filter.target fields of user filter
	 */
	UserFilter findOneLoadACL(String userName, String id, String projectName);

	/**
	 * Find available filters by owner(or shared)
	 * 
	 * @param projectName
	 * @param ids
	 * @param userName
	 * @return
	 */
	List<UserFilter> findAvailableFilters(String projectName, String[] ids, String userName);
}