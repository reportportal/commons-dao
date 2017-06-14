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

/**
 * @author Dzmitry_Kavalets
 */
public interface UserPreferenceRepositoryCustom {

	void deleteByUserName(String userName);

	void deleteByUsernameAndProject(String username, String project);

	/**
	 * Delete unshared filter from user's preferences. Removed provided filter ID from preferences of all users <b>EXCEPT</b> provided one
	 *
	 * @param username Username of filter onwner
	 * @param project  Project of filter owner
	 * @param filterId ID of filter to be removed
	 */
	void deleteUnsharedFilters(String username, String project, String filterId);
}