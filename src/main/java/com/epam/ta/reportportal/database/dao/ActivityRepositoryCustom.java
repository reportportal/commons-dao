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

import org.springframework.data.domain.Pageable;

import com.epam.ta.reportportal.database.Time;
import com.epam.ta.reportportal.database.entity.item.Activity;
import com.epam.ta.reportportal.database.search.Filter;
import org.springframework.data.domain.Sort;

/**
 * @author Dzmitry_Kavalets
 */
public interface ActivityRepositoryCustom {

	/**
	 * Find activities for specified test item
	 *
	 * @param testItemId
	 * @return
	 */
	List<Activity> findActivitiesByTestItemId(String testItemId, Filter filter, Pageable pageable);

	/**
	 * Find activities for specified project
	 *
	 * @param projectId
	 * @param filter
	 * @param pageable
	 * @return
	 */
	List<Activity> findActivitiesByProjectId(String projectId, Filter filter, Pageable pageable);

	/**
	 * Delete outdated activities
	 *
	 * @param projectId
	 * @param period
	 */
	void deleteModifiedLaterAgo(String projectId, Time period);

	List<Activity> findByFilterWithSortingAndLimit(Filter filter, Sort sort, int limit);

	List<Activity> findByLoggedObjectRef(String objectRef);

}