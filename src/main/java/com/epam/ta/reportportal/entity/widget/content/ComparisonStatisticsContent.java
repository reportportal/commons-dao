package com.epam.ta.reportportal.entity.widget.content;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/**
 * @author Ivan Budayeu
 */
public class ComparisonStatisticsContent extends AbstractLaunchStatisticsContent {

	@JsonProperty(value = "values")
	private Map<String, String> values;

	public Map<String, String> getValues() {
		return values;
	}

	public void setValues(Map<String, String> values) {
		this.values = values;
	}
}
