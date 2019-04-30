/*
 * Copyright 2018 EPAM Systems
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
import com.epam.ta.reportportal.dao.util.QueryUtils;
import com.epam.ta.reportportal.entity.enums.LaunchModeEnum;
import com.epam.ta.reportportal.entity.enums.StatusEnum;
import com.epam.ta.reportportal.entity.launch.Launch;
import com.epam.ta.reportportal.jooq.enums.JLaunchModeEnum;
import com.epam.ta.reportportal.jooq.enums.JStatusEnum;
import com.epam.ta.reportportal.util.SortUtils;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.epam.ta.reportportal.commons.querygen.FilterTarget.FILTERED_QUERY;
import static com.epam.ta.reportportal.dao.constant.WidgetContentRepositoryConstants.ID;
import static com.epam.ta.reportportal.dao.constant.WidgetContentRepositoryConstants.LAUNCHES;
import static com.epam.ta.reportportal.dao.util.JooqFieldNameTransformer.fieldName;
import static com.epam.ta.reportportal.dao.util.ResultFetchers.LAUNCH_FETCHER;
import static com.epam.ta.reportportal.jooq.Tables.*;
import static java.util.Optional.ofNullable;
import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.name;

/**
 * @author Pavel Bortnik
 */
@Repository
public class LaunchRepositoryCustomImpl implements LaunchRepositoryCustom {

	@Autowired
	private DSLContext dsl;

	@Override
	public boolean hasItemsInStatuses(Long launchId, List<JStatusEnum> statuses) {
		return dsl.fetchExists(dsl.selectOne()
				.from(TEST_ITEM)
				.join(TEST_ITEM_RESULTS)
				.on(TEST_ITEM.ITEM_ID.eq(TEST_ITEM_RESULTS.RESULT_ID))
				.where(TEST_ITEM.LAUNCH_ID.eq(launchId).and(TEST_ITEM_RESULTS.STATUS.in(statuses))));
	}

	@Override
	public List<Launch> findByFilter(Queryable filter) {
		return LAUNCH_FETCHER.apply(dsl.fetch(QueryBuilder.newBuilder(filter).wrap().build()));
	}

	@Override
	public Page<Launch> findByFilter(Queryable filter, Pageable pageable) {
		return PageableExecutionUtils.getPage(LAUNCH_FETCHER.apply(dsl.fetch(QueryBuilder.newBuilder(filter)
				.with(pageable)
				.wrap()
				.withWrapperSort(pageable.getSort())
				.build())), pageable, () -> dsl.fetchCount(QueryBuilder.newBuilder(filter).build()));
	}

	@Override
	public List<String> getLaunchNamesByModeExcludedByStatus(Long projectId, String value, LaunchModeEnum mode, StatusEnum status) {
		return dsl.selectDistinct(LAUNCH.NAME)
				.from(LAUNCH)
				.leftJoin(PROJECT)
				.on(LAUNCH.PROJECT_ID.eq(PROJECT.ID))
				.where(PROJECT.ID.eq(projectId))
				.and(LAUNCH.MODE.eq(JLaunchModeEnum.valueOf(mode.name())))
				.and(LAUNCH.STATUS.notEqual(JStatusEnum.valueOf(status.name())))
				.and(LAUNCH.NAME.likeIgnoreCase("%" + value + "%"))
				.fetch(LAUNCH.NAME);
	}

	@Override
	public List<String> getOwnerNames(Long projectId, String value, String mode) {
		return dsl.selectDistinct(USERS.LOGIN)
				.from(LAUNCH)
				.leftJoin(PROJECT)
				.on(LAUNCH.PROJECT_ID.eq(PROJECT.ID))
				.leftJoin(USERS)
				.on(LAUNCH.USER_ID.eq(USERS.ID))
				.where(PROJECT.ID.eq(projectId))
				.and(USERS.LOGIN.likeIgnoreCase("%" + value + "%"))
				.and(LAUNCH.MODE.eq(JLaunchModeEnum.valueOf(mode)))
				.fetch(USERS.LOGIN);
	}

	@Override
	public Map<String, String> getStatuses(Long projectId, Long[] ids) {
		return dsl.select(LAUNCH.ID, LAUNCH.STATUS)
				.from(LAUNCH)
				.where(LAUNCH.PROJECT_ID.eq(projectId))
				.and(LAUNCH.ID.in(ids))
				.fetch()
				.intoMap(record -> String.valueOf(record.component1()), record -> record.component2().getName());
	}

	@Override
	public Optional<Launch> findLatestByFilter(Filter filter) {
		return ofNullable(dsl.with(LAUNCHES)
				.as(QueryUtils.createQueryBuilderWithLatestLaunchesOption(filter, true).build())
				.select()
				.from(LAUNCH)
				.join(LAUNCHES)
				.on(field(name(LAUNCHES, ID), Long.class).eq(LAUNCH.ID))
				.orderBy(LAUNCH.NAME, LAUNCH.NUMBER.desc())
				.fetchOneInto(Launch.class));
	}

	@Override
	public Page<Launch> findAllLatestByFilter(Filter filter, Pageable pageable) {

		return PageableExecutionUtils.getPage(
				LAUNCH_FETCHER.apply(dsl.with(LAUNCHES)
						.as(QueryUtils.createQueryBuilderWithLatestLaunchesOption(filter, true).with(pageable).build())
						.select()
						.from(LAUNCH)
						.join(LAUNCHES)
						.on(field(name(LAUNCHES, ID), Long.class).eq(LAUNCH.ID))
						.leftJoin(STATISTICS)
						.on(LAUNCH.ID.eq(STATISTICS.LAUNCH_ID))
						.leftJoin(STATISTICS_FIELD)
						.on(STATISTICS.STATISTICS_FIELD_ID.eq(STATISTICS_FIELD.SF_ID))
						.leftJoin(ITEM_ATTRIBUTE)
						.on(LAUNCH.ID.eq(ITEM_ATTRIBUTE.LAUNCH_ID))
						.orderBy(SortUtils.TO_SORT_FIELDS.apply(pageable.getSort(), filter.getTarget()))
						.fetch()),
				pageable,
				() -> dsl.fetchCount(dsl.with(LAUNCHES)
						.as(QueryUtils.createQueryBuilderWithLatestLaunchesOption(filter, true).build())
						.selectOne()
						.distinctOn(LAUNCH.NAME)
						.from(LAUNCH)
						.join(LAUNCHES)
						.on(field(name(LAUNCHES, ID), Long.class).eq(LAUNCH.ID)))
		);
	}

	@Override
	public List<Long> findLaunchIdsByProjectId(Long projectId) {
		return dsl.select(LAUNCH.ID).from(LAUNCH).where(LAUNCH.PROJECT_ID.eq(projectId)).fetchInto(Long.class);
	}

	@Override
	public Optional<Launch> findLastRun(Long projectId, String mode) {
		return LAUNCH_FETCHER.apply(dsl.fetch(dsl.with(FILTERED_QUERY)
				.as(dsl.select(LAUNCH.ID)
						.from(LAUNCH)
						.where(LAUNCH.PROJECT_ID.eq(projectId)
								.and(LAUNCH.MODE.eq(JLaunchModeEnum.valueOf(mode)).and(LAUNCH.STATUS.ne(JStatusEnum.IN_PROGRESS))))
						.orderBy(LAUNCH.START_TIME.desc())
						.limit(1))
				.select()
				.from(LAUNCH)
				.join(FILTERED_QUERY)
				.on(LAUNCH.ID.eq(fieldName(FILTERED_QUERY, ID).cast(Long.class)))
				.leftJoin(STATISTICS)
				.on(LAUNCH.ID.eq(STATISTICS.LAUNCH_ID))
				.leftJoin(STATISTICS_FIELD)
				.on(STATISTICS.STATISTICS_FIELD_ID.eq(STATISTICS_FIELD.SF_ID))
				.leftJoin(ITEM_ATTRIBUTE)
				.on(LAUNCH.ID.eq(ITEM_ATTRIBUTE.LAUNCH_ID)))).stream().findFirst();
	}

	@Override
	public Integer countLaunches(Long projectId, String mode, LocalDateTime from) {
		return dsl.fetchCount(
				LAUNCH,
				LAUNCH.PROJECT_ID.eq(projectId)
						.and(LAUNCH.MODE.eq(JLaunchModeEnum.valueOf(mode)))
						.and(LAUNCH.STATUS.ne(JStatusEnum.IN_PROGRESS).and(LAUNCH.START_TIME.greaterThan(Timestamp.valueOf(from))))
		);
	}

	@Override
	public Integer countLaunches(Long projectId, String mode) {
		return dsl.fetchCount(
				LAUNCH,
				LAUNCH.PROJECT_ID.eq(projectId)
						.and(LAUNCH.MODE.eq(JLaunchModeEnum.valueOf(mode)))
						.and(LAUNCH.STATUS.ne(JStatusEnum.IN_PROGRESS))
		);
	}

	@Override
	public Map<String, Integer> countLaunchesGroupedByOwner(Long projectId, String mode, LocalDateTime from) {
		return dsl.select(USERS.LOGIN, DSL.count().as("count"))
				.from(LAUNCH)
				.join(USERS)
				.on(LAUNCH.USER_ID.eq(USERS.ID))
				.where(LAUNCH.PROJECT_ID.eq(projectId)
						.and(LAUNCH.MODE.eq(JLaunchModeEnum.valueOf(mode))
								.and(LAUNCH.STATUS.ne(JStatusEnum.IN_PROGRESS))
								.and(LAUNCH.START_TIME.greaterThan(Timestamp.valueOf(from)))))
				.groupBy(USERS.LOGIN)
				.fetchMap(USERS.LOGIN, field("count", Integer.class));
	}

}
