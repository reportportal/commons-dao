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

import com.epam.ta.reportportal.commons.querygen.QueryBuilder;
import com.epam.ta.reportportal.commons.querygen.Queryable;
import com.epam.ta.reportportal.entity.project.Project;
import com.epam.ta.reportportal.entity.project.ProjectInfo;
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
		return PROJECT_FETCHER.apply(dsl.fetch(QueryBuilder.newBuilder(filter).wrap().build()));
	}

	@Override
	public Page<Project> findByFilter(Queryable filter, Pageable pageable) {
		return PageableExecutionUtils.getPage(PROJECT_FETCHER.apply(dsl.fetch(QueryBuilder.newBuilder(filter)
				.with(pageable)
				.wrap()
				.withWrapperSort(pageable.getSort())
				.build())), pageable, () -> dsl.fetchCount(QueryBuilder.newBuilder(filter).build()));
	}

	@Override
	public Page<ProjectInfo> findProjectInfoByFilter(Queryable filter, Pageable pageable) {
		return PageableExecutionUtils.getPage(
				dsl.fetch(QueryBuilder.newBuilder(filter).with(pageable).build()).into(ProjectInfo.class),
				pageable,
				() -> dsl.fetchCount(QueryBuilder.newBuilder(filter).build())
		);
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
	public Page<Project> findAllIdsAndProjectAttributes(Queryable filter, Pageable pageable) {

		return PageableExecutionUtils.getPage(
				PROJECT_FETCHER.apply(dsl.fetch(dsl.with(FILTERED_PROJECT)
						.as(QueryBuilder.newBuilder(filter).with(pageable).build())
						.select(PROJECT.ID, ATTRIBUTE.NAME, PROJECT_ATTRIBUTE.VALUE)
						.from(PROJECT)
						.join(PROJECT_ATTRIBUTE)
						.on(PROJECT.ID.eq(PROJECT_ATTRIBUTE.PROJECT_ID))
						.join(ATTRIBUTE)
						.on(PROJECT_ATTRIBUTE.ATTRIBUTE_ID.eq(ATTRIBUTE.ID))
						.join(DSL.table(name(FILTERED_PROJECT)))
						.on(fieldName(FILTERED_PROJECT, PROJECT.ID.getName()).cast(Long.class).eq(PROJECT.ID)))),
				pageable,
				() -> dsl.fetchCount(QueryBuilder.newBuilder(filter).build())
		);
	}
}
