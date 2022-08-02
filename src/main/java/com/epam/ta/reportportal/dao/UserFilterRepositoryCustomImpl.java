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

import com.epam.ta.reportportal.commons.querygen.ConvertibleCondition;
import com.epam.ta.reportportal.commons.querygen.FilterCondition;
import com.epam.ta.reportportal.commons.querygen.QueryBuilder;
import com.epam.ta.reportportal.commons.querygen.Queryable;
import com.epam.ta.reportportal.entity.filter.UserFilter;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.epam.ta.reportportal.dao.util.ResultFetchers.USER_FILTER_FETCHER;

/**
 * @author <a href="mailto:pavel_bortnik@epam.com">Pavel Bortnik</a>
 */
@Repository
public class UserFilterRepositoryCustomImpl implements UserFilterRepositoryCustom {

	private DSLContext dsl;

	@Autowired
	public void setDsl(DSLContext dsl) {
		this.dsl = dsl;
	}

	@Override
	public List<UserFilter> findByFilter(Queryable filter) {
		return USER_FILTER_FETCHER.apply(dsl.fetch(QueryBuilder.newBuilder(
				filter,
				filter.getFilterConditions()
						.stream()
						.map(ConvertibleCondition::getAllConditions)
						.flatMap(Collection::stream)
						.map(FilterCondition::getSearchCriteria)
						.collect(Collectors.toSet())
		).wrap().build()));
	}

	@Override
	public Page<UserFilter> findByFilter(Queryable filter, Pageable pageable) {
		Set<String> fields = filter.getFilterConditions()
				.stream()
				.map(ConvertibleCondition::getAllConditions)
				.flatMap(Collection::stream)
				.map(FilterCondition::getSearchCriteria)
				.collect(Collectors.toSet());
		fields.addAll(pageable.getSort().get().map(Sort.Order::getProperty).collect(Collectors.toSet()));

		return PageableExecutionUtils.getPage(USER_FILTER_FETCHER.apply(dsl.fetch(QueryBuilder.newBuilder(filter, fields)
				.with(pageable)
				.wrap()
				.withWrapperSort(pageable.getSort())
				.build())), pageable, () -> dsl.fetchCount(QueryBuilder.newBuilder(filter, fields).build()));
	}

}
