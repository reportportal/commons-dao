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

package com.epam.ta.reportportal.entity.widget.content;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import java.util.*;

import static com.epam.ta.reportportal.dao.constant.WidgetContentRepositoryConstants.*;

/**
 * @author Ivan Budayeu
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LaunchesStatisticsContent extends AbstractLaunchStatisticsContent {

	@Column(name = ATTRIBUTE_VALUES)
	@JsonProperty(value = "attributes")
	private Set<String> attributes = new LinkedHashSet<>();

	@JsonProperty(value = "values")
	private Map<String, String> values = new LinkedHashMap<>();

	@Column(name = SUM)
	@JsonProperty(value = SUM)
	private Map<String, Integer> totalStatistics = new LinkedHashMap<>();

	@Column(name = DURATION)
	@JsonProperty(value = DURATION)
	private Long duration;

	@JsonIgnore
	@Column(name = FILTER_NAME)
	private String filterName;

	public Set<String> getAttributes() {
		return attributes;
	}

	public void setAttributes(Set<String> attributes) {
		this.attributes = attributes;
	}

	public Map<String, String> getValues() {
		return values;
	}

	public void setValues(Map<String, String> values) {
		this.values = values;
	}

	public Map<String, Integer> getTotalStatistics() {
		return totalStatistics;
	}

	public void setTotalStatistics(Map<String, Integer> totalStatistics) {
		this.totalStatistics = totalStatistics;
	}

	public Long getDuration() {
		return duration;
	}

	public void setDuration(Long duration) {
		this.duration = duration;
	}

	public String getFilterName() {
		return filterName;
	}

	public void setFilterName(String filterName) {
		this.filterName = filterName;
	}
}
