package com.epam.ta.reportportal.entity.widget.content;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import java.io.Serializable;

/**
 * @author Ivan Budayeu
 */
public class PassingRateStatisticsResult implements Serializable {

	@Column(name = "passed")
	@JsonProperty(value = "passed")
	private int passed;

	@Column(name = "total")
	@JsonProperty(value = "total")
	private int total;

	public PassingRateStatisticsResult() {
	}

	public int getPassed() {
		return passed;
	}

	public void setPassed(int passed) {
		this.passed = passed;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
}
