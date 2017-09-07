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

import com.epam.ta.reportportal.database.entity.Launch;
import com.epam.ta.reportportal.database.entity.Project;
import com.epam.ta.reportportal.database.entity.Status;
import com.epam.ta.reportportal.database.entity.item.TestItem;
import com.epam.ta.reportportal.database.entity.statistics.StatisticSubType;
import com.epam.ta.reportportal.database.search.Filter;
import com.epam.ta.reportportal.database.search.Queryable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.DocumentCallbackHandler;

import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface LaunchRepositoryCustom extends StatisticsUpdatePolicy<TestItem, Project.Configuration> {

	/**
	 * Whether launch contains items
	 *
	 * @param launch
	 * @return
	 */
	boolean hasItems(Launch launch);

	/**
	 * Whether launch contains items with provided state
	 *
	 * @param launch
	 * @return
	 */
	boolean hasItems(Launch launch, Status itemStatus);

	/**
	 * Finds launches for specified project
	 *
	 * @param project
	 * @return
	 */
	List<Launch> findLaunchIdsByProject(Project project);

	/**
	 * Finds launches modified later than period
	 *
	 * @param period
	 * @param status
	 * @return
	 */
	List<Launch> findModifiedLaterAgo(Duration period, Status status);

	/**
	 * Finds launches modified later than period
	 *
	 * @param period
	 * @param status
	 * @param project
	 * @return
	 */
	List<Launch> findModifiedLaterAgo(Duration period, Status status, String project);

	/**
	 * Find launches by filter load only id and number fields. Load specified
	 * quantity of launches.
	 *
	 * @param filter
	 * @param sort
	 * @param quantity
	 * @return
	 */
	List<Launch> findIdsByFilter(Filter filter, Sort sort, int quantity);

	/**
	 * Find launches by filter load only id field.
	 *
	 * @param filter
	 * @return
	 */
	List<Launch> findIdsByFilter(Filter filter);

	/**
	 * Find launches by filter only id field.
	 *
	 * @param filter
	 * @param limit
	 * @return
	 */
	List<Launch> findIdsByFilter(Filter filter, int limit);

	/**
	 * Get list of distinct values from MongoDB collection
	 *
	 * @param projectName
	 * @param containsValue
	 * @param distinctBy
	 * @return
	 */
	List<String> findDistinctValues(String projectName, String containsValue, String distinctBy);

	/**
	 * Find launches by user ref
	 *
	 * @param userRef
	 * @return
	 */
	List<Launch> findByUserRef(String userRef);

	/**
	 * Update user ref in launches
	 *
	 * @param oldOwner
	 * @param newOwner
	 */
	void updateUserRef(String oldOwner, String newOwner);

	/**
	 * Get all unique fields for launches in specified mode
	 *
	 * @param projectName
	 * @param containsValue
	 * @param distinctBy
	 * @return
	 */
	List<String> findValuesWithMode(String projectName, String containsValue, String distinctBy, String mode);

	/**
	 * Get grouped launches per owner. 'IN_PROGRESS' launches are excluded
	 *
	 * @param projectName
	 * @param mode
	 * @return
	 */
	Map<String, Integer> findGroupedLaunchesByOwner(String projectName, String mode, Date from);

	/**
	 * Find launches by specified projectId, mode and from date. 'IN_PROGRESS'
	 * launches are excluded
	 *
	 * @param projectId
	 * @param from
	 * @param mode
	 * @return
	 */
	List<Launch> findLaunchesByProjectId(String projectId, Date from, String mode);

	/**
	 * Find launches quantity. 'IN_PROGRESS' launches are excluded
	 *
	 * @param projectId
	 * @param mode
	 * @param from
	 * @return
	 */
	Long findLaunchesQuantity(String projectId, String mode, Date from);

	/**
	 * Find latest unique launches by filter
	 *
	 * @param filter
	 * @param pageable
	 * @return
	 */

	Page<Launch> findLatestLaunches(Queryable filter, Pageable pageable);

	/**
	 * Load chart data according specified input parameters grouped by containing
	 * tag with regex. Result will be stored in {@link DocumentCallbackHandler} object
	 *
	 * @param filter			Filter
	 * @param contentFields		Content fields for creating results
	 * @param limit				Results limit
	 * @param tagPrefix			Tag prefix for grouping by
	 * @param callbackHandler	Results handler
	 */
	void cumulativeStatisticsGroupedByTag(Queryable filter, List<String> contentFields, long limit, String tagPrefix,
			DocumentCallbackHandler callbackHandler);

	/**
	 * Load chart data according specified input parameters and only for latest launches.
	 * Result should be returned from {@link DocumentCallbackHandler} object
	 *
	 * @param filter
	 * @param sort
	 * @param contentFields
	 * @param limit
	 * @param callbackHandler
	 */
	void findLatestWithCallback(Queryable filter, Sort sort, List<String> contentFields, long limit,
			DocumentCallbackHandler callbackHandler);

	/**
	 * Find last launch. 'IN_PROGRESS' launch is excluded
	 *
	 * @param projectId
	 * @return
	 */
	Optional<Launch> findLastLaunch(String projectId, String mode);

	/**
	 * Find latest launch. 'IN_PROGRESS' launch is excluded
	 *
	 * @param projectName name
	 * @param launchName  launch
	 * @param mode        mode
	 * @return launch with latest number
	 */
	Optional<Launch> findLatestLaunch(String projectName, String launchName, String mode);

	/**
	 * Find last launch within specified project and mode
	 */
	Optional<Launch> findLastLaunch(String projectId, String launchName, String mode);

	List<Launch> findByFilterWithSortingAndLimit(Filter filter, Sort sort, int limit);

	List<Launch> findLaunchesWithSpecificStat(String projectRef, StatisticSubType type);

	void dropIssueStatisticsType(String id, StatisticSubType type);

	List<String> findLaunchIdsByProjectIds(List<String> ids);
}