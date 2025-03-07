/*
 * Copyright 2025 EPAM Systems
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.commons.ReportPortalUser;
import com.epam.ta.reportportal.entity.group.GroupProject;
import com.epam.ta.reportportal.entity.group.GroupProjectId;
import com.epam.ta.reportportal.entity.group.dto.GroupProjectDetailsRecord;
import com.epam.ta.reportportal.entity.project.ProjectRole;
import java.util.List;
import java.util.Optional;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Repository for {@link GroupProject} and related entities.
 *
 * @author <a href="mailto:Reingold_Shekhtel@epam.com">Reingold Shekhtel</a>
 * @see GroupProject
 * @see ReportPortalUser.ProjectDetails
 * @see ProjectRole
 */
public interface GroupProjectRepository extends
    ReportPortalRepository<GroupProject, GroupProjectId> {

  /**
   * Finds all roles of the user in the project via group membership.
   *
   * @param userId    user id
   * @param projectId project id
   * @return {@link List} of {@link ProjectRole}
   */
  @Query(value = """
      SELECT DISTINCT gp.project_role
      FROM groups_projects gp
      JOIN groups_users gu
      ON gp.group_id = gu.group_id
        AND gu.user_id = :userId
      WHERE gp.project_id = :projectId
      """,
      nativeQuery = true
  )
  @Cacheable(
      value = "groupUserProjectRolesCache",
      key = "#userId + '_' + #projectId",
      cacheManager = "caffeineCacheManager"
  )
  List<ProjectRole> findUserProjectRoles(
      @Param("userId") Long userId,
      @Param("projectId") Long projectId
  );

  /**
   * Finds a raw project details of the user in the project via group membership.
   *
   * @param userId      user id
   * @param projectName project name
   * @return {@link Optional} of {@link GroupProjectDetailsRecord}
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

  /**
   * Finds project details of the user in the project via group membership.
   *
   * @param userId      user id
   * @param projectName project name
   * @return {@link Optional} of {@link ReportPortalUser.ProjectDetails}
   */
  @Cacheable(
      value = "groupProjectDetailsCache",
      key = "#userId + '_' + #projectName",
      cacheManager = "caffeineCacheManager"
  )
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
   * Finds all projects of the user via group membership.
   *
   * @param userId user id
   * @return {@link List} of {@link GroupProject}
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

  /**
   * Finds all projects of the Group.
   *
   * @param groupId user id
   * @return {@link List} of {@link GroupProject}
   */
  List<GroupProject> findAllByGroupId(Long groupId);

  /**
   * Finds all projects of the Group with pagination.
   *
   * @param groupId   user id
   * @param pageable  {@link Pageable}
   * @return {@link Page} of {@link GroupProject}
   */
  Page<GroupProject> findAllByGroupId(Long groupId, Pageable pageable);
}