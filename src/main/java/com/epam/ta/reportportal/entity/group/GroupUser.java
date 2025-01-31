package com.epam.ta.reportportal.entity.group;

import com.epam.ta.reportportal.entity.user.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "groups_users", schema = "public")
public class GroupUser implements Serializable {
  @EmbeddedId
  private GroupUserId id;

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

  @PreUpdate
  protected void onUpdated() {
    this.updatedAt = Instant.now();
  }
}
