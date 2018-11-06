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

import com.epam.ta.reportportal.entity.item.TestItem;
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

}
