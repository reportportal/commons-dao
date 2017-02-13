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

package com.epam.ta.reportportal.database.entity.item;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.epam.ta.reportportal.database.entity.Modifiable;
import com.epam.ta.reportportal.database.search.FilterCriteria;

/**
 * @author Dzmitry_Kavalets
 */
@Document
@CompoundIndexes(@CompoundIndex(name = "loggedObjectRef_creationDate", def = "{'loggedObjectRef' : 1, 'lastModifiedDate' : 1 }", background = true))
public class Activity implements Serializable, Modifiable {

	public static final String LOGGED_OBJECT_REF = "loggedObjectRef";
	public static final String PROJECT_REF = "projectRef";
	public static final String OBJECT_TYPE = "objectType";
	public static final String ACTION_TYPE = "actionType";
	public static final String USER_REF = "userRef";
	private static final long serialVersionUID = -8493206063346751955L;

	@Id
	private String id;

	@FilterCriteria(USER_REF)
	private String userRef;

	@FilterCriteria(LOGGED_OBJECT_REF)
	private String loggedObjectRef;

	@LastModifiedDate
	@FilterCriteria(LAST_MODIFIED)
	@Field(LAST_MODIFIED)
	private Date lastModifiedDate;

	@FilterCriteria(OBJECT_TYPE)
	private String objectType;

	@FilterCriteria(ACTION_TYPE)
	private String actionType;

	@FilterCriteria(PROJECT_REF)
	private String projectRef;

	@FilterCriteria("history")
	private Map<String, FieldValues> history;

	@FilterCriteria("name")
	private String name;

	public static class FieldValues implements Serializable {
		/**
		 * Generated SVUID
		 */
		private static final long serialVersionUID = -5372980278266685691L;
		public static final String OLD_VALUE = "oldValue";
		public static final String NEW_VALUE = "newValue";

		private String oldValue;
		private String newValue;

		public static FieldValues newOne() {
			return new FieldValues();
		}

		public String getOldValue() {
			return oldValue;
		}

		public void setOldValue(String oldValue) {
			this.oldValue = oldValue;
		}

		public String getNewValue() {
			return newValue;
		}

		public void setNewValue(String newValue) {
			this.newValue = newValue;
		}

		public FieldValues withOldValue(String oldValue) {
			this.oldValue = oldValue;
			return this;
		}

		public FieldValues withNewValue(String newValue) {
			this.newValue = newValue;
			return this;
		}

		@Override
		public String toString() {
			final StringBuilder sb = new StringBuilder("ChangedValues{");
			sb.append(", oldValue='").append(oldValue).append('\'');
			sb.append(", newValue='").append(newValue).append('\'');
			sb.append('}');
			return sb.toString();
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getObjectType() {
		return objectType;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}

	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	public String getProjectRef() {
		return projectRef;
	}

	public void setProjectRef(String projectRef) {
		this.projectRef = projectRef;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserRef() {
		return userRef;
	}

	public void setUserRef(String userRef) {
		this.userRef = userRef;
	}

	public String getLoggedObjectRef() {
		return loggedObjectRef;
	}

	public void setLoggedObjectRef(String loggedObjectRef) {
		this.loggedObjectRef = loggedObjectRef;
	}

	@Override
	public Date getLastModified() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public Map<String, FieldValues> getHistory() {
		return null == history ? new HashMap<>() : history;
	}

	public void setHistory(Map<String, FieldValues> history) {
		this.history = history;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Activity activity = (Activity) o;

		if (history != null ? !history.equals(activity.history) : activity.history != null)
			return false;
		if (id != null ? !id.equals(activity.id) : activity.id != null)
			return false;
		if (lastModifiedDate != null ? !lastModifiedDate.equals(activity.lastModifiedDate) : activity.lastModifiedDate != null)
			return false;
		if (loggedObjectRef != null ? !loggedObjectRef.equals(activity.loggedObjectRef) : activity.loggedObjectRef != null)
			return false;
		if (userRef != null ? !userRef.equals(activity.userRef) : activity.userRef != null)
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (userRef != null ? userRef.hashCode() : 0);
		result = 31 * result + (loggedObjectRef != null ? loggedObjectRef.hashCode() : 0);
		result = 31 * result + (lastModifiedDate != null ? lastModifiedDate.hashCode() : 0);
		result = 31 * result + (history != null ? history.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("Activity{");
		sb.append("id='").append(id).append('\'');
		sb.append(", userRef='").append(userRef).append('\'');
		sb.append(", loggedObjectRef='").append(loggedObjectRef).append('\'');
		sb.append(", lastModifiedDate=").append(lastModifiedDate);
		sb.append(", history=").append(history);
		sb.append('}');
		return sb.toString();
	}
}
