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

import com.epam.ta.reportportal.commons.querygen.Filter;
import com.epam.ta.reportportal.entity.enums.StatusEnum;
import com.epam.ta.reportportal.entity.launch.Launch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author Pavel Bortnik
 */
public interface LaunchRepositoryCustom extends FilterableRepository<Launch> {

	Boolean identifyStatus(Long launchId);

	List<String> getLaunchNames(Long projectId, String value, String mode);

	List<String> getOwnerNames(Long projectId, String value, String mode);

	Map<String, String> getStatuses(Long projectId, Long[] ids);

	Optional<Launch> findLatestByNameAndFilter(String launchName, Filter filter);

	Page<Launch> findAllLatestByFilter(Filter filter, Pageable pageable);

	/**
	 * Finds the last valid launch in project
	 *
	 * @param projectId Project id
	 * @param mode      Launch mode
	 * @return {@link Optional} of {@link Launch}
	 */
	Optional<Launch> findLastRun(Long projectId, String mode);

	/**
	 * Counts launches with mode for specified project from provided date
	 *
	 * @param projectId Project id
	 * @param mode      Launch mode
	 * @param fromDate  From Date to count
	 * @return Launches count
	 */
	Integer countLaunches(Long projectId, String mode, LocalDateTime fromDate);

	/**
	 * Counts quantity of launches with mode per user for specified project.
	 *
	 * @param projectId Project id
	 * @param mode      Launch mode
	 * @param from      From Date to count
	 * @return Map of username -> launches count
	 */
	Map<String, Integer> countLaunchesGroupedByOwner(Long projectId, String mode, LocalDateTime from);

	Page<Long> getIdsInStatusModifiedBefore(Long projectId, Date before, Pageable pageable, StatusEnum... statuses);
}
