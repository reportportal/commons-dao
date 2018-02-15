package com.epam.ta.reportportal.database.entity.launch;

import com.epam.ta.reportportal.database.entity.enums.PostgreSQLEnumType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

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
public class Launch {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false, precision = 64)
	private Long id;

	@Column(name = "project_id", nullable = false, precision = 32)
	private Integer projectId;

	@Column(name = "user_id", nullable = false, precision = 32)
	private Long userId;

	@Column(name = "name", nullable = false, length = 256)
	private String name;

	@Column(name = "description")
	private String description;

	@Column(name = "start_time", nullable = false, updatable = false)
	private Timestamp startTime;

	@Column(name = "number", nullable = false, precision = 32)
	private Integer number;

	@Column(name = "last_modified", nullable = false)
	@LastModifiedDate
	private Timestamp lastModified;

	@Column(name = "mode", nullable = false)
	@Type(type = "pqsql_enum")
	private LaunchModeEnum mode;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
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

	public Timestamp getStartTime() {
		return startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Timestamp getLastModified() {
		return lastModified;
	}

	public void setLastModified(Timestamp lastModified) {
		this.lastModified = lastModified;
	}

	public LaunchModeEnum getMode() {
		return mode;
	}

	public void setMode(LaunchModeEnum mode) {
		this.mode = mode;
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
		return Objects.equals(id, launch.id) && Objects.equals(projectId, launch.projectId) && Objects.equals(userId, launch.userId)
				&& Objects.equals(name, launch.name) && Objects.equals(description, launch.description) && Objects.equals(
				startTime, launch.startTime) && Objects.equals(number, launch.number) && Objects.equals(lastModified, launch.lastModified)
				&& mode == launch.mode;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, projectId, userId, name, description, startTime, number, lastModified, mode);
	}

	@Override
	public String toString() {
		return "Launch{" + "id=" + id + ", projectId=" + projectId + ", userId=" + userId + ", name='" + name + '\'' + ", description='"
				+ description + '\'' + ", startTime=" + startTime + ", number=" + number + ", lastModified=" + lastModified + ", mode="
				+ mode + '}';
	}
}
