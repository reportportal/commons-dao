package com.epam.ta.reportportal.entity.widget.content;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author Ivan Budayeu
 */
public class ActivityContent implements Serializable {

	@JsonProperty(value = "id")
	private Long activityId;

	@JsonProperty(value = "project_id")
	private Long projectId;

	@JsonProperty(value = "action_type")
	private String actionType;

	@JsonProperty(value = "entity")
	private String entity;

	@JsonProperty(value = "project_name")
	private String projectName;

	@JsonProperty(value = "user")
	private String userLogin;

	@JsonProperty(value = "last_modified")
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
