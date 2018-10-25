/*
 *  Copyright (C) 2018 EPAM Systems
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.entity.integration.IntegrationType;

import java.util.Optional;

/**
 * Repository for {@link com.epam.ta.reportportal.entity.integration.IntegrationType} entity
 *
 * @author Yauheni_Martynau
 */
public interface IntegrationTypeRepository extends ReportPortalRepository<IntegrationType, Long> {

	/**
	 * Searches for an integration
	 *
	 * @param name Integration name
	 * @return Optional of integration
	 */
	Optional<IntegrationType> findByName(String name);

	/**
	 * Searches for an integration by name and group
	 *
	 * @param name      Integration name
	 * @param groupType Integration group
	 * @return Optional of integration
	 */
	Optional<IntegrationType> findByNameAndGroupType(String name, String groupType);
}
