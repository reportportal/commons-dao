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

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;
import org.jooq.Record;
import org.jooq.SelectQuery;
import org.jooq.impl.DSL;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.*;
import java.util.function.Function;

import static com.epam.ta.reportportal.commons.querygen.QueryBuilder.HAVING_CONDITION;
import static com.epam.ta.reportportal.commons.querygen.QueryBuilder.filterConverter;

/**
 * Filter for building queries to database. Contains CriteriaHolder which is mapping between request
 * search criterias and DB search criterias and value to be filtered
 *
 * @author Andrei Varabyeu
 */
public class Filter implements Serializable, Queryable {

	private Long id;

	private FilterTarget target;

	private Set<FilterCondition> filterConditions;

	/**
	 * This constructor uses during serialization to database.
	 */
	@SuppressWarnings("unused")
	private Filter() {

	}

	public Filter(Class<?> target, Condition condition, boolean negative, String value, String searchCriteria) {
		this(FilterTarget.findByClass(target), Sets.newHashSet(new FilterCondition(condition, negative, value, searchCriteria)));
	}

	public Filter(Class<?> target, Set<FilterCondition> filterConditions) {
		this(FilterTarget.findByClass(target), filterConditions);
	}

	public Filter(Long filterId, Class<?> target, Condition condition, boolean negative, String value, String searchCriteria) {
		this(filterId, FilterTarget.findByClass(target), Sets.newHashSet(new FilterCondition(condition, negative, value, searchCriteria)));
	}

	public Filter(Long filterId, Class<?> target, Set<FilterCondition> filterConditions) {
		this(filterId, FilterTarget.findByClass(target), filterConditions);
	}

	protected Filter(Long id, FilterTarget target, Set<FilterCondition> filterConditions) {
		Assert.notNull(id, "Filter id shouldn't be null");
		Assert.notNull(target, "Filter target shouldn't be null");
		Assert.notNull(filterConditions, "Conditions value shouldn't be null");
		this.id = id;
		this.target = target;
		this.filterConditions = filterConditions;
	}

	protected Filter(FilterTarget target, Set<FilterCondition> filterConditions) {
		Assert.notNull(target, "Filter target shouldn't be null");
		Assert.notNull(filterConditions, "Conditions value shouldn't be null");
		this.target = target;
		this.filterConditions = filterConditions;
	}

	public Long getId() {
		return id;
	}

	public FilterTarget getTarget() {
		return target;
	}

	public Set<FilterCondition> getFilterConditions() {
		return filterConditions;
	}

	public Filter withCondition(FilterCondition filterCondition) {
		this.filterConditions.add(filterCondition);
		return this;
	}

	public Filter withConditions(Collection<FilterCondition> conditions) {
		this.filterConditions.addAll(conditions);
		return this;
	}

	@Override
	public SelectQuery<? extends Record> toQuery() {
		QueryBuilder query = QueryBuilder.newBuilder(this.target);
		Map<ConditionType, org.jooq.Condition> conditions = toCondition();
		return query.addCondition(conditions.get(ConditionType.WHERE)).addHavingCondition(conditions.get(ConditionType.HAVING)).build();
	}

	@Override
	public Map<ConditionType, org.jooq.Condition> toCondition() {
		Map<ConditionType, org.jooq.Condition> resultedConditions = new HashMap<>();
		for (FilterCondition filterCondition : filterConditions) {
			if (HAVING_CONDITION.test(filterCondition, this.target)) {
				addTransformedCondition(resultedConditions, filterCondition, ConditionType.HAVING);
			} else {
				addTransformedCondition(resultedConditions, filterCondition, ConditionType.WHERE);
			}
		}
		return resultedConditions;
	}

	/**
	 * Transforms {@link FilterCondition} into {@link org.jooq.Condition} and adds it to existed {@link Condition}
	 * according the {@link ConditionType} with {@link FilterCondition#getOperator()}
	 * {@link org.jooq.Operator}
	 *
	 * @param resultedConditions Resulted map of conditions divided into {@link ConditionType}
	 * @param filterCondition    Filter condition that should be converted
	 * @param conditionType      {@link ConditionType}
	 * @return Updated map of conditions
	 */
	private Map<ConditionType, org.jooq.Condition> addTransformedCondition(Map<ConditionType, org.jooq.Condition> resultedConditions,
			FilterCondition filterCondition, ConditionType conditionType) {
		final Function<FilterCondition, org.jooq.Condition> transformer = filterConverter(this.target);
		org.jooq.Condition composite = resultedConditions.getOrDefault(conditionType, DSL.noCondition());
		composite = DSL.condition(filterCondition.getOperator(), composite, transformer.apply(filterCondition));
		resultedConditions.put(conditionType, composite);
		return resultedConditions;
	}

	public static FilterBuilder builder() {
		return new FilterBuilder();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		Filter filter = (Filter) o;
		return Objects.equals(id, filter.id) && target == filter.target && Objects.equals(filterConditions, filter.filterConditions);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, target, filterConditions);
	}

	@Override
	public String toString() {
		return "Filter{" + "id=" + id + ", target=" + target + ", filterConditions=" + filterConditions + '}';
	}

	/**
	 * Builder for {@link Filter}
	 */
	public static class FilterBuilder {

		private Class<?> target;

		private Set<FilterCondition> conditions = Sets.newHashSet();

		private FilterBuilder() {

		}

		public FilterBuilder withTarget(Class<?> target) {
			this.target = target;
			return this;
		}

		public FilterBuilder withCondition(FilterCondition condition) {
			this.conditions.add(condition);
			return this;
		}

		public Filter build() {
			Set<FilterCondition> filterConditions = Sets.newHashSet();
			filterConditions.addAll(this.conditions);
			Preconditions.checkArgument(null != target, "FilterTarget should not be null");
			Preconditions.checkArgument(!filterConditions.isEmpty(), "Filter should contain at least one condition");
			return new Filter(FilterTarget.findByClass(target), filterConditions);
		}
	}

}