package com.epam.ta.reportportal.dao.util;

import org.jooq.Field;

import java.util.Arrays;
import java.util.function.Predicate;

/**
 * @author <a href="mailto:ivan_budayeu@epam.com">Ivan Budayeu</a>
 */
public final class RecordMapperUtils {

	private RecordMapperUtils() {
		//static only
	}

	public static Predicate<Field<?>> fieldExcludingPredicate(Field<?>... fields) {
		return field -> Arrays.stream(fields)
				.noneMatch(f -> f.getName().equalsIgnoreCase(field.getName()) && f.getQualifiedName()
						.toString()
						.equalsIgnoreCase(field.getQualifiedName().toString()));
	}
}
