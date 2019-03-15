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

import com.epam.ta.reportportal.entity.integration.Integration;
import com.epam.ta.reportportal.entity.integration.IntegrationType;
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
	 * Retrieve integration by ID and project ID
	 *
	 * @param id        ID of integrations
	 * @param projectId ID of project
	 * @return Optional of integration
	 */
	Optional<Integration> findByIdAndProjectId(Long id, Long projectId);

	/**
	 * Retrieve given project's integrations
	 *
	 * @param projectId ID of project
	 * @return Found integrations
	 */
	List<Integration> findAllByProjectId(Long projectId);

	/**
	 * Retrieve all {@link Integration} by project ID and integration type
	 *
	 * @param projectId       {@link com.epam.ta.reportportal.entity.project.Project#id}
	 * @param integrationType {@link IntegrationType}
	 * @return The {@link List} of the {@link Integration}
	 */
	List<Integration> findAllByProjectIdAndType(Long projectId, IntegrationType integrationType);

	/**
	 * Delete all {@link Integration} with {@link Integration#project} == NULL by integration type ID
	 *
	 * @param typeId {@link IntegrationType#id}
	 */
	@Modifying
	@Query(value = "DELETE FROM integration WHERE project_id IS NULL AND type = :typeId", nativeQuery = true)
	int deleteAllGlobalByIntegrationTypeId(@Param("typeId") Long typeId);

	/**
	 * Delete all {@link Integration} by projectID and integration type ID
	 *
	 * @param typeId {@link IntegrationType#id}
	 */
	@Modifying
	@Query(value = "DELETE FROM integration WHERE project_id = :projectId AND type = :typeId", nativeQuery = true)
	int deleteAllByProjectIdAndIntegrationTypeId(@Param("projectId") Long projectId, @Param("typeId") Long typeId);

	/**
	 * Retrieve all {@link Integration} with {@link Integration#project} == null by integration type
	 *
	 * @param integrationType {@link Integration#type}
	 * @return @return The {@link List} of the {@link Integration}
	 */
	@Query(value = "SELECT i FROM Integration i WHERE i.project IS NULL AND i.type = :integrationType")
	List<Integration> findAllGlobalByType(@Param("integrationType") IntegrationType integrationType);

	/**
	 * Find BTS integration by BTS url, BTS project name and Report Portal project id
	 *
	 * @param url        Bug Tracking System url
	 * @param btsProject Bug Tracking System project name
	 * @param projectId  {@link com.epam.ta.reportportal.entity.project.Project#id}
	 * @return The {@link Integration} wrapped in the {@link Optional}
	 */
	@Query(value = "SELECT i.id, i.enabled, i.project_id, i.creation_date, i.params, i.type, 0 as clazz_ FROM integration i"
			+ " WHERE (params->'params'->>'url' = :url AND params->'params'->>'project' = :btsProject"
			+ " AND i.project_id = :projectId) LIMIT 1", nativeQuery = true)
	Optional<Integration> findProjectBtsByUrlAndLinkedProject(@Param("url") String url, @Param("btsProject") String btsProject,
			@Param("projectId") Long projectId);

	/**
	 * Find BTS integration by BTS url, BTS project name and {@link Integration#project} == null
	 *
	 * @param url        Bug Tracking System url
	 * @param btsProject Bug Tracking System project name
	 * @return The {@link Integration} wrapped in the {@link Optional}
	 */
	@Query(value = "SELECT i.id, i.enabled, i.project_id, i.creation_date, i.params, i.type, 0 as clazz_ FROM integration i "
			+ " WHERE params->'params'->>'url' = :url AND i.params->'params'->>'project' = :btsProject AND i.project_id IS NULL", nativeQuery = true)
	Optional<Integration> findGlobalBtsByUrlAndLinkedProject(@Param("url") String url, @Param("btsProject") String btsProject);

	/**
	 * Update {@link Integration#enabled} by integration ID
	 *
	 * @param enabled       Enabled state flag
	 * @param integrationId {@link Integration#id}
	 */
	@Modifying
	@Query(value = "UPDATE integration SET enabled = :enabled WHERE id = :integrationId", nativeQuery = true)
	void updateEnabledStateById(@Param("enabled") boolean enabled, @Param("integrationId") Long integrationId);

	/**
	 * Update {@link Integration#enabled} of all integrations by integration type id
	 *
	 * @param enabled           Enabled state flag
	 * @param integrationTypeId {@link IntegrationType#id}
	 */
	@Modifying
	@Query(value = "UPDATE integration SET enabled = :enabled WHERE type = :integrationTypeId", nativeQuery = true)
	void updateEnabledStateByIntegrationTypeId(@Param("enabled") boolean enabled, @Param("integrationTypeId") Long integrationTypeId);
}
