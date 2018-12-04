package com.epam.ta.reportportal.entity.widget.content;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Map;

/**
 * @author <a href="mailto:ivan_budayeu@epam.com">Ivan Budayeu</a>
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OverallStatisticsContent implements Serializable {

	@JsonProperty(value = "values")
	private Map<String, Long> values;

	public OverallStatisticsContent() {
	}

	public OverallStatisticsContent(Map<String, Long> values) {
		this.values = values;
	}

	public Map<String, Long> getValues() {
		return values;
	}

	public void setValues(Map<String, Long> values) {
		this.values = values;
	}
}
