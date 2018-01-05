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
import java.util.stream.Stream;

public interface LaunchRepositoryCustom extends StatisticsUpdatePolicy<TestItem, Project.Configuration> {

	/**
	 * Whether launch contains items
	 *
	 * @param launch Launch to be searched
	 * @return TRUE of there is at least one item under this launch
	 */
	boolean hasItems(Launch launch);

	/**
	 * Whether launch contains items with provided state
	 *
	 * @param launch Launch to be searched
	 * @return TRUE of there is at least one item under this launch with provided status
	 */
	boolean hasItems(Launch launch, Status itemStatus);

	/**
	 * Finds launches for specified project
	 *
	 * @param project Project launch should be executed under
	 * @return List of launches
	 */
	List<Launch> findLaunchIdsByProject(Project project);

	/**
	 * Finds launches modified later than period
	 *
	 * @param period Time period
	 * @param status Launch Status
	 * @return List of launches
	 */
	List<Launch> findModifiedLaterAgo(Duration period, Status status);

	/**
	 * Finds launches modified later than period
	 *
	 * @param period  Time period
	 * @param status  Launch Status
	 * @param project Project launch was be executed under
	 * @return List of launches
	 */
	List<Launch> findModifiedLaterAgo(Duration period, Status status, String project);

	/**
	 * Find launches by filter load only id and number fields. Load specified
	 * quantity of launches.
	 *
	 * @param filter   Query filter
	 * @param sort     Sorting details
	 * @param quantity Results count
	 * @return List of launches
	 */
	List<Launch> findIdsByFilter(Filter filter, Sort sort, int quantity);

	/**
	 * Find launches by filter load only id field.
	 *
	 * @param filter Query filter
	 * @return List of launches
	 */
	List<Launch> findIdsByFilter(Filter filter);

	/**
	 * Find launches by filter only id field.
	 *
	 * @param filter Query filter
	 * @param limit  Results count
	 * @return List of launches
	 */
	List<Launch> findIdsByFilter(Filter filter, int limit);

	/**
	 * Get list of distinct values from MongoDB collection
	 *
	 * @param projectName   Project launch was be executed under
	 * @param containsValue Value to be searched
	 * @param distinctBy    Search field name
	 * @return List of launches
	 */
	List<String> findDistinctValues(String projectName, String containsValue, String distinctBy);

	/**
	 * Find launches by user ref
	 *
	 * @param userRef User launch was executed under
	 * @return List of launches
	 */
	List<Launch> findByUserRef(String userRef);

	/**
	 * Update user ref in launches
	 *
	 * @param oldOwner Old owner
	 * @param newOwner New owner
	 */
	void updateUserRef(String oldOwner, String newOwner);

	/**
	 * Get all unique fields for launches in specified mode
	 *
	 * @param projectName   Project launch was be executed under
	 * @param containsValue Value to be searched
	 * @param distinctBy    Search field name
	 * @return unique fields for launches in specified mode
	 */
	List<String> findValuesWithMode(String projectName, String containsValue, String distinctBy, String mode);

	/**
	 * Get grouped launches per owner. 'IN_PROGRESS' launches are excluded
	 *
	 * @param projectName Project launch was be executed under
	 * @param mode        Launch {@link com.epam.ta.reportportal.ws.model.launch.Mode}
	 * @return Map owner -> launches count
	 */
	Map<String, Integer> findGroupedLaunchesByOwner(String projectName, String mode, Date from);

	/**
	 * Find launches by specified projectId, mode and from date. 'IN_PROGRESS'
	 * launches are excluded
	 *
	 * @param projectId Project launch was be executed under
	 * @param mode      Launch Mode
	 * @param from      Start Date
	 * @return List of launches
	 */
	List<Launch> findLaunchesByProjectId(String projectId, Date from, String mode);

	/**
	 * Find launches quantity. 'IN_PROGRESS' launches are excluded
	 *
	 * @param projectId Project launch was be executed under
	 * @param mode      Launch Mode
	 * @param from      Start Date
	 * @return List of launches
	 */
	Long findLaunchesQuantity(String projectId, String mode, Date from);

	/**
	 * Find latest unique launches by filter
	 *
	 * @param filter   Query filter
	 * @param pageable Page Details
	 * @return List of launches
	 */

	Page<Launch> findLatestLaunches(Queryable filter, Pageable pageable);

	/**
	 * Load chart data according specified input parameters grouped by containing
	 * tag with regex. Result will be stored in {@link DocumentCallbackHandler} object
	 *
	 * @param filter          Filter
	 * @param contentFields   Content fields for creating results
	 * @param limit           Results limit
	 * @param tagPrefix       Tag prefix for grouping by
	 * @param callbackHandler Results handler
	 */
	void cumulativeStatisticsGroupedByTag(Queryable filter, List<String> contentFields, long limit, String tagPrefix,
			DocumentCallbackHandler callbackHandler);

	/**
	 * Load chart data according specified input parameters and only for latest launches.
	 * Result should be returned from {@link DocumentCallbackHandler} object
	 *
	 * @param filter          Query filter
	 * @param sort            Sorting details
	 * @param contentFields   Content Fields
	 * @param limit           Results limit
	 * @param callbackHandler Found results converter
	 */
	void findLatestWithCallback(Queryable filter, Sort sort, List<String> contentFields, long limit,
			DocumentCallbackHandler callbackHandler);

	/**
	 * Find last launch. 'IN_PROGRESS' launch is excluded
	 *
	 * @param projectId Project launch was be executed under
	 * @return Found launch or {@link Optional#empty()}
	 */
	Optional<Launch> findLastLaunch(String projectId, String mode);

	/**
	 * Find latest launch. 'IN_PROGRESS' launch is excluded
	 *
	 * @param projectName name
	 * @param launchName  launch
	 * @param mode        mode
	 * @return launch with latest number or {@link Optional#empty()}
	 */
	Optional<Launch> findLatestLaunch(String projectName, String launchName, String mode);

	/**
	 * Find last launch within specified project and mode
	 *
	 * @param launchName Name of launch
	 * @param projectId  Project launch was be executed under
	 * @return Found launch or {@link Optional#empty()}
	 */
	Optional<Launch> findLastLaunch(String projectId, String launchName, String mode);

	List<Launch> findByFilterWithSortingAndLimit(Filter filter, Sort sort, int limit);

	/**
	 * @param projectRef Project launch was be executed under
	 * @param type       Statistics type
	 * @return List of launches
	 */
	List<Launch> findLaunchesWithSpecificStat(String projectRef, StatisticSubType type);

	/**
	 * @param id   Launch ID
	 * @param type Statistics type
	 */
	void dropIssueStatisticsType(String id, StatisticSubType type);

	/**
	 * Finds launches by project names/ids
	 *
	 * @param ids Project IDs
	 * @return List of launch IDs
	 */
	List<String> findLaunchIdsByProjectIds(List<String> ids);

	/**
	 * Updates hasRetry field
	 *
	 * @param id         Launch ID
	 * @param hasRetries hasRetries field value
	 */
	void updateHasRetries(String id, boolean hasRetries);

	public Stream<Launch> streamLaunchesForJob(String project);
}