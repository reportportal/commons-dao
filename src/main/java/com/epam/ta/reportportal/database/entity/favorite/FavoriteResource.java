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
 
package com.epam.ta.reportportal.database.entity.favorite;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.epam.ta.reportportal.database.search.FilterCriteria;

/**
 * Favorite resource DAO layer object. Container for shared resources which user
 * selected as favorite.
 * 
 * @author Aliaksei_Makayed
 * 
 */

@Document
//TODO add correct indexing
public class FavoriteResource {

	public static final String TYPE_CRITERIA = "resourceType";
	public static final String RESOURCE_ID_CRITERIA = "resourceId";
	public static final String USERNAME_CRITERIA = "userName";
	public static final String PROJECT_CRITERIA = "projectName";
		
	@Id
	public String id;
	
	@FilterCriteria(TYPE_CRITERIA)
	public String resourceType;
	
	@FilterCriteria(RESOURCE_ID_CRITERIA)
	@Indexed(background = true)
	public String resourceId;
	
	@FilterCriteria(USERNAME_CRITERIA)
	public String userName;
	
	// project name required because user can be assigned to few projects
	// and can have favorite resources in each project
	@FilterCriteria(PROJECT_CRITERIA)
	public String projectName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getResourceType() {
		return resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	@Override
	public String toString() {
		return "FavoriteResource{" +
				"id='" + id + '\'' +
				", resourceType='" + resourceType + '\'' +
				", resourceId='" + resourceId + '\'' +
				", userName='" + userName + '\'' +
				", projectName='" + projectName + '\'' +
				'}';
	}
}