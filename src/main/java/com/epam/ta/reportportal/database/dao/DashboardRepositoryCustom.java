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

import org.springframework.data.domain.Sort;

import com.epam.ta.reportportal.database.entity.Dashboard;

public interface DashboardRepositoryCustom {

	/**
	 * Finds dashboards by user project, load with sorting all fields
	 * 
	 * @param sort
	 * @param userName
	 * @param projectName
	 * @returnList <Dashboard>
	 */
	List<Dashboard> findAll(String userName, Sort sort, String projectName);
}