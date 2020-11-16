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

import com.epam.ta.reportportal.commons.querygen.Filter;
import com.epam.ta.reportportal.commons.querygen.ProjectFilter;
import com.epam.ta.reportportal.commons.querygen.QueryBuilder;
import com.epam.ta.reportportal.entity.ShareableEntity;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.support.PageableExecutionUtils;

import java.util.List;
import java.util.function.Function;

import static com.epam.ta.reportportal.dao.util.ShareableUtils.*;
import static com.epam.ta.reportportal.jooq.Tables.SHAREABLE_ENTITY;

/**
 * @author <a href="mailto:ivan_budayeu@epam.com">Ivan Budayeu</a>
 */
public abstract class AbstractShareableRepositoryImpl<T extends ShareableEntity> implements ShareableRepository<T> {

	protected DSLContext dsl;

	@Autowired
	public void setDsl(DSLContext dsl) {
		this.dsl = dsl;
	}

	protected Page<T> getPermitted(Function<Result<? extends Record>, List<T>> fetcher, Filter filter, Pageable pageable, String userName) {

		return PageableExecutionUtils.getPage(
				fetcher.apply(dsl.fetch(QueryBuilder.newBuilder(filter)
						.addCondition(permittedCondition(userName))
						.with(pageable)
						.wrap()
						.withWrapperSort(pageable.getSort())
						.build())),
				pageable,
				() -> dsl.fetchCount(QueryBuilder.newBuilder(filter).addCondition(permittedCondition(userName)).build())
		);

	}

	protected Page<T> getOwn(Function<Result<? extends Record>, List<T>> fetcher, ProjectFilter filter, Pageable pageable,
			String userName) {
		return PageableExecutionUtils.getPage(
				fetcher.apply(dsl.fetch(QueryBuilder.newBuilder(filter)
						.addCondition(ownCondition(userName))
						.with(pageable)
						.wrap()
						.withWrapperSort(pageable.getSort())
						.build())),
				pageable,
				() -> dsl.fetchCount(QueryBuilder.newBuilder(filter).addCondition(ownCondition(userName)).build())
		);
	}

	protected Page<T> getShared(Function<Result<? extends Record>, List<T>> fetcher, ProjectFilter filter, Pageable pageable,
			String userName) {
		return PageableExecutionUtils.getPage(
				fetcher.apply(dsl.fetch(QueryBuilder.newBuilder(filter)
						.addCondition(sharedCondition(userName))
						.with(pageable)
						.wrap()
						.withWrapperSort(pageable.getSort())
						.build())),
				pageable,
				() -> dsl.fetchCount(QueryBuilder.newBuilder(filter).addCondition(sharedCondition(userName)).build())
		);
	}

	@Override
	public void updateSharingFlag(List<Long> ids, boolean isShared) {
		dsl.update(SHAREABLE_ENTITY).set(SHAREABLE_ENTITY.SHARED, isShared).where(SHAREABLE_ENTITY.ID.in(ids)).execute();
	}
}
