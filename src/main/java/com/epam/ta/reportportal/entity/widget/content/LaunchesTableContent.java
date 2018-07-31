package com.epam.ta.reportportal.entity.widget.content;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Map;

/**
 * @author Ivan Budayeu
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LaunchesTableContent implements Serializable {

	@JsonProperty(value = "launch_id")
	private Long launchId;

	@JsonIgnore
	private int issueCount;

	private Integer number;

	@JsonIgnore
	private String issueName;

	private String name;

	private String status;

	@JsonProperty(value = "last_modified")
	private Timestamp lastModified;

	@JsonProperty(value = "statistics")
	private Map<String, Integer> issueStatisticsMap;

	public LaunchesTableContent() {
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Timestamp getLastModified() {
		return lastModified;
	}

	public void setLastModified(Timestamp lastModified) {
		this.lastModified = lastModified;
	}

	public Integer getIssueCount() {
		return issueCount;
	}

	public void setIssueCount(Integer issueCount) {
		this.issueCount = issueCount;
	}

	public String getIssueName() {
		return issueName;
	}

	public void setIssueName(String issueName) {
		this.issueName = issueName;
	}

	public void setIssueCount(int issueCount) {
		this.issueCount = issueCount;
	}

	public Map<String, Integer> getIssueStatisticsMap() {
		return issueStatisticsMap;
	}

	public void setIssueStatisticsMap(Map<String, Integer> issueStatisticsMap) {
		this.issueStatisticsMap = issueStatisticsMap;
	}

	public Long getLaunchId() {
		return launchId;
	}

	public void setLaunchId(Long launchId) {
		this.launchId = launchId;
	}
}
