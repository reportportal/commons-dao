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
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.epam.ta.reportportal.commons.querygen.QueryBuilder.STATISTICS_KEY;
import static com.epam.ta.reportportal.commons.querygen.QueryBuilder.filterConverter;
import static com.google.common.base.Preconditions.checkArgument;

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

	public Filter(Collection<Filter> filters) {
		checkArgument(null != filters && !filters.isEmpty(), "Empty filter list");
		checkArgument(1 == filters.stream().map(Filter::getTarget).distinct().count(), "Different targets");
		this.target = filters.iterator().next().getTarget();
		this.filterConditions = filters.stream().flatMap(f -> f.getFilterConditions().stream()).collect(Collectors.toSet());
	}

	public Filter(Filter... filters) {
		this(Arrays.asList(filters));
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

	public final FilterTarget getTarget() {
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
		final Function<FilterCondition, org.jooq.Condition> transformer = filterConverter(this.target);
		QueryBuilder query = QueryBuilder.newBuilder(this.target);
		this.filterConditions.forEach(it -> transformCondition(transformer, query, it));
		return query.build();
	}

	private void transformCondition(Function<FilterCondition, org.jooq.Condition> transformer, QueryBuilder query, FilterCondition it) {
		if (it.getSearchCriteria().startsWith(STATISTICS_KEY)) {
			query.addStatisticsCondition(it, transformer.apply(it), it.getOperator());
		} else {
			query.addCondition(transformer.apply(it), it.getOperator());
		}
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

		if (target != filter.target) {
			return false;
		}
		return filterConditions != null ? filterConditions.equals(filter.filterConditions) : filter.filterConditions == null;
	}

	@Override
	public int hashCode() {
		int result = target != null ? target.hashCode() : 0;
		result = 31 * result + (filterConditions != null ? filterConditions.hashCode() : 0);
		return result;
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