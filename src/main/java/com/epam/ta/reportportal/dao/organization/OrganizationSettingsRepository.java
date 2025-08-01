/*
 * Copyright 2025 EPAM Systems
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


import com.epam.ta.reportportal.entity.organization.OrganizationSetting;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for accessing organization settings.
 *
 * @author <a href="mailto:pavel_bortnik@epam.com">Pavel Bortnik</a>
 */
public interface OrganizationSettingsRepository extends JpaRepository<OrganizationSetting, Long> {

  /**
   * Retrieves all settings for the specified organization.
   *
   * @param organizationId the ID of the organization
   * @return list of organization settings
   */
  List<OrganizationSetting> findByOrganizationId(Long organizationId);

}
