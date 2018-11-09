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
import com.epam.ta.reportportal.entity.enums.StatusEnum;
import com.epam.ta.reportportal.entity.launch.Launch;
import com.epam.ta.reportportal.jooq.enums.JLaunchModeEnum;
import com.epam.ta.reportportal.jooq.enums.JStatusEnum;
import com.epam.ta.reportportal.jooq.tables.JLaunch;
import com.epam.ta.reportportal.jooq.tables.JProject;
import com.epam.ta.reportportal.jooq.tables.JUsers;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.SelectConditionStep;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

import static com.epam.ta.reportportal.commons.EntityUtils.TO_LOCAL_DATE_TIME;
import static com.epam.ta.reportportal.dao.constant.WidgetContentRepositoryConstants.LAUNCHES;
import static com.epam.ta.reportportal.dao.util.RecordMappers.LAUNCH_RECORD_MAPPER;
import static com.epam.ta.reportportal.jooq.Tables.*;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

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

	public List<Launch> findByFilter(Filter filter) {
		return dsl.fetch(QueryBuilder.newBuilder(filter).build()).map(LAUNCH_RECORD_MAPPER);
	}

	public Page<Launch> findByFilter(Filter filter, Pageable pageable) {
		return PageableExecutionUtils.getPage(
				dsl.fetch(QueryBuilder.newBuilder(filter).with(pageable).build()).map(LAUNCH_RECORD_MAPPER),
				pageable,
				() -> dsl.fetchCount(QueryBuilder.newBuilder(filter).build())
		);
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
		return ofNullable(dsl.with(LAUNCHES)
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
		return ofNullable(dsl.select()
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

	@Override
	public Page<Long> getIdsModifiedBefore(Long projectId, Date before, Pageable pageable) {
		Page<Long> page = Page.empty(pageable);

		if (ofNullable(before).isPresent()) {
			Condition condition = LAUNCH.PROJECT_ID.eq(projectId)
					.and(LAUNCH.LAST_MODIFIED.lessOrEqual(Timestamp.valueOf(TO_LOCAL_DATE_TIME.apply(before))));
			page = PageableExecutionUtils.getPage(
					dsl.fetch(selectLaunchIdsQuery(condition).limit(pageable.getPageSize())
							.offset(Long.valueOf(pageable.getOffset()).intValue())).into(Long.class),
					pageable,
					() -> dsl.fetchCount(selectLaunchIdsQuery(condition))
			);
		}

		return page;
	}

	@Override
	public Page<Long> getIdsInStatusModifiedBefore(Long projectId, Date before, Pageable pageable, StatusEnum... statuses) {
		Page<Long> page = Page.empty(pageable);

		if (ofNullable(before).isPresent()) {
			List<JStatusEnum> jStatuses = Arrays.stream(statuses).map(it -> JStatusEnum.valueOf(it.name())).collect(toList());
			Condition condition = LAUNCH.PROJECT_ID.eq(projectId)
					.and(LAUNCH.STATUS.in(jStatuses))
					.and(LAUNCH.LAST_MODIFIED.lessOrEqual(Timestamp.valueOf(TO_LOCAL_DATE_TIME.apply(before))));
			page = PageableExecutionUtils.getPage(
					dsl.fetch(selectLaunchIdsQuery(condition).limit(pageable.getPageSize())
							.offset(Long.valueOf(pageable.getOffset()).intValue())).into(Long.class),
					pageable,
					() -> dsl.fetchCount(selectLaunchIdsQuery(condition))
			);
		}

		return page;
	}

	private SelectConditionStep<? extends Record> selectLaunchIdsQuery(Condition condition) {
		return dsl.select(LAUNCH.ID).from(LAUNCH).where(condition);
	}
}
