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

import com.epam.ta.reportportal.entity.enums.StatusEnum;
import com.epam.ta.reportportal.entity.item.TestItem;
import com.epam.ta.reportportal.entity.item.TestItemResults;
import com.epam.ta.reportportal.entity.launch.Launch;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author Pavel Bortnik
 */
public interface TestItemRepository extends ReportPortalRepository<TestItem, Long>, TestItemRepositoryCustom {

	@Query(value = "SELECT * FROM test_item WHERE item_id = (SELECT parent_id FROM test_item WHERE item_id = :childId)", nativeQuery = true)
	Optional<TestItem> findParentByChildId(@Param("childId") Long childId);

	/**
	 * Retrieve the {@link List} of the {@link TestItem#getItemId()} by launch ID, {@link StatusEnum#name()} and {@link TestItem#isHasChildren()} == false
	 *
	 * @param launchId {@link Launch#getId()}
	 * @param status   {@link StatusEnum#name()}
	 * @return the {@link List} of the {@link TestItem#getItemId()}
	 */
	@Query(value = "SELECT test_item.item_id FROM test_item JOIN test_item_results result ON test_item.item_id = result.result_id "
			+ " WHERE test_item.launch_id = :launchId AND NOT test_item.has_children "
			+ " AND result.status = CAST(:#{#status.name()} AS STATUS_ENUM) ORDER BY test_item.item_id LIMIT :pageSize OFFSET :pageOffset", nativeQuery = true)
	List<Long> findIdsByNotHasChildrenAndLaunchIdAndStatus(@Param("launchId") Long launchId, @Param("status") StatusEnum status,
			@Param("pageSize") Integer limit, @Param("pageOffset") Long offset);

	/**
	 * Retrieve the {@link List} of the {@link TestItem#getItemId()} by launch ID, {@link StatusEnum#name()} and {@link TestItem#isHasChildren()} == true
	 * ordered (DESCENDING) by 'nlevel' of the {@link TestItem#getPath()}
	 *
	 * @param launchId {@link Launch#getId()}
	 * @param status   {@link StatusEnum#name()}
	 * @return the {@link List} of the {@link TestItem#getItemId()}
	 * @see <a href="https://www.postgresql.org/docs/current/ltree.html">https://www.postgresql.org/docs/current/ltree.html</a>
	 */
	@Query(value = "SELECT test_item.item_id FROM test_item JOIN test_item_results result ON test_item.item_id = result.result_id "
			+ " WHERE test_item.launch_id = :launchId AND test_item.has_children AND result.status = CAST(:#{#status.name()} AS STATUS_ENUM)"
			+ " ORDER BY nlevel(test_item.path) DESC, test_item.item_id LIMIT :pageSize OFFSET :pageOffset", nativeQuery = true)
	List<Long> findIdsByHasChildrenAndLaunchIdAndStatusOrderedByPathLevel(@Param("launchId") Long launchId,
			@Param("status") StatusEnum status, @Param("pageSize") Integer limit, @Param("pageOffset") Long offset);

	/**
	 * Retrieve the {@link Stream} of the {@link TestItem#getItemId()} under parent {@link TestItem#getPath()}, {@link StatusEnum#name()}
	 * and {@link TestItem#isHasChildren()} == false
	 *
	 * @param parentPath {@link TestItem#getPath()} of the parent item
	 * @param status     {@link StatusEnum#name()}
	 * @return the {@link List} of the {@link TestItem#getItemId()}
	 */
	@Query(value = "SELECT test_item.item_id FROM test_item JOIN test_item_results result ON test_item.item_id = result.result_id "
			+ " WHERE CAST(:parentPath AS LTREE) @> test_item.path AND CAST(:parentPath AS LTREE) != test_item.path "
			+ " AND NOT test_item.has_children AND result.status = CAST(:#{#status.name()} AS STATUS_ENUM) ORDER BY test_item.item_id LIMIT :pageSize OFFSET :pageOffset", nativeQuery = true)
	List<Long> findIdsByNotHasChildrenAndParentPathAndStatus(@Param("parentPath") String parentPath, @Param("status") StatusEnum status,
			@Param("pageSize") Integer limit, @Param("pageOffset") Long offset);

	/**
	 * Retrieve the {@link Stream} of the {@link TestItem#getItemId()} under parent {@link TestItem#getPath()}, {@link StatusEnum#name()}
	 * and {@link TestItem#isHasChildren()} == true ordered (DESCENDING) by 'nlevel' of the {@link TestItem#getPath()}
	 *
	 * @param parentPath {@link TestItem#getPath()} of the parent item
	 * @param status     {@link StatusEnum#name()}
	 * @return the {@link List} of the {@link TestItem#getItemId()}
	 * @see <a href="https://www.postgresql.org/docs/current/ltree.html">https://www.postgresql.org/docs/current/ltree.html</a>
	 */
	@Query(value = "SELECT test_item.item_id FROM test_item JOIN test_item_results result ON test_item.item_id = result.result_id "
			+ " WHERE CAST(:parentPath AS LTREE) @> test_item.path AND CAST(:parentPath AS LTREE) != test_item.path "
			+ " AND test_item.has_children AND result.status = CAST(:#{#status.name()} AS STATUS_ENUM)"
			+ " ORDER BY nlevel(test_item.path) DESC, test_item.item_id LIMIT :pageSize OFFSET :pageOffset", nativeQuery = true)
	List<Long> findIdsByHasChildrenAndParentPathAndStatusOrderedByPathLevel(@Param("parentPath") String parentPath,
			@Param("status") StatusEnum status, @Param("pageSize") Integer limit, @Param("pageOffset") Long offset);

	List<TestItem> findTestItemsByUniqueId(String uniqueId);

	List<TestItem> findTestItemsByLaunchId(Long launchId);

	Optional<TestItem> findByUuid(String uuid);

	/**
	 * Finds {@link TestItem#getItemId()} by {@link TestItem#getUuid()} and sets a lock on the found 'item' row in the database.
	 * Required for fetching 'item' from the concurrent environment to provide synchronization between dependant entities
	 *
	 * @param uuid {@link TestItem#getUuid()}
	 * @return {@link Optional} with {@link TestItem} object
	 */
	@Query(value = "SELECT ti.item_id FROM test_item ti WHERE ti.uuid = :uuid FOR UPDATE", nativeQuery = true)
	Optional<Long> findIdByUuidForUpdate(@Param("uuid") String uuid);

	/**
	 * Finds all {@link TestItem} by specified launch id
	 *
	 * @param launchId {@link Launch#getId()}
	 * @return {@link List} of {@link TestItem} ordered by {@link TestItem#getStartTime()} ascending
	 */
	List<TestItem> findTestItemsByLaunchIdOrderByStartTimeAsc(Long launchId);

	/**
	 * Execute sql-function that changes a structure of retries according to the MAX {@link TestItem#getStartTime()}.
	 * If the new-inserted {@link TestItem} with specified {@link TestItem#getItemId()} is a retry
	 * and it has {@link TestItem#getStartTime()} greater than MAX {@link TestItem#getStartTime()} of the other {@link TestItem}
	 * with the same {@link TestItem#getUniqueId()} then all those test items become retries of the new-inserted one:
	 * theirs {@link TestItem#isHasRetries()} flag is set to 'false' and {@link TestItem#getRetryOf()} gets the new-inserted {@link TestItem#getItemId()} value.
	 * The same operation applies to the new-inserted {@link TestItem} if its {@link TestItem#getStartTime()} is less than
	 * MAX {@link TestItem#getStartTime()} of the other {@link TestItem} with the same {@link TestItem#getUniqueId()}
	 *
	 * @param itemId The new-inserted {@link TestItem#getItemId()}
	 * @deprecated {@link TestItemRepository#handleRetry(Long, Long)} should be used instead
	 */
	@Deprecated
	@Query(value = "SELECT handle_retries(:itemId)", nativeQuery = true)
	void handleRetries(@Param("itemId") Long itemId);

	/**
	 * Execute sql-function that changes a structure of retries assigning {@link TestItem#getRetryOf()} value
	 * of the previously inserted retries and previous retries' parent to the new inserted parent id
	 *
	 * @param itemId      Previous retries' parent {@link TestItem#getItemId()}
	 * @param retryParent The new-inserted {@link TestItem#getItemId()}
	 */
	@Query(value = "SELECT handle_retry(:itemId, :retryParent)", nativeQuery = true)
	void handleRetry(@Param("itemId") Long itemId, @Param("retryParent") Long retryParent);

	@Query(value = "DELETE FROM test_item WHERE test_item.item_id = :itemId", nativeQuery = true)
	void deleteTestItem(@Param(value = "itemId") Long itemId);

	/**
	 * Checks does test item have children.
	 *
	 * @param itemPath Current item path in a tree
	 * @return True if has
	 */
	@Query(value = "SELECT EXISTS(SELECT 1 FROM test_item t WHERE t.path <@ CAST(:itemPath AS LTREE) AND t.item_id != :itemId LIMIT 1)", nativeQuery = true)
	boolean hasChildren(@Param("itemId") Long itemId, @Param("itemPath") String itemPath);

	/**
	 * Checks does test item have children with {@link TestItem#isHasStats()} == true.
	 *
	 * @param itemId Parent item id
	 * @return True if has
	 */
	@Query(value = "SELECT exists(SELECT 1 FROM test_item t WHERE t.parent_id = :itemId AND t.has_stats)", nativeQuery = true)
	boolean hasChildrenWithStats(@Param("itemId") Long itemId);

	/**
	 * Checks does test item have parent with provided status.
	 *
	 * @param itemId   Cuttent item id
	 * @param itemPath Current item path in a tree
	 * @param status   {@link StatusEnum}
	 * @return 'True' if has, otherwise 'false'
	 */
	@Query(value = "SELECT EXISTS(SELECT 1 FROM test_item ti JOIN test_item_results tir ON ti.item_id = tir.result_id"
			+ " WHERE ti.path @> CAST(:itemPath AS LTREE) AND ti.has_stats = TRUE AND ti.item_id != :itemId AND tir.status = CAST(:#{#status.name()} AS STATUS_ENUM) LIMIT 1)", nativeQuery = true)
	boolean hasParentWithStatus(@Param("itemId") Long itemId, @Param("itemPath") String itemPath, @Param("status") StatusEnum status);

	/**
	 * Check for existence of descendants with statuses NOT EQUAL to provided status
	 *
	 * @param parentId {@link TestItem#getParent()} ID
	 * @param statuses {@link StatusEnum#name()} Array
	 * @return 'true' if items with statuses NOT EQUAL to provided status exist, otherwise 'false'
	 */
	@Query(value = "SELECT EXISTS(SELECT 1 FROM test_item ti JOIN test_item_results tir ON ti.item_id = tir.result_id"
			+ " WHERE ti.parent_id = :parentId AND ti.retry_of IS NULL AND CAST(tir.status AS VARCHAR) NOT IN (:statuses))", nativeQuery = true)
	boolean hasDescendantsNotInStatus(@Param("parentId") Long parentId, @Param("statuses") String... statuses);

	/**
	 * True if the parent item has any child items with provided status.
	 *
	 * @param parentId   parent item {@link TestItem#getItemId()}
	 * @param parentPath parent item {@link TestItem#getPath()}
	 * @param statuses   child item {@link TestItemResults#getStatus()}
	 * @return True if contains, false if not
	 */
	@Query(value = "SELECT EXISTS(SELECT 1 FROM test_item ti JOIN test_item_results tir ON ti.item_id = tir.result_id"
			+ " WHERE ti.path <@ CAST(:parentPath AS LTREE) AND ti.item_id != :parentId AND CAST(tir.status AS VARCHAR) IN (:statuses))", nativeQuery = true)
	boolean hasItemsInStatusByParent(@Param("parentId") Long parentId, @Param("parentPath") String parentPath,
			@Param("statuses") String... statuses);

	/**
	 * True if the launch has any items with issue.
	 *
	 * @param launchId parent item {@link TestItem#getItemId()}
	 * @return True if contains, false if not
	 */
	@Query(value = "SELECT EXISTS(SELECT 1 FROM test_item ti JOIN issue i ON ti.item_id = i.issue_id WHERE ti.launch_id = :launchId)", nativeQuery = true)
	boolean hasItemsWithIssueByLaunch(@Param("launchId") Long launchId);

	/**
	 * Interrupts all {@link com.epam.ta.reportportal.entity.enums.StatusEnum#IN_PROGRESS} children items of the
	 * launch with provided launchId.
	 * Sets them {@link com.epam.ta.reportportal.entity.enums.StatusEnum#INTERRUPTED} status
	 *
	 * @param launchId Launch id
	 */
	@Modifying
	@Query(value =
			"UPDATE test_item_results SET status = 'INTERRUPTED', end_time = CURRENT_TIMESTAMP, duration = EXTRACT(EPOCH FROM CURRENT_TIMESTAMP - i.start_time)"
					+ "FROM test_item i WHERE i.item_id = result_id AND i.launch_id = :launchId AND status = 'IN_PROGRESS'", nativeQuery = true)
	void interruptInProgressItems(@Param("launchId") Long launchId);

	/**
	 * Checks if all children of test item with id = {@code parentId}, except item with id = {@code stepId},
	 * has status not in provided {@code statuses}
	 *
	 * @param parentId Id of parent test item
	 * @param stepId   Id of test item that should be ignored during the checking
	 * @param statuses {@link StatusEnum#name()} Array
	 * @return True if has
	 */
	@Query(value = "SELECT EXISTS(SELECT 1 FROM test_item JOIN test_item_results result ON test_item.item_id = result.result_id "
			+ " WHERE test_item.parent_id = :parentId AND test_item.item_id != :stepId AND test_item.retry_of IS NULL "
			+ " AND CAST(result.status AS VARCHAR) NOT IN (:statuses))", nativeQuery = true)
	boolean hasDescendantsNotInStatusExcludingById(@Param("parentId") Long parentId, @Param("stepId") Long stepId,
			@Param("statuses") String... statuses);

	/**
	 * Finds {@link TestItem} with specified {@code path}
	 *
	 * @param path Path of {@link TestItem}
	 * @return {@link Optional} of {@link TestItem} if it exists, {@link Optional#empty()} if not
	 */
	@Query(value = "SELECT * FROM test_item t WHERE t.path = CAST(:path AS LTREE)", nativeQuery = true)
	Optional<TestItem> findByPath(@Param("path") String path);

	/**
	 * Finds latest {@link TestItem#getItemId()} with specified {@code uniqueId}, {@code launchId}, {@code parentId}
	 *
	 * @param uniqueId {@link TestItem#getUniqueId()}
	 * @param launchId {@link TestItem#getLaunchId()}
	 * @param parentId {@link TestItem#getParentId()}
	 * @return {@link Optional} of {@link TestItem} if exists otherwise {@link Optional#empty()}
	 */
	@Query(value = "SELECT t.item_id FROM test_item t WHERE t.unique_id = :uniqueId AND t.launch_id = :launchId "
			+ " AND t.parent_id = :parentId AND t.has_stats AND t.retry_of IS NULL"
			+ " ORDER BY t.start_time DESC, t.item_id DESC LIMIT 1 FOR UPDATE", nativeQuery = true)
	Optional<Long> findLatestIdByUniqueIdAndLaunchIdAndParentId(@Param("uniqueId") String uniqueId, @Param("launchId") Long launchId,
			@Param("parentId") Long parentId);

	/**
	 * Finds latest {@link TestItem#getItemId()} with specified {@code uniqueId}, {@code launchId}, {@code parentId} and not equal {@code itemId}
	 *
	 * @param uniqueId {@link TestItem#getUniqueId()}
	 * @param launchId {@link TestItem#getLaunchId()}
	 * @param parentId {@link TestItem#getParentId()}
	 * @param itemId   {@link TestItem#getItemId()} ()}
	 * @return {@link Optional} of {@link TestItem} if exists otherwise {@link Optional#empty()}
	 */
	@Query(value = "SELECT t.item_id FROM test_item t WHERE t.unique_id = :uniqueId AND t.launch_id = :launchId "
			+ " AND t.parent_id = :parentId AND t.item_id != :itemId AND t.has_stats AND t.retry_of IS NULL"
			+ " ORDER BY t.start_time DESC, t.item_id DESC LIMIT 1 FOR UPDATE", nativeQuery = true)
	Optional<Long> findLatestIdByUniqueIdAndLaunchIdAndParentIdAndItemIdNotEqual(@Param("uniqueId") String uniqueId,
			@Param("launchId") Long launchId, @Param("parentId") Long parentId, @Param("itemId") Long itemId);

	/**
	 * Finds all descendants ids of {@link TestItem} with {@code path} include its own id
	 *
	 * @param path Path of {@link TestItem}
	 * @return {@link List<Long>} of test item ids
	 */
	@Query(value = "SELECT item_id FROM test_item WHERE path <@ CAST(:path AS LTREE)", nativeQuery = true)
	List<Long> selectAllDescendantsIds(@Param("path") String path);

	void deleteAllByItemIdIn(Collection<Long> ids);

	/**
	 * Finds latest root(without any parent) {@link TestItem} with specified {@code testCaseHash} and {@code launchId}
	 *
	 * @param testCaseHash {@link TestItem#getTestCaseHash()}
	 * @param launchId     {@link TestItem#getLaunchId()}
	 * @return {@link Optional} of {@link TestItem#getItemId()} if exists otherwise {@link Optional#empty()}
	 */
	@Query(value =
			"SELECT t.item_id FROM test_item t WHERE t.test_case_hash = :testCaseHash AND t.launch_id = :launchId AND t.parent_id IS NULL "
					+ " ORDER BY t.start_time DESC, t.item_id DESC LIMIT 1 FOR UPDATE", nativeQuery = true)
	Optional<Long> findLatestIdByTestCaseHashAndLaunchIdWithoutParents(@Param("testCaseHash") Integer testCaseHash,
			@Param("launchId") Long launchId);

	/**
	 * Finds latest {@link TestItem#getItemId()} with specified {@code testCaseHash}, {@code launchId} and {@code parentId}
	 *
	 * @param testCaseHash {@link TestItem#getTestCaseHash()}
	 * @param launchId     {@link TestItem#getLaunchId()}
	 * @param parentId     {@link TestItem#getParentId()}
	 * @return {@link Optional} of {@link TestItem#getItemId()} if exists otherwise {@link Optional#empty()}
	 */
	@Query(value = "SELECT t.item_id FROM test_item t WHERE t.test_case_hash = :testCaseHash AND t.launch_id = :launchId "
			+ " AND t.parent_id = :parentId AND t.has_stats AND t.retry_of IS NULL"
			+ " ORDER BY t.start_time DESC, t.item_id DESC LIMIT 1 FOR UPDATE", nativeQuery = true)
	Optional<Long> findLatestIdByTestCaseHashAndLaunchIdAndParentId(@Param("testCaseHash") Integer testCaseHash,
			@Param("launchId") Long launchId, @Param("parentId") Long parentId);
}
