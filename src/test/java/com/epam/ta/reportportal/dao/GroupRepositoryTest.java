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
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.jdbc.Sql;

@Sql("/db/fill/group/group-fill.sql")
class GroupRepositoryTest extends BaseTest {

  @Autowired
  private CacheManager cacheManager;
  @Autowired
  private GroupRepository groupRepository;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private ProjectRepository projectRepository;

  private Group rebelGroup;
  private User fakeChubaka;
  private Project falcon;

  @BeforeEach
  void setUp() {
    rebelGroup = groupRepository.findBySlug("rebel-group");
    fakeChubaka = userRepository.findByLogin("fake_chubaka")
        .orElseThrow(() -> new RuntimeException("User not found"));
    falcon = projectRepository.findByName("millennium_falcon")
        .orElseThrow(() -> new RuntimeException("Project not found"));
  }

  @Test
  void shouldReturnAssignedUsers() {
    assertNotNull(rebelGroup.getUsers());
  }

  @Test
  void shouldReturnAssignedProjects() {
    assertNotNull(rebelGroup.getProjects());
  }

  @Test
  void shouldReturnUserGroupProjectRoles() {
    final List<ProjectRole> projectRoles = groupRepository.getUserProjectRoles(
        fakeChubaka.getId(),
        falcon.getId()
    );
    assertEquals(3, projectRoles.size());
  }

  @Test
  void shouldGetMaxUserProjectRole() {
    final List<ProjectRole> projectRoles = groupRepository.getUserProjectRoles(
        fakeChubaka.getId(),
        falcon.getId()
    );

    ReportPortalUser.ProjectDetails projectDetails = new ReportPortalUser.ProjectDetails(
        falcon.getId(),
        falcon.getName(),
        ProjectRole.OPERATOR
    );

    projectDetails.setHighestRole(projectRoles);

    assertEquals(ProjectRole.MEMBER, projectDetails.getProjectRole());
  }

  @Test
  void shouldReturnUserProjectDetails() {
    Long userId = fakeChubaka.getId();
    String projectName = falcon.getName();

    final Optional<ReportPortalUser.ProjectDetails> projectDetails = groupRepository
        .getProjectDetails(userId, projectName);

    assertTrue(projectDetails.isPresent());
    assertEquals(falcon.getId(), projectDetails.get().getProjectId());
    assertEquals(ProjectRole.MEMBER, projectDetails.get().getProjectRole());
  }

  @Test
  void ShouldCacheUserProjectRoles() {
    final List<ProjectRole> projectRoles = groupRepository.getUserProjectRoles(
        fakeChubaka.getId(),
        falcon.getId()
    );
    assertNotNull(projectRoles);

    Cache cache = cacheManager.getCache("userProjectRolesCache");
    assertNotNull(cache);

    Cache.ValueWrapper valueWrapper = cache.get(fakeChubaka.getId() + "_" + falcon.getId());
    assertNotNull(valueWrapper);
  }

  @Test
  void ShouldCacheProjectDetails() {
    final Long userId = fakeChubaka.getId();
    final String projectName = falcon.getName();
    final Optional<ReportPortalUser.ProjectDetails> projectDetails = groupRepository
        .getProjectDetails(userId, projectName);
    assertTrue(projectDetails.isPresent());

    Cache cache = cacheManager.getCache("projectDetailsCache");
    assertNotNull(cache);
    Cache.ValueWrapper valueWrapper = cache.get(userId + "_" + projectName);
    assertNotNull(valueWrapper);
  }
}