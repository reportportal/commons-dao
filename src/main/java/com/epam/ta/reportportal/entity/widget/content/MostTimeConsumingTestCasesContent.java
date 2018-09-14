package com.epam.ta.reportportal.entity.widget.content;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import java.io.Serializable;

import static com.epam.ta.reportportal.dao.constant.WidgetContentRepositoryConstants.*;

/**
 * @author Ivan Budaev
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MostTimeConsumingTestCasesContent implements Serializable {

	@JsonProperty(value = ID)
	@Column(name = ID)
	private Long id;

	@JsonProperty(value = NAME)
	@Column(name = NAME)
	private String name;

	@Column(name = "status")
	private String status;

	@Column(name = "type")
	private String type;

	@JsonProperty(value = UNIQUE_ID)
	@Column(name = UNIQUE_ID)
	private String uniqueId;

	@JsonProperty(value = START_TIME)
	@Column(name = START_TIME)
	private Long startTime;

	@JsonProperty(value = END_TIME)
	@Column(name = END_TIME)
	private Long endTime;

	@JsonProperty(value = DURATION)
	@Column(name = DURATION)
	private Double duration;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	public Long getStartTime() {
		return startTime;
	}

	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}

	public Long getEndTime() {
		return endTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

	public Double getDuration() {
		return duration;
	}

	public void setDuration(Double duration) {
		this.duration = duration;
	}
}
