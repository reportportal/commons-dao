package com.epam.ta.reportportal.entity.widget.content;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import java.io.Serializable;

import static com.epam.ta.reportportal.dao.constant.WidgetContentRepositoryConstants.*;

/**
 * @author Ivan Budayeu
 */
public class PassingRateStatisticsResult implements Serializable {

	@Column(name = PASSED)
	@JsonProperty(value = PASSED)
	private int passed;

	@Column(name = TOTAL)
	@JsonProperty(value = TOTAL)
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
