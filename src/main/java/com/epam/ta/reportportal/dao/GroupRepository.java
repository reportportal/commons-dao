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

import com.epam.ta.reportportal.entity.group.Group;
import com.epam.ta.reportportal.entity.group.dto.GroupSummaryDto;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

/**
 * Repository for {@link Group}.
 *
 * @see Group
 *
 * @author <a href="mailto:Reingold_Shekhtel@epam.com">Reingold Shekhtel</a>
 */
public interface GroupRepository extends ReportPortalRepository<Group, Long> {

  /**
   * Retrieves a group by its slug.
   *
   * @param slug group slug
   * @return {@link Optional} of {@link Group}
   */
  Optional<Group> findBySlug(String slug);

  /**
   * Retrieves a group by its UUID.
   *
   * @param uuid group UUID
   * @return {@link Optional} of {@link Group}
   */
  Optional<Group> findByUuid(UUID uuid);


  /**
   * Retrieves all group's summaries.
   *
   * @param pageable a {@link Pageable} object for pagination
   * @return {@link Page} of {@link GroupSummaryDto}
   */
  @Query("""
      SELECT new com.epam.ta.reportportal.entity.group.dto.GroupSummaryDto(
        g.id, g.uuid, g.slug, g.name, g.createdBy, g.createdAt, g.updatedAt,
        COUNT(DISTINCT u), COUNT(DISTINCT p)
      )
      FROM Group g
      LEFT JOIN g.users u
      LEFT JOIN g.projects p
      GROUP BY g.id, g.uuid, g.slug, g.name, g.createdBy, g.createdAt, g.updatedAt
      """)
  Page<GroupSummaryDto> findAllWithSummary(Pageable pageable);
}