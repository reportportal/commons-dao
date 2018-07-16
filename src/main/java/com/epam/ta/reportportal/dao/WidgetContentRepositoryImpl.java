package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.commons.querygen.Filter;
import com.epam.ta.reportportal.commons.querygen.QueryBuilder;
import com.epam.ta.reportportal.entity.widget.content.StatisticsContent;
import org.jooq.DSLContext;
import org.jooq.Select;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import static com.epam.ta.reportportal.jooq.Tables.*;
import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.name;

/**
 * Repository that contains queries of content loading for widgets.
 *
 * @author Pavel Bortnik
 */
@Repository
public class WidgetContentRepositoryImpl implements WidgetContentRepository {

	@Autowired
	private DSLContext dsl;

	@Override
	public List<StatisticsContent> overallStatisticsContent(Filter filter, Map<String, List<String>> contentFields, boolean latest) {
		Select commonSelect;
		if (latest) {
			commonSelect = dsl.select(field(name("launches", "id")).cast(Long.class))
					.distinctOn(field(name("launches", "launch_name")).cast(String.class))
					.from(name("launches")).orderBy(
							field(name("launches", "launch_name")).cast(String.class),
							field(name("launches", "number")).cast(Integer.class).desc()
					);
		} else {
			commonSelect = dsl.select(field(name("launches", "id")).cast(Long.class)).from(name("launches"));
		}

		return dsl.with("launches")
				.as(QueryBuilder.newBuilder(filter).build())
				.select(EXECUTION_STATISTICS.ES_STATUS.as("field"), DSL.sumDistinct(EXECUTION_STATISTICS.ES_COUNTER))
				.from(LAUNCH)
				.join(EXECUTION_STATISTICS)
				.on(LAUNCH.ID.eq(EXECUTION_STATISTICS.LAUNCH_ID))
				.where(EXECUTION_STATISTICS.LAUNCH_ID.in(commonSelect))
				.and(EXECUTION_STATISTICS.ES_STATUS.in(contentFields.get("executions")))
				.groupBy(EXECUTION_STATISTICS.ES_STATUS)
				.unionAll(dsl.select(ISSUE_TYPE.LOCATOR.as("field"), DSL.sumDistinct(ISSUE_STATISTICS.IS_COUNTER))
						.from(LAUNCH)
						.join(ISSUE_STATISTICS)
						.on(LAUNCH.ID.eq(ISSUE_STATISTICS.LAUNCH_ID))
						.join(ISSUE_TYPE)
						.on(ISSUE_STATISTICS.ISSUE_TYPE_ID.eq(ISSUE_TYPE.ID))
						.where(ISSUE_STATISTICS.LAUNCH_ID.in(commonSelect))
						.and(ISSUE_TYPE.LOCATOR.in(contentFields.get("defects")))
						.groupBy(ISSUE_TYPE.LOCATOR))
				.fetchInto(StatisticsContent.class);
	}
}
