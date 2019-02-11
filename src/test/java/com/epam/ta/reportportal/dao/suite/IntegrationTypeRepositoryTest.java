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
import com.epam.ta.reportportal.dao.IntegrationTypeRepository;
import com.epam.ta.reportportal.entity.enums.IntegrationGroupEnum;
import com.epam.ta.reportportal.entity.integration.IntegrationType;
import org.flywaydb.test.annotation.FlywayTest;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author <a href="mailto:ivan_budayeu@epam.com">Ivan Budayeu</a>
 */
public class IntegrationTypeRepositoryTest extends BaseTest {

	private static final String FILL_SCRIPT_PATH = "/db/fill/integration";

	private final static String JIRA_INTEGRATION_TYPE_NAME = "JIRA";
	private final static String WRONG_INTEGRATION_TYPE_NAME = "WRONG";
	private static final long BTS_INTEGRATIONS_COUNT = 2L;


	@FlywayTest(locationsForMigrate = { FILL_SCRIPT_PATH }, invokeCleanDB = false)
	@BeforeClass
	public static void before() {
	}

	@Autowired
	private IntegrationTypeRepository integrationTypeRepository;

	@Test
	public void shouldFindWhenNameExists() {

		Assert.assertTrue(integrationTypeRepository.findByName(JIRA_INTEGRATION_TYPE_NAME).isPresent());

	}

	@Test
	public void shouldNotFindWhenNameExists() {

		Assert.assertFalse(integrationTypeRepository.findByName(WRONG_INTEGRATION_TYPE_NAME).isPresent());
	}

	@Test
	public void shouldFindWhenNameExistsAndIntegrationGroupExists() {

		Assert.assertTrue(integrationTypeRepository.findByNameAndIntegrationGroup(JIRA_INTEGRATION_TYPE_NAME, IntegrationGroupEnum.BTS)
				.isPresent());
	}

	@Test
	public void shouldNotFindWhenIncorrectNameAndIntegrationGroupExists() {

		Assert.assertFalse(integrationTypeRepository.findByNameAndIntegrationGroup(WRONG_INTEGRATION_TYPE_NAME, IntegrationGroupEnum.BTS)
				.isPresent());
	}

	@Test
	public void shouldNotFindWhenNameExistsAndIntegrationGroupNotExists() {

		Assert.assertFalse(integrationTypeRepository.findByNameAndIntegrationGroup(JIRA_INTEGRATION_TYPE_NAME,
				IntegrationGroupEnum.NOTIFICATION
		).isPresent());
	}

	@Test
	public void shouldFindAllByIntegrationGroup() {

		List<IntegrationType> integrationTypes = integrationTypeRepository.findAllByIntegrationGroup(IntegrationGroupEnum.BTS);

		Assert.assertNotNull(integrationTypes);
		Assert.assertEquals(BTS_INTEGRATIONS_COUNT, integrationTypes.size());
	}
}
