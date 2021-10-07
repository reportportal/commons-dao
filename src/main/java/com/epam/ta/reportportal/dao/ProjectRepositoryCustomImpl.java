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

import com.epam.ta.reportportal.commons.querygen.FilterTarget;
import com.epam.ta.reportportal.commons.querygen.QueryBuilder;
import com.epam.ta.reportportal.commons.querygen.Queryable;
import com.epam.ta.reportportal.entity.enums.ProjectType;
import com.epam.ta.reportportal.entity.project.Project;
import com.epam.ta.reportportal.entity.project.ProjectInfo;
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
import java.util.Optional;

import static com.epam.ta.reportportal.dao.util.JooqFieldNameTransformer.fieldName;
import static com.epam.ta.reportportal.dao.util.QueryUtils.collectJoinFields;
import static com.epam.ta.reportportal.dao.util.RecordMappers.PROJECT_MAPPER;
import static com.epam.ta.reportportal.dao.util.ResultFetchers.PROJECT_FETCHER;
import static com.epam.ta.reportportal.jooq.Tables.*;
import static org.jooq.impl.DSL.name;

@Repository
public class ProjectRepositoryCustomImpl implements ProjectRepositoryCustom {

	private static final String FILTERED_PROJECT = "filtered_project";

	@Autowired
	private DSLContext dsl;

	@Override
	public List<Project> findByFilter(Queryable filter) {
		return PROJECT_FETCHER.apply(dsl.fetch(QueryBuilder.newBuilder(filter, collectJoinFields(filter)).wrap().build()));
	}

	@Override
	public Page<Project> findByFilter(Queryable filter, Pageable pageable) {
		return PageableExecutionUtils.getPage(PROJECT_FETCHER.apply(dsl.fetch(QueryBuilder.newBuilder(filter,
				collectJoinFields(filter, pageable.getSort())
				).with(pageable).wrap().withWrapperSort(pageable.getSort()).build())),
				pageable,
				() -> dsl.fetchCount(QueryBuilder.newBuilder(filter).build())
		);
	}

	@Override
	public Optional<Project> findRawByName(String name) {
		return dsl.select(PROJECT.fields()).from(PROJECT).where(PROJECT.NAME.eq(name)).fetchOptional(PROJECT_MAPPER);
	}

	@Override
	public List<ProjectInfo> findProjectInfoByFilter(Queryable filter) {
		return dsl.fetch(QueryBuilder.newBuilder(filter).build()).into(ProjectInfo.class);
	}

	@Override
	public Page<ProjectInfo> findProjectInfoByFilter(Queryable filter, Pageable pageable) {
		return PageableExecutionUtils.getPage(dsl.fetch(QueryBuilder.newBuilder(filter).with(pageable).build()).into(ProjectInfo.class),
				pageable,
				() -> dsl.fetchCount(QueryBuilder.newBuilder(filter).build())
		);
	}

	@Override
	public List<String> findAllProjectNames() {
		return dsl.select(PROJECT.NAME).from(PROJECT).fetchInto(String.class);
	}

	@Override
	public List<String> findAllProjectNamesByTerm(String term) {
		return dsl.select(PROJECT.NAME)
				.from(PROJECT)
				.where(PROJECT.NAME.likeIgnoreCase("%" + DSL.escape(term, '\\') + "%"))
				.fetchInto(String.class);
	}

	@Override
	public List<Project> findAllByUserLogin(String login) {
		return PROJECT_FETCHER.apply(dsl.select(PROJECT.fields())
				.from(PROJECT)
				.join(PROJECT_USER)
				.on(PROJECT.ID.eq(PROJECT_USER.PROJECT_ID))
				.join(USERS)
				.on(PROJECT_USER.USER_ID.eq(USERS.ID))
				.where(USERS.LOGIN.eq(login))
				.fetch());
	}

	@Override
	public Page<Project> findAllIdsAndProjectAttributes(Pageable pageable) {

		return PageableExecutionUtils.getPage(PROJECT_FETCHER.apply(dsl.fetch(dsl.with(FILTERED_PROJECT)
						.as(QueryBuilder.newBuilder(FilterTarget.PROJECT_TARGET, collectJoinFields(pageable.getSort())).with(pageable).build())
						.select(PROJECT.ID, ATTRIBUTE.NAME, PROJECT_ATTRIBUTE.VALUE)
						.from(PROJECT)
						.join(PROJECT_ATTRIBUTE)
						.on(PROJECT.ID.eq(PROJECT_ATTRIBUTE.PROJECT_ID))
						.join(ATTRIBUTE)
						.on(PROJECT_ATTRIBUTE.ATTRIBUTE_ID.eq(ATTRIBUTE.ID))
						.join(DSL.table(name(FILTERED_PROJECT)))
						.on(fieldName(FILTERED_PROJECT, PROJECT.ID.getName()).cast(Long.class).eq(PROJECT.ID)))),
				pageable,
				() -> dsl.fetchCount(QueryBuilder.newBuilder(FilterTarget.PROJECT_TARGET).build())
		);
	}

	@Override
	public int deleteByTypeAndLastLaunchRunBefore(ProjectType projectType, LocalDateTime bound, int limit) {
		return dsl.deleteFrom(PROJECT)
				.where(PROJECT.ID.in(dsl.select(PROJECT.ID)
						.from(PROJECT)
						.join(LAUNCH)
						.onKey()
						.where(PROJECT.PROJECT_TYPE.eq(projectType.name()))
						.groupBy(PROJECT.ID)
						.having(DSL.max(LAUNCH.START_TIME).le(Timestamp.valueOf(bound)))
						.limit(limit)))
				.execute();
	}

}
