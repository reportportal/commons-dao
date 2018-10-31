/*
 *  Copyright (C) 2018 EPAM Systems
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.commons.querygen.Filter;
import com.epam.ta.reportportal.commons.querygen.QueryBuilder;
import com.epam.ta.reportportal.entity.project.Project;
import com.epam.ta.reportportal.entity.project.ProjectInfo;
import com.epam.ta.reportportal.jooq.enums.JLaunchModeEnum;
import com.epam.ta.reportportal.jooq.enums.JStatusEnum;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.epam.ta.reportportal.dao.util.JooqFieldNameTransformer.fieldName;
import static com.epam.ta.reportportal.dao.util.RecordMappers.PROJECT_MAPPER;
import static com.epam.ta.reportportal.jooq.Tables.*;
import static org.jooq.impl.DSL.choose;
import static org.jooq.impl.DSL.name;

@Repository
public class ProjectRepositoryCustomImpl implements ProjectRepositoryCustom {

	@Autowired
	private DSLContext dsl;

	@Override
	public List<Project> findByFilter(Filter filter) {

		return dsl.fetch(QueryBuilder.newBuilder(filter).build()).map(PROJECT_MAPPER);
	}

	@Override
	public Page<Project> findByFilter(Filter filter, Pageable pageable) {
		return PageableExecutionUtils.getPage(
				dsl.fetch(QueryBuilder.newBuilder(filter).with(pageable).build()).map(PROJECT_MAPPER),
				pageable,
				() -> dsl.fetchCount(QueryBuilder.newBuilder(filter).build())
		);
	}

	@Override
	public Page<ProjectInfo> findProjectInfoByFilter(Filter filter, Pageable pageable, String mode) {
		final String FILTERED_PROJECT = "filtered_project";
		return PageableExecutionUtils.getPage(dsl.with(FILTERED_PROJECT)
				.as(QueryBuilder.newBuilder(filter).with(pageable).build())
				.select(DSL.countDistinct(PROJECT_USER.USER_ID).as("usersQuantity"),
						DSL.countDistinct(choose().when(LAUNCH.MODE.eq(JLaunchModeEnum.valueOf(mode))
								.and(LAUNCH.STATUS.ne(JStatusEnum.IN_PROGRESS)), LAUNCH.ID)).as("launchesQuantity"),
						DSL.max(LAUNCH.START_TIME).as("lastRun"),
						fieldName(FILTERED_PROJECT, PROJECT.ID.getName()),
						fieldName(FILTERED_PROJECT, PROJECT.CREATION_DATE.getName()),
						fieldName(FILTERED_PROJECT, PROJECT.NAME.getName()),
						fieldName(FILTERED_PROJECT, PROJECT.PROJECT_TYPE.getName())
				)
				.from(DSL.table(name(FILTERED_PROJECT))
						.leftJoin(PROJECT_USER)
						.on(fieldName(FILTERED_PROJECT, PROJECT.ID.getName()).cast(Long.class).eq(PROJECT_USER.PROJECT_ID))
						.leftJoin(LAUNCH)
						.on(fieldName(FILTERED_PROJECT, PROJECT.ID.getName()).cast(Long.class).eq(LAUNCH.PROJECT_ID)))
				.groupBy(
						fieldName(FILTERED_PROJECT, PROJECT.ID.getName()),
						fieldName(FILTERED_PROJECT, PROJECT.CREATION_DATE.getName()),
						fieldName(FILTERED_PROJECT, PROJECT.NAME.getName()),
						fieldName(FILTERED_PROJECT, PROJECT.PROJECT_TYPE.getName())
				)
				.fetch()
				.into(ProjectInfo.class), pageable, () -> dsl.fetchCount(QueryBuilder.newBuilder(filter).build()));
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
}
