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

package com.epam.ta.reportportal.entity.project;

import com.epam.ta.reportportal.entity.Metadata;
import com.epam.ta.reportportal.entity.enums.ProjectType;
import com.epam.ta.reportportal.entity.integration.Integration;
import com.epam.ta.reportportal.entity.pattern.PatternTemplate;
import com.epam.ta.reportportal.entity.project.email.SenderCase;
import com.epam.ta.reportportal.entity.user.ProjectUser;
import com.google.common.collect.Sets;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

/**
 * @author Ivan Budayeu
 */
@Entity
@TypeDef(name = "json", typeClass = Metadata.class)
@Table(name = "project", schema = "public")
@Getter
@Setter
@NoArgsConstructor
public class Project implements Serializable {

  private static final long serialVersionUID = -263516611;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", unique = true, nullable = false, precision = 64)
  private Long id;

  @Column(name = "name")
  private String name;

  @Column(name = "project_type")
  private ProjectType projectType;

  @OneToMany(mappedBy = "project", cascade = {CascadeType.PERSIST}, fetch = FetchType.LAZY)
  @OrderBy("creationDate desc")
  private Set<Integration> integrations = Sets.newHashSet();

  @OneToMany(mappedBy = "project", cascade = {CascadeType.PERSIST}, fetch = FetchType.LAZY)
  private Set<ProjectAttribute> projectAttributes = Sets.newHashSet();

  @OneToMany(mappedBy = "project", cascade = {CascadeType.PERSIST,
      CascadeType.MERGE, CascadeType.REMOVE}, fetch = FetchType.LAZY)
  @OrderBy(value = "issue_type_id")
  private Set<ProjectIssueType> projectIssueTypes = Sets.newHashSet();

  @OneToMany(mappedBy = "project", cascade = {
      CascadeType.PERSIST}, fetch = FetchType.EAGER, orphanRemoval = true)
  private Set<SenderCase> senderCases = Sets.newHashSet();

  @Column(name = "creation_date")
  private Date creationDate;

  @Type(type = "json")
  @Column(name = "metadata")
  private Metadata metadata;

  // TODO: rename to meaningful variable. eg. orgSlug, orgKey or else
  @Column(name = "organization")
  private String org;

  @Column(name = "organization_id", nullable = false)
  private Long organizationId;

  @Column(name = "key")
  private String key;

  @Column(name = "slug")
  private String slug;

  @Column(name = "allocated_storage", updatable = false)
  private long allocatedStorage;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "project", cascade = CascadeType.PERSIST)
  private Set<ProjectUser> users = Sets.newHashSet();

  @OneToMany(fetch = FetchType.EAGER, orphanRemoval = true)
  @JoinColumn(name = "project_id", updatable = false)
  @OrderBy
  private Set<PatternTemplate> patternTemplates = Sets.newHashSet();

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Project project = (Project) o;
    return Objects.equals(name, project.name)
        && Objects.equals(key, project.key)
        && Objects.equals(organizationId, project.organizationId)
        && Objects.equals(allocatedStorage, project.allocatedStorage)
        && Objects.equals(creationDate, project.creationDate)
        && Objects.equals(metadata, project.metadata);
  }

  @Override
  public int hashCode() {

    return Objects.hash(name, creationDate, metadata, allocatedStorage);
  }

}
