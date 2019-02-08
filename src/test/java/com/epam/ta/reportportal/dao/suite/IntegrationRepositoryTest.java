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
import com.epam.ta.reportportal.entity.enums.IntegrationGroupEnum;
import com.epam.ta.reportportal.entity.integration.Integration;
import com.epam.ta.reportportal.entity.integration.IntegrationType;
import org.flywaydb.test.annotation.FlywayTest;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.epam.ta.reportportal.dao.constant.TestConstants.*;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;

/**
 * @author <a href="mailto:ivan_budayeu@epam.com">Ivan Budayeu</a>
 */
public class IntegrationRepositoryTest extends BaseTest {

	public static final String FILL_SCRIPT_PATH = "db/fill/integration";

	private static final long GLOBAL_EMAIL_INTEGRATIONS_COUNT = 1L;
	private static final long SUPERADMIN_PROJECT_BTS_INTEGRATIONS_COUNT = 4L;
	private static final long GLOBAL_BTS_INTEGRATIONS_COUNT = 2L;

	private static final Long RALLY_INTEGRATION_TYPE_ID = 2L;
	private static final Long JIRA_INTEGRATION_TYPE_ID = 3L;

	private static final Long RALLY_INTEGRATION_ID = 1L;
	private static final Long JIRA_INTEGRATION_ID = 2L;

	@Autowired
	private IntegrationRepository integrationRepository;

	@Autowired
	private IntegrationTypeRepository integrationTypeRepository;

	@FlywayTest(locationsForMigrate = { FILL_SCRIPT_PATH }, invokeCleanDB = false)
	@BeforeClass
	public static void before() {
	}

	@Test
	public void shouldUpdateEnabledStateByIntegrationId() {

		integrationRepository.updateEnabledStateById(true, RALLY_INTEGRATION_ID);

		Integration after = integrationRepository.findById(RALLY_INTEGRATION_ID).get();

		Assert.assertTrue(after.isEnabled());

	}

	@Test
	public void shouldUpdateEnabledStateByIntegrationTypeId() {

		IntegrationType integrationType = integrationTypeRepository.findById(JIRA_INTEGRATION_TYPE_ID).get();

		integrationRepository.updateEnabledStateByIntegrationTypeId(true, integrationType.getId());

		List<Integration> enabledAfter = integrationRepository.findAllByProjectIdAndType(DEFAULT_PERSONAL_PROJECT_ID, integrationType);

		enabledAfter.forEach(integration -> Assert.assertTrue(integration.isEnabled()));
	}

	@Test
	public void shouldFindAllByProjectIdAndIntegrationTypeWhenExists() {

		IntegrationType integrationType = integrationTypeRepository.findById(JIRA_INTEGRATION_TYPE_ID).get();

		List<Integration> integrations = integrationRepository.findAllByProjectIdAndType(DEFAULT_PERSONAL_PROJECT_ID, integrationType);

		Assert.assertNotNull(integrations);
		Assert.assertEquals(1L, integrations.size());
	}

	@Test
	public void shouldDeleteAllByIntegrationTypeId() {

		IntegrationType integrationType = integrationTypeRepository.findById(JIRA_INTEGRATION_TYPE_ID).get();

		integrationRepository.deleteAllByIntegrationTypeId(integrationType.getId());

		Assert.assertThat(integrationRepository.findAllByProjectIdAndType(DEFAULT_PERSONAL_PROJECT_ID, integrationType), is(empty()));
		Assert.assertThat(integrationRepository.findAllByProjectIdAndType(SUPERADMIN_PERSONAL_PROJECT_ID, integrationType), is(empty()));
	}

	@Test
	public void shouldFindAllGlobalByIntegrationType() {

		IntegrationType integrationType = integrationTypeRepository.findById(EMAIL_INTEGRATION_TYPE_ID).get();

		List<Integration> globalEmailIntegrations = integrationRepository.findAllGlobalByType(integrationType);

		Assert.assertNotNull(globalEmailIntegrations);
		Assert.assertEquals(GLOBAL_EMAIL_INTEGRATIONS_COUNT, globalEmailIntegrations.size());

		globalEmailIntegrations.forEach(i -> Assert.assertNull(i.getProject()));
	}

	@Test
	public void shouldFindGlobalBtsIntegrationByUrlAndLinkedProject() {

		Optional<Integration> globalBtsIntegration = integrationRepository.findGlobalBtsByUrlAndLinkedProject("bts.com", "bts_project");

		Assert.assertTrue(globalBtsIntegration.isPresent());
		Assert.assertNull(globalBtsIntegration.get().getProject());
	}

	@Test
	public void shouldFindProjectBtsIntegrationByUrlAndLinkedProject() {

		Optional<Integration> projectBtsIntegration = integrationRepository.findProjectBtsByUrlAndLinkedProject("projectbts.com",
				"project",
				SUPERADMIN_PERSONAL_PROJECT_ID
		);

		Assert.assertTrue(projectBtsIntegration.isPresent());
		Assert.assertNotNull(projectBtsIntegration.get().getProject());
	}

	@Test
	public void shouldFindGlobalIntegrationById() {

		Optional<Integration> globalIntegration = integrationRepository.findGlobalById(GLOBAL_EMAIL_INTEGRATION_ID);

		Assert.assertTrue(globalIntegration.isPresent());
		Assert.assertNull(globalIntegration.get().getProject());
	}

	@Test
	public void shouldFindAllProjectIntegrationsByProjectIdAndIntegrationTypeIds() {

		List<Long> integrationTypeIds = integrationTypeRepository.findAllByIntegrationGroup(IntegrationGroupEnum.BTS)
				.stream()
				.map(IntegrationType::getId)
				.collect(Collectors.toList());

		List<Integration> integrations = integrationRepository.findAllByProjectIdAndInIntegrationTypeIds(SUPERADMIN_PERSONAL_PROJECT_ID,
				integrationTypeIds
		);

		Assert.assertNotNull(integrations);
		Assert.assertEquals(SUPERADMIN_PROJECT_BTS_INTEGRATIONS_COUNT, integrations.size());

		integrations.forEach(i -> Assert.assertNotNull(i.getProject()));
	}

	@Test
	public void shouldFindAllGlobalInIntegrationTypeIds() {

		List<Long> integrationTypeIds = integrationTypeRepository.findAllByIntegrationGroup(IntegrationGroupEnum.BTS)
				.stream()
				.map(IntegrationType::getId)
				.collect(Collectors.toList());

		List<Integration> integrations = integrationRepository.findAllGlobalInIntegrationTypeIds(integrationTypeIds);

		Assert.assertNotNull(integrations);
		Assert.assertEquals(GLOBAL_BTS_INTEGRATIONS_COUNT, integrations.size());

		integrations.forEach(i -> Assert.assertNull(i.getProject()));
	}

	@Test
	public void shouldFindAllGlobalProjectIntegrationsNotInIntegrationTypeIds() {

		List<Long> integrationTypeIds = integrationTypeRepository.findAllByIntegrationGroup(IntegrationGroupEnum.BTS)
				.stream()
				.map(IntegrationType::getId)
				.collect(Collectors.toList());

		List<Integration> integrations = integrationRepository.findAllGlobalNotInIntegrationTypeIds(integrationTypeIds);

		Assert.assertNotNull(integrations);
		Assert.assertEquals(GLOBAL_EMAIL_INTEGRATIONS_COUNT, integrations.size());

		integrations.forEach(i -> Assert.assertNull(i.getProject()));
	}

}
