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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.epam.ta.reportportal.BaseTest;
import com.epam.ta.reportportal.entity.group.Group;
import com.epam.ta.reportportal.entity.group.GroupProject;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;

/**
 * Test class for {@link GroupProjectRepository}.
 *
 * @author <a href="mailto:Reingold_Shekhtel@epam.com">Reingold Shekhtel</a>
 */
@Sql("/db/fill/group/group-fill.sql")
class GroupProjectRepositoryTest extends BaseTest {

  @Autowired
  private GroupProjectRepository groupProjectRepository;
  @Autowired
  private GroupRepository groupRepository;

  private Group rebel;

  @BeforeEach
  void setUp() {
    rebel = groupRepository.findBySlug("rebel-group")
        .orElseThrow(() -> new RuntimeException("Group not found"));
  }

  @Test
  void ShouldFindAllGroupProjects() {
    List<GroupProject> groupProjects = groupProjectRepository.findAllByGroupId(rebel.getId());
    assertEquals(1, groupProjects.size());
  }

  @Test
  void shouldFindProjectsPageByGroupId() {
    var pageable = PageRequest.of(0, 10);
    Page<GroupProject> groupProjects = groupProjectRepository.findAllByGroupId(rebel.getId(), pageable);
    assertNotNull(groupProjects);
    assertEquals(1, groupProjects.getTotalElements());
  }
}