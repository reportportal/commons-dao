/*
 * Copyright 2016 EPAM Systems
 * 
 * 
 * This file is part of EPAM Report Portal.
 * https://github.com/epam/ReportPortal
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
 
package com.epam.ta.reportportal.database.entity.filter;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.hateoas.Identifiable;

import com.epam.ta.reportportal.database.entity.sharing.Shareable;
import com.epam.ta.reportportal.database.search.Filter;
import com.epam.ta.reportportal.database.search.FilterCriteria;

/**
 * Filter DAO layer object. Represent stored filter from ui.
 * 
 * @author Aliaksei_Makayed
 * 
 */

@Document
@CompoundIndexes({ 
@CompoundIndex(name = "project_name_name", def = "{'projectName': 1,'name': 1}") 
})
public class UserFilter extends Shareable implements Serializable, Identifiable<String> {

	private static final long serialVersionUID = 2746422761895104850L;

	public static final String PROJECT_NAME = "projectName";
	public static final String NAME = "name";
	public static final String IS_LINK = "is_link";

	@Id
	private String id;

	@FilterCriteria("name")
	private String name;

	private Filter filter;

	private SelectionOptions selectionOptions;

	@FilterCriteria("projectName")
	private String projectName;

	@FilterCriteria(IS_LINK)
	private boolean isLink;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setFilter(Filter filter) {
		this.filter = filter;
	}

	public Filter getFilter() {
		return filter;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public boolean isLink() {
		return isLink;
	}

	public void setIsLink(boolean isLink) {
		this.isLink = isLink;
	}

	@Override
	public String getId() {
		return id;
	}

	public SelectionOptions getSelectionOptions() {
		return selectionOptions;
	}

	public void setSelectionOptions(SelectionOptions selectionOptions) {
		this.selectionOptions = selectionOptions;
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
		result = prime * result + ((filter == null) ? 0 : filter.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + (isLink ? 1231 : 1237);
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((projectName == null) ? 0 : projectName.hashCode());
		result = prime * result + ((selectionOptions == null) ? 0 : selectionOptions.hashCode());
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
		UserFilter other = (UserFilter) obj;
		if (filter == null) {
			if (other.filter != null)
				return false;
		} else if (!filter.equals(other.filter))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (isLink != other.isLink)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (projectName == null) {
			if (other.projectName != null)
				return false;
		} else if (!projectName.equals(other.projectName))
			return false;
		if (selectionOptions == null) {
			if (other.selectionOptions != null)
				return false;
		} else if (!selectionOptions.equals(other.selectionOptions))
			return false;
		return true;
	}
}