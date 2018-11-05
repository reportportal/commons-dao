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

package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.commons.querygen.Filter;
import com.epam.ta.reportportal.config.TestConfiguration;
import com.epam.ta.reportportal.config.util.SqlRunner;
import com.epam.ta.reportportal.entity.project.Project;
import com.epam.ta.reportportal.entity.project.ProjectInfo;
import com.google.common.collect.Sets;
import org.hamcrest.Matchers;
import org.hsqldb.cmdline.SqlToolError;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Ivan Budaev
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
@Transactional("transactionManager")
public class ProjectRepositoryTest {

	@Autowired
	private ProjectRepository projectRepository;

	@BeforeClass
	public static void init() throws SQLException, ClassNotFoundException, IOException, SqlToolError {
		Class.forName("org.hsqldb.jdbc.JDBCDriver");
//		runSqlScript("/test-dropall-script.sql");
//		runSqlScript("/test-create-script.sql");
//		runSqlScript("/user/users-projects-fill.sql");
	}

//	@AfterClass
//	public static void destroy() throws SQLException, IOException, SqlToolError {
//		runSqlScript("/test-dropall-script.sql");
//	}

	private static void runSqlScript(String scriptPath) throws SQLException, IOException, SqlToolError {
		try (Connection connection = getConnection()) {
			new SqlRunner().runSqlScript(connection, scriptPath);
		}
	}

	private static Connection getConnection() throws SQLException {
		return DriverManager.getConnection("jdbc:postgresql://localhost:5432/reportportal", "rpuser", "rppass");
	}

	@Test
	public void findAllProjectNames() {
		List<String> names = projectRepository.findAllProjectNames();
		Assert.assertThat("Incorrect projects size", names, Matchers.hasSize(3));
		Assert.assertThat(
				"Results don't contain all project",
				names,
				Matchers.hasItems("test_user_1_personal", "test_user_2_personal", "test_common_project_1")
		);
	}

	@Test
	public void findUserProjectsTest() {
		List<Project> projects = projectRepository.findUserProjects("qwerty");

		Assert.assertNotNull(projects);
		Assert.assertTrue(projects.size() >= 1);
	}

	@Test
	public void testProject() {
		Filter filter = new Filter(Project.class, Sets.newHashSet());
		Pageable pageable = PageRequest.of(0, 20);
		Page<ProjectInfo> projectsInfo = projectRepository.findProjectInfoByFilter(filter, pageable, "DEFAULT");
		Assert.assertNotEquals(projectsInfo.getTotalElements(), 0);
	}
}
