/*
 * Copyright 2019 EPAM Systems
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

import com.epam.ta.reportportal.commons.querygen.*;
import com.epam.ta.reportportal.dao.util.QueryUtils;
import com.epam.ta.reportportal.dao.util.TimestampUtils;
import com.epam.ta.reportportal.entity.enums.LaunchModeEnum;
import com.epam.ta.reportportal.entity.enums.StatusEnum;
import com.epam.ta.reportportal.entity.enums.TestItemIssueGroup;
import com.epam.ta.reportportal.entity.enums.TestItemTypeEnum;
import com.epam.ta.reportportal.entity.item.NestedStep;
import com.epam.ta.reportportal.entity.item.PathName;
import com.epam.ta.reportportal.entity.item.TestItem;
import com.epam.ta.reportportal.entity.item.history.TestItemHistory;
import com.epam.ta.reportportal.entity.item.issue.IssueType;
import com.epam.ta.reportportal.entity.statistics.Statistics;
import com.epam.ta.reportportal.entity.statistics.StatisticsField;
import com.epam.ta.reportportal.jooq.Tables;
import com.epam.ta.reportportal.jooq.enums.JIssueGroupEnum;
import com.epam.ta.reportportal.jooq.enums.JLaunchModeEnum;
import com.epam.ta.reportportal.jooq.enums.JStatusEnum;
import com.epam.ta.reportportal.jooq.tables.JTestItem;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.math.NumberUtils;
import org.jooq.Condition;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.epam.ta.reportportal.commons.querygen.FilterTarget.FILTERED_ID;
import static com.epam.ta.reportportal.commons.querygen.FilterTarget.FILTERED_QUERY;
import static com.epam.ta.reportportal.commons.querygen.QueryBuilder.retrieveOffsetAndApplyBoundaries;
import static com.epam.ta.reportportal.commons.querygen.constant.GeneralCriteriaConstant.CRITERIA_LAUNCH_ID;
import static com.epam.ta.reportportal.commons.querygen.constant.GeneralCriteriaConstant.CRITERIA_START_TIME;
import static com.epam.ta.reportportal.commons.querygen.constant.LaunchCriteriaConstant.CRITERIA_LAUNCH_MODE;
import static com.epam.ta.reportportal.commons.querygen.constant.TestItemCriteriaConstant.CRITERIA_TEST_CASE_HASH;
import static com.epam.ta.reportportal.commons.querygen.constant.TestItemCriteriaConstant.CRITERIA_UNIQUE_ID;
import static com.epam.ta.reportportal.dao.constant.LogRepositoryConstants.ITEM;
import static com.epam.ta.reportportal.dao.constant.LogRepositoryConstants.LOGS;
import static com.epam.ta.reportportal.dao.constant.TestItemRepositoryConstants.*;
import static com.epam.ta.reportportal.dao.constant.WidgetContentRepositoryConstants.*;
import static com.epam.ta.reportportal.dao.constant.WidgetRepositoryConstants.ID;
import static com.epam.ta.reportportal.dao.util.JooqFieldNameTransformer.fieldName;
import static com.epam.ta.reportportal.dao.util.RecordMappers.*;
import static com.epam.ta.reportportal.dao.util.ResultFetchers.PATH_NAMES_FETCHER;
import static com.epam.ta.reportportal.dao.util.ResultFetchers.TEST_ITEM_FETCHER;
import static com.epam.ta.reportportal.jooq.Tables.*;
import static com.epam.ta.reportportal.jooq.tables.JIssueType.ISSUE_TYPE;
import static com.epam.ta.reportportal.jooq.tables.JTestItem.TEST_ITEM;
import static com.epam.ta.reportportal.jooq.tables.JTestItemResults.TEST_ITEM_RESULTS;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;
import static org.jooq.impl.DSL.*;

/**
 * @author Pavel Bortnik
 */
@Repository
public class TestItemRepositoryCustomImpl implements TestItemRepositoryCustom {

	private static final String OUTER_ITEM_TABLE = "outer_item_table";
	private static final String INNER_ITEM_TABLE = "inner_item_table";
	private static final String TEST_CASE_ID_TABLE = "test_case_id_table";
	private static final String RESULT_OUTER_TABLE = "resultOuterTable";
	private static final String LATERAL_TABLE = "lateralTable";
	private static final String RESULT_INNER_TABLE = "resultInnerTable";

	private static final String CHILD_ITEM_TABLE = "child";

	private static final String ITEM_START_TIME = "itemStartTime";
	private static final String LAUNCH_START_TIME = "launchStartTime";
	private static final String ACCUMULATED_STATISTICS = "accumulated_statistics";

	private DSLContext dsl;

	@Autowired
	public void setDsl(DSLContext dsl) {
		this.dsl = dsl;
	}

	@Override
	public Set<Statistics> accumulateStatisticsByFilter(Queryable filter) {
		return dsl.fetch(DSL.with(FILTERED_QUERY)
				.as(QueryBuilder.newBuilder(filter, QueryUtils.collectJoinFields(filter)).build())
				.select(DSL.sum(STATISTICS.S_COUNTER).as(ACCUMULATED_STATISTICS), STATISTICS_FIELD.NAME)
				.from(STATISTICS)
				.join(DSL.table(DSL.name(FILTERED_QUERY)))
				.on(STATISTICS.ITEM_ID.eq(field(DSL.name(FILTERED_QUERY, FILTERED_ID), Long.class)))
				.join(STATISTICS_FIELD)
				.on(STATISTICS.STATISTICS_FIELD_ID.eq(STATISTICS_FIELD.SF_ID))
				.groupBy(STATISTICS_FIELD.NAME)
				.getQuery()).intoSet(r -> {
			Statistics statistics = new Statistics();
			StatisticsField statisticsField = new StatisticsField();
			statisticsField.setName(r.get(STATISTICS_FIELD.NAME));
			statistics.setStatisticsField(statisticsField);
			statistics.setCounter(ofNullable(r.get(ACCUMULATED_STATISTICS, Integer.class)).orElse(0));
			return statistics;
		});
	}

	@Override
	public Page<TestItem> findByFilter(boolean isLatest, Queryable launchFilter, Queryable testItemFilter, Pageable launchPageable,
			Pageable testItemPageable) {

		Table<? extends Record> launchesTable = QueryUtils.createQueryBuilderWithLatestLaunchesOption(launchFilter,
				launchPageable.getSort(),
				isLatest
		).with(launchPageable).build().asTable(LAUNCHES);

		Set<String> joinFields = QueryUtils.collectJoinFields(testItemFilter, testItemPageable.getSort());

		return PageableExecutionUtils.getPage(TEST_ITEM_FETCHER.apply(dsl.fetch(QueryBuilder.newBuilder(testItemFilter, joinFields)
						.with(testItemPageable)
						.addJointToStart(launchesTable,
								JoinType.JOIN,
								TEST_ITEM.LAUNCH_ID.eq(fieldName(launchesTable.getName(), ID).cast(Long.class))
						)
						.wrap()
						.withWrapperSort(testItemPageable.getSort())
						.build())),
				testItemPageable,
				() -> dsl.fetchCount(QueryBuilder.newBuilder(testItemFilter, joinFields)
						.addJointToStart(launchesTable,
								JoinType.JOIN,
								TEST_ITEM.LAUNCH_ID.eq(fieldName(launchesTable.getName(), ID).cast(Long.class))
						)
						.build())
		);
	}

	@Override
	public Page<TestItemHistory> loadItemsHistoryPage(Queryable filter, Pageable pageable, Long projectId, int historyDepth,
			boolean usingHash) {
		SelectQuery<? extends Record> filteringQuery = QueryBuilder.newBuilder(filter,
				QueryUtils.collectJoinFields(filter, pageable.getSort())
		).with(pageable.getSort()).build();
		Field<?> historyGroupingField = usingHash ? TEST_ITEM.TEST_CASE_HASH : TEST_ITEM.UNIQUE_ID;
		Page<String> historyBaseline = loadHistoryBaseline(filteringQuery, historyGroupingField, LAUNCH.PROJECT_ID.eq(projectId), pageable);

		List<TestItemHistory> itemHistories = historyBaseline.getContent().stream().map(value -> {
			List<Long> itemIds = loadHistoryItem(getHistoryFilter(filter, usingHash, value),
					pageable.getSort(),
					LAUNCH.PROJECT_ID.eq(projectId)
			).map(testItem -> getHistoryIds(testItem, usingHash, projectId, historyDepth - 1)).orElseGet(Collections::emptyList);
			return new TestItemHistory(value, itemIds);
		}).collect(Collectors.toList());

		return new PageImpl<>(itemHistories, pageable, historyBaseline.getTotalElements());

	}

	@Override
	public Page<TestItemHistory> loadItemsHistoryPage(Queryable filter, Pageable pageable, Long projectId, String launchName,
			int historyDepth, boolean usingHash) {
		SelectQuery<? extends Record> filteringQuery = QueryBuilder.newBuilder(filter,
				QueryUtils.collectJoinFields(filter, pageable.getSort())
		).with(pageable.getSort()).build();
		Field<?> historyGroupingField = usingHash ? TEST_ITEM.TEST_CASE_HASH : TEST_ITEM.UNIQUE_ID;
		Page<String> historyBaseline = loadHistoryBaseline(filteringQuery,
				historyGroupingField,
				LAUNCH.PROJECT_ID.eq(projectId).and(LAUNCH.NAME.eq(launchName)),
				pageable
		);

		List<TestItemHistory> itemHistories = historyBaseline.getContent().stream().map(value -> {
			List<Long> itemIds = loadHistoryItem(getHistoryFilter(filter, usingHash, value),
					pageable.getSort(),
					LAUNCH.PROJECT_ID.eq(projectId).and(LAUNCH.NAME.eq(launchName))
			).map(testItem -> getHistoryIds(testItem, usingHash, projectId, launchName, historyDepth - 1))
					.orElseGet(Collections::emptyList);
			return new TestItemHistory(value, itemIds);
		}).collect(Collectors.toList());

		return new PageImpl<>(itemHistories, pageable, historyBaseline.getTotalElements());

	}

	@Override
	public Page<TestItemHistory> loadItemsHistoryPage(Queryable filter, Pageable pageable, Long projectId, List<Long> launchIds,
			int historyDepth, boolean usingHash) {
		SelectQuery<? extends Record> filteringQuery = QueryBuilder.newBuilder(filter,
				QueryUtils.collectJoinFields(filter, pageable.getSort())
		).with(pageable.getSort()).addCondition(LAUNCH.ID.in(launchIds).and(LAUNCH.PROJECT_ID.eq(projectId))).build();

		Field<?> historyGroupingField = usingHash ? TEST_ITEM.TEST_CASE_HASH : TEST_ITEM.UNIQUE_ID;
		Page<String> historyBaseline = loadHistoryBaseline(filteringQuery,
				historyGroupingField,
				LAUNCH.ID.in(launchIds).and(LAUNCH.PROJECT_ID.eq(projectId)),
				pageable
		);

		List<TestItemHistory> itemHistories = historyBaseline.getContent().stream().map(value -> {
			List<Long> itemIds = loadHistoryItem(getHistoryFilter(filter, usingHash, value),
					pageable.getSort(),
					LAUNCH.ID.in(launchIds).and(LAUNCH.PROJECT_ID.eq(projectId))
			).map(testItem -> getHistoryIds(testItem, usingHash, projectId, historyDepth - 1)).orElseGet(Collections::emptyList);
			return new TestItemHistory(value, itemIds);
		}).collect(Collectors.toList());

		return new PageImpl<>(itemHistories, pageable, historyBaseline.getTotalElements());

	}

	@Override
	public Page<TestItemHistory> loadItemsHistoryPage(boolean isLatest, Queryable launchFilter, Queryable testItemFilter,
			Pageable launchPageable, Pageable testItemPageable, Long projectId, int historyDepth, boolean usingHash) {
		SelectQuery<? extends Record> filteringQuery = buildCompositeFilterHistoryQuery(isLatest,
				launchFilter,
				testItemFilter,
				launchPageable,
				testItemPageable
		);

		Field<?> historyGroupingField = usingHash ? TEST_ITEM.TEST_CASE_HASH : TEST_ITEM.UNIQUE_ID;
		Page<String> historyBaseline = loadHistoryBaseline(filteringQuery,
				historyGroupingField,
				LAUNCH.PROJECT_ID.eq(projectId),
				testItemPageable
		);

		List<TestItemHistory> itemHistories = historyBaseline.getContent().stream().map(value -> {
			List<Long> itemIds = loadHistoryItem(getHistoryFilter(testItemFilter, usingHash, value),
					testItemPageable.getSort(),
					LAUNCH.PROJECT_ID.eq(projectId)
			).map(testItem -> getHistoryIds(testItem, usingHash, projectId, historyDepth - 1)).orElseGet(Collections::emptyList);
			return new TestItemHistory(value, itemIds);
		}).collect(Collectors.toList());

		return new PageImpl<>(itemHistories, testItemPageable, historyBaseline.getTotalElements());
	}

	@Override
	public Page<TestItemHistory> loadItemsHistoryPage(boolean isLatest, Queryable launchFilter, Queryable testItemFilter,
			Pageable launchPageable, Pageable testItemPageable, Long projectId, String launchName, int historyDepth, boolean usingHash) {
		SelectQuery<? extends Record> filteringQuery = buildCompositeFilterHistoryQuery(isLatest,
				launchFilter,
				testItemFilter,
				launchPageable,
				testItemPageable
		);

		Field<?> historyGroupingField = usingHash ? TEST_ITEM.TEST_CASE_HASH : TEST_ITEM.UNIQUE_ID;
		Page<String> historyBaseline = loadHistoryBaseline(filteringQuery,
				historyGroupingField,
				LAUNCH.PROJECT_ID.eq(projectId).and(LAUNCH.NAME.eq(launchName)),
				testItemPageable
		);

		List<TestItemHistory> itemHistories = historyBaseline.getContent().stream().map(value -> {
			List<Long> itemIds = loadHistoryItem(getHistoryFilter(testItemFilter, usingHash, value),
					testItemPageable.getSort(),
					LAUNCH.PROJECT_ID.eq(projectId).and(LAUNCH.NAME.eq(launchName))
			).map(testItem -> getHistoryIds(testItem, usingHash, projectId, historyDepth - 1)).orElseGet(Collections::emptyList);
			return new TestItemHistory(value, itemIds);
		}).collect(Collectors.toList());

		return new PageImpl<>(itemHistories, testItemPageable, historyBaseline.getTotalElements());
	}

	private SelectQuery<? extends Record> buildCompositeFilterHistoryQuery(boolean isLatest, Queryable launchFilter,
			Queryable testItemFilter, Pageable launchPageable, Pageable testItemPageable) {
		Table<? extends Record> launchesTable = QueryUtils.createQueryBuilderWithLatestLaunchesOption(launchFilter,
				launchPageable.getSort(),
				isLatest
		).with(launchPageable).build().asTable(LAUNCHES);

		return QueryBuilder.newBuilder(testItemFilter, QueryUtils.collectJoinFields(testItemFilter, testItemPageable.getSort()))
				.with(testItemPageable.getSort())
				.addJointToStart(launchesTable,
						JoinType.JOIN,
						TEST_ITEM.LAUNCH_ID.eq(fieldName(launchesTable.getName(), ID).cast(Long.class))
				)
				.build();
	}

	private Page<String> loadHistoryBaseline(SelectQuery<? extends Record> filteringQuery, Field<?> historyGroupingField,
			Condition baselineCondition, Pageable pageable) {
		return PageableExecutionUtils.getPage(dsl.with(ITEMS)
						.as(filteringQuery)
						.select(historyGroupingField)
						.from(TEST_ITEM)
						.join(ITEMS)
						.on(TEST_ITEM.ITEM_ID.eq(fieldName(ITEMS, ID).cast(Long.class)))
						.join(LAUNCH)
						.on(TEST_ITEM.LAUNCH_ID.eq(LAUNCH.ID))
						.where(baselineCondition)
						.and(LAUNCH.MODE.eq(JLaunchModeEnum.DEFAULT))
						.groupBy(historyGroupingField)
						.orderBy(max(TEST_ITEM.START_TIME))
						.limit(pageable.getPageSize())
						.offset(retrieveOffsetAndApplyBoundaries(pageable))
						.fetchInto(String.class),
				pageable,
				() -> dsl.fetchCount(with(ITEMS).as(filteringQuery)
						.select(TEST_ITEM.field(historyGroupingField))
						.from(TEST_ITEM)
						.join(ITEMS)
						.on(TEST_ITEM.ITEM_ID.eq(fieldName(ITEMS, ID).cast(Long.class)))
						.groupBy(TEST_ITEM.field(historyGroupingField)))
		);
	}

	private Filter getHistoryFilter(Queryable filter, boolean usingHash, String historyValue) {
		List<ConvertibleCondition> commonConditions = filter.getFilterConditions();
		return new Filter(filter.getTarget().getClazz(), Lists.newArrayList()).withConditions(commonConditions)
				.withCondition(usingHash ?
						FilterCondition.builder().eq(CRITERIA_TEST_CASE_HASH, historyValue).build() :
						FilterCondition.builder().eq(CRITERIA_UNIQUE_ID, historyValue).build())
				.withCondition(FilterCondition.builder().eq(CRITERIA_LAUNCH_MODE, LaunchModeEnum.DEFAULT.name()).build());
	}

	private List<Long> getHistoryIds(TestItem testItem, boolean usingHash, Long projectId, int historyDepth) {
		List<Long> historyIds = usingHash ?
				loadHistory(testItem.getStartTime(),
						testItem.getItemId(),
						LAUNCH.PROJECT_ID.eq(projectId).and(TEST_ITEM.TEST_CASE_HASH.eq(testItem.getTestCaseHash())),
						historyDepth
				) :
				loadHistory(testItem.getStartTime(),
						testItem.getItemId(),
						LAUNCH.PROJECT_ID.eq(projectId).and(TEST_ITEM.UNIQUE_ID.eq(testItem.getUniqueId())),
						historyDepth
				);
		historyIds.add(0, testItem.getItemId());
		return historyIds;
	}

	private List<Long> getHistoryIds(TestItem testItem, boolean usingHash, Long projectId, String launchName, int historyDepth) {
		if (historyDepth > 0) {
			List<Long> historyIds = usingHash ?
					loadHistory(testItem.getStartTime(),
							testItem.getItemId(),
							LAUNCH.PROJECT_ID.eq(projectId)
									.and(LAUNCH.NAME.eq(launchName))
									.and(TEST_ITEM.TEST_CASE_HASH.eq(testItem.getTestCaseHash())),
							historyDepth
					) :
					loadHistory(testItem.getStartTime(),
							testItem.getItemId(),
							LAUNCH.PROJECT_ID.eq(projectId)
									.and(LAUNCH.NAME.eq(launchName))
									.and(TEST_ITEM.UNIQUE_ID.eq(testItem.getUniqueId())),
							historyDepth
					);
			historyIds.add(0, testItem.getItemId());
			return historyIds;
		}
		return Lists.newArrayList(testItem.getItemId());
	}

	private Optional<TestItem> loadHistoryItem(Queryable filter, Sort sort, Condition baselineCondition) {
		List<Sort.Order> orders = sort.get().collect(toList());
		orders.add(new Sort.Order(Sort.Direction.DESC, CRITERIA_START_TIME));

		SelectQuery<? extends Record> selectQuery = QueryBuilder.newBuilder(filter, QueryUtils.collectJoinFields(filter, sort)).with(Sort.by(orders)).with(1).build();
		selectQuery.addConditions(baselineCondition);

		return dsl.with(HISTORY)
				.as(selectQuery)
				.select()
				.from(TEST_ITEM)
				.join(HISTORY)
				.on(TEST_ITEM.ITEM_ID.eq(fieldName(HISTORY, ID).cast(Long.class)))
				.fetchInto(TestItem.class)
				.stream()
				.findFirst();
	}

	private List<Long> loadHistory(LocalDateTime startTime, Long itemId, Condition baselineCondition, int historyDepth) {
		return dsl.select(TEST_ITEM.ITEM_ID)
				.from(TEST_ITEM)
				.join(LAUNCH)
				.on(TEST_ITEM.LAUNCH_ID.eq(LAUNCH.ID))
				.where(baselineCondition)
				.and(TEST_ITEM.ITEM_ID.notEqual(itemId))
				.and(TEST_ITEM.START_TIME.lessOrEqual(Timestamp.valueOf(startTime)))
				.and(LAUNCH.MODE.eq(JLaunchModeEnum.DEFAULT))
				.orderBy(TEST_ITEM.START_TIME.desc(), LAUNCH.START_TIME.desc(), LAUNCH.NUMBER.desc())
				.limit(historyDepth)
				.fetchInto(Long.class);
	}

	@Override
	public List<TestItem> selectAllDescendants(Long itemId) {
		return commonTestItemDslSelect().where(TEST_ITEM.PARENT_ID.eq(itemId)).fetch(TEST_ITEM_RECORD_MAPPER);
	}

	@Override
	public List<TestItem> selectAllDescendantsWithChildren(Long itemId) {
		JTestItem childTestItem = JTestItem.TEST_ITEM.as("cti");
		return commonTestItemDslSelect().where(TEST_ITEM.PARENT_ID.eq(itemId))
				.and(DSL.exists(DSL.selectOne()
						.from(TEST_ITEM)
						.join(childTestItem)
						.on(TEST_ITEM.ITEM_ID.eq(childTestItem.PARENT_ID))
						.where(TEST_ITEM.PARENT_ID.eq(itemId))))
				.fetch(TEST_ITEM_RECORD_MAPPER);
	}

	@Override
	public List<Long> findTestItemIdsByLaunchId(Long launchId, Pageable pageable) {
		return dsl.select(TEST_ITEM.ITEM_ID)
				.from(TEST_ITEM)
				.where(TEST_ITEM.LAUNCH_ID.eq(launchId))
				.orderBy(TEST_ITEM.ITEM_ID)
				.limit(pageable.getPageSize())
				.offset(retrieveOffsetAndApplyBoundaries(pageable))
				.fetchInto(Long.class);
	}

	@Override
	public List<TestItem> selectItemsInStatusByLaunch(Long launchId, StatusEnum... statuses) {
		List<JStatusEnum> jStatuses = Arrays.stream(statuses).map(it -> JStatusEnum.valueOf(it.name())).collect(toList());
		return commonTestItemDslSelect().where(TEST_ITEM.LAUNCH_ID.eq(launchId).and(TEST_ITEM_RESULTS.STATUS.in(jStatuses)))
				.fetch(TEST_ITEM_RECORD_MAPPER);
	}

	@Override
	public List<TestItem> selectItemsInStatusByParent(Long itemId, StatusEnum... statuses) {
		List<JStatusEnum> jStatuses = Arrays.stream(statuses).map(it -> JStatusEnum.valueOf(it.name())).collect(toList());
		return commonTestItemDslSelect().where(TEST_ITEM.PARENT_ID.eq(itemId).and(TEST_ITEM_RESULTS.STATUS.in(jStatuses)))
				.fetch(TEST_ITEM_RECORD_MAPPER);
	}

	@Override
	public Boolean hasItemsInStatusByLaunch(Long launchId, StatusEnum... statuses) {
		List<JStatusEnum> jStatuses = Arrays.stream(statuses).map(it -> JStatusEnum.valueOf(it.name())).collect(toList());
		return dsl.fetchExists(dsl.selectOne()
				.from(TEST_ITEM)
				.join(TEST_ITEM_RESULTS)
				.onKey()
				.where(TEST_ITEM.LAUNCH_ID.eq(launchId))
				.and(TEST_ITEM_RESULTS.STATUS.in(jStatuses))
				.limit(1));
	}

	@Override
	public List<TestItem> findAllNotInIssueByLaunch(Long launchId, String locator) {
		return commonTestItemDslSelect().join(ISSUE)
				.on(ISSUE.ISSUE_ID.eq(TEST_ITEM_RESULTS.RESULT_ID))
				.join(ISSUE_TYPE)
				.on(ISSUE.ISSUE_TYPE.eq(ISSUE_TYPE.ID))
				.where(TEST_ITEM.LAUNCH_ID.eq(launchId))
				.and(ISSUE_TYPE.LOCATOR.ne(locator))
				.fetch(TEST_ITEM_RECORD_MAPPER);
	}

	@Override
	public List<Long> selectIdsNotInIssueByLaunch(Long launchId, String locator) {
		return dsl.select(TEST_ITEM.ITEM_ID)
				.from(TEST_ITEM)
				.join(TEST_ITEM_RESULTS)
				.on(TEST_ITEM.ITEM_ID.eq(TEST_ITEM_RESULTS.RESULT_ID))
				.join(ISSUE)
				.on(ISSUE.ISSUE_ID.eq(TEST_ITEM_RESULTS.RESULT_ID))
				.join(ISSUE_TYPE)
				.on(ISSUE.ISSUE_TYPE.eq(ISSUE_TYPE.ID))
				.where(TEST_ITEM.LAUNCH_ID.eq(launchId))
				.and(ISSUE_TYPE.LOCATOR.ne(locator))
				.fetchInto(Long.class);
	}

	@Override
	public List<TestItem> findAllNotInIssueGroupByLaunch(Long launchId, TestItemIssueGroup issueGroup) {
		return dsl.select()
				.from(TEST_ITEM)
				.join(TEST_ITEM_RESULTS)
				.on(TEST_ITEM.ITEM_ID.eq(TEST_ITEM_RESULTS.RESULT_ID))
				.join(ISSUE)
				.on(ISSUE.ISSUE_ID.eq(TEST_ITEM_RESULTS.RESULT_ID))
				.join(ISSUE_TYPE)
				.on(ISSUE.ISSUE_TYPE.eq(ISSUE_TYPE.ID))
				.join(ISSUE_GROUP)
				.on(ISSUE_TYPE.ISSUE_GROUP_ID.eq(ISSUE_GROUP.ISSUE_GROUP_ID))
				.where(TEST_ITEM.LAUNCH_ID.eq(launchId).and(ISSUE_GROUP.ISSUE_GROUP_.ne(JIssueGroupEnum.valueOf(issueGroup.getValue()))))
				.fetch(TEST_ITEM_RECORD_MAPPER);

	}

	@Override
	public List<Long> selectIdsNotInIssueGroupByLaunch(Long launchId, TestItemIssueGroup issueGroup) {
		return dsl.select(TEST_ITEM.ITEM_ID)
				.from(TEST_ITEM)
				.join(TEST_ITEM_RESULTS)
				.on(TEST_ITEM.ITEM_ID.eq(TEST_ITEM_RESULTS.RESULT_ID))
				.join(ISSUE)
				.on(ISSUE.ISSUE_ID.eq(TEST_ITEM_RESULTS.RESULT_ID))
				.join(ISSUE_TYPE)
				.on(ISSUE.ISSUE_TYPE.eq(ISSUE_TYPE.ID))
				.join(ISSUE_GROUP)
				.on(ISSUE_TYPE.ISSUE_GROUP_ID.eq(ISSUE_GROUP.ISSUE_GROUP_ID))
				.where(TEST_ITEM.LAUNCH_ID.eq(launchId).and(ISSUE_GROUP.ISSUE_GROUP_.ne(JIssueGroupEnum.valueOf(issueGroup.getValue()))))
				.fetchInto(Long.class);
	}

	@Override
	public List<TestItem> findAllInIssueGroupByLaunch(Long launchId, TestItemIssueGroup issueGroup) {
		return dsl.select()
				.from(TEST_ITEM)
				.join(TEST_ITEM_RESULTS)
				.on(TEST_ITEM.ITEM_ID.eq(TEST_ITEM_RESULTS.RESULT_ID))
				.join(ISSUE)
				.on(ISSUE.ISSUE_ID.eq(TEST_ITEM_RESULTS.RESULT_ID))
				.join(ISSUE_TYPE)
				.on(ISSUE.ISSUE_TYPE.eq(ISSUE_TYPE.ID))
				.join(ISSUE_GROUP)
				.on(ISSUE_TYPE.ISSUE_GROUP_ID.eq(ISSUE_GROUP.ISSUE_GROUP_ID))
				.where(TEST_ITEM.LAUNCH_ID.eq(launchId).and(ISSUE_GROUP.ISSUE_GROUP_.eq(JIssueGroupEnum.valueOf(issueGroup.getValue()))))
				.fetch(TEST_ITEM_RECORD_MAPPER);
	}

	@Override
	public List<Long> selectIdsWithIssueByLaunch(Long launchId) {
		return dsl.select(TEST_ITEM.ITEM_ID)
				.from(TEST_ITEM)
				.join(TEST_ITEM_RESULTS)
				.on(TEST_ITEM.ITEM_ID.eq(TEST_ITEM_RESULTS.RESULT_ID))
				.join(ISSUE)
				.on(ISSUE.ISSUE_ID.eq(TEST_ITEM_RESULTS.RESULT_ID))
				.where(TEST_ITEM.LAUNCH_ID.eq(launchId))
				.fetchInto(Long.class);
	}

	@Override
	public Boolean hasItemsInStatusAddedLately(Long launchId, Duration period, StatusEnum... statuses) {
		List<JStatusEnum> jStatuses = Arrays.stream(statuses).map(it -> JStatusEnum.valueOf(it.name())).collect(toList());
		return dsl.fetchExists(dsl.selectOne()
				.from(TEST_ITEM)
				.join(TEST_ITEM_RESULTS)
				.onKey()
				.where(TEST_ITEM.LAUNCH_ID.eq(launchId))
				.and(TEST_ITEM_RESULTS.STATUS.in(jStatuses))
				.and(TEST_ITEM.START_TIME.gt(TimestampUtils.getTimestampBackFromNow(period)))
				.limit(1));
	}

	@Override
	public Boolean hasLogs(Long launchId, Duration period, StatusEnum... statuses) {
		List<JStatusEnum> jStatuses = Arrays.stream(statuses).map(it -> JStatusEnum.valueOf(it.name())).collect(toList());
		return dsl.fetchExists(dsl.selectOne()
				.from(TEST_ITEM)
				.join(TEST_ITEM_RESULTS)
				.onKey()
				.join(LOG)
				.on(TEST_ITEM.ITEM_ID.eq(LOG.ITEM_ID))
				.where(TEST_ITEM.LAUNCH_ID.eq(launchId))
				.and(TEST_ITEM_RESULTS.STATUS.in(jStatuses))
				.and(TEST_ITEM.START_TIME.lt(TimestampUtils.getTimestampBackFromNow(period)))
				.limit(1));
	}

	@Override
	public List<TestItem> selectItemsInIssueByLaunch(Long launchId, String issueType) {
		return commonTestItemDslSelect().join(ISSUE)
				.on(ISSUE.ISSUE_ID.eq(TEST_ITEM_RESULTS.RESULT_ID))
				.join(ISSUE_TYPE)
				.on(ISSUE.ISSUE_TYPE.eq(ISSUE_TYPE.ID))
				.where(TEST_ITEM.LAUNCH_ID.eq(launchId))
				.and(ISSUE_TYPE.LOCATOR.eq(issueType))
				.fetch(TEST_ITEM_RECORD_MAPPER::map);
	}

	@Override
	public List<TestItem> selectRetries(List<Long> retryOfIds) {
		return dsl.select()
				.from(TEST_ITEM)
				.join(TEST_ITEM_RESULTS)
				.on(TEST_ITEM.ITEM_ID.eq(TEST_ITEM_RESULTS.RESULT_ID))
				.where(TEST_ITEM.RETRY_OF.in(retryOfIds))
				.and(TEST_ITEM.LAUNCH_ID.isNull())
				.orderBy(TEST_ITEM.START_TIME)
				.fetch(TEST_ITEM_RECORD_MAPPER);
	}

	@Override
	public List<IssueType> selectIssueLocatorsByProject(Long projectId) {
		return dsl.select()
				.from(PROJECT)
				.join(ISSUE_TYPE_PROJECT)
				.on(PROJECT.ID.eq(ISSUE_TYPE_PROJECT.PROJECT_ID))
				.join(ISSUE_TYPE)
				.on(ISSUE_TYPE_PROJECT.ISSUE_TYPE_ID.eq(ISSUE_TYPE.ID))
				.join(ISSUE_GROUP)
				.on(Tables.ISSUE_TYPE.ISSUE_GROUP_ID.eq(ISSUE_GROUP.ISSUE_GROUP_ID))
				.where(PROJECT.ID.eq(projectId))
				.fetch(ISSUE_TYPE_RECORD_MAPPER);
	}

	@Override
	public Optional<IssueType> selectIssueTypeByLocator(Long projectId, String locator) {
		return ofNullable(dsl.select()
				.from(ISSUE_TYPE)
				.join(ISSUE_TYPE_PROJECT)
				.on(ISSUE_TYPE_PROJECT.ISSUE_TYPE_ID.eq(ISSUE_TYPE.ID))
				.join(ISSUE_GROUP)
				.on(ISSUE_TYPE.ISSUE_GROUP_ID.eq(ISSUE_GROUP.ISSUE_GROUP_ID))
				.where(ISSUE_TYPE_PROJECT.PROJECT_ID.eq(projectId))
				.and(ISSUE_TYPE.LOCATOR.eq(locator))
				.fetchOne(ISSUE_TYPE_RECORD_MAPPER));
	}

	@Override
	public Map<Long, String> selectPathNames(Long itemId, Long projectId) {

		JTestItem parentItem = TEST_ITEM.as("parent");
		JTestItem childItem = TEST_ITEM.as("child");
		return dsl.select(parentItem.ITEM_ID, parentItem.NAME)
				.from(childItem)
				.leftJoin(parentItem)
				.on(DSL.sql(childItem.PATH + " <@ " + parentItem.PATH))
				.and(childItem.ITEM_ID.notEqual(parentItem.ITEM_ID))
				.join(LAUNCH)
				.on(childItem.LAUNCH_ID.eq(LAUNCH.ID))
				.where(childItem.ITEM_ID.eq(itemId))
				.and(LAUNCH.PROJECT_ID.eq(projectId))
				.orderBy(parentItem.ITEM_ID)
				.fetch()
				.stream()
				.collect(LinkedHashMap::new,
						(m, result) -> ofNullable(result.get(parentItem.ITEM_ID)).ifPresent(id -> m.put(id, result.get(parentItem.NAME))),
						LinkedHashMap::putAll
				);
	}

	@Override
	public Map<Long, PathName> selectPathNames(Collection<Long> ids, Long projectId) {

		JTestItem parentItem = TEST_ITEM.as("parent");
		JTestItem childItem = TEST_ITEM.as("child");
		return PATH_NAMES_FETCHER.apply(dsl.select(childItem.ITEM_ID, parentItem.ITEM_ID, parentItem.NAME, LAUNCH.NAME, LAUNCH.NUMBER)
				.from(childItem)
				.leftJoin(parentItem)
				.on(DSL.sql(childItem.PATH + " <@ " + parentItem.PATH))
				.and(childItem.ITEM_ID.notEqual(parentItem.ITEM_ID))
				.join(LAUNCH)
				.on(childItem.LAUNCH_ID.eq(LAUNCH.ID))
				.where(childItem.ITEM_ID.in(ids))
				.and(LAUNCH.PROJECT_ID.eq(projectId))
				.orderBy(childItem.ITEM_ID, parentItem.START_TIME.asc())
				.fetch());
	}

	/**
	 * {@link Log} entities are searched from the whole tree under
	 * {@link TestItem} that matched to the provided `launchId` and `autoAnalyzed` conditions
	 */
	@Override
	public List<Long> selectIdsByAnalyzedWithLevelGte(boolean autoAnalyzed, Long launchId, int logLevel) {

		JTestItem outerItemTable = TEST_ITEM.as(OUTER_ITEM_TABLE);
		JTestItem nestedItemTable = TEST_ITEM.as(NESTED);

		return dsl.selectDistinct(fieldName(ID))
				.from(DSL.select(outerItemTable.ITEM_ID.as(ID))
						.from(outerItemTable)
						.join(TEST_ITEM_RESULTS)
						.on(outerItemTable.ITEM_ID.eq(TEST_ITEM_RESULTS.RESULT_ID))
						.join(ISSUE)
						.on(TEST_ITEM_RESULTS.RESULT_ID.eq(ISSUE.ISSUE_ID))
						.where(outerItemTable.LAUNCH_ID.eq(launchId))
						.and(outerItemTable.HAS_STATS)
						.andNot(outerItemTable.HAS_CHILDREN)
						.and(ISSUE.AUTO_ANALYZED.eq(autoAnalyzed))
						.and(DSL.exists(DSL.selectOne()
								.from(nestedItemTable)
								.join(LOG)
								.on(nestedItemTable.ITEM_ID.eq(LOG.ITEM_ID))
								.where(nestedItemTable.LAUNCH_ID.eq(launchId))
								.andNot(nestedItemTable.HAS_STATS)
								.and(LOG.LOG_LEVEL.greaterOrEqual(logLevel))
								.and(DSL.sql(outerItemTable.PATH + " @> " + nestedItemTable.PATH))))
						.unionAll(DSL.selectDistinct(TEST_ITEM.ITEM_ID.as(ID))
								.from(TEST_ITEM)
								.join(TEST_ITEM_RESULTS)
								.on(TEST_ITEM.ITEM_ID.eq(TEST_ITEM_RESULTS.RESULT_ID))
								.join(ISSUE)
								.on(TEST_ITEM_RESULTS.RESULT_ID.eq(ISSUE.ISSUE_ID))
								.join(LOG)
								.on(TEST_ITEM.ITEM_ID.eq(LOG.ITEM_ID))
								.where(TEST_ITEM.LAUNCH_ID.eq(launchId))
								.and(ISSUE.AUTO_ANALYZED.eq(autoAnalyzed))
								.and(LOG.LOG_LEVEL.greaterOrEqual(logLevel)))
						.asTable(ITEM))
				.fetchInto(Long.class);
	}

	@Override
	public int updateStatusAndEndTimeById(Long itemId, JStatusEnum status, LocalDateTime endTime) {

		return dsl.update(TEST_ITEM_RESULTS)
				.set(TEST_ITEM_RESULTS.STATUS, status)
				.set(TEST_ITEM_RESULTS.END_TIME, Timestamp.valueOf(endTime))
				.set(TEST_ITEM_RESULTS.DURATION,
						dsl.select(DSL.extract(endTime, DatePart.EPOCH)
								.minus(DSL.extract(TEST_ITEM.START_TIME, DatePart.EPOCH))
								.cast(Double.class)).from(TEST_ITEM).where(TEST_ITEM.ITEM_ID.eq(itemId))
				)
				.where(TEST_ITEM_RESULTS.RESULT_ID.eq(itemId))
				.execute();
	}

	@Override
	public TestItemTypeEnum getTypeByItemId(Long itemId) {
		return dsl.select(TEST_ITEM.TYPE).from(TEST_ITEM).where(TEST_ITEM.ITEM_ID.eq(itemId)).fetchOneInto(TestItemTypeEnum.class);
	}

	@Override
	public List<Long> selectIdsByStringPatternMatchedLogMessage(Queryable filter, Integer logLevel, String pattern) {

		JTestItem childItemTable = TEST_ITEM.as(CHILD_ITEM_TABLE);

		SelectQuery<? extends Record> itemQuery = QueryBuilder.newBuilder(filter, QueryUtils.collectJoinFields(filter)).build();
		itemQuery.addJoin(childItemTable, JoinType.JOIN, DSL.condition(childItemTable.PATH + " <@ " + TEST_ITEM.PATH));
		itemQuery.addJoin(LOG, JoinType.LEFT_OUTER_JOIN, childItemTable.ITEM_ID.eq(LOG.ITEM_ID));
		filter.getFilterConditions()
				.stream()
				.flatMap(it -> it.getAllConditions().stream())
				.filter(condition -> CRITERIA_LAUNCH_ID.equals(condition.getSearchCriteria()))
				.findFirst()
				.ifPresent(launchIdCondition -> itemQuery.addConditions(childItemTable.LAUNCH_ID.eq(NumberUtils.toLong(launchIdCondition.getValue()))));
		itemQuery.addConditions(LOG.LOG_LEVEL.greaterOrEqual(logLevel), LOG.LOG_MESSAGE.like("%" + DSL.escape(pattern, '\\') + "%"));

		return dsl.select(fieldName(ITEMS, ID)).from(itemQuery.asTable(ITEMS)).fetchInto(Long.class);

	}

	@Override
	public List<Long> selectIdsByRegexPatternMatchedLogMessage(Queryable filter, Integer logLevel, String pattern) {

		JTestItem childItemTable = TEST_ITEM.as(CHILD_ITEM_TABLE);

		SelectQuery<? extends Record> itemQuery = QueryBuilder.newBuilder(filter, QueryUtils.collectJoinFields(filter)).build();
		itemQuery.addJoin(childItemTable, JoinType.JOIN, DSL.condition(childItemTable.PATH + " <@ " + TEST_ITEM.PATH));
		itemQuery.addJoin(LOG, JoinType.LEFT_OUTER_JOIN, childItemTable.ITEM_ID.eq(LOG.ITEM_ID));
		filter.getFilterConditions()
				.stream()
				.flatMap(it -> it.getAllConditions().stream())
				.filter(condition -> CRITERIA_LAUNCH_ID.equals(condition.getSearchCriteria()))
				.findFirst()
				.ifPresent(launchIdCondition -> itemQuery.addConditions(childItemTable.LAUNCH_ID.eq(NumberUtils.toLong(launchIdCondition.getValue()))));
		itemQuery.addConditions(LOG.LOG_LEVEL.greaterOrEqual(logLevel), LOG.LOG_MESSAGE.likeRegex(pattern));

		return dsl.select(fieldName(ITEMS, ID)).from(itemQuery.asTable(ITEMS)).fetchInto(Long.class);
	}

	/**
	 * Commons select of an item with it's results and structure
	 *
	 * @return Select condition step
	 */
	private SelectOnConditionStep<Record> commonTestItemDslSelect() {
		return dsl.select().from(TEST_ITEM).join(TEST_ITEM_RESULTS).on(TEST_ITEM.ITEM_ID.eq(TEST_ITEM_RESULTS.RESULT_ID));
	}

	@Override
	public List<TestItem> findByFilter(Queryable filter) {
		return TEST_ITEM_FETCHER.apply(dsl.fetch(QueryBuilder.newBuilder(filter, QueryUtils.collectJoinFields(filter)).wrap().build()));
	}

	@Override
	public Page<TestItem> findByFilter(Queryable filter, Pageable pageable) {

		Set<String> joinFields = QueryUtils.collectJoinFields(filter, pageable.getSort());
		List<TestItem> items = TEST_ITEM_FETCHER.apply(dsl.fetch(QueryBuilder.newBuilder(filter, joinFields)
				.with(pageable)
				.wrap()
				.withWrapperSort(pageable.getSort())
				.build()));

		return PageableExecutionUtils.getPage(items, pageable, () -> dsl.fetchCount(QueryBuilder.newBuilder(filter, joinFields).build()));
	}

	@Override
	public List<NestedStep> findAllNestedStepsByIds(Collection<Long> ids, Queryable logFilter, boolean excludePassedLogs) {
		JTestItem nested = TEST_ITEM.as(NESTED);
		SelectQuery<? extends Record> logsSelectQuery = QueryBuilder.newBuilder(logFilter, QueryUtils.collectJoinFields(logFilter)).build();

		return dsl.select(TEST_ITEM.ITEM_ID,
				TEST_ITEM.NAME,
				TEST_ITEM.START_TIME,
				TEST_ITEM.TYPE,
				TEST_ITEM_RESULTS.STATUS,
				TEST_ITEM_RESULTS.END_TIME,
				TEST_ITEM_RESULTS.DURATION,
				DSL.field(hasContentQuery(nested, logsSelectQuery, excludePassedLogs)).as(HAS_CONTENT),
				DSL.field(dsl.with(LOGS)
						.as(logsSelectQuery)
						.selectCount()
						.from(LOG)
						.join(nested)
						.on(LOG.ITEM_ID.eq(nested.ITEM_ID))
						.join(LOGS)
						.on(LOG.ID.eq(fieldName(LOGS, ID).cast(Long.class)))
						.join(ATTACHMENT)
						.on(LOG.ATTACHMENT_ID.eq(ATTACHMENT.ID))
						.where(nested.HAS_STATS.isFalse()
								.and(DSL.sql(fieldName(NESTED, TEST_ITEM.PATH.getName()) + " <@ cast(? AS LTREE)", TEST_ITEM.PATH))))
						.as(ATTACHMENTS_COUNT)
		)
				.from(TEST_ITEM)
				.join(TEST_ITEM_RESULTS)
				.on(TEST_ITEM.ITEM_ID.eq(TEST_ITEM_RESULTS.RESULT_ID))
				.where(TEST_ITEM.ITEM_ID.in(ids))
				.fetch(NESTED_STEP_RECORD_MAPPER);
	}

	private Condition hasContentQuery(JTestItem nested, SelectQuery<? extends Record> logsSelectQuery, boolean excludePassedLogs) {
		if (excludePassedLogs) {
			return DSL.exists(dsl.with(LOGS)
					.as(logsSelectQuery)
					.select()
					.from(LOG)
					.join(LOGS)
					.on(LOG.ID.eq(fieldName(LOGS, ID).cast(Long.class)))
					.join(TEST_ITEM_RESULTS)
					.on(LOG.ITEM_ID.eq(TEST_ITEM_RESULTS.RESULT_ID))
					.where(LOG.ITEM_ID.eq(TEST_ITEM.ITEM_ID)))
					.and(TEST_ITEM_RESULTS.STATUS.notIn(JStatusEnum.PASSED, JStatusEnum.INFO, JStatusEnum.WARN));
		} else {
			return DSL.exists(dsl.with(LOGS)
					.as(logsSelectQuery)
					.select()
					.from(LOG)
					.join(LOGS)
					.on(LOG.ID.eq(fieldName(LOGS, ID).cast(Long.class)))
					.where(LOG.ITEM_ID.eq(TEST_ITEM.ITEM_ID)))
					.orExists(dsl.select().from(nested).where(nested.PARENT_ID.eq(TEST_ITEM.ITEM_ID).and(nested.HAS_STATS.isFalse())));
		}
	}

}
