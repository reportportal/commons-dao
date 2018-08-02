package com.epam.ta.reportportal.entity.widget.content;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Ivan Budayeu
 */
public class NotPassedCasesContent extends AbstractLaunchStatisticsContent {

	@JsonProperty(value = "% (Failed+Skipped)/Total")
	private Double percentage;

	public NotPassedCasesContent() {
	}

	public Double getPercentage() {
		return percentage;
	}

	public void setPercentage(Double percentage) {
		this.percentage = percentage;
	}
}
