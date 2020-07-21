package com.epam.ta.reportportal.dao.widget.healthcheck.content.provider;

import com.epam.ta.reportportal.dao.widget.WidgetContentProvider;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

import static com.epam.ta.reportportal.dao.util.WidgetContentUtil.COMPONENT_HEALTH_CHECK_TABLE_COLUMN_FETCHER;

/**
 * @author <a href="mailto:ivan_budayeu@epam.com">Ivan Budayeu</a>
 */
@Component
public class HealthCheckTableColumnProvider implements WidgetContentProvider<Map<String, List<String>>> {

	private final DSLContext dslContext;

	@Autowired
	public HealthCheckTableColumnProvider(DSLContext dslContext) {
		this.dslContext = dslContext;
	}

	@Override
	public Map<String, List<String>> apply(Select<? extends Record> records) {
		return COMPONENT_HEALTH_CHECK_TABLE_COLUMN_FETCHER.apply(dslContext.fetch(records));
	}
}
