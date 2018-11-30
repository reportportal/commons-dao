/*
 * Copyright (C) 2018 EPAM Systems
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.epam.ta.reportportal.entity.widget.content;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import java.io.Serializable;
import java.sql.Timestamp;

import static com.epam.ta.reportportal.dao.constant.WidgetContentRepositoryConstants.*;

/**
 * @author Ivan Budayeu
 */
public class ActivityContent implements Serializable {

	@Column(name = ID)
	@JsonProperty(value = ID)
	private Long activityId;

	@Column(name = PROJECT_ID)
	@JsonProperty(value = "projectId")
	private Long projectId;

	@Column(name = ACTION_TYPE)
	@JsonProperty(value = "actionType")
	private String actionType;

	@Column(name = ENTITY)
	@JsonProperty(value = ENTITY)
	private String entity;

	@Column(name = PROJECT_NAME)
	@JsonProperty(value = "projectName")
	private String projectName;

	@Column(name = USER_LOGIN)
	@JsonProperty(value = "user")
	private String userLogin;

	@Column(name = LAST_MODIFIED)
	@JsonProperty(value = "lastModified")
	private Timestamp lastModified;

	public Long getActivityId() {
		return activityId;
	}

	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	public String getEntity() {
		return entity;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getUserLogin() {
		return userLogin;
	}

	public void setUserLogin(String userLogin) {
		this.userLogin = userLogin;
	}

	public Timestamp getLastModified() {
		return lastModified;
	}

	public void setLastModified(Timestamp lastModified) {
		this.lastModified = lastModified;
	}
}
