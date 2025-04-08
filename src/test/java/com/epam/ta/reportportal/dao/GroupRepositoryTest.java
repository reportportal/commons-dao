package com.epam.ta.reportportal.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.epam.ta.reportportal.BaseTest;
import com.epam.ta.reportportal.entity.group.Group;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;

@Sql("/db/fill/group/group-fill.sql")
public class GroupRepositoryTest extends BaseTest {

  @Autowired
  private GroupRepository groupRepository;

  @Test
  void testGroupCreation() {
    groupRepository.save(new Group("Test group", "test-group", 1L));
    var group = groupRepository.findBySlug("test-group")
        .orElseThrow(() -> new RuntimeException("Group not found")
        );

    group.setSlug("new-test-group");
    groupRepository.save(group);
    assertTrue(groupRepository.findBySlug("new-test-group").isPresent());
  }

  @Test
  void testFindByUuid() {
    var group = new Group("Test group", "test-group", 1L);
    groupRepository.save(group);

    assertTrue(groupRepository.findByUuid(group.getUuid()).isPresent());
  }

  @Test
  void testFindAllWithStats() {
    var page = PageRequest.ofSize(100);
    var groupSummary = groupRepository.findAllWithStats(page);

    assertFalse(groupSummary.isEmpty());
    assertEquals(2, groupSummary.getContent().getFirst().userCount());
    assertEquals(1, groupSummary.getContent().getFirst().projectCount());
  }

  @Test
  void testFindWithStatsById() {
    var group = groupRepository.findBySlug("rebel-group")
        .orElseThrow(() -> new RuntimeException("Group not found")
        );
    var groupSummary = groupRepository.findWithStatsById(group.getId());

    assertTrue(groupSummary.isPresent());
    assertEquals(2, groupSummary.get().userCount());
    assertEquals(1, groupSummary.get().projectCount());
  }
}
