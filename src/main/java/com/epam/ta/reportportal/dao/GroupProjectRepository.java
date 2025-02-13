package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.commons.ReportPortalUser;
import com.epam.ta.reportportal.entity.group.GroupProject;
import com.epam.ta.reportportal.entity.group.GroupProjectId;
import com.epam.ta.reportportal.entity.group.dto.GroupProjectDetailsRecord;
import com.epam.ta.reportportal.entity.project.ProjectRole;
import java.util.List;
import java.util.Optional;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GroupProjectRepository extends
    ReportPortalRepository<GroupProject, GroupProjectId> {

  /**
   * @param userId    user id
   * @param projectId project id
   * @return all roles of the user in the project via group membership {@link List<ProjectRole>}
   */
  @Query(value = """
      SELECT DISTINCT gp.project_role
      FROM groups_projects gp
      JOIN groups_users gu
        ON gp.group_id = gu.group_id
          AND gu.user_id = :userId
      WHERE project_id = :projectId
      """,
      nativeQuery = true
  )
  @Cacheable(value = "groupUserProjectRolesCache", key = "#userId + '_' + #projectId", cacheManager = "caffeineCacheManager")
  List<ProjectRole> findUserProjectRoles(
      @Param("userId") Long userId,
      @Param("projectId") Long projectId
  );

  /**
   * @param userId      user id
   * @param projectName project name
   * @return project details record of the user in the project via group membership
   * {@link GroupProjectDetailsRecord}
   */

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
  Optional<GroupProjectDetailsRecord> findProjectDetailsRaw(
      @Param("userId") Long userId,
      @Param("projectName") String projectName
  );

  @Cacheable(value = "groupProjectDetailsCache", key = "#userId + '_' + #projectName", cacheManager = "caffeineCacheManager")
  default Optional<ReportPortalUser.ProjectDetails> findProjectDetails(Long userId,
      String projectName) {
    return findProjectDetailsRaw(userId, projectName)
        .map(record -> new ReportPortalUser.ProjectDetails(
            record.projectId(),
            record.projectName(),
            record.projectRoles()
        ));
  }

  /**
   * @param userId user id
   * @return all projects of the user via group membership {@link List<GroupProject>}
   */
  @Query(value = """
      SELECT gp.project_id, gp.group_id, gp.project_role, gp.created_at, gp.updated_at
      FROM groups_projects gp
      JOIN groups_users gu
        ON gp.group_id = gu.group_id
      WHERE gu.user_id = :user_id
      """,
      nativeQuery = true
  )
  List<GroupProject> findAllUserProjects(@Param("user_id") Long userId);
}