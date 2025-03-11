/*
 * Copyright 2025 EPAM Systems
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

package com.epam.ta.reportportal.entity.group;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * Group entity.
 *
 * @author <a href="mailto:Reingold_Shekhtel@epam.com">Reingold Shekhtel</a>
 * @see GroupUser
 * @see GroupProject
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "groups", schema = "public")
public class Group implements Serializable {

  @Serial
  private static final long serialVersionUID = 1823423444;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "slug")
  private String slug;

  @Column(name = "name")
  private String name;

  @Column(name = "created_by")
  private Long createdBy;

  @Column(name = "created_at", updatable = false)
  private Instant createdAt;

  @Column(name = "updated_at")
  private Instant updatedAt;

  @OneToMany(mappedBy = "group", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<GroupUser> users;

  @OneToMany(mappedBy = "group", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<GroupProject> projects;

  /**
   * Constructor for creating a new group.
   *
   * @param name      Group name
   * @param slug      Group slug
   * @param createdBy User ID who created the group
   */
  public Group(String name, String slug, Long createdBy) {
    this.name = name;
    this.slug = slug;
    this.createdBy = createdBy;
  }

  /**
   * Set the created_at and updated_at fields before persisting the entity.
   */
  @PrePersist
  protected void onCreate() {
    this.createdAt = Instant.now();
    this.updatedAt = this.createdAt;
  }

  /**
   * Updates the updated_at field before updating the entity.
   */
  @PreUpdate
  protected void onUpdated() {
    this.updatedAt = Instant.now();
  }
}