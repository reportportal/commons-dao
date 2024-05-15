package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.BaseTest;
import com.epam.ta.reportportal.entity.organization.MembershipDetails;
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
    final Optional<MembershipDetails> membershipDetails =
        projectUserRepository.findDetailsByUserIdAndProjectKey(1L, projectKey);

    Assertions.assertTrue(membershipDetails.isPresent());
    Assertions.assertNotNull(membershipDetails.get().getOrgId());

    Assertions.assertEquals(projectKey, membershipDetails.get().getProjectName());
    Assertions.assertEquals(1L, membershipDetails.get().getProjectId());
    Assertions.assertEquals(ProjectRole.EDITOR, membershipDetails.get().getProjectRole());
  }

  @Test
  void shouldNotFindDetailsByUserIdAndProjectKeyWhenNotExists() {

    final String projectKey = "falcon-key";
    final Optional<MembershipDetails> projectDetails =
        projectUserRepository.findDetailsByUserIdAndProjectKey(2L, projectKey);

    Assertions.assertFalse(projectDetails.isPresent());
  }
}
