package com.epam.ta.reportportal.database.entity.item;

/**
 * @author Pavel Bortnik
 */
public enum RetryType {
	RETRY("RETRY"),
	LAST("LAST");

	RetryType(String value) {
		this.value = value;
	}

	private String value;

	public String getValue() {
		return value;
	}
}
