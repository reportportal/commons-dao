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

import org.jooq.Condition;
import org.jooq.Operator;
import org.jooq.Record;
import org.jooq.SelectQuery;
import org.jooq.impl.DSL;

import java.util.*;
import java.util.stream.Collectors;

import static org.postgresql.shaded.com.ongres.scram.common.util.Preconditions.checkArgument;

/**
 * Composite filter. Combines filters using {@link Operator#AND} and builds query.
 *
 * @author <a href="mailto:pavel_bortnik@epam.com">Pavel Bortnik</a>
 */
public class CompositeFilter implements Queryable {

	private Collection<Queryable> filters;
	private FilterTarget target;

	public CompositeFilter(Collection<Queryable> filters) {
		checkArgument(null != filters && !filters.isEmpty(), "Empty filter list");
		checkArgument(1 == filters.stream().map(Queryable::getTarget).distinct().count(), "Different targets");
		this.target = filters.iterator().next().getTarget();
		this.filters = filters;
	}

	public CompositeFilter(Queryable... filters) {
		this(Arrays.asList(filters));
	}

	@Override
	public SelectQuery<? extends Record> toQuery() {
		QueryBuilder query = QueryBuilder.newBuilder(this.target);
		Map<ConditionType, Condition> conditions = toCondition();
		return query.addCondition(conditions.get(ConditionType.WHERE)).addHavingCondition(conditions.get(ConditionType.HAVING)).build();
	}

	@Override
	public Map<ConditionType, Condition> toCondition() {
		Map<ConditionType, Condition> resultedConditions = new HashMap<>();
		for (Queryable filter : filters) {
			filter.toCondition().forEach((conditionType, condition) -> {
				Condition compositeCondition = resultedConditions.getOrDefault(conditionType, DSL.noCondition());
				resultedConditions.put(conditionType, DSL.condition(Operator.AND, compositeCondition, condition));
			});
		}
		return resultedConditions;
	}

	@Override
	public FilterTarget getTarget() {
		return target;
	}

	@Override
	public List<ConvertibleCondition> getFilterConditions() {
		return filters.stream().flatMap(it -> it.getFilterConditions().stream()).collect(Collectors.toList());
	}
}
