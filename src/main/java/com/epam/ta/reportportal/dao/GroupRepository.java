package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.commons.ReportPortalUser;
import com.epam.ta.reportportal.entity.group.Group;
import com.epam.ta.reportportal.entity.project.ProjectRole;
import java.util.List;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GroupRepository extends ReportPortalRepository<Group, Long>{

  @Query(value = """
      SELECT DISTINCT gp.project_role
      FROM groups_projects gp
      JOIN groups_users gu
        ON gp.group_id = gu.group_id
            AND gu.user_id = :userId
            AND gp.project_id = :projectId
      """,
      nativeQuery = true
  )
  List<ProjectRole> getUserProjectRoles(
      @Param("userId") Long userId,
      @Param("projectId") Long projectId
  );

  @Query(value = """
        SELECT p.id AS projectId,
          p.name AS projectName,
          array_agg(gp.project_role) AS projectRole
        FROM groups_projects gp
        JOIN groups_users gu
          ON gp.group_id = gu.group_id
            AND gu.user_id = :userId
        JOIN Project p
          ON gp.project_id = p.id
            AND p.name = :projectName group by p.id
        """,
      nativeQuery = true
  )
  Optional<ReportPortalUser.ProjectDetailsMapper> getProjectDetailsRaw(
      @Param("userId") Long userId,
      @Param("projectName") String projectName
  );

  default Optional<ReportPortalUser.ProjectDetails> getProjectDetails(
      @NotNull Long userId,
      @NotNull String projectName
  ) {
    return getProjectDetailsRaw(userId, projectName)
        .map(ReportPortalUser.ProjectDetailsMapper::toProjectDetails);
  }

  Group findBySlug(String slug);
}