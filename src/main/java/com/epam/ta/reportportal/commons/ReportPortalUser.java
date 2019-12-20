/*
 * Copyright 2019 EPAM Systems
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.epam.ta.reportportal.commons;

import com.epam.ta.reportportal.entity.project.ProjectRole;
import com.epam.ta.reportportal.entity.user.UserRole;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

/**
 * ReportPortal user representation
 *
 * @author <a href="mailto:andrei_varabyeu@epam.com">Andrei Varabyeu</a>
 */
public class ReportPortalUser extends User {

	private Long userId;

	private UserRole userRole;

	private String email;

	private Map<String, ProjectDetails> projectDetails;

	private ReportPortalUser(String username, String password, Collection<? extends GrantedAuthority> authorities, Long userId,
			UserRole role, Map<String, ProjectDetails> projectDetails, String email) {
		super(username, password, authorities);
		this.userId = userId;
		this.userRole = role;
		this.projectDetails = projectDetails;
		this.email = email;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public UserRole getUserRole() {
		return userRole;
	}

	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Map<String, ProjectDetails> getProjectDetails() {
		return projectDetails;
	}

	public void setProjectDetails(Map<String, ProjectDetails> projectDetails) {
		this.projectDetails = projectDetails;
	}

	public static ReportPortalUserBuilder userBuilder() {
		return new ReportPortalUserBuilder();
	}

	public static class ProjectDetails implements Serializable {

		@JsonProperty(value = "id")
		private Long projectId;

		@JsonProperty(value = "name")
		private String projectName;

		@JsonProperty("role")
		private ProjectRole projectRole;

		public ProjectDetails(Long projectId, String projectName, ProjectRole projectRole) {
			this.projectId = projectId;
			this.projectName = projectName;
			this.projectRole = projectRole;
		}

		public Long getProjectId() {
			return projectId;
		}

		public String getProjectName() {
			return projectName;
		}

		public ProjectRole getProjectRole() {
			return projectRole;
		}
	}

	public static class ReportPortalUserBuilder {
		private String username;
		private String password;
		private Long userId;
		private UserRole userRole;
		private String email;
		private Map<String, ProjectDetails> projectDetails;
		private Collection<? extends GrantedAuthority> authorities;

		private ReportPortalUserBuilder() {

		}

		public ReportPortalUserBuilder withUserName(String userName) {
			this.username = userName;
			return this;
		}

		public ReportPortalUserBuilder withPassword(String password) {
			this.password = password;
			return this;
		}

		public ReportPortalUserBuilder withAuthorities(Collection<? extends GrantedAuthority> authorities) {
			this.authorities = authorities;
			return this;
		}

		public ReportPortalUserBuilder withUserDetails(UserDetails userDetails) {
			this.username = userDetails.getUsername();
			this.password = userDetails.getPassword();
			this.authorities = userDetails.getAuthorities();
			return this;
		}

		public ReportPortalUserBuilder withUserId(Long userId) {
			this.userId = userId;
			return this;
		}

		public ReportPortalUserBuilder withUserRole(UserRole userRole) {
			this.userRole = userRole;
			return this;
		}

		public ReportPortalUserBuilder withEmail(String email) {
			this.email = email;
			return this;
		}

		public ReportPortalUserBuilder withProjectDetails(Map<String, ProjectDetails> projectDetails) {
			this.projectDetails = projectDetails;
			return this;
		}

		public ReportPortalUser build() {
			return new ReportPortalUser(username, password, authorities, userId, userRole, projectDetails, email);
		}
	}
}
