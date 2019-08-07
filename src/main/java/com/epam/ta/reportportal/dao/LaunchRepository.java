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

import com.epam.ta.reportportal.entity.enums.LaunchModeEnum;
import com.epam.ta.reportportal.entity.enums.StatusEnum;
import com.epam.ta.reportportal.entity.launch.Launch;
import com.epam.ta.reportportal.entity.project.Project;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author Pavel Bortnik
 */
public interface LaunchRepository extends ReportPortalRepository<Launch, Long>, LaunchRepositoryCustom {

	void deleteByProjectId(Long projectId);

	List<Launch> findAllByName(String name);

	Optional<Launch> findByUuid(String uuid);

	List<Launch> findByProjectIdAndStartTimeGreaterThanAndMode(Long projectId, LocalDateTime after, LaunchModeEnum mode);

	@Modifying
	@Query(value = "DELETE FROM Launch l WHERE l.projectId = :projectId AND l.lastModified < :before")
	int deleteLaunchesByProjectIdModifiedBefore(@Param("projectId") Long projectId, @Param("before") LocalDateTime before);

	@Query(value = "SELECT l.id FROM Launch l WHERE l.projectId = :projectId AND l.lastModified < :before")
	Stream<Long> streamIdsModifiedBefore(@Param("projectId") Long projectId, @Param("before") LocalDateTime before);

	@Query(value = "SELECT l.id FROM Launch l WHERE l.status = :status AND l.projectId = :projectId AND l.lastModified < :before")
	Stream<Long> streamIdsWithStatusModifiedBefore(@Param("projectId") Long projectId, @Param("status") StatusEnum status,
			@Param("before") LocalDateTime before);

	@Query(value = "SELECT * FROM launch l WHERE l.id <= :startingLaunchId AND l.name = :launchName "
			+ "AND l.project_id = :projectId ORDER BY id DESC LIMIT :historyDepth", nativeQuery = true)
	List<Launch> findLaunchesHistory(@Param("historyDepth") int historyDepth, @Param("startingLaunchId") Long startingLaunchId,
			@Param("launchName") String launchName, @Param("projectId") Long projectId);

	@Query(value = "SELECT merge_launch(?1)", nativeQuery = true)
	void mergeLaunchTestItems(Long launchId);

	/**
	 * Checks if a {@link Launch} has items with retries.
	 *
	 * @param launchId Current {@link Launch#id}
	 * @return True if has
	 */
	@Query(value = "SELECT exists(SELECT 1 FROM launch JOIN test_item ON launch.id = test_item.launch_id "
			+ "WHERE launch.id = :launchId AND test_item.has_retries LIMIT 1)", nativeQuery = true)
	boolean hasRetries(@Param("launchId") Long launchId);

	@Query(value = "SELECT exists(SELECT 1 FROM test_item ti JOIN test_item_results tir ON ti.item_id = tir.result_id "
			+ " WHERE ti.launch_id = :launchId AND tir.status <> cast(:#{#status.name()} as status_enum) LIMIT 1)", nativeQuery = true)
	boolean hasItemsWithStatusNotEqual(@Param("launchId") Long launchId, @Param("status") StatusEnum status);

	@Query(value = "SELECT exists(SELECT 1 FROM test_item ti JOIN test_item_results tir ON ti.item_id = tir.result_id "
			+ " WHERE ti.launch_id = :launchId AND tir.status = cast(:#{#status.name()} as status_enum) LIMIT 1)", nativeQuery = true)
	boolean hasItemsWithStatusEqual(@Param("launchId") Long launchId, @Param("status") StatusEnum status);

	@Query(value = "SELECT exists(SELECT 1 FROM test_item ti WHERE ti.launch_id = :launchId LIMIT 1)", nativeQuery = true)
	boolean hasItems(@Param("launchId") Long launchId);

	/**
	 * Finds the latest(that has max {@link Launch#number) {@link Launch} with specified {@code name} and {@code projectId}
	 *
	 * @param name      Name of {@link Launch}
	 * @param projectId Id of {@link Project}
	 * @return {@link Optional<Launch>} if exists, {@link Optional#empty()} if not
	 */
	@Query(value = "SELECT * FROM launch l WHERE l.name =:name AND l.project_id=:projectId ORDER BY l.number DESC LIMIT 1", nativeQuery = true)
	Optional<Launch> findLatestByNameAndProjectId(@Param("name") String name, @Param("projectId") Long projectId);

	/**
	 * Finds launch by {@link Launch#id} and sets a lock on the found launch row in the database.
	 * Required for fetching launch from the concurrent environment to provide synchronization between dependant entities
	 *
	 * @param id {@link Launch#id}
	 * @return {@link Optional} with {@link Launch} object
	 */
	@Lock(value = LockModeType.PESSIMISTIC_READ)
	@Query(value = "SELECT l FROM Launch l WHERE l.id = :id")
	Optional<Launch> findByIdForUpdate(@Param("id") Long id);
}
