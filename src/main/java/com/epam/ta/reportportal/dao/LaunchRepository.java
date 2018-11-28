/*
 * Copyright (C) 2018 EPAM Systems
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
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author Pavel Bortnik
 */
public interface LaunchRepository extends ReportPortalRepository<Launch, Long>, LaunchRepositoryCustom {

	void deleteByProjectId(Long projectId);

	List<Launch> findAllByName(String name);

	List<Launch> findByProjectIdAndStartTimeGreaterThanAndMode(Long projectId, LocalDateTime after, LaunchModeEnum mode);

	@Modifying
	@Query(value = "DELETE FROM Launch l WHERE l.projectId = :projectId AND l.lastModified < :before")
	int deleteLaunchesByProjectIdModifiedBefore(@Param("projectId") Long projectId, @Param("before") LocalDateTime before);

	@Query(value = "SELECT l.id FROM Launch l WHERE l.projectId = :projectId AND l.lastModified < :before")
	Stream<Long> streamIdsModifiedBefore(@Param("projectId") Long projectId, @Param("before") LocalDateTime before);

	@Query(value = "SELECT l.id FROM Launch l WHERE l.status = :status AND l.projectId = :projectId AND l.lastModified < :before")
	Stream<Long> streamIdsWithStatusModifiedBefore(@Param("projectId") Long projectId, @Param("status") StatusEnum status, @Param("before") LocalDateTime before);

	@Query(value = "SELECT * FROM launch l WHERE l.id <= :startingLaunchId AND l.name = :launchName "
			+ "AND l.project_id = :projectId ORDER BY id DESC LIMIT :historyDepth", nativeQuery = true)
	List<Launch> findLaunchesHistory(@Param("historyDepth") int historyDepth, @Param("startingLaunchId") Long startingLaunchId,
			@Param("launchName") String launchName, @Param("projectId") Long projectId);

	@Query(value = "SELECT merge_launch(?1)", nativeQuery = true)
	void mergeLaunchTestItems(Long launchId);

	@Query(value = "SELECT id FROM launch WHERE project_id=:projectId", nativeQuery = true)
	List<Long> findLaunchIdsByProjectId(@Param("projectId") Long projectId);

}
