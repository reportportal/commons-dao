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

import com.epam.ta.reportportal.dao.converters.JpaInstantConverter;
import com.epam.ta.reportportal.entity.Metadata;
import com.epam.ta.reportportal.entity.Modifiable;
import java.io.Serializable;
import java.time.Instant;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * @author Ivan Budaev
 */
@Entity
@Table(name = "user_creation_bid")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class UserCreationBid implements Serializable, Modifiable {

  @Id
  @Column(name = "uuid")
  private String uuid;

  @LastModifiedDate
  @Column(name = LAST_MODIFIED)
  @Convert(converter = JpaInstantConverter.class)
  private Instant lastModified;

  @Column(name = "email")
  private String email;

	@Column(name = "project_name")
	private String projectName;

  @Column(name = "role")
  private String role;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "inviting_user_id")
  private User invitingUser;

	@Type(Metadata.class)
	@Column(name = "metadata")
	private Metadata metadata;

  public String getUuid() {
    return uuid;
  }

  public void setUuid(String uuid) {
    this.uuid = uuid;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getProjectName() {
    return projectName;
  }

  public void setProjectName(String projectName) {
    this.projectName = projectName;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  @Override
  public Instant getLastModified() {
    return lastModified;
  }

	public void setLastModified(Instant lastModified) {
		this.lastModified = lastModified;
	}

	public Metadata getMetadata() {
		return metadata;
	}

	public void setMetadata(Metadata metadata) {
		this.metadata = metadata;
	}

  public User getInvitingUser() {
    return invitingUser;
  }

  public void setInvitingUser(User invitingUser) {
    this.invitingUser = invitingUser;
  }
}
