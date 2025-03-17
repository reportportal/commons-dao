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