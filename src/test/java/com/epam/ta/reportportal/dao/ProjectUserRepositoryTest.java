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
	void shouldFindDetailsByUserIdAndProjectName() {

		final String projectName = "superadmin_personal";
		final Optional<ReportPortalUser.ProjectDetails> projectDetails = projectUserRepository.findDetailsByUserIdAndProjectName(1L,
				projectName
		);

		Assertions.assertTrue(projectDetails.isPresent());

		Assertions.assertEquals(projectName, projectDetails.get().getProjectName());
		Assertions.assertEquals(1L, projectDetails.get().getProjectId());
		Assertions.assertEquals(ProjectRole.PROJECT_MANAGER, projectDetails.get().getProjectRole());
	}

	@Test
	void shouldNotFindDetailsByUserIdAndProjectNameWhenNotExists() {

		final String projectName = "superadmin_personal";
		final Optional<ReportPortalUser.ProjectDetails> projectDetails = projectUserRepository.findDetailsByUserIdAndProjectName(2L,
				projectName
		);

		Assertions.assertFalse(projectDetails.isPresent());
	}
}