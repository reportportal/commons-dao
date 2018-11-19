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
import com.epam.ta.reportportal.entity.enums.ProjectAttributeEnum;
import com.epam.ta.reportportal.entity.project.Project;
import com.epam.ta.reportportal.entity.project.ProjectInfo;
import com.epam.ta.reportportal.jooq.enums.JLaunchModeEnum;
import com.epam.ta.reportportal.jooq.enums.JStatusEnum;
import org.jooq.DSLContext;
import org.jooq.Record1;
import org.jooq.SelectForUpdateStep;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.epam.ta.reportportal.dao.util.JooqFieldNameTransformer.fieldName;
import static com.epam.ta.reportportal.dao.util.RecordMappers.PROJECT_FETCHER;
import static com.epam.ta.reportportal.dao.util.RecordMappers.PROJECT_MAPPER;
import static com.epam.ta.reportportal.jooq.Tables.*;
import static org.jooq.impl.DSL.choose;
import static org.jooq.impl.DSL.name;

@Repository
public class ProjectRepositoryCustomImpl implements ProjectRepositoryCustom {

	private static final String USERS_QUANTITY = "usersQuantity";
	private static final String LAUNCHES_QUANTITY = "launchesQuantity";
	private static final String LAST_RUN = "lastRun";

	@Autowired
	private DSLContext dsl;

	@Override
	public List<Project> findByFilter(Filter filter) {
		return PROJECT_FETCHER.apply(dsl.fetch(QueryBuilder.newBuilder(filter).withWrapper(filter.getTarget()).build()));
	}

	@Override
	public Page<Project> findByFilter(Filter filter, Pageable pageable) {
		return PageableExecutionUtils.getPage(PROJECT_FETCHER.apply(dsl.fetch(QueryBuilder.newBuilder(filter)
				.with(pageable)
				.withWrapper(filter.getTarget())
				.with(pageable.getSort())
				.build())), pageable, () -> dsl.fetchCount(QueryBuilder.newBuilder(filter).build()));
	}

	@Override
	public Page<ProjectInfo> findProjectInfoByFilter(Filter filter, Pageable pageable, String mode) {
		final String FILTERED_PROJECT = "filtered_project";
		return PageableExecutionUtils.getPage(dsl.with(FILTERED_PROJECT)
				.as(QueryBuilder.newBuilder(filter).with(pageable).build())
				.select(DSL.countDistinct(PROJECT_USER.USER_ID).as(USERS_QUANTITY),
						DSL.countDistinct(choose().when(LAUNCH.MODE.eq(JLaunchModeEnum.valueOf(mode))
								.and(LAUNCH.STATUS.ne(JStatusEnum.IN_PROGRESS)), LAUNCH.ID)).as(LAUNCHES_QUANTITY),
						DSL.max(LAUNCH.START_TIME).as(LAST_RUN),
						PROJECT.ID,
						PROJECT.CREATION_DATE,
						PROJECT.NAME,
						PROJECT.PROJECT_TYPE
				)
				.from(PROJECT)
				.leftJoin(PROJECT_USER)
				.on(PROJECT.ID.eq(PROJECT_USER.PROJECT_ID))
				.leftJoin(LAUNCH)
				.on(PROJECT.ID.eq(LAUNCH.PROJECT_ID))
				.join(DSL.table(name(FILTERED_PROJECT)))
				.on(fieldName(FILTERED_PROJECT, PROJECT.ID.getName()).cast(Long.class).eq(PROJECT.ID))
				.groupBy(PROJECT.ID, PROJECT.CREATION_DATE, PROJECT.NAME, PROJECT.PROJECT_TYPE)
				.fetch()
				.into(ProjectInfo.class), pageable, () -> dsl.fetchCount(QueryBuilder.newBuilder(filter).build()));
	}

	@Override
	public ProjectInfo findProjectInfoFromDate(Long projectId, LocalDateTime fromDate, String mode) {
		return dsl.select(
				DSL.countDistinct(PROJECT_USER.USER_ID).as(USERS_QUANTITY),
				DSL.countDistinct(choose().when(LAUNCH.MODE.eq(JLaunchModeEnum.valueOf(mode))
						.and(LAUNCH.START_TIME.gt(Timestamp.valueOf(fromDate)))
						.and(LAUNCH.STATUS.ne(JStatusEnum.IN_PROGRESS)), LAUNCH.ID)).as(LAUNCHES_QUANTITY),
				DSL.max(LAUNCH.START_TIME).as(LAST_RUN),
				PROJECT.ID,
				PROJECT.CREATION_DATE,
				PROJECT.NAME,
				PROJECT.PROJECT_TYPE
		)
				.from(PROJECT)
				.leftJoin(PROJECT_USER)
				.on(PROJECT.ID.eq(PROJECT_USER.PROJECT_ID))
				.leftJoin(LAUNCH)
				.on(PROJECT.ID.eq(LAUNCH.PROJECT_ID))
				.where(PROJECT.ID.eq(projectId))
				.groupBy(PROJECT.ID, PROJECT.CREATION_DATE, PROJECT.NAME, PROJECT.PROJECT_TYPE)
				.fetchOne()
				.into(ProjectInfo.class);
	}

	@Override
	public Optional<String> findPersonalProjectName(String username) {
		return Optional.ofNullable(dsl.select()
				.from(USERS)
				.join(PROJECT)
				.on(USERS.DEFAULT_PROJECT_ID.eq(PROJECT.ID))
				.where(USERS.LOGIN.eq(username))
				.fetchOne(PROJECT.NAME));
	}

	@Override
	public List<String> findAllProjectNames() {
		return dsl.select(PROJECT.NAME).from(PROJECT).fetchInto(String.class);
	}

	@Override
	public Page<Project> findAllIdsAndProjectAttributes(ProjectAttributeEnum projectAttribute, Pageable pageable) {

		SelectForUpdateStep<Record1<Long>> subquery = DSL.select(PROJECT.ID)
				.distinctOn(PROJECT.ID)
				.from(PROJECT)
				.join(PROJECT_ATTRIBUTE)
				.on(PROJECT.ID.eq(PROJECT_ATTRIBUTE.PROJECT_ID))
				.join(ATTRIBUTE)
				.on(PROJECT_ATTRIBUTE.ATTRIBUTE_ID.eq(ATTRIBUTE.ID))
				.where(ATTRIBUTE.NAME.eq(projectAttribute.getAttribute()))
				.orderBy(PROJECT.ID)
				.limit(pageable.getPageSize())
				.offset(Long.valueOf(pageable.getOffset()).intValue());

		return PageableExecutionUtils.getPage(PROJECT_FETCHER.apply(dsl.fetch(dsl.select()
				.from(PROJECT)
				.join(subquery.asTable("subquery"))
				.on(fieldName("subquery", "id").cast(Long.class).eq(PROJECT.ID))
				.join(PROJECT_ATTRIBUTE)
				.on(PROJECT.ID.eq(PROJECT_ATTRIBUTE.PROJECT_ID))
				.join(ATTRIBUTE)
				.on(PROJECT_ATTRIBUTE.ATTRIBUTE_ID.eq(ATTRIBUTE.ID)))), pageable, () -> dsl.fetchCount(subquery));
	}
}
