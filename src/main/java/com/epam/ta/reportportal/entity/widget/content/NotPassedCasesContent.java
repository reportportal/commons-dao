package com.epam.ta.reportportal.entity.widget.content;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import java.util.Map;

/**
 * @author Ivan Budayeu
 */
public class NotPassedCasesContent extends AbstractLaunchStatisticsContent {

	@Column(name = "percentage")
	@JsonProperty(value = "% (Failed+Skipped)/Total")
	private Double percentage;

	@JsonProperty(value = "values")
	private Map<String, String> values;

	public NotPassedCasesContent() {
	}

	public Map<String, String> getValues() {
		return values;
	}

	public void setValues(Map<String, String> values) {
		this.values = values;
	}

	//
	public Double getPercentage() {
		return percentage;
	}

	public void setPercentage(Double percentage) {
		this.percentage = percentage;
	}
}
