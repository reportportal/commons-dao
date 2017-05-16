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

import com.epam.ta.reportportal.database.entity.item.FailReferenceResource;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Repository interface for {@link FailReferenceResource} instances. Provides basic CRUD operations
 * due to the extension of {@link CrudRepository}
 * 
 * @author Andrei_Ramanchuk
 *
 */
public interface FailReferenceResourceRepository extends ReportPortalRepository<FailReferenceResource, String> {

	String FIND_BY_LAUNCH_REF = "{'launchRef' : ?0 }";

	@Query(FIND_BY_LAUNCH_REF)
	List<FailReferenceResource> findAllLaunchIssues(String launchId);

	@Query(value = FIND_BY_LAUNCH_REF, delete = true)
	void deleteAllIssuesForLaunch(String launchId);
}