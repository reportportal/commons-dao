package com.epam.ta.reportportal.database.entity.project;

import com.epam.ta.reportportal.jooq.enums.ProjectTypeEnum;
import org.jooq.tools.json.JSONObject;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Period;
import java.util.Objects;

/**
 * @author Pavel Bortnik
 */
@Entity
@Table(name = "project_configuration", schema = "public", indexes = {
		@Index(name = "project_configuration_email_configuration_id_key", unique = true, columnList = "email_configuration_id ASC"),
		@Index(name = "project_configuration_pk", unique = true, columnList = "id ASC") })
public class ProjectConfiguration {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false, precision = 32)
	private Integer id;

	@Column(name = "project_type", nullable = false)
	private ProjectTypeEnum projectType;

	@Column(name = "interrupt_timeout", nullable = false)
	private Period interruptTimeout;

	@Column(name = "keep_logs_interval", nullable = false)
	private Period keepLogsInterval;

	@Column(name = "keep_screenshots_interval", nullable = false)
	private Period keepScreenshotsInterval;

	@Column(name = "aa_enabled", nullable = false)
	private Boolean aaEnabled;

	@Column(name = "metadata")
	private JSONObject metadata;

	@Column(name = "email_configuration_id", unique = true, precision = 32)
	private Integer emailConfigurationId;

	@Column(name = "created_on", nullable = false)
	private Timestamp createdOn;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public ProjectTypeEnum getProjectType() {
		return projectType;
	}

	public void setProjectType(ProjectTypeEnum projectType) {
		this.projectType = projectType;
	}

	public Period getInterruptTimeout() {
		return interruptTimeout;
	}

	public void setInterruptTimeout(Period interruptTimeout) {
		this.interruptTimeout = interruptTimeout;
	}

	public Period getKeepLogsInterval() {
		return keepLogsInterval;
	}

	public void setKeepLogsInterval(Period keepLogsInterval) {
		this.keepLogsInterval = keepLogsInterval;
	}

	public Period getKeepScreenshotsInterval() {
		return keepScreenshotsInterval;
	}

	public void setKeepScreenshotsInterval(Period keepScreenshotsInterval) {
		this.keepScreenshotsInterval = keepScreenshotsInterval;
	}

	public Boolean getAaEnabled() {
		return aaEnabled;
	}

	public void setAaEnabled(Boolean aaEnabled) {
		this.aaEnabled = aaEnabled;
	}

	public JSONObject getMetadata() {
		return metadata;
	}

	public void setMetadata(JSONObject metadata) {
		this.metadata = metadata;
	}

	public Integer getEmailConfigurationId() {
		return emailConfigurationId;
	}

	public void setEmailConfigurationId(Integer emailConfigurationId) {
		this.emailConfigurationId = emailConfigurationId;
	}

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	@Override
	public String toString() {
		return "ProjectConfiguration{" + "id=" + id + ", projectType=" + projectType + ", interruptTimeout=" + interruptTimeout
				+ ", keepLogsInterval=" + keepLogsInterval + ", keepScreenshotsInterval=" + keepScreenshotsInterval + ", aaEnabled="
				+ aaEnabled + ", metadata=" + metadata + ", emailConfigurationId=" + emailConfigurationId + ", createdOn=" + createdOn
				+ '}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		ProjectConfiguration that = (ProjectConfiguration) o;
		return Objects.equals(id, that.id) && projectType == that.projectType && Objects.equals(interruptTimeout, that.interruptTimeout)
				&& Objects.equals(keepLogsInterval, that.keepLogsInterval) && Objects.equals(
				keepScreenshotsInterval, that.keepScreenshotsInterval) && Objects.equals(aaEnabled, that.aaEnabled) && Objects.equals(
				metadata, that.metadata) && Objects.equals(
				emailConfigurationId, that.emailConfigurationId) && Objects.equals(createdOn, that.createdOn);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, projectType, interruptTimeout, keepLogsInterval, keepScreenshotsInterval, aaEnabled, metadata,
				emailConfigurationId, createdOn
		);
	}
}
