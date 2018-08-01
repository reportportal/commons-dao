package com.epam.ta.reportportal.entity.widget.content;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Map;

/**
 * @author Ivan Budayeu
 */
public class ComparisonStatisticsContent extends AbstractLaunchStatisticsContent implements Serializable {

	@JsonIgnore
	private Double issuePercentage;

	@JsonIgnore
	private String issueName;

	@JsonProperty(value = "statistics")
	private Map<String, Double> statistics;

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

	public Map<String, Double> getStatistics() {
		return statistics;
	}

	public void setStatistics(Map<String, Double> statistics) {
		this.statistics = statistics;
	}
}
