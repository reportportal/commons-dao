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
import com.epam.ta.reportportal.entity.launch.Launch;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import javax.persistence.QueryHint;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.hibernate.jpa.QueryHints.HINT_FETCH_SIZE;

/**
 * @author Pavel Bortnik
 */
public interface TestItemRepository extends ReportPortalRepository<TestItem, Long>, TestItemRepositoryCustom {

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
	 * Finds all {@link TestItem} by specified launch id
	 *
	 * @param launchId {@link Launch#id}
	 * @return {@link List<TestItem>} ordered by {@link TestItem#startTime} ascending
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

	@Query(value = "SELECT testitem.item_id, testitemresult.result_id, issue.issue_id, issuetype.id, issuegroup.issue_group_id, "
			+ "testitem.code_ref, testitem.description, testitem.has_children, testitem.has_retries, testitem.has_stats, "
			+ "testitem.last_modified, testitem.launch_id, testitem.name, testitem.parent_id, testitem.path, testitem.retry_of, "
			+ "testitem.start_time, testitem.type, testitem.unique_id, testitem.uuid, testitemresult.duration, testitemresult.end_time, "
			+ "testitemresult.status, issue.auto_analyzed, issue.ignore_analyzer, issue.issue_description, issue.issue_type, issuetype.hex_color, "
			+ "issuetype.issue_group_id, issuetype.locator, issuetype.issue_name, issuetype.abbreviation, issuegroup.issue_group "
			+ "FROM test_item testitem LEFT OUTER JOIN test_item_results testitemresult ON testitem.item_id=testitemresult.result_id "
			+ "LEFT OUTER JOIN issue issue ON testitemresult.result_id=issue.issue_id LEFT OUTER JOIN issue_type issuetype ON "
			+ "issue.issue_type=issuetype.id LEFT OUTER JOIN issue_group issuegroup ON issuetype.issue_group_id=issuegroup.issue_group_id "
			+ "WHERE (testitem.unique_id IN (:uniqueIds)) AND (testitem.launch_id IN (:launchIds)) LIMIT :limit", nativeQuery = true)
	List<TestItem> loadItemsHistory(@Param("uniqueIds") List<String> uniqueIds, @Param("launchIds") List<Long> launchIds,
			@Param("limit") int limit);

	/**
	 * Checks if all children of test item with id = {@code parentId}, except item with id = {@code stepId},
	 * has status not equal provided {@code status}
	 *
	 * @param parentId Id of parent test item
	 * @param stepId   Id of test item that should be ignored during the checking
	 * @param status   status {@link com.epam.ta.reportportal.entity.enums.StatusEnum}
	 * @return True if has
	 */
	@Query(value = "SELECT exists(SELECT 1 FROM test_item " + "JOIN test_item_results result ON test_item.item_id = result.result_id "
			+ "WHERE test_item.parent_id=:parentId AND test_item.item_id!=:stepId AND result.status!=cast(:#{#status.name()} AS STATUS_ENUM) LIMIT 1)", nativeQuery = true)
	boolean hasStatusNotEqualsWithoutStepItem(@Param("parentId") Long parentId, @Param("stepId") Long stepId,
			@Param("status") StatusEnum status);

	@Query(value = "SELECT 1 FROM test_item item JOIN test_item_results result ON item.item_id = result.result_id WHERE item.parent_id <> :parentId AND result.status <> cast(:#{#status.name()} AS STATUS_ENUM) AND item.path <@ cast(:path AS LTREE)", nativeQuery = true)
	boolean hasStatusNotEqualByParent(@Param("parentId") Long parentId, @Param("path") String path, @Param("status") StatusEnum status);

	/**
	 * Finds root(without any parent) {@link TestItem} with specified {@code name} and {@code launchId}
	 *
	 * @param name     Name of {@link TestItem}
	 * @param launchId ID of {@link Launch}
	 * @return {@link Optional<TestItem>} if it exists, {@link Optional#empty()} if not
	 */
	@Query("SELECT t FROM TestItem t WHERE t.name=:name AND t.parent IS NULL AND t.launchId=:launchId")
	Optional<TestItem> findByNameAndLaunchWithoutParents(@Param("name") String name, @Param("launchId") Long launchId);

	/**
	 * Finds {@link TestItem} with specified {@code name} and {@code launchId} under {@code path}
	 *
	 * @param name     Name of {@link TestItem}
	 * @param launchId ID of {@link Launch}
	 * @param path     Path of {@link TestItem}
	 * @return {@link Optional<TestItem>} if it exists, {@link Optional#empty()} if not
	 */
	@Query(value = "SELECT * FROM test_item t WHERE t.name=:name AND t.launch_id=:launchId AND t.path <@ cast(:path AS LTREE)", nativeQuery = true)
	Optional<TestItem> findByNameAndLaunchUnderPath(@Param("name") String name, @Param("launchId") Long launchId,
			@Param("path") String path);
}
