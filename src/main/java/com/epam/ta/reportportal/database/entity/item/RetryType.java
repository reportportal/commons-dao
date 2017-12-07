package com.epam.ta.reportportal.database.entity.item;

import java.util.Arrays;
import java.util.Optional;

/**
 * @author Pavel Bortnik
 */
public enum RetryType {
	ROOT("ROOT"),
	RETRY("RETRY"),
	LAST("LAST");

	RetryType(String value) {
		this.value = value;
	}

	private String value;

	public static Optional<RetryType> fromString(String string) {
		return Arrays.stream(values()).filter(it -> it.getValue().equals(string)).findAny();
	}

	public String getValue() {
		return value;
	}
}
