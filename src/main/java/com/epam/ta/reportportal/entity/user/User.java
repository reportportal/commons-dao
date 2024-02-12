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
import com.epam.ta.reportportal.entity.organization.OrganizationUser;
import com.google.common.collect.Sets;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

/**
 * @author Andrei Varabyeu
 */
@Entity
@TypeDef(name = "meta", typeClass = Metadata.class)
@Table(name = "users", schema = "public")
@Getter
@Setter
@NoArgsConstructor
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

  @OneToMany(fetch = FetchType.EAGER, mappedBy = "user", cascade = {CascadeType.PERSIST,
      CascadeType.MERGE, CascadeType.REFRESH})
  private Set<ProjectUser> projects = Sets.newHashSet();

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
  private Set<OrganizationUser> organizationUser = Sets.newHashSet();


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    User user = (User) o;
    return isExpired == user.isExpired
        && Objects.equals(id, user.id)
        && Objects.equals(login, user.login)
        && Objects.equals(password, user.password)
        && Objects.equals(email, user.email)
        && role == user.role
        && Objects.equals(fullName, user.fullName)
        && Objects.equals(metadata, user.metadata)
        && Objects.equals(attachment, user.attachment)
        && Objects.equals(attachmentThumbnail, user.attachmentThumbnail)
        && userType == user.userType;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, login, password, email, role, fullName, isExpired, metadata, attachment,
        attachmentThumbnail, userType);
  }
}
