package com.epam.ta.reportportal.entity.widget.content;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/**
 * @author Ivan Budayeu
 */
public class LaunchStatisticsContent extends AbstractLaunchStatisticsContent {

	@JsonIgnore
	private Integer issueCount;

	@JsonIgnore
	private String issueName;

	@JsonProperty(value = "statistics")
	private Map<String, Integer> issuesMap;

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

	public Map<String, Integer> getIssuesMap() {
		return issuesMap;
	}

	public void setIssuesMap(Map<String, Integer> issuesMap) {
		this.issuesMap = issuesMap;
	}
}
