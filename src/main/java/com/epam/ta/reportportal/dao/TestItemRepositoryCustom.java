/*
 *  Copyright (C) 2018 EPAM Systems
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.entity.enums.StatusEnum;
import com.epam.ta.reportportal.entity.item.TestItem;
import com.epam.ta.reportportal.entity.item.issue.IssueType;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author Pavel Bortnik
 */
public interface TestItemRepositoryCustom extends FilterableRepository<TestItem> {

	/**
	 * Selects all descendants of TestItem with provided id.
	 *
	 * @param itemId TestItem id
	 * @return List of all descendants
	 */
	List<TestItem> selectAllDescendants(Long itemId);

	/**
	 * Selects all descendants of TestItem with provided id, which has at least one child.
	 *
	 * @param itemId TestItem id
	 * @return List of descendants
	 */
	List<TestItem> selectAllDescendantsWithChildren(Long itemId);

	/**
	 * Select common items object that have provided status for
	 * specified launch.
	 *
	 * @param launchId Launch
	 * @param statuses Statuses
	 * @return List of items
	 */
	List<TestItem> selectItemsInStatusByLaunch(Long launchId, StatusEnum... statuses);

	/**
	 * Select common items object that have provided status for
	 * specified parent item.
	 *
	 * @param parentId Parent item
	 * @param statuses Statuses
	 * @return List of items
	 */
	List<TestItem> selectItemsInStatusByParent(Long parentId, StatusEnum... statuses);

	/**
	 * True if the provided launch contains any items with
	 * a specified status.
	 *
	 * @param launchId Checking launch id
	 * @param statuses Checking statuses
	 * @return True if contains, false if not
	 */
	Boolean hasItemsInStatusByLaunch(Long launchId, StatusEnum... statuses);

	/**
	 * True if the provided parent item contains any items with
	 * a specified status.
	 *
	 * @param parentId Checking launch id
	 * @param statuses Checking statuses
	 * @return True if contains, false if not
	 */
	Boolean hasItemsInStatusByParent(Long parentId, StatusEnum... statuses);

	/**
	 * Select ids of items that has different issue from provided for
	 * specified launch.
	 *
	 * @param launchId  Launch
	 * @param issueType Issue type locator
	 * @return List of item ids
	 */
	List<Long> selectIdsNotInIssueByLaunch(Long launchId, String issueType);

	/**
	 * True if the {@link com.epam.ta.reportportal.entity.item.TestItem} with matching 'status' and 'launchId'
	 * was started within the provided 'period'
	 *
	 * @param period   {@link Duration}
	 * @param launchId {@link com.epam.ta.reportportal.entity.launch.Launch#id}
	 * @param statuses {@link StatusEnum}
	 * @return true if items(the item) exist(exists)
	 */
	Boolean hasItemsInStatusAddedLately(Long launchId, Duration period, StatusEnum... statuses);

	/**
	 * Select test items that has issue with provided issue type for
	 * specified launch.
	 *
	 * @param launchId  Launch id
	 * @param issueType Issue type
	 * @return List of items
	 */
	List<TestItem> selectItemsInIssueByLaunch(Long launchId, String issueType);

	/**
	 * Identifies status of the provided item using it's children.
	 *
	 * @param testItemId Test Item
	 * @return Status of test item
	 */
	StatusEnum identifyStatus(Long testItemId);

	//TODO move to project repo
	List<IssueType> selectIssueLocatorsByProject(Long projectId);

	/**
	 * Selects issue type object by provided locator for specified project.
	 *
	 * @param projectId Project id
	 * @param locator   Issue type locator
	 * @return Issue type
	 */
	Optional<IssueType> selectIssueTypeByLocator(Long projectId, String locator);

	/**
	 * Select unsorted ids and names of all items in a tree till current.
	 *
	 * @param path itemPath
	 * @return id -> name
	 */
	Map<Long, String> selectPathNames(String path);
}
