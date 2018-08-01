package com.epam.ta.reportportal.entity.widget.content;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * @author Ivan Budayeu
 */
public class InvestigatedStatisticsResult extends AbstractLaunchStatisticsContent implements Serializable {

	@JsonProperty(value = "to_investigate")
	private double notInvestigatedPercentage;

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
