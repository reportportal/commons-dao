package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.entity.organization.MembershipDetails;
import java.util.Optional;

public interface ProjectUserRepositoryCustom {

  Optional<MembershipDetails> findDetailsByUserIdAndProjectKey(Long userId, String projectKey);

  Optional<MembershipDetails> findAdminDetailsProjectKey(String projectKey);
}
