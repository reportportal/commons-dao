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

package com.epam.ta.reportportal.dao.util;

import com.epam.ta.reportportal.commons.querygen.ConvertibleCondition;
import com.epam.ta.reportportal.commons.querygen.Filter;
import com.epam.ta.reportportal.commons.querygen.FilterCondition;
import com.epam.ta.reportportal.commons.querygen.QueryBuilder;
import org.jooq.SortOrder;
import org.jooq.impl.DSL;
import org.springframework.data.domain.Sort;

import java.util.Collection;
import java.util.Set;

import static com.epam.ta.reportportal.dao.constant.WidgetContentRepositoryConstants.ID;
import static com.epam.ta.reportportal.dao.constant.WidgetContentRepositoryConstants.LAUNCHES;
import static com.epam.ta.reportportal.jooq.tables.JLaunch.LAUNCH;
import static java.util.stream.Collectors.toSet;
import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.name;

/**
 * @author <a href="mailto:ivan_budayeu@epam.com">Ivan Budayeu</a>
 */
public final class QueryUtils {

	private QueryUtils() {
		//static only
	}

	public static QueryBuilder createQueryBuilderWithLatestLaunchesOption(Filter filter, Sort sort, boolean isLatest) {

		Set<String> joinFields = collectJoinFields(filter, sort);
		QueryBuilder queryBuilder = QueryBuilder.newBuilder(filter, joinFields);

		if (isLatest) {
			queryBuilder.with(LAUNCH.NUMBER, SortOrder.DESC)
					.addCondition(LAUNCH.ID.in(DSL.with(LAUNCHES)
							.as(QueryBuilder.newBuilder(filter, joinFields).build())
							.selectDistinct(LAUNCH.ID)
							.on(LAUNCH.NAME)
							.from(LAUNCH)
							.join(LAUNCHES)
							.on(field(name(LAUNCHES, ID), Long.class).eq(LAUNCH.ID))
							.orderBy(LAUNCH.NAME, LAUNCH.NUMBER.desc())));
		}

		return queryBuilder;

	}

	public static Set<String> collectJoinFields(Filter filter, Sort sort) {
		Set<String> joinFields = filter.getFilterConditions()
				.stream()
				.map(ConvertibleCondition::getAllConditions)
				.flatMap(Collection::stream)
				.map(FilterCondition::getSearchCriteria)
				.collect(toSet());
		joinFields.addAll(sort.get().map(Sort.Order::getProperty).collect(toSet()));
		return joinFields;
	}

}
