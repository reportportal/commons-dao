package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.commons.querygen.Filter;
import com.epam.ta.reportportal.commons.querygen.QueryBuilder;
import com.epam.ta.reportportal.entity.widget.content.MostFailedContent;
import com.epam.ta.reportportal.entity.widget.content.StatisticsContent;
import com.epam.ta.reportportal.jooq.enums.JTestItemTypeEnum;
import org.jooq.DSLContext;
import org.jooq.Select;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import static com.epam.ta.reportportal.dao.WidgetContentRepositoryConstants.*;
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
			commonSelect = dsl.select(field(name(LAUNCHES, "id")).cast(Long.class))
					.distinctOn(field(name(LAUNCHES, "launch_name")).cast(String.class))
					.from(name(LAUNCHES))
					.orderBy(
							field(name(LAUNCHES, "launch_name")).cast(String.class),
							field(name(LAUNCHES, "number")).cast(Integer.class).desc()
					);
		} else {
			commonSelect = dsl.select(field(name(LAUNCHES, "id")).cast(Long.class)).from(name(LAUNCHES));
		}

		return dsl.with(LAUNCHES)
				.as(QueryBuilder.newBuilder(filter).build())
				.select(EXECUTION_STATISTICS.ES_STATUS.as("field"), DSL.sumDistinct(EXECUTION_STATISTICS.ES_COUNTER))
				.from(LAUNCH)
				.join(EXECUTION_STATISTICS)
				.on(LAUNCH.ID.eq(EXECUTION_STATISTICS.LAUNCH_ID))
				.where(EXECUTION_STATISTICS.LAUNCH_ID.in(commonSelect))
				.and(EXECUTION_STATISTICS.ES_STATUS.in(contentFields.get(EXECUTIONS_KEY)))
				.groupBy(EXECUTION_STATISTICS.ES_STATUS)
				.unionAll(dsl.select(ISSUE_TYPE.LOCATOR.as("field"), DSL.sumDistinct(ISSUE_STATISTICS.IS_COUNTER))
						.from(LAUNCH)
						.join(ISSUE_STATISTICS)
						.on(LAUNCH.ID.eq(ISSUE_STATISTICS.LAUNCH_ID))
						.join(ISSUE_TYPE)
						.on(ISSUE_STATISTICS.ISSUE_TYPE_ID.eq(ISSUE_TYPE.ID))
						.where(ISSUE_STATISTICS.LAUNCH_ID.in(commonSelect))
						.and(ISSUE_TYPE.LOCATOR.in(contentFields.get(DEFECTS_KEY)))
						.groupBy(ISSUE_TYPE.LOCATOR))
				.fetchInto(StatisticsContent.class);
	}

	@Override
	public List<MostFailedContent> mostFailedByExecutionCriteria(String launchName, String criteria) {
		return dsl.with(HISTORY)
				.as(dsl.select(TEST_ITEM.UNIQUE_ID,
						TEST_ITEM.NAME,
						DSL.arrayAgg(DSL.when(TEST_ITEM_RESULTS.STATUS.eq(DSL.cast(criteria.toUpperCase(), TEST_ITEM_RESULTS.STATUS)),
								"true"
						)
								.otherwise("false"))
								.orderBy(LAUNCH.NUMBER.asc())
								.as(STATUS_HISTORY),
						DSL.arrayAgg(TEST_ITEM.START_TIME).orderBy(LAUNCH.NUMBER.asc()).as(START_TIME_HISTORY),
						DSL.sum(DSL.when(TEST_ITEM_RESULTS.STATUS.eq(DSL.cast(criteria.toUpperCase(), TEST_ITEM_RESULTS.STATUS)), 1)
								.otherwise(0))
								.as(CRITERIA),
						DSL.count(TEST_ITEM_RESULTS.STATUS).as(TOTAL)
				)
						.from(LAUNCH)
						.join(TEST_ITEM_STRUCTURE)
						.on(LAUNCH.ID.eq(TEST_ITEM_STRUCTURE.LAUNCH_ID))
						.join(TEST_ITEM_RESULTS)
						.on(TEST_ITEM_STRUCTURE.STRUCTURE_ID.eq(TEST_ITEM_RESULTS.RESULT_ID))
						.join(TEST_ITEM)
						.on(TEST_ITEM_STRUCTURE.STRUCTURE_ID.eq(TEST_ITEM.ITEM_ID))
						.where(TEST_ITEM.TYPE.eq(JTestItemTypeEnum.STEP))
						.and(LAUNCH.NAME.eq(launchName))
						.groupBy(TEST_ITEM.UNIQUE_ID, TEST_ITEM.NAME))
				.select()
				.from(DSL.table(DSL.name(HISTORY)))
				.orderBy(DSL.field(DSL.name(CRITERIA)).desc(), DSL.field(DSL.name(TOTAL)).asc())
				.fetchInto(MostFailedContent.class);
	}

	@Override
	public List<MostFailedContent> mostFailedByDefectCriteria(String launchName, String criteria) {
		return dsl.with(HISTORY)
				.as(dsl.select(TEST_ITEM.UNIQUE_ID,
						TEST_ITEM.NAME,
						DSL.arrayAgg(DSL.when(ISSUE_GROUP.ISSUE_GROUP_.eq(DSL.cast(criteria.toUpperCase(), ISSUE_GROUP.ISSUE_GROUP_)),
								"true"
						)
								.otherwise("false"))
								.orderBy(LAUNCH.NUMBER.asc())
								.as(STATUS_HISTORY),
						DSL.arrayAgg(TEST_ITEM.START_TIME).orderBy(LAUNCH.NUMBER.asc()).as(START_TIME_HISTORY),
						DSL.sum(DSL.when(ISSUE_GROUP.ISSUE_GROUP_.eq(DSL.cast(criteria.toUpperCase(), ISSUE_GROUP.ISSUE_GROUP_)), 1)
								.otherwise(0))
								.as(CRITERIA),
						DSL.count(TEST_ITEM_RESULTS.RESULT_ID).as(TOTAL)
				)
						.from(LAUNCH)
						.join(TEST_ITEM_STRUCTURE)
						.on(LAUNCH.ID.eq(TEST_ITEM_STRUCTURE.LAUNCH_ID))
						.join(TEST_ITEM_RESULTS)
						.on(TEST_ITEM_STRUCTURE.STRUCTURE_ID.eq(TEST_ITEM_RESULTS.RESULT_ID))
						.join(TEST_ITEM)
						.on(TEST_ITEM_STRUCTURE.STRUCTURE_ID.eq(TEST_ITEM.ITEM_ID))
						.leftJoin(ISSUE)
						.on(TEST_ITEM_RESULTS.RESULT_ID.eq(ISSUE.ISSUE_ID))
						.leftJoin(ISSUE_TYPE)
						.on(ISSUE.ISSUE_TYPE.eq(ISSUE_TYPE.ID))
						.leftJoin(ISSUE_GROUP)
						.on(ISSUE_TYPE.ISSUE_GROUP_ID.eq(ISSUE_GROUP.ISSUE_GROUP_ID))
						.where(TEST_ITEM.TYPE.eq(JTestItemTypeEnum.STEP))
						.and(LAUNCH.NAME.eq(launchName))
						.groupBy(TEST_ITEM.UNIQUE_ID, TEST_ITEM.NAME))
				.select()
				.from(DSL.table(DSL.name(HISTORY)))
				.orderBy(DSL.field(DSL.name(CRITERIA)).desc(), DSL.field(DSL.name(TOTAL)).asc())
				.fetchInto(MostFailedContent.class);
	}
}
