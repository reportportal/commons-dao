package com.epam.ta.reportportal.commons;

import java.util.Arrays;
import java.util.Optional;

/**
 * Email notification cases enumerator for project settings
 *
 * @author Andrei_Ramanchuk
 */
public enum SendCase {

	//@formatter:off
	ALWAYS("always"),
	FAILED("failed"),
	TO_INVESTIGATE("to_investigate"),
	MORE_10("more_10"),
	MORE_20("more_20"),
	MORE_50("more_50");
	//@formatter:on

	private final String value;

	SendCase(String value) {
		this.value = value;
	}

	public static Optional<SendCase> findByName(String name) {
		return Arrays.stream(SendCase.values()).filter(val -> val.getCaseString().equalsIgnoreCase(name)).findAny();
	}

	public static boolean isPresent(String name) {
		return findByName(name).isPresent();
	}

	public String getCaseString() {
		return value;
	}
}