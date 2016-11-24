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

package com.epam.ta.reportportal.database.search;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

/**
 * Filter for building queries to database. Contains CriteriaHolder which is mapping between request
 * search criterias and DB search criterias and value to be filtered
 *
 * @author Andrei Varabyeu
 */
public class Filter implements Serializable {

	private static final long serialVersionUID = 1L;

	private Class<?> target;

	private Set<FilterCondition> filterConditions;

	public Filter(Class<?> target, Condition condition, boolean negative, String value, String searchCriteria) {
		Assert.notNull(target, "Filter target shouldn'e be null");
		this.target = target;
		this.filterConditions = Sets.newHashSet(new FilterCondition(condition, negative, value, searchCriteria));
	}

	public Filter(Class<?> target, Set<FilterCondition> filterConditions) {
		Assert.notNull(target, "Filter target shouldn'e be null");
		Assert.notNull(filterConditions, "Conditions value shouldn't be null");
		this.target = target;
		this.filterConditions = filterConditions;
	}

	/**
	 * This constructor uses during serialization to database.
	 */
	@SuppressWarnings("unused")
	private Filter() {

	}

	public Class<?> getTarget() {
		return target;
	}

	public Set<FilterCondition> getFilterConditions() {
		return filterConditions;
	}

	public void addCondition(FilterCondition filterCondition) {
		this.filterConditions.add(filterCondition);
	}

	public void addConditions(Collection<FilterCondition> conditions) {
		this.filterConditions.addAll(conditions);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((filterConditions == null) ? 0 : filterConditions.hashCode());
		result = prime * result + ((target == null) ? 0 : target.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Filter other = (Filter) obj;
		if (filterConditions == null) {
			if (other.filterConditions != null)
				return false;
		} else if (!filterConditions.equals(other.filterConditions))
			return false;
		if (target == null) {
			if (other.target != null)
				return false;
		} else if (!target.equals(other.target))
			return false;
		return true;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("Filter{");
		sb.append("target=").append(target);
		sb.append(", filterConditions=").append(filterConditions);
		sb.append('}');
		return sb.toString();
	}

	public static FilterBuilder builder() {
		return new FilterBuilder();
	}

	/**
	 * Builder for {@link Filter}
	 */
	public static class FilterBuilder {
		private Class<?> target;
		private ImmutableSet.Builder<FilterCondition> conditions = ImmutableSet.builder();

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
			Set<FilterCondition> filterConditions = this.conditions.build();
			Preconditions.checkArgument(null != target, "Target should not be null");
			Preconditions.checkArgument(!filterConditions.isEmpty(), "Filter should contain at least one condition");
			return new Filter(target, filterConditions);
		}
	}

}