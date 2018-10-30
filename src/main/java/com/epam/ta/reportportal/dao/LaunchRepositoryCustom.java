/*
 * Copyright 2017 EPAM Systems
 *
 *
 * This file is part of EPAM Report Portal.
 * https://github.com/reportportal/service-api
 *
 * Report Portal is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Report Portal is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Report Portal.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.commons.querygen.Filter;
import com.epam.ta.reportportal.entity.launch.Launch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
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
}
