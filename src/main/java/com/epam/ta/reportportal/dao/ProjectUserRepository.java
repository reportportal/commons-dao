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

import com.epam.ta.reportportal.entity.user.ProjectUser;
import com.epam.ta.reportportal.entity.user.ProjectUserId;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author Pavel Bortnik
 */
public interface ProjectUserRepository extends ReportPortalRepository<ProjectUser, ProjectUserId>,
    ProjectUserRepositoryCustom {

  @Query(value = "SELECT pu.project_id FROM project_user pu WHERE pu.user_id = :userId", nativeQuery = true)
  List<Long> findProjectIdsByUserId(@Param("userId") Long userId);

  Optional<ProjectUser> findProjectUserByUserIdAndProjectId(Long userId, Long projectId);
}
