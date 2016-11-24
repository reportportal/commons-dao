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

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.epam.ta.reportportal.database.entity.Log;

/**
 * Repository interface for {@link Log} instances. Provides basic CRUD operations due to the
 * extension of {@link CrudRepository}
 * 
 * @author Henadzi_Vrubleuski
 * 
 */
public interface LogRepository extends LogRepositoryCustom, ReportPortalRepository<Log, String> {

	String FIND_LOG_ENTRY_BY_STEP_ID = "{ 'testItemRef': ?0 }";

	/**
	 * Finds logs by given logTime
	 * 
	 * @param logTime
	 * @return
	 */
	List<Log> findByLogTime(Date logTime);

	/**
	 * Finds log by TestStep ID. Returns fully populated
	 * {@link com.epam.ta.reportportal.database.entity.Log}
	 * 
	 * @param testStep
	 * @return
	 */
	@Query(value = FIND_LOG_ENTRY_BY_STEP_ID, fields = "{'id' : 1}")
	List<Log> findLogIdsByTestItemId(String testItem);

	List<Log> findByTestItemRef(String testItem);

}