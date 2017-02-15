/*
 * Copyright 2016 EPAM Systems
 * 
 * 
 * This file is part of EPAM Report Portal.
 * https://github.com/reportportal/commons-dao
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

package com.epam.ta.reportportal.database.dao;

import com.epam.ta.reportportal.database.entity.Launch;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Repository interface for {@link Launch} instances. Provides basic CRUD operations due to the
 * extension of {@link CrudRepository}
 *
 * @author Henadzi_Vrubleuski
 */
public interface LaunchRepository extends LaunchRepositoryCustom, ReportPortalRepository<Launch, String> {

	String FIND_BY_USER_PROJECT_QUERY = "{'user': ?0 , 'projectRef': ?1 }";

	String FIND_LAUNCH_ENTRY_BY_PROJECT_ID = "{ 'projectRef': ?0 }";

	String FIND_BY_ID = "{ '_id' : ?0 }";

	String NAME_NUMBER_MODE_FIELDS = "{'number' : 1, 'name' : 1, 'mode' : 1, 'start_time' : 1, 'status' : 1}";

	/**
	 * Find Launch by given name.
	 *
	 * @param name Launch name
	 * @return List of {@link Launch}
	 */
	List<Launch> findByName(String name);

	/**
	 * Finds launches IDs by provided project ID
	 *
	 * @param id ID of Project
	 * @return List of {@link Launch}
	 */
	@Query(value = FIND_LAUNCH_ENTRY_BY_PROJECT_ID, fields = "{'id' : 1}")
	List<Launch> findLaunchIdsByProjectId(String id);

	/**
	 * Finds Number and Mode of Launch by its ID
	 *
	 * @param id ID of Launch
	 * @return {@link Launch}
	 */
	@Query(value = FIND_BY_ID, fields = NAME_NUMBER_MODE_FIELDS)
	Launch findNameNumberAndModeById(String id);

	/**
	 * Find {@link Launch} by id, load only next fields:
	 * <li>id;
	 * <li>startTime;
	 * <li>status.
	 *
	 * @param id ID of Launch
	 * @return {@link Launch}
	 */
	@Query(value = "{ '_id': ?0 }", fields = "{'id' : 1, 'startTime':1, 'status':1, 'projectRef':1 }")
	Launch loadStatusProjectRefAndStartTime(String id);

	/**
	 * Deletes all launches of provided project
	 *
	 * @param projectRef Project Name
	 */
	void deleteByProjectRef(String projectRef);
}