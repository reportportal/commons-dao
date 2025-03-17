package com.epam.ta.reportportal.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.epam.ta.reportportal.BaseTest;
import com.epam.ta.reportportal.commons.ReportPortalUser;
import com.epam.ta.reportportal.entity.group.GroupProject;
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
class GroupMembershipRepositoryTest extends BaseTest {

  @Autowired
  private CacheManager cacheManager;
  @Autowired
  private GroupMembershipRepository groupMembershipRepository;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private ProjectRepository projectRepository;

  private User fakeChubaka;
  private Project falcon;

  @BeforeEach
  void setUp() {
    fakeChubaka = userRepository.findByLogin("fake_chubaka")
        .orElseThrow(() -> new RuntimeException("User not found"));
    falcon = projectRepository.findByName("millennium_falcon")
        .orElseThrow(() -> new RuntimeException("Project not found"));
  }

  @Test
  void shouldReturnUserGroupProjectRoles() {
    final List<ProjectRole> projectRoles = groupMembershipRepository.findUserProjectRoles(
        fakeChubaka.getId(),
        falcon.getId()
    );
    assertEquals(2, projectRoles.size());
  }

  @Test
  void shouldGetMaxUserProjectRole() {
    final List<ProjectRole> projectRoles = groupMembershipRepository.findUserProjectRoles(
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

    final Optional<ReportPortalUser.ProjectDetails> projectDetails = groupMembershipRepository
        .findProjectDetails(userId, projectName);

    assertTrue(projectDetails.isPresent());
    assertEquals(falcon.getId(), projectDetails.get().getProjectId());
    assertEquals(ProjectRole.MEMBER, projectDetails.get().getProjectRole());
  }

  @Test
  void shouldReturnProjectDetailsByProjectId() {

    final Optional<ReportPortalUser.ProjectDetails> projectDetails = groupMembershipRepository
        .findProjectDetails(fakeChubaka.getId(), falcon.getId());

    assertTrue(projectDetails.isPresent());
    assertEquals(falcon.getId(), projectDetails.get().getProjectId());
    assertEquals(ProjectRole.MEMBER, projectDetails.get().getProjectRole());
  }

  @Test
  void ShouldCacheUserProjectRoles() {
    final List<ProjectRole> projectRoles = groupMembershipRepository.findUserProjectRoles(
        fakeChubaka.getId(),
        falcon.getId()
    );
    assertNotNull(projectRoles);

    Cache cache = cacheManager.getCache("groupUserProjectRolesCache");
    assertNotNull(cache);

    Cache.ValueWrapper valueWrapper = cache.get(fakeChubaka.getId() + "_" + falcon.getId());
    assertNotNull(valueWrapper);
  }

  @Test
  void ShouldCacheProjectDetails() {
    final Long userId = fakeChubaka.getId();
    final String projectName = falcon.getName();
    final Optional<ReportPortalUser.ProjectDetails> projectDetails = groupMembershipRepository.findProjectDetails(
        userId,
        projectName
    );
    assertTrue(projectDetails.isPresent());
    Cache cache = cacheManager.getCache("groupProjectDetailsCache");
    assertNotNull(cache);
    Cache.ValueWrapper valueWrapper = cache.get(userId + "_" + projectName);
    assertNotNull(valueWrapper);
  }

  @Test
  void ShouldReturnAllUserProjects() {
    List<GroupProject> groupProjects = groupMembershipRepository.findAllUserProjects(
        fakeChubaka.getId());
    assertEquals(4, groupProjects.size());
  }
}