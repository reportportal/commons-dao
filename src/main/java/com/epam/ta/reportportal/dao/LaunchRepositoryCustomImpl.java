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

import com.epam.ta.reportportal.commons.querygen.Filter;
import com.epam.ta.reportportal.commons.querygen.QueryBuilder;
import com.epam.ta.reportportal.entity.launch.Launch;
import com.epam.ta.reportportal.jooq.enums.JLaunchModeEnum;
import com.epam.ta.reportportal.jooq.enums.JStatusEnum;
import com.epam.ta.reportportal.jooq.tables.JLaunch;
import com.epam.ta.reportportal.jooq.tables.JProject;
import com.epam.ta.reportportal.jooq.tables.JUsers;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.epam.ta.reportportal.dao.constant.WidgetContentRepositoryConstants.LAUNCHES;
import static com.epam.ta.reportportal.dao.util.RecordMappers.LAUNCH_RECORD_MAPPER;
import static com.epam.ta.reportportal.dao.util.ResultFetchers.LAUNCH_FETCHER;
import static com.epam.ta.reportportal.jooq.Tables.*;

/**
 * @author Pavel Bortnik
 */
@Repository
public class LaunchRepositoryCustomImpl implements LaunchRepositoryCustom {

	@Autowired
	private DSLContext dsl;

	@Override
	public Boolean identifyStatus(Long launchId) {
		return dsl.fetchExists(dsl.selectOne()
				.from(TEST_ITEM)
				.join(TEST_ITEM_RESULTS)
				.on(TEST_ITEM.ITEM_ID.eq(TEST_ITEM_RESULTS.RESULT_ID))
				.where(TEST_ITEM.LAUNCH_ID.eq(launchId)
						.and(TEST_ITEM_RESULTS.STATUS.eq(JStatusEnum.FAILED).or(TEST_ITEM_RESULTS.STATUS.eq(JStatusEnum.SKIPPED)))));
	}

	@Override
	public List<Launch> findByFilter(Filter filter) {
		return LAUNCH_FETCHER.apply(dsl.fetch(QueryBuilder.newBuilder(filter).withWrapper(filter.getTarget()).build()));
	}

	@Override
	public Page<Launch> findByFilter(Filter filter, Pageable pageable) {
		return PageableExecutionUtils.getPage(LAUNCH_FETCHER.apply(dsl.fetch(QueryBuilder.newBuilder(filter)
				.with(pageable)
				.withWrapper(filter.getTarget())
				.withWrappedSort(pageable.getSort())
				.build())), pageable, () -> dsl.fetchCount(QueryBuilder.newBuilder(filter).build()));
	}

	@Override
	public List<String> getLaunchNames(Long projectId, String value, String mode) {

		JLaunch l = LAUNCH.as("l");
		JProject p = PROJECT.as("p");

		return dsl.select()
				.from(l)
				.leftJoin(p)
				.on(l.PROJECT_ID.eq(p.ID))
				.where(p.ID.eq(projectId))
				.and(l.MODE.eq(JLaunchModeEnum.valueOf(mode)))
				.and(l.NAME.like("%" + value + "%"))
				.fetch(l.NAME);
	}

	@Override
	public List<String> getOwnerNames(Long projectId, String value, String mode) {

		JLaunch l = LAUNCH.as("l");
		JProject p = PROJECT.as("p");
		JUsers u = USERS.as("u");

		return dsl.selectDistinct()
				.from(l)
				.leftJoin(p)
				.on(l.PROJECT_ID.eq(p.ID))
				.leftJoin(u)
				.on(l.USER_ID.eq(u.ID))
				.where(p.ID.eq(projectId))
				.and(u.FULL_NAME.like("%" + value + "%"))
				.and(l.MODE.eq(JLaunchModeEnum.valueOf(mode)))
				.fetch(u.FULL_NAME);
	}

	@Override
	public Map<String, String> getStatuses(Long projectId, Long[] ids) {

		JLaunch l = LAUNCH.as("l");
		JProject p = PROJECT.as("p");

		//		return dsl.select()
		//				.from(l)
		//				.leftJoin(p)
		//				.on(l.PROJECT_ID.eq(p.ID))
		//				.where(p.ID.eq(projectId))
		//				.and(l.ID.in(ids))
		//				.fetch(LAUNCH_MAPPER)
		//				.stream()
		//				.collect(Collectors.toMap(launch -> String.valueOf(launch.getId()), launch -> launch.getStatus().toString()));
		return Collections.emptyMap();
	}

	@Override
	public Optional<Launch> findLatestByNameAndFilter(String launchName, Filter filter) {
		return Optional.ofNullable(dsl.with(LAUNCHES)
				.as(QueryBuilder.newBuilder(filter).build())
				.select()
				.distinctOn(LAUNCH.NAME)
				.from(LAUNCH)
				.where(LAUNCH.NAME.eq(launchName))
				.orderBy(LAUNCH.NAME, LAUNCH.NUMBER.desc())
				.fetchOneInto(Launch.class));
	}

	@Override
	public Page<Launch> findAllLatestByFilter(Filter filter, Pageable pageable) {

		return PageableExecutionUtils.getPage(
				dsl.fetch(dsl.with(LAUNCHES)
						.as(QueryBuilder.newBuilder(filter).with(pageable).build())
						.select()
						.distinctOn(LAUNCH.NAME)
						.from(LAUNCH)
						.orderBy(LAUNCH.NAME, LAUNCH.NUMBER.desc())).map(LAUNCH_RECORD_MAPPER),
				pageable,
				() -> dsl.fetchCount(dsl.with(LAUNCHES)
						.as(QueryBuilder.newBuilder(filter).build())
						.select()
						.distinctOn(LAUNCH.NAME)
						.from(LAUNCH)
						.orderBy(LAUNCH.NAME, LAUNCH.NUMBER.desc()))
		);
	}

	@Override
	public Optional<Launch> findLastRun(Long projectId, String mode) {
		return Optional.ofNullable(dsl.select()
				.from(LAUNCH)
				.where(LAUNCH.PROJECT_ID.eq(projectId))
				.and(LAUNCH.MODE.eq(JLaunchModeEnum.valueOf(mode)))
				.and(LAUNCH.STATUS.ne(JStatusEnum.IN_PROGRESS))
				.limit(1)
				.fetchOne()
				.into(Launch.class));
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
				.fetchMap(USERS.LOGIN, DSL.field("count", Integer.class));
	}
}
