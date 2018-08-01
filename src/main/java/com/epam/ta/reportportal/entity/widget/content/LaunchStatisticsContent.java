package com.epam.ta.reportportal.entity.widget.content;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

/**
 * @author Ivan Budayeu
 */
public class LaunchStatisticsContent extends AbstractLaunchStatisticsContent implements Serializable {

	@JsonIgnore
	private Integer issueCount;

	@JsonIgnore
	private String issueName;

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

}
