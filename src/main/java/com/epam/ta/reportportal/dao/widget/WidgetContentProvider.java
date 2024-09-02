package com.epam.ta.reportportal.dao.widget;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.jooq.Record;
import org.jooq.Select;

/**
 * @author <a href="mailto:ivan_budayeu@epam.com">Ivan Budayeu</a>
 */
public interface WidgetContentProvider<U, R> extends BiFunction<Select<? extends Record>, U, R> {

}
