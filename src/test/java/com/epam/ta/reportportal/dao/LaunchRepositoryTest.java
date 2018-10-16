/*
 *  Copyright (C) 2018 EPAM Systems
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.commons.querygen.Condition;
import com.epam.ta.reportportal.commons.querygen.Filter;
import com.epam.ta.reportportal.commons.querygen.FilterCondition;
import com.epam.ta.reportportal.config.TestConfiguration;
import com.epam.ta.reportportal.config.util.SqlRunner;
import com.epam.ta.reportportal.entity.enums.LaunchModeEnum;
import com.epam.ta.reportportal.entity.enums.StatusEnum;
import com.epam.ta.reportportal.entity.launch.Launch;
import com.epam.ta.reportportal.ws.model.launch.Mode;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;
import org.hamcrest.Matchers;
import org.hsqldb.cmdline.SqlToolError;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import static com.epam.ta.reportportal.commons.querygen.constant.GeneralCriteriaConstant.PROJECT_ID;

/**
 * @author Ivan Budaev
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
@Transactional("transactionManager")
public class LaunchRepositoryTest {

	@Autowired
	private LaunchRepository launchRepository;

	@BeforeClass
	public static void init() throws SQLException, ClassNotFoundException, IOException, SqlToolError {
		Class.forName("org.hsqldb.jdbc.JDBCDriver");
		//		runSqlScript("/test-dropall-script.sql");
		//		runSqlScript("/test-create-script.sql");
		//		runSqlScript("/test-fill-script.sql");
	}
	//
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
	public void testLoadLaunchesHistory() {
		List<Launch> demoLaunchS = launchRepository.findLaunchesHistory(2, 2L,"Demo launch s", 2L);
		Assert.assertThat(demoLaunchS.size(), Matchers.equalTo(2));
		demoLaunchS.forEach(it -> Assert.assertThat(it.getName(), Matchers.equalToIgnoringCase("Demo launch s")));
	}

	@Test
	public void mergeLaunchTestItems() {
		long time = System.nanoTime() / 1000000;
		launchRepository.mergeLaunchTestItems(1L);
		System.out.println(System.nanoTime() / 1000000 - time);
		System.out.println("OK");
	}

	@Test
	public void findAllLatestLaunchesTest() {

		Page<Launch> allLatestByFilter = launchRepository.findAllLatestByFilter(buildDefaultFilter(1L), PageRequest.of(0, 2));

		Assert.assertNotNull(allLatestByFilter);
		Assert.assertEquals(2, allLatestByFilter.getNumberOfElements());
	}

	@Test
	public void getLaunchNamesTest() {
		List<String> launchNames = launchRepository.getLaunchNames(1L, "launch", LaunchModeEnum.DEFAULT.toString());

		Assert.assertNotNull(launchNames);
		Assert.assertTrue(CollectionUtils.isNotEmpty(launchNames));
	}

	@Test
	public void jsonParsingTest() throws JsonProcessingException {
		Launch launch = launchRepository.findById(2L).get();

		String string = new ObjectMapper().writeValueAsString(launch);
	}

	private Filter buildDefaultFilter(Long projectId) {
		Set<FilterCondition> conditionSet = Sets.newHashSet(new FilterCondition(Condition.EQUALS,
						false,
						String.valueOf(projectId),
						PROJECT_ID
				),
				new FilterCondition(Condition.NOT_EQUALS, false, StatusEnum.IN_PROGRESS.name(), "status"),
				new FilterCondition(Condition.EQUALS, false, Mode.DEFAULT.toString(), "mode")
		);
		return new Filter(Launch.class, conditionSet);
	}
}