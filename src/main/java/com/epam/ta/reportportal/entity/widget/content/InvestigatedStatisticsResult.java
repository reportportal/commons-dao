package com.epam.ta.reportportal.entity.widget.content;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;

import static com.epam.ta.reportportal.dao.constant.WidgetContentRepositoryConstants.*;

/**
 * @author Ivan Budayeu
 */
public class InvestigatedStatisticsResult extends AbstractLaunchStatisticsContent {

	@Column(name = TO_INVESTIGATE)
	@JsonProperty(value = TO_INVESTIGATE)
	private double notInvestigatedPercentage;

	@Column(name = INVESTIGATED)
	@JsonProperty(value = INVESTIGATED)
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
