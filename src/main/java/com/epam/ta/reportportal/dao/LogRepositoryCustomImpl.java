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

import com.epam.ta.reportportal.commons.querygen.Filter;
import com.epam.ta.reportportal.commons.querygen.QueryBuilder;
import com.epam.ta.reportportal.commons.querygen.Queryable;
import com.epam.ta.reportportal.dao.constant.LogRepositoryConstants;
import com.epam.ta.reportportal.dao.util.QueryUtils;
import com.epam.ta.reportportal.dao.util.TimestampUtils;
import com.epam.ta.reportportal.entity.enums.StatusEnum;
import com.epam.ta.reportportal.entity.item.NestedItem;
import com.epam.ta.reportportal.entity.log.Log;
import com.epam.ta.reportportal.exception.ReportPortalException;
import com.epam.ta.reportportal.jooq.enums.JStatusEnum;
import com.epam.ta.reportportal.jooq.tables.JTestItem;
import com.epam.ta.reportportal.ws.model.ErrorType;
import com.epam.ta.reportportal.ws.model.analyzer.IndexLog;
import com.google.common.collect.Lists;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.time.Duration;
import java.util.*;
import java.util.stream.Stream;

import static com.epam.ta.reportportal.commons.querygen.constant.LogCriteriaConstant.CRITERIA_LOG_TIME;
import static com.epam.ta.reportportal.commons.querygen.constant.TestItemCriteriaConstant.CRITERIA_STATUS;
import static com.epam.ta.reportportal.dao.constant.LogRepositoryConstants.*;
import static com.epam.ta.reportportal.dao.constant.TestItemRepositoryConstants.NESTED;
import static com.epam.ta.reportportal.dao.constant.WidgetRepositoryConstants.ID;
import static com.epam.ta.reportportal.dao.util.JooqFieldNameTransformer.fieldName;
import static com.epam.ta.reportportal.dao.util.RecordMappers.*;
import static com.epam.ta.reportportal.dao.util.ResultFetchers.LOG_FETCHER;
import static com.epam.ta.reportportal.dao.util.ResultFetchers.NESTED_ITEM_FETCHER;
import static com.epam.ta.reportportal.jooq.Tables.LAUNCH;
import static com.epam.ta.reportportal.jooq.Tables.LOG;
import static com.epam.ta.reportportal.jooq.Tables.CLUSTERS;
import static com.epam.ta.reportportal.jooq.tables.JAttachment.ATTACHMENT;
import static com.epam.ta.reportportal.jooq.tables.JTestItem.TEST_ITEM;
import static com.epam.ta.reportportal.jooq.tables.JTestItemResults.TEST_ITEM_RESULTS;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;
import static org.jooq.impl.DSL.field;

/**
 * @author Pavel Bortnik
 */
@Repository
public class LogRepositoryCustomImpl implements LogRepositoryCustom {

	public static final String ROOT_ITEM_ID = "root_id";

	private static final String PARENT_ITEM_TABLE = "parent";
	private static final String CHILD_ITEM_TABLE = "child";

	private DSLContext dsl;

	@Autowired
	public void setDsl(DSLContext dsl) {
		this.dsl = dsl;
	}

	@Override
	public boolean hasLogs(Long itemId) {
		return dsl.fetchExists(dsl.selectOne().from(LOG).where(LOG.ITEM_ID.eq(itemId)));
	}

	@Override
	public List<Log> findByTestItemId(Long itemId, int limit) {
		if (itemId == null || limit <= 0) {
			return new ArrayList<>();
		}

		return dsl.select()
				.from(LOG)
				.leftJoin(ATTACHMENT)
				.on(LOG.ATTACHMENT_ID.eq(ATTACHMENT.ID))
				.where(LOG.ITEM_ID.eq(itemId))
				.orderBy(LOG.LOG_TIME.asc())
				.limit(limit)
				.fetch()
				.map(r -> LOG_MAPPER.apply(r, ATTACHMENT_MAPPER));
	}

	@Override
	public List<Log> findByTestItemId(Long itemId) {
		if (itemId == null) {
			return new ArrayList<>();
		}

		return dsl.select()
				.from(LOG)
				.leftJoin(ATTACHMENT)
				.on(LOG.ATTACHMENT_ID.eq(ATTACHMENT.ID))
				.where(LOG.ITEM_ID.eq(itemId))
				.orderBy(LOG.LOG_TIME.asc())
				.fetch()
				.map(r -> LOG_MAPPER.apply(r, ATTACHMENT_MAPPER));
	}

	/**
	 * @return {@link List} of {@link Log} without {@link Log#getAttachment()}
	 */
	@Override
	public List<Log> findAllUnderTestItemByLaunchIdAndTestItemIdsAndLogLevelGte(Long launchId, List<Long> itemIds, int logLevel) {
		return buildLogsUnderItemsQuery(launchId, itemIds, false).and(LOG.LOG_LEVEL.greaterOrEqual(logLevel))
				.fetch()
				.map(LOG_UNDER_RECORD_MAPPER);
	}

	@Override
	public Map<Long, List<IndexLog>> findAllIndexUnderTestItemByLaunchIdAndTestItemIdsAndLogLevelGte(Long launchId, List<Long> itemIds, int logLevel) {
		JTestItem parentItemTable = TEST_ITEM.as(PARENT_ITEM_TABLE);
		JTestItem childItemTable = TEST_ITEM.as(CHILD_ITEM_TABLE);

		return INDEX_LOG_FETCHER.apply(dsl.selectDistinct(LOG.ID, LOG.LOG_LEVEL, LOG.LOG_MESSAGE, parentItemTable.ITEM_ID.as(ROOT_ITEM_ID), CLUSTERS.INDEX_ID)
				.on(LOG.ID)
				.from(LOG)
				.join(childItemTable)
				.on(LOG.ITEM_ID.eq(childItemTable.ITEM_ID))
				.join(parentItemTable)
				.on(DSL.sql(childItemTable.PATH + " <@ " + parentItemTable.PATH))
				.leftJoin(CLUSTERS)
				.on(LOG.CLUSTER_ID.eq(CLUSTERS.ID))
				.where(childItemTable.LAUNCH_ID.eq(launchId))
				.and(parentItemTable.LAUNCH_ID.eq(launchId))
				.and(parentItemTable.ITEM_ID.in(itemIds))
				.and(LOG.LOG_LEVEL.greaterOrEqual(logLevel))
				.fetch());
	}

	@Override
	public List<Log> findLatestUnderTestItemByLaunchIdAndTestItemIdsAndLogLevelGte(Long launchId, Long itemId, int logLevel, int limit) {
		return Lists.reverse(buildLogsUnderItemsQuery(launchId, Collections.singletonList(itemId), false).and(LOG.LOG_LEVEL.greaterOrEqual(
				logLevel)).orderBy(LOG.LOG_TIME.desc()).limit(limit).fetch().map(LOG_UNDER_RECORD_MAPPER));
	}

	@Override
	public List<Log> findAllUnderTestItemByLaunchIdAndTestItemIdsWithLimit(Long launchId, List<Long> itemIds, int limit) {
		return buildLogsUnderItemsQuery(launchId, itemIds, true).limit(limit)
				.fetch()
				.map(r -> LOG_UNDER_MAPPER.apply(r, ATTACHMENT_MAPPER));

	}

	@Override
	public List<Long> findIdsByFilter(Queryable filter) {
		Set<String> fields = QueryUtils.collectJoinFields(filter);
		return dsl.select(fieldName(ID)).from(QueryBuilder.newBuilder(filter, fields).build()).fetchInto(Long.class);
	}

	@Override
	public List<Long> findIdsByTestItemId(Long testItemId) {
		return dsl.select(LOG.ID).from(LOG).where(LOG.ITEM_ID.eq(testItemId)).fetchInto(Long.class);
	}

	@Override
	public List<Long> findIdsUnderTestItemByLaunchIdAndTestItemIdsAndLogLevelGte(Long launchId, List<Long> itemIds, int logLevel) {

		JTestItem parentItemTable = TEST_ITEM.as(PARENT_ITEM_TABLE);
		JTestItem childItemTable = TEST_ITEM.as(CHILD_ITEM_TABLE);

		return dsl.selectDistinct(LOG.ID)
				.from(LOG)
				.join(childItemTable)
				.on(LOG.ITEM_ID.eq(childItemTable.ITEM_ID))
				.join(parentItemTable)
				.on(DSL.sql(childItemTable.PATH + " <@ " + parentItemTable.PATH))
				.where(childItemTable.LAUNCH_ID.eq(launchId))
				.and(parentItemTable.LAUNCH_ID.eq(launchId))
				.and(parentItemTable.ITEM_ID.in(itemIds))
				.and(LOG.LOG_LEVEL.greaterOrEqual(logLevel))
				.fetchInto(Long.class);
	}

	@Override
	public List<Long> findItemLogIdsByLaunchIdAndLogLevelGte(Long launchId, int logLevel) {
		return dsl.select(LOG.ID)
				.from(LOG)
				.leftJoin(TEST_ITEM)
				.on(LOG.ITEM_ID.eq(TEST_ITEM.ITEM_ID))
				.join(LAUNCH)
				.on(TEST_ITEM.LAUNCH_ID.eq(LAUNCH.ID))
				.where(LAUNCH.ID.eq(launchId))
				.and(LOG.LOG_LEVEL.greaterOrEqual(logLevel))
				.fetch(LOG.ID, Long.class);
	}

	@Override
	public List<Long> findItemLogIdsByLaunchIdsAndLogLevelGte(List<Long> launchIds, int logLevel) {
		return dsl.select(LOG.ID)
				.from(LOG)
				.leftJoin(TEST_ITEM)
				.on(LOG.ITEM_ID.eq(TEST_ITEM.ITEM_ID))
				.join(LAUNCH)
				.on(TEST_ITEM.LAUNCH_ID.eq(LAUNCH.ID))
				.where(LAUNCH.ID.in(launchIds))
				.and(LOG.LOG_LEVEL.greaterOrEqual(logLevel))
				.fetch(LOG.ID, Long.class);
	}

	@Override
	public List<Long> findIdsByTestItemIdsAndLogLevelGte(List<Long> itemIds, int logLevel) {
		return dsl.select(LOG.ID)
				.from(LOG)
				.where(LOG.ITEM_ID.in(itemIds))
				.and(LOG.LOG_LEVEL.greaterOrEqual(logLevel))
				.fetch(LOG.ID, Long.class);
	}

	@Override
	public List<Log> findByFilter(Queryable filter) {
		return LOG_FETCHER.apply(dsl.fetch(QueryBuilder.newBuilder(filter, QueryUtils.collectJoinFields(filter)).wrap().build()));
	}

	@Override
	public Page<Log> findByFilter(Queryable filter, Pageable pageable) {
		Set<String> joinFields = QueryUtils.collectJoinFields(filter, pageable.getSort());
		return PageableExecutionUtils.getPage(LOG_FETCHER.apply(dsl.fetch(QueryBuilder.newBuilder(filter, joinFields)
				.with(pageable)
				.wrap()
				.withWrapperSort(pageable.getSort())
				.build())), pageable, () -> dsl.fetchCount(QueryBuilder.newBuilder(filter, joinFields).build()));
	}

	@Override
	public Integer getPageNumber(Long id, Filter filter, Pageable pageable) {

		Sort.Order order = ofNullable(pageable.getSort().getOrderFor(CRITERIA_LOG_TIME)).orElseThrow(() -> new ReportPortalException(
				ErrorType.INCORRECT_SORTING_PARAMETERS));

		OrderField<?> sortField = order.getDirection().isAscending() ? LOG.LOG_TIME.asc() : LOG.LOG_TIME.desc();

		return ofNullable(dsl.select(fieldName(ROW_NUMBER))
				.from(dsl.select(LOG.ID, DSL.rowNumber().over(DSL.orderBy(sortField)).as(ROW_NUMBER))
						.from(LOG)
						.join(QueryBuilder.newBuilder(filter, QueryUtils.collectJoinFields(filter, pageable.getSort()))
								.with(pageable.getSort())
								.build()
								.asTable(DISTINCT_LOGS_TABLE))
						.on(LOG.ID.eq(fieldName(DISTINCT_LOGS_TABLE, ID).cast(Long.class))))
				.where(fieldName(ID).cast(Long.class).eq(id))
				.fetchAny()).map(r -> {
			Long rowNumber = r.into(Long.class);
			return BigDecimal.valueOf(rowNumber).divide(BigDecimal.valueOf(pageable.getPageSize()), RoundingMode.CEILING).intValue();
		}).orElseThrow(() -> new ReportPortalException(ErrorType.LOG_NOT_FOUND, id));

	}

	@Override
	public boolean hasLogsAddedLately(Duration period, Long launchId, StatusEnum... statuses) {
		List<JStatusEnum> jStatuses = Arrays.stream(statuses).map(it -> JStatusEnum.valueOf(it.name())).collect(toList());
		return dsl.fetchExists(dsl.selectOne()
				.from(LOG)
				.join(TEST_ITEM)
				.on(LOG.ITEM_ID.eq(TEST_ITEM.ITEM_ID))
				.join(TEST_ITEM_RESULTS)
				.on(TEST_ITEM.ITEM_ID.eq(TEST_ITEM_RESULTS.RESULT_ID))
				.where(TEST_ITEM.LAUNCH_ID.eq(launchId))
				.and(TEST_ITEM_RESULTS.STATUS.in(jStatuses))
				.and(LOG.LOG_TIME.gt(TimestampUtils.getTimestampBackFromNow(period)))
				.limit(1));
	}

	@Override
	public int deleteByPeriodAndTestItemIds(Duration period, Collection<Long> testItemIds) {

		return dsl.deleteFrom(LOG)
				.where(LOG.ITEM_ID.in(testItemIds).and(LOG.LOG_TIME.lt(TimestampUtils.getTimestampBackFromNow(period))))
				.execute();
	}

	@Override
	public int deleteByPeriodAndLaunchIds(Duration period, Collection<Long> launchIds) {
		return dsl.deleteFrom(LOG)
				.where(LOG.LAUNCH_ID.in(launchIds).and(LOG.LOG_TIME.lt(TimestampUtils.getTimestampBackFromNow(period))))
				.execute();
	}

	@Override
	public Page<NestedItem> findNestedItems(Long parentId, boolean excludeEmptySteps, boolean excludeLogs, Queryable filter,
			Pageable pageable) {

		SortField<Object> sorting = pageable.getSort()
				.stream()
				.filter(order -> CRITERIA_LOG_TIME.equals(order.getProperty()))
				.findFirst()
				.map(order -> order.isAscending() ? field(TIME).sort(SortOrder.ASC) : field(TIME).sort(SortOrder.DESC))
				.orElseGet(() -> field(TIME).sort(SortOrder.ASC));

		SelectOrderByStep<Record3<Long, Timestamp, String>> selectQuery = buildNestedStepQuery(parentId, excludeEmptySteps, filter);

		if (!excludeLogs) {
			selectQuery = selectQuery.unionAll(buildNestedLogQuery(parentId, filter));
		}

		int total = dsl.fetchCount(selectQuery);

		return PageableExecutionUtils.getPage(NESTED_ITEM_FETCHER.apply(dsl.fetch(selectQuery.orderBy(sorting, field(ID).asc())
				.limit(pageable.getPageSize())
				.offset(QueryBuilder.retrieveOffsetAndApplyBoundaries(pageable)))), pageable, () -> total);

	}

	@Override
	public List<String> findMessagesByLaunchIdAndItemIdAndPathAndLevelGte(Long launchId, Long itemId, String path, Integer level) {
		return dsl.select(LOG.LOG_MESSAGE)
				.from(LOG)
				.join(TEST_ITEM)
				.on(LOG.ITEM_ID.eq(TEST_ITEM.ITEM_ID))
				.where(LOG.LOG_LEVEL.ge(level)
						.and(TEST_ITEM.LAUNCH_ID.eq(launchId))
						.and(TEST_ITEM.ITEM_ID.eq(itemId)
								.or(TEST_ITEM.HAS_STATS.eq(false).and(DSL.sql(TEST_ITEM.PATH + " <@ cast(? AS LTREE)", path)))))
				.fetch(LOG.LOG_MESSAGE);
	}

	@Override
	public int deleteByProjectId(Long projectId) {
		return dsl.deleteFrom(LOG).where(LOG.PROJECT_ID.eq(projectId)).execute();
	}

	private SelectConditionStep<? extends Record> buildLogsUnderItemsQuery(Long launchId, List<Long> itemIds, boolean includeAttachments) {

		JTestItem parentItemTable = TEST_ITEM.as(PARENT_ITEM_TABLE);
		JTestItem childItemTable = TEST_ITEM.as(CHILD_ITEM_TABLE);

		List<Field<?>> selectFields = Lists.newArrayList(LOG.ID,
				LOG.LOG_LEVEL,
				LOG.LOG_MESSAGE,
				LOG.LOG_TIME,
				parentItemTable.ITEM_ID.as(ROOT_ITEM_ID),
				LOG.LAUNCH_ID,
				LOG.LAST_MODIFIED,
				LOG.CLUSTER_ID
		);

		if (includeAttachments) {
			Collections.addAll(selectFields, ATTACHMENT.fields());
		}

		SelectOnConditionStep<Record> logsSelect = dsl.selectDistinct(selectFields)
				.on(LOG.ID, LOG.LOG_TIME)
				.from(LOG)
				.join(childItemTable)
				.on(LOG.ITEM_ID.eq(childItemTable.ITEM_ID))
				.join(parentItemTable)
				.on(DSL.sql(childItemTable.PATH + " <@ " + parentItemTable.PATH));

		if (includeAttachments) {
			logsSelect = logsSelect.leftJoin(ATTACHMENT).on(LOG.ATTACHMENT_ID.eq(ATTACHMENT.ID));
		}

		return logsSelect.where(childItemTable.LAUNCH_ID.eq(launchId))
				.and(parentItemTable.LAUNCH_ID.eq(launchId))
				.and(parentItemTable.ITEM_ID.in(itemIds));
	}

	private SelectHavingStep<Record3<Long, Timestamp, String>> buildNestedStepQuery(Long parentId, boolean excludeEmptySteps,
			Queryable filter) {

		SelectConditionStep<Record3<Long, Timestamp, String>> nestedStepSelect = dsl.select(TEST_ITEM.ITEM_ID.as(ID),
						TEST_ITEM.START_TIME.as(TIME),
						DSL.val(ITEM).as(TYPE)
				)
				.from(TEST_ITEM)
				.join(TEST_ITEM_RESULTS)
				.on(TEST_ITEM.ITEM_ID.eq(TEST_ITEM_RESULTS.RESULT_ID))
				.where(TEST_ITEM.PARENT_ID.eq(parentId))
				.and(TEST_ITEM.HAS_STATS.isFalse());

		filter.getFilterConditions()
				.stream()
				.flatMap(condition -> condition.getAllConditions().stream())
				.filter(c -> CRITERIA_STATUS.equals(c.getSearchCriteria()))
				.findFirst()
				.map(c -> Stream.of(c.getValue().split(",")).filter(StatusEnum::isPresent).map(JStatusEnum::valueOf).collect(toList()))
				.map(TEST_ITEM_RESULTS.STATUS::in)
				.ifPresent(nestedStepSelect::and);

		if (excludeEmptySteps) {
			JTestItem nested = TEST_ITEM.as(NESTED);
			nestedStepSelect.and(field(DSL.exists(dsl.with(LOGS)
							.as(QueryBuilder.newBuilder(filter, QueryUtils.collectJoinFields(filter))
									.addCondition(LOG.ITEM_ID.eq(parentId))
									.build())
							.select()
							.from(LOG)
							.join(LOGS)
							.on(LOG.ID.eq(field(LOGS, ID).cast(Long.class)))
							.where(LOG.ITEM_ID.eq(TEST_ITEM.ITEM_ID)))
					.orExists(dsl.select().from(nested).where(nested.PARENT_ID.eq(TEST_ITEM.ITEM_ID)))));
		}

		return nestedStepSelect.groupBy(TEST_ITEM.ITEM_ID);
	}

	private SelectOnConditionStep<Record3<Long, Timestamp, String>> buildNestedLogQuery(Long parentId, Queryable filter) {

		Queryable logFilter = filter.getFilterConditions()
				.stream()
				.flatMap(condition -> condition.getAllConditions().stream())
				.filter(condition -> CRITERIA_STATUS.equalsIgnoreCase(condition.getSearchCriteria()))
				.findAny()
				.map(condition -> (Queryable) new Filter(filter.getTarget().getClazz(),
						filter.getFilterConditions()
								.stream()
								.flatMap(simpleCondition -> simpleCondition.getAllConditions().stream())
								.filter(filterCondition -> !condition.getSearchCriteria()
										.equalsIgnoreCase(filterCondition.getSearchCriteria()))
								.collect(toList())
				))
				.orElse(filter);

		QueryBuilder queryBuilder = QueryBuilder.newBuilder(logFilter, QueryUtils.collectJoinFields(logFilter));

		return dsl.with(LOGS)
				.as(queryBuilder.addCondition(LOG.ITEM_ID.eq(parentId)).build())
				.select(LOG.ID.as(ID), LOG.LOG_TIME.as(TIME), DSL.val(LogRepositoryConstants.LOG).as(TYPE))
				.from(LOG)
				.join(LOGS)
				.on(fieldName(LOGS, ID).cast(Long.class).eq(LOG.ID));
	}
}
