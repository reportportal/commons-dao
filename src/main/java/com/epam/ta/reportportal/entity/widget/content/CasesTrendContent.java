package com.epam.ta.reportportal.entity.widget.content;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author Ivan Budayeu
 */
public class CasesTrendContent implements Serializable {

	private String launchId;
	private String launchName;
	private Integer launchNumber;
	private Timestamp startTime;
	private int delta;
	private int total;

	public CasesTrendContent() {
	}

	public Integer getLaunchNumber() {
		return launchNumber;
	}

	public void setLaunchNumber(Integer launchNumber) {
		this.launchNumber = launchNumber;
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

	public Timestamp getStartTime() {
		return startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public String getLaunchId() {
		return launchId;
	}

	public void setLaunchId(String launchId) {
		this.launchId = launchId;
	}

	public String getLaunchName() {
		return launchName;
	}

	public void setLaunchName(String launchName) {
		this.launchName = launchName;
	}
}
