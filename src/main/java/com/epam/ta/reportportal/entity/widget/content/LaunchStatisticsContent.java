package com.epam.ta.reportportal.entity.widget.content;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;

/**
 * @author Ivan Budayeu
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LaunchStatisticsContent extends AbstractLaunchStatisticsContent {

	@JsonProperty(value = "status")
	private String status;

	@JsonProperty(value = "end_time")
	private Timestamp endTime;

	@JsonProperty(value = "last_modified")
	private Timestamp lastModified;

	public LaunchStatisticsContent() {
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Timestamp getEndTime() {
		return endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	public Timestamp getLastModified() {
		return lastModified;
	}

	public void setLastModified(Timestamp lastModified) {
		this.lastModified = lastModified;
	}

}
