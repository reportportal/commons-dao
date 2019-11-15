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

package com.epam.ta.reportportal.entity.widget;

import com.epam.ta.reportportal.entity.ShareableEntity;
import com.epam.ta.reportportal.entity.dashboard.DashboardWidget;
import com.epam.ta.reportportal.entity.filter.UserFilter;
import com.google.common.collect.Sets;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * @author Pavel Bortnik
 */
@Entity
@Table(name = "widget")
@TypeDef(name = "widgetOptions", typeClass = WidgetOptions.class)
public class Widget extends ShareableEntity implements Serializable {

	@Column(name = "name")
	private String name;

	@Column(name = "description")
	private String description;

	@Column(name = "widget_type")
	private String widgetType;

	@Column(name = "items_count")
	private int itemsCount;

	@ManyToMany
	@JoinTable(name = "widget_filter", joinColumns = @JoinColumn(name = "widget_id"), inverseJoinColumns = @JoinColumn(name = "filter_id"))
	private Set<UserFilter> filters;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "content_field", joinColumns = @JoinColumn(name = "id"))
	@Column(name = "field")
	@OrderBy(value = "id")
	private Set<String> contentFields = Sets.newLinkedHashSet();

	@Type(type = "widgetOptions")
	@Column(name = "widget_options")
	private WidgetOptions widgetOptions;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "widget")
	@Fetch(value = FetchMode.JOIN)
	private Set<DashboardWidget> dashboardWidgets = Sets.newHashSet();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getWidgetType() {
		return widgetType;
	}

	public void setWidgetType(String widgetType) {
		this.widgetType = widgetType;
	}

	public int getItemsCount() {
		return itemsCount;
	}

	public void setItemsCount(int itemsCount) {
		this.itemsCount = itemsCount;
	}

	public Set<String> getContentFields() {
		return contentFields;
	}

	public void setContentFields(Set<String> contentFields) {
		this.contentFields = contentFields;
	}

	public WidgetOptions getWidgetOptions() {
		return widgetOptions;
	}

	public void setWidgetOptions(WidgetOptions widgetOptions) {
		this.widgetOptions = widgetOptions;
	}

	public Set<DashboardWidget> getDashboardWidgets() {
		return dashboardWidgets;
	}

	public void setDashboardWidgets(Set<DashboardWidget> dashboardWidgets) {
		this.dashboardWidgets = dashboardWidgets;
	}

	public Set<UserFilter> getFilters() {
		return filters;
	}

	public void setFilters(Set<UserFilter> filters) {
		this.filters = filters;
	}
}
