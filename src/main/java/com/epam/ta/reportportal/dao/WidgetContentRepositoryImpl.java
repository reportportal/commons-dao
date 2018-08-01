package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.commons.querygen.Filter;
import com.epam.ta.reportportal.commons.querygen.QueryBuilder;
import com.epam.ta.reportportal.entity.launch.Launch;
import com.epam.ta.reportportal.entity.widget.content.*;
import com.epam.ta.reportportal.exception.ReportPortalException;
import com.epam.ta.reportportal.jooq.enums.JIssueGroupEnum;
import com.epam.ta.reportportal.jooq.enums.JStatusEnum;
import com.epam.ta.reportportal.jooq.enums.JTestItemTypeEnum;
import com.google.common.base.CaseFormat;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Select;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

import static com.epam.ta.reportportal.dao.WidgetContentRepositoryConstants.*;
import static com.epam.ta.reportportal.jooq.Tables.*;
import static com.epam.ta.reportportal.jooq.tables.JExecutionStatistics.EXECUTION_STATISTICS;
import static com.epam.ta.reportportal.jooq.tables.JIssueGroup.ISSUE_GROUP;
import static com.epam.ta.reportportal.jooq.tables.JIssueStatistics.ISSUE_STATISTICS;
import static com.epam.ta.reportportal.jooq.tables.JIssueType.ISSUE_TYPE;
import static com.epam.ta.reportportal.jooq.tables.JLaunch.LAUNCH;
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
	public List<StatisticsContent> overallStatisticsContent(Filter filter, Map<String, List<String>> contentFields, boolean latest,
			int limit) {
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
				.and(EXECUTION_STATISTICS.ES_STATUS.in(Optional.ofNullable(contentFields.get(EXECUTIONS_KEY))
						.orElse(Collections.emptyList())))
				.groupBy(EXECUTION_STATISTICS.ES_STATUS)
				.unionAll(dsl.select(ISSUE_TYPE.LOCATOR.as("field"), DSL.sumDistinct(ISSUE_STATISTICS.IS_COUNTER))
						.from(LAUNCH)
						.join(ISSUE_STATISTICS)
						.on(LAUNCH.ID.eq(ISSUE_STATISTICS.LAUNCH_ID))
						.join(ISSUE_TYPE)
						.on(ISSUE_STATISTICS.ISSUE_TYPE_ID.eq(ISSUE_TYPE.ID))
						.where(ISSUE_STATISTICS.LAUNCH_ID.in(commonSelect))
						.and(ISSUE_TYPE.LOCATOR.in(Optional.ofNullable(contentFields.get(DEFECTS_KEY)).orElse(Collections.emptyList())))
						.groupBy(ISSUE_TYPE.LOCATOR))
				.limit(limit)
				.fetchInto(StatisticsContent.class);
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
				.where(DSL.field(DSL.name(CRITERIA)).greaterThan(0))
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
				.where(DSL.field(DSL.name(CRITERIA)).greaterThan(0))
				.orderBy(DSL.field(DSL.name(CRITERIA)).desc(), DSL.field(DSL.name(TOTAL)).asc())
				.limit(limit)
				.fetchInto(MostFailedContent.class);
	}

	@Override
	public Map<Integer, Map<String, Integer>> launchStatistics(Filter filter, Map<String, List<String>> contentFields) {

		List<LaunchStatisticsContent> launchStatisticsContents = dsl.with(LAUNCHES)
				.as(QueryBuilder.newBuilder(filter).build())
				.select(LAUNCH.NUMBER.as("launchNumber"), ISSUE_TYPE.LOCATOR.as("issueName"), ISSUE_STATISTICS.IS_COUNTER.as("issueCount"))
				.from(LAUNCH)
				.join(ISSUE_STATISTICS)
				.on(LAUNCH.ID.eq(ISSUE_STATISTICS.LAUNCH_ID))
				.join(ISSUE_TYPE)
				.on(ISSUE_STATISTICS.ISSUE_TYPE_ID.eq(ISSUE_TYPE.ID))
				.where(ISSUE_TYPE.LOCATOR.in(Optional.ofNullable(contentFields.get(DEFECTS_KEY)).orElseGet(Collections::emptyList)))
				.groupBy(LAUNCH.NUMBER, ISSUE_TYPE.LOCATOR, ISSUE_STATISTICS.IS_COUNTER)
				.unionAll(dsl.select(LAUNCH.NUMBER.as("launchNumber"),
						LAUNCH.STATUS.cast(String.class),
						ISSUE_STATISTICS.IS_COUNTER.as("issueCount")
				)
						.from(LAUNCH)
						.join(ISSUE_STATISTICS)
						.on(LAUNCH.ID.eq(ISSUE_STATISTICS.LAUNCH_ID))
						.where(LAUNCH.STATUS.in(Optional.ofNullable(contentFields.get(EXECUTIONS_KEY)).orElseGet(Collections::emptyList)))
						.groupBy(LAUNCH.NUMBER, LAUNCH.STATUS, ISSUE_STATISTICS.IS_COUNTER))
				.fetchInto(LaunchStatisticsContent.class);

		return launchStatisticsContents.stream()
				.collect(Collectors.groupingBy(LaunchStatisticsContent::getLaunchNumber,
						Collectors.groupingBy(LaunchStatisticsContent::getIssueName,
								Collectors.summingInt(LaunchStatisticsContent::getIssueCount)
						)
				));

	}

	@Override
	public List<InvestigatedStatisticsResult> investigatedStatistics(Filter filter, Map<String, List<String>> contentFields) {

		return dsl.with(LAUNCHES)
				.as(QueryBuilder.newBuilder(filter).build())
				.select(LAUNCH.NUMBER.as("launchNumber"),
						LAUNCH.NAME.as("name"),
						LAUNCH.START_TIME.as("startTime"),
						(sum(when(ISSUE_GROUP.ISSUE_GROUP_.equal(JIssueGroupEnum.TO_INVESTIGATE),
								0
						).otherwise(ISSUE_STATISTICS.IS_COUNTER))).cast(Double.class)
								.mul(100)
								.div(sum(ISSUE_STATISTICS.IS_COUNTER))
								.as("investigatedPercentage"),
						(sum(when(ISSUE_GROUP.ISSUE_GROUP_.notEqual(JIssueGroupEnum.TO_INVESTIGATE),
								0
						).otherwise(ISSUE_STATISTICS.IS_COUNTER))).cast(Double.class)
								.mul(100)
								.div(sum(ISSUE_STATISTICS.IS_COUNTER))
								.as("notInvestigatedPercentage")
				)
				.from(LAUNCH)
				.join(ISSUE_STATISTICS)
				.on(ISSUE_STATISTICS.LAUNCH_ID.eq(LAUNCH.ID))
				.join(ISSUE_TYPE)
				.on(ISSUE_TYPE.ID.eq(ISSUE_STATISTICS.ISSUE_TYPE_ID))
				.join(ISSUE_GROUP)
				.on(ISSUE_GROUP.ISSUE_GROUP_ID.eq(ISSUE_TYPE.ISSUE_GROUP_ID))
				.groupBy(LAUNCH.NUMBER, LAUNCH.NAME, LAUNCH.START_TIME)
				.orderBy(LAUNCH.NUMBER)
				.fetchInto(InvestigatedStatisticsResult.class);
	}

	@Override
	public PassStatisticsResult launchPassPerLaunchStatistics(Filter filter, Map<String, List<String>> contentFields, Launch launch) {
		return dsl.with(LAUNCHES)
				.as(QueryBuilder.newBuilder(filter).build())
				.select(sum(when(EXECUTION_STATISTICS.ES_STATUS.equal(JStatusEnum.PASSED.getLiteral()),
						EXECUTION_STATISTICS.ES_COUNTER
						)).as("passed"),
						sum(when(EXECUTION_STATISTICS.ES_STATUS.in(Optional.ofNullable(contentFields.get(EXECUTIONS_KEY))
								.orElseGet(Collections::emptyList)), EXECUTION_STATISTICS.ES_COUNTER)).as("total")
				)
				.from(LAUNCH)
				.join(EXECUTION_STATISTICS)
				.on(LAUNCH.ID.eq(EXECUTION_STATISTICS.LAUNCH_ID))
				.where(LAUNCH.NAME.eq(launch.getName()))
				.and(EXECUTION_STATISTICS.ES_STATUS.in(Optional.ofNullable(contentFields.get(EXECUTIONS_KEY))
						.orElseGet(Collections::emptyList)))
				.and(LAUNCH.NUMBER.eq(launch.getNumber().intValue()))
				.groupBy(LAUNCH.NAME)
				.fetchInto(PassStatisticsResult.class)
				.stream()
				.findFirst()
				.orElseThrow(() -> new ReportPortalException("Widget for launch name: " + launch.getName() + " not found"));
	}

	@Override
	public PassStatisticsResult summaryPassStatistics(Filter filter, Map<String, List<String>> contentFields) {
		return dsl.with(LAUNCHES)
				.as(QueryBuilder.newBuilder(filter).build())
				.select(sum(when(EXECUTION_STATISTICS.ES_STATUS.equal(JStatusEnum.PASSED.getLiteral()),
						EXECUTION_STATISTICS.ES_COUNTER
						)).as("passed"),
						sum(when(EXECUTION_STATISTICS.ES_STATUS.in(Optional.ofNullable(contentFields.get(EXECUTIONS_KEY))
								.orElseGet(Collections::emptyList)), EXECUTION_STATISTICS.ES_COUNTER)).as("total")
				)
				.from(EXECUTION_STATISTICS)
				.where(EXECUTION_STATISTICS.ES_STATUS.in(Optional.ofNullable(contentFields.get(EXECUTIONS_KEY))
						.orElseGet(Collections::emptyList)))
				.fetchInto(PassStatisticsResult.class)
				.stream()
				.findFirst()
				.orElseThrow(() -> new ReportPortalException("No results for filter were found"));
	}

	@Override
	public List<CasesTrendContent> casesTrendStatistics(Filter filter, Map<String, List<String>> contentFields) {
		return dsl.with(LAUNCHES)
				.as(QueryBuilder.newBuilder(filter).build())
				.select(LAUNCH.NUMBER.as("launchNumber"),
						LAUNCH.UUID.as("launchId"),
						LAUNCH.START_TIME.as("startTime"),
						LAUNCH.NAME.as("launchName"),
						sum(EXECUTION_STATISTICS.ES_COUNTER).as("total"),
						sum(EXECUTION_STATISTICS.ES_COUNTER).sub(lag(sum(EXECUTION_STATISTICS.ES_COUNTER)).over().orderBy(LAUNCH.NUMBER))
								.as("delta")
				)
				.from(EXECUTION_STATISTICS)
				.join(LAUNCH)
				.on(EXECUTION_STATISTICS.LAUNCH_ID.eq(LAUNCH.ID))
				.groupBy(LAUNCH.NUMBER, LAUNCH.UUID, LAUNCH.START_TIME, LAUNCH.NAME)
				.fetchInto(CasesTrendContent.class);
	}

	@Override
	public Map<Integer, Map<String, Integer>> bugTrendStatistics(Filter filter, Map<String, List<String>> contentFields) {

		List<LaunchStatisticsContent> launchStatisticsContents = dsl.with(LAUNCHES)
				.as(QueryBuilder.newBuilder(filter).build())
				.select(LAUNCH.NUMBER.as("launchNumber"), ISSUE_TYPE.LOCATOR.as("issueName"), ISSUE_STATISTICS.IS_COUNTER.as("issueCount"))
				.from(LAUNCH)
				.join(ISSUE_STATISTICS)
				.on(LAUNCH.ID.eq(ISSUE_STATISTICS.LAUNCH_ID))
				.join(ISSUE_TYPE)
				.on(ISSUE_STATISTICS.ISSUE_TYPE_ID.eq(ISSUE_TYPE.ID))
				.where(ISSUE_TYPE.LOCATOR.in(Optional.ofNullable(contentFields.get(DEFECTS_KEY)).orElseGet(Collections::emptyList)))
				.groupBy(LAUNCH.NUMBER, ISSUE_TYPE.LOCATOR, ISSUE_STATISTICS.IS_COUNTER)
				.fetchInto(LaunchStatisticsContent.class);

		return launchStatisticsContents.stream()
				.collect(Collectors.groupingBy(LaunchStatisticsContent::getLaunchNumber,
						Collectors.groupingBy(LaunchStatisticsContent::getIssueName,
								Collectors.summingInt(LaunchStatisticsContent::getIssueCount)
						)
				));

	}

	@Override
	public Map<Integer, Map<String, Double>> launchesComparisonStatistics(Filter filter, Map<String, List<String>> contentFields,
			Long... launchIds) {

		List<Long> launchIdList = Arrays.stream(launchIds).collect(Collectors.toList());

		List<ComparisonStatisticsContent> launchStatisticsContents = dsl.with(LAUNCHES)
				.as(QueryBuilder.newBuilder(filter).build())
				.select(LAUNCH.NUMBER.as("launchNumber"),
						ISSUE_GROUP.ISSUE_GROUP_.cast(String.class).as("issueName"),
						(sum(ISSUE_STATISTICS.IS_COUNTER)).cast(Double.class)
								.div(sum(sum(ISSUE_STATISTICS.IS_COUNTER)).over().partitionBy(LAUNCH.NUMBER))
								.mul(100)
								.as("issuePercentage")
				)
				.from(LAUNCH)
				.join(ISSUE_STATISTICS)
				.on(LAUNCH.ID.eq(ISSUE_STATISTICS.LAUNCH_ID))
				.join(ISSUE_TYPE)
				.on(ISSUE_STATISTICS.ISSUE_TYPE_ID.eq(ISSUE_TYPE.ID))
				.join(ISSUE_GROUP)
				.on(ISSUE_TYPE.ISSUE_GROUP_ID.eq(ISSUE_GROUP.ISSUE_GROUP_ID))
				.and(LAUNCH.ID.in(launchIdList))
				.and(ISSUE_GROUP.ISSUE_GROUP_.in(Optional.ofNullable(contentFields.get(DEFECTS_KEY)).orElseGet(Collections::emptyList)))
				.groupBy(LAUNCH.NUMBER, ISSUE_GROUP.ISSUE_GROUP_)
				.unionAll(dsl.select(LAUNCH.NUMBER.as("launchNumber"),
						EXECUTION_STATISTICS.ES_STATUS.as("issueName"),
						(sum(EXECUTION_STATISTICS.ES_COUNTER)).cast(Double.class)
								.div(sum(sum(EXECUTION_STATISTICS.ES_COUNTER)).over().partitionBy(LAUNCH.NUMBER))
								.mul(100)
								.as("issuePercentage")
				)
						.from(LAUNCH)
						.join(EXECUTION_STATISTICS)
						.on(LAUNCH.ID.eq(EXECUTION_STATISTICS.LAUNCH_ID))
						.and(LAUNCH.ID.in(launchIdList))
						.and(EXECUTION_STATISTICS.ES_STATUS.in(Optional.ofNullable(contentFields.get(EXECUTIONS_KEY))
								.orElseGet(Collections::emptyList)))
						.groupBy(LAUNCH.NUMBER, EXECUTION_STATISTICS.ES_STATUS))
				.fetchInto(ComparisonStatisticsContent.class);

		return launchStatisticsContents.stream()
				.collect(Collectors.groupingBy(ComparisonStatisticsContent::getLaunchNumber, Collectors.groupingBy(
						ComparisonStatisticsContent::getIssueName,
						Collectors.summingDouble(ComparisonStatisticsContent::getIssuePercentage)
				)));
	}

	@Override
	public List<LaunchesDurationContent> launchesDurationStatistics(Filter filter, Map<String, List<String>> contentFields) {
		List<LaunchesDurationContent> launchesDurationContents = dsl.with(LAUNCHES)
				.as(QueryBuilder.newBuilder(filter).build())
				.select(LAUNCH.NAME.as("name"),
						LAUNCH.NUMBER.as("number"),
						LAUNCH.STATUS.as("status"),
						LAUNCH.START_TIME.as("startTime"),
						LAUNCH.END_TIME.as("endTime")
				)
				.from(LAUNCH)
				.where(LAUNCH.STATUS.in(Optional.ofNullable(contentFields.get(EXECUTIONS_KEY)).orElseGet(Collections::emptyList)))
				.groupBy(LAUNCH.NAME, LAUNCH.NUMBER, LAUNCH.STATUS, LAUNCH.START_TIME, LAUNCH.END_TIME)
				.fetchInto(LaunchesDurationContent.class);

		launchesDurationContents.forEach(content -> content.setDuration(content.getEndTime().getTime() - content.getStartTime().getTime()));

		return launchesDurationContents;
	}

	@Override
	public List<NotPassedCasesContent> notPassedCasesStatistics(Filter filter, Map<String, List<String>> contentFields) {
		return dsl.with(LAUNCHES)
				.as(QueryBuilder.newBuilder(filter).build())
				.select(LAUNCH.NUMBER.as("number"), LAUNCH.NAME.as("name"), LAUNCH.START_TIME.as("startTime"), (sum(when(
						EXECUTION_STATISTICS.ES_STATUS.equal(JStatusEnum.PASSED.getLiteral()),
						0
				).otherwise(EXECUTION_STATISTICS.ES_COUNTER))).cast(Double.class)
						.mul(100)
						.div(sum(EXECUTION_STATISTICS.ES_COUNTER))
						.as("percentage"))
				.from(LAUNCH)
				.join(EXECUTION_STATISTICS)
				.on(LAUNCH.ID.eq(EXECUTION_STATISTICS.LAUNCH_ID))
				.and(EXECUTION_STATISTICS.ES_STATUS.in(Optional.ofNullable(contentFields.get(EXECUTIONS_KEY))
						.orElseGet(Collections::emptyList)))
				.groupBy(LAUNCH.NUMBER, LAUNCH.NAME, LAUNCH.START_TIME)
				.fetchInto(NotPassedCasesContent.class);
	}

	@Override
	public List<LaunchesTableContent> launchesTableStatistics(Filter filter, Map<String, List<String>> contentFields,
			List<String> tableColumns) {

		Set<Field<?>> selectFields = buildColumnsSelect(tableColumns,
				LAUNCH.ID.as("launchId"),
				LAUNCH.STATUS.as("status"),
				ISSUE_TYPE.LOCATOR.as("issueName"),
				ISSUE_STATISTICS.IS_COUNTER.as("issueCount")
		);

		List<LaunchesTableContent> launchesTableContents = dsl.with(LAUNCHES)
				.as(QueryBuilder.newBuilder(filter).build())
				.select(selectFields)
				.from(LAUNCH)
				.leftJoin(ISSUE_STATISTICS)
				.on(LAUNCH.ID.eq(ISSUE_STATISTICS.LAUNCH_ID))
				.leftJoin(ISSUE_TYPE)
				.on(ISSUE_STATISTICS.ISSUE_TYPE_ID.eq(ISSUE_TYPE.ID))
				.where(ISSUE_TYPE.LOCATOR.in(Optional.ofNullable(contentFields.get(DEFECTS_KEY)).orElseGet(Collections::emptyList)))
				.groupBy(selectFields)
				.orderBy(LAUNCH.ID)
				.fetchInto(LaunchesTableContent.class);

		Map<Long, Map<String, Integer>> issuesMap = launchesTableContents.stream()
				.collect(Collectors.groupingBy(LaunchesTableContent::getLaunchId,
						Collectors.groupingBy(LaunchesTableContent::getIssueName,
								Collectors.summingInt(LaunchesTableContent::getIssueCount)
						)
				));

		List<LaunchesTableContent> resultLaunchesTableContent = new ArrayList<>(issuesMap.size());

		issuesMap.forEach((key, value) -> launchesTableContents.stream()
				.filter(content -> Objects.equals(key, content.getLaunchId()))
				.findFirst()
				.ifPresent(content -> {
					content.setIssueStatisticsMap(value);
					resultLaunchesTableContent.add(content);
				}));

		return resultLaunchesTableContent;

	}

	private Set<Field<?>> buildColumnsSelect(List<String> tableColumns, Field<?>... selectFields) {

		Set<Field<?>> resultSelectFields = new HashSet<>();
		Collections.addAll(resultSelectFields, selectFields);

		tableColumns.forEach(columnName -> {
			Optional<Field<?>> selectField = Optional.ofNullable(LAUNCH.field(columnName)
					.as(CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, columnName)));
			selectField.ifPresent(resultSelectFields::add);
		});

		return resultSelectFields;
	}
}
