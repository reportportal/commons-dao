package com.epam.ta.reportportal.entity;

import java.util.ArrayList;

public class ActivityDetails extends JsonbObject {

	private ArrayList<HistoryField> history;

	private String objectName;

	public ActivityDetails() {
	}

	public ActivityDetails(String objectName) {
		this.objectName = objectName;
	}

	public ActivityDetails(String objectName, ArrayList<HistoryField> history) {
		this.history = history;
		this.objectName = objectName;
	}

	public ArrayList<HistoryField> getHistory() {
		return history;
	}

	public void setHistory(ArrayList<HistoryField> history) {
		this.history = history;
	}

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public void addHistoryField(HistoryField historyField) {
		history.add(historyField);
	}
}