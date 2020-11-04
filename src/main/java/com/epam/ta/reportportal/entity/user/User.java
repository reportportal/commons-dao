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

package com.epam.ta.reportportal.entity.user;

import com.epam.ta.reportportal.entity.Metadata;
import com.google.common.collect.Sets;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

/**
 * @author Andrei Varabyeu
 */
@Entity
@TypeDef(name = "meta", typeClass = Metadata.class)
@Table(name = "users", schema = "public")
public class User implements Serializable {

	private static final long serialVersionUID = 923392981;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false, precision = 64)
	private Long id;

	@Column(name = "login")
	private String login;

	@Column(name = "password")
	private String password;

	@Column(name = "email")
	private String email;

	@Column(name = "role")
	@Enumerated(EnumType.STRING)
	private UserRole role;

	@Column(name = "full_name")
	private String fullName;

	@Column(name = "expired")
	private boolean isExpired;

	@Column(name = "metadata")
	@Type(type = "meta")
	private Metadata metadata;

	@Column(name = "attachment")
	private String attachment;

	@Column(name = "attachment_thumbnail")
	private String attachmentThumbnail;

	@Column(name = "type")
	private UserType userType;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
	private Set<ProjectUser> projects = Sets.newHashSet();

	public User() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLogin() {
		return this.login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return this.email;
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

	public Set<ProjectUser> getProjects() {
		return projects;
	}

	public void setProjects(Set<ProjectUser> projects) {
		this.projects = projects;
	}

	public String getFullName() {
		return this.fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public boolean isExpired() {
		return isExpired;
	}

	public void setExpired(boolean expired) {
		isExpired = expired;
	}

	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	public String getAttachmentThumbnail() {
		return attachmentThumbnail;
	}

	public void setAttachmentThumbnail(String attachmentThumbnail) {
		this.attachmentThumbnail = attachmentThumbnail;
	}

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	public Metadata getMetadata() {
		return metadata;
	}

	public void setMetadata(Metadata metadata) {
		this.metadata = metadata;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		User user = (User) o;
		return isExpired == user.isExpired && Objects.equals(id, user.id) && Objects.equals(login, user.login) && Objects.equals(password,
				user.password
		) && Objects.equals(email, user.email) && role == user.role && Objects.equals(fullName, user.fullName) && Objects.equals(metadata,
				user.metadata
		) && Objects.equals(attachment, user.attachment) && Objects.equals(attachmentThumbnail, user.attachmentThumbnail)
				&& userType == user.userType;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, login, password, email, role, fullName, isExpired, metadata, attachment, attachmentThumbnail, userType);
	}

}
