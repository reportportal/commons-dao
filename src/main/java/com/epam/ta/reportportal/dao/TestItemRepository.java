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
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.math.BigInteger;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.hibernate.jpa.QueryHints.HINT_FETCH_SIZE;

/**
 * @author Pavel Bortnik
 */
public interface TestItemRepository extends ReportPortalRepository<TestItem, Long>, TestItemRepositoryCustom {

	@QueryHints(value = @QueryHint(name = HINT_FETCH_SIZE, value = "1"))
	@Query(value = "SELECT ti.id FROM TestItem ti WHERE ti.launchId = :launchId")
	Stream<Long> streamTestItemIdsByLaunchId(@Param("launchId") Long launchId);

	@Query(value = "SELECT parent FROM TestItem child JOIN child.parent parent WHERE child.itemId = :childId")
	Optional<TestItem> findParentByChildId(@Param("childId") Long childId);

	/**
	 * Retrieve the {@link List} of the {@link TestItem#itemId} by launch ID, {@link StatusEnum#name()} and {@link TestItem#hasChildren} == false
	 *
	 * @param launchId {@link com.epam.ta.reportportal.entity.launch.Launch#id}
	 * @param status   {@link StatusEnum#name()}
	 * @return the {@link List} of the {@link TestItem#itemId}
	 */
	@QueryHints(value = @QueryHint(name = HINT_FETCH_SIZE, value = "1"))
	@Query(value = "SELECT test_item.item_id FROM test_item JOIN test_item_results result ON test_item.item_id = result.result_id "
			+ " WHERE test_item.launch_id = :launchId AND NOT test_item.has_children AND result.status = cast(:#{#status.name()} AS STATUS_ENUM)", nativeQuery = true)
	Stream<BigInteger> streamIdsByNotHasChildrenAndLaunchIdAndStatus(@Param("launchId") Long launchId, @Param("status") StatusEnum status);

	/**
	 * Retrieve the {@link List} of the {@link TestItem#itemId} by launch ID, {@link StatusEnum#name()} and {@link TestItem#hasChildren} == true
	 * ordered (DESCENDING) by 'nlevel' of the {@link TestItem#path}
	 *
	 * @param launchId {@link com.epam.ta.reportportal.entity.launch.Launch#id}
	 * @param status   {@link StatusEnum#name()}
	 * @return the {@link List} of the {@link TestItem#itemId}
	 * @see <a href="https://www.postgresql.org/docs/current/ltree.html">https://www.postgresql.org/docs/current/ltree.html</a>
	 */
	@QueryHints(value = @QueryHint(name = HINT_FETCH_SIZE, value = "1"))
	@Query(value = "SELECT test_item.item_id FROM test_item JOIN test_item_results result ON test_item.item_id = result.result_id "
			+ " WHERE test_item.launch_id = :launchId AND test_item.has_children AND result.status = cast(:#{#status.name()} AS STATUS_ENUM)"
			+ " ORDER BY nlevel(test_item.path) DESC", nativeQuery = true)
	Stream<BigInteger> streamIdsByHasChildrenAndLaunchIdAndStatusOrderedByPathLevel(@Param("launchId") Long launchId,
			@Param("status") StatusEnum status);

	/**
	 * Retrieve the {@link Stream} of the {@link TestItem#itemId} under parent {@link TestItem#path}, {@link StatusEnum#name()}
	 * and {@link TestItem#hasChildren} == false
	 *
	 * @param parentPath {@link TestItem#path} of the parent item
	 * @param status     {@link StatusEnum#name()}
	 * @return the {@link List} of the {@link TestItem#itemId}
	 */
	@QueryHints(value = @QueryHint(name = HINT_FETCH_SIZE, value = "1"))
	@Query(value = "SELECT test_item.item_id FROM test_item JOIN test_item_results result ON test_item.item_id = result.result_id "
			+ " WHERE cast(:parentPath AS LTREE) @> test_item.path AND cast(:parentPath AS LTREE) != test_item.path "
			+ " AND NOT test_item.has_children AND result.status = cast(:#{#status.name()} AS STATUS_ENUM)", nativeQuery = true)
	Stream<BigInteger> streamIdsByNotHasChildrenAndParentPathAndStatus(@Param("parentPath") String parentPath,
			@Param("status") StatusEnum status);

	/**
	 * Retrieve the {@link Stream} of the {@link TestItem#itemId} under parent {@link TestItem#path}, {@link StatusEnum#name()}
	 * and {@link TestItem#hasChildren} == true ordered (DESCENDING) by 'nlevel' of the {@link TestItem#path}
	 *
	 * @param parentPath {@link TestItem#path} of the parent item
	 * @param status     {@link StatusEnum#name()}
	 * @return the {@link List} of the {@link TestItem#itemId}
	 * @see <a href="https://www.postgresql.org/docs/current/ltree.html">https://www.postgresql.org/docs/current/ltree.html</a>
	 */
	@QueryHints(value = @QueryHint(name = HINT_FETCH_SIZE, value = "1"))
	@Query(value = "SELECT test_item.item_id FROM test_item JOIN test_item_results result ON test_item.item_id = result.result_id "
			+ " WHERE cast(:parentPath AS LTREE) @> test_item.path AND cast(:parentPath AS LTREE) != test_item.path "
			+ " AND test_item.has_children AND result.status = cast(:#{#status.name()} AS STATUS_ENUM)"
			+ " ORDER BY nlevel(test_item.path) DESC", nativeQuery = true)
	Stream<BigInteger> streamIdsByHasChildrenAndParentPathAndStatusOrderedByPathLevel(@Param("parentPath") String parentPath,
			@Param("status") StatusEnum status);

	List<TestItem> findTestItemsByUniqueId(String uniqueId);

	List<TestItem> findTestItemsByLaunchId(Long launchId);

	Optional<TestItem> findByUuid(String uuid);

	/**
	 * Finds {@link TestItem} by {@link TestItem#getUuid()} and sets a lock on the found 'item' row in the database.
	 * Required for fetching 'item' from the concurrent environment to provide synchronization between dependant entities
	 *
	 * @param uuid {@link TestItem#getUuid()}
	 * @return {@link Optional} with {@link TestItem} object
	 */
	@Query(value = "SELECT ti FROM TestItem ti WHERE ti.uuid = :uuid")
	@Lock(value = LockModeType.PESSIMISTIC_WRITE)
	Optional<TestItem> findByUuidForUpdate(@Param("uuid") String uuid);

	/**
	 * Finds all {@link TestItem} by specified launch id
	 *
	 * @param launchId {@link Launch#id}
	 * @return {@link List} of {@link TestItem} ordered by {@link TestItem#startTime} ascending
	 */
	List<TestItem> findTestItemsByLaunchIdOrderByStartTimeAsc(Long launchId);

	/**
	 * Execute sql-function that changes a structure of retries according to the MAX {@link TestItem#startTime}.
	 * If the new-inserted {@link TestItem} with specified {@link TestItem#itemId} is a retry
	 * and it has {@link TestItem#startTime} greater than MAX {@link TestItem#startTime} of the other {@link TestItem}
	 * with the same {@link TestItem#uniqueId} then all those test items become retries of the new-inserted one:
	 * theirs {@link TestItem#hasRetries} flag is set to 'false' and {@link TestItem#retryOf} gets the new-inserted {@link TestItem#itemId} value.
	 * The same operation applies to the new-inserted {@link TestItem} if its {@link TestItem#startTime} is less than
	 * MAX {@link TestItem#startTime} of the other {@link TestItem} with the same {@link TestItem#uniqueId}
	 *
	 * @param itemId The new-inserted {@link TestItem#itemId}
	 */
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
	@Query(value = "SELECT exists(SELECT 1 FROM test_item t WHERE t.path <@ cast(:itemPath AS LTREE) AND t.item_id != :itemId LIMIT 1)", nativeQuery = true)
	boolean hasChildren(@Param("itemId") Long itemId, @Param("itemPath") String itemPath);

	/**
	 * Checks does test item have parent with provided status.
	 *
	 * @param itemId   Cuttent item id
	 * @param itemPath Current item path in a tree
	 * @param status   {@link StatusEnum}
	 * @return 'True' if has, otherwise 'false'
	 */
	@Query(value = "SELECT exists(SELECT 1 FROM test_item ti JOIN test_item_results tir ON ti.item_id = tir.result_id"
			+ " WHERE ti.path @> cast(:itemPath AS LTREE) AND ti.has_stats = TRUE AND ti.item_id != :itemId AND tir.status = cast(:#{#status.name()} AS STATUS_ENUM) LIMIT 1)", nativeQuery = true)
	boolean hasParentWithStatus(@Param("itemId") Long itemId, @Param("itemPath") String itemPath, @Param("status") StatusEnum status);

	/**
	 * Check for existence of descendants with statuses NOT EQUAL to provided status
	 *
	 * @param parentId {@link TestItem#getParent()} ID
	 * @param statuses {@link StatusEnum#name()} Array
	 * @return 'true' if items with statuses NOT EQUAL to provided status exist, otherwise 'false'
	 */
	@Query(value = "SELECT exists(SELECT 1 FROM test_item ti JOIN test_item_results tir ON ti.item_id = tir.result_id"
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
	@Query(value = "SELECT exists(SELECT 1 FROM test_item ti JOIN test_item_results tir ON ti.item_id = tir.result_id"
			+ " WHERE ti.path <@ cast(:parentPath AS LTREE) AND ti.item_id != :parentId AND cast(tir.status AS VARCHAR) IN (:statuses))", nativeQuery = true)
	boolean hasItemsInStatusByParent(@Param("parentId") Long parentId, @Param("parentPath") String parentPath,
			@Param("statuses") String... statuses);

	/**
	 * True if the launch has any items with issue.
	 *
	 * @param launchId parent item {@link TestItem#getItemId()}
	 * @return True if contains, false if not
	 */
	@Query(value = "SELECT exists(SELECT 1 FROM test_item ti JOIN issue i ON ti.item_id = i.issue_type WHERE ti.launch_id = :launchId)", nativeQuery = true)
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
			"UPDATE test_item_results SET status = 'INTERRUPTED', end_time = current_timestamp, duration = EXTRACT(EPOCH FROM current_timestamp - i.start_time)"
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
	@Query(value = "SELECT exists(SELECT 1 FROM test_item JOIN test_item_results result ON test_item.item_id = result.result_id "
			+ " WHERE test_item.parent_id = :parentId AND test_item.item_id != :stepId AND test_item.retry_of IS NULL "
			+ " AND CAST(result.status AS VARCHAR) NOT IN (:statuses))", nativeQuery = true)
	boolean hasDescendantsNotInStatusExcludingById(@Param("parentId") Long parentId, @Param("stepId") Long stepId,
			@Param("statuses") String... statuses);

	/**
	 * Finds root(without any parent) {@link TestItem} with specified {@code name} and {@code launchId}
	 *
	 * @param name     Name of {@link TestItem}
	 * @param launchId ID of {@link Launch}
	 * @return {@link Optional} of {@link TestItem} if it exists, {@link Optional#empty()} if not
	 */
	@Query("SELECT t FROM TestItem t WHERE t.name=:name AND t.parent IS NULL AND t.launchId=:launchId")
	Optional<TestItem> findByNameAndLaunchWithoutParents(@Param("name") String name, @Param("launchId") Long launchId);

	/**
	 * Finds {@link TestItem} with specified {@code name} and {@code launchId} under {@code path}
	 *
	 * @param name     Name of {@link TestItem}
	 * @param launchId ID of {@link Launch}
	 * @param path     Path of {@link TestItem}
	 * @return {@link Optional} of {@link TestItem} if it exists, {@link Optional#empty()} if not
	 */
	@Query(value = "SELECT * FROM test_item t WHERE t.name=:name AND t.launch_id=:launchId AND t.path <@ cast(:path AS LTREE)", nativeQuery = true)
	Optional<TestItem> findByNameAndLaunchUnderPath(@Param("name") String name, @Param("launchId") Long launchId,
			@Param("path") String path);

	/**
	 * Finds all descendants ids of {@link TestItem} with {@code path} include its own id
	 *
	 * @param path Path of {@link TestItem}
	 * @return {@link List<Long>} of test item ids
	 */
	@Query(value = "SELECT item_id FROM test_item WHERE path <@ cast(:path AS LTREE)", nativeQuery = true)
	List<Long> selectAllDescendantsIds(@Param("path") String path);

	void deleteAllByItemIdIn(Collection<Long> ids);
}
