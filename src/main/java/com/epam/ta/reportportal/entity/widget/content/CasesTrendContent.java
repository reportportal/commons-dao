package com.epam.ta.reportportal.entity.widget.content;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * @author Ivan Budayeu
 */
public class CasesTrendContent extends AbstractLaunchStatisticsContent implements Serializable {

	@JsonProperty(value = "delta")
	private int delta;

	@JsonProperty(value = "total")
	private int total;

	public CasesTrendContent() {
	}

	public int getDelta() {
		return delta;
	}

	public void setDelta(int delta) {
		this.delta = delta;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
}
