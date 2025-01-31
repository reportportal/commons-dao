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
@Table(name = "groups_projects", schema = "public")
public class GroupProject {
  @EmbeddedId
  private GroupProjectId id;

  @Enumerated(EnumType.STRING)
  @Column(name = "project_role")
  private ProjectRole projectRole;

  @Column (name = "created_at", updatable = false)
  private Instant createdAt;

  @Column(name = "updated_at")
  private Instant updatedAt;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("groupId")
  private Group group;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("projectId")
  private Project project;
}
