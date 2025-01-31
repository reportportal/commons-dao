package com.epam.ta.reportportal.entity.group;

import com.epam.ta.reportportal.entity.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "groups_users", schema = "public")
public class GroupUser implements Serializable {
  @EmbeddedId
  private GroupUserId id;

  @Column(name = "created_at", updatable = false)
  private Instant createdAt;

  @Column(name = "updated_at")
  private Instant updatedAt;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("groupId")
  private Group group;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("userId")
  private User user;

  public GroupUser(Long groupId, Long userId) {
    this.id = new GroupUserId(groupId, userId);
  }
}
