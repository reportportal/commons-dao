package com.epam.ta.reportportal.entity.widget.content;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author Ivan Budayeu
 */
public class InvestigatedStatisticsResult implements Serializable {

	private Integer launchNumber;
	private double notInvestigatedPercentage;
	private double investigatedPercentage;
	private String name;
	private Timestamp startTime;

	public InvestigatedStatisticsResult() {
	}

	public Integer getLaunchNumber() {
		return launchNumber;
	}

	public void setLaunchNumber(Integer launchNumber) {
		this.launchNumber = launchNumber;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Timestamp getStartTime() {
		return startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}
}
