/*
 * Copyright 2016 EPAM Systems
 * 
 * 
 * This file is part of EPAM Report Portal.
 * https://github.com/epam/ReportPortal
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

import java.util.List;

import org.springframework.data.mongodb.repository.Query;

import com.epam.ta.reportportal.database.entity.item.TestItem;

/**
 * Repository interface for test items
 * 
 * @author Andrei Varabyeu
 * @author Andrei_Ramanchuk
 * 
 */
public interface TestItemRepository extends TestItemRepositoryCustom, ReportPortalRepository<TestItem, String> {

	String FIND_SUITE_ID_BY_ITEM_ID = "{ 'launchRef' : ?0 }";

	@Query(value = FIND_SUITE_ID_BY_ITEM_ID, fields = "{'id' : 1}")
	List<TestItem> findIdsByLaunch(String launch);

	@Query(value = " { 'parent': ?0 } ")
	List<TestItem> findAllDescendants(String parentId);

	@Query(value = " { 'status': ?0 , 'launchRef': ?1  } ")
	List<TestItem> findInStatusItems(String status, String launch);
}