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
import com.epam.ta.reportportal.entity.widget.Widget;
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

import static com.epam.ta.reportportal.dao.util.ResultFetchers.WIDGET_FETCHER;
import static com.epam.ta.reportportal.jooq.tables.JOwnedEntity.OWNED_ENTITY;
import static com.epam.ta.reportportal.jooq.tables.JWidget.WIDGET;
import static com.epam.ta.reportportal.jooq.tables.JWidgetFilter.WIDGET_FILTER;

/**
 * @author <a href="mailto:pavel_bortnik@epam.com">Pavel Bortnik</a>
 */
@Repository
public class WidgetRepositoryCustomImpl implements WidgetRepositoryCustom {

	private final DSLContext dsl;

	@Autowired
	public WidgetRepositoryCustomImpl(DSLContext dsl) {
		this.dsl = dsl;
	}

	@Override
	public int deleteRelationByFilterIdAndNotOwner(Long filterId, String owner) {
		return dsl.deleteFrom(WIDGET_FILTER)
				.where(WIDGET_FILTER.WIDGET_ID.in(dsl.select(WIDGET.ID)
						.from(WIDGET)
						.join(WIDGET_FILTER)
						.on(WIDGET.ID.eq(WIDGET_FILTER.WIDGET_ID))
						.join(OWNED_ENTITY)
						.on(WIDGET.ID.eq(OWNED_ENTITY.ID))
						.where(WIDGET_FILTER.FILTER_ID.eq(filterId))
						.and(OWNED_ENTITY.OWNER.notEqual(owner))))
				.execute();
	}

	@Override
	public List<Widget> findByFilter(Queryable filter) {
		return WIDGET_FETCHER.apply(dsl.fetch(QueryBuilder.newBuilder(
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
	public Page<Widget> findByFilter(Queryable filter, Pageable pageable) {
		Set<String> fields = filter.getFilterConditions()
				.stream()
				.map(ConvertibleCondition::getAllConditions)
				.flatMap(Collection::stream)
				.map(FilterCondition::getSearchCriteria)
				.collect(Collectors.toSet());
		fields.addAll(pageable.getSort().get().map(Sort.Order::getProperty).collect(Collectors.toSet()));

		return PageableExecutionUtils.getPage(WIDGET_FETCHER.apply(dsl.fetch(QueryBuilder.newBuilder(filter, fields)
				.with(pageable)
				.wrap()
				.withWrapperSort(pageable.getSort())
				.build())), pageable, () -> dsl.fetchCount(QueryBuilder.newBuilder(filter, fields).build()));
	}
}
