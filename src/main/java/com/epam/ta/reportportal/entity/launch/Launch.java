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

package com.epam.ta.reportportal.entity.launch;

import com.epam.ta.reportportal.entity.ItemAttribute;
import com.epam.ta.reportportal.entity.enums.LaunchModeEnum;
import com.epam.ta.reportportal.entity.enums.PostgreSQLEnumType;
import com.epam.ta.reportportal.entity.enums.StatusEnum;
import com.epam.ta.reportportal.entity.log.Log;
import com.epam.ta.reportportal.entity.statistics.Statistics;
import com.epam.ta.reportportal.entity.user.User;
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
import java.util.Objects;
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

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@Column(name = "name", nullable = false, length = 256)
	private String name;

	@Column(name = "description")
	private String description;

	@Column(name = "start_time", nullable = false)
	private LocalDateTime startTime;

	@Column(name = "end_time")
	private LocalDateTime endTime;

	@Column(name = "number", nullable = false, precision = 32)
	private Long number;

	@Column(name = "has_retries")
	private boolean hasRetries;

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

	@OneToMany(mappedBy = "launch", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	@Fetch(FetchMode.JOIN)
	private Set<ItemAttribute> attributes = Sets.newHashSet();

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "launch_id", insertable = false, updatable = false)
	private Set<Statistics> statistics = Sets.newHashSet();

	@OneToMany(mappedBy = "launch", fetch = FetchType.LAZY, orphanRemoval = true)
	private Set<Log> logs = Sets.newHashSet();

	@Column(name = "approximate_duration")
	private double approximateDuration;

	public Set<ItemAttribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(Set<ItemAttribute> tags) {
		this.attributes.clear();
		this.attributes.addAll(tags);
	}

	public Launch() {
	}

	public Launch(Long id) {
		this.id = id;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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

	public Set<Statistics> getStatistics() {
		return statistics;
	}

	public void setStatistics(Set<Statistics> statistics) {
		this.statistics = statistics;
	}

	public LocalDateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}

	public Long getNumber() {
		return number;
	}

	public void setNumber(Long number) {
		this.number = number;
	}

	public boolean isHasRetries() {
		return hasRetries;
	}

	public void setHasRetries(boolean hasRetries) {
		this.hasRetries = hasRetries;
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

	public Set<Log> getLogs() {
		return logs;
	}

	public void setLogs(Set<Log> logs) {
		this.logs = logs;
	}

	public void setStatus(StatusEnum status) {
		this.status = status;
	}

	public double getApproximateDuration() {
		return approximateDuration;
	}

	public void setApproximateDuration(double approximateDuration) {
		this.approximateDuration = approximateDuration;
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
		return Objects.equals(uuid, launch.uuid) && Objects.equals(projectId, launch.projectId) && Objects.equals(name, launch.name)
				&& Objects.equals(description, launch.description) && Objects.equals(startTime, launch.startTime) && Objects.equals(endTime,
				launch.endTime
		) && Objects.equals(number, launch.number) && hasRetries == launch.isHasRetries() && mode == launch.mode && status == launch.status;
	}

	@Override
	public int hashCode() {
		return Objects.hash(uuid, projectId, name, description, startTime, endTime, number, hasRetries, mode, status);
	}

	@Override
	public String toString() {
		return "Launch{" + "id=" + id + ", uuid='" + uuid + '\'' + ", projectId=" + projectId + ", user=" + user + ", name='" + name + '\''
				+ ", description='" + description + '\'' + ", startTime=" + startTime + ", endTime=" + endTime + ", number=" + number
				+ ", hasRetries=" + hasRetries + ", lastModified=" + lastModified + ", mode=" + mode + ", status=" + status
				+ ", attributes=" + attributes + ", statistics=" + statistics + '}';
	}
}