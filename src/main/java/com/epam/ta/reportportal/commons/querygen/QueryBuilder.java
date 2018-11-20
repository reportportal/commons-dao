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
import com.epam.ta.reportportal.ws.model.ErrorType;
import org.jooq.Condition;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Optional;
import java.util.function.Function;
import java.util.stream.StreamSupport;

import static com.epam.ta.reportportal.jooq.Tables.*;
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
	 * JOOQ SQL query representation
	 */
	private SelectQuery<? extends Record> query;

	private QueryBuilder(FilterTarget target) {
		query = target.getQuery();
	}

	private QueryBuilder(SelectQuery<? extends Record> query) {
		this.query = query;
	}

	public static QueryBuilder newBuilder(FilterTarget target) {
		return new QueryBuilder(target);
	}

	public static QueryBuilder newBuilder(Queryable queryable) {
		return new QueryBuilder(queryable.toQuery());
	}

	/**
	 * Adds condition to the query
	 *
	 * @param condition Condition
	 * @return QueryBuilder
	 */
	public QueryBuilder addCondition(Condition condition, Operator operator) {
		query.addConditions(operator, condition);
		return this;
	}

	/**
	 * Adds statistics condition to the query
	 *
	 * @param condition Condition
	 */
	void addStatisticsCondition(FilterCondition filterCondition, Condition condition, Operator operator) {
		if (filterCondition.getSearchCriteria().startsWith(STATISTICS_KEY)) {
			query.addSelect(DSL.max(STATISTICS.S_COUNTER)
					.filterWhere(STATISTICS_FIELD.NAME.eq(filterCondition.getSearchCriteria()))
					.as(filterCondition.getSearchCriteria()));
			query.addConditions(operator, condition);
		}
	}

	public QueryBuilder with(boolean latest) {
		if (latest) {
			query.addConditions(LAUNCH.ID.in(DSL.selectDistinct(LAUNCH.ID)
					.on(LAUNCH.NAME)
					.from(LAUNCH)
					.orderBy(LAUNCH.NAME, LAUNCH.NUMBER.desc())));
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
		query.addLimit(p.getPageSize());
		query.addOffset(Long.valueOf(p.getOffset()).intValue());
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
	 * Add sorting {@link Sort}
	 *
	 * @param sort Sort condition
	 * @return QueryBuilder
	 */
	public QueryBuilder with(Sort sort) {
		ofNullable(sort).ifPresent(s -> StreamSupport.stream(s.spliterator(), false).forEach(order -> {
			if (!order.getProperty().startsWith(STATISTICS_KEY)) {
				query.addSelect(field(order.getProperty()));
			} else {
				query.addSelect(DSL.max(STATISTICS.S_COUNTER)
						.filterWhere(STATISTICS_FIELD.NAME.eq(order.getProperty()))
						.as(order.getProperty()));
			}
			query.addOrderBy(field(order.getProperty()).sort(order.getDirection().isDescending() ? SortOrder.DESC : SortOrder.ASC));
		}));
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
	 * @param filterTarget Filter target
	 * @return Query builder
	 */
	public QueryBuilder withWrapper(FilterTarget filterTarget) {
		query = filterTarget.wrapQuery(query);
		return this;
	}

	/**
	 * Joins inner query to load columns exclueing provided fields after filtering
	 *
	 * @param filterTarget Filter target
	 * @return Query builder
	 */
	public QueryBuilder withWrapper(FilterTarget filterTarget, String... excludingFields) {
		query = filterTarget.wrapQuery(query, excludingFields);
		return this;
	}

	static Function<FilterCondition, Condition> filterConverter(FilterTarget target) {
		return filterCondition -> {
			String searchCriteria = filterCondition.getSearchCriteria();
			Optional<CriteriaHolder> criteriaHolder = target.getCriteriaByFilter(searchCriteria);

			/*
				creates criteria holder for statistics search criteria cause there
				can be custom statistics so we can't know it till this moment
			*/
			if (searchCriteria.startsWith(STATISTICS_KEY)) {
				criteriaHolder = Optional.of(new CriteriaHolder(searchCriteria, searchCriteria, Long.class));
			}

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