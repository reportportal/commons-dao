package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.commons.ReportPortalUser;

import java.util.Optional;

public interface ProjectUserRepositoryCustom {

	Optional<ReportPortalUser.ProjectDetails> findDetailsByUserIdAndProjectName(Long userId, String projectName);

	Optional<ReportPortalUser.ProjectDetails> findAdminDetailsByProjectName(String projectName);
}
