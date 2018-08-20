package com.epam.ta.reportportal.entity.widget.content;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author Ivan Budayeu
 */
public abstract class AbstractLaunchStatisticsContent implements Serializable {

	@Column(name = "launch_id")
	@JsonProperty(value = "id")
	private Long launchId;

	@Column(name = "number")
	@JsonProperty(value = "number")
	private Integer number;

	@Column(name = "project")
	@JsonProperty(value = "name")
	private String name;

	@Column(name = "start_time")
	@JsonProperty(value = "start_time")
	private Timestamp startTime;

	public Long getLaunchId() {
		return launchId;
	}

	public void setLaunchId(Long launchId) {
		this.launchId = launchId;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Timestamp getStartTime() {
		return startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("AbstractLaunchStatisticsContent{");
		sb.append("launchId=").append(launchId);
		sb.append(", number=").append(number);
		sb.append(", name='").append(name).append('\'');
		sb.append(", startTime=").append(startTime);
		sb.append('}');
		return sb.toString();
	}
}
