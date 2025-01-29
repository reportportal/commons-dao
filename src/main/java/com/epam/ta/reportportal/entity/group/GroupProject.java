package com.epam.ta.reportportal.entity.group;

import com.epam.ta.reportportal.entity.project.ProjectRole;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "groups_projects", schema = "public")
public class GroupProject {
  @EmbeddedId
  private GroupProjectId id;

  @Column(name = "project_role")
  private ProjectRole projectRole;

  @Column (name = "created_at", updatable = false)
  private LocalDateTime createdAt;

  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  public GroupProject() {}

  public GroupProject(Long groupId, Long projectId, ProjectRole projectRole) {
    this.id = new GroupProjectId(groupId, projectId);
    this.projectRole = projectRole;
  }
}
