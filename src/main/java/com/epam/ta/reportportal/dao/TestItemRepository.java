/*
 * Copyright 2018 EPAM Systems
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

import com.epam.ta.reportportal.entity.item.TestItem;
import com.epam.ta.reportportal.entity.launch.Launch;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.stream.Stream;

/**
 * @author Pavel Bortnik
 */
public interface TestItemRepository extends ReportPortalRepository<TestItem, Long>, TestItemRepositoryCustom {

	@Query(value = "SELECT ti.id FROM TestItem ti WHERE ti.launch.id = :launchId")
	Stream<Long> streamTestItemIdsByLaunchId(@Param("launchId") Long launchId);

	List<TestItem> findTestItemsByUniqueId(String uniqueId);

	List<TestItem> findTestItemsByLaunchId(Long launchId);

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
	 * and it has {@link TestItem#startTime} greater than MAX {@link TestItem#startTime} of other {@link TestItem}
	 * with the same {@link TestItem#uniqueId} then all those test items becomes retries of the new-inserted one:
	 * theirs {@link TestItem#hasRetries} flag sets to 'false' and {@link TestItem#retryOf} gets the new-inserted {@link TestItem#itemId} value.
	 * The same operation applies to the new-inserted {@link TestItem} if its {@link TestItem#startTime} is less than
	 * MAX {@link TestItem#startTime} of other {@link TestItem} with the same {@link TestItem#uniqueId}
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
	@Query(value = "SELECT EXISTS(SELECT 1 FROM test_item t WHERE t.path <@ cast(:itemPath AS LTREE) AND t.item_id != :itemId LIMIT 1)", nativeQuery = true)
	boolean hasChildren(@Param("itemId") Long itemId, @Param("itemPath") String itemPath);

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

	@Query(value = "SELECT * FROM test_item ti WHERE ti.unique_id IN :uniqueIds AND ti.launch_id IN :launchIds", nativeQuery = true)
	List<TestItem> loadItemsHistory(@Param("uniqueIds") List<String> uniqueIds, @Param("launchIds") List<Long> launchIds);

	/**
	 * Checks if all children of test item with id = {@code parentId}, except item with id = {@code stepId},
	 * has status not equal provided {@code status}
	 *
	 * @param parentId Id of parent test item
	 * @param stepId   Id of test item that should be ignored during the checking
	 * @param status   status {@link com.epam.ta.reportportal.entity.enums.StatusEnum}
	 * @return True if has
	 */
	@Query(value = "select exists(select from test_item " + "join test_item_results result on test_item.item_id = result.result_id "
			+ "where test_item.parent_id=:parentId and test_item.item_id!=:stepId and result.status!=cast(:status as status_enum))", nativeQuery = true)
	boolean hasStatusNotEqualsWithoutStepItem(@Param("parentId") Long parentId, @Param("stepId") Long stepId,
			@Param("status") String status);

}
