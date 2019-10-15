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

package com.epam.ta.reportportal.entity.dashboard;

import com.epam.ta.reportportal.entity.widget.Widget;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Pavel Bortnik
 */
@Entity
@Table(name = "dashboard_widget")
public class DashboardWidget implements Serializable {

	@EmbeddedId
	private DashboardWidgetId id;

	@ManyToOne(fetch = FetchType.EAGER)
	@MapsId("dashboard_id")
	private Dashboard dashboard;

	@ManyToOne(fetch = FetchType.EAGER)
	@MapsId("widget_id")
	private Widget widget;

	@Column(name = "widget_name")
	private String widgetName;

	@Column(name = "widget_owner")
	private String widgetOwner;

	@Column(name = "widget_type")
	private String widgetType;

	@Column(name = "is_created_on")
	private boolean createdOn;

	@Column(name = "widget_width")
	private int width;

	@Column(name = "widget_height")
	private int height;

	@Column(name = "widget_position_x")
	private int positionX;

	@Column(name = "widget_position_y")
	private int positionY;

	@Column(name = "share")
	private boolean share;

	public DashboardWidgetId getId() {
		return id;
	}

	public void setId(DashboardWidgetId id) {
		this.id = id;
	}

	public Dashboard getDashboard() {
		return dashboard;
	}

	public void setDashboard(Dashboard dashboard) {
		this.dashboard = dashboard;
	}

	public Widget getWidget() {
		return widget;
	}

	public void setWidget(Widget widget) {
		this.widget = widget;
	}

	public String getWidgetName() {
		return widgetName;
	}

	public void setWidgetName(String widgetName) {
		this.widgetName = widgetName;
	}

	public String getWidgetOwner() {
		return widgetOwner;
	}

	public void setWidgetOwner(String widgetOwner) {
		this.widgetOwner = widgetOwner;
	}

	public boolean isCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(boolean createdOn) {
		this.createdOn = createdOn;
	}

	public int getWidth() {
		return width;
	}

	public String getWidgetType() {
		return widgetType;
	}

	public void setWidgetType(String widgetType) {
		this.widgetType = widgetType;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getPositionX() {
		return positionX;
	}

	public void setPositionX(int positionX) {
		this.positionX = positionX;
	}

	public int getPositionY() {
		return positionY;
	}

	public void setPositionY(int positionY) {
		this.positionY = positionY;
	}

	public boolean isShare() {
		return share;
	}

	public void setShare(boolean share) {
		this.share = share;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		DashboardWidget that = (DashboardWidget) o;

		return id != null ? id.equals(that.id) : that.id == null;
	}

	@Override
	public int hashCode() {
		return id != null ? id.hashCode() : 0;
	}
}
