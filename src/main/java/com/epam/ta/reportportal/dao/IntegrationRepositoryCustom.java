/*
 * Copyright 2019 EPAM Systems
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
import java.util.List;
import java.util.Optional;

/**
 * @author Yauheni_Martynau
 */
public interface IntegrationRepositoryCustom extends FilterableRepository<Integration> {

  /**
   * Retrieve integration with {@link Integration#project} == null by integration ID
   *
   * @param integrationId {@link Integration#id}
   * @return The {@link Integration} wrapped in the {@link Optional}
   */
  Optional<Integration> findGlobalById(Long integrationId);

  /**
   * Retrieve integrations by project ID and
   * {@link com.epam.ta.reportportal.entity.integration.IntegrationType#id} IN provided integration
   * type IDs
   *
   * @param projectId          {@link com.epam.ta.reportportal.entity.project.Project#id} of the
   *                           {@link Integration}
   * @param integrationTypeIds The {@link List} of the
   *                           {@link
   *                           com.epam.ta.reportportal.entity.integration.IntegrationType#id}
   * @return The {@link List} of the {@link Integration}
   */
  List<Integration> findAllByProjectIdAndInIntegrationTypeIds(Long projectId,
      List<Long> integrationTypeIds);

  /**
   * Retrieve integrations with
   * {@link com.epam.ta.reportportal.entity.integration.IntegrationType#id} IN provided integration
   * type IDs
   *
   * @param integrationTypeIds The {@link List} of the
   *                           {@link
   *                           com.epam.ta.reportportal.entity.integration.IntegrationType#id}
   * @return The {@link List} of the {@link Integration}
   */
  List<Integration> findAllGlobalInIntegrationTypeIds(List<Long> integrationTypeIds);

  /**
   * Retrieve integrations with
   * {@link com.epam.ta.reportportal.entity.integration.IntegrationType#id} NOT IN provided
   * integration type IDs
   *
   * @param integrationTypeIds The {@link List} of the
   *                           {@link
   *                           com.epam.ta.reportportal.entity.integration.IntegrationType#id}
   * @return The {@link List} of the {@link Integration}
   */
  List<Integration> findAllGlobalNotInIntegrationTypeIds(List<Long> integrationTypeIds);
}
