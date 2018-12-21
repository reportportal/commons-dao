/*
 * Copyright (C) 2018 EPAM Systems
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
package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.entity.enums.IntegrationGroupEnum;
import com.epam.ta.reportportal.entity.integration.Integration;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * @author Ivan Budayeu
 * @author Andrei Varabyeu
 */
public interface IntegrationRepository extends ReportPortalRepository<Integration, Long>, IntegrationRepositoryCustom {

	/**
	 * Retrieves integration by ID and project ID
	 *
	 * @param id        ID of integrations
	 * @param projectId ID of project
	 * @return Optional of integration
	 */
	Optional<Integration> findByIdAndProjectId(Long id, Long projectId);

	/**
	 * Retrieves given project's integrations
	 *
	 * @param projectId ID of project
	 * @return Found integrations
	 */
	List<Integration> findAllByProjectId(Long projectId);

	@Query(value = "SELECT i.id, i.enabled, i.project_id, i.creation_date, i.params, i.type, 0 as clazz_ FROM integration i"
			+ " WHERE (params->'params'->>'url' = :url AND params->'params'->>'project' = :btsProject"
			+ " AND i.project_id = :projectId) LIMIT 1", nativeQuery = true)
	Optional<Integration> findByUrlAndBtsProjectAndProjectId(@Param("url") String url, @Param("btsProject") String btsProject,
			@Param("projectId") Long projectId);

	void deleteAllByTypeIntegrationGroup(IntegrationGroupEnum integrationGroup);

	@Modifying
	@Query(value = "UPDATE integration SET enabled = :enabled WHERE id = :integrationId", nativeQuery = true)
	void updateIntegrationEnabledState(@Param("enabled") boolean enabled, @Param("integrationId") Long integrationId);

	@Modifying
	@Query(value = "UPDATE integration SET enabled = :enabled WHERE type = :integrationTypeId", nativeQuery = true)
	void updateIntegrationGroupEnabledState(@Param("enabled") boolean enabled, @Param("integrationTypeId") Long integrationTypeId);
}
