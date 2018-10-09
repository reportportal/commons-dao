package com.epam.ta.reportportal.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

public class HistoryField implements Serializable {
	private String field;
	private String oldValue;
	private String newValue;

	private HistoryField(String field, String oldValue, String newValue) {
		this.field = field;
		this.oldValue = oldValue;
		this.newValue = newValue;
	}

	public HistoryField() {
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getOldValue() {
		return oldValue;
	}

	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}

	public String getNewValue() {
		return newValue;
	}

	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}

	@JsonIgnore
	public boolean isEmpty() {
		return null == oldValue || null == newValue;
	}

	public static HistoryField of(String field, String oldValue, String newValue) {
		return new HistoryField(field, oldValue, newValue);
	}
}