/*
 * Copyright 2017 EPAM Systems
 *
 *
 * This file is part of EPAM Report Portal.
 * https://github.com/reportportal/service-api
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

package com.epam.ta.reportportal.entity.launch;

import com.epam.ta.reportportal.entity.enums.LaunchModeEnum;
import com.epam.ta.reportportal.entity.enums.PostgreSQLEnumType;
import com.epam.ta.reportportal.entity.enums.StatusEnum;
import com.epam.ta.reportportal.entity.item.ExecutionStatistics;
import com.epam.ta.reportportal.entity.item.IssueStatistics;
import com.google.common.collect.Sets;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Pavel Bortnik
 */

@Entity
@EntityListeners(AuditingEntityListener.class)
@TypeDef(name = "pqsql_enum", typeClass = PostgreSQLEnumType.class)
@Table(name = "launch", schema = "public", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "name", "number", "project_id" }) }, indexes = {
		@Index(name = "launch_pk", unique = true, columnList = "id ASC"),
		@Index(name = "unq_name_number", unique = true, columnList = "name ASC, number ASC, project_id ASC") })
public class Launch implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false, precision = 64)
	private Long id;

	@Column(name = "uuid", unique = true, nullable = false)
	private String uuid;

	@Column(name = "project_id", nullable = false, precision = 32)
	private Long projectId;

	@Column(name = "user_id", nullable = false, precision = 32)
	private Long userId;

	@Column(name = "name", nullable = false, length = 256)
	private String name;

	@Column(name = "description")
	private String description;

	@Column(name = "start_time", nullable = false, updatable = false)
	private LocalDateTime startTime;

	@Column(name = "end_time", nullable = false, updatable = false)
	private LocalDateTime endTime;

	@Column(name = "number", nullable = false, precision = 32)
	private Long number;

	@Column(name = "email_sender_case_id")
	private Long emailSenderCaseId;

	@Column(name = "last_modified", nullable = false)
	@LastModifiedDate
	private LocalDateTime lastModified;

	@Column(name = "mode", nullable = false)
	@Enumerated(EnumType.STRING)
	@Type(type = "pqsql_enum")
	private LaunchModeEnum mode;

	@Column(name = "status", nullable = false)
	@Enumerated(EnumType.STRING)
	@Type(type = "pqsql_enum")
	private StatusEnum status;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinColumn(name = "launch_id")
	private Set<LaunchTag> tags = Sets.newHashSet();

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "launch_id", insertable = false, updatable = false)
	private Set<ExecutionStatistics> executionStatistics = new HashSet<>();

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "launch_id", insertable = false, updatable = false)
	private Set<IssueStatistics> issueStatistics = new HashSet<>();

	public Set<LaunchTag> getTags() {
		return tags;
	}

	public void setTags(Set<LaunchTag> tags) {
		this.tags.clear();
		this.tags.addAll(tags);
	}

	public Launch(Long id, String uuid, Long projectId, Long userId, String name, String description, LocalDateTime startTime,
			LocalDateTime endTime, Long number, LocalDateTime lastModified, LaunchModeEnum mode, StatusEnum status) {
		this.id = id;
		this.uuid = uuid;
		this.projectId = projectId;
		this.userId = userId;
		this.name = name;
		this.description = description;
		this.startTime = startTime;
		this.endTime = endTime;
		this.number = number;
		this.lastModified = lastModified;
		this.mode = mode;
		this.status = status;
	}

	public Launch() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public Set<ExecutionStatistics> getExecutionStatistics() {
		return executionStatistics;
	}

	public void setExecutionStatistics(Set<ExecutionStatistics> executionStatistics) {
		this.executionStatistics = executionStatistics;
	}

	public Set<IssueStatistics> getIssueStatistics() {
		return issueStatistics;
	}

	public void setIssueStatistics(Set<IssueStatistics> issueStatistics) {
		this.issueStatistics = issueStatistics;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	public LocalDateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}

	public Long getEmailSenderCaseId() {
		return emailSenderCaseId;
	}

	public void setEmailSenderCaseId(Long emailSenderCaseId) {
		this.emailSenderCaseId = emailSenderCaseId;
	}

	public Long getNumber() {
		return number;
	}

	public void setNumber(Long number) {
		this.number = number;
	}

	public LocalDateTime getLastModified() {
		return lastModified;
	}

	public void setLastModified(LocalDateTime lastModified) {
		this.lastModified = lastModified;
	}

	public LaunchModeEnum getMode() {
		return mode;
	}

	public void setMode(LaunchModeEnum mode) {
		this.mode = mode;
	}

	public StatusEnum getStatus() {
		return status;
	}

	public void setStatus(StatusEnum status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Launch{" + "id=" + id + ", uuid='" + uuid + '\'' + ", projectId=" + projectId + ", userId=" + userId + ", name='" + name
				+ '\'' + ", description='" + description + '\'' + ", startTime=" + startTime + ", endTime=" + endTime + ", number=" + number
				+ ", lastModified=" + lastModified + ", mode=" + mode + ", status=" + status + ", tags=" + tags + ", executionStatistics="
				+ executionStatistics + ", issueStatistics=" + issueStatistics + '}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		Launch launch = (Launch) o;

		if (id != null ? !id.equals(launch.id) : launch.id != null) {
			return false;
		}
		if (uuid != null ? !uuid.equals(launch.uuid) : launch.uuid != null) {
			return false;
		}
		if (projectId != null ? !projectId.equals(launch.projectId) : launch.projectId != null) {
			return false;
		}
		if (userId != null ? !userId.equals(launch.userId) : launch.userId != null) {
			return false;
		}
		if (name != null ? !name.equals(launch.name) : launch.name != null) {
			return false;
		}
		if (description != null ? !description.equals(launch.description) : launch.description != null) {
			return false;
		}
		if (startTime != null ? !startTime.equals(launch.startTime) : launch.startTime != null) {
			return false;
		}
		if (endTime != null ? !endTime.equals(launch.endTime) : launch.endTime != null) {
			return false;
		}
		if (number != null ? !number.equals(launch.number) : launch.number != null) {
			return false;
		}
		if (lastModified != null ? !lastModified.equals(launch.lastModified) : launch.lastModified != null) {
			return false;
		}
		if (mode != launch.mode) {
			return false;
		}
		if (status != launch.status) {
			return false;
		}
		if (tags != null ? !tags.equals(launch.tags) : launch.tags != null) {
			return false;
		}
		if (executionStatistics != null ? !executionStatistics.equals(launch.executionStatistics) : launch.executionStatistics != null) {
			return false;
		}
		return issueStatistics != null ? issueStatistics.equals(launch.issueStatistics) : launch.issueStatistics == null;
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (uuid != null ? uuid.hashCode() : 0);
		result = 31 * result + (projectId != null ? projectId.hashCode() : 0);
		result = 31 * result + (userId != null ? userId.hashCode() : 0);
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (description != null ? description.hashCode() : 0);
		result = 31 * result + (startTime != null ? startTime.hashCode() : 0);
		result = 31 * result + (endTime != null ? endTime.hashCode() : 0);
		result = 31 * result + (number != null ? number.hashCode() : 0);
		result = 31 * result + (lastModified != null ? lastModified.hashCode() : 0);
		result = 31 * result + (mode != null ? mode.hashCode() : 0);
		result = 31 * result + (status != null ? status.hashCode() : 0);
		result = 31 * result + (tags != null ? tags.hashCode() : 0);
		result = 31 * result + (executionStatistics != null ? executionStatistics.hashCode() : 0);
		result = 31 * result + (issueStatistics != null ? issueStatistics.hashCode() : 0);
		return result;
	}
}
