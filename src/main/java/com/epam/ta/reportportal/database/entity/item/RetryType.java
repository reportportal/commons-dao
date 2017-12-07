package com.epam.ta.reportportal.database.entity.item;

import java.util.Arrays;
import java.util.Optional;

/**
 * @author Pavel Bortnik
 */
public enum RetryType {
	ROOT("root"),
	RETRY("retry"),
	LAST("last");

	RetryType(String value) {
		this.value = value;
	}

	private String value;

	public static Optional<RetryType> fromString(String string) {
		return Optional.ofNullable(string)
				.flatMap(str -> Arrays.stream(values())
						.filter(it -> it.value.equals(str)).findAny());
	}

	public String getValue() {
		return value;
	}
}
