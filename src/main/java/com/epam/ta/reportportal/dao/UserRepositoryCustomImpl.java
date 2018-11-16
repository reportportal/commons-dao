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

import com.epam.ta.reportportal.BinaryData;
import com.epam.ta.reportportal.commons.querygen.Filter;
import com.epam.ta.reportportal.commons.querygen.QueryBuilder;
import com.epam.ta.reportportal.entity.user.User;
import com.epam.ta.reportportal.filesystem.DataStore;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.SelectForUpdateStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.epam.ta.reportportal.dao.util.ResultFetchers.USER_FETCHER;
import static com.epam.ta.reportportal.jooq.Tables.PROJECT;
import static com.epam.ta.reportportal.jooq.Tables.PROJECT_USER;
import static com.epam.ta.reportportal.jooq.tables.JUsers.USERS;
import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.name;

/**
 * @author Pavel Bortnik
 */
@Repository
public class UserRepositoryCustomImpl implements UserRepositoryCustom {

	private final DataStore dataStore;

	private final DSLContext dsl;

	@Autowired
	public UserRepositoryCustomImpl(DataStore dataStore, DSLContext dsl) {
		this.dataStore = dataStore;
		this.dsl = dsl;
	}

	@Override
	public String uploadUserPhoto(String username, BinaryData binaryData) {
		return dataStore.save(username, binaryData.getInputStream());
	}

	@Override
	public String replaceUserPhoto(String username, BinaryData binaryData) {
		return dataStore.save(username, binaryData.getInputStream());
	}

	@Override
	public String replaceUserPhoto(User user, BinaryData binaryData) {
		return dataStore.save(user.getLogin(), binaryData.getInputStream());
	}

	@Override
	public void deleteUserPhoto(String path) {
		dataStore.delete(path);
	}

	@Override
	public Page<User> searchForUser(String term, Pageable pageable) {

		SelectForUpdateStep<Record> select = dsl.select()
				.from(USERS)
				.where(USERS.LOGIN.like("%" + term + "%").or(USERS.FULL_NAME.like("%" + term + "%")).or(USERS.EMAIL.like("%" + term + "%")))
				.limit(pageable.getPageSize())
				.offset(Long.valueOf(pageable.getOffset()).intValue());

		return PageableExecutionUtils.getPage(USER_FETCHER.apply(dsl.fetch(dsl.with("temp_users")
				.as(select)
				.select()
				.from(USERS)
				.join("temp_users")
				.on(USERS.ID.eq(field(name("temp_users", "id"), Long.class)))
				.leftJoin(PROJECT_USER)
				.on(USERS.ID.eq(PROJECT_USER.USER_ID))
				.leftJoin(PROJECT)
				.on(PROJECT_USER.PROJECT_ID.eq(PROJECT.ID)))), pageable, () -> dsl.fetchCount(select));
	}

	@Override
	public Page<User> findByFilterExcluding(Filter filter, Pageable pageable, String... exclude) {
		return PageableExecutionUtils.getPage(USER_FETCHER.apply(QueryBuilder.newBuilder(filter)
				.with(pageable)
				.withWrapper(filter.getTarget(), exclude)
				.with(pageable.getSort())
				.build()
				.fetch()), pageable, () -> dsl.fetchCount(QueryBuilder.newBuilder(filter).build()));
	}

	@Override
	public List<User> findByFilter(Filter filter) {
		return USER_FETCHER.apply(dsl.fetch(QueryBuilder.newBuilder(filter).withWrapper(filter.getTarget()).build()));
	}

	@Override
	public Page<User> findByFilter(Filter filter, Pageable pageable) {
		return PageableExecutionUtils.getPage(USER_FETCHER.apply(dsl.fetch(QueryBuilder.newBuilder(filter)
				.with(pageable)
				.withWrapper(filter.getTarget())
				.with(pageable.getSort())
				.build())), pageable, () -> dsl.fetchCount(QueryBuilder.newBuilder(filter).build()));
	}

}
