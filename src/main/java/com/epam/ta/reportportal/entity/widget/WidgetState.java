package com.epam.ta.reportportal.entity.widget;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Optional;

/**
 * @author <a href="mailto:ivan_budayeu@epam.com">Ivan Budayeu</a>
 */
public enum WidgetState {

	CREATED("created"),

	RENDERING("rendering"),

	READY("ready"),

	FAILED("failed");

	WidgetState(String value) {
		this.value = value;
	}

	private final String value;

	public String getValue() {
		return value;
	}

	public static Optional<WidgetState> findByName(@Nullable String name) {
		return Arrays.stream(WidgetState.values()).filter(state -> state.getValue().equalsIgnoreCase(name)).findAny();
	}
}
