/*
 * Copyright 2018 EPAM Systems
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

package com.epam.ta.reportportal.entity.activity;

import com.epam.ta.reportportal.entity.enums.PostgreSQLEnumType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

/**
 * Activity table entity
 *
 * @author Andrei Varabyeu
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "activity", schema = "public")
@TypeDef(name = "activityDetails", typeClass = ActivityDetails.class)
@TypeDef(name = "pqsql_enum", typeClass = PostgreSQLEnumType.class)
public class Activity implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false, precision = 64)
	private Long id;

	@Column(name = "user_id", nullable = false, precision = 32)
	private Long userId;

	@Column(name = "project_id", nullable = false)
	private Long projectId;

	@Enumerated(EnumType.STRING)
	@Column(name = "entity", unique = true, nullable = false)
	@Type(type = "pqsql_enum")
	private ActivityEntityType activityEntityType;

	@Column(name = "action", nullable = false)
	private String action;

	@Column(name = "details")
	@Type(type = "activityDetails")
	private ActivityDetails details;

	@Column(name = "creation_date")
	private LocalDateTime createdAt;

	@Column(name = "object_id")
	private Long objectId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public ActivityEntityType getActivityEntityType() {
		return activityEntityType;
	}

	public void setActivityEntityType(ActivityEntityType activityEntityType) {
		this.activityEntityType = activityEntityType;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public ActivityDetails getDetails() {
		return details;
	}

	public void setDetails(ActivityDetails details) {
		this.details = details;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public Long getObjectId() {
		return objectId;
	}

	public void setObjectId(Long objectId) {
		this.objectId = objectId;
	}

	public enum ActivityEntityType {
		LAUNCH("launch"),
		ITEM("item"),
		DASHBOARD("dashboard"),
		DEFECT_TYPE("defectType"),
		EMAIL_CONFIG("emailConfig"),
		FILTER("filter"),
		IMPORT("import"),
		INTEGRATION("integration"),
		ITEM_ISSUE("itemIssue"),
		PROJECT("project"),
		SHARING("sharing"),
		TICKET("ticket"),
		USER("user"),
		WIDGET("widget");

		private String value;

		ActivityEntityType(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		public static Optional<ActivityEntityType> fromString(String string) {
			return Optional.ofNullable(string)
					.flatMap(str -> Arrays.stream(values()).filter(it -> it.value.equalsIgnoreCase(str)).findAny());
		}
	}
}
