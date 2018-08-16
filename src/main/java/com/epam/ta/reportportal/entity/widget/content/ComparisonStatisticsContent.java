package com.epam.ta.reportportal.entity.widget.content;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/**
 * @author Ivan Budayeu
 */
public class ComparisonStatisticsContent extends AbstractLaunchStatisticsContent {

	@JsonIgnore
	private Double issuePercentage;

	@JsonIgnore
	private String issueName;

	@JsonProperty(value = "defect_groups")
	private Map<String, Double> defectGroups;

	@JsonProperty(value = "executions")
	private Map<String, Double> executionsMap;

	public ComparisonStatisticsContent() {
	}

	public Double getIssuePercentage() {
		return issuePercentage;
	}

	public void setIssuePercentage(Double issuePercentage) {
		this.issuePercentage = issuePercentage;
	}

	public String getIssueName() {
		return issueName;
	}

	public void setIssueName(String issueName) {
		this.issueName = issueName;
	}

	public Map<String, Double> getDefectGroups() {
		return defectGroups;
	}

	public void setDefectGroups(Map<String, Double> defectGroups) {
		this.defectGroups = defectGroups;
	}

	public Map<String, Double> getExecutionsMap() {
		return executionsMap;
	}

	public void setExecutionsMap(Map<String, Double> executionsMap) {
		this.executionsMap = executionsMap;
	}
}
