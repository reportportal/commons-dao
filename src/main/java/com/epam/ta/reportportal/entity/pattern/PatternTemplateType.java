package com.epam.ta.reportportal.entity.pattern;

import java.util.Arrays;
import java.util.Optional;

/**
 * @author <a href="mailto:ivan_budayeu@epam.com">Ivan Budayeu</a>
 */
public enum PatternTemplateType {

	STRING,
	REGEX;

	public static Optional<PatternTemplateType> fromString(String string) {
		return Arrays.stream(PatternTemplateType.values()).filter(type -> type.name().equalsIgnoreCase(string)).findFirst();
	}
}
