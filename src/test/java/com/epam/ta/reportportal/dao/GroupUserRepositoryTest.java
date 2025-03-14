package com.epam.ta.reportportal.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.epam.ta.reportportal.BaseTest;
import com.epam.ta.reportportal.entity.group.Group;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;

@Sql("/db/fill/group/group-fill.sql")
class GroupUserRepositoryTest extends BaseTest {

  @Autowired
  private GroupUserRepository groupUserRepository;
  @Autowired
  private GroupRepository groupRepository;

  private Group rebel;

  @BeforeEach
  void setUp() {
    rebel = groupRepository.findBySlug("rebel-group")
            .orElseThrow(() -> new RuntimeException("Group not found"));
  }

  @Test
  void shouldFindAllUsersByGroupId() {
    var groupUsers = groupUserRepository.findAllByGroupId(rebel.getId());
    assertNotNull(groupUsers);
    assertEquals(2, groupUsers.size());
  }

  @Test
  void shouldFindUsersPageByGroupId() {
    var pageable = PageRequest.of(0, 10);
    var groupUsers = groupUserRepository.findAllByGroupId(rebel.getId(), pageable);
    assertNotNull(groupUsers);
    assertEquals(2, groupUsers.getTotalElements());
  }
}