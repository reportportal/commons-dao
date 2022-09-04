package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.BaseTest;
import com.epam.ta.reportportal.commons.ReportPortalUser;
import com.epam.ta.reportportal.entity.project.ProjectRole;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class ProjectUserRepositoryTest extends BaseTest {

	@Autowired
	private ProjectUserRepository projectUserRepository;


	@Test
	void shouldFindDetailsByUserIdAndProjectKey() {

		final String projectKey = "superadmin_personal";
		final Optional<ReportPortalUser.ProjectDetails> projectDetails = projectUserRepository.findDetailsByUserIdAndProjectKey(1L,
				projectKey
		);

		assertTrue(projectDetails.isPresent());

		assertEquals(projectKey, projectDetails.get().getProjectKey());
		assertEquals(1L, projectDetails.get().getProjectId());
		assertEquals(ProjectRole.PROJECT_MANAGER, projectDetails.get().getProjectRole());
	}

	@Test
	void shouldNotFindDetailsByUserIdAndProjectKeyWhenNotExists() {

		final String projectKey = "superadmin_personal";
		final Optional<ReportPortalUser.ProjectDetails> projectDetails = projectUserRepository.findDetailsByUserIdAndProjectKey(2L,
				projectKey
		);

		assertFalse(projectDetails.isPresent());
	}
}