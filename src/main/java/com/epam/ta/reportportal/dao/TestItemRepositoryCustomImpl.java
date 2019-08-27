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

import com.epam.ta.reportportal.commons.MoreCollectors;
import com.epam.ta.reportportal.commons.querygen.ConvertibleCondition;
import com.epam.ta.reportportal.commons.querygen.FilterCondition;
import com.epam.ta.reportportal.commons.querygen.QueryBuilder;
import com.epam.ta.reportportal.commons.querygen.Queryable;
import com.epam.ta.reportportal.dao.util.TimestampUtils;
import com.epam.ta.reportportal.entity.enums.LogLevel;
import com.epam.ta.reportportal.entity.enums.StatusEnum;
import com.epam.ta.reportportal.entity.enums.TestItemIssueGroup;
import com.epam.ta.reportportal.entity.enums.TestItemTypeEnum;
import com.epam.ta.reportportal.entity.item.NestedStep;
import com.epam.ta.reportportal.entity.item.TestItem;
import com.epam.ta.reportportal.entity.item.issue.IssueType;
import com.epam.ta.reportportal.jooq.Tables;
import com.epam.ta.reportportal.jooq.enums.JIssueGroupEnum;
import com.epam.ta.reportportal.jooq.enums.JStatusEnum;
import com.epam.ta.reportportal.jooq.tables.JTestItem;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.epam.ta.reportportal.dao.constant.LogRepositoryConstants.LOGS;
import static com.epam.ta.reportportal.dao.constant.TestItemRepositoryConstants.*;
import static com.epam.ta.reportportal.dao.constant.WidgetContentRepositoryConstants.ITEMS;
import static com.epam.ta.reportportal.dao.constant.WidgetContentRepositoryConstants.LAUNCHES;
import static com.epam.ta.reportportal.dao.constant.WidgetRepositoryConstants.ID;
import static com.epam.ta.reportportal.dao.util.JooqFieldNameTransformer.fieldName;
import static com.epam.ta.reportportal.dao.util.RecordMappers.*;
import static com.epam.ta.reportportal.dao.util.ResultFetchers.RETRIES_FETCHER;
import static com.epam.ta.reportportal.dao.util.ResultFetchers.TEST_ITEM_FETCHER;
import static com.epam.ta.reportportal.jooq.Tables.*;
import static com.epam.ta.reportportal.jooq.tables.JIssueType.ISSUE_TYPE;
import static com.epam.ta.reportportal.jooq.tables.JTestItem.TEST_ITEM;
import static com.epam.ta.reportportal.jooq.tables.JTestItemResults.TEST_ITEM_RESULTS;
import static java.util.stream.Collectors.toList;

/**
 * @author Pavel Bortnik
 */
@Repository
public class TestItemRepositoryCustomImpl implements TestItemRepositoryCustom {

	private DSLContext dsl;

	@Autowired
	public void setDsl(DSLContext dsl) {
		this.dsl = dsl;
	}

	@Override
	public Page<TestItem> findByFilter(Queryable launchFilter, Queryable testItemFilter, Pageable launchPageable,
			Pageable testItemPageable) {

		Set<String> fields = launchFilter.getFilterConditions()
				.stream()
				.map(ConvertibleCondition::getAllConditions)
				.flatMap(Collection::stream)
				.map(FilterCondition::getSearchCriteria)
				.collect(Collectors.toSet());
		fields.addAll(launchPageable.getSort().get().map(Sort.Order::getProperty).collect(Collectors.toSet()));

		Table<? extends Record> launchesTable = QueryBuilder.newBuilder(launchFilter, fields)
				.with(launchPageable)
				.build()
				.asTable(LAUNCHES);
		List<TestItem> items = TEST_ITEM_FETCHER.apply(dsl.fetch(QueryBuilder.newBuilder(testItemFilter)
				.with(testItemPageable)
				.addJointToStart(launchesTable,
						JoinType.JOIN,
						TEST_ITEM.LAUNCH_ID.eq(fieldName(launchesTable.getName(), ID).cast(Long.class))
				)
				.wrap()
				.withWrapperSort(testItemPageable.getSort())
				.build()));

		fetchRetries(items);

		return PageableExecutionUtils.getPage(items,
				testItemPageable,
				() -> dsl.fetchCount(QueryBuilder.newBuilder(testItemFilter)
						.addJointToStart(launchesTable,
								JoinType.JOIN,
								TEST_ITEM.LAUNCH_ID.eq(fieldName(launchesTable.getName(), ID).cast(Long.class))
						)
						.build())
		);
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
	public Boolean hasItemsInStatusByParent(Long parentId, String parentPath, StatusEnum... statuses) {
		List<JStatusEnum> jStatuses = Arrays.stream(statuses).map(it -> JStatusEnum.valueOf(it.name())).collect(toList());
		return dsl.fetchExists(commonTestItemDslSelect().where(DSL.sql(TEST_ITEM.PATH + " <@ cast(? AS LTREE)", parentPath))
				.and(TEST_ITEM.ITEM_ID.ne(parentId))
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
	public Boolean hasItemsInStatusAddedLately(Long launchId, Duration period, StatusEnum... statuses) {
		List<JStatusEnum> jStatuses = Arrays.stream(statuses).map(it -> JStatusEnum.valueOf(it.name())).collect(toList());
		return dsl.fetchExists(dsl.selectOne()
				.from(TEST_ITEM)
				.join(TEST_ITEM_RESULTS)
				.onKey()
				.where(TEST_ITEM.LAUNCH_ID.eq(launchId))
				.and(TEST_ITEM_RESULTS.STATUS.in(jStatuses))
				.and(TEST_ITEM.LAST_MODIFIED.gt(TimestampUtils.getTimestampBackFromNow(period)))
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
				.and(TEST_ITEM.LAST_MODIFIED.lt(TimestampUtils.getTimestampBackFromNow(period)))
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
	public boolean hasDescendantsWithStatusNotEqual(Long parentId, JStatusEnum status) {
		return dsl.fetchExists(dsl.selectOne()
				.from(TEST_ITEM)
				.join(TEST_ITEM_RESULTS)
				.on(TEST_ITEM.ITEM_ID.eq(TEST_ITEM_RESULTS.RESULT_ID))
				.where(TEST_ITEM.PARENT_ID.eq(parentId).and(TEST_ITEM_RESULTS.STATUS.notEqual(status))));
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
		return Optional.ofNullable(dsl.select()
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
	public Map<Long, String> selectPathNames(String path) {
		return dsl.select(TEST_ITEM.ITEM_ID, TEST_ITEM.NAME)
				.from(TEST_ITEM)
				.where(DSL.sql(TEST_ITEM.PATH + " @> cast(? AS LTREE)", path))
				.and(DSL.sql(TEST_ITEM.PATH + " != cast(? AS LTREE)", path))
				.orderBy(TEST_ITEM.ITEM_ID)
				.fetch()
				.stream()
				.collect(MoreCollectors.toLinkedMap(r -> r.get(TEST_ITEM.ITEM_ID), r -> r.get(TEST_ITEM.NAME)));
	}

	@Override
	public Map<Long, Map<Long, String>> selectPathNames(Collection<Long> ids) {

		JTestItem parentItem = TEST_ITEM.as("parent");
		JTestItem childItem = TEST_ITEM.as("child");
		return PATH_NAMES_FETCHER.apply(dsl.select(childItem.ITEM_ID, parentItem.ITEM_ID, parentItem.NAME, LAUNCH.NAME)
				.from(childItem)
				.join(parentItem)
				.on(DSL.sql(childItem.PATH + " <@ " + parentItem.PATH))
				.and(childItem.ITEM_ID.notEqual(parentItem.ITEM_ID))
				.join(LAUNCH)
				.on(childItem.LAUNCH_ID.eq(LAUNCH.ID))
				.where(childItem.ITEM_ID.in(ids))
				.orderBy(childItem.ITEM_ID, parentItem.ITEM_ID)
				.fetch());
	}

	public static final Function<Result<? extends Record>, Map<Long, Map<Long, String>>> PATH_NAMES_FETCHER = result -> {
		Map<Long, Map<Long, String>> content = Maps.newHashMap();
		JTestItem parentItem = TEST_ITEM.as("parent");
		JTestItem childItem = TEST_ITEM.as("child");
		result.forEach(record -> {
			Long parentItemId = record.get(parentItem.ITEM_ID);
			String parentName = record.get(parentItem.NAME);
			Long childItemId = record.get(childItem.ITEM_ID);

			Map<Long, String> pathNames = content.computeIfAbsent(childItemId, k -> {
				LinkedHashMap<Long, String> pathMapping = Maps.newLinkedHashMap();
				pathMapping.put(BigDecimal.ZERO.longValue(), record.get(LAUNCH.NAME));
				return pathMapping;
			});
			pathNames.put(parentItemId, parentName);
		});

		return content;
	};

	@Override
	public List<Long> selectIdsByAnalyzedWithLevelGte(boolean autoAnalyzed, Long launchId, int logLevel) {
		return dsl.selectDistinct(TEST_ITEM.ITEM_ID)
				.from(TEST_ITEM)
				.join(TEST_ITEM_RESULTS)
				.on(TEST_ITEM.ITEM_ID.eq(TEST_ITEM_RESULTS.RESULT_ID))
				.join(ISSUE)
				.on(ISSUE.ISSUE_ID.eq(TEST_ITEM_RESULTS.RESULT_ID))
				.join(LOG)
				.on(TEST_ITEM.ITEM_ID.eq(LOG.ITEM_ID))
				.where(TEST_ITEM.LAUNCH_ID.eq(launchId))
				.and(ISSUE.AUTO_ANALYZED.eq(autoAnalyzed))
				.and(LOG.LOG_LEVEL.greaterOrEqual(LogLevel.ERROR.toInt()))
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
		SelectQuery<? extends Record> itemQuery = QueryBuilder.newBuilder(filter).build();
		itemQuery.addJoin(LOG, JoinType.LEFT_OUTER_JOIN, TEST_ITEM.ITEM_ID.eq(LOG.ITEM_ID));
		itemQuery.addConditions(LOG.LOG_LEVEL.greaterOrEqual(logLevel), LOG.LOG_MESSAGE.like("%" + DSL.escape(pattern, '\\') + "%"));

		return dsl.select(fieldName(ITEMS, ID)).from(itemQuery.asTable(ITEMS)).fetchInto(Long.class);

	}

	@Override
	public List<Long> selectIdsByRegexPatternMatchedLogMessage(Queryable filter, Integer logLevel, String pattern) {
		SelectQuery<? extends Record> itemQuery = QueryBuilder.newBuilder(filter).build();
		itemQuery.addJoin(LOG, JoinType.LEFT_OUTER_JOIN, TEST_ITEM.ITEM_ID.eq(LOG.ITEM_ID));
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
		List<TestItem> items = TEST_ITEM_FETCHER.apply(dsl.fetch(QueryBuilder.newBuilder(filter).wrap().build()));
		fetchRetries(items);
		return items;
	}

	@Override
	public Page<TestItem> findByFilter(Queryable filter, Pageable pageable) {

		List<TestItem> items = TEST_ITEM_FETCHER.apply(dsl.fetch(QueryBuilder.newBuilder(filter)
				.with(pageable)
				.wrap()
				.withWrapperSort(pageable.getSort())
				.build()));

		fetchRetries(items);

		return PageableExecutionUtils.getPage(items, pageable, () -> dsl.fetchCount(QueryBuilder.newBuilder(filter).build()));
	}

	@Override
	public List<NestedStep> findAllNestedStepsByIds(Collection<Long> ids, Queryable logFilter) {
		JTestItem nested = TEST_ITEM.as(NESTED);
		SelectQuery<? extends Record> logsSelectQuery = QueryBuilder.newBuilder(logFilter).build();

		return dsl.select(TEST_ITEM.ITEM_ID,
				TEST_ITEM.NAME,
				TEST_ITEM.START_TIME,
				TEST_ITEM.TYPE,
				TEST_ITEM_RESULTS.STATUS,
				TEST_ITEM_RESULTS.END_TIME,
				TEST_ITEM_RESULTS.DURATION,
				DSL.field(DSL.exists(dsl.with(LOGS)
						.as(logsSelectQuery)
						.select()
						.from(LOG)
						.join(LOGS)
						.on(LOG.ID.eq(fieldName(LOGS, ID).cast(Long.class)))
						.where(LOG.ITEM_ID.eq(TEST_ITEM.ITEM_ID)))
						.orExists(dsl.select().from(nested).where(nested.PARENT_ID.eq(TEST_ITEM.ITEM_ID).and(nested.HAS_STATS.isFalse()))))
						.as(HAS_CONTENT),
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

	private void fetchRetries(List<TestItem> items) {

		List<TestItem> itemsWithRetries = items.stream().filter(TestItem::isHasRetries).collect(toList());

		if (CollectionUtils.isNotEmpty(itemsWithRetries)) {
			RETRIES_FETCHER.accept(items,
					dsl.select()
							.from(TEST_ITEM)
							.join(TEST_ITEM_RESULTS)
							.on(TEST_ITEM.ITEM_ID.eq(TEST_ITEM_RESULTS.RESULT_ID))
							.leftJoin(ITEM_ATTRIBUTE)
							.on(TEST_ITEM.ITEM_ID.eq(ITEM_ATTRIBUTE.ITEM_ID))
							.leftJoin(PARAMETER)
							.on(TEST_ITEM.ITEM_ID.eq(PARAMETER.ITEM_ID))
							.where(TEST_ITEM.RETRY_OF.in(itemsWithRetries.stream().map(TestItem::getItemId).collect(Collectors.toList())))
							.orderBy(TEST_ITEM.START_TIME)
							.fetch()
			);
		}
	}
}
