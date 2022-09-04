package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.commons.ReportPortalUser;

import java.util.Optional;

public interface ProjectUserRepositoryCustom {
	Optional<ReportPortalUser.ProjectDetails> findDetailsByUserIdAndProjectKey(Long userId, String projectKey);
}
