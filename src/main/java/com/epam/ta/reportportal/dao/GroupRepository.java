package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.entity.group.Group;
import com.epam.ta.reportportal.entity.project.ProjectRole;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GroupRepository extends ReportPortalRepository<Group, Long> {
  @Query(value = "SELECT DISTINCT gp.project_role "
    + "FROM groups_projects gp "
      + "JOIN groups_users gu "
        + "ON gp.group_id = gu.group_id "
          + "AND gu.user_id = :userId  "
          + "AND gp.project_id = :projectId"
    , nativeQuery = true
  )
  List<ProjectRole> getUserProjectRoles(
    @Param("userId") Long userId,
    @Param("projectId") Long projectId
  );

  @Query(value = "SELECT DISTINCT gp.project_role "
      + "FROM groups_projects gp "
        + "JOIN groups_users gu "
          + "ON gp.group_id = gu.group_id "
            + "AND gu.user_id = :userId "
      + "WHERE gp.project_id = ("
        + "SELECT p.id "
        + "FROM project p "
        + "WHERE p.name = :projectName)",
    nativeQuery = true
  )
  List<ProjectRole> getUserProjectRoles(Long userId, String projectName);
}