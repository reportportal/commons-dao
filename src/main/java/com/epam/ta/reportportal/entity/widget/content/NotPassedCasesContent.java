package com.epam.ta.reportportal.entity.widget.content;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author Ivan Budayeu
 */
public class NotPassedCasesContent implements Serializable {

	@JsonProperty(value = "launch_number")
	private Integer number;

	@JsonProperty(value = "name")
	private String name;

	@JsonProperty(value = "start_time")
	private Timestamp startTime;

	@JsonProperty(value = "% (Failed+Skipped)/Total")
	private Double percentage;

	public NotPassedCasesContent() {
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

	public Double getPercentage() {
		return percentage;
	}

	public void setPercentage(Double percentage) {
		this.percentage = percentage;
	}

}
