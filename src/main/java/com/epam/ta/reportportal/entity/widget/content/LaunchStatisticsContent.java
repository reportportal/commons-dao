package com.epam.ta.reportportal.entity.widget.content;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;
import java.util.Map;

/**
 * @author Ivan Budayeu
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LaunchStatisticsContent extends AbstractLaunchStatisticsContent {

	@JsonIgnore
	private Integer issueCount;

	@JsonIgnore
	private String issueName;

	@JsonProperty(value = "status")
	private String status;

	@JsonProperty(value = "end_time")
	private Timestamp endTime;

	@JsonProperty(value = "last_modified")
	private Timestamp lastModified;

	@JsonProperty(value = "defects")
	private Map<String, Integer> defectsMap;

	@JsonProperty(value = "executions")
	private Map<String, Integer> executionsMap;

	public LaunchStatisticsContent() {
	}

	public String getIssueName() {
		return issueName;
	}

	public void setIssueName(String issueName) {
		this.issueName = issueName;
	}

	public Integer getIssueCount() {
		return issueCount;
	}

	public void setIssueCount(Integer issueCount) {
		this.issueCount = issueCount;
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

	public Map<String, Integer> getDefectsMap() {
		return defectsMap;
	}

	public void setDefectsMap(Map<String, Integer> defectsMap) {
		this.defectsMap = defectsMap;
	}

	public Map<String, Integer> getExecutionsMap() {
		return executionsMap;
	}

	public void setExecutionsMap(Map<String, Integer> executionsMap) {
		this.executionsMap = executionsMap;
	}
}
