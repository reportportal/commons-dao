/*
 * Copyright 2017 EPAM Systems
 *
 *
 * This file is part of EPAM Report Portal.
 * https://github.com/reportportal/service-api
 *
 * Report Portal is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Report Portal is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Report Portal.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.commons.querygen.Filter;
import com.epam.ta.reportportal.commons.querygen.QueryBuilder;
import com.epam.ta.reportportal.entity.enums.StatusEnum;
import com.epam.ta.reportportal.entity.enums.TestItemTypeEnum;
import com.epam.ta.reportportal.entity.item.Parameter;
import com.epam.ta.reportportal.entity.item.TestItem;
import com.epam.ta.reportportal.entity.item.issue.IssueEntity;
import com.epam.ta.reportportal.entity.item.issue.IssueGroup;
import com.epam.ta.reportportal.entity.item.issue.IssueType;
import com.epam.ta.reportportal.jooq.Tables;
import com.epam.ta.reportportal.jooq.enums.JStatusEnum;
import com.epam.ta.reportportal.jooq.tables.JTestItem;
import com.epam.ta.reportportal.jooq.tables.JTestItemResults;
import com.epam.ta.reportportal.jooq.tables.JTestItemStructure;
import com.google.common.collect.Lists;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.types.DayToSecond;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.epam.ta.reportportal.jooq.Tables.*;
import static java.util.stream.Collectors.toList;
import static org.jooq.impl.DSL.*;

/**
 * @author Pavel Bortnik
 */
@Repository
public class TestItemRepositoryCustomImpl implements TestItemRepositoryCustom {

	private static final RecordMapper<? super Record, TestItem> TEST_ITEM_MAPPER = r -> new TestItem(r.get(JTestItem.TEST_ITEM.ITEM_ID,
			Long.class
	),
			r.get(JTestItem.TEST_ITEM.NAME, String.class),
			r.get(JTestItem.TEST_ITEM.TYPE, TestItemTypeEnum.class),
			r.get(JTestItem.TEST_ITEM.START_TIME, LocalDateTime.class),
			r.get(JTestItem.TEST_ITEM.DESCRIPTION, String.class),
			r.get(JTestItem.TEST_ITEM.LAST_MODIFIED, LocalDateTime.class),
			r.get(JTestItem.TEST_ITEM.UNIQUE_ID, String.class)
	);

	private static final RecordMapper<? super Record, IssueType> ISSUE_TYPE_MAPPER = r -> {
		IssueType type = r.into(IssueType.class);
		type.setIssueGroup(r.into(IssueGroup.class));
		return type;
	};

	/**
	 * Fetching record results into Test item object.
	 */
	private static final RecordMapper<? super Record, TestItem> TEST_ITEM_FETCH = r -> {
		TestItem testItem = r.into(TestItem.class);
		//		testItem.setTestItemStructure(r.into(TestItemStructure.class));
		//		testItem.setTestItemResults(r.into(TestItemResults.class));
		return testItem;
	};

	private DSLContext dsl;

	@Autowired
	public void setDsl(DSLContext dsl) {
		this.dsl = dsl;
	}

	@Override
	public List<TestItem> selectAllDescendants(Long itemId) {
		return commonTestItemDslSelect().where(TEST_ITEM_STRUCTURE.PARENT_ID.eq(itemId)).fetch(TEST_ITEM_FETCH);
	}

	@Override
	public List<TestItem> selectAllDescendantsWithChildren(Long itemId) {
		return commonTestItemDslSelect().where(TEST_ITEM_STRUCTURE.PARENT_ID.eq(itemId))
				.andExists(dsl.selectOne().from(TEST_ITEM_STRUCTURE).where(TEST_ITEM_STRUCTURE.PARENT_ID.eq(TEST_ITEM.ITEM_ID)))
				.fetch(TEST_ITEM_FETCH);
	}

	public Map<Long, String> selectPathNames(Long itemId) {
		JTestItemStructure tis = TEST_ITEM_STRUCTURE.as("tis");
		JTestItem ti = TEST_ITEM.as("ti");
		return dsl.withRecursive("p")
				.as(dsl.select(TEST_ITEM_STRUCTURE.STRUCTURE_ID, TEST_ITEM_STRUCTURE.PARENT_ID, TEST_ITEM.NAME)
						.from(TEST_ITEM_STRUCTURE)
						.join(TEST_ITEM)
						.onKey()
						.where(TEST_ITEM_STRUCTURE.STRUCTURE_ID.eq(itemId))
						.unionAll(dsl.select(tis.STRUCTURE_ID, tis.PARENT_ID, ti.NAME)
								.from(tis)
								.join(ti)
								.onKey()
								.join(name("p"))
								.on(tis.STRUCTURE_ID.eq(field(name("p", "parent_id"), Long.class)))))
				.select()
				.from(name("p"))
				.fetch()
				.intoMap(field(name(TEST_ITEM_STRUCTURE.STRUCTURE_ID.getName()), Long.class), field(name("name"), String.class));
	}

	@Override
	public List<TestItem> selectItemsInStatusByLaunch(Long launchId, StatusEnum... statuses) {
		List<JStatusEnum> jStatuses = Arrays.stream(statuses).map(it -> JStatusEnum.valueOf(it.name())).collect(toList());
		return commonTestItemDslSelect().where(TEST_ITEM_STRUCTURE.LAUNCH_ID.eq(launchId).and(TEST_ITEM_RESULTS.STATUS.in(jStatuses)))
				.fetch(TEST_ITEM_FETCH);
	}

	@Override
	public List<TestItem> selectItemsInStatusByParent(Long itemId, StatusEnum... statuses) {
		List<JStatusEnum> jStatuses = Arrays.stream(statuses).map(it -> JStatusEnum.valueOf(it.name())).collect(toList());
		return commonTestItemDslSelect().where(TEST_ITEM_STRUCTURE.PARENT_ID.eq(itemId).and(TEST_ITEM_RESULTS.STATUS.in(jStatuses)))
				.fetch(TEST_ITEM_FETCH);
	}

	@Override
	public Boolean hasItemsInStatusByLaunch(Long launchId, StatusEnum... statuses) {
		List<JStatusEnum> jStatuses = Arrays.stream(statuses).map(it -> JStatusEnum.valueOf(it.name())).collect(toList());
		return dsl.fetchExists(dsl.selectOne()
				.from(TEST_ITEM_STRUCTURE)
				.join(TEST_ITEM_RESULTS)
				.onKey()
				.where(TEST_ITEM_STRUCTURE.LAUNCH_ID.eq(launchId))
				.and(TEST_ITEM_RESULTS.STATUS.in(jStatuses)));
	}

	@Override
	public Boolean hasItemsInStatusByParent(Long parentId, StatusEnum... statuses) {
		List<JStatusEnum> jStatuses = Arrays.stream(statuses).map(it -> JStatusEnum.valueOf(it.name())).collect(toList());
		return dsl.fetchExists(commonTestItemDslSelect().where(TEST_ITEM_STRUCTURE.PARENT_ID.eq(parentId))
				.and(TEST_ITEM_RESULTS.STATUS.in(jStatuses)));
	}

	@Override
	public List<Long> selectIdsNotInIssueByLaunch(Long launchId, String issueType) {
		return commonTestItemDslSelect().join(ISSUE)
				.on(ISSUE.ISSUE_ID.eq(TEST_ITEM_RESULTS.RESULT_ID))
				.join(ISSUE_TYPE)
				.on(ISSUE.ISSUE_TYPE.eq(ISSUE_TYPE.ID))
				.where(TEST_ITEM_STRUCTURE.LAUNCH_ID.eq(launchId))
				.and(ISSUE_TYPE.LOCATOR.ne(issueType))
				.fetchInto(Long.class);
	}

	@Override
	public List<TestItem> selectItemsInIssueByLaunch(Long launchId, String issueType) {
		return commonTestItemDslSelect().join(ISSUE)
				.on(ISSUE.ISSUE_ID.eq(TEST_ITEM_RESULTS.RESULT_ID))
				.join(ISSUE_TYPE)
				.on(ISSUE.ISSUE_TYPE.eq(ISSUE_TYPE.ID))
				.where(TEST_ITEM_STRUCTURE.LAUNCH_ID.eq(launchId))
				.and(ISSUE_TYPE.LOCATOR.eq(issueType))
				.fetch(r -> {
					TestItem item = TEST_ITEM_FETCH.map(r);
					item.getItemStructure().getItemResults().setIssue(r.into(IssueEntity.class));
					return item;
				});
	}

	@Override
	public StatusEnum identifyStatus(Long testItemId) {
		return dsl.fetchExists(dsl.selectOne()
				.from(TEST_ITEM)
				.join(TEST_ITEM_STRUCTURE)
				.on(TEST_ITEM.ITEM_ID.eq(TEST_ITEM_STRUCTURE.STRUCTURE_ID))
				.join(TEST_ITEM_RESULTS)
				.on(TEST_ITEM.ITEM_ID.eq(TEST_ITEM_RESULTS.RESULT_ID))
				.where(TEST_ITEM_STRUCTURE.PARENT_ID.eq(testItemId)
						.and(TEST_ITEM_RESULTS.STATUS.eq(JStatusEnum.FAILED).or(TEST_ITEM_RESULTS.STATUS.eq(JStatusEnum.SKIPPED))))) ?
				StatusEnum.FAILED :
				StatusEnum.PASSED;
	}

	@Override
	public boolean hasChildren(Long testItemId) {
		return dsl.fetchExists(dsl.selectOne().from(TEST_ITEM_STRUCTURE).where(TEST_ITEM_STRUCTURE.PARENT_ID.eq(testItemId)));
	}

	@Override
	public List<IssueType> selectIssueLocatorsByProject(Long projectId) {
		return dsl.select()
				.from(PROJECT)
				.join(PROJECT_CONFIGURATION)
				.on(PROJECT_CONFIGURATION.ID.eq(PROJECT.ID))
				.join(ISSUE_TYPE_PROJECT_CONFIGURATION)
				.on(PROJECT_CONFIGURATION.ID.eq(ISSUE_TYPE_PROJECT_CONFIGURATION.CONFIGURATION_ID))
				.join(Tables.ISSUE_TYPE)
				.on(ISSUE_TYPE_PROJECT_CONFIGURATION.ISSUE_TYPE_ID.eq(Tables.ISSUE_TYPE.ID))
				.fetch(ISSUE_TYPE_MAPPER);
	}

	@Override
	public void interruptInProgressItems(Long launchId) {
		JTestItemResults res = TEST_ITEM_RESULTS.as("res");
		JTestItem ts = TEST_ITEM.as("ts");
		dsl.update(res)
				.set(res.STATUS, JStatusEnum.INTERRUPTED)
				.set(res.DURATION, extractEpochFrom(DSL.timestampDiff(currentTimestamp(), ts.START_TIME)))
				.from(ts)
				.where(TEST_ITEM_STRUCTURE.LAUNCH_ID.eq(launchId))
				.and(res.RESULT_ID.eq(ts.ITEM_ID))
				.and(res.STATUS.eq(JStatusEnum.IN_PROGRESS))
				.execute();
	}

	@Override
	public Optional<IssueType> selectIssueTypeByLocator(Long projectId, String locator) {
		return Optional.ofNullable(dsl.select()
				.from(ISSUE_TYPE)
				.join(ISSUE_GROUP)
				.on(ISSUE_TYPE.ISSUE_GROUP_ID.eq(ISSUE_GROUP.ISSUE_GROUP_ID))
				.join(ISSUE_TYPE_PROJECT_CONFIGURATION)
				.on(ISSUE_TYPE.ID.eq(ISSUE_TYPE_PROJECT_CONFIGURATION.ISSUE_TYPE_ID))
				.where(ISSUE_TYPE_PROJECT_CONFIGURATION.CONFIGURATION_ID.eq(projectId))
				.and(ISSUE_TYPE.LOCATOR.eq(locator))
				.fetchOne(ISSUE_TYPE_MAPPER));
	}

	/**
	 * Extracts duration in seconds from interval.
	 *
	 * @param field Interval
	 * @return Duration in seconds
	 */
	private static Field<Double> extractEpochFrom(Field<DayToSecond> field) {
		return DSL.field("extract(epoch from {0})", Double.class, field);
	}

	/**
	 * Commons select of an item with it's results and structure
	 *
	 * @return Select condition step
	 */

	private SelectOnConditionStep<Record> commonTestItemDslSelect() {
		return dsl.select()
				.from(TEST_ITEM)
				.join(TEST_ITEM_STRUCTURE)
				.on(TEST_ITEM.ITEM_ID.eq(TEST_ITEM_STRUCTURE.STRUCTURE_ID))
				.join(TEST_ITEM_RESULTS)
				.on(TEST_ITEM.ITEM_ID.eq(TEST_ITEM_RESULTS.RESULT_ID));
	}

	@Override
	public List<TestItem> findByFilter(Filter filter) {

		Map<TestItem, List<Parameter>> testItemListMap = dsl.fetch(QueryBuilder.newBuilder(filter).build())
				.intoGroups(TEST_ITEM_MAPPER, Parameter.class);

		testItemListMap.forEach(TestItem::setParameters);

		return Lists.newArrayList(testItemListMap.keySet());
	}
}
