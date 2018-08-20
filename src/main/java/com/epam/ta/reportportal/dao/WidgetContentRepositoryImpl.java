package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.commons.querygen.Filter;
import com.epam.ta.reportportal.commons.querygen.QueryBuilder;
import com.epam.ta.reportportal.entity.enums.StatusEnum;
import com.epam.ta.reportportal.entity.enums.TestItemIssueGroup;
import com.epam.ta.reportportal.entity.launch.Launch;
import com.epam.ta.reportportal.entity.widget.content.*;
import com.epam.ta.reportportal.exception.ReportPortalException;
import com.epam.ta.reportportal.jooq.enums.JIssueGroupEnum;
import com.epam.ta.reportportal.jooq.enums.JStatusEnum;
import com.epam.ta.reportportal.entity.widget.content.MostFailedContent;
import com.epam.ta.reportportal.entity.widget.content.StatisticsContent;
import com.epam.ta.reportportal.jooq.enums.JTestItemTypeEnum;
import org.apache.commons.collections.CollectionUtils;
import org.jooq.*;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;
import java.util.List;
import java.util.Map;

import static com.epam.ta.reportportal.dao.LaunchRepositoryCustomImpl.LAUNCH_FETCHER;
import static com.epam.ta.reportportal.dao.constant.WidgetContentRepositoryConstants.*;
import static com.epam.ta.reportportal.jooq.tables.JActivity.ACTIVITY;
import static com.epam.ta.reportportal.jooq.tables.JExecutionStatistics.EXECUTION_STATISTICS;
import static com.epam.ta.reportportal.jooq.tables.JIssue.ISSUE;
import static com.epam.ta.reportportal.jooq.tables.JIssueGroup.ISSUE_GROUP;
import static com.epam.ta.reportportal.jooq.tables.JIssueStatistics.ISSUE_STATISTICS;
import static com.epam.ta.reportportal.jooq.tables.JIssueTicket.ISSUE_TICKET;
import static com.epam.ta.reportportal.jooq.tables.JIssueType.ISSUE_TYPE;
import static com.epam.ta.reportportal.jooq.tables.JLaunch.LAUNCH;
import static com.epam.ta.reportportal.jooq.tables.JProject.PROJECT;
import static com.epam.ta.reportportal.jooq.tables.JStatistics.STATISTICS;
import static com.epam.ta.reportportal.jooq.tables.JTestItem.TEST_ITEM;
import static com.epam.ta.reportportal.jooq.tables.JTestItemResults.TEST_ITEM_RESULTS;
import static com.epam.ta.reportportal.jooq.tables.JTestItemStructure.TEST_ITEM_STRUCTURE;
import static com.epam.ta.reportportal.jooq.tables.JTicket.TICKET;
import static com.epam.ta.reportportal.jooq.tables.JUsers.USERS;
import static org.jooq.impl.DSL.*;
import static com.epam.ta.reportportal.dao.WidgetContentRepositoryConstants.*;
import static com.epam.ta.reportportal.jooq.Tables.*;

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
	public List<StatisticsContent> overallStatisticsContent(Filter filter, Map<String, List<String>> contentFields, boolean latest,
			int limit) {
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
				.where(DSL.field(DSL.name(CRITERIA)).greaterThan(ZERO_QUERY_VALUE))
				.orderBy(DSL.field(DSL.name(CRITERIA)).desc(), DSL.field(DSL.name(TOTAL)).asc())
				.limit(limit)
				.fetchInto(MostFailedContent.class);
	}

	@Override
	public List<Launch> launchStatistics(Filter filter, Map<String, List<String>> contentFields, int limit) {

		Select commonSelect = buildCommonSelectWithLimit(LAUNCHES, limit);

		List<Launch> launchStatisticsContents = LAUNCH_FETCHER.apply(dsl.with(LAUNCHES)
				.as(QueryBuilder.newBuilder(filter).build())
				.select(LAUNCH.ID,
						LAUNCH.NUMBER,
						LAUNCH.NAME,
						LAUNCH.START_TIME,
						ISSUE_TYPE.LOCATOR,
						ISSUE_STATISTICS.IS_COUNTER,
						ISSUE_GROUP.ISSUE_GROUP_,
						field(name(EXECUTION_STATS, EXECUTION_STATISTICS.ES_STATUS.getName())),
						field(name(EXECUTION_STATS, EXECUTION_STATISTICS.ES_COUNTER.getName()))
				)
				.from(LAUNCH)
				.join(ISSUE_STATISTICS)
				.on(LAUNCH.ID.eq(ISSUE_STATISTICS.LAUNCH_ID))
				.join(ISSUE_TYPE)
				.on(ISSUE_STATISTICS.ISSUE_TYPE_ID.eq(ISSUE_TYPE.ID))
				.join(ISSUE_GROUP)
				.on(ISSUE_TYPE.ISSUE_GROUP_ID.eq(ISSUE_GROUP.ISSUE_GROUP_ID))
				.join(select(LAUNCH.ID, EXECUTION_STATISTICS.ES_STATUS, EXECUTION_STATISTICS.ES_COUNTER).from(LAUNCH)
						.join(EXECUTION_STATISTICS)
						.on(LAUNCH.ID.eq(EXECUTION_STATISTICS.LAUNCH_ID))
						.where(EXECUTION_STATISTICS.ES_STATUS.in(Optional.ofNullable(contentFields.get(EXECUTIONS_KEY))
								.orElseGet(Collections::emptyList)))
						.and(EXECUTION_STATISTICS.LAUNCH_ID.in(commonSelect))
						.groupBy(LAUNCH.ID, EXECUTION_STATISTICS.ES_STATUS, EXECUTION_STATISTICS.ES_COUNTER)
						.asTable(EXECUTION_STATS))
				.on(LAUNCH.ID.eq(field(name(EXECUTION_STATS, ID)).cast(Long.class)))
				.where(ISSUE_TYPE.LOCATOR.in(Optional.ofNullable(contentFields.get(DEFECTS_KEY)).orElseGet(Collections::emptyList)))
				.and(ISSUE_STATISTICS.LAUNCH_ID.in(commonSelect))
				.groupBy(LAUNCH.ID,
						LAUNCH.NUMBER,
						LAUNCH.NAME,
						LAUNCH.START_TIME,
						ISSUE_TYPE.LOCATOR,
						ISSUE_STATISTICS.IS_COUNTER,
						ISSUE_GROUP.ISSUE_GROUP_ID,
						field(name(EXECUTION_STATS, EXECUTION_STATISTICS.ES_STATUS.getName())),
						field(name(EXECUTION_STATS, EXECUTION_STATISTICS.ES_COUNTER.getName()))
				)
				.fetch());

		return launchStatisticsContents;
	}

	@Override
	public List<InvestigatedStatisticsResult> investigatedStatistics(Filter filter, int limit) {

		Select commonSelect = buildCommonSelectWithLimit(LAUNCHES, limit);

		return dsl.with(LAUNCHES)
				.as(QueryBuilder.newBuilder(filter).build())
				.select(LAUNCH.ID.as(LAUNCH_ID),
						LAUNCH.NUMBER.as(LAUNCH_NUMBER),
						LAUNCH.NAME.as(NAME),
						LAUNCH.START_TIME.as(START_TIME),
						(sum(when(ISSUE_GROUP.ISSUE_GROUP_.equal(JIssueGroupEnum.TO_INVESTIGATE),
								ZERO_QUERY_VALUE
						).otherwise(ISSUE_STATISTICS.IS_COUNTER))).cast(Double.class)
								.mul(PERCENTAGE_MULTIPLIER)
								.div(sum(ISSUE_STATISTICS.IS_COUNTER))
								.as(INVESTIGATED_PERCENTAGE),
						(sum(when(ISSUE_GROUP.ISSUE_GROUP_.notEqual(JIssueGroupEnum.TO_INVESTIGATE),
								ZERO_QUERY_VALUE
						).otherwise(ISSUE_STATISTICS.IS_COUNTER))).cast(Double.class)
								.mul(PERCENTAGE_MULTIPLIER)
								.div(sum(ISSUE_STATISTICS.IS_COUNTER))
								.as(NOT_INVESTIGATED_PERCENTAGE)
				)
				.from(LAUNCH)
				.join(ISSUE_STATISTICS)
				.on(ISSUE_STATISTICS.LAUNCH_ID.eq(LAUNCH.ID))
				.join(ISSUE_TYPE)
				.on(ISSUE_TYPE.ID.eq(ISSUE_STATISTICS.ISSUE_TYPE_ID))
				.join(ISSUE_GROUP)
				.on(ISSUE_GROUP.ISSUE_GROUP_ID.eq(ISSUE_TYPE.ISSUE_GROUP_ID))
				.and(LAUNCH.ID.in(commonSelect))
				.groupBy(LAUNCH.ID, LAUNCH.NUMBER, LAUNCH.NAME, LAUNCH.START_TIME)
				.fetchInto(InvestigatedStatisticsResult.class);
	}

	@Override
	public PassingRateStatisticsResult passingRatePerLaunchStatistics(Filter filter, Map<String, List<String>> contentFields, Launch launch,
			int limit) {

		return dsl.with(LAUNCHES)
				.as(QueryBuilder.newBuilder(filter).build())
				.select(
						sum(when(EXECUTION_STATISTICS.ES_STATUS.equal(JStatusEnum.PASSED.getLiteral()),
								EXECUTION_STATISTICS.ES_COUNTER
						)).as(PASSED),
						sum(when(EXECUTION_STATISTICS.ES_STATUS.in(Optional.ofNullable(contentFields.get(EXECUTIONS_KEY))
								.orElseGet(Collections::emptyList)), EXECUTION_STATISTICS.ES_COUNTER)).as(TOTAL)
				)
				.from(LAUNCH)
				.join(EXECUTION_STATISTICS)
				.on(LAUNCH.ID.eq(EXECUTION_STATISTICS.LAUNCH_ID))
				.where(LAUNCH.NAME.eq(launch.getName()))
				.and(EXECUTION_STATISTICS.ES_STATUS.in(Optional.ofNullable(contentFields.get(EXECUTIONS_KEY))
						.orElseGet(Collections::emptyList)))
				.and(LAUNCH.NUMBER.eq(launch.getNumber().intValue()))
				.and(LAUNCH.ID.eq(launch.getId()))
				.groupBy(LAUNCH.NAME)
				.limit(limit)
				.fetchInto(PassingRateStatisticsResult.class)
				.stream()
				.findFirst()
				.orElseThrow(() -> new ReportPortalException("Widget for launch name: " + launch.getName() + " not found"));
	}

	@Override
	public PassingRateStatisticsResult summaryPassingRateStatistics(Filter filter, Map<String, List<String>> contentFields, int limit) {

		Select commonSelect = buildCommonSelectWithLimit(LAUNCHES, limit);

		return dsl.with(LAUNCHES)
				.as(QueryBuilder.newBuilder(filter).build())
				.select(
						sum(when(EXECUTION_STATISTICS.ES_STATUS.equal(JStatusEnum.PASSED.getLiteral()),
								EXECUTION_STATISTICS.ES_COUNTER
						)).as(PASSED),
						sum(when(EXECUTION_STATISTICS.ES_STATUS.in(Optional.ofNullable(contentFields.get(EXECUTIONS_KEY))
								.orElseGet(Collections::emptyList)), EXECUTION_STATISTICS.ES_COUNTER)).as(TOTAL)
				)
				.from(EXECUTION_STATISTICS)
				.where(EXECUTION_STATISTICS.ES_STATUS.in(Optional.ofNullable(contentFields.get(EXECUTIONS_KEY))
						.orElseGet(Collections::emptyList)))
				.and(EXECUTION_STATISTICS.LAUNCH_ID.in(commonSelect))
				.fetchInto(PassingRateStatisticsResult.class)
				.stream()
				.findFirst()
				.orElseThrow(() -> new ReportPortalException("No results for filter were found"));
	}

	@Override
	public List<CasesTrendContent> casesTrendStatistics(Filter filter, Map<String, List<String>> contentFields, int limit) {

		return dsl.select(fieldName(STATISTICS.LAUNCH_ID),
				fieldName(LAUNCH.NUMBER),
				fieldName(LAUNCH.START_TIME),
				fieldName(LAUNCH.NAME),
				sum(fieldName(STATISTICS.S_COUNTER).cast(Integer.class)).as(TOTAL),
				sum(fieldName(STATISTICS.S_COUNTER).cast(Integer.class)).sub(lag(sum(fieldName(STATISTICS.S_COUNTER).cast(Integer.class))).over()
						.orderBy(LAUNCH.NUMBER)).as(DELTA)
		)
				.from(QueryBuilder.newBuilder(filter).build())
				.groupBy(fieldName(STATISTICS.LAUNCH_ID), fieldName(LAUNCH.NUMBER), fieldName(LAUNCH.START_TIME), fieldName(LAUNCH.NAME))
				.fetchInto(CasesTrendContent.class);
	}

	@Override
	public List<LaunchStatisticsContent> bugTrendStatistics(Filter filter, Map<String, List<String>> contentFields, int limit) {

		Select commonSelect = buildCommonSelectWithLimit(LAUNCHES, limit);

		List<LaunchStatisticsContent> launchStatisticsContents = dsl.with(LAUNCHES)
				.as(QueryBuilder.newBuilder(filter).build())
				.select(LAUNCH.ID.as(LAUNCH_ID),
						LAUNCH.NUMBER.as(LAUNCH_NUMBER),
						LAUNCH.START_TIME.as(START_TIME),
						LAUNCH.NAME.as(NAME),
						ISSUE_TYPE.LOCATOR.as(ISSUE_NAME),
						ISSUE_STATISTICS.IS_COUNTER.as(ISSUE_COUNT)
				)
				.from(LAUNCH)
				.join(ISSUE_STATISTICS)
				.on(LAUNCH.ID.eq(ISSUE_STATISTICS.LAUNCH_ID))
				.join(ISSUE_TYPE)
				.on(ISSUE_STATISTICS.ISSUE_TYPE_ID.eq(ISSUE_TYPE.ID))
				.where(ISSUE_TYPE.LOCATOR.in(Optional.ofNullable(contentFields.get(DEFECTS_KEY)).orElseGet(Collections::emptyList)))
				.and(LAUNCH.ID.in(commonSelect))
				.groupBy(LAUNCH.ID, LAUNCH.NUMBER, LAUNCH.START_TIME, LAUNCH.NAME, ISSUE_TYPE.LOCATOR, ISSUE_STATISTICS.IS_COUNTER)
				.fetchInto(LaunchStatisticsContent.class);

		//		Map<Long, Map<String, Integer>> issuesMap = launchStatisticsContents.stream()
		//				.collect(Collectors.groupingBy(LaunchStatisticsContent::getLaunchId,
		//						Collectors.groupingBy(LaunchStatisticsContent::getIssueName,
		//								Collectors.summingInt(LaunchStatisticsContent::getIssueCount)
		//						)
		//				));
		//
		//		List<LaunchStatisticsContent> resultLaunchStatisticsContents = new ArrayList<>(issuesMap.size());
		//
		//		issuesMap.forEach((key, value) -> launchStatisticsContents.stream()
		//				.filter(content -> Objects.equals(key, content.getLaunchId()))
		//				.findFirst()
		//				.ifPresent(content -> {
		//					content.setDefectsMap(value);
		//					resultLaunchStatisticsContents.add(content);
		//				}));

		return null;

	}

	@Override
	public List<ComparisonStatisticsContent> launchesComparisonStatistics(Filter filter, Map<String, List<String>> contentFields,
			int limit) {

		Select commonSelect = buildCommonSelectWithLimit(LAUNCHES, limit);

		List<ComparisonStatisticsContent> comparisonStatisticsContents = dsl.with(LAUNCHES)
				.as(QueryBuilder.newBuilder(filter).build())
				.select(LAUNCH.ID.as(LAUNCH_ID),
						LAUNCH.NAME.as(NAME),
						LAUNCH.START_TIME.as(START_TIME),
						LAUNCH.NUMBER.as(LAUNCH_NUMBER),
						ISSUE_GROUP.ISSUE_GROUP_.cast(String.class).as(ISSUE_NAME),
						(sum(ISSUE_STATISTICS.IS_COUNTER)).cast(Double.class)
								.div(sum(sum(ISSUE_STATISTICS.IS_COUNTER)).over().partitionBy(LAUNCH.NUMBER))
								.mul(PERCENTAGE_MULTIPLIER)
								.as(ISSUE_PERCENTAGE)
				)
				.from(LAUNCH)
				.join(ISSUE_STATISTICS)
				.on(LAUNCH.ID.eq(ISSUE_STATISTICS.LAUNCH_ID))
				.join(ISSUE_TYPE)
				.on(ISSUE_STATISTICS.ISSUE_TYPE_ID.eq(ISSUE_TYPE.ID))
				.join(ISSUE_GROUP)
				.on(ISSUE_TYPE.ISSUE_GROUP_ID.eq(ISSUE_GROUP.ISSUE_GROUP_ID))
				.where(LAUNCH.ID.in(commonSelect))
				.and(ISSUE_GROUP.ISSUE_GROUP_.in(Optional.ofNullable(contentFields.get(ISSUE_GROUP_KEY)).orElseGet(Collections::emptyList)))
				.groupBy(LAUNCH.ID, LAUNCH.NAME, LAUNCH.START_TIME, LAUNCH.NUMBER, ISSUE_GROUP.ISSUE_GROUP_)
				.unionAll(dsl.select(LAUNCH.ID.as(LAUNCH_ID),
						LAUNCH.NAME.as(NAME),
						LAUNCH.START_TIME.as(START_TIME),
						LAUNCH.NUMBER.as(LAUNCH_NUMBER),
						EXECUTION_STATISTICS.ES_STATUS.as(ISSUE_NAME),
						(sum(EXECUTION_STATISTICS.ES_COUNTER)).cast(Double.class)
								.div(sum(sum(EXECUTION_STATISTICS.ES_COUNTER)).over().partitionBy(LAUNCH.NUMBER))
								.mul(PERCENTAGE_MULTIPLIER)
								.as(ISSUE_PERCENTAGE)
				)
						.from(LAUNCH)
						.join(EXECUTION_STATISTICS)
						.on(LAUNCH.ID.eq(EXECUTION_STATISTICS.LAUNCH_ID))
						.where(LAUNCH.ID.in(commonSelect))
						.and(EXECUTION_STATISTICS.ES_STATUS.in(Optional.ofNullable(contentFields.get(EXECUTIONS_KEY))
								.orElseGet(Collections::emptyList)))
						.groupBy(LAUNCH.ID, LAUNCH.NAME, LAUNCH.START_TIME, LAUNCH.NUMBER, EXECUTION_STATISTICS.ES_STATUS))
				.fetchInto(ComparisonStatisticsContent.class);

		Map<Long, Map<String, Double>> issuesMap = comparisonStatisticsContents.stream()
				.collect(Collectors.groupingBy(ComparisonStatisticsContent::getLaunchId,
						Collectors.groupingBy(ComparisonStatisticsContent::getIssueName,
								Collectors.summingDouble(ComparisonStatisticsContent::getIssuePercentage)
						)
				));

		List<ComparisonStatisticsContent> resultComparisonStatisticsContent = new ArrayList<>(issuesMap.size());

		issuesMap.forEach((key, value) -> comparisonStatisticsContents.stream()
				.filter(content -> Objects.equals(key, content.getLaunchId()))
				.findFirst()
				.ifPresent(content -> {
					Map<String, Double> executions = new HashMap<>();
					Map<String, Double> defectGroups = new HashMap<>();
					value.keySet().forEach(name -> {
						if (StatusEnum.isPresent(name)) {
							executions.put(name, value.get(name));
						} else if (TestItemIssueGroup.isPresent(name)) {
							defectGroups.put(name, value.get(name));
						}
					});
					content.setDefectGroups(defectGroups);
					content.setExecutionsMap(executions);
					resultComparisonStatisticsContent.add(content);
				}));

		return resultComparisonStatisticsContent;
	}

	@Override
	public List<LaunchesDurationContent> launchesDurationStatistics(Filter filter, Map<String, List<String>> contentFields, int limit) {

		Select commonSelect = buildCommonSelectWithLimit(LAUNCHES, limit);

		List<LaunchesDurationContent> launchesDurationContents = dsl.with(LAUNCHES)
				.as(QueryBuilder.newBuilder(filter).build())
				.select(LAUNCH.ID.as(LAUNCH_ID),
						LAUNCH.NAME.as(NAME),
						LAUNCH.NUMBER.as(LAUNCH_NUMBER),
						LAUNCH.STATUS.as(STATUS),
						LAUNCH.START_TIME.as(START_TIME),
						LAUNCH.END_TIME.as(END_TIME)
				)
				.from(LAUNCH)
				.where(LAUNCH.STATUS.in(Optional.ofNullable(contentFields.get(EXECUTIONS_KEY)).orElseGet(Collections::emptyList)))
				.and(LAUNCH.ID.in(commonSelect))
				.groupBy(LAUNCH.ID, LAUNCH.NAME, LAUNCH.NUMBER, LAUNCH.STATUS, LAUNCH.START_TIME, LAUNCH.END_TIME)
				.fetchInto(LaunchesDurationContent.class);

		launchesDurationContents.forEach(content -> content.setDuration(content.getEndTime().getTime() - content.getStartTime().getTime()));

		return launchesDurationContents;
	}

	@Override
	public List<NotPassedCasesContent> notPassedCasesStatistics(Filter filter, Map<String, List<String>> contentFields, int limit) {

		Select commonSelect = buildCommonSelectWithLimit(LAUNCHES, limit);

		return dsl.with(LAUNCHES)
				.as(QueryBuilder.newBuilder(filter).build())
				.select(LAUNCH.ID.as(LAUNCH_ID),
						LAUNCH.NUMBER.as(LAUNCH_NUMBER),
						LAUNCH.NAME.as(NAME),
						LAUNCH.START_TIME.as(START_TIME),
						(sum(when(EXECUTION_STATISTICS.ES_STATUS.equal(JStatusEnum.PASSED.getLiteral()), ZERO_QUERY_VALUE).otherwise(
								EXECUTION_STATISTICS.ES_COUNTER))).cast(Double.class)
								.mul(PERCENTAGE_MULTIPLIER)
								.div(sum(EXECUTION_STATISTICS.ES_COUNTER))
								.as(PERCENTAGE)
				)
				.from(LAUNCH)
				.join(EXECUTION_STATISTICS)
				.on(LAUNCH.ID.eq(EXECUTION_STATISTICS.LAUNCH_ID))
				.and(EXECUTION_STATISTICS.ES_STATUS.in(Optional.ofNullable(contentFields.get(EXECUTIONS_KEY))
						.orElseGet(Collections::emptyList)))
				.and(EXECUTION_STATISTICS.LAUNCH_ID.in(commonSelect))
				.groupBy(LAUNCH.ID, LAUNCH.NUMBER, LAUNCH.NAME, LAUNCH.START_TIME)
				.fetchInto(NotPassedCasesContent.class);
	}

	@Override
	public List<Launch> launchesTableStatistics(Filter filter, Map<String, List<String>> contentFields, int limit) {

		final boolean executionsFlag = Optional.ofNullable(contentFields.get(EXECUTIONS_KEY)).isPresent();
		final boolean defectsFlag = Optional.ofNullable(contentFields.get(DEFECTS_KEY)).isPresent();

		Select commonSelect = buildCommonSelectWithLimit(LAUNCHES, limit);

		Set<Field<?>> commonSelectFields = buildColumnsSelect(contentFields.get(TABLE_COLUMN_KEY));

		Set<Field<?>> executionsSelectFields = new LinkedHashSet<>(commonSelectFields);

		if (null != contentFields.get(EXECUTIONS_KEY)) {
			Collections.addAll(commonSelectFields,
					field(name(EXECUTION_STATS, EXECUTION_STATISTICS.ES_STATUS.getName())),
					field(name(EXECUTION_STATS, EXECUTION_STATISTICS.ES_COUNTER.getName()))
			);
			Collections.addAll(executionsSelectFields, EXECUTION_STATISTICS.ES_STATUS, EXECUTION_STATISTICS.ES_COUNTER);
		}

		if (null != contentFields.get(DEFECTS_KEY)) {
			Collections.addAll(commonSelectFields, ISSUE_TYPE.LOCATOR, ISSUE_STATISTICS.IS_COUNTER);
		}

		SelectJoinStep<Record> selectJoinStep = dsl.with(LAUNCHES)
				.as(QueryBuilder.newBuilder(filter).build())
				.select(commonSelectFields)
				.from(LAUNCH);

		return LAUNCH_FETCHER.apply(buildJoins(selectJoinStep,
				commonSelectFields,
				executionsSelectFields,
				commonSelect,
				contentFields,
				executionsFlag,
				defectsFlag
		).fetch());

	}

	@Override
	public List<ActivityContent> activityStatistics(Filter filter, String login, Map<String, List<String>> activityTypes, int limit) {

		Select commonSelect = buildCommonSelectWithLimit(ACTIVITIES, limit);

		return dsl.with(ACTIVITIES)
				.as(QueryBuilder.newBuilder(filter).build())
				.select(ACTIVITY.ID.as(ACTIVITY_ID),
						ACTIVITY.ACTION.as(ACTION_TYPE),
						ACTIVITY.ENTITY.as(ENTITY),
						ACTIVITY.CREATION_DATE.as(LAST_MODIFIED),
						USERS.LOGIN.as(USER_LOGIN),
						PROJECT.ID.as(PROJECT_ID),
						PROJECT.NAME.as(PROJECT_NAME)
				)
				.from(ACTIVITY)
				.leftJoin(USERS)
				.on(ACTIVITY.USER_ID.eq(USERS.ID))
				.leftJoin(PROJECT)
				.on(ACTIVITY.PROJECT_ID.eq(PROJECT.ID))
				.where(USERS.LOGIN.eq(login))
				.and(ACTIVITY.ACTION.in(activityTypes.get(ACTIVITY_TYPE)))
				.and(ACTIVITY.ID.in(commonSelect))
				.groupBy(ACTIVITY.ID, ACTIVITY.ACTION, ACTIVITY.ENTITY, ACTIVITY.CREATION_DATE, USERS.LOGIN, PROJECT.ID, PROJECT.NAME)
				.fetchInto(ActivityContent.class);
	}

	@Override
	public Map<String, List<UniqueBugContent>> uniqueBugStatistics(Filter filter, int limit) {

		List<UniqueBugContent> uniqueBugContents = dsl.select(TICKET.TICKET_ID.as(TICKET_ID),
				TICKET.SUBMIT_DATE.as(SUBMIT_DATE),
				TICKET.URL.as(URL),
				TEST_ITEM.ITEM_ID.as(TEST_ITEM_ID),
				TEST_ITEM.NAME.as(TEST_ITEM_NAME),
				USERS.LOGIN.as(SUBMITTER)

		)
				.from(TICKET)
				.join(ISSUE_TICKET)
				.on(TICKET.ID.eq(ISSUE_TICKET.TICKET_ID))
				.join(ISSUE)
				.on(ISSUE_TICKET.ISSUE_ID.eq(ISSUE.ISSUE_ID))
				.join(TEST_ITEM_RESULTS)
				.on(ISSUE.ISSUE_ID.eq(TEST_ITEM_RESULTS.RESULT_ID))
				.join(TEST_ITEM_STRUCTURE)
				.on(TEST_ITEM_RESULTS.RESULT_ID.eq(TEST_ITEM_STRUCTURE.STRUCTURE_ID))
				.join(TEST_ITEM)
				.on(TEST_ITEM_STRUCTURE.STRUCTURE_ID.eq(TEST_ITEM.ITEM_ID))
				.join(USERS)
				.on(TICKET.SUBMITTER_ID.eq(USERS.ID))
				.fetchInto(UniqueBugContent.class);

		return uniqueBugContents.stream().collect(Collectors.groupingBy(UniqueBugContent::getTicketId));
	}

	@Override
	public List<FlakyCasesTableContent> flakyCasesStatistics(Filter filter, int limit) {

		Select commonSelect = buildCommonSelectWithLimit(LAUNCHES, limit);

		return dsl.select(field(name(FLAKY_TABLE_RESULTS, TEST_ITEM.UNIQUE_ID.getName())).as(UNIQUE_ID),
				field(name(FLAKY_TABLE_RESULTS, TEST_ITEM.NAME.getName())).as(ITEM_NAME),
				DSL.arrayAgg(field(name(FLAKY_TABLE_RESULTS, TEST_ITEM_RESULTS.STATUS.getName()))).as(STATUSES),
				sum(field(name(FLAKY_TABLE_RESULTS, SWITCH_FLAG)).cast(Long.class)).as(FLAKY_COUNT),
				sum(field(name(FLAKY_TABLE_RESULTS, TOTAL)).cast(Long.class)).as(TOTAL)
		)
				.from(dsl.select(TEST_ITEM.UNIQUE_ID,
						TEST_ITEM.NAME,
						TEST_ITEM_RESULTS.STATUS,
						when(TEST_ITEM_RESULTS.STATUS.notEqual(lag(TEST_ITEM_RESULTS.STATUS).over(orderBy(TEST_ITEM.ITEM_ID))),
								1
						).otherwise(ZERO_QUERY_VALUE).as(SWITCH_FLAG),
						count(TEST_ITEM_RESULTS.STATUS).as(TOTAL)
				)
						.from(LAUNCH)
						.join(TEST_ITEM_STRUCTURE)
						.on(LAUNCH.ID.eq(TEST_ITEM_STRUCTURE.LAUNCH_ID))
						.join(TEST_ITEM_RESULTS)
						.on(TEST_ITEM_STRUCTURE.STRUCTURE_ID.eq(TEST_ITEM_RESULTS.RESULT_ID))
						.join(TEST_ITEM)
						.on(TEST_ITEM_STRUCTURE.STRUCTURE_ID.eq(TEST_ITEM.ITEM_ID))
						.where(LAUNCH.ID.in(commonSelect))
						.and(TEST_ITEM.TYPE.eq(JTestItemTypeEnum.STEP))
						.groupBy(TEST_ITEM.ITEM_ID, TEST_ITEM_RESULTS.STATUS, TEST_ITEM.UNIQUE_ID, TEST_ITEM.NAME)
						.asTable(FLAKY_TABLE_RESULTS))
				.groupBy(field(name(FLAKY_TABLE_RESULTS, TEST_ITEM.UNIQUE_ID.getName())),
						field(name(FLAKY_TABLE_RESULTS, TEST_ITEM.NAME.getName()))
				)
				.fetchInto(FlakyCasesTableContent.class);
	}

	private Select buildCommonSelectWithLimit(String alias, int limit) {
		return dsl.select(field(name(alias, ID)).cast(Long.class)).from(name(alias)).limit(limit);
	}

	private Field<?> fieldName(TableField tableField) {
		return field(name(tableField.getName()));
	}

	private List<LaunchStatisticsContent> buildResultLaunchesStatistics(List<LaunchStatisticsContent> launchStatisticsContents) {

		//		Map<Long, Map<String, Integer>> issuesMap = launchStatisticsContents.stream()
		//				.collect(Collectors.groupingBy(LaunchStatisticsContent::getLaunchId,
		//						Collectors.groupingBy(LaunchStatisticsContent::getIssueName,
		//								Collectors.summingInt(LaunchStatisticsContent::getIssueCount)
		//						)
		//				));
		//
		//		List<LaunchStatisticsContent> resultLaunchStatisticsContent = new ArrayList<>(issuesMap.size());
		//
		//		issuesMap.forEach((key, value) -> launchStatisticsContents.stream()
		//				.filter(content -> Objects.equals(key, content.getLaunchId()))
		//				.findFirst()
		//				.ifPresent(content -> {
		//					Map<String, Integer> executions = new HashMap<>();
		//					Map<String, Integer> defects = new HashMap<>();
		//					value.keySet().forEach(name -> {
		//						if (StatusEnum.isPresent(name)) {
		//							executions.put(name, value.get(name));
		//						} else {
		//							defects.put(name, value.get(name));
		//						}
		//					});
		//					content.setDefectsMap(defects);
		//					content.setExecutionsMap(executions);
		//					resultLaunchStatisticsContent.add(content);
		//				}));

		return null;
	}

	private SelectHavingStep<Record> buildJoins(SelectJoinStep<Record> select, Set<Field<?>> commonSelectFields,
			Set<Field<?>> executionsSelectFields, Select commonSelect, Map<String, List<String>> contentFields, boolean executionsFlag,
			boolean defectsFlag) {

		if (executionsFlag) {
			select = select.join(select(executionsSelectFields).from(LAUNCH)
					.join(EXECUTION_STATISTICS)
					.on(LAUNCH.ID.eq(EXECUTION_STATISTICS.LAUNCH_ID))
					.where(EXECUTION_STATISTICS.ES_STATUS.in(Optional.ofNullable(contentFields.get(EXECUTIONS_KEY))
							.orElseGet(Collections::emptyList)))
					.and(EXECUTION_STATISTICS.LAUNCH_ID.in(commonSelect))
					.groupBy(executionsSelectFields)
					.asTable(EXECUTION_STATS)).on(LAUNCH.ID.eq(field(name(EXECUTION_STATS, ID)).cast(Long.class)));
		}

		if (defectsFlag) {
			return select.join(ISSUE_STATISTICS)
					.on(LAUNCH.ID.eq(ISSUE_STATISTICS.LAUNCH_ID))
					.join(ISSUE_TYPE)
					.on(ISSUE_STATISTICS.ISSUE_TYPE_ID.eq(ISSUE_TYPE.ID))
					.join(ISSUE_GROUP)
					.on(ISSUE_TYPE.ISSUE_GROUP_ID.eq(ISSUE_GROUP.ISSUE_GROUP_ID))
					.where(ISSUE_TYPE.LOCATOR.in(Optional.ofNullable(contentFields.get(DEFECTS_KEY)).orElseGet(Collections::emptyList)))
					.and(ISSUE_STATISTICS.LAUNCH_ID.in(commonSelect))
					.groupBy(commonSelectFields);
		}
		return select;
	}

	private Set<Field<?>> buildColumnsSelect(List<String> tableColumns) {

		Set<Field<?>> resultSelectFields = new LinkedHashSet<>();
		resultSelectFields.add(LAUNCH.ID);

		if (CollectionUtils.isEmpty(tableColumns)) {
			return resultSelectFields;
		}

		tableColumns.forEach(columnName -> {
			Optional<Field<?>> selectField = Optional.ofNullable(LAUNCH.field(columnName));
			selectField.ifPresent(resultSelectFields::add);
		});

		return resultSelectFields;
	}

	//	private Set<Field<?>> fillWithCustomFields(Set<Field<?>> fieldSet, Field<?>... fields) {
	//		fieldSet.addAll(Arrays.stream(fields).collect(Collectors.toSet()));
	//		return fieldSet;
	//	}
}
