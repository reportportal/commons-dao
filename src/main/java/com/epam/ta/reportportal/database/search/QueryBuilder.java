/*
 * Copyright 2016 EPAM Systems
 * 
 * 
 * This file is part of EPAM Report Portal.
 * https://github.com/reportportal/commons-dao
 * 
 * Report Portal is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Report Portal is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Report Portal.  If not, see <http://www.gnu.org/licenses/>.
 */ 
/*
 * This file is part of Report Portal.
 *
 * Report Portal is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Report Portal is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Report Portal.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.epam.ta.reportportal.database.search;

import com.epam.ta.reportportal.commons.Preconditions;
import com.epam.ta.reportportal.commons.validation.BusinessRule;
import com.epam.ta.reportportal.commons.validation.Suppliers;
import com.epam.ta.reportportal.ws.model.ErrorType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Optional;
import java.util.function.Function;

/**
 * MongoDB query builder. Constructs MongoDB
 * {@link org.springframework.data.mongodb.core.query.Query} by provided filters <br>
 * <p>
 * TODO Some interface for QueryBuilder should be created to avoid problems with possible changing
 * of DB engine
 *
 * @author Andrei Varabyeu
 * @author Andrei_Ramanchuk
 */
public class QueryBuilder {

	/**
	 * MongoDB postfix to be applied to reference fields
	 */
	private static final String REFERENCE_POSTFIX = ".$id";

	/**
	 * MongoDB query representation
	 */
	private Query query;

	private QueryBuilder() {
		query = new Query();
	}

	public static QueryBuilder newBuilder() {
		return new QueryBuilder();
	}

	/**
	 * Adds {@link com.epam.ta.reportportal.database.search.Filter} using 'AND' condition.
	 *
	 * @param filter
	 * @return QueryBuilder
	 */
	public QueryBuilder with(Queryable filter) {
		query.addCriteria(filter.toCriteria());
		return this;
	}

	/**
	 * Adds {@link org.springframework.data.domain.Pageable} conditions
	 *
	 * @param p Pageable
	 * @return QueryBuilder
	 */
	public QueryBuilder with(Pageable p) {
		query.with(p);
		return this;
	}

	/**
	 * Add limit
	 *
	 * @param limit Limit
	 * @return QueryBuilder
	 */
	public QueryBuilder with(int limit) {
		query.limit(limit);
		return this;
	}

	/**
	 * Add sorting {@link Sort}
	 *
	 * @param sort
	 * @return QueryBuilder
	 */
	public QueryBuilder with(Sort sort) {
		query.with(sort);
		return this;
	}

	/**
	 * Builds query
	 *
	 * @return Query
	 */
	public Query build() {
		return query;
	}

	public static Function<FilterCondition, Criteria> filterConverter(CriteriaMap<?> map) {
		return filterCondition -> {
			Optional<CriteriaHolder> criteriaHolder = map.getCriteriaHolderUnchecked(filterCondition.getSearchCriteria());
			BusinessRule.expect(criteriaHolder, Preconditions.IS_PRESENT).verify(ErrorType.INCORRECT_FILTER_PARAMETERS,
					Suppliers.formattedSupplier("Filter parameter {} is not defined", filterCondition.getSearchCriteria()));

			Criteria searchCriteria;
			if (criteriaHolder.get().isReference()) {
				searchCriteria = Criteria.where(criteriaHolder.get().getQueryCriteria().concat(REFERENCE_POSTFIX));
			} else {
				searchCriteria = Criteria.where(criteriaHolder.get().getQueryCriteria());
			}

			/* Does FilterCondition contains negative=true? */
			if (filterCondition.isNegative()) {
				searchCriteria = searchCriteria.not();
			}

			filterCondition.getCondition().addCondition(searchCriteria, filterCondition, criteriaHolder.get());

			return searchCriteria;
		};
	}
}
