package com.epam.ta.reportportal.entity.widget.content;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author Ivan Budayeu
 */
public abstract class AbstractLaunchStatisticsContent implements Serializable {

	@JsonProperty(value = "id")
	private Long launchId;

	@JsonProperty(value = "number")
	private Integer number;

	@JsonProperty(value = "name")
	private String name;

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
		return MoreObjects.toStringHelper(this)
				.add("launchId", launchId)
				.add("number", number)
				.add("name", name)
				.add("startTime", startTime)
				.toString();
	}
}
