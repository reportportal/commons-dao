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

package com.epam.ta.reportportal.database.entity;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.hateoas.Identifiable;

/**
 * @author Dzmitry_Kavalets
 */
@Document
public class UserPreference implements Serializable, Identifiable<String> {

	private static final long serialVersionUID = 5896663390864360204L;

	@Id
	private String id;

	private String projectRef;

	private String userRef;

	private LaunchTabs launchTabs;

	public void setId(String id) {
		this.id = id;
	}

	public String getProjectRef() {
		return projectRef;
	}

	public String getUserRef() {
		return userRef;
	}

	public void setUserRef(String userRef) {
		this.userRef = userRef;
	}

	public void setProjectRef(String projectRef) {
		this.projectRef = projectRef;
	}

	public LaunchTabs getLaunchTabs() {
		return launchTabs;
	}

	public void setLaunchTabs(LaunchTabs launchTabs) {
		this.launchTabs = launchTabs;
	}

	public static class LaunchTabs implements Serializable {

		private static final long serialVersionUID = -8820884642136353655L;
		private String active;
		private List<String> filters;

		public String getActive() {
			return active;
		}

		public void setActive(String active) {
			this.active = active;
		}

		public List<String> getFilters() {
			return filters;
		}

		public void setFilters(List<String> filters) {
			this.filters = filters;
		}
	}

	@Override
	public String getId() {
		return null;
	}
}