/*
 * Copyright 2024 EPAM Systems
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.epam.ta.reportportal.dao.organization;

import com.epam.ta.reportportal.dao.ReportPortalRepository;
import com.epam.ta.reportportal.entity.user.OrganizationUser;
import com.epam.ta.reportportal.entity.user.OrganizationUserId;
import java.util.Optional;

/**
 * This interface represents a repository for the OrganizationUser entity.
 *
 * @author Siarhei Hrabko
 */
public interface OrganizationUserRepository extends
    ReportPortalRepository<OrganizationUser, OrganizationUserId> {

  /**
   * This method is used to find a list of OrganizationUser entities by user ID and organization ID.
   * It returns a list of OrganizationUser entities that match the given user ID and organization
   * ID.
   *
   * @param userId The ID of the user.
   * @param orgId  The ID of the organization.
   * @return A list of OrganizationUser entities that match the given user ID and organization ID.
   */
  Optional<OrganizationUser> findByUserIdAndOrganization_Id(Long userId, Long orgId);
}
