package com.epam.ta.reportportal.entity.widget.content;

import java.io.Serializable;

/**
 * @author Ivan Budayeu
 */
public class ComparisonStatisticsContent implements Serializable {

	private Integer launchNumber;
	private String issueName;
	private Double issuePercentage;

	public ComparisonStatisticsContent() {
	}

	public Integer getLaunchNumber() {
		return launchNumber;
	}

	public void setLaunchNumber(Integer launchNumber) {
		this.launchNumber = launchNumber;
	}

	public String getIssueName() {
		return issueName;
	}

	public void setIssueName(String issueName) {
		this.issueName = issueName;
	}

	public Double getIssuePercentage() {
		return issuePercentage;
	}

	public void setIssuePercentage(Double issuePercentage) {
		this.issuePercentage = issuePercentage;
	}
}
