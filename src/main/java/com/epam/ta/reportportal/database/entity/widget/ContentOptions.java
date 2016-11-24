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
import java.util.List;
import java.util.Map;

/**
 * DAO layer holder for all possible chart parameters
 *
 * @author Aliaksei_Makayed
 *
 */
public class ContentOptions implements Serializable {

	private static final long serialVersionUID = 1L;

	private String type;

	private String gadgetType;

	// fields names for any required meta data(for example: dots at the x axis)
	private List<String> metadataFields;

	// fields for main data(for example: graphs at the chart)
	private List<String> contentFields;

	private Map<String, List<String>> widgetOptions; //NOSONAR

	private int itemsCount;

	public int getItemsCount() {
		return itemsCount;
	}

	public void setItemsCount(int itemsCount) {
		this.itemsCount = itemsCount;
	}

	public void setWidgetOptions(Map<String, List<String>> widgetOptions) {
		this.widgetOptions = widgetOptions;
	}

	public Map<String, List<String>> getWidgetOptions() {
		return widgetOptions;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getGadgetType() {
		return gadgetType;
	}

	public void setGadgetType(String value) {
		this.gadgetType = value;
	}

	public void setContentFields(List<String> contentFields) {
		this.contentFields = contentFields;
	}

	public List<String> getMetadataFields() {
		return metadataFields;
	}

	public void setMetadataFields(List<String> metadataFields) {
		this.metadataFields = metadataFields;
	}

	public List<String> getContentFields() {
		return contentFields;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((contentFields == null) ? 0 : contentFields.hashCode());
		result = prime * result + ((metadataFields == null) ? 0 : metadataFields.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		ContentOptions other = (ContentOptions) obj;
		if (contentFields == null) {
			if (other.contentFields != null)
				return false;
		} else if (!contentFields.equals(other.contentFields))
			return false;
		if (metadataFields == null) {
			if (other.metadataFields != null)
				return false;
		} else if (!metadataFields.equals(other.metadataFields))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}
}