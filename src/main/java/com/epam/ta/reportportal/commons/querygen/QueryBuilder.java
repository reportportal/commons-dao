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

package com.epam.ta.reportportal.commons.querygen;

import com.epam.ta.reportportal.commons.Preconditions;
import com.epam.ta.reportportal.commons.validation.BusinessRule;
import com.epam.ta.reportportal.commons.validation.Suppliers;
import com.epam.ta.reportportal.exception.ReportPortalException;
import com.epam.ta.reportportal.ws.model.ErrorType;
import com.google.common.collect.ImmutableList;
import org.jooq.Condition;
import org.jooq.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.stream.StreamSupport;

import static java.util.Optional.ofNullable;
import static org.jooq.impl.DSL.field;

/**
 * PostgreSQL query builder using JOOQ. Constructs PostgreSQL {@link Query}
 * by provided filters.
 *
 * @author <a href="mailto:pavel_bortnik@epam.com">Pavel Bortnik</a>
 */
public class QueryBuilder {

	/**
	 * Key word for statistics criteria. Query builder works a little bit in another way
	 * with statistics criteria. It implements kind of pivot using PostgerSQL possibilities
	 */
	public final static String STATISTICS_KEY = "statistics";

	/**
	 * Conditions that should be applied with HAVING
	 */
	private static final List<com.epam.ta.reportportal.commons.querygen.Condition> HAVING_CONDITIONS = ImmutableList.<com.epam.ta.reportportal.commons.querygen.Condition>builder()
			.add(com.epam.ta.reportportal.commons.querygen.Condition.HAS)
			.add(com.epam.ta.reportportal.commons.querygen.Condition.OVERLAP)
			.build();

	/**
	 * Predicate that checks if filter condition should be applied with HAVING
	 */
	public final static BiPredicate<FilterCondition, FilterTarget> HAVING_CONDITION = (filterCondition, target) -> {
		CriteriaHolder criteria = target.getCriteriaByFilter(filterCondition.getSearchCriteria())
				.orElseThrow(() -> new ReportPortalException(ErrorType.INCORRECT_FILTER_PARAMETERS, filterCondition.getSearchCriteria()));
		return HAVING_CONDITIONS.contains(filterCondition.getCondition()) || filterCondition.getSearchCriteria().startsWith(STATISTICS_KEY)
				|| !criteria.getQueryCriteria().equals(criteria.getAggregateCriteria());
	};

	/**
	 * JOOQ SQL query representation
	 */
	private SelectQuery<? extends Record> query;

	private FilterTarget filterTarget;

	private QueryBuilder(FilterTarget target) {
		filterTarget = target;
		query = target.getQuery();
	}

	private QueryBuilder(Queryable query) {
		filterTarget = query.getTarget();
		this.query = query.toQuery();
	}

	public static QueryBuilder newBuilder(FilterTarget target) {
		return new QueryBuilder(target);
	}

	public static QueryBuilder newBuilder(Queryable queryable) {
		return new QueryBuilder(queryable);
	}

	/**
	 * Adds condition to the query
	 *
	 * @param condition Condition
	 * @return QueryBuilder
	 */
	public QueryBuilder addCondition(Condition condition) {
		if (null != condition) {
			query.addConditions(condition);
		}
		return this;
	}

	/**
	 * Adds statistics condition to the query
	 *
	 * @param condition Condition
	 */
	public QueryBuilder addHavingCondition(Condition condition) {
		if (null != condition) {
			query.addHaving(condition);
		}
		return this;
	}

	/**
	 * Adds {@link Pageable} conditions
	 *
	 * @param p Pageable
	 * @return QueryBuilder
	 */
	public QueryBuilder with(Pageable p) {
		if (p.isPaged()) {
			query.addLimit(p.getPageSize());
			query.addOffset(Long.valueOf(p.getOffset()).intValue());
		}
		return with(p.getSort());
	}

	/**
	 * Add limit
	 *
	 * @param limit Limit
	 * @return QueryBuilder
	 */
	public QueryBuilder with(int limit) {
		query.addLimit(limit);
		return this;
	}

	/**
	 * Convert properties to query criteria and add sorting {@link Sort}
	 *
	 * @param sort Sort condition
	 * @return QueryBuilder
	 */
	public QueryBuilder with(Sort sort) {
		ofNullable(sort).ifPresent(s -> StreamSupport.stream(s.spliterator(), false).forEach(order -> {

			CriteriaHolder criteria = filterTarget.getCriteriaByFilter(order.getProperty())
					.orElseThrow(() -> new ReportPortalException(ErrorType.INCORRECT_SORTING_PARAMETERS, order.getProperty()));

			query.addSelect(field(criteria.getAggregateCriteria()));
			query.addOrderBy(field(criteria.getAggregateCriteria()).sort(order.getDirection().isDescending() ?
					SortOrder.DESC :
					SortOrder.ASC));
		}));
		return this;
	}

	public QueryBuilder with(Field<?> field, SortOrder sort) {
		query.addSelect(field);
		query.addOrderBy(field.sort(sort));
		return this;
	}

	/**
	 * Builds query
	 *
	 * @return Query
	 */
	public SelectQuery<? extends Record> build() {
		return query;
	}

	/**
	 * Joins inner query to load all columns after filtering
	 *
	 * @return Query builder
	 */
	public QueryBuilder wrap() {
		query = filterTarget.wrapQuery(query);
		return this;
	}

	/**
	 * Joins inner query to load columns excluding provided fields after filtering
	 *
	 * @return Query builder
	 */
	public QueryBuilder wrapExcludingFields(String... excludingFields) {
		query = filterTarget.wrapQuery(query, excludingFields);
		return this;
	}

	public QueryBuilder withWrapperSort(Sort sort) {
		ofNullable(sort).ifPresent(s -> StreamSupport.stream(s.spliterator(), false).forEach(order -> {
			CriteriaHolder criteria = filterTarget.getCriteriaByFilter(order.getProperty())
					.orElseThrow(() -> new ReportPortalException(ErrorType.INCORRECT_SORTING_PARAMETERS, order.getProperty()));
			query.addSelect(field(criteria.getQueryCriteria()));
			query.addOrderBy(field(criteria.getQueryCriteria()).sort(order.getDirection().isDescending() ? SortOrder.DESC : SortOrder.ASC));
		}));
		return this;
	}

	static Function<FilterCondition, Condition> filterConverter(FilterTarget target) {
		return filterCondition -> {
			String searchCriteria = filterCondition.getSearchCriteria();
			Optional<CriteriaHolder> criteriaHolder = target.getCriteriaByFilter(searchCriteria);

			BusinessRule.expect(criteriaHolder, Preconditions.IS_PRESENT).verify(
					ErrorType.INCORRECT_FILTER_PARAMETERS,
					Suppliers.formattedSupplier("Filter parameter {} is not defined", searchCriteria)
			);

			Condition condition = filterCondition.getCondition().toCondition(filterCondition, criteriaHolder.get());

			/* Does FilterCondition contains negative=true? */
			if (filterCondition.isNegative()) {
				condition = condition.not();
			}

			return condition;
		};
	}
}