package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.commons.querygen.Filter;
import com.epam.ta.reportportal.commons.querygen.QueryBuilder;
import com.epam.ta.reportportal.dao.util.FieldNameTransformer;
import com.epam.ta.reportportal.entity.widget.content.*;
import com.epam.ta.reportportal.exception.ReportPortalException;
import com.epam.ta.reportportal.jooq.enums.JStatusEnum;
import com.epam.ta.reportportal.jooq.enums.JTestItemTypeEnum;
import com.epam.ta.reportportal.ws.model.ErrorType;
import com.google.common.collect.Lists;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.epam.ta.reportportal.dao.constant.WidgetContentRepositoryConstants.*;
import static com.epam.ta.reportportal.dao.util.FieldNameTransformer.fieldName;
import static com.epam.ta.reportportal.dao.util.ResultFetcher.LAUNCHES_STATISTICS_FETCHER;
import static com.epam.ta.reportportal.dao.util.ResultFetcher.NOT_PASSED_CASES_FETCHER;
import static com.epam.ta.reportportal.jooq.tables.JActivity.ACTIVITY;
import static com.epam.ta.reportportal.jooq.tables.JFilter.FILTER;
import static com.epam.ta.reportportal.jooq.tables.JIssue.ISSUE;
import static com.epam.ta.reportportal.jooq.tables.JIssueGroup.ISSUE_GROUP;
import static com.epam.ta.reportportal.jooq.tables.JIssueType.ISSUE_TYPE;
import static com.epam.ta.reportportal.jooq.tables.JLaunch.LAUNCH;
import static com.epam.ta.reportportal.jooq.tables.JLaunchTag.LAUNCH_TAG;
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

	@Override
	public List<StatisticsContent> overallStatisticsContent(Filter filter, List<String> contentFields, boolean latest, int limit) {
		//		Select commonSelect;
		//		if (latest) {
		//			commonSelect = dsl.select(field(name(LAUNCHES, "id")).cast(Long.class))
		//					.distinctOn(field(name(LAUNCHES, "launch_name")).cast(String.class))
		//					.from(name(LAUNCHES))
		//					.orderBy(
		//							field(name(LAUNCHES, "launch_name")).cast(String.class),
		//							field(name(LAUNCHES, "number")).cast(Integer.class).desc()
		//					);
		//		} else {
		//			commonSelect = dsl.select(field(name(LAUNCHES, "id")).cast(Long.class)).from(name(LAUNCHES));
		//		}
		//
		//		return dsl.with(LAUNCHES)
		//				.as(QueryBuilder.newBuilder(filter).build())
		//				.select(EXECUTION_STATISTICS.ES_STATUS.as("field"), DSL.sumDistinct(EXECUTION_STATISTICS.ES_COUNTER))
		//				.from(LAUNCH)
		//				.join(EXECUTION_STATISTICS)
		//				.on(LAUNCH.ID.eq(EXECUTION_STATISTICS.LAUNCH_ID))
		//				.where(EXECUTION_STATISTICS.LAUNCH_ID.in(commonSelect))
		//				.and(EXECUTION_STATISTICS.ES_STATUS.in(Optional.ofNullable(contentFields.get(EXECUTIONS_KEY))
		//						.orElse(Collections.emptyList())))
		//				.groupBy(EXECUTION_STATISTICS.ES_STATUS)
		//				.unionAll(dsl.select(ISSUE_TYPE.LOCATOR.as("field"), DSL.sumDistinct(ISSUE_STATISTICS.IS_COUNTER))
		//						.from(LAUNCH)
		//						.join(ISSUE_STATISTICS)
		//						.on(LAUNCH.ID.eq(ISSUE_STATISTICS.LAUNCH_ID))
		//						.join(ISSUE_TYPE)
		//						.on(ISSUE_STATISTICS.ISSUE_TYPE_ID.eq(ISSUE_TYPE.ID))
		//						.where(ISSUE_STATISTICS.LAUNCH_ID.in(commonSelect))
		//						.and(ISSUE_TYPE.LOCATOR.in(Optional.ofNullable(contentFields.get(DEFECTS_KEY)).orElse(Collections.emptyList())))
		//						.groupBy(ISSUE_TYPE.LOCATOR))
		//				.limit(limit)
		//				.fetchInto(StatisticsContent.class);
		return null;
	}

	@Override
	public List<MostFailedContent> mostFailedByExecutionCriteria(String launchName, String criteria, int limit) {
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
								.otherwise(ZERO_QUERY_VALUE)).as(CRITERIA),
						DSL.count(TEST_ITEM_RESULTS.STATUS).as(TOTAL)
				)
						.from(LAUNCH)
						.join(TEST_ITEM)
						.on(LAUNCH.ID.eq(TEST_ITEM.LAUNCH_ID))
						.join(TEST_ITEM_RESULTS)
						.on(TEST_ITEM.ITEM_ID.eq(TEST_ITEM_RESULTS.RESULT_ID))
						.where(TEST_ITEM.TYPE.eq(JTestItemTypeEnum.STEP))
						.and(LAUNCH.NAME.eq(launchName))
						.groupBy(TEST_ITEM.UNIQUE_ID, TEST_ITEM.NAME))
				.select()
				.from(DSL.table(DSL.name(HISTORY)))
				.where(DSL.field(DSL.name(CRITERIA)).greaterThan(ZERO_QUERY_VALUE))
				.orderBy(DSL.field(DSL.name(CRITERIA)).desc(), DSL.field(DSL.name(TOTAL)).asc())
				.limit(limit)
				.fetchInto(MostFailedContent.class);
	}

	@Override
	public List<MostFailedContent> mostFailedByDefectCriteria(String launchName, String criteria, int limit) {
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
								.otherwise(ZERO_QUERY_VALUE)).as(CRITERIA),
						DSL.count(TEST_ITEM_RESULTS.RESULT_ID).as(TOTAL)
				)
						.from(LAUNCH)
						.join(TEST_ITEM)
						.on(LAUNCH.ID.eq(TEST_ITEM.LAUNCH_ID))
						.join(TEST_ITEM_RESULTS)
						.on(TEST_ITEM.ITEM_ID.eq(TEST_ITEM_RESULTS.RESULT_ID))
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
				.where(DSL.field(DSL.name(CRITERIA)).greaterThan(ZERO_QUERY_VALUE))
				.orderBy(DSL.field(DSL.name(CRITERIA)).desc(), DSL.field(DSL.name(TOTAL)).asc())
				.limit(limit)
				.fetchInto(MostFailedContent.class);
	}

	@Override
	public List<LaunchesStatisticsContent> launchStatistics(Filter filter, List<String> contentFields, Sort sort, int limit) {

		List<Field<?>> fields = buildFieldsFromContentFields(contentFields);

		Collections.addAll(fields, fieldName(LAUNCH.ID), fieldName(LAUNCH.NUMBER), fieldName(LAUNCH.START_TIME), fieldName(LAUNCH.NAME));

		return LAUNCHES_STATISTICS_FETCHER.apply(dsl.select(fields)
				.from(QueryBuilder.newBuilder(filter).with(sort).with(limit).build())
				.fetch(), contentFields);

	}

	@Override
	public List<InvestigatedStatisticsResult> investigatedStatistics(Filter filter, Sort sort, int limit) {

		Field<Double> toInvestigate = round(val(PERCENTAGE_MULTIPLIER).mul(fieldName(DEFECTS_TO_INVESTIGATE_TOTAL).cast(Double.class))
				.div(nullif(fieldName(DEFECTS_AUTOMATION_BUG_TOTAL).add(fieldName(DEFECTS_NO_DEFECT_TOTAL))
						.add(fieldName(DEFECTS_TO_INVESTIGATE_TOTAL))
						.add(fieldName(DEFECTS_PRODUCT_BUG_TOTAL))
						.add(fieldName(DEFECTS_SYSTEM_ISSUE_TOTAL)), 0).cast(Double.class)), 2);

		return dsl.select(fieldName(LAUNCH.ID),
				fieldName(LAUNCH.NUMBER),
				fieldName(LAUNCH.START_TIME),
				fieldName(LAUNCH.NAME),
				toInvestigate.as(TO_INVESTIGATE),
				val(PERCENTAGE_MULTIPLIER).sub(coalesce(toInvestigate, 0)).as(INVESTIGATED)
		).from(QueryBuilder.newBuilder(filter).with(sort).with(limit).build()).fetchInto(InvestigatedStatisticsResult.class);

	}

	@Override
	public PassingRateStatisticsResult passingRateStatistics(Filter filter, Sort sort, int limit) {

		return dsl.select(sum(fieldName(EXECUTIONS_PASSED).cast(Integer.class)).as(PASSED),
				sum(fieldName(EXECUTIONS_TOTAL).cast(Integer.class)).as(TOTAL)
		)
				.from(QueryBuilder.newBuilder(filter).with(sort).with(limit).build())
				.fetchInto(PassingRateStatisticsResult.class)
				.stream()
				.findFirst()
				.orElseThrow(() -> new ReportPortalException("No results for filter were found"));
	}

	@Override
	public List<CasesTrendContent> casesTrendStatistics(Filter filter, String executionContentField, Sort sort, int limit) {

		Field<Integer> executionField = fieldName(executionContentField).cast(Integer.class);

		return dsl.select(fieldName(LAUNCH.ID),
				fieldName(LAUNCH.NUMBER),
				fieldName(LAUNCH.START_TIME),
				fieldName(LAUNCH.NAME),
				executionField.as(executionContentField),
				executionField.sub(lag(executionField).over().orderBy(executionField)).as(DELTA)
		).from(QueryBuilder.newBuilder(filter).with(sort).with(limit).build()).fetchInto(CasesTrendContent.class);
	}

	@Override
	public List<LaunchesStatisticsContent> bugTrendStatistics(Filter filter, List<String> contentFields, Sort sort, int limit) {

		List<Field<?>> fields = buildFieldsFromContentFields(contentFields);

		Field<?> sumField = fields.stream()
				.reduce(Field::add)
				.orElseThrow(() -> new ReportPortalException(ErrorType.BAD_REQUEST_ERROR, "Content fields should not be empty"))
				.as(TOTAL);

		Collections.addAll(fields,
				sumField,
				fieldName(LAUNCH.ID),
				fieldName(LAUNCH.NAME),
				fieldName(LAUNCH.NUMBER),
				fieldName(LAUNCH.START_TIME)
		);

		return LAUNCHES_STATISTICS_FETCHER.apply(dsl.select(fields)
				.from(QueryBuilder.newBuilder(filter).with(sort).with(limit).build())
				.fetch(), contentFields);

	}

	@Override
	public List<LaunchesStatisticsContent> launchesComparisonStatistics(Filter filter, List<String> contentFields, Sort sort, int limit) {

		List<Field<Double>> fields = contentFields.stream()
				.map(contentField -> fieldName(contentField).cast(Double.class).as(contentField))
				.collect(Collectors.toList());

		Field<Double> contentFieldsSum = fields.stream()
				.reduce(Field::add)
				.orElseThrow(() -> new ReportPortalException(ErrorType.BAD_REQUEST_ERROR, "Content fields should not be empty"));

		List<Field<?>> statisticsFields = fields.stream()
				.map(field -> round(val(PERCENTAGE_MULTIPLIER).mul(field).div(nullif(contentFieldsSum, 0).cast(Double.class)), 2).as(field))
				.collect(Collectors.toList());

		Collections.addAll(statisticsFields,
				fieldName(LAUNCH.ID),
				fieldName(LAUNCH.NAME),
				fieldName(LAUNCH.NUMBER),
				fieldName(LAUNCH.START_TIME)
		);

		return LAUNCHES_STATISTICS_FETCHER.apply(dsl.select(statisticsFields)
				.from(QueryBuilder.newBuilder(filter).with(sort).with(limit).build())
				.fetch(), contentFields);

	}

	@Override
	public List<LaunchesDurationContent> launchesDurationStatistics(Filter filter, Sort sort, boolean isLatest, int limit) {

		return dsl.select(fieldName(LAUNCH.ID),
				fieldName(LAUNCH.NAME),
				fieldName(LAUNCH.NUMBER),
				fieldName(LAUNCH.STATUS),
				fieldName(LAUNCH.START_TIME),
				fieldName(LAUNCH.END_TIME),
				timestampDiff(fieldName(LAUNCH.END_TIME).cast(Timestamp.class), (fieldName(LAUNCH.START_TIME).cast(Timestamp.class))).as(
						DURATION)
		).from(QueryBuilder.newBuilder(filter).with(isLatest).with(sort).with(limit).build()).fetchInto(LaunchesDurationContent.class);
	}

	@Override
	public List<NotPassedCasesContent> notPassedCasesStatistics(Filter filter, Sort sort, int limit) {

		return NOT_PASSED_CASES_FETCHER.apply(dsl.select(fieldName(LAUNCH.ID),
				fieldName(LAUNCH.NUMBER),
				fieldName(LAUNCH.START_TIME),
				fieldName(LAUNCH.NAME),
				coalesce(round(val(PERCENTAGE_MULTIPLIER).mul(fieldName(EXECUTIONS_FAILED).add(fieldName(EXECUTIONS_SKIPPED))
						.cast(Double.class)).div(nullif(fieldName(EXECUTIONS_TOTAL), 0).cast(Double.class)), 2), 0).as(PERCENTAGE)
		).from(QueryBuilder.newBuilder(filter).with(sort).with(limit).build()).fetch());
	}

	@Override
	public List<LaunchesStatisticsContent> launchesTableStatistics(Filter filter, List<String> contentFields, Sort sort, int limit) {

		List<Field<?>> fields = buildFieldsFromContentFields(contentFields);

		Collections.addAll(fields, fieldName(LAUNCH.ID), fieldName(LAUNCH.NUMBER), fieldName(LAUNCH.START_TIME), fieldName(LAUNCH.NAME));

		return LAUNCHES_STATISTICS_FETCHER.apply(dsl.select(fields)
				.from(QueryBuilder.newBuilder(filter).with(sort).with(limit).build())
				.fetch(), contentFields);

	}

	@Override
	public List<ActivityContent> activityStatistics(Filter filter, Sort sort, int limit) {

		return dsl.select(fieldName(ACTIVITY.ID).as(ID),
				fieldName(ACTIVITY.ACTION).as(ACTION_TYPE),
				fieldName(ACTIVITY.ENTITY).as(ENTITY),
				fieldName(ACTIVITY.CREATION_DATE).as(LAST_MODIFIED),
				fieldName(USERS.LOGIN).as(USER_LOGIN),
				fieldName(PROJECT.NAME).as(PROJECT_NAME)
		).from(QueryBuilder.newBuilder(filter).with(sort).with(limit).build()).fetchInto(ActivityContent.class);

	}

	@Override
	public Map<String, List<UniqueBugContent>> uniqueBugStatistics(Filter filter, int limit) {

		List<UniqueBugContent> uniqueBugContents = dsl.select(fieldName(TICKET.TICKET_ID),
				fieldName(TICKET.SUBMIT_DATE),
				fieldName(TICKET.URL),
				fieldName(TEST_ITEM.ITEM_ID),
				fieldName(TEST_ITEM.NAME),
				fieldName(USERS.LOGIN)

		).from(QueryBuilder.newBuilder(filter).with(limit).build()).fetchInto(UniqueBugContent.class);

		return uniqueBugContents.stream().collect(groupingBy(UniqueBugContent::getTicketId));
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
						.as(QueryBuilder.newBuilder(filter).build())
						.select(TEST_ITEM.UNIQUE_ID,
								TEST_ITEM.NAME,
								TEST_ITEM_RESULTS.STATUS,
								when(TEST_ITEM_RESULTS.STATUS.notEqual(lag(TEST_ITEM_RESULTS.STATUS).over(orderBy(TEST_ITEM.ITEM_ID))),
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
				.fetchInto(FlakyCasesTableContent.class);
	}

	@Override
	public Map<String, List<LaunchesStatisticsContent>> cumulativeTrendStatistics(Filter filter, List<String> contentFields, Sort sort,
			String tagPrefix, int limit) {

		List<Field<?>> fields = buildFieldsFromContentFields(contentFields);

		Collections.addAll(fields,
				fieldName(LAUNCHES_SUB_QUERY, ID),
				fieldName(LAUNCH.NUMBER),
				fieldName(LAUNCH.START_TIME),
				fieldName(LAUNCH.NAME),
				fieldName(LAUNCH_TAG.VALUE).as(TAG_VALUE)
		);

		Select<Record> select = dsl.select(fields)
				.from(QueryBuilder.newBuilder(filter).with(sort).with(LAUNCHES_COUNT).build().asTable(LAUNCHES_SUB_QUERY))
				.join(LAUNCH_TAG)
				.on(LAUNCH_TAG.LAUNCH_ID.eq(fieldName(LAUNCHES_SUB_QUERY, ID).cast(Long.class)))
				.where(LAUNCH_TAG.VALUE.in(DSL.selectDistinct(LAUNCH_TAG.VALUE)
						.on(LAUNCH_TAG.VALUE)
						.from(LAUNCH_TAG)
						.where(LAUNCH_TAG.VALUE.like(tagPrefix + LIKE_CONDITION_SYMBOL))
						.orderBy(LAUNCH_TAG.VALUE.desc())
						.limit(limit)))
				.orderBy(ofNullable(sort).map(s -> StreamSupport.stream(s.spliterator(), false)
						.map(order -> field(name(order.getProperty())).sort(order.getDirection().isDescending() ?
								SortOrder.DESC :
								SortOrder.ASC))
						.collect(Collectors.toList())).orElseGet(Collections::emptyList));

		return LAUNCHES_STATISTICS_FETCHER.apply(select.fetch(), contentFields)
				.stream()
				.collect(groupingBy(LaunchesStatisticsContent::getTagValue))
				.entrySet()
				.stream()
				.sorted(Map.Entry.comparingByKey())
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue, LinkedHashMap::new));
	}

	@Override
	public Map<String, List<LaunchesStatisticsContent>> productStatusGroupedByFilterStatistics(Map<Filter, Sort> filterSortMapping,
			List<String> contentFields, List<String> tags, boolean isLatest, int limit) {

		List<Field<?>> fields = buildFieldsFromContentFields(contentFields);

		Collections.addAll(fields,
				fieldName(LAUNCHES_SUB_QUERY, ID),
				fieldName(LAUNCH.NUMBER),
				fieldName(LAUNCHES_SUB_QUERY, NAME),
				fieldName(LAUNCH.START_TIME),
				coalesce(round(val(PERCENTAGE_MULTIPLIER).mul(fieldName(EXECUTIONS_PASSED).cast(Double.class))
						.div(nullif(fieldName(EXECUTIONS_TOTAL), 0).cast(Double.class)), 2)).as(PASSING_RATE),
				timestampDiff(fieldName(LAUNCH.END_TIME).cast(Timestamp.class),
						(fieldName(LAUNCH.START_TIME).cast(Timestamp.class))
				).as(DURATION),
				FILTER.NAME.as(FILTER_NAME)
		);

		Select<? extends Record> select = filterSortMapping.entrySet()
				.stream()
				.map(f -> buildProjectStatusFilterGroupedSelect(f.getKey(), fields, tags, f.getValue(), isLatest, limit))
				.collect(Collectors.toList())
				.stream()
				.reduce((prev, curr) -> curr = prev.unionAll(curr))
				.orElseThrow(() -> new ReportPortalException(ErrorType.BAD_REQUEST_ERROR, "Query build for Product Status Widget failed"));

		Map<String, List<LaunchesStatisticsContent>> productStatusContent = LAUNCHES_STATISTICS_FETCHER.apply(select.fetch(), contentFields)
				.stream()
				.collect(groupingBy(LaunchesStatisticsContent::getFilterName, LinkedHashMap::new, toList()));

		productStatusContent.put(TOTAL, countFilterTotalStatistics(productStatusContent));

		return productStatusContent;
	}

	@Override
	public List<LaunchesStatisticsContent> productStatusGroupedByLaunchesStatistics(Filter filter, List<String> contentFields,
			List<String> tags, Sort sort, boolean isLatest, int limit) {

		List<Field<?>> fields = buildFieldsFromContentFields(contentFields);

		Collections.addAll(fields, fieldName(LAUNCH.ID), fieldName(LAUNCH.NUMBER), fieldName(LAUNCH.START_TIME), fieldName(LAUNCH.NAME));

		List<Condition> conditions = tags.stream()
				.map(cf -> LAUNCH_TAG.VALUE.like(cf + LIKE_CONDITION_SYMBOL))
				.collect(Collectors.toList());

		Optional<Condition> combinedTagCondition = conditions.stream().reduce((prev, curr) -> curr = prev.or(curr));

		if (combinedTagCondition.isPresent()) {
			return fetchProductStatusLaunchGroupedStatisticsWithTags(filter,
					contentFields,
					fields,
					combinedTagCondition.get(),
					sort,
					isLatest,
					limit
			);
		}

		List<LaunchesStatisticsContent> productStatusStatisticsResult = LAUNCHES_STATISTICS_FETCHER.apply(dsl.select(fields)
				.from(QueryBuilder.newBuilder(filter).with(isLatest).with(sort).with(limit).build().asTable(LAUNCHES_SUB_QUERY))
				.fetch(), contentFields);

		productStatusStatisticsResult.add(countLaunchTotalStatistics(productStatusStatisticsResult));
		return productStatusStatisticsResult;
	}

	@Override
	public List<MostTimeConsumingTestCasesContent> mostTimeConsumingTestCasesStatistics(Filter filter) {
		return dsl.select(fieldName(TEST_ITEM.ITEM_ID).as(ID),
				fieldName(TEST_ITEM.UNIQUE_ID),
				fieldName(TEST_ITEM.NAME),
				fieldName(TEST_ITEM.TYPE),
				fieldName(TEST_ITEM.START_TIME),
				fieldName(TEST_ITEM_RESULTS.END_TIME),
				fieldName(TEST_ITEM_RESULTS.DURATION),
				fieldName(TEST_ITEM_RESULTS.STATUS)
		)
				.from(QueryBuilder.newBuilder(filter)
						.addCondition(TEST_ITEM_RESULTS.STATUS.in(JStatusEnum.PASSED, JStatusEnum.FAILED))
						.with(20)
						.build())
				.orderBy(fieldName(TEST_ITEM_RESULTS.DURATION).desc())
				.fetchInto(MostTimeConsumingTestCasesContent.class);
	}

	private List<LaunchesStatisticsContent> fetchProductStatusLaunchGroupedStatisticsWithTags(Filter filter, List<String> contentFields,
			List<Field<?>> fields, Condition combinedTagCondition, Sort sort, boolean isLatest, int limit) {

		List<Field<?>> selectFields = Lists.newArrayList(fields);
		selectFields.add(arrayAgg(fieldName(LAUNCH_TAG.VALUE)).orderBy(fieldName(LAUNCH_TAG.VALUE)).as(TAG_VALUES));

		return LAUNCHES_STATISTICS_FETCHER.apply(dsl.select(selectFields)
				.from(QueryBuilder.newBuilder(filter).with(isLatest).with(sort).with(limit).build().asTable(LAUNCHES_SUB_QUERY))
				.leftJoin(dsl.select(LAUNCH_TAG.LAUNCH_ID, LAUNCH_TAG.VALUE)
						.from(LAUNCH_TAG)
						.where(combinedTagCondition)
						.orderBy(LAUNCH_TAG.VALUE)
						.asTable(TAG_TABLE))
				.on(fieldName(TAG_TABLE, LAUNCH_ID).cast(Long.class).eq(fieldName(LAUNCHES_SUB_QUERY, ID).cast(Long.class)))
				.groupBy(fields)
				.orderBy(ofNullable(sort).map(s -> StreamSupport.stream(s.spliterator(), false)
						.map(order -> field(name(order.getProperty())).sort(order.getDirection().isDescending() ?
								SortOrder.DESC :
								SortOrder.ASC))
						.collect(Collectors.toList())).orElseGet(Collections::emptyList))
				.fetch(), contentFields);
	}

	private Select<Record> buildProjectStatusFilterGroupedSelect(Filter filter, List<Field<?>> fields, List<String> tags, Sort sort,
			boolean isLatest, int limit) {
		List<Condition> conditions = Lists.newArrayList(FILTER.ID.eq(filter.getId()));
		List<Condition> tagConditions = tags.stream()
				.map(tag -> LAUNCH_TAG.VALUE.like(tag + LIKE_CONDITION_SYMBOL))
				.collect(Collectors.toList());
		Optional<Condition> combinedTagCondition = tagConditions.stream().reduce((prev, curr) -> curr = prev.or(curr));

		if (combinedTagCondition.isPresent()) {
			return buildProjectStatusFilterGroupedQuery(filter, conditions, fields, combinedTagCondition.get(), sort, isLatest, limit);
		}

		return dsl.select(fields)
				.from(QueryBuilder.newBuilder(filter).with(sort).with(isLatest).with(limit).build().asTable(LAUNCHES_SUB_QUERY))
				.join(PROJECT)
				.on(PROJECT.ID.eq(fieldName(LAUNCHES_SUB_QUERY, PROJECT_ID).cast(Long.class)))
				.join(FILTER)
				.on(FILTER.PROJECT_ID.eq(PROJECT.ID))
				.where(conditions);
	}

	private Select<Record> buildProjectStatusFilterGroupedQuery(Filter filter, List<Condition> conditions, List<Field<?>> fields,
			Condition combinedTagCondition, Sort sort, boolean isLatest, int limit) {

		List<Field<?>> selectFields = Lists.newArrayList(fields);
		selectFields.add(arrayAgg(fieldName(LAUNCH_TAG.VALUE)).orderBy(fieldName(LAUNCH_TAG.VALUE)).as(TAG_VALUES));

		return dsl.select(selectFields)
				.from(QueryBuilder.newBuilder(filter).with(sort).with(isLatest).with(limit).build().asTable(LAUNCHES_SUB_QUERY))
				.join(PROJECT)
				.on(PROJECT.ID.eq(fieldName(LAUNCHES_SUB_QUERY, PROJECT_ID).cast(Long.class)))
				.join(FILTER)
				.on(FILTER.PROJECT_ID.eq(PROJECT.ID))
				.leftJoin(dsl.select(LAUNCH_TAG.LAUNCH_ID, LAUNCH_TAG.VALUE)
						.from(LAUNCH_TAG)
						.where(combinedTagCondition)
						.orderBy(LAUNCH_TAG.VALUE)
						.asTable(TAG_TABLE))
				.on(fieldName(TAG_TABLE, LAUNCH_ID).cast(Long.class).eq(fieldName(LAUNCHES_SUB_QUERY, ID).cast(Long.class)))
				.where(conditions)
				.groupBy(fields)
				.orderBy(ofNullable(sort).map(s -> StreamSupport.stream(s.spliterator(), false)
						.map(order -> field(name(order.getProperty())).sort(order.getDirection().isDescending() ?
								SortOrder.DESC :
								SortOrder.ASC))
						.collect(Collectors.toList())).orElseGet(Collections::emptyList));

	}

	private LaunchesStatisticsContent countLaunchTotalStatistics(List<LaunchesStatisticsContent> launchesStatisticsResult) {
		Map<String, Integer> total = launchesStatisticsResult.stream()
				.flatMap(lsc -> lsc.getValues().entrySet().stream())
				.collect(Collectors.groupingBy(entry -> (entry.getKey()), summingInt(entry -> Integer.parseInt(entry.getValue()))));

		Double averagePassingRate = launchesStatisticsResult.stream()
				.collect(averagingDouble(lsc -> ofNullable(lsc.getPassingRate()).orElse(0D)));

		LaunchesStatisticsContent launchesStatisticsContent = new LaunchesStatisticsContent();
		launchesStatisticsContent.setTotalStatistics(total);

		Double roundedAveragePassingRate = BigDecimal.valueOf(averagePassingRate).setScale(2, RoundingMode.HALF_UP).doubleValue();
		launchesStatisticsContent.setAveragePassingRate(roundedAveragePassingRate);

		return launchesStatisticsContent;
	}

	private List<LaunchesStatisticsContent> countFilterTotalStatistics(
			Map<String, List<LaunchesStatisticsContent>> launchesStatisticsResult) {
		Map<String, Integer> total = launchesStatisticsResult.values()
				.stream()
				.flatMap(Collection::stream)
				.flatMap(lsc -> lsc.getValues().entrySet().stream())
				.collect(Collectors.groupingBy(entry -> (entry.getKey()), summingInt(entry -> Integer.parseInt(entry.getValue()))));

		Double averagePassingRate = launchesStatisticsResult.values()
				.stream()
				.flatMap(Collection::stream)
				.collect(averagingDouble(lsc -> ofNullable(lsc.getPassingRate()).orElse(0D)));

		LaunchesStatisticsContent launchesStatisticsContent = new LaunchesStatisticsContent();
		launchesStatisticsContent.setTotalStatistics(total);

		Double roundedAveragePassingRate = BigDecimal.valueOf(averagePassingRate).setScale(2, RoundingMode.HALF_UP).doubleValue();
		launchesStatisticsContent.setAveragePassingRate(roundedAveragePassingRate);

		return Lists.newArrayList(launchesStatisticsContent);
	}

	private List<Field<?>> buildFieldsFromContentFields(List<String> contentFields) {
		return contentFields.stream().map(FieldNameTransformer::fieldName).collect(Collectors.toList());
	}

}
