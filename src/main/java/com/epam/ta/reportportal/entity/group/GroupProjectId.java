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
public class GroupProjectId implements Serializable {

  @Column(name = "group_id")
  private Long groupId;
  @Column(name = "project_id")
  private Long projectId;

  public GroupProjectId() {
  }

  public GroupProjectId(Long groupId, Long projectId) {
    this.groupId = groupId;
    this.projectId = projectId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    GroupProjectId that = (GroupProjectId) o;
    return Objects.equals(groupId, that.groupId) && Objects.equals(projectId, that.projectId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(groupId, projectId);
  }
}