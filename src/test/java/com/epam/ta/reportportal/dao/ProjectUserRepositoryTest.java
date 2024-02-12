package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.BaseTest;
import com.epam.ta.reportportal.commons.ReportPortalUser;
import com.epam.ta.reportportal.entity.project.ProjectRole;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class ProjectUserRepositoryTest extends BaseTest {

  @Autowired
  private ProjectUserRepository projectUserRepository;


  @Test
  void shouldFindDetailsByUserIdAndProjectKey() {

    final String projectKey = "superadmin_personal";
    final Optional<ReportPortalUser.ProjectDetails> projectDetails = projectUserRepository.findDetailsByUserIdAndProjectKey(
        1L,
        projectKey
    );

    Assertions.assertTrue(projectDetails.isPresent());

    Assertions.assertEquals(projectKey, projectDetails.get().getProjectName());
    Assertions.assertEquals(1L, projectDetails.get().getProjectId());
    Assertions.assertEquals(ProjectRole.PROJECT_MANAGER, projectDetails.get().getProjectRole());
  }

  @Test
  void shouldNotFindDetailsByUserIdAndProjectKeyWhenNotExists() {

    final String projectKey = "falcon-key";
    final Optional<ReportPortalUser.ProjectDetails> projectDetails = projectUserRepository.findDetailsByUserIdAndProjectKey(
        2L,
        projectKey
    );

    Assertions.assertFalse(projectDetails.isPresent());
  }
}
