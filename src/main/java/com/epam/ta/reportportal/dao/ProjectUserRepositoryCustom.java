package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.commons.UserAssignmentDetails;
import java.util.Optional;

public interface ProjectUserRepositoryCustom {

  Optional<UserAssignmentDetails> findDetailsByUserIdAndProjectKey(Long userId, String projectKey);

  Optional<UserAssignmentDetails> findAdminDetailsProjectKey(String projectKey);
}
