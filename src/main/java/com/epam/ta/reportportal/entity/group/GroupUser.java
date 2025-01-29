package com.epam.ta.reportportal.entity.group;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "groups_users", schema = "public")
public class GroupUser implements Serializable {
  @EmbeddedId
  private GroupUserId id;

  @Column(name = "created_at", updatable = false)
  private LocalDateTime createdAt;

  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  public GroupUser() {}

  public GroupUser(Long groupId, Long userId) {
    this.id = new GroupUserId(groupId, userId);
  }
}
