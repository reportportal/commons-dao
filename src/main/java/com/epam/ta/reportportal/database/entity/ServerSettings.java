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
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.hateoas.Identifiable;

import com.epam.ta.reportportal.database.search.FilterCriteria;
import com.epam.ta.reportportal.ws.model.settings.ServerEmailConfig;

/**
 * <b>GLOBAL SERVER SETTINGS</b> stored in database for beans properties definitions<br>
 * Documents could be stored by profile name as document ID<br>
 * 
 * Current content:<br>
 * EMail settings
 * 
 * @author Andrei_Ramanchuk
 *
 */
@Document
public class ServerSettings implements Serializable, Modifiable, Identifiable<String> {
	/**
	 * Default ID
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private String profile;

	/*
	 * TODO projectRef could be here for per project profiling functionality with default profile
	 * for general settings
	 */
	private boolean active;

	private ServerEmailConfig serverEmailConfig;

	private Map<String, OAuth2LoginDetails> oAuth2LoginDetails;

	@LastModifiedDate
	@FilterCriteria(LAST_MODIFIED)
	@Field(LAST_MODIFIED)
	private Date lastModified;

	public void setId(String profile) {
		this.profile = profile;
	}

	@Override
	public String getId() {
		return profile;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

	@Override
	public Date getLastModified() {
		return lastModified;
	}

	public void setServerEmailConfig(ServerEmailConfig config) {
		this.serverEmailConfig = config;
	}

	public ServerEmailConfig getServerEmailConfig() {
		return serverEmailConfig;
	}

	public void setActive(boolean is) {
		this.active = is;
	}

	public boolean getActive() {
		return active;
	}

	public Map<String, OAuth2LoginDetails> getoAuth2LoginDetails() {
		return oAuth2LoginDetails;
	}

	public void setoAuth2LoginDetails(Map<String, OAuth2LoginDetails> oAuth2LoginDetails) {
		this.oAuth2LoginDetails = oAuth2LoginDetails;
	}
}