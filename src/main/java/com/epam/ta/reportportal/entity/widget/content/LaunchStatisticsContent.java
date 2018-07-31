package com.epam.ta.reportportal.entity.widget.content;

import java.io.Serializable;

/**
 * @author Ivan Budayeu
 */
public class LaunchStatisticsContent implements Serializable {

	private Integer launchNumber;
	private String issueName;
	private Integer issueCount;

	public LaunchStatisticsContent() {
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

	public Integer getIssueCount() {
		return issueCount;
	}

	public void setIssueCount(Integer issueCount) {
		this.issueCount = issueCount;
	}

}
