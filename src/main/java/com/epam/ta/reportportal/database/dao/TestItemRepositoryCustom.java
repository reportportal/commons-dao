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
import com.epam.ta.reportportal.database.entity.history.ItemHistoryObject;
import com.epam.ta.reportportal.database.entity.item.TestItem;
import com.epam.ta.reportportal.database.entity.item.TestItemType;
import com.epam.ta.reportportal.database.entity.item.issue.TestItemIssue;
import com.epam.ta.reportportal.database.entity.statistics.StatisticSubType;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface TestItemRepositoryCustom extends StatisticsUpdatePolicy<TestItem, Project.Configuration> {

	boolean hasDescendants(Object... id);

	List<TestItem> findDescendants(String... id);

	List<TestItem> findByLaunch(Launch launch);

	/**
	 * Finds testItems by specified launch IDs
	 *
	 * @param launches
	 * @return
	 */
	List<TestItem> findIdsByLaunch(Iterable<Launch> launches);

	/**
	 * Get names of path field of test item
	 *
	 * @param path
	 * @return
	 */
	Map<String, String> findPathNames(Iterable<String> path);

	/**
	 * Finds items modified later than provided period with provided status
	 * which is item of provided launch
	 *
	 * @param period
	 * @param status
	 * @param launch
	 * @param hasChild
	 * @return
	 */
	List<TestItem> findModifiedLaterAgo(Duration period, Status status, Launch launch, boolean hasChild);

	/**
	 * Finds items modified later than provided period with provided status
	 * which is item of provided launch
	 *
	 * @param period
	 * @param status
	 * @param launch
	 * @return
	 */
	List<TestItem> findModifiedLaterAgo(Duration period, Status status, Launch launch);

	/**
	 * Get list of distinct values from TestItems collection
	 *
	 * @param launchId      - parent launch id
	 * @param containsValue - part of string of searching value
	 * @param distinctBy    - field which should contain value
	 * @return
	 */
	List<String> findDistinctValues(String launchId, String containsValue, String distinctBy);

	/**
	 * Get list of unique tickets for specified list of launches
	 *
	 * @param launchesId
	 * @return
	 */
	List<String> getUniqueTicketsCount(List<Launch> launchesId);

	/**
	 * Get content of Most Failed Test Cases widget
	 *
	 * @param launchIds
	 * @param criteria
	 * @return
	 * @deprecated
	 */
	Map<String, String> getMostFailedTestCases(List<Launch> launchIds, String criteria);

	/**
	 * Get history of
	 *
	 * @param launchIds
	 * @param criteria
	 * @return
	 */
	List<? extends ItemHistoryObject> getMostFailedItemHistory(List<String> launchIds, String criteria);

	/**
	 * Get status history of potential flaky items in specified launches
	 *
	 * @param launchIds Launches to find
	 * @return List of status history entities
	 */
	List<? extends ItemHistoryObject> getFlakyItemStatusHistory(List<String> launchIds);

	/**
	 * Whether some of provided items has logs
	 *
	 * @param items
	 * @return
	 */
	boolean hasLogs(Iterable<TestItem> items);

	/**
	 * Load states of specified testItems in specified launches
	 *
	 * @param uniqueIds   Items unique ids
	 * @param launchesIds Launches ids
	 * @return Founded items
	 */
	List<TestItem> loadItemsHistory(List<String> uniqueIds, List<String> launchesIds);

	/**
	 * Whether launch contains any testItems added lately.
	 *
	 * @param period
	 * @param launch
	 * @param status
	 * @return
	 */
	boolean hasTestItemsAddedLately(final Duration period, Launch launch, Status status);

	/**
	 * Get test-items for specified launch with specified issue type.
	 *
	 * @param issueType
	 * @param launchId
	 * @return
	 */
	List<TestItem> findInIssueTypeItems(String issueType, String launchId);

	/**
	 * Get test-items ids for specified launches.
	 *
	 * @param launchRefs
	 * @return
	 */
	List<String> findItemIdsByLaunchRef(List<String> launchRefs);

	/**
	 * Get test-items for specified launch with specified test item type.
	 *
	 * @param launchId
	 * @param type
	 * @return
	 */
	List<TestItem> findItemsWithType(String launchId, TestItemType type);

	/**
	 * Get ids of test-items with specified name for specified launches.
	 *
	 * @param name
	 * @param launchRefs
	 * @return
	 */
	Set<String> findIdsWithNameByLaunchesRef(String name, Set<String> launchRefs);

	/**
	 * Get elements in launch branches specified by has_childs status.
	 *
	 * @param hasChildren
	 * @param launch
	 * @return
	 */
	List<TestItem> findByHasChildStatus(boolean hasChildren, String launch);

	/**
	 * Get all test items without descendants under specified launches with
	 * specified sub-types.
	 *
	 * @param launchesIds
	 * @param hasChild
	 * @param type
	 * @return
	 */
	List<TestItem> findForSpecifiedSubType(List<String> launchesIds, boolean hasChild, StatisticSubType type);

	/**
	 * Get test-items for specified launch with any issue type
	 *
	 * @param launchId
	 * @return
	 */
	List<TestItem> findTestItemWithIssues(String launchId);

	boolean hasChildrenWithStatuses(String itemId, Status... status);

	void dropIssueStatisticsType(String id, StatisticSubType type);

	/**
	 * Updates hasChilds value
	 *
	 * @param id        TestItem ID
	 * @param hasChilds hasChilds field value
	 */
	void updateHasChilds(String id, boolean hasChilds);

	/**
	 * Update only issue type for specified items without
	 * updating its' statistics
	 *
	 * @param forUpdate items for update
	 */
	void updateItemsIssues(Map<String, TestItemIssue> forUpdate);

	/**
	 * Get test items without parent with specified launch.
	 *
	 * @param launchId launch reference
	 * @return list of test items
	 */
	List<TestItem> findWithoutParentByLaunchRef(String launchId);
}