/*
 * Copyright (C) 2018 EPAM Systems
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.commons.querygen.CriteriaHolder;
import com.epam.ta.reportportal.commons.querygen.Filter;
import com.epam.ta.reportportal.commons.querygen.QueryBuilder;
import com.epam.ta.reportportal.commons.validation.Suppliers;
import com.epam.ta.reportportal.dao.util.JooqFieldNameTransformer;
import com.epam.ta.reportportal.entity.widget.content.*;
import com.epam.ta.reportportal.exception.ReportPortalException;
import com.epam.ta.reportportal.jooq.enums.JTestItemTypeEnum;
import com.epam.ta.reportportal.ws.model.ErrorType;
import com.google.common.collect.Lists;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.epam.ta.reportportal.commons.querygen.QueryBuilder.STATISTICS_KEY;
import static com.epam.ta.reportportal.dao.constant.WidgetContentRepositoryConstants.*;
import static com.epam.ta.reportportal.dao.util.JooqFieldNameTransformer.fieldName;
import static com.epam.ta.reportportal.dao.util.WidgetContentUtil.*;
import static com.epam.ta.reportportal.jooq.Tables.*;
import static com.epam.ta.reportportal.jooq.tables.JActivity.ACTIVITY;
import static com.epam.ta.reportportal.jooq.tables.JIssue.ISSUE;
import static com.epam.ta.reportportal.jooq.tables.JIssueTicket.ISSUE_TICKET;
import static com.epam.ta.reportportal.jooq.tables.JLaunch.LAUNCH;
import static com.epam.ta.reportportal.jooq.tables.JProject.PROJECT;
import static com.epam.ta.reportportal.jooq.tables.JTestItem.TEST_ITEM;
import static com.epam.ta.reportportal.jooq.tables.JTestItemResults.TEST_ITEM_RESULTS;
import static com.epam.ta.reportportal.jooq.tables.JTicket.TICKET;
import static com.epam.ta.reportportal.jooq.tables.JUsers.USERS;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.*;
import static org.jooq.impl.DSL.*;

/**
 * Repository that contains queries of content loading for widgets.
 *
 * @author Pavel Bortnik
 */
@Repository
public class WidgetContentRepositoryImpl implements WidgetContentRepository {

	@Autowired
	private DSLContext dsl;

	private static final List<JTestItemTypeEnum> HAS_METHOD_OR_CLASS = Arrays.stream(JTestItemTypeEnum.values()).filter(it -> {
		String name = it.name();
		return name.contains("METHOD") || name.contains("CLASS");
	}).collect(Collectors.toList());

	@Override
	public OverallStatisticsContent overallStatisticsContent(Filter filter, Sort sort, List<String> contentFields, boolean latest,
			int limit) {

		return OVERALL_STATISTICS_FETCHER.apply(dsl.with(LAUNCHES)
				.as(QueryBuilder.newBuilder(filter).with(sort).with(limit).with(latest).build())
				.select(STATISTICS_FIELD.NAME, sum(STATISTICS.S_COUNTER).as(SUM))
				.from(STATISTICS)
				.join(LAUNCHES)
				.on(fieldName(LAUNCHES, ID).cast(Long.class).eq(STATISTICS.LAUNCH_ID))
				.join(STATISTICS_FIELD)
				.on(STATISTICS.STATISTICS_FIELD_ID.eq(STATISTICS_FIELD.SF_ID))
				.groupBy(STATISTICS_FIELD.NAME)
				.fetch());
	}

	@Override
	public List<CriteriaHistoryItem> topItemsByCriteria(Filter filter, String criteria, int limit, boolean includeMethods) {
		return dsl.with(HISTORY)
				.as(dsl.with(LAUNCHES)
						.as(QueryBuilder.newBuilder(filter).build())
						.select(TEST_ITEM.UNIQUE_ID,
								TEST_ITEM.NAME,
								DSL.arrayAgg(DSL.when(STATISTICS_FIELD.NAME.eq(criteria), "true").otherwise("false"))
										.orderBy(LAUNCH.NUMBER.asc())
										.as(STATUS_HISTORY),
								DSL.arrayAgg(TEST_ITEM.START_TIME).orderBy(LAUNCH.NUMBER.asc()).as(START_TIME_HISTORY),
								DSL.sum(DSL.when(STATISTICS_FIELD.NAME.eq(criteria), 1).otherwise(ZERO_QUERY_VALUE)).as(CRITERIA),
								DSL.count(TEST_ITEM_RESULTS.STATUS).as(TOTAL)
						)
						.from(LAUNCH)
						.join(TEST_ITEM)
						.on(LAUNCH.ID.eq(TEST_ITEM.LAUNCH_ID))
						.join(TEST_ITEM_RESULTS)
						.on(TEST_ITEM.ITEM_ID.eq(TEST_ITEM_RESULTS.RESULT_ID))
						.join(STATISTICS)
						.on(TEST_ITEM.ITEM_ID.eq(STATISTICS.ITEM_ID))
						.join(STATISTICS_FIELD)
						.on(STATISTICS.STATISTICS_FIELD_ID.eq(STATISTICS_FIELD.SF_ID))
						.where(TEST_ITEM.TYPE.in(includeMethods ?
								Lists.newArrayList(HAS_METHOD_OR_CLASS, JTestItemTypeEnum.STEP) :
								Collections.singletonList(JTestItemTypeEnum.STEP)))
						.and(STATISTICS_FIELD.NAME.eq(criteria))
						.and(TEST_ITEM.LAUNCH_ID.in(dsl.select(field(name(LAUNCHES, ID)).cast(Long.class)).from(name(LAUNCHES))))
						.groupBy(TEST_ITEM.UNIQUE_ID, TEST_ITEM.NAME))
				.select()
				.from(DSL.table(DSL.name(HISTORY)))
				.where(DSL.field(DSL.name(CRITERIA)).greaterThan(ZERO_QUERY_VALUE))
				.orderBy(DSL.field(DSL.name(CRITERIA)).desc(), DSL.field(DSL.name(TOTAL)).asc())
				.limit(limit)
				.fetchInto(CriteriaHistoryItem.class);
	}

	@Override
	public List<FlakyCasesTableContent> flakyCasesStatistics(Filter filter, int limit) {

		Select commonSelect = dsl.select(field(name(LAUNCHES, ID)).cast(Long.class))
				.from(name(LAUNCHES))
				.orderBy(field(name(LAUNCHES, NUMBER)).desc())
				.limit(limit);

		return dsl.select(field(name(FLAKY_TABLE_RESULTS, TEST_ITEM.UNIQUE_ID.getName())).as(UNIQUE_ID),
				field(name(FLAKY_TABLE_RESULTS, TEST_ITEM.NAME.getName())).as(ITEM_NAME),
				DSL.arrayAgg(field(name(FLAKY_TABLE_RESULTS, TEST_ITEM_RESULTS.STATUS.getName()))).as(STATUSES),
				sum(field(name(FLAKY_TABLE_RESULTS, SWITCH_FLAG)).cast(Long.class)).as(FLAKY_COUNT),
				sum(field(name(FLAKY_TABLE_RESULTS, TOTAL)).cast(Long.class)).as(TOTAL)
		)
				.from(dsl.with(LAUNCHES)
						.as(QueryBuilder.newBuilder(filter).with(LAUNCH.NUMBER.desc()).build())
						.select(TEST_ITEM.UNIQUE_ID,
								TEST_ITEM.NAME,
								TEST_ITEM_RESULTS.STATUS,
								when(TEST_ITEM_RESULTS.STATUS.notEqual(lag(TEST_ITEM_RESULTS.STATUS).over(orderBy(TEST_ITEM.UNIQUE_ID,
										TEST_ITEM.ITEM_ID
										)))
												.and(TEST_ITEM.UNIQUE_ID.equal(lag(TEST_ITEM.UNIQUE_ID).over(orderBy(TEST_ITEM.UNIQUE_ID,
														TEST_ITEM.ITEM_ID
												)))),
										1
								).otherwise(ZERO_QUERY_VALUE).as(SWITCH_FLAG),
								count(TEST_ITEM_RESULTS.STATUS).as(TOTAL)
						)
						.from(LAUNCH)
						.join(TEST_ITEM)
						.on(LAUNCH.ID.eq(TEST_ITEM.LAUNCH_ID))
						.join(TEST_ITEM_RESULTS)
						.on(TEST_ITEM.ITEM_ID.eq(TEST_ITEM_RESULTS.RESULT_ID))
						.where(LAUNCH.ID.in(commonSelect))
						.and(TEST_ITEM.TYPE.eq(JTestItemTypeEnum.STEP))
						.groupBy(TEST_ITEM.ITEM_ID, TEST_ITEM_RESULTS.STATUS, TEST_ITEM.UNIQUE_ID, TEST_ITEM.NAME)
						.asTable(FLAKY_TABLE_RESULTS))
				.groupBy(field(name(FLAKY_TABLE_RESULTS, TEST_ITEM.UNIQUE_ID.getName())),
						field(name(FLAKY_TABLE_RESULTS, TEST_ITEM.NAME.getName()))
				)
				.orderBy(fieldName(FLAKY_COUNT).desc(), fieldName(TOTAL).asc(), fieldName(UNIQUE_ID))
				.limit(20)
				.fetchInto(FlakyCasesTableContent.class);
	}

	@Override
	public List<LaunchesStatisticsContent> launchStatistics(Filter filter, List<String> contentFields, @Nullable Sort sort, int limit) {

		return LAUNCHES_STATISTICS_FETCHER.apply(dsl.with(LAUNCHES)
				.as(QueryBuilder.newBuilder(filter).with(sort).with(limit).build())
				.select(LAUNCH.ID, LAUNCH.NUMBER, LAUNCH.START_TIME, LAUNCH.NAME, STATISTICS_FIELD.NAME, STATISTICS.S_COUNTER)
				.from(LAUNCH)
				.join(LAUNCHES)
				.on(LAUNCH.ID.eq(fieldName(LAUNCHES, ID).cast(Long.class)))
				.leftJoin(STATISTICS)
				.on(LAUNCH.ID.eq(STATISTICS.LAUNCH_ID))
				.join(STATISTICS_FIELD)
				.on(STATISTICS.STATISTICS_FIELD_ID.eq(STATISTICS_FIELD.SF_ID))
				.where(STATISTICS_FIELD.NAME.in(contentFields))
				.orderBy(StreamSupport.stream(sort.spliterator(), false)
						.map(order -> field(order.getProperty()).sort(order.getDirection().isDescending() ? SortOrder.DESC : SortOrder.ASC))
						.collect(Collectors.toList()))
				.fetch());
	}

	@Override
	public List<InvestigatedStatisticsResult> investigatedStatistics(Filter filter, Sort sort, int limit) {

		List<Field<?>> groupingFields = StreamSupport.stream(sort.spliterator(), false).map(s -> field(s.getProperty())).collect(toList());

		Collections.addAll(groupingFields, LAUNCH.ID, LAUNCH.NUMBER, LAUNCH.START_TIME, LAUNCH.NAME);

		return dsl.with(LAUNCHES)
				.as(QueryBuilder.newBuilder(filter).with(sort).with(limit).build())
				.select(LAUNCH.ID,
						LAUNCH.NUMBER,
						LAUNCH.START_TIME,
						LAUNCH.NAME,
						round(val(PERCENTAGE_MULTIPLIER).mul(dsl.select(sum(STATISTICS.S_COUNTER))
								.from(STATISTICS)
								.join(STATISTICS_FIELD)
								.onKey()
								.where(STATISTICS_FIELD.NAME.eq(DEFECTS_TO_INVESTIGATE_TOTAL).and(STATISTICS.LAUNCH_ID.eq(LAUNCH.ID)))
								.asField()
								.cast(Double.class))
								.div(nullif(dsl.select(sum(STATISTICS.S_COUNTER))
										.from(STATISTICS)
										.join(STATISTICS_FIELD)
										.onKey()
										.where(STATISTICS_FIELD.NAME.in(DEFECTS_AUTOMATION_BUG_TOTAL,
												DEFECTS_NO_DEFECT_TOTAL,
												DEFECTS_TO_INVESTIGATE_TOTAL,
												DEFECTS_PRODUCT_BUG_TOTAL,
												DEFECTS_SYSTEM_ISSUE_TOTAL
										).and(STATISTICS.LAUNCH_ID.eq(LAUNCH.ID)))
										.asField(), 0)), 2).as(TO_INVESTIGATE)
				)
				.from(LAUNCH)
				.join(LAUNCHES)
				.on(LAUNCH.ID.eq(fieldName(LAUNCHES, ID).cast(Long.class)))
				.leftJoin(STATISTICS)
				.on(LAUNCH.ID.eq(STATISTICS.LAUNCH_ID))
				.join(STATISTICS_FIELD)
				.on(STATISTICS.STATISTICS_FIELD_ID.eq(STATISTICS_FIELD.SF_ID))
				.groupBy(groupingFields)
				.orderBy(StreamSupport.stream(sort.spliterator(), false)
						.map(order -> field(order.getProperty()).sort(order.getDirection().isDescending() ? SortOrder.DESC : SortOrder.ASC))
						.collect(Collectors.toList()))
				.fetch(INVESTIGATED_STATISTICS_CONTENT_RECORD_MAPPER);

	}

	@Override
	public PassingRateStatisticsResult passingRatePerLaunchStatistics(Filter filter, Sort sort, int limit) {

		List<Field<?>> groupingFields = StreamSupport.stream(sort.spliterator(), false).map(s -> field(s.getProperty())).collect(toList());

		return buildPassingRateSelect(filter, sort, limit).groupBy(groupingFields)
				.orderBy(StreamSupport.stream(sort.spliterator(), false)
						.map(order -> field(order.getProperty()).sort(order.getDirection().isDescending() ? SortOrder.DESC : SortOrder.ASC))
						.collect(Collectors.toList()))
				.fetchInto(PassingRateStatisticsResult.class)
				.stream()
				.findFirst()
				.orElseThrow(() -> new ReportPortalException("No results for filter were found"));
	}

	@Override
	public PassingRateStatisticsResult summaryPassingRateStatistics(Filter filter, Sort sort, int limit) {
		return buildPassingRateSelect(filter, sort, limit).fetchInto(PassingRateStatisticsResult.class)
				.stream()
				.findFirst()
				.orElseThrow(() -> new ReportPortalException("No results for filter were found"));
	}

	@Override
	public List<CasesTrendContent> casesTrendStatistics(Filter filter, String contentField, Sort sort, int limit) {

		List<? extends SortField<?>> deltaCounterSort = ofNullable(sort).map(s -> StreamSupport.stream(s.spliterator(), false)
				.map(order -> field(order.getProperty()).sort(order.getDirection().isDescending() ? SortOrder.DESC : SortOrder.ASC))
				.collect(Collectors.toList())).orElseGet(Collections::emptyList);

		return dsl.with(LAUNCHES)
				.as(QueryBuilder.newBuilder(filter).with(sort).with(limit).build())
				.select(LAUNCH.ID,
						LAUNCH.NUMBER,
						LAUNCH.START_TIME,
						LAUNCH.NAME,
						STATISTICS.S_COUNTER.as(contentField),
						STATISTICS.S_COUNTER.sub(lag(STATISTICS.S_COUNTER).over().orderBy(deltaCounterSort)).as(DELTA)
				)
				.from(LAUNCH)
				.join(LAUNCHES)
				.on(LAUNCH.ID.eq(fieldName(LAUNCHES, ID).cast(Long.class)))
				.leftJoin(STATISTICS)
				.on(LAUNCH.ID.eq(STATISTICS.LAUNCH_ID))
				.join(STATISTICS_FIELD)
				.on(STATISTICS.STATISTICS_FIELD_ID.eq(STATISTICS_FIELD.SF_ID))
				.where(STATISTICS_FIELD.NAME.eq(contentField))
				.orderBy(deltaCounterSort)
				.fetchInto(CasesTrendContent.class);
	}

	@Override
	public List<LaunchesStatisticsContent> bugTrendStatistics(Filter filter, List<String> contentFields, Sort sort, int limit) {

		return BUG_TREND_STATISTICS_FETCHER.apply(dsl.with(LAUNCHES)
				.as(QueryBuilder.newBuilder(filter).with(sort).with(limit).build())
				.select(LAUNCH.ID, LAUNCH.NAME, LAUNCH.NUMBER, LAUNCH.START_TIME, STATISTICS_FIELD.NAME, STATISTICS.S_COUNTER)
				.from(LAUNCH)
				.join(LAUNCHES)
				.on(LAUNCH.ID.eq(fieldName(LAUNCHES, ID).cast(Long.class)))
				.leftJoin(STATISTICS)
				.on(LAUNCH.ID.eq(STATISTICS.LAUNCH_ID))
				.join(STATISTICS_FIELD)
				.on(STATISTICS.STATISTICS_FIELD_ID.eq(STATISTICS_FIELD.SF_ID))
				.where(STATISTICS_FIELD.NAME.in(contentFields))
				.fetch());

	}

	@Override
	public List<LaunchesStatisticsContent> launchesComparisonStatistics(Filter filter, List<String> contentFields, Sort sort, int limit) {

		List<String> executionStatisticsFields = contentFields.stream().filter(cf -> cf.contains(EXECUTIONS_KEY)).collect(toList());
		List<String> defectStatisticsFields = contentFields.stream().filter(cf -> cf.contains(DEFECTS_KEY)).collect(toList());

		return LAUNCHES_STATISTICS_FETCHER.apply(dsl.with(LAUNCHES)
				.as(QueryBuilder.newBuilder(filter).with(sort).with(limit).build())
				.select(LAUNCH.ID,
						LAUNCH.NAME,
						LAUNCH.NUMBER,
						LAUNCH.START_TIME,
						STATISTICS_FIELD.NAME,
						round(val(PERCENTAGE_MULTIPLIER).mul(STATISTICS.S_COUNTER)
										.div(nullif(DSL.select(DSL.sum(STATISTICS.S_COUNTER))
												.from(STATISTICS)
												.join(STATISTICS_FIELD)
												.on(STATISTICS.STATISTICS_FIELD_ID.eq(STATISTICS_FIELD.SF_ID))
												.where(STATISTICS.LAUNCH_ID.eq(LAUNCH.ID))
												.and(STATISTICS_FIELD.NAME.in(executionStatisticsFields)), 0).cast(Double.class)),
								2
						).as("s_counter")
				)
				.from(LAUNCH)
				.join(LAUNCHES)
				.on(LAUNCH.ID.eq(fieldName(LAUNCHES, ID).cast(Long.class)))
				.leftJoin(STATISTICS)
				.on(LAUNCH.ID.eq(STATISTICS.LAUNCH_ID))
				.join(STATISTICS_FIELD)
				.on(STATISTICS.STATISTICS_FIELD_ID.eq(STATISTICS_FIELD.SF_ID))
				.where(STATISTICS_FIELD.NAME.in(executionStatisticsFields))
				.orderBy(StreamSupport.stream(sort.spliterator(), false)
						.map(order -> field(order.getProperty()).sort(order.getDirection().isDescending() ? SortOrder.DESC : SortOrder.ASC))
						.collect(Collectors.toList()))
				.unionAll(DSL.select(LAUNCH.ID,
						LAUNCH.NAME,
						LAUNCH.NUMBER,
						LAUNCH.START_TIME,
						STATISTICS_FIELD.NAME,
						round(val(PERCENTAGE_MULTIPLIER).mul(STATISTICS.S_COUNTER)
								.div(nullif(DSL.select(DSL.sum(STATISTICS.S_COUNTER))
										.from(STATISTICS)
										.join(STATISTICS_FIELD)
										.on(STATISTICS.STATISTICS_FIELD_ID.eq(STATISTICS_FIELD.SF_ID))
										.where(STATISTICS.LAUNCH_ID.eq(LAUNCH.ID))
										.and(STATISTICS_FIELD.NAME.in(defectStatisticsFields)), 0).cast(Double.class)), 2)
				)
						.from(LAUNCH)
						.join(LAUNCHES)
						.on(LAUNCH.ID.eq(fieldName(LAUNCHES, ID).cast(Long.class)))
						.leftJoin(STATISTICS)
						.on(LAUNCH.ID.eq(STATISTICS.LAUNCH_ID))
						.join(STATISTICS_FIELD)
						.on(STATISTICS.STATISTICS_FIELD_ID.eq(STATISTICS_FIELD.SF_ID))
						.where(STATISTICS_FIELD.NAME.in(defectStatisticsFields))
						.orderBy(StreamSupport.stream(sort.spliterator(), false)
								.map(order -> field(order.getProperty()).sort(order.getDirection().isDescending() ?
										SortOrder.DESC :
										SortOrder.ASC))
								.collect(Collectors.toList())))
				.fetch());

	}

	@Override
	public List<LaunchesDurationContent> launchesDurationStatistics(Filter filter, Sort sort, boolean isLatest, int limit) {

		return dsl.with(LAUNCHES)
				.as(QueryBuilder.newBuilder(filter).with(isLatest).with(sort).with(limit).build())
				.select(LAUNCH.ID,
						LAUNCH.NAME,
						LAUNCH.NUMBER,
						LAUNCH.STATUS,
						LAUNCH.START_TIME,
						LAUNCH.END_TIME,
						timestampDiff(LAUNCH.END_TIME, LAUNCH.START_TIME).as(DURATION)
				)
				.from(LAUNCH)
				.join(LAUNCHES)
				.on(LAUNCH.ID.eq(fieldName(LAUNCHES, ID).cast(Long.class)))
				.orderBy(StreamSupport.stream(sort.spliterator(), false)
						.map(order -> field(order.getProperty()).sort(order.getDirection().isDescending() ? SortOrder.DESC : SortOrder.ASC))
						.collect(Collectors.toList()))
				.fetchInto(LaunchesDurationContent.class);
	}

	@Override
	public List<NotPassedCasesContent> notPassedCasesStatistics(Filter filter, Sort sort, int limit) {

		return dsl.with(LAUNCHES)
				.as(QueryBuilder.newBuilder(filter).with(sort).with(limit).build())
				.select(LAUNCH.ID,
						LAUNCH.NAME,
						LAUNCH.NUMBER,
						LAUNCH.START_TIME,
						STATISTICS.S_COUNTER,
						coalesce(round(val(PERCENTAGE_MULTIPLIER).mul(DSL.select(DSL.sum(STATISTICS.S_COUNTER))
								.from(STATISTICS)
								.join(STATISTICS_FIELD)
								.on(STATISTICS.STATISTICS_FIELD_ID.eq(STATISTICS_FIELD.SF_ID))
								.where(STATISTICS_FIELD.NAME.in(EXECUTIONS_SKIPPED, EXECUTIONS_FAILED))
								.and(STATISTICS.LAUNCH_ID.eq(LAUNCH.ID))
								.asField()
								.cast(Double.class)).div(nullif(STATISTICS.S_COUNTER, 0).cast(Double.class)), 2), 0).as(PERCENTAGE)
				)
				.from(LAUNCH)
				.join(LAUNCHES)
				.on(LAUNCH.ID.eq(fieldName(LAUNCHES, ID).cast(Long.class)))
				.leftJoin(STATISTICS)
				.on(LAUNCH.ID.eq(STATISTICS.LAUNCH_ID))
				.join(STATISTICS_FIELD)
				.on(STATISTICS.STATISTICS_FIELD_ID.eq(STATISTICS_FIELD.SF_ID))
				.where(STATISTICS_FIELD.NAME.in(EXECUTIONS_TOTAL))
				.fetch(NOT_PASSED_CASES_CONTENT_RECORD_MAPPER);
	}

	@Override
	public List<LaunchesTableContent> launchesTableStatistics(Filter filter, List<String> contentFields, Sort sort, int limit) {

		Map<String, String> criteria = filter.getTarget()
				.getCriteriaHolders()
				.stream()
				.collect(Collectors.toMap(CriteriaHolder::getFilterCriteria, CriteriaHolder::getQueryCriteria));

		boolean remove = contentFields.remove("tags");

		List<Field<?>> selectFields = contentFields.stream()
				.filter(cf -> !cf.startsWith(STATISTICS_KEY))
				.map(cf -> field(ofNullable(criteria.get(cf)).orElseThrow(() -> new ReportPortalException(Suppliers.formattedSupplier(
						"Unknown table field - '{}'",
						cf
				).get()))))
				.collect(Collectors.toList());

		Collections.addAll(selectFields, LAUNCH.ID, fieldName(STATISTICS_TABLE, STATISTICS_COUNTER), fieldName(STATISTICS_TABLE, SF_NAME));

		List<SortField<?>> orderFields = StreamSupport.stream(sort.spliterator(), false)
				.map(order -> field(order.getProperty()).sort(order.getDirection().isDescending() ? SortOrder.DESC : SortOrder.ASC))
				.collect(Collectors.toList());

		List<String> statisticsFields = contentFields.stream().filter(cf -> cf.startsWith(STATISTICS_KEY)).collect(toList());

		return LAUNCHES_TABLE_FETCHER.apply(dsl.with(LAUNCHES)
				.as(QueryBuilder.newBuilder(filter).with(sort).with(limit).build())
				.select(selectFields)
				.from(LAUNCH)
				.join(LAUNCHES)
				.on(LAUNCH.ID.eq(fieldName(LAUNCHES, ID).cast(Long.class)))
				.leftJoin(DSL.select(STATISTICS.LAUNCH_ID, STATISTICS.S_COUNTER.as(STATISTICS_COUNTER), STATISTICS_FIELD.NAME.as(SF_NAME))
						.from(STATISTICS)
						.join(STATISTICS_FIELD)
						.on(STATISTICS.STATISTICS_FIELD_ID.eq(STATISTICS_FIELD.SF_ID))
						.where(STATISTICS_FIELD.NAME.in(statisticsFields))
						.asTable(STATISTICS_TABLE))
				.on(LAUNCH.ID.eq(fieldName(STATISTICS_TABLE, LAUNCH_ID).cast(Long.class)))
				.join(USERS)
				.on(LAUNCH.USER_ID.eq(USERS.ID))
				.orderBy(orderFields)
				.fetch(), contentFields);

	}

	@Override
	public List<ActivityContent> activityStatistics(Filter filter, Sort sort, int limit) {

		return dsl.with(ACTIVITIES)
				.as(QueryBuilder.newBuilder(filter).with(sort).with(limit).build())
				.select(ACTIVITY.ID.as(ID),
						ACTIVITY.ACTION.as(ACTION_TYPE),
						ACTIVITY.ENTITY.as(ENTITY),
						ACTIVITY.CREATION_DATE.as(LAST_MODIFIED),
						USERS.LOGIN.as(USER_LOGIN),
						PROJECT.NAME.as(PROJECT_NAME)
				)
				.from(ACTIVITY)
				.join(ACTIVITIES)
				.on(fieldName(ACTIVITIES, ID).cast(Long.class).eq(ACTIVITY.ID))
				.join(USERS)
				.on(ACTIVITY.USER_ID.eq(USERS.ID))
				.join(PROJECT)
				.on(ACTIVITY.PROJECT_ID.eq(PROJECT.ID))
				.fetchInto(ActivityContent.class);

	}

	@Override
	public Map<String, List<UniqueBugContent>> uniqueBugStatistics(Filter filter, Sort sort, boolean isLatest, int limit) {

		List<UniqueBugContent> uniqueBugContents = dsl.with(LAUNCHES)
				.as(QueryBuilder.newBuilder(filter).with(limit).with(sort).with(isLatest).build())
				.select(TICKET.TICKET_ID,
						TICKET.SUBMIT_DATE,
						TICKET.URL,
						TEST_ITEM.ITEM_ID,
						TEST_ITEM.NAME,
						TEST_ITEM.DESCRIPTION,
						TEST_ITEM.LAUNCH_ID,
						USERS.LOGIN
				)
				.from(TEST_ITEM)
				.join(LAUNCHES)
				.on(fieldName(LAUNCHES, ID).cast(Long.class).eq(TEST_ITEM.LAUNCH_ID))
				.join(TEST_ITEM_RESULTS)
				.on(TEST_ITEM.ITEM_ID.eq(TEST_ITEM_RESULTS.RESULT_ID))
				.leftJoin(ISSUE)
				.on(TEST_ITEM.ITEM_ID.eq(ISSUE.ISSUE_ID))
				.leftJoin(ISSUE_TICKET)
				.on(ISSUE.ISSUE_ID.eq(ISSUE_TICKET.ISSUE_ID))
				.join(TICKET)
				.on(ISSUE_TICKET.TICKET_ID.eq(TICKET.ID))
				.join(USERS)
				.on(TICKET.SUBMITTER_ID.eq(USERS.ID))
				.fetchInto(UniqueBugContent.class);

		return uniqueBugContents.stream().collect(groupingBy(UniqueBugContent::getTicketId, LinkedHashMap::new, toList()));
	}

	@Override
	public Map<String, List<CumulativeTrendChartContent>> cumulativeTrendStatistics(Filter filter, List<String> contentFields, Sort sort,
			String tagPrefix, int limit) {

		List<String> statisticsFields = contentFields.stream().filter(cf -> cf.startsWith(STATISTICS_KEY)).collect(toList());

		return CUMULATIVE_TREND_CHART_FETCHER.apply(dsl.with(LAUNCHES)
				.as(QueryBuilder.newBuilder(filter).with(sort).with(LAUNCHES_COUNT).build())
				.select(LAUNCH.ID,
						LAUNCH.NAME,
						LAUNCH.NUMBER,
						LAUNCH.START_TIME,
						ITEM_ATTRIBUTE.ID,
						ITEM_ATTRIBUTE.VALUE,
						fieldName(STATISTICS_TABLE, STATISTICS_COUNTER),
						fieldName(STATISTICS_TABLE, SF_NAME)
				)
				.from(LAUNCH)
				.join(LAUNCHES)
				.on(fieldName(LAUNCHES, ID).cast(Long.class).eq(LAUNCH.ID))
				.join(ITEM_ATTRIBUTE)
				.on(ITEM_ATTRIBUTE.LAUNCH_ID.eq(LAUNCH.ID))
				.leftJoin(DSL.select(STATISTICS.LAUNCH_ID, STATISTICS.S_COUNTER.as(STATISTICS_COUNTER), STATISTICS_FIELD.NAME.as(SF_NAME))
						.from(STATISTICS)
						.join(STATISTICS_FIELD)
						.on(STATISTICS.STATISTICS_FIELD_ID.eq(STATISTICS_FIELD.SF_ID))
						.where(STATISTICS_FIELD.NAME.in(statisticsFields))
						.asTable(STATISTICS_TABLE))
				.on(LAUNCH.ID.eq(fieldName(STATISTICS_TABLE, LAUNCH_ID).cast(Long.class)))
				.orderBy(ofNullable(sort).map(s -> StreamSupport.stream(s.spliterator(), false)
						.map(order -> field(name(order.getProperty())).sort(order.getDirection().isDescending() ?
								SortOrder.DESC :
								SortOrder.ASC))
						.collect(Collectors.toList())).orElseGet(Collections::emptyList))
				.fetch());
	}

	@Override
	public Map<String, List<ProductStatusStatisticsContent>> productStatusGroupedByFilterStatistics(Map<Filter, Sort> filterSortMapping,
			List<String> contentFields, List<String> tags, boolean isLatest, int limit) {

		Select<? extends Record> select = filterSortMapping.entrySet()
				.stream()
				.map(f -> (Select<? extends Record>) buildFilterGroupedQuery(f.getKey(),
						isLatest,
						f.getValue(),
						limit,
						contentFields,
						tags
				))
				.collect(Collectors.toList())
				.stream()
				.reduce((prev, curr) -> curr = prev.unionAll(curr))
				.orElseThrow(() -> new ReportPortalException(ErrorType.BAD_REQUEST_ERROR, "Query build for Product Status Widget failed"));

		Map<String, List<ProductStatusStatisticsContent>> productStatusContent = PRODUCT_STATUS_FILTER_GROUPED_FETCHER.apply(select.fetch());

		productStatusContent.put(TOTAL, countFilterTotalStatistics(productStatusContent));

		return productStatusContent;
	}

	@Override
	public List<ProductStatusStatisticsContent> productStatusGroupedByLaunchesStatistics(Filter filter, List<String> contentFields,
			List<String> tags, Sort sort, boolean isLatest, int limit) {

		List<ProductStatusStatisticsContent> productStatusStatisticsResult = PRODUCT_STATUS_LAUNCH_GROUPED_FETCHER.apply(
				buildLaunchGroupedQuery(filter, isLatest, sort, limit, contentFields, tags).fetch());

		productStatusStatisticsResult.add(countLaunchTotalStatistics(productStatusStatisticsResult));
		return productStatusStatisticsResult;
	}

	@Override
	public List<MostTimeConsumingTestCasesContent> mostTimeConsumingTestCasesStatistics(Filter filter) {
		return dsl.with(ITEMS)
				.as(QueryBuilder.newBuilder(filter).build())
				.select(TEST_ITEM.ITEM_ID.as(ID),
						TEST_ITEM.UNIQUE_ID,
						TEST_ITEM.NAME,
						TEST_ITEM.TYPE,
						TEST_ITEM.START_TIME,
						TEST_ITEM_RESULTS.END_TIME,
						TEST_ITEM_RESULTS.DURATION,
						TEST_ITEM_RESULTS.STATUS
				)
				.from(TEST_ITEM)
				.join(ITEMS)
				.on(fieldName(ITEMS, ID).cast(Long.class).eq(TEST_ITEM.ITEM_ID))
				.join(TEST_ITEM_RESULTS)
				.on(TEST_ITEM.ITEM_ID.eq(TEST_ITEM_RESULTS.RESULT_ID))
				.orderBy(fieldName(TEST_ITEM_RESULTS.DURATION).desc())
				.limit(20)
				.fetchInto(MostTimeConsumingTestCasesContent.class);
	}

	private List<SortField<Object>> buildSortFields(Sort sort) {
		return ofNullable(sort).map(s -> StreamSupport.stream(s.spliterator(), false)
				.map(order -> field(order.getProperty()).sort(order.getDirection().isDescending() ? SortOrder.DESC : SortOrder.ASC))
				.collect(Collectors.toList())).orElseGet(Collections::emptyList);
	}

	private SelectOnConditionStep<? extends Record> buildPassingRateSelect(Filter filter, Sort sort, int limit) {
		return dsl.with(LAUNCHES)
				.as(QueryBuilder.newBuilder(filter).with(sort).with(limit).build())
				.select(sum(when(STATISTICS_FIELD.NAME.eq(EXECUTIONS_PASSED), STATISTICS.S_COUNTER).otherwise(0)).as(PASSED),
						sum(when(STATISTICS_FIELD.NAME.eq(EXECUTIONS_TOTAL), STATISTICS.S_COUNTER).otherwise(0)).as(TOTAL)
				)
				.from(LAUNCH)
				.join(LAUNCHES)
				.on(LAUNCH.ID.eq(fieldName(LAUNCHES, ID).cast(Long.class)))
				.leftJoin(STATISTICS)
				.on(LAUNCH.ID.eq(STATISTICS.LAUNCH_ID))
				.join(STATISTICS_FIELD)
				.on(STATISTICS.STATISTICS_FIELD_ID.eq(STATISTICS_FIELD.SF_ID));
	}

	private SelectSeekStepN<? extends Record> buildFilterGroupedQuery(Filter filter, boolean isLatest, Sort sort, int limit,
			Collection<String> contentFields, Collection<String> tags) {

		List<Field<?>> fields = Lists.newArrayList(LAUNCH.ID,
				LAUNCH.NAME,
				LAUNCH.NUMBER,
				LAUNCH.START_TIME,
				LAUNCH.STATUS,
				fieldName(STATISTICS_TABLE, SF_NAME),
				fieldName(STATISTICS_TABLE, STATISTICS_COUNTER),
				round(val(PERCENTAGE_MULTIPLIER).mul(dsl.select(sum(STATISTICS.S_COUNTER))
						.from(STATISTICS)
						.join(STATISTICS_FIELD)
						.onKey()
						.where(STATISTICS_FIELD.NAME.eq(EXECUTIONS_PASSED).and(STATISTICS.LAUNCH_ID.eq(LAUNCH.ID)))
						.asField()
						.cast(Double.class))
						.div(nullif(dsl.select(sum(STATISTICS.S_COUNTER))
								.from(STATISTICS)
								.join(STATISTICS_FIELD)
								.onKey()
								.where(STATISTICS_FIELD.NAME.eq(EXECUTIONS_TOTAL).and(STATISTICS.LAUNCH_ID.eq(LAUNCH.ID)))
								.asField(), 0)), 2).as(PASSING_RATE),
				timestampDiff(LAUNCH.END_TIME, LAUNCH.START_TIME).as(DURATION),
				DSL.selectDistinct(FILTER.NAME).from(FILTER).where(FILTER.ID.eq(filter.getId())).asField(FILTER_NAME)
		);

		return buildProductStatusQuery(filter, isLatest, sort, limit, fields, contentFields, tags).orderBy(buildSortFields(sort));

	}

	private SelectSeekStepN<? extends Record> buildLaunchGroupedQuery(Filter filter, boolean isLatest, Sort sort, int limit,
			Collection<String> contentFields, Collection<String> tags) {

		List<Field<?>> fields = Lists.newArrayList(LAUNCH.ID,
				LAUNCH.NAME,
				LAUNCH.NUMBER,
				LAUNCH.START_TIME,
				LAUNCH.STATUS,
				fieldName(STATISTICS_TABLE, SF_NAME),
				fieldName(STATISTICS_TABLE, STATISTICS_COUNTER),
				round(val(PERCENTAGE_MULTIPLIER).mul(dsl.select(sum(STATISTICS.S_COUNTER))
						.from(STATISTICS)
						.join(STATISTICS_FIELD)
						.onKey()
						.where(STATISTICS_FIELD.NAME.eq(EXECUTIONS_PASSED).and(STATISTICS.LAUNCH_ID.eq(LAUNCH.ID)))
						.asField()
						.cast(Double.class))
						.div(nullif(dsl.select(sum(STATISTICS.S_COUNTER))
								.from(STATISTICS)
								.join(STATISTICS_FIELD)
								.onKey()
								.where(STATISTICS_FIELD.NAME.eq(EXECUTIONS_TOTAL).and(STATISTICS.LAUNCH_ID.eq(LAUNCH.ID)))
								.asField(), 0)), 2).as(PASSING_RATE),
				timestampDiff(LAUNCH.END_TIME, LAUNCH.START_TIME).as(DURATION)
		);

		return buildProductStatusQuery(filter, isLatest, sort, limit, fields, contentFields, tags).orderBy(buildSortFields(sort));
	}

	private SelectOnConditionStep<? extends Record> buildProductStatusQuery(Filter filter, boolean isLatest, Sort sort, int limit,
			Collection<Field<?>> fields, Collection<String> contentFields, Collection<String> tags) {

		List<Condition> conditions = tags.stream()
				.map(cf -> ITEM_ATTRIBUTE.KEY.like(cf + LIKE_CONDITION_SYMBOL))
				.collect(Collectors.toList());

		Optional<Condition> combinedTagCondition = conditions.stream().reduce((prev, curr) -> curr = prev.or(curr));

		List<String> statisticsFields = contentFields.stream().filter(cf -> cf.startsWith(STATISTICS_KEY)).collect(toList());

		if (combinedTagCondition.isPresent()) {
			Collections.addAll(fields, fieldName(ATTRIBUTE_TABLE, ATTRIBUTE_ID), fieldName(ATTRIBUTE_TABLE, ATTRIBUTE_VALUE));
			return getProductStatusSelect(filter, isLatest, sort, limit, fields, statisticsFields).leftJoin(DSL.select(ITEM_ATTRIBUTE.ID.as(
					ATTRIBUTE_ID), ITEM_ATTRIBUTE.VALUE.as(ATTRIBUTE_VALUE))
					.from(ITEM_ATTRIBUTE)
					.where(combinedTagCondition.get())
					.asTable(ATTRIBUTE_TABLE)).on(LAUNCH.ID.eq(fieldName(ATTRIBUTE_TABLE, ATTRIBUTE_ID).cast(Long.class)));
		} else {
			return getProductStatusSelect(filter, isLatest, sort, limit, fields, statisticsFields);
		}
	}

	private SelectOnConditionStep<? extends Record> getProductStatusSelect(Filter filter, boolean isLatest, Sort sort, int limit,
			Collection<Field<?>> fields, Collection<String> contentFields) {
		return dsl.with(LAUNCHES)
				.as(QueryBuilder.newBuilder(filter).with(isLatest).with(sort).with(limit).build())
				.select(fields)
				.from(LAUNCH)
				.join(LAUNCHES)
				.on(LAUNCH.ID.eq(fieldName(LAUNCHES, ID).cast(Long.class)))
				.leftJoin(DSL.select(STATISTICS.LAUNCH_ID, STATISTICS.S_COUNTER.as(STATISTICS_COUNTER), STATISTICS_FIELD.NAME.as(SF_NAME))
						.from(STATISTICS)
						.join(STATISTICS_FIELD)
						.on(STATISTICS.STATISTICS_FIELD_ID.eq(STATISTICS_FIELD.SF_ID))
						.where(STATISTICS_FIELD.NAME.in(contentFields))
						.asTable(STATISTICS_TABLE))
				.on(LAUNCH.ID.eq(fieldName(STATISTICS_TABLE, LAUNCH_ID).cast(Long.class)));
	}

	private ProductStatusStatisticsContent countLaunchTotalStatistics(List<ProductStatusStatisticsContent> launchesStatisticsResult) {
		Map<String, Integer> total = launchesStatisticsResult.stream()
				.flatMap(lsc -> lsc.getValues().entrySet().stream())
				.collect(Collectors.groupingBy(entry -> (entry.getKey()), summingInt(entry -> Integer.parseInt(entry.getValue()))));

		Double averagePassingRate = launchesStatisticsResult.stream()
				.collect(averagingDouble(lsc -> ofNullable(lsc.getPassingRate()).orElse(0D)));

		ProductStatusStatisticsContent launchesStatisticsContent = new ProductStatusStatisticsContent();
		launchesStatisticsContent.setTotalStatistics(total);

		Double roundedAveragePassingRate = BigDecimal.valueOf(averagePassingRate).setScale(2, RoundingMode.HALF_UP).doubleValue();
		launchesStatisticsContent.setAveragePassingRate(roundedAveragePassingRate);

		return launchesStatisticsContent;
	}

	private List<ProductStatusStatisticsContent> countFilterTotalStatistics(
			Map<String, List<ProductStatusStatisticsContent>> launchesStatisticsResult) {
		Map<String, Integer> total = launchesStatisticsResult.values()
				.stream()
				.flatMap(Collection::stream)
				.flatMap(lsc -> lsc.getValues().entrySet().stream())
				.collect(Collectors.groupingBy(entry -> (entry.getKey()), summingInt(entry -> Integer.parseInt(entry.getValue()))));

		Double averagePassingRate = launchesStatisticsResult.values()
				.stream()
				.flatMap(Collection::stream)
				.collect(averagingDouble(lsc -> ofNullable(lsc.getPassingRate()).orElse(0D)));

		ProductStatusStatisticsContent launchesStatisticsContent = new ProductStatusStatisticsContent();
		launchesStatisticsContent.setTotalStatistics(total);

		Double roundedAveragePassingRate = BigDecimal.valueOf(averagePassingRate).setScale(2, RoundingMode.HALF_UP).doubleValue();
		launchesStatisticsContent.setAveragePassingRate(roundedAveragePassingRate);

		return Lists.newArrayList(launchesStatisticsContent);
	}

	private List<Field<?>> buildFieldsFromContentFields(List<String> contentFields) {
		return contentFields.stream().map(JooqFieldNameTransformer::fieldName).collect(Collectors.toList());
	}

}
