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

import com.epam.ta.reportportal.commons.ReportPortalUser;
import com.epam.ta.reportportal.commons.querygen.QueryBuilder;
import com.epam.ta.reportportal.commons.querygen.Queryable;
import com.epam.ta.reportportal.entity.project.ProjectRole;
import com.epam.ta.reportportal.entity.user.User;
import com.epam.ta.reportportal.exception.ReportPortalException;
import com.epam.ta.reportportal.ws.model.ErrorType;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.epam.ta.reportportal.dao.util.RecordMappers.REPORT_PORTAL_USER_MAPPER;
import static com.epam.ta.reportportal.dao.util.RecordMappers.USER_MAPPER;
import static com.epam.ta.reportportal.dao.util.ResultFetchers.*;
import static com.epam.ta.reportportal.jooq.tables.JProject.PROJECT;
import static com.epam.ta.reportportal.jooq.tables.JProjectUser.PROJECT_USER;
import static com.epam.ta.reportportal.jooq.tables.JUsers.USERS;

/**
 * @author Pavel Bortnik
 */
@Repository
public class UserRepositoryCustomImpl implements UserRepositoryCustom {

	private final DSLContext dsl;

	@Autowired
	public UserRepositoryCustomImpl(DSLContext dsl) {
		this.dsl = dsl;
	}

	@Override
	public List<User> findByFilter(Queryable filter) {
		return USER_FETCHER.apply(dsl.fetch(QueryBuilder.newBuilder(filter).wrap().build()));
	}

	@Override
	public Page<User> findByFilter(Queryable filter, Pageable pageable) {
		return PageableExecutionUtils.getPage(USER_FETCHER.apply(dsl.fetch(QueryBuilder.newBuilder(filter)
				.with(pageable)
				.wrap()
				.withWrapperSort(pageable.getSort())
				.build())), pageable, () -> dsl.fetchCount(QueryBuilder.newBuilder(filter).build()));
	}

	@Override
	public Page<User> findByFilterExcludingProjects(Queryable filter, Pageable pageable) {
		return PageableExecutionUtils.getPage(USER_WITHOUT_PROJECT_FETCHER.apply(dsl.fetch(QueryBuilder.newBuilder(filter)
				.with(pageable)
				.wrap()
				.withWrapperSort(pageable.getSort())
				.build())), pageable, () -> dsl.fetchCount(QueryBuilder.newBuilder(filter).build()));
	}

	@Override
	public Optional<User> findRawById(Long id) {
		return dsl.select().from(USERS).where(USERS.ID.eq(id)).fetchOptional(USER_MAPPER);
	}

	@Override
	public Page<User> findByFilterExcluding(Queryable filter, Pageable pageable, String... exclude) {
		return PageableExecutionUtils.getPage(USER_FETCHER.apply(dsl.fetch(QueryBuilder.newBuilder(filter)
				.with(pageable)
				.wrapExcludingFields(exclude)
				.withWrapperSort(pageable.getSort())
				.build())), pageable, () -> dsl.fetchCount(QueryBuilder.newBuilder(filter).build()));
	}

	@Override
	public Map<String, ProjectRole> findUsernamesWithProjectRolesByProjectId(Long projectId) {
		return dsl.select(USERS.LOGIN, PROJECT_USER.PROJECT_ROLE)
				.from(USERS)
				.join(PROJECT_USER)
				.on(USERS.ID.eq(PROJECT_USER.USER_ID))
				.where(PROJECT_USER.PROJECT_ID.eq(projectId))
				.fetch()
				.stream()
				.collect(Collectors.toMap(r -> r.get(USERS.LOGIN), r -> {
					String projectRoleName = r.get(PROJECT_USER.PROJECT_ROLE).getLiteral();
					return ProjectRole.forName(projectRoleName)
							.orElseThrow(() -> new ReportPortalException(ErrorType.ROLE_NOT_FOUND, projectRoleName));
				}));
	}

	@Override
	public Optional<ReportPortalUser> findUserDetails(String login) {
		return Optional.ofNullable(REPORTPORTAL_USER_FETCHER.apply(dsl.select(
				USERS.ID,
				USERS.LOGIN,
				USERS.PASSWORD,
				USERS.ROLE,
				USERS.EMAIL,
				PROJECT_USER.PROJECT_ID,
				PROJECT_USER.PROJECT_ROLE,
				PROJECT.NAME
		)
				.from(USERS)
				.leftJoin(PROJECT_USER)
				.on(USERS.ID.eq(PROJECT_USER.USER_ID))
				.leftJoin(PROJECT)
				.on(PROJECT_USER.PROJECT_ID.eq(PROJECT.ID))
				.where(USERS.LOGIN.eq(login))
				.fetch()));
	}

	@Override
	public Optional<ReportPortalUser> findReportPortalUser(String login) {
		return dsl.select(USERS.ID, USERS.LOGIN, USERS.PASSWORD, USERS.ROLE, USERS.EMAIL)
				.from(USERS)
				.where(USERS.LOGIN.eq(login))
				.fetchOptional(REPORT_PORTAL_USER_MAPPER);
	}

}
