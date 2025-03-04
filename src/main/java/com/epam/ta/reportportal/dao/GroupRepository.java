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
import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
   * Retrieves a page of groups.
   *
   * @param pageable {@link Pageable}
   * @return {@link Page} of {@link Group}
   */
  @NotNull Page<Group> findAll(@NotNull Pageable pageable);
}