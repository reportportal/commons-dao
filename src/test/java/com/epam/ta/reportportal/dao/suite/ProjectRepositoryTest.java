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

package com.epam.ta.reportportal.dao.suite;

import com.epam.ta.reportportal.BaseTest;
import com.epam.ta.reportportal.commons.querygen.Condition;
import com.epam.ta.reportportal.commons.querygen.Filter;
import com.epam.ta.reportportal.commons.querygen.FilterCondition;
import com.epam.ta.reportportal.dao.ProjectRepository;
import com.epam.ta.reportportal.entity.enums.ProjectAttributeEnum;
import com.epam.ta.reportportal.entity.integration.Integration;
import com.epam.ta.reportportal.entity.project.Project;
import com.epam.ta.reportportal.entity.project.ProjectInfo;
import com.epam.ta.reportportal.exception.ReportPortalException;
import com.epam.ta.reportportal.ws.model.ErrorType;
import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;
import org.flywaydb.test.annotation.FlywayTest;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.epam.ta.reportportal.commons.querygen.constant.ProjectCriteriaConstant.CRITERIA_ATTRIBUTE_NAME;
import static java.util.Optional.ofNullable;

/**
 * @author Ivan Budaev
 */
@FlywayTest
public class ProjectRepositoryTest extends BaseTest {

	@Autowired
	private ProjectRepository projectRepository;

	@Test
	public void findAllIdsAndProjectAttributesTest() {

		Filter filter = Filter.builder().withTarget(Project.class).withCondition(new FilterCondition(Condition.EQUALS, false,

				ProjectAttributeEnum.KEEP_LOGS.getAttribute(), CRITERIA_ATTRIBUTE_NAME
		)).build();

		Page<Project> projects = projectRepository.findAllIdsAndProjectAttributes(filter, PageRequest.of(0, 2));

		Assert.assertNotNull(projects);
		Assert.assertTrue(CollectionUtils.isNotEmpty(projects.getContent()));
		projects.getContent().forEach(project -> {
			Assert.assertNotNull(project.getId());
			Assert.assertTrue(CollectionUtils.isNotEmpty(project.getProjectAttributes()));
			Assert.assertEquals(11, project.getProjectAttributes().size());
			Assert.assertTrue(project.getProjectAttributes()
					.stream()
					.allMatch(pa -> ofNullable(pa.getValue()).isPresent() && pa.getAttribute()
							.getName()
							.equals(ProjectAttributeEnum.KEEP_LOGS.getAttribute())));
		});
	}

	@Test
	public void test() {
		Project project = projectRepository.findById(1L).orElseThrow(() -> new ReportPortalException(ErrorType.PROJECT_NOT_FOUND, 1L));

		Integration email = project.getIntegrations()
				.stream()
				.filter(it -> it.getType().getName().equalsIgnoreCase("email"))
				.findFirst()
				.get();

		email.setEnabled(true);

		projectRepository.save(project);

		System.out.println();

	}

	@Test
	public void findAllProjectNames() {
		List<String> names = projectRepository.findAllProjectNames();
		Assert.assertThat("Incorrect projects size", names, Matchers.hasSize(2));
		Assert.assertThat("Results don't contain all project",
				names, Matchers.hasItems("default_personal", "superadmin_personal")
		);
	}

	@Test
	public void findUserProjectsTest() {
		List<Project> projects = projectRepository.findUserProjects("default");

		Assert.assertNotNull(projects);
		Assert.assertEquals(1, projects.size());
	}

	@Test
	public void testProject() {
		Filter filter = new Filter(Project.class, Sets.newHashSet());
		Pageable pageable = PageRequest.of(0, 20);
		Page<ProjectInfo> projectsInfo = projectRepository.findProjectInfoByFilter(filter, pageable, "DEFAULT");
		Assert.assertNotEquals(0, projectsInfo.getTotalElements());
	}
}
