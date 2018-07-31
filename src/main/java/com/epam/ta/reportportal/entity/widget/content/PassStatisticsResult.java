package com.epam.ta.reportportal.entity.widget.content;

import java.io.Serializable;

/**
 * @author Ivan Budayeu
 */
public class PassStatisticsResult implements Serializable {

	private int passed;
	private int total;

	public PassStatisticsResult() {
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
