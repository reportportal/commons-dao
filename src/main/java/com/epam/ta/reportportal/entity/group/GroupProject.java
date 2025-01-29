package com.epam.ta.reportportal.entity.group;

import com.epam.ta.reportportal.entity.project.Project;
import com.epam.ta.reportportal.entity.project.ProjectRole;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "groups_projects", schema = "public")
public class GroupProject {
  @EmbeddedId
  private GroupProjectId id;

  @Enumerated(EnumType.STRING)
  @Column(name = "project_role")
  private ProjectRole projectRole;

  @Column (name = "created_at", updatable = false)
  private LocalDateTime createdAt;

  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("groupId")
  private Group group;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("projectId")
  private Project project;

  public GroupProject(Long groupId, Long projectId, ProjectRole projectRole) {
    this.id = new GroupProjectId(groupId, projectId);
    this.projectRole = projectRole;
  }
}
