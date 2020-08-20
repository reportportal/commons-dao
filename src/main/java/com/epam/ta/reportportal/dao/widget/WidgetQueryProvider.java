package com.epam.ta.reportportal.dao.widget;

import org.jooq.Record;
import org.jooq.Select;

import java.util.Set;
import java.util.function.Function;

/**
 * @author <a href="mailto:ivan_budayeu@epam.com">Ivan Budayeu</a>
 */
public interface WidgetQueryProvider<T> extends Function<T, Select<? extends Record>> {

	Set<String> getSupportedSorting();
}
