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
import com.epam.ta.reportportal.entity.organization.Organization;

import java.util.Optional;

/**
 * @author Siarhei Hrabko
 */
public interface OrganizationRepository extends ReportPortalRepository<Organization, Long> {

	/**
	 * @param name name of organization
	 * @return {@link Optional} of {@link Organization}
	 */
	Optional<Organization> findByName(String name);

  /**
   * @param slug slug name of organization
   * @return {@link Optional} of {@link Organization}
   */
  Optional<Organization> findBySlug(String slug);
}
