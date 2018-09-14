package com.epam.ta.reportportal.dao.util;

import org.jooq.Field;
import org.jooq.TableField;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.name;

/**
 * @author Ivan Budaev
 */
public final class FieldNameTransformer {

	public static Field<?> fieldName(TableField tableField) {
		return field(name(tableField.getName()));
	}

	public static Field<?> fieldName(String tableFieldName) {
		return field(name(tableFieldName));
	}

	public static Field<?> fieldName(String... fieldQualifiers) {
		return field(name(fieldQualifiers));
	}
}
