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

package com.epam.ta.reportportal.entity.project;

import com.epam.ta.reportportal.entity.attribute.Attribute;
import com.epam.ta.reportportal.entity.enums.ProjectAttributeEnum;
import com.epam.ta.reportportal.entity.enums.ProjectType;
import com.epam.ta.reportportal.entity.enums.TestItemIssueGroup;
import com.epam.ta.reportportal.entity.item.issue.IssueGroup;
import com.epam.ta.reportportal.entity.item.issue.IssueType;
import com.epam.ta.reportportal.entity.project.email.SenderCase;
import com.epam.ta.reportportal.entity.user.ProjectUser;
import com.epam.ta.reportportal.entity.user.User;
import com.google.common.collect.Sets;
import org.apache.commons.collections.MapUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author <a href="mailto:ihar_kahadouski@epam.com">Ihar Kahadouski</a>
 */
class ProjectUtilsTest {

	private Project project;

	private Set<Attribute> defaultAttributes;

	private String[] userLoginsToExclude;
	private String[] userEmailsToExclude;

	private String[] userLoginsNotToExclude;
	private String[] userEmailsNotToExclude;

	@BeforeEach
	void setUp() {
		project = getTestProject();
		defaultAttributes = getDefaultAttributes();
		userLoginsToExclude = new String[] { "exclude", "exclude1", "exclude2" };
		userEmailsToExclude = new String[] { "exclude@mail.com", "exclude1@mail.com", "exclude2@mail.com" };
		userLoginsNotToExclude = new String[] { "NOT_exclude", "NOT_exclude1", "NOT_exclude2" };
		userEmailsNotToExclude = new String[] { "NOT_exclude@mail.com", "NOT_exclude1@mail.com", "NOT_exclude2@mail.com" };
	}

	@Test
	void defaultProjectAttributes() {
		final Set<ProjectAttribute> projectAttributes = ProjectUtils.defaultProjectAttributes(project, defaultAttributes);
		projectAttributes.forEach(it -> {
			assertEquals(project, it.getProject());
			assertThat(it.getAttribute()).isIn(defaultAttributes);
			assertEquals(it.getValue(), ProjectAttributeEnum.findByAttributeName(it.getAttribute().getName()).get().getDefaultValue());
		});
	}

	@Test
	void defaultIssueTypes() {
		final List<IssueType> defaultIssueTypes = getDefaultIssueTypes();
		final Set<ProjectIssueType> projectIssueTypes = ProjectUtils.defaultIssueTypes(project, defaultIssueTypes);
		assertNotNull(projectIssueTypes);
		assertEquals(defaultIssueTypes.size(), projectIssueTypes.size());
		projectIssueTypes.forEach(it -> {
			assertEquals(project, it.getProject());
			assertThat(it.getIssueType()).isIn(defaultIssueTypes);
		});
	}

	@Test
	void doesHaveUser() {
		assertTrue(ProjectUtils.doesHaveUser(project, "test_user"));
		assertFalse(ProjectUtils.doesHaveUser(project, "darth_vader"));
	}

	@Test
	void findUserConfigByLogin() {
		final ProjectUser projectUser = ProjectUtils.findUserConfigByLogin(project, "test_user");
		assertNotNull(projectUser);
		assertEquals(getTestUser(), projectUser.getUser());
		assertEquals(project, projectUser.getProject());
		assertEquals(ProjectRole.PROJECT_MANAGER, projectUser.getProjectRole());
	}

	@Test
	void getConfigParameters() {
		final Map<String, String> configParameters = ProjectUtils.getConfigParameters(getProjectAttributes());
		assertNotNull(configParameters);
		assertTrue(!configParameters.isEmpty());
		assertThat(configParameters).isEqualTo(getAttributeWithValuesMap().entrySet()
				.stream()
				.collect(Collectors.toMap(it -> it.getKey().getName(), Map.Entry::getValue)));
	}

	private static List<IssueType> getDefaultIssueTypes() {
		IssueGroup tiGroup = new IssueGroup();
		tiGroup.setId(1);
		tiGroup.setTestItemIssueGroup(TestItemIssueGroup.TO_INVESTIGATE);
		IssueGroup abGroup = new IssueGroup();
		abGroup.setId(2);
		abGroup.setTestItemIssueGroup(TestItemIssueGroup.AUTOMATION_BUG);
		IssueGroup pbGroup = new IssueGroup();
		pbGroup.setId(3);
		pbGroup.setTestItemIssueGroup(TestItemIssueGroup.PRODUCT_BUG);
		IssueGroup ndGroup = new IssueGroup();
		ndGroup.setId(4);
		ndGroup.setTestItemIssueGroup(TestItemIssueGroup.NO_DEFECT);
		IssueGroup siGroup = new IssueGroup();
		siGroup.setId(5);
		siGroup.setTestItemIssueGroup(TestItemIssueGroup.SYSTEM_ISSUE);
		return Arrays.asList(new IssueType(tiGroup, "ti001", "To Investigate", "TI", "#ffb743"),
				new IssueType(abGroup, "ab001", "Automation Bug", "AB", "#f7d63e"),
				new IssueType(pbGroup, "pb001", "Product Bug", "PB", "#ec3900"),
				new IssueType(ndGroup, "nd001", "No Defect", "ND", "#777777"),
				new IssueType(siGroup, "si001", "System Issue", "SI", "#0274d1")
		);
	}

	@Test
	void excludeProjectRecipientsTest() {

		Project project = getProjectWithRecipients();
		project.setSenderCases(getEmailSenderCasesWithRecipientsOnly());

		Set<User> usersToExclude = project.getUsers().stream().map(ProjectUser::getUser).collect(Collectors.toSet());

		ProjectUtils.excludeProjectRecipients(usersToExclude, project);

		project.getSenderCases().forEach(ec -> {
			Arrays.stream(userLoginsNotToExclude).forEach(excludedLogin -> assertTrue(ec.getRecipients().contains(excludedLogin)));
			Arrays.stream(userEmailsNotToExclude).forEach(excludedLogin -> assertTrue(ec.getRecipients().contains(excludedLogin)));
			Arrays.stream(userLoginsToExclude).forEach(excludedLogin -> assertFalse(ec.getRecipients().contains(excludedLogin)));
			Arrays.stream(userEmailsToExclude).forEach(excludedLogin -> assertFalse(ec.getRecipients().contains(excludedLogin)));
		});
	}

	@Test
	void updateProjectRecipientsTest() {

		final String newEmail = "new_email@mail.com";

		Project project = new Project();

		project.setSenderCases(getEmailSenderCasesWithRecipientsOnly());

		ProjectUtils.updateProjectRecipients(userEmailsNotToExclude[0], newEmail, project);

		project.getSenderCases().forEach(ec -> {
			assertTrue(ec.getRecipients().contains(newEmail));
			assertFalse(ec.getRecipients().contains(userEmailsNotToExclude[0]));
		});

	}

	@Test
	void isPersonalForUserPositive() {

		boolean isPersonal = ProjectUtils.isPersonalForUser(ProjectType.PERSONAL, "qwe_personal1234", "qwe");

		assertTrue(isPersonal);
	}

	@Test
	void isPersonalForUserNegative() {

		boolean isPersonal = ProjectUtils.isPersonalForUser(ProjectType.PERSONAL, "qwe_personal1234", "qwe_personal");

		assertFalse(isPersonal);
	}

	@Test
	void isPersonalForUserNegativeWithProjectType() {

		boolean isPersonal = ProjectUtils.isPersonalForUser(ProjectType.INTERNAL, "qwe_personal1234", "qwe");

		assertFalse(isPersonal);
	}

	@Test
	void isAssignedPositiveTest() {
		User user = new User();
		ProjectUser projectUser = new ProjectUser();
		projectUser.setUser(user);
		Project project = new Project();
		project.setId(1L);
		projectUser.setProject(project);
		user.setProjects(Sets.newHashSet(projectUser));

		assertTrue(ProjectUtils.isAssignedToProject(user, 1L));
	}

	@Test
	void isAssignedNegativeTest() {
		User user = new User();
		ProjectUser projectUser = new ProjectUser();
		projectUser.setUser(user);
		Project project = new Project();
		project.setId(1L);
		projectUser.setProject(project);
		user.setProjects(Sets.newHashSet(projectUser));

		assertFalse(ProjectUtils.isAssignedToProject(user, 2L));
	}

	private static Project getTestProject() {
		Project project = new Project();
		project.setId(1L);
		project.setName("test_project");
		project.setProjectType(ProjectType.PERSONAL);
		project.setCreationDate(new Date());
		project.setUsers(Sets.newHashSet(new ProjectUser().withUser(getTestUser())
				.withProject(project)
				.withProjectRole(ProjectRole.PROJECT_MANAGER)));
		return project;
	}

	private static User getTestUser() {
		User user = new User();
		user.setLogin("test_user");
		return user;
	}

	private static Set<Attribute> getDefaultAttributes() {
		return Arrays.stream(ProjectAttributeEnum.values()).map(it -> {
			final Attribute attribute = new Attribute();
			attribute.setName(it.getAttribute());
			attribute.setId(new Random().nextLong());
			return attribute;
		}).collect(Collectors.toSet());
	}

	private static Map<Attribute, String> getAttributeWithValuesMap() {
		final Attribute attr1 = new Attribute();
		attr1.setId(1L);
		attr1.setName("one");

		final Attribute attr2 = new Attribute();
		attr2.setId(1L);
		attr2.setName("two");

		Attribute attr3 = new Attribute();
		attr3.setId(1L);
		attr3.setName("analyzer.param");

		Map<Attribute, String> attributeMap = new HashMap<>();
		attributeMap.put(attr1, "valOne");
		attributeMap.put(attr2, "valTwo");
		attributeMap.put(attr3, "value");
		return attributeMap;
	}

	private static Set<ProjectAttribute> getProjectAttributes() {
		return getAttributeWithValuesMap().entrySet()
				.stream()
				.map(it -> new ProjectAttribute().withAttribute(it.getKey()).withProject(getTestProject()).withValue(it.getValue()))
				.collect(Collectors.toSet());
	}

	@Test
	void getConfigParametersByPrefix() {
		Map<String, String> configParameters = ProjectUtils.getConfigParametersByPrefix(getProjectAttributes(),
				ProjectAttributeEnum.Prefix.ANALYZER
		);

		assertTrue(MapUtils.isNotEmpty(configParameters));
		configParameters.forEach((key, value) -> assertTrue(key.startsWith(ProjectAttributeEnum.Prefix.ANALYZER)));
	}

	private Project getProjectWithRecipients() {

		User firstUser = new User();
		firstUser.setLogin(userLoginsToExclude[0]);
		firstUser.setEmail(userEmailsToExclude[0]);

		User secondUser = new User();
		secondUser.setLogin(userLoginsToExclude[1]);
		secondUser.setEmail(userEmailsToExclude[1]);

		User thirdUser = new User();
		thirdUser.setLogin(userLoginsToExclude[2]);
		thirdUser.setEmail(userEmailsToExclude[2]);

		Set<User> users = Sets.newHashSet(firstUser, secondUser, thirdUser);

		Project project = new Project();

		Set<ProjectUser> projectUsers = users.stream().map(u -> {
			ProjectUser projectUser = new ProjectUser();
			projectUser.setUser(u);

			projectUser.setProject(project);

			return projectUser;
		}).collect(Collectors.toSet());

		project.setUsers(projectUsers);

		return project;

	}

	private Set<SenderCase> getEmailSenderCasesWithRecipientsOnly() {

		SenderCase firstSenderCase = new SenderCase();

		firstSenderCase.setId(1L);
		firstSenderCase.setRecipients((Stream.of(userLoginsToExclude, userEmailsToExclude, userLoginsNotToExclude, userEmailsNotToExclude)
				.flatMap(Arrays::stream)
				.collect(Collectors.toSet())));

		SenderCase secondSenderCase = new SenderCase();

		secondSenderCase.setId(2L);
		secondSenderCase.setRecipients((Stream.of(userLoginsToExclude, userEmailsToExclude, userLoginsNotToExclude, userEmailsNotToExclude)
				.flatMap(Arrays::stream)
				.collect(Collectors.toSet())));

		return Sets.newHashSet(firstSenderCase, secondSenderCase);
	}
}