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
public class LaunchesTableContent extends AbstractLaunchStatisticsContent {

	@JsonIgnore
	private int issueCount;

	@JsonIgnore
	private String issueName;

	@JsonProperty(value = "status")
	private String status;

	@JsonProperty(value = "end_time")
	private Timestamp endTime;

	@JsonProperty(value = "last_modified")
	private Timestamp lastModified;

	@JsonProperty(value = "statistics")
	private Map<String, Integer> issueStatisticsMap;

	public LaunchesTableContent() {
	}

	public int getIssueCount() {
		return issueCount;
	}

	public void setIssueCount(int issueCount) {
		this.issueCount = issueCount;
	}

	public String getIssueName() {
		return issueName;
	}

	public void setIssueName(String issueName) {
		this.issueName = issueName;
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

	public Map<String, Integer> getIssueStatisticsMap() {
		return issueStatisticsMap;
	}

	public void setIssueStatisticsMap(Map<String, Integer> issueStatisticsMap) {
		this.issueStatisticsMap = issueStatisticsMap;
	}
}
