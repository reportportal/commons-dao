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

package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.BaseTest;
import com.epam.ta.reportportal.commons.querygen.Condition;
import com.epam.ta.reportportal.commons.querygen.Filter;
import com.epam.ta.reportportal.entity.enums.ProjectAttributeEnum;
import com.epam.ta.reportportal.entity.enums.ProjectType;
import com.epam.ta.reportportal.entity.project.Project;
import com.epam.ta.reportportal.entity.project.ProjectInfo;
import org.apache.commons.collections4.CollectionUtils;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.epam.ta.reportportal.commons.querygen.constant.ProjectCriteriaConstant.CRITERIA_PROJECT_NAME;
import static com.epam.ta.reportportal.entity.project.ProjectInfo.USERS_QUANTITY;
import static java.util.Optional.ofNullable;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Ivan Budaev
 */
class ProjectRepositoryTest extends BaseTest {

	@Autowired
	private ProjectRepository projectRepository;

	@Test
	void findAllIdsAndProjectAttributesTest() {

		Page<Project> projects = projectRepository.findAllIdsAndProjectAttributes(PageRequest.of(0, 2));

		assertNotNull(projects);
		assertTrue(CollectionUtils.isNotEmpty(projects.getContent()));
		projects.getContent().forEach(project -> {
			assertNotNull(project.getId());
			assertTrue(CollectionUtils.isNotEmpty(project.getProjectAttributes()));
			assertEquals(13, project.getProjectAttributes().size());
			assertTrue(project.getProjectAttributes()
					.stream()
					.anyMatch(pa -> ofNullable(pa.getValue()).isPresent() && pa.getAttribute()
							.getName()
							.equals(ProjectAttributeEnum.KEEP_LOGS.getAttribute())));
		});
	}

	@Test
	void findByName() {
		final String projectName = "default_personal";

		final Optional<Project> projectOptional = projectRepository.findByName(projectName);

		assertTrue(projectOptional.isPresent());
		assertEquals(projectName, projectOptional.get().getName());
	}

	@Test
	void existsByName() {
		assertTrue(projectRepository.existsByName("default_personal"));
		assertTrue(projectRepository.existsByName("superadmin_personal"));
		assertFalse(projectRepository.existsByName("not_existed"));
	}

	@Test
	void findAllProjectNames() {
		List<String> names = projectRepository.findAllProjectNames();
		assertThat("Incorrect projects size", names, Matchers.hasSize(2));
		assertThat("Results don't contain all project", names, Matchers.hasItems("default_personal", "superadmin_personal"));
	}

	@Test
	void findAllProjectNamesByTerm() {
		List<String> names = projectRepository.findAllProjectNamesByTerm("UpEr");
		assertThat("Incorrect projects size", names, Matchers.hasSize(1));
		assertThat("Results don't contain all project", names, Matchers.hasItems("superadmin_personal"));
	}

	@Test
	void findUserProjectsTest() {
		List<Project> projects = projectRepository.findUserProjects("default");
		assertNotNull(projects);
		assertEquals(1, projects.size());
	}

	@Test
	void findAllByUserLogin() {
		List<Project> projects = projectRepository.findAllByUserLogin("default");
		assertNotNull(projects);
		assertEquals(1, projects.size());
	}

	@Test
	void findUserProjectByLoginAndType() {
		List<Project> userProjects = projectRepository.findUserProjects("superadmin", "PERSONAL");
		assertNotNull(userProjects);
		assertEquals(1, userProjects.size());
		assertEquals(ProjectType.PERSONAL, userProjects.get(0).getProjectType());
	}

	@Test
	void shouldFindProjectByName() {
		final Optional<Project> project = projectRepository.findRawByName("superadmin_personal");
		assertTrue(project.isPresent());
	}

	@Test
	void shouldNotFindProjectByName() {
		final Optional<Project> project = projectRepository.findRawByName("some_random_name");
		assertFalse(project.isPresent());
	}

	@Test
	void findProjectInfoByFilter() {
		final List<ProjectInfo> projectInfos = projectRepository.findProjectInfoByFilter(new Filter(ProjectInfo.class,
				Condition.GREATER_THAN_OR_EQUALS,
				false,
				"1",
				USERS_QUANTITY
		));
		assertEquals(2, projectInfos.size());
	}

	@Test
	void findProjectInfoByFilterWithPagination() {
		final Page<ProjectInfo> projectInfoPage = projectRepository.findProjectInfoByFilter(new Filter(ProjectInfo.class,
				Condition.EQUALS,
				false,
				"default_personal",
				CRITERIA_PROJECT_NAME
		), PageRequest.of(0, 10));
		assertEquals(1, projectInfoPage.getTotalElements());
	}

	@Sql("/db/fill/project/expired-project-fill.sql")
	@Test
	void deleteOneByTypeAndLastRunBefore() {
		int count = projectRepository.deleteByTypeAndLastLaunchRunBefore(ProjectType.UPSA, LocalDateTime.now().minusDays(11), 1);
		assertEquals(count, 1);
		assertFalse(projectRepository.findById(100L).isPresent());
	}
}
