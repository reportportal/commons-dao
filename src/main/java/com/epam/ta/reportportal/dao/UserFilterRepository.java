/*
 * Copyright 2019 EPAM Systems
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

import com.epam.ta.reportportal.entity.filter.UserFilter;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author Pavel Bortnik
 */
public interface UserFilterRepository extends ReportPortalRepository<UserFilter, Long>,
    UserFilterRepositoryCustom {

  /**
   * Finds filter by 'id' and 'project id'
   *
   * @param id        {@link UserFilter#id}
   * @param projectId Id of the {@link com.epam.ta.reportportal.entity.project.Project} whose filter will be extracted
   * @return {@link UserFilter} wrapped in the {@link Optional}
   */
  Optional<UserFilter> findByIdAndProjectId(Long id, Long projectId);

  /**
   * @param ids       {@link Iterable} of the filter Ids
   * @param projectId Id of the {@link com.epam.ta.reportportal.entity.project.Project} whose filters will be extracted
   * @return The {@link List} of the {@link UserFilter}
   */
  List<UserFilter> findAllByIdInAndProjectId(Collection<Long> ids, Long projectId);

  /**
   * @param projectId Id of the {@link com.epam.ta.reportportal.entity.project.Project} whose filters will be extracted
   * @return The {@link List} of the {@link UserFilter}
   */
  List<UserFilter> findAllByProjectId(Long projectId);

  /**
   * Checks the existence of the {@link UserFilter} with specified name for a user on a project
   *
   * @param name      {@link UserFilter#name}
   * @param owner     {@link UserFilter#owner}
   * @param projectId Id of the {@link com.epam.ta.reportportal.entity.project.Project} on which filter existence will
   *                  be checked
   * @return if exists 'true' else 'false'
   */
  boolean existsByNameAndOwnerAndProjectId(String name, String owner, Long projectId);

  /**
   * Checks the existence of the {@link UserFilter} with specified name on a project
   *
   * @param name      {@link UserFilter#name}
   * @param projectId Id of the {@link com.epam.ta.reportportal.entity.project.Project} on which filter existence will
   *                  be checked
   * @return if exists 'true' else 'false'
   */
  boolean existsByNameAndProjectId(String name, Long projectId);

  /**
   * Finds locked dashboards that use the specified filter. Returns dashboard names.
   *
   * @param filterId  The filter ID
   * @param projectId The project ID to filter dashboards
   * @return List of dashboard names
   */
  @Query(value = """
      SELECT d.name
      FROM dashboard d
      INNER JOIN owned_entity oe ON d.id = oe.id
      WHERE oe.locked = true
        AND oe.project_id = :projectId
        AND d.id IN (
          SELECT dw.dashboard_id
          FROM dashboard_widget dw
          INNER JOIN widget_filter wf ON dw.widget_id = wf.widget_id
          WHERE wf.filter_id = :filterId
        )
      """, nativeQuery = true)
  List<String> findLockedDashboardsByFilterId(@Param("filterId") Long filterId, @Param("projectId") Long projectId);
}
