package com.epam.ta.reportportal.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.epam.ta.reportportal.BaseTest;
import com.epam.ta.reportportal.commons.ReportPortalUser;
import com.epam.ta.reportportal.entity.group.Group;
import com.epam.ta.reportportal.entity.project.Project;
import com.epam.ta.reportportal.entity.project.ProjectRole;
import com.epam.ta.reportportal.entity.user.User;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

@Sql("/db/fill/group/group-fill.sql")
class GroupRepositoryTest extends BaseTest {

  @Autowired
  private GroupRepository groupRepository;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private ProjectRepository projectRepository;

  private Group rebelGroup;
  private User hanSolo;
  private Project falcon;

  @BeforeEach
  void setUp() {
    rebelGroup = groupRepository.findBySlug("rebel-group");
    hanSolo = userRepository.findByLogin("han_solo")
        .orElseThrow(() -> new RuntimeException("User not found"));
    falcon = projectRepository.findByName("millennium_falcon")
        .orElseThrow(() -> new RuntimeException("Project not found"));
  }

  @Test
  void shouldAssignUserToGroup() {
    assertNotNull(rebelGroup.getUsers());
  }

  @Test
  void shouldAssignProjectToGroup() {
    assertNotNull(rebelGroup.getProjects());
  }

  @Test
  void shouldReturnUserProjectRoles() {
    List<ProjectRole> projectRoles = groupRepository.getUserProjectRoles(
        hanSolo.getId(),
        falcon.getId()
    );
    assertNotNull(projectRoles);
  }

  @Test
  void shouldReturnUserProjectDetails() {
    Long userId = hanSolo.getId();
    String projectName = falcon.getName();

    Optional<ReportPortalUser.ProjectDetails> projectDetails = groupRepository
        .getUserProjectDetails(userId, projectName);
    assertTrue(projectDetails.isPresent());
  }
}