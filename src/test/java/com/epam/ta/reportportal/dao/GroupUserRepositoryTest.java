package com.epam.ta.reportportal.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.epam.ta.reportportal.BaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;

@Sql("/db/fill/group/group-fill.sql")
class GroupUserRepositoryTest extends BaseTest {

  @Autowired
  private GroupUserRepository groupUserRepository;

  @Test
  void testFindAllUsersByGroupId() {
    var groupUsers = groupUserRepository.findAllByGroupId(1L);
    assertNotNull(groupUsers);
    assertEquals(2, groupUsers.size());
  }

  @Test
  void testFindUsersPageByGroupId() {
    var pageable = PageRequest.of(0, 10);
    var groupUsers = groupUserRepository.findAllByGroupId(1L, pageable);
    assertNotNull(groupUsers);
    assertEquals(2, groupUsers.getTotalElements());
  }
}