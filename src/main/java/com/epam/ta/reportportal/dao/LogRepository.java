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
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author Pavel Bortnik
 */
public interface LogRepository extends ReportPortalRepository<Log, Long>, LogRepositoryCustom {

	List<Log> findLogsByLogTime(Timestamp timestamp);

	List<Log> findAllByTestItemItemIdInAndLogLevelIsGreaterThanEqual(List<Long> testItemIds, Integer logLevel);

	@Query("SELECT lo.id FROM Log lo JOIN lo.testItem ti JOIN ti.launch la WHERE la.id =:launchId")
	List<Long> findLogIdsByLaunch(@Param("launchId") Long launchId);
}
