package com.epam.ta.reportportal.entity.widget.content;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;

/**
 * @author Ivan Budayeu
 */
public class CasesTrendContent extends AbstractLaunchStatisticsContent {

	@Column(name = "delta")
	@JsonProperty(value = "delta")
	private int delta;

	@Column(name = "statistics$executions$total")
	@JsonProperty(value = "statistics$executions$total")
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

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("CasesTrendContent{");
		sb.append(super.toString());
		sb.append("delta=").append(delta);
		sb.append(", total=").append(total);
		sb.append('}');
		return sb.toString();
	}
}
