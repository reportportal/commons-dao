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

package com.epam.ta.reportportal.database.entity.widget;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.hateoas.Identifiable;

import com.epam.ta.reportportal.database.entity.sharing.Shareable;

/**
 * Widget DAO layer object.
 * 
 * @author Aliaksei_Makayed
 * 
 */
// UI get widgets one by one using id fields, so additional indexing isn't
// required for widgets
@Document
public class Widget extends Shareable implements Serializable, Identifiable<String> {

	public static final String USER_NAME = "userName";
	public static final String NAME = "name";
	public static final String WIDGET = "widget";
	public static final String GADGET_TYPE = "contentOptions.gadgetType";
	public static final String CONTENT_FIELDS = "contentOptions.contentFields";

	private static final long serialVersionUID = -8462041796130099338L;

	@Id
	private String id;

	private String name;

	private ContentOptions contentOptions;

	private String applyingFilterId;

	// TODO consider creating separate level for project related objects.
	private String projectName;

	@Override
	public String getId() {
		return this.id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ContentOptions getContentOptions() {
		return contentOptions;
	}

	public void setContentOptions(ContentOptions contentOptions) {
		this.contentOptions = contentOptions;
	}

	public String getApplyingFilterId() {
		return applyingFilterId;
	}

	public void setApplyingFilterId(String applyingFilterId) {
		this.applyingFilterId = applyingFilterId;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((applyingFilterId == null) ? 0 : applyingFilterId.hashCode());
		result = prime * result + ((contentOptions == null) ? 0 : contentOptions.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Widget other = (Widget) obj;
		if (applyingFilterId == null) {
			if (other.applyingFilterId != null)
				return false;
		} else if (!applyingFilterId.equals(other.applyingFilterId))
			return false;
		if (contentOptions == null) {
			if (other.contentOptions != null)
				return false;
		} else if (!contentOptions.equals(other.contentOptions))
			return false;
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
		return true;
	}

}