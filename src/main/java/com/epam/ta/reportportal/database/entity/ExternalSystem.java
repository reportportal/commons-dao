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
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.epam.ta.reportportal.database.entity.item.issue.ExternalSystemType;
import com.epam.ta.reportportal.ws.model.externalsystem.PostFormField;

/**
 * Model representation of External System Configuration for projects
 * 
 * @author Andrei_Ramanchuk
 */
@Document
public class ExternalSystem implements Serializable {
	private static final long serialVersionUID = -6449163583685872125L;

	@Id
	private String id;

	@Indexed
	private String url;

	@Indexed
	private String projectRef;

	private ExternalSystemType externalSystemType;
	private AuthType externalSystemAuth;
	private String username;
	private String password;
	private String domain;
	private String accessKey;
	private String project;
	private List<PostFormField> fields;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setExternalSystemType(ExternalSystemType type) {
		this.externalSystemType = type;
	}

	public ExternalSystemType getExternalSystemType() {
		return externalSystemType;
	}

	public void setExternalSystemAuth(AuthType type) {
		this.externalSystemAuth = type;
	}

	public AuthType getExternalSystemAuth() {
		return externalSystemAuth;
	}

	public void setUrl(String value) {
		this.url = value;
	}

	public String getUrl() {
		return url;
	}

	public void setProjectRef(String reference) {
		this.projectRef = reference;
	}

	public String getProjectRef() {
		return projectRef;
	}

	public void setUsername(String name) {
		this.username = name;
	}

	public String getUsername() {
		return username;
	}

	public void setPassword(String pass) {
		this.password = pass;
	}

	public String getPassword() {
		return password;
	}

	public void setDomain(String value) {
		this.domain = value;
	}

	public String getDomain() {
		return domain;
	}

	public void setAccessKey(String key) {
		this.accessKey = key;
	}

	public String getAccessKey() {
		return accessKey;
	}

	public void setProject(String extProject) {
		this.project = extProject;
	}

	public String getProject() {
		return project;
	}

	public void setFields(List<PostFormField> form) {
		this.fields = form;
	}

	public List<PostFormField> getFields() {
		return fields;
	}
}
