package com.epam.ta.reportportal.entity.group;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class GroupUserId implements Serializable {
  @Column(name = "group_id")
  private Long groupId;
  @Column(name = "user_id")
  private Long userId;

  public GroupUserId() {}

  public GroupUserId(Long groupId, Long userId) {
    this.groupId = groupId;
    this.userId = userId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    GroupUserId that = (GroupUserId) o;
    return Objects.equals(groupId, that.groupId) && Objects.equals(userId, that.userId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(groupId, userId);
  }
}