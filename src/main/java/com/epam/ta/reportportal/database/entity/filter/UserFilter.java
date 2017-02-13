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

package com.epam.ta.reportportal.database.entity.filter;

import java.io.Serializable;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

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
@CompoundIndexes({ @CompoundIndex(name = "project_name_name", def = "{'projectName': 1,'name': 1}") })
public class UserFilter extends Shareable implements Serializable {

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
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		UserFilter that = (UserFilter) o;
		return isLink == that.isLink &&
				Objects.equals(id, that.id) &&
				Objects.equals(name, that.name) &&
				Objects.equals(filter, that.filter) &&
				Objects.equals(selectionOptions, that.selectionOptions) &&
				Objects.equals(projectName, that.projectName);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, filter, selectionOptions, projectName, isLink);
	}
}
