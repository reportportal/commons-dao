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

import com.epam.ta.reportportal.entity.enums.IntegrationGroupEnum;
import com.epam.ta.reportportal.entity.integration.Integration;
import com.epam.ta.reportportal.entity.integration.IntegrationParams;
import com.epam.ta.reportportal.entity.integration.IntegrationType;
import com.epam.ta.reportportal.entity.project.Project;
import com.epam.ta.reportportal.entity.project.email.SendCaseType;
import com.epam.ta.reportportal.exception.ReportPortalException;
import org.assertj.core.util.Lists;
import org.jetbrains.annotations.NotNull;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.*;

import static com.epam.ta.reportportal.util.integration.email.EmailIntegrationUtil.EMAIL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

/**
 * @author <a href="mailto:ihar_kahadouski@epam.com">Ihar Kahadouski</a>
 */
@RunWith(MockitoJUnitRunner.class)
public class EmailIntegrationUtilTest {

	private static final String RULES = "rules";

	private Project project;
	private Map<String, Object> integrationParams;
	private Map<String, Object> rule;

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Before
	public void setUp() throws Exception {
		project = getTestProject();
		integrationParams = getTestIntegrationParams();
		rule = getTestRule();

	}

	@Test
	public void getEmailIntegration() {
		final Optional<Integration> emailIntegration = EmailIntegrationUtil.getEmailIntegration(project);
		assertTrue(emailIntegration.isPresent());
		assertEquals(EMAIL, emailIntegration.get().getType().getName());
	}

	@Test
	public void getEmailRules() {
		final List<Map<String, Object>> emailRules = EmailIntegrationUtil.getEmailRules(integrationParams);
		assertNotNull(emailRules);
		assertEquals(1, emailRules.size());
		assertThat(emailRules).isEqualTo(Lists.newArrayList(getTestRule()));
	}

	@Test
	public void getEmailRulesBadRequest() {
		thrown.expect(ReportPortalException.class);
		thrown.expectMessage("Error in handled Request. Please, check specified parameters: 'Integration parameters should exits.'");
		EmailIntegrationUtil.getEmailRules(new HashMap<>());
	}

	@Test
	public void getEmailRulesNotExists() {
		thrown.expect(ReportPortalException.class);
		thrown.expectMessage("Error during object retrieving: 'Rules should exists.'");
		final HashMap<String, Object> withoutRules = new HashMap<>(integrationParams);
		withoutRules.remove(RULES);
		EmailIntegrationUtil.getEmailRules(withoutRules);
	}

	@Test
	public void getEmailRulesIncorrectRuleClassType() {
		thrown.expect(ReportPortalException.class);
		thrown.expectMessage("Error during object retrieving: 'Incorrect class type for rules.'");
		final HashMap<String, Object> withWrongType = new HashMap<>(integrationParams);
		withWrongType.put(RULES, new Object());
		EmailIntegrationUtil.getEmailRules(withWrongType);
	}

	@Test
	public void getRuleValues() {
		final List<String> ruleValues = EmailIntegrationUtil.getRuleValues(rule, SendCaseType.RECIPIENTS);
		assertNotNull(ruleValues);
		assertTrue(!ruleValues.isEmpty());
		assertThat(ruleValues).containsExactly("owner", "default");
	}

	@Test
	public void getLaunchStatsValue() {
		final String launchStatsValue = EmailIntegrationUtil.getLaunchStatsValue(rule);
		assertNotNull(launchStatsValue);
		assertEquals("always", launchStatsValue);
	}

	private static Project getTestProject() {
		Project project = new Project();

		IntegrationType integrationType = new IntegrationType();
		integrationType.setId(1L);
		integrationType.setCreationDate(LocalDateTime.now());
		integrationType.setName(EMAIL);
		integrationType.setIntegrationGroup(IntegrationGroupEnum.NOTIFICATION);

		Map<String, Object> params = getTestIntegrationParams();

		IntegrationParams integrationParams = new IntegrationParams(params);

		Integration integration = new Integration();
		integration.setId(1L);
		integration.setProject(project);
		integration.setCreationDate(LocalDateTime.now());
		integration.setEnabled(true);
		integration.setType(integrationType);
		integrationType.getIntegrations().add(integration);
		integration.setParams(integrationParams);

		project.getIntegrations().add(integration);

		return project;
	}

	@NotNull
	private static Map<String, Object> getTestIntegrationParams() {
		Map<String, Object> params = new HashMap<>();
		List<Map<String, Object>> rules = new ArrayList<>();
		Map<String, Object> rule = getTestRule();
		rules.add(rule);

		params.put(RULES, rules);
		params.put("other", Lists.newArrayList("some garbage"));
		return params;
	}

	@NotNull
	private static Map<String, Object> getTestRule() {
		Map<String, Object> rule = new HashMap<>();
		rule.put("recipients", Lists.newArrayList("owner", "default"));
		rule.put("launchTagRule", Lists.newArrayList("notExistsTag"));
		rule.put("launchNameRule", new ArrayList<>());
		rule.put("launchStatsRule", "always");
		return rule;
	}
}