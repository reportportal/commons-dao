package com.epam.ta.reportportal.dao.widget;

import org.jooq.Record;
import org.jooq.Select;

import java.util.function.Function;

/**
 * @author <a href="mailto:ivan_budayeu@epam.com">Ivan Budayeu</a>
 */
public interface WidgetContentProvider<R> extends Function<Select<? extends Record>, R> {
}
