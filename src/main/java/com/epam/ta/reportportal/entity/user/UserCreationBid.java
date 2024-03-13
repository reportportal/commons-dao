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
import com.epam.ta.reportportal.entity.Modifiable;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * @author Ivan Budaev
 */
@Entity
@Table(name = "user_creation_bid")
@TypeDef(name = "json", typeClass = Metadata.class)
@EntityListeners(AuditingEntityListener.class)
public class UserCreationBid implements Serializable, Modifiable {

  @Id
  @Column(name = "uuid")
  private String uuid;

  @LastModifiedDate
  @Column(name = LAST_MODIFIED)
  private Date lastModified;

  @Column(name = "email")
  private String email;

	@Column(name = "project_name")
	private String projectName;

  @Column(name = "role")
  private String role;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "inviting_user_id")
  private User invitingUser;

	@Type(type = "json")
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
  public Date getLastModified() {
    return lastModified;
  }

	public void setLastModified(Date lastModified) {
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
