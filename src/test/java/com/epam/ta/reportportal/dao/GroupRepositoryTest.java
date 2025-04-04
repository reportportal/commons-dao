package com.epam.ta.reportportal.dao;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.epam.ta.reportportal.BaseTest;
import com.epam.ta.reportportal.entity.group.Group;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

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
}
