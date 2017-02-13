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

package com.epam.ta.reportportal.database.entity.user;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.epam.ta.reportportal.database.entity.Modifiable;
import com.epam.ta.reportportal.database.search.FilterCriteria;

/**
 * @author Dzmitry_Kavalets
 */
@Document
public class RestorePasswordBid implements Serializable, Modifiable {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = 5010586530900139611L;

	@Id
	private String id;

	@LastModifiedDate
	@FilterCriteria(LAST_MODIFIED)
	@Field(LAST_MODIFIED)
	@Indexed(expireAfterSeconds = 86400) // TTL index for 1 day
	private Date lastModifiedDate;

	private String email;

	public String getId() {
		return id;
	}

	@Override
	public Date getLastModified() {
		return lastModifiedDate;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		RestorePasswordBid that = (RestorePasswordBid) o;

		if (id != null ? !id.equals(that.id) : that.id != null)
			return false;
		if (lastModifiedDate != null ? !lastModifiedDate.equals(that.lastModifiedDate) : that.lastModifiedDate != null)
			return false;
		return !(email != null ? !email.equals(that.email) : that.email != null);

	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (lastModifiedDate != null ? lastModifiedDate.hashCode() : 0);
		result = 31 * result + (email != null ? email.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("RestorePasswordBid{");
		sb.append("id='").append(id).append('\'');
		sb.append(", lastModifiedDate=").append(lastModifiedDate);
		sb.append(", email='").append(email).append('\'');
		sb.append('}');
		return sb.toString();
	}
}
