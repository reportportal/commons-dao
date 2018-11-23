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

import com.epam.ta.reportportal.commons.MoreCollectors;
import com.epam.ta.reportportal.commons.querygen.Filter;
import com.epam.ta.reportportal.commons.querygen.QueryBuilder;
import com.epam.ta.reportportal.entity.enums.StatusEnum;
import com.epam.ta.reportportal.entity.item.TestItem;
import com.epam.ta.reportportal.entity.item.issue.IssueEntity;
import com.epam.ta.reportportal.entity.item.issue.IssueType;
import com.epam.ta.reportportal.jooq.enums.JStatusEnum;
import com.epam.ta.reportportal.jooq.tables.JTestItem;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.SelectOnConditionStep;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.epam.ta.reportportal.dao.util.RecordMappers.ISSUE_TYPE_RECORD_MAPPER;
import static com.epam.ta.reportportal.dao.util.RecordMappers.TEST_ITEM_RECORD_MAPPER;
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
				.and(TEST_ITEM_RESULTS.STATUS.in(jStatuses)));
	}

	@Override
	public Boolean hasItemsInStatusByParent(Long parentId, StatusEnum... statuses) {
		List<JStatusEnum> jStatuses = Arrays.stream(statuses).map(it -> JStatusEnum.valueOf(it.name())).collect(toList());
		return dsl.fetchExists(commonTestItemDslSelect().where(TEST_ITEM.PARENT_ID.eq(parentId))
				.and(TEST_ITEM_RESULTS.STATUS.in(jStatuses)));
	}

	@Override
	public List<Long> selectIdsNotInIssueByLaunch(Long launchId, String issueType) {
		return commonTestItemDslSelect().join(ISSUE)
				.on(ISSUE.ISSUE_ID.eq(TEST_ITEM_RESULTS.RESULT_ID))
				.join(ISSUE_TYPE)
				.on(ISSUE.ISSUE_TYPE.eq(ISSUE_TYPE.ID))
				.where(TEST_ITEM.LAUNCH_ID.eq(launchId))
				.and(ISSUE_TYPE.LOCATOR.ne(issueType))
				.fetchInto(Long.class);
	}

	@Override
	public List<TestItem> selectItemsInIssueByLaunch(Long launchId, String issueType) {
		return commonTestItemDslSelect().join(ISSUE)
				.on(ISSUE.ISSUE_ID.eq(TEST_ITEM_RESULTS.RESULT_ID))
				.join(ISSUE_TYPE)
				.on(ISSUE.ISSUE_TYPE.eq(ISSUE_TYPE.ID))
				.where(TEST_ITEM.LAUNCH_ID.eq(launchId))
				.and(ISSUE_TYPE.LOCATOR.eq(issueType))
				.fetch(r -> {
					TestItem item = TEST_ITEM_RECORD_MAPPER.map(r);
					item.getItemResults().setIssue(r.into(IssueEntity.class));
					return item;
				});
	}

	@Override
	public StatusEnum identifyStatus(Long testItemId) {
		return dsl.fetchExists(dsl.selectOne()
				.from(TEST_ITEM)
				.join(TEST_ITEM_RESULTS)
				.on(TEST_ITEM.ITEM_ID.eq(TEST_ITEM_RESULTS.RESULT_ID))
				.where(TEST_ITEM.PARENT_ID.eq(testItemId).and(TEST_ITEM_RESULTS.STATUS.notEqual(JStatusEnum.PASSED)))) ?
				StatusEnum.FAILED :
				StatusEnum.PASSED;
	}

	@Override
	public List<IssueType> selectIssueLocatorsByProject(Long projectId) {
		return dsl.select()
				.from(PROJECT)
				.join(ISSUE_TYPE_PROJECT)
				.on(PROJECT.ID.eq(ISSUE_TYPE_PROJECT.PROJECT_ID))
				.join(ISSUE_TYPE)
				.on(ISSUE_TYPE_PROJECT.ISSUE_TYPE_ID.eq(ISSUE_TYPE.ID))
				.where(PROJECT.ID.eq(projectId))
				.fetch(ISSUE_TYPE_RECORD_MAPPER);
	}

	@Override
	public Optional<IssueType> selectIssueTypeByLocator(Long projectId, String locator) {
		return Optional.ofNullable(dsl.select()
				.from(PROJECT)
				.join(ISSUE_TYPE_PROJECT)
				.on(PROJECT.ID.eq(ISSUE_TYPE_PROJECT.PROJECT_ID))
				.join(ISSUE_TYPE)
				.on(ISSUE_TYPE_PROJECT.ISSUE_TYPE_ID.eq(ISSUE_TYPE.ID))
				.where(PROJECT.ID.eq(projectId))
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

	/**
	 * Commons select of an item with it's results and structure
	 *
	 * @return Select condition step
	 */
	private SelectOnConditionStep<Record> commonTestItemDslSelect() {
		return dsl.select().from(TEST_ITEM).join(TEST_ITEM_RESULTS).on(TEST_ITEM.ITEM_ID.eq(TEST_ITEM_RESULTS.RESULT_ID));
	}

	@Override
	public List<TestItem> findByFilter(Filter filter) {
		List<TestItem> items = TEST_ITEM_FETCHER.apply(dsl.fetch(QueryBuilder.newBuilder(filter).withWrapper(filter.getTarget()).build()));
		fetchRetries(items);
		return items;
	}

	@Override
	public Page<TestItem> findByFilter(Filter filter, Pageable pageable) {
		List<TestItem> items = TEST_ITEM_FETCHER.apply(dsl.fetch(QueryBuilder.newBuilder(filter)
				.with(pageable)
				.withWrapper(filter.getTarget())
				.withWrappedSort(pageable.getSort())
				.build()));
		fetchRetries(items);
		return PageableExecutionUtils.getPage(items, pageable, () -> dsl.fetchCount(QueryBuilder.newBuilder(filter).build()));
	}

	private void fetchRetries(List<TestItem> items) {
		RETRIES_FETCHER.accept(
				items,
				dsl.select()
						.from(TEST_ITEM)
						.join(TEST_ITEM_RESULTS)
						.on(TEST_ITEM.ITEM_ID.eq(TEST_ITEM_RESULTS.RESULT_ID))
						.leftJoin(ITEM_TAG)
						.on(TEST_ITEM.ITEM_ID.eq(ITEM_TAG.ITEM_ID))
						.leftJoin(PARAMETER)
						.on(TEST_ITEM.ITEM_ID.eq(PARAMETER.ITEM_ID))
						.where(TEST_ITEM.RETRY_OF.in(items.stream().map(TestItem::getItemId).collect(Collectors.toList())))
						.fetch()
		);
	}
}
