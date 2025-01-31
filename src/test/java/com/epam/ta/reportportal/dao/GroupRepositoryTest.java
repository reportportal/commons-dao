package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.BaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class GroupRepositoryTest extends BaseTest {
  @Autowired
  private GroupRepository groupRepository;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private ProjectRepository projectRepository;
  @Autowired
  private GroupUserRepository groupUserRepository;
  @Autowired
  private GroupProjectRepository groupProjectRepository;

  @Test
  void shouldAssignUserToGroup() {
  }

  @Test
  void shouldAssignProjectToGroup() {
  }

  @Test
  void shouldReturnUserProjectRoles() {
  }

  @Test
  void shouldReturnUserProjectDetails() {
  }
}