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

package com.epam.ta.reportportal.entity.widget;

import com.epam.ta.reportportal.entity.dashboard.DashboardWidget;
import com.epam.ta.reportportal.entity.filter.UserFilter;
import com.epam.ta.reportportal.entity.project.Project;
import com.google.common.collect.Sets;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Pavel Bortnik
 */
@Entity
@Table(name = "widget", schema = "public")
public class Widget implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "description")
	private String description;

	@Column(name = "widget_type")
	private String widgetType;

	@Column(name = "items_count")
	private int itemsCount;

	@ManyToOne
	@JoinColumn(name = "filter_id")
	private UserFilter filter;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinColumn(name = "widget_id")
	private Set<ContentField> contentFields = Sets.newHashSet();

	@ElementCollection(fetch = FetchType.LAZY)
	@JoinTable(name = "widget_option", joinColumns = @JoinColumn(name = "widget_id"))
	@MapKeyColumn(name = "option")
	@Column(name = "value")
	private Map<String, String> widgetOptions = new HashMap<String, String>();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "project_id")
	private Project project;

	@OneToMany(mappedBy = "widget", cascade = CascadeType.ALL, orphanRemoval = true)
	@Fetch(value = FetchMode.JOIN)
	private Set<DashboardWidget> dashboardWidgets = Sets.newHashSet();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public Set<ContentField> getContentFields() {
		return contentFields;
	}

	public void setContentFields(Set<ContentField> contentFields) {
		this.contentFields = contentFields;
	}

	public Map<String, String> getWidgetOptions() {
		return widgetOptions;
	}

	public void setWidgetOptions(Map<String, String> widgetOptions) {
		this.widgetOptions = widgetOptions;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Set<DashboardWidget> getDashboardWidgets() {
		return dashboardWidgets;
	}

	public void setDashboardWidgets(Set<DashboardWidget> dashboardWidgets) {
		this.dashboardWidgets = dashboardWidgets;
	}

	public UserFilter getFilter() {
		return filter;
	}

	public void setFilter(UserFilter filter) {
		this.filter = filter;
	}
}
