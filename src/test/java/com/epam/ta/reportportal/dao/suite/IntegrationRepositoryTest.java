/*
 * Copyright 2018 EPAM Systems
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

package com.epam.ta.reportportal.dao.suite;

import com.epam.ta.reportportal.BaseTest;
import com.epam.ta.reportportal.dao.IntegrationRepository;
import com.epam.ta.reportportal.dao.IntegrationTypeRepository;
import com.epam.ta.reportportal.entity.integration.Integration;
import com.epam.ta.reportportal.entity.integration.IntegrationType;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

import static com.epam.ta.reportportal.dao.constant.TestConstants.*;
import static java.util.stream.Collectors.toMap;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;

/**
 * @author <a href="mailto:ivan_budayeu@epam.com">Ivan Budayeu</a>
 */
public class IntegrationRepositoryTest extends BaseTest {

	@Autowired
	private IntegrationRepository integrationRepository;

	@Autowired
	private IntegrationTypeRepository integrationTypeRepository;

	@Test
	public void shouldUpdateEnabledStateByIntegrationId() {

		integrationRepository.updateEnabledStateById(true, RALLY_INTEGRATION_ID);

		Integration after = integrationRepository.findById(RALLY_INTEGRATION_ID).get();

		Assert.assertTrue(after.isEnabled());

	}

	public void shouldUpdateEnabledStateByIntegrationTypeId() {

		IntegrationType integrationType = integrationTypeRepository.findById(JIRA_INTEGRATION_TYPE_ID).get();

		Map<Long, Boolean> enabledBefore = integrationRepository.findAllByProjectIdAndType(DEFAULT_PERSONAL_PROJECT_ID, integrationType)
				.stream()
				.collect(toMap(Integration::getId, Integration::isEnabled));

		integrationRepository.updateEnabledStateByIntegrationTypeId(true, integrationType.getId());

		Map<Long, Boolean> enabledAfter = integrationRepository.findAllByProjectIdAndType(DEFAULT_PERSONAL_PROJECT_ID, integrationType)
				.stream()
				.collect(toMap(Integration::getId, Integration::isEnabled));

		enabledAfter.forEach((key, value) -> Assert.assertNotEquals(value, enabledBefore.get(key)));
	}

	public void shouldFindAllByProjectIdAndIntegrationTypeWhenExists() {

		IntegrationType integrationType = integrationTypeRepository.findById(JIRA_INTEGRATION_TYPE_ID).get();

		List<Integration> integrations = integrationRepository.findAllByProjectIdAndType(DEFAULT_PERSONAL_PROJECT_ID, integrationType);

		Assert.assertNotNull(integrations);
		Assert.assertEquals(2, integrations.size());
	}

	public void shouldDeleteAllByIntegrationTypeId() {

		IntegrationType integrationType = integrationTypeRepository.findById(JIRA_INTEGRATION_TYPE_ID).get();

		integrationRepository.deleteAllByTypeId(integrationType.getId());

		Assert.assertThat(integrationRepository.findAllByProjectIdAndType(DEFAULT_PERSONAL_PROJECT_ID, integrationType), is(empty()));
		Assert.assertThat(integrationRepository.findAllByProjectIdAndType(SUPERADMIN_PERSONAL_PROJECT_ID, integrationType), is(empty()));
	}

}
