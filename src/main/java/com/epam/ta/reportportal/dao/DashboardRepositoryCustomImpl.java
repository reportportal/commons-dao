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

import com.epam.ta.reportportal.commons.querygen.ProjectFilter;
import com.epam.ta.reportportal.commons.querygen.QueryBuilder;
import com.epam.ta.reportportal.entity.dashboard.Dashboard;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.support.PageableExecutionUtils;

import static com.epam.ta.reportportal.dao.util.ResultFetchers.DASHBOARD_FETCHER;
import static com.epam.ta.reportportal.dao.util.ShareableUtils.*;

/**
 * @author <a href="mailto:pavel_bortnik@epam.com">Pavel Bortnik</a>
 */
public class DashboardRepositoryCustomImpl implements DashboardRepositoryCustom {

	private final DSLContext dsl;

	@Autowired
	public DashboardRepositoryCustomImpl(DSLContext dsl) {
		this.dsl = dsl;
	}

	@Override
	public Page<Dashboard> getPermitted(ProjectFilter filter, Pageable pageable, String userName) {
		return PageableExecutionUtils.getPage(
				DASHBOARD_FETCHER.apply(dsl.fetch(QueryBuilder.newBuilder(filter)
						.addCondition(permittedCondition(userName))
						.withWrapper(filter.getTarget())
						.build())),
				pageable,
				() -> dsl.fetchCount(QueryBuilder.newBuilder(filter).addCondition(permittedCondition(userName)).build())
		);
	}

	@Override
	public Page<Dashboard> getOwn(ProjectFilter filter, Pageable pageable, String userName) {
		return PageableExecutionUtils.getPage(
				DASHBOARD_FETCHER.apply(dsl.fetch(QueryBuilder.newBuilder(filter)
						.addCondition(ownCondition(userName))
						.withWrapper(filter.getTarget())
						.build())),
				pageable,
				() -> dsl.fetchCount(QueryBuilder.newBuilder(filter).addCondition(ownCondition(userName)).build())
		);
	}

	@Override
	public Page<Dashboard> getShared(ProjectFilter filter, Pageable pageable, String userName) {
		return PageableExecutionUtils.getPage(
				DASHBOARD_FETCHER.apply(dsl.fetch(QueryBuilder.newBuilder(filter)
						.addCondition(sharedCondition(userName))
						.withWrapper(filter.getTarget())
						.build())),
				pageable,
				() -> dsl.fetchCount(QueryBuilder.newBuilder(filter).addCondition(sharedCondition(userName)).build())
		);
	}
}
