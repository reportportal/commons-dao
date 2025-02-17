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

import com.epam.ta.reportportal.entity.user.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * GroupUser entity.
 *
 * @author <a href="mailto:Reingold_Shekhtel@epam.com">Reingold Shekhtel</a>
 * @see GroupUserId
 * @see Group
 * @see User
 * @see GroupRole
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "groups_users", schema = "public")
public class GroupUser implements Serializable {

  @EmbeddedId
  private GroupUserId id;

  @Enumerated(EnumType.STRING)
  @Column(name = "group_role")
  private GroupRole groupRole;

  @Column(name = "created_at", updatable = false)
  private Instant createdAt;

  @Column(name = "updated_at")
  private Instant updatedAt;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @MapsId("groupId")
  private Group group;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @MapsId("userId")
  private User user;

  /**
   * Constructor for GroupUser entity.
   *
   * @param group     Group
   * @param user      User
   * @param groupRole GroupRole
   */
  public GroupUser(
      @NotNull Group group,
      @NotNull User user,
      GroupRole groupRole
  ) {
    this.id = new GroupUserId(group.getId(), user.getId());
    this.group = group;
    this.user = user;
    this.groupRole = groupRole;
    this.createdAt = Instant.now();
  }

  /**
   * Updates the updated at field.
   */
  @PreUpdate
  protected void onUpdated() {
    this.updatedAt = Instant.now();
  }
}
