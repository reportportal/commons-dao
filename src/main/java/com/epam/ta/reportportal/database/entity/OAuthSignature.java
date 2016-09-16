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

package com.epam.ta.reportportal.database.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.hateoas.Identifiable;

import com.epam.ta.reportportal.database.search.FilterCriteria;

/**
 * OAuth signatures storage
 * 
 * @author Andrei_Ramanchuk
 */
@Document
public class OAuthSignature implements Serializable, Identifiable<String> {
	/**
	 * Generated ID
	 */
	private static final long serialVersionUID = -7433798786159769937L;

	@Id
	private String id;

	@Indexed
	@FilterCriteria("systemUrl")
	private String externalSystemUrl;

	private String consumerKey;

	private String externalSystemType;

	private Date issuedOn;

	private Date expiresOn;

	@Indexed
	@FilterCriteria("userRef")
	private String userRef;

	private String accessToken;

	@Override
	public String getId() {
		return id;
	}

	public void setId(String value) {
		this.id = value;
	}

	public String getExternalSystemUrl() {
		return externalSystemUrl;
	}

	public void setExternalSystemUrl(String url) {
		this.externalSystemUrl = url;
	}

	public void setConsumerKey(String key) {
		this.consumerKey = key;
	}

	public String getConsumerKey() {
		return consumerKey;
	}

	public String getExternalSystemType() {
		return externalSystemType;
	}

	public void setExternalSystemType(String type) {
		this.externalSystemType = type;
	}

	public Date getIssuedOn() {
		return issuedOn;
	}

	public void setIssuedOn(Date date) {
		this.issuedOn = date;
	}

	public Date getExpiresOn() {
		return expiresOn;
	}

	public void setExpiresOn(Date date) {
		this.expiresOn = date;
	}

	public String getUserRef() {
		return userRef;
	}

	public void setUserRef(String user) {
		this.userRef = user;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String value) {
		this.accessToken = value;
	}
}