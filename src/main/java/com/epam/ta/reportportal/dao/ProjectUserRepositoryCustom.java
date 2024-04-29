package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.commons.ReportPortalUser.OrganizationDetails;
import java.util.Optional;

public interface ProjectUserRepositoryCustom {

  Optional<OrganizationDetails> findDetailsByUserIdAndProjectKey(Long userId, String projectKey);

  Optional<OrganizationDetails> findAdminDetailsProjectKey(String projectKey);
}
