package com.epam.ta.reportportal.entity.widget.content;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

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
