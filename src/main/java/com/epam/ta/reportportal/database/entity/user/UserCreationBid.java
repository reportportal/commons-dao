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

package com.epam.ta.reportportal.database.entity.user;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.hateoas.Identifiable;

import com.epam.ta.reportportal.database.entity.Modifiable;
import com.epam.ta.reportportal.database.search.FilterCriteria;

/**
 * Document representation for User Creation Bid
 * 
 * @author Andrei_Ramanchuk
 */
@Document
public class UserCreationBid implements Serializable, Identifiable<String>, Modifiable {
	/**
	 * Generated ID
	 */
	private static final long serialVersionUID = -6071966822957707992L;

	@Id
	private String id;

	@LastModifiedDate
	@FilterCriteria(LAST_MODIFIED)
	@Field(LAST_MODIFIED)
	@Indexed(expireAfterSeconds = 86400) // TTL index for 1 day
	private Date lastModifiedDate;

	private String email;

	private String defaultProject;

	private String role;

	@Override
	public String getId() {
		return id;
	}

	public void setId(String value) {
		this.id = value;
	}

	public void setEmail(String value) {
		this.email = value;
	}

	public String getEmail() {
		return email;
	}

	public void setDefaultProject(String value) {
		this.defaultProject = value;
	}

	public String getDefaultProject() {
		return defaultProject;
	}

	public void setRole(String value) {
		this.role = value;
	}

	public String getRole() {
		return role;
	}

	@Override
	public Date getLastModified() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date value) {
		this.lastModifiedDate = value;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		UserCreationBid bid = (UserCreationBid) o;
		if (id != null ? !id.equals(bid.id) : bid.id != null)
			return false;
		if (lastModifiedDate != null ? !lastModifiedDate.equals(bid.lastModifiedDate) : bid.lastModifiedDate != null)
			return false;
		if (email != null ? !email.equals(bid.email) : bid.email != null)
			return false;
		if (defaultProject != null ? !defaultProject.equals(bid.defaultProject) : bid.defaultProject != null)
			return false;
		if (role != null ? !role.equals(bid.role) : bid.role != null)
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (lastModifiedDate != null ? lastModifiedDate.hashCode() : 0);
		result = 31 * result + (email != null ? email.hashCode() : 0);
		result = 31 * result + (defaultProject != null ? defaultProject.hashCode() : 0);
		result = 31 * result + (role != null ? role.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("CreateUserBid {");
		sb.append("id='").append(id).append('\'');
		sb.append(", lastModifiedDate='").append(lastModifiedDate).append('\'');
		sb.append(", email='").append(email).append('\'');
		sb.append(", defaultProject='").append(defaultProject).append('\'');
		sb.append(", role='").append(role).append('\'');
		sb.append('}');
		return sb.toString();
	}
}