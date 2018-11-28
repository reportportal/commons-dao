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

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	@JoinColumn(name = "filter_id")
	private Set<FilterCondition> filterCondition = Sets.newHashSet();

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
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
