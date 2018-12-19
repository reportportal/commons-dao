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

package com.epam.ta.reportportal.entity.project;

import com.epam.ta.reportportal.entity.attribute.Attribute;
import com.epam.ta.reportportal.entity.enums.ProjectAttributeEnum;
import com.epam.ta.reportportal.entity.enums.ProjectType;
import com.epam.ta.reportportal.entity.enums.TestItemIssueGroup;
import com.epam.ta.reportportal.entity.item.issue.IssueGroup;
import com.epam.ta.reportportal.entity.item.issue.IssueType;
import com.epam.ta.reportportal.entity.user.ProjectUser;
import com.epam.ta.reportportal.entity.user.User;
import com.google.common.collect.Sets;
import org.jetbrains.annotations.NotNull;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

/**
 * @author <a href="mailto:ihar_kahadouski@epam.com">Ihar Kahadouski</a>
 */
public class ProjectUtilsTest {

	private Project project;

	private Set<Attribute> defaultAttributes;

	@Before
	public void setUp() throws Exception {
		project = getTestProject();
		defaultAttributes = getDefaultAttributes();
	}

	@Test
	public void defaultProjectAttributes() {
		final Set<ProjectAttribute> projectAttributes = ProjectUtils.defaultProjectAttributes(project, defaultAttributes);
		projectAttributes.forEach(it -> {
			assertEquals(project, it.getProject());
			assertThat(it.getAttribute()).isIn(defaultAttributes);
			assertEquals(it.getValue(), ProjectAttributeEnum.findByAttributeName(it.getAttribute().getName()).get().getDefaultValue());
		});
	}

	@Test
	public void defaultIssueTypes() {
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
	public void doesHaveUser() {
		assertTrue(ProjectUtils.doesHaveUser(project, "test_user"));
		assertFalse(ProjectUtils.doesHaveUser(project, "darth_vader"));
	}

	@Test
	public void findUserConfigByLogin() {
		final ProjectUser projectUser = ProjectUtils.findUserConfigByLogin(project, "test_user");
		assertNotNull(projectUser);
		assertEquals(getTestUser(), projectUser.getUser());
		assertEquals(project, projectUser.getProject());
		assertEquals(ProjectRole.PROJECT_MANAGER, projectUser.getProjectRole());
	}

	@Test
	public void getConfigParameters() {
		final Map<String, String> configParameters = ProjectUtils.getConfigParameters(getProjectAttributes());
		assertNotNull(configParameters);
		assertTrue(!configParameters.isEmpty());
		assertThat(configParameters).isEqualTo(getAttributeWithValuesMap().entrySet()
				.stream()
				.collect(Collectors.toMap(it -> it.getKey().getName(), Map.Entry::getValue)));
	}

	@Test
	public void getOwner() {
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
		return Arrays.asList(
				new IssueType(tiGroup, "ti001", "To Investigate", "TI", "#ffb743"),
				new IssueType(abGroup, "ab001", "Automation Bug", "AB", "#f7d63e"),
				new IssueType(pbGroup, "pb001", "Product Bug", "PB", "#ec3900"),
				new IssueType(ndGroup, "nd001", "No Defect", "ND", "#777777"),
				new IssueType(siGroup, "si001", "System Issue", "SI", "#0274d1")
		);
	}

	private static Set<ProjectAttribute> getProjectAttributes() {
		return getAttributeWithValuesMap().entrySet()
				.stream()
				.map(it -> new ProjectAttribute().withAttribute(it.getKey()).withProject(getTestProject()).withValue(it.getValue()))
				.collect(Collectors.toSet());
	}

	@NotNull
	private static Map<Attribute, String> getAttributeWithValuesMap() {
		final Attribute attr1 = new Attribute();
		attr1.setId(1L);
		attr1.setName("one");

		final Attribute attr2 = new Attribute();
		attr2.setId(1L);
		attr2.setName("two");

		Map<Attribute, String> attributeMap = new HashMap<>();
		attributeMap.put(attr1, "valOne");
		attributeMap.put(attr2, "valTwo");
		return attributeMap;
	}
}