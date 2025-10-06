/*
 * Copyright 2019 EPAM Systems
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.entity.log.ProjectLogType;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LogTypeRepository extends ReportPortalRepository<ProjectLogType, Long> {

  List<ProjectLogType> findByProjectId(Long projectId);

  @Query("SELECT COUNT(log.id) > 0 FROM ProjectLogType log " +
      "WHERE log.projectId = :projectId AND (LOWER(log.name) = LOWER(:name) OR log.level = :level)")
  boolean existsByProjectIdAndNameOrLevelIgnoreCase(@Param("projectId") Long projectId,
      @Param("name") String name, @Param("level") Integer level);

  @Query("SELECT COUNT(log.id) FROM ProjectLogType log " +
      "WHERE log.projectId = :projectId AND log.filterable = true")
  long countFilterableLogTypes(@Param("projectId") Long projectId);
}
