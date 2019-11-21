/*
 * Copyright 2019 EPAM Systems
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.commons.querygen.Queryable;
import com.epam.ta.reportportal.entity.enums.StatusEnum;
import com.epam.ta.reportportal.entity.enums.TestItemIssueGroup;
import com.epam.ta.reportportal.entity.enums.TestItemTypeEnum;
import com.epam.ta.reportportal.entity.item.NestedStep;
import com.epam.ta.reportportal.entity.item.PathName;
import com.epam.ta.reportportal.entity.item.TestItem;
import com.epam.ta.reportportal.entity.item.history.TestItemHistory;
import com.epam.ta.reportportal.entity.item.issue.IssueType;
import com.epam.ta.reportportal.jooq.enums.JStatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author Pavel Bortnik
 */
public interface TestItemRepositoryCustom extends FilterableRepository<TestItem> {

	/**
	 * Executes query built for given filters and maps result for given page
	 *
	 * @param isLatest         Flag for retrieving only latest launches
	 * @param launchFilter     {@link Queryable} with {@link com.epam.ta.reportportal.commons.querygen.FilterTarget#LAUNCH_TARGET}
	 * @param testItemFilter   {@link Queryable} with {@link com.epam.ta.reportportal.commons.querygen.FilterTarget#TEST_ITEM_TARGET}
	 * @param launchPageable   {@link Pageable} for {@link com.epam.ta.reportportal.entity.launch.Launch} query
	 * @param testItemPageable {@link Pageable} for {@link TestItem} query
	 * @return List of mapped entries found
	 */
	Page<TestItem> findByFilter(boolean isLatest, Queryable launchFilter, Queryable testItemFilter, Pageable launchPageable,
			Pageable testItemPageable);

	Page<TestItemHistory> loadItemsHistoryPage(Queryable filter, Pageable pageable, Long projectId, int historyDepth);

	Page<TestItemHistory> loadItemsHistoryPage(Queryable filter, Pageable pageable, Long projectId, String launchName, int historyDepth);

	Page<TestItemHistory> loadItemsHistoryPage(boolean isLatest, Queryable launchFilter, Queryable testItemFilter, Pageable launchPageable,
			Pageable testItemPageable, Long projectId, int historyDepth);

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
	 * True if the parent item has any child items with provided status.
	 *
	 * @param parentId   parent item {@link TestItem#itemId}
	 * @param parentPath parent item {@link TestItem#path}
	 * @param statuses   child item {@link com.epam.ta.reportportal.entity.item.TestItemResults#status}
	 * @return True if contains, false if not
	 */
	Boolean hasItemsInStatusByParent(Long parentId, String parentPath, StatusEnum... statuses);

	/**
	 * Select items that has different issue from provided for
	 * specified launch.
	 *
	 * @param launchId Launch
	 * @param locator  Issue type locator
	 * @return List of items
	 */
	List<TestItem> findAllNotInIssueByLaunch(Long launchId, String locator);

	/**
	 * Select items that has different issue from provided for
	 * specified launch.
	 *
	 * @param launchId Launch
	 * @param locator  Issue type locator
	 * @return List of items
	 */
	List<Long> selectIdsNotInIssueByLaunch(Long launchId, String locator);

	List<TestItem> findAllNotInIssueGroupByLaunch(Long launchId, TestItemIssueGroup issueGroup);

	List<Long> selectIdsNotInIssueGroupByLaunch(Long launchId, TestItemIssueGroup issueGroup);

	List<TestItem> findAllInIssueGroupByLaunch(Long launchId, TestItemIssueGroup issueGroup);

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
	 * True if {@link TestItem} wasn't modified before the provided 'period' and has logs
	 *
	 * @param launchId {@link com.epam.ta.reportportal.entity.launch.Launch#id}
	 * @param period   {@link Duration}
	 * @param statuses {@link StatusEnum}
	 * @return true if {@link TestItem} wasn't modified before the provided 'period' and has logs
	 */
	Boolean hasLogs(Long launchId, Duration period, StatusEnum... statuses);

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
	 * Check for existence of descendants with statuses NOT EQUAL to provided status
	 *
	 * @param parentId {@link TestItem#parent} ID
	 * @param status   {@link JStatusEnum}
	 * @return 'true' if items with statuses NOT EQUAL to provided status exist, otherwise 'false'
	 */
	boolean hasDescendantsWithStatusNotEqual(Long parentId, JStatusEnum status);

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

	/**
	 * Select {@link PathName} containing ids and names of all items in a tree till current and launch name and number
	 * for each item id from the provided collection
	 *
	 * @param ids {@link Collection} of {@link TestItem#getItemId()}
	 * @return id from collection -> {@link PathName}
	 */
	Map<Long, PathName> selectPathNames(Collection<Long> ids);

	/**
	 * Select item IDs by analyzed status and launch id with log level greater or equals than error
	 *
	 * @param autoAnalyzed {@link com.epam.ta.reportportal.ws.model.issue.Issue#autoAnalyzed}
	 * @param launchId     {@link TestItem#launchId} ID
	 * @param logLevel     {@link com.epam.ta.reportportal.entity.log.Log#logLevel}
	 * @return The {@link List} of the {@link TestItem#itemId}
	 */
	List<Long> selectIdsByAnalyzedWithLevelGte(boolean autoAnalyzed, Long launchId, int logLevel);

	/**
	 * @param itemId  {@link TestItem#itemId}
	 * @param status  New status
	 * @param endTime {@link com.epam.ta.reportportal.entity.item.TestItemResults#endTime}
	 * @return 1 if updated, otherwise 0
	 */
	int updateStatusAndEndTimeById(Long itemId, JStatusEnum status, LocalDateTime endTime);

	/**
	 * @param itemId {@link TestItem#itemId}
	 * @return {@link TestItemTypeEnum}
	 */
	TestItemTypeEnum getTypeByItemId(Long itemId);

	/**
	 * Select item IDs by launch ID and issue type ID with logs which level is greater than or equal to provided
	 * and message is matched by the STRING pattern
	 *
	 * @param filter   {@link Queryable}
	 * @param logLevel {@link com.epam.ta.reportportal.entity.log.Log#logLevel}
	 * @param pattern  CASE SENSITIVE STRING pattern for log message search
	 * @return The {@link List} of the {@link TestItem#itemId}
	 */
	List<Long> selectIdsByStringPatternMatchedLogMessage(Queryable filter, Integer logLevel, String pattern);

	/**
	 * Select item IDs by launch ID and issue type ID with logs which level is greater than or equal to provided
	 * and message is matched by the REGEX pattern
	 *
	 * @param filter   {@link Queryable}
	 * @param logLevel {@link com.epam.ta.reportportal.entity.log.Log#logLevel}
	 * @param pattern  REGEX pattern for log message search
	 * @return The {@link List} of the {@link TestItem#itemId}
	 */
	List<Long> selectIdsByRegexPatternMatchedLogMessage(Queryable filter, Integer logLevel, String pattern);

	/**
	 * Select {@link NestedStep} entities by provided 'IDs' with {@link NestedStep#attachmentsCount}
	 * of the {@link com.epam.ta.reportportal.entity.log.Log} entities of all descendants for each {@link NestedStep}
	 * and {@link NestedStep#hasContent} flag to check whether entity is a last one
	 * in the descendants tree or there are {@link com.epam.ta.reportportal.entity.log.Log} or {@link NestedStep} entities exist under it
	 *
	 * @param ids       {@link Collection} of the {@link TestItem#itemId}
	 * @param logFilter {@link Queryable} with {@link com.epam.ta.reportportal.entity.log.Log} target, to evaluate 'hasContent' flag
	 *                  and   attachments count
	 * @return {@link List} of the {@link NestedStep}
	 */

	List<NestedStep> findAllNestedStepsByIds(Collection<Long> ids, Queryable logFilter, boolean excludePassedLogs);
}
