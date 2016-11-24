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

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.hateoas.Identifiable;

import com.epam.ta.reportportal.database.entity.project.EntryType;
import com.epam.ta.reportportal.database.search.FilterCriteria;

/**
 * 
 * @author Andrei_Kliashchonak
 * @author Andrei_Ramanchuk
 * 
 */
@Document
public class User implements Serializable, Identifiable<String> {

	public static final String IS_EXPIRED = "isExpired";
	public static final String NAME = "name";
	public static final String LOGIN = "login";
	public static final String PHOTO_ID = "photoId";
	public static final String EXPIRED = "expired";
	public static final String TYPE = "type";
	public static final String EMAIL = "email";

	private static final long serialVersionUID = 6589946977687369280L;

	@Id
	@FilterCriteria(LOGIN)
	private String login;

	private String password;

	@Indexed(unique = true)
	@FilterCriteria(EMAIL)
	private String email;

	private String photoId;

	private UserRole role;

	@FilterCriteria(TYPE)
	private UserType type;

	@FilterCriteria(EXPIRED)
	private boolean isExpired;

	private String defaultProject;

	@FilterCriteria(NAME)
	private String fullName;

	private MetaInfo metaInfo;

	public User() {
	}

	@Override
	public String getId() {
		return this.login;
	}

	/**
	 * @return the photoId
	 */
	public String getPhotoId() {
		return photoId;
	}

	/**
	 * @param photoId
	 *            the photoId to set
	 */
	public void setPhotoId(String photoId) {
		this.photoId = photoId;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	public UserType getType() {
		return type;
	}

	public void setType(UserType type) {
		this.type = type;
	}

	public void setIsExpired(boolean value) {
		this.isExpired = value;
	}

	public boolean getIsExpired() {
		return isExpired;
	}

	public void setDefaultProject(String value) {
		this.defaultProject = value;
	}

	public String getDefaultProject() {
		return defaultProject;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	/**
	 * Null-safe getter
	 * 
	 * @return
	 */
	@NotNull
	public MetaInfo getMetaInfo() {
		return metaInfo == null ? metaInfo = new MetaInfo() : metaInfo;
	}

	public void setMetaInfo(MetaInfo metaInfo) {
		this.metaInfo = metaInfo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((defaultProject == null) ? 0 : defaultProject.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((fullName == null) ? 0 : fullName.hashCode());
		result = prime * result + (isExpired ? 1231 : 1237);
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((photoId == null) ? 0 : photoId.hashCode());
		result = prime * result + ((role == null) ? 0 : role.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		User other = (User) obj;
		if (defaultProject == null) {
			if (other.defaultProject != null) {
				return false;
			}
		} else if (!defaultProject.equals(other.defaultProject)) {
			return false;
		}
		if (email == null) {
			if (other.email != null) {
				return false;
			}
		} else if (!email.equals(other.email)) {
			return false;
		}
		if (fullName == null) {
			if (other.fullName != null) {
				return false;
			}
		} else if (!fullName.equals(other.fullName)) {
			return false;
		}
		if (isExpired != other.isExpired) {
			return false;
		}
		if (login == null) {
			if (other.login != null) {
				return false;
			}
		} else if (!login.equals(other.login)) {
			return false;
		}
		if (password == null) {
			if (other.password != null) {
				return false;
			}
		} else if (!password.equals(other.password)) {
			return false;
		}
		if (photoId == null) {
			if (other.photoId != null) {
				return false;
			}
		} else if (!photoId.equals(other.photoId)) {
			return false;
		}
		if (role != other.role) {
			return false;
		}
		if (type != other.type) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "User{" + "login='" + login + '\'' + ", password='" + password + '\'' + ", email='" + email + '\'' + ", photoId='" + photoId
				+ '\'' + ", role=" + role + ", type=" + type + ", isExpired=" + isExpired + ", defaultProject='" + defaultProject + '\''
				+ ", fullName='" + fullName + '\'' + ", metaInfo=" + metaInfo + '}';
	}

	public static class MetaInfo implements Serializable {

		private static final long serialVersionUID = 1L;

		public static final String LAST_LOGIN_PATH = "metaInfo.lastLogin";

		public static final String SYNCHRONIZATION_DATE = "metaInfo.synchronizationDate";

		private Date lastLogin;

		@Field("synchronizationDate")
		private Date synchronizationDate;

		/**
		 * @return the lastLogin
		 */
		public Date getLastLogin() {
			return lastLogin;
		}

		/**
		 * @param lastLogin
		 *            the lastLogin to set
		 */
		public void setLastLogin(Date lastLogin) {
			this.lastLogin = lastLogin;
		}

		/**
		 * @return the synchronizationDate
		 */
		public Date getSynchronizationDate() {
			return synchronizationDate;
		}

		/**
		 * @param synchronizationDate
		 *            the synchronizationDate to set
		 */
		public void setSynchronizationDate(Date synchronizationDate) {
			this.synchronizationDate = synchronizationDate;
		}

	}

}
