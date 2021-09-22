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

package com.epam.ta.reportportal.commons.querygen;

import com.epam.ta.reportportal.commons.querygen.query.QuerySupplier;
import org.jooq.Condition;

import java.util.List;
import java.util.Map;

/**
 * Can be used to generate SQL queries with help of JOOQ
 *
 * @author <a href="mailto:andrei_varabyeu@epam.com">Andrei Varabyeu</a>
 * @author <a href="mailto:pavel_bortnik@epam.com">Pavel Bortnik</a>
 */
public interface Queryable {

	/**
	 * Builds a query with lazy joins
	 *
	 * @return {@link QuerySupplier}
	 */
	QuerySupplier toQuery();

	/**
	 * Build a map where key is {@link ConditionType} and value is a composite {@link Condition}
	 * that should be applied either in {@link ConditionType#HAVING} or {@link ConditionType#WHERE} clause
	 *
	 * @return Resulted map
	 */
	Map<ConditionType, Condition> toCondition();

	/**
	 * @return {@link FilterTarget}
	 */
	FilterTarget getTarget();

	/**
	 * @return Set of {@link FilterCondition}
	 */
	List<ConvertibleCondition> getFilterConditions();

	boolean replaceSearchCriteria(FilterCondition oldCondition, FilterCondition newCondition);
}