/*
 * Copyright 2017 EPAM Systems
 *
 *
 * This file is part of EPAM Report Portal.
 * https://github.com/reportportal/service-api
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

package com.epam.ta.reportportal.entity.filter;

import com.epam.ta.reportportal.commons.querygen.FilterCondition;
import com.google.common.collect.Sets;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * Extension of basic filter object. Represents user filter
 * with sorting and conditions
 *
 * @author Pavel Bortnik
 */

@Entity
@Table(name = "user_filter", schema = "public")
public class UserFilter extends Queryable implements Serializable {

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinColumn(name = "filter_id")
	private Set<FilterCondition> filterCondition = Sets.newHashSet();

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinColumn(name = "filter_id")
	private Set<FilterSort> filterSorts = Sets.newLinkedHashSet();

	public Set<FilterCondition> getFilterCondition() {
		return filterCondition;
	}

	public void setFilterCondition(Set<FilterCondition> filterCondition) {
		this.filterCondition = filterCondition;
	}

	public Set<FilterSort> getFilterSorts() {
		return filterSorts;
	}

	public void setFilterSorts(Set<FilterSort> filterSorts) {
		this.filterSorts = filterSorts;
	}

}
