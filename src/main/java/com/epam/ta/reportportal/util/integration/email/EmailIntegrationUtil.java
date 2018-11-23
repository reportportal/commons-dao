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

package com.epam.ta.reportportal.util.integration.email;

import com.epam.ta.reportportal.entity.integration.Integration;
import com.epam.ta.reportportal.entity.project.Project;
import com.epam.ta.reportportal.entity.project.email.SendCaseType;
import com.epam.ta.reportportal.ws.model.ErrorType;
import com.google.common.base.Predicates;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.epam.ta.reportportal.commons.validation.BusinessRule.expect;
import static com.epam.ta.reportportal.entity.project.email.SendCaseType.LAUNCH_STATS_RULE;

/**
 * @author <a href="mailto:pavel_bortnik@epam.com">Pavel Bortnik</a>
 */
public class EmailIntegrationUtil {

	public static final String EMAIL = "email";
	private static final String RULES = "rules";

	private EmailIntegrationUtil() {
		//static only
	}

	/**
	 * Extract email integration from project
	 *
	 * @param project Project
	 * @return Optional of Integration
	 */
	public static Optional<Integration> getEmailIntegration(Project project) {
		if (project != null && CollectionUtils.isNotEmpty(project.getIntegrations())) {
			return project.getIntegrations().stream().filter(it -> it.getType().getName().equalsIgnoreCase(EMAIL)).findFirst();
		}
		return Optional.empty();
	}

	/**
	 * Extract email rules from email integration parameters
	 *
	 * @param integrationParams Email integration parameters
	 * @return List of rules
	 */
	public static List<Map<String, Object>> getEmailRules(Map<String, Object> integrationParams) {
		expect(integrationParams, MapUtils::isNotEmpty).verify(ErrorType.BAD_REQUEST_ERROR, "Integration parameters should exits.");

		Optional<Object> rules = Optional.ofNullable(integrationParams.get(RULES));
		expect(rules, Optional::isPresent).verify(ErrorType.OBJECT_RETRIEVAL_ERROR, "Rules should exists.");
		expect(rules.get(), Predicates.instanceOf(List.class)).verify(ErrorType.OBJECT_RETRIEVAL_ERROR, "Incorrect class type for rules.");

		return (List<Map<String, Object>>) rules.get();
	}

	/**
	 * Get list of case values
	 *
	 * @param rule     Rule to extract
	 * @param caseType Case to extract
	 * @return List of case values
	 */
	public static List<String> getRuleValues(Map<String, Object> rule, SendCaseType caseType) {
		expect(rule, MapUtils::isNotEmpty).verify(ErrorType.BAD_REQUEST_ERROR, "Launch rule should exist.");

		expect(caseType, Predicates.notNull().and(it -> it != SendCaseType.LAUNCH_STATS_RULE)).verify(ErrorType.BAD_REQUEST_ERROR,
				"Case type should exist."
		);

		Optional<Object> result = Optional.ofNullable(rule.get(caseType.getCaseTypeString()));
		expect(result, Optional::isPresent).verify(ErrorType.OBJECT_RETRIEVAL_ERROR, "Rules should exists.");
		expect(result, Predicates.instanceOf(List.class)).verify(ErrorType.OBJECT_RETRIEVAL_ERROR,
				"Incorrect result of retrieving " + caseType.getCaseTypeString()
		);
		return (List<String>) result.get();
	}

	public static String getLaunchStatsValue(Map<String, Object> rule) {
		expect(rule, MapUtils::isNotEmpty).verify(ErrorType.BAD_REQUEST_ERROR, "Launch stats rule should exist.");
		Optional<Object> launchStatsRule = Optional.ofNullable(rule.get(LAUNCH_STATS_RULE.getCaseTypeString()));
		expect(launchStatsRule, Optional::isPresent).verify(ErrorType.OBJECT_RETRIEVAL_ERROR,
				"Rule should exists.",
				LAUNCH_STATS_RULE.getCaseTypeString()
		);

		expect(launchStatsRule, Predicates.instanceOf(String.class)).verify(ErrorType.OBJECT_RETRIEVAL_ERROR,
				"Incorrect result of retrieving " + LAUNCH_STATS_RULE.getCaseTypeString()
		);
		return (String) launchStatsRule.get();
	}

}
