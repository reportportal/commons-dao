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

package com.epam.ta.reportportal.database.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.hateoas.Identifiable;

import com.epam.ta.reportportal.database.entity.sharing.Shareable;

/**
 * Persistence layer object for storing dashboards settings.
 * 
 * @author Aliaksei_Makayed
 */

@Document
@CompoundIndexes({ @CompoundIndex(name = "projectName_creationDate", def = "{'projectName': 1, 'creationDate': 1}") })
public class Dashboard extends Shareable implements Serializable, Identifiable<String> {

	private static final long serialVersionUID = -3353164425951585301L;

	public static final String NAME = "name";
	public static final String USER_NAME = "userName";
	public static final String PROJECT_NAME = "projectName";
	public static final String CREATION_DATE = "creationDate";
	public static final String DASHBOARD = "dashboard";

	@Id
	private String id;

	private String name;

	private List<WidgetObject> widgets;

	private String projectName;

	@CreatedDate
	private Date creationDate;

	private String description;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Dashboard() {
		widgets = new ArrayList<>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<WidgetObject> getWidgets() {
		return widgets;
	}

	public void setWidgets(List<WidgetObject> widgets) {
		this.widgets = widgets;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getId() {
		return this.id;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public static class WidgetObject implements Serializable {
		/**
		 * Generated SVUID
		 */
		private static final long serialVersionUID = -720962097930728425L;

		private String widgetId;
		private List<Integer> widgetSize;
		private List<Integer> widgetPosition;

		public WidgetObject() {
		}

		public WidgetObject(String widgetId, List<Integer> widgetSize, List<Integer> widgetPosition) {
			this.setWidgetId(widgetId);
			this.setWidgetSize(widgetSize);
			this.setWidgetPosition(widgetPosition);
		}

		public void setWidgetId(String value) {
			this.widgetId = value;
		}

		public String getWidgetId() {
			return widgetId;
		}

		public void setWidgetSize(List<Integer> value) {
			this.widgetSize = value;
		}

		public List<Integer> getWidgetSize() {
			return widgetSize;
		}

		public void setWidgetPosition(List<Integer> value) {
			this.widgetPosition = value;
		}

		public List<Integer> getWidgetPosition() {
			return widgetPosition;
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((widgets == null) ? 0 : widgets.hashCode());
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
		Dashboard other = (Dashboard) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (widgets == null) {
			if (other.widgets != null)
				return false;
		} else if (!widgets.equals(other.widgets))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Dashboard{" + "id='" + id + '\'' + ", name='" + name + '\'' + ", widgets=" + widgets + ", projectName='" + projectName
				+ '\'' + ", creationDate=" + creationDate + '}';
	}
}