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

package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.BaseTest;
import com.epam.ta.reportportal.entity.enums.IntegrationGroupEnum;
import com.epam.ta.reportportal.entity.integration.IntegrationType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author <a href="mailto:ivan_budayeu@epam.com">Ivan Budayeu</a>
 */
class IntegrationTypeRepositoryTest extends BaseTest {

	private final static String JIRA_INTEGRATION_TYPE_NAME = "jiRA";
	private final static String WRONG_INTEGRATION_TYPE_NAME = "WRONG";
	private static final long BTS_INTEGRATIONS_COUNT = 2L;

	@Autowired
	private IntegrationTypeRepository integrationTypeRepository;

	@Test
	void shouldFindWhenNameExists() {

		assertTrue(integrationTypeRepository.findByName(JIRA_INTEGRATION_TYPE_NAME).isPresent());

	}

	@Test
	void shouldNotFindWhenNameExists() {

		assertFalse(integrationTypeRepository.findByName(WRONG_INTEGRATION_TYPE_NAME).isPresent());
	}

	@Test
	void shouldFindWhenNameExistsAndIntegrationGroupExists() {

		assertTrue(integrationTypeRepository.findByNameAndIntegrationGroup(JIRA_INTEGRATION_TYPE_NAME, IntegrationGroupEnum.BTS)
				.isPresent());
	}

	@Test
	void shouldNotFindWhenIncorrectNameAndIntegrationGroupExists() {

		assertFalse(integrationTypeRepository.findByNameAndIntegrationGroup(WRONG_INTEGRATION_TYPE_NAME, IntegrationGroupEnum.BTS)
				.isPresent());
	}

	@Test
	void shouldNotFindWhenNameExistsAndIntegrationGroupNotExists() {

		assertFalse(integrationTypeRepository.findByNameAndIntegrationGroup(JIRA_INTEGRATION_TYPE_NAME, IntegrationGroupEnum.NOTIFICATION)
				.isPresent());
	}

	@Test
	void shouldFindAllByIntegrationGroup() {

		List<IntegrationType> integrationTypes = integrationTypeRepository.findAllByIntegrationGroup(IntegrationGroupEnum.BTS);

		assertNotNull(integrationTypes);
		assertEquals(BTS_INTEGRATIONS_COUNT, integrationTypes.size());
	}
}
