package com.epam.ta.reportportal.database.entity.item;

/**
 * Retry type.
 *
 * @author Pavel Bortnik
 */
public enum RetryType {

	/**
	 * The first test run that was retried
	 */
	ROOT("ROOT"),

	/**
	 * Retry run
	 */
	RETRY("RETRY"),

	/**
	 * Last run in retries
	 */
	LAST("LAST");

	RetryType(String value) {
		this.value = value;
	}

	private String value;

	public String getValue() {
		return value;
	}
}
