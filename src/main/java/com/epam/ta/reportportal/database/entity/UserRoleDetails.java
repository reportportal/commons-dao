package com.epam.ta.reportportal.database.entity;
/*
 * Copyright 2017 EPAM Systems
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

import com.epam.ta.reportportal.database.entity.user.User;
import com.google.common.base.MoreObjects;

import java.io.Serializable;
import java.util.Map;

/**
 * Just a wrapper for User and his Project roles
 *
 * @author Andrei Varabyeu
 */
public class UserRoleDetails implements Serializable {

	private User user;
	private Map<String, ProjectRole> projects;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Map<String, ProjectRole> getProjects() {
		return projects;
	}

	public void setProjects(Map<String, ProjectRole> projects) {
		this.projects = projects;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("user", user).add("projects", projects).toString();
	}

}
