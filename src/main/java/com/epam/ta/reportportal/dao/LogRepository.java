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

import com.epam.ta.reportportal.entity.log.Log;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * @author Pavel Bortnik
 */
public interface LogRepository extends ReportPortalRepository<Log, Long>, LogRepositoryCustom {

	Optional<Log> findByUuid(String uuid);

	List<Log> findLogsByLogTime(Timestamp timestamp);

	@Modifying
	@Query(value = "UPDATE log SET launch_id = :newLaunchId WHERE launch_id = :currentLaunchId", nativeQuery = true)
	void updateLaunchIdByLaunchId(@Param("currentLaunchId") Long currentLaunchId, @Param("newLaunchId") Long newLaunchId);

	@Modifying
	@Query(value = "UPDATE log SET cluster_id = :clusterId WHERE id IN (:ids)", nativeQuery = true)
	int updateClusterIdByIdIn(@Param("clusterId") Long clusterId, @Param("ids") Collection<Long> ids);

	@Modifying
	@Query(value = "UPDATE log SET cluster_id = NULL WHERE cluster_id IN (SELECT id FROM clusters WHERE clusters.launch_id = :launchId)", nativeQuery = true)
	int updateClusterIdSetNullByLaunchId(@Param("launchId") Long launchId);
}
