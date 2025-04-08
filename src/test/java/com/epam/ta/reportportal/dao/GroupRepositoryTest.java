package com.epam.ta.reportportal.dao;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.epam.ta.reportportal.BaseTest;
import com.epam.ta.reportportal.entity.group.Group;
import java.awt.print.Pageable;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;

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
  void testFindAllWithSummary() {
    var group = new Group("Test group", "test-group", 1L);
    groupRepository.save(group);

    var page = PageRequest.ofSize(100);
    var groupSummary = groupRepository.findAllWithSummary(page);

    assertFalse(groupSummary.isEmpty());
  }
}
