/*
 *  Copyright (C) 2018 EPAM Systems
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.epam.ta.reportportal.entity.project;

import javax.persistence.Column;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Pavel Bortnik
 */
public class ProjectInfo implements Serializable {

	@Column(name = "id")
	private Long id;

	@Column(name = "creation_date")
	private LocalDateTime creationDate;

	@Column(name = "name")
	private String name;

	@Column(name = "project_type")
	private String projectType;

	@Column(name = "usersQuantity")
	private Integer usersQuantity;

	@Column(name = "launchesQuantity")
	private Integer launchesQuantity;

	@Column(name = "lastRun")
	private LocalDateTime lastRun;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProjectType() {
		return projectType;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}

	public Integer getUsersQuantity() {
		return usersQuantity;
	}

	public void setUsersQuantity(Integer usersQuantity) {
		this.usersQuantity = usersQuantity;
	}

	public Integer getLaunchesQuantity() {
		return launchesQuantity;
	}

	public void setLaunchesQuantity(Integer launchesQuantity) {
		this.launchesQuantity = launchesQuantity;
	}

	public LocalDateTime getLastRun() {
		return lastRun;
	}

	public void setLastRun(LocalDateTime lastRun) {
		this.lastRun = lastRun;
	}
}
