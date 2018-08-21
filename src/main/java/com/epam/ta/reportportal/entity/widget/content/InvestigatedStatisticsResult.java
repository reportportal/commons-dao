package com.epam.ta.reportportal.entity.widget.content;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;

/**
 * @author Ivan Budayeu
 */
public class InvestigatedStatisticsResult extends AbstractLaunchStatisticsContent {

	@Column(name = "to_investigate")
	@JsonProperty(value = "to_investigate")
	private double notInvestigatedPercentage;

	@Column(name = "investigated")
	@JsonProperty(value = "investigated")
	private double investigatedPercentage;

	public InvestigatedStatisticsResult() {
	}

	public double getNotInvestigatedPercentage() {
		return notInvestigatedPercentage;
	}

	public void setNotInvestigatedPercentage(double notInvestigatedPercentage) {
		this.notInvestigatedPercentage = notInvestigatedPercentage;
	}

	public double getInvestigatedPercentage() {
		return investigatedPercentage;
	}

	public void setInvestigatedPercentage(double investigatedPercentage) {
		this.investigatedPercentage = investigatedPercentage;
	}
}
