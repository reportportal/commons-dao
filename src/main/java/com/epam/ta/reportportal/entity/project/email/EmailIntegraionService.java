/*
 *  Copyright (C) 2018 EPAM Systems
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.epam.ta.reportportal.entity.project.email;

import com.epam.ta.reportportal.dao.IntegrationTypeRepository;
import com.epam.ta.reportportal.entity.enums.IntegrationGroupEnum;
import com.epam.ta.reportportal.entity.integration.Integration;
import com.epam.ta.reportportal.entity.integration.IntegrationParams;
import com.epam.ta.reportportal.entity.integration.IntegrationType;
import com.epam.ta.reportportal.entity.project.Project;
import com.epam.ta.reportportal.ws.model.ErrorType;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.epam.ta.reportportal.commons.validation.BusinessRule.expect;
import static com.epam.ta.reportportal.dao.constant.WidgetRepositoryConstants.OWNER;

/**
 * @author Pavel Bortnik
 */
@Component
public class EmailIntegraionService {

	@Autowired
	private IntegrationTypeRepository integrationTypeRepository;

	/**
	 * Setup default project email configuration
	 *
	 * @param project
	 * @return project object with default email config
	 */
	public Project setDefaultEmailConfiguration(Project project) {
		Optional<IntegrationType> email = integrationTypeRepository.findByNameAndIntegrationGroup("email",
				IntegrationGroupEnum.NOTIFICATION
		);
		expect(email, Optional::isPresent).verify(ErrorType.INTEGRATION_NOT_FOUND, "email");

		Map<String, Object> defaultCases = new HashMap<>();
		defaultCases.put(SendCaseType.RECIPIENTS.getCaseTypeString(), Lists.newArrayList(OWNER));
		defaultCases.put(SendCaseType.LAUNCH_STATS_RULE.getCaseTypeString(), Lists.newArrayList(LaunchStatsRule.ALWAYS.getRuleString()));
		IntegrationParams integrationParams = new IntegrationParams(defaultCases);

		Integration integration = new Integration();
		integration.setParams(integrationParams);
		integration.setProject(project);
		integration.setType(email.get());

		project.getIntegrations().add(integration);
		return project;
	}

}
