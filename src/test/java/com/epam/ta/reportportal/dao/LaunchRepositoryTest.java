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
package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.commons.querygen.Condition;
import com.epam.ta.reportportal.commons.querygen.Filter;
import com.epam.ta.reportportal.commons.querygen.FilterCondition;
import com.epam.ta.reportportal.config.TestConfiguration;
import com.epam.ta.reportportal.entity.enums.KeepLogsDelay;
import com.epam.ta.reportportal.entity.enums.LaunchModeEnum;
import com.epam.ta.reportportal.entity.enums.StatusEnum;
import com.epam.ta.reportportal.entity.launch.Launch;
import com.epam.ta.reportportal.ws.model.launch.Mode;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.epam.ta.reportportal.commons.querygen.constant.GeneralCriteriaConstant.CRITERIA_PROJECT_ID;
import static com.epam.ta.reportportal.dao.constant.TestConstants.SUPERADMIN_PERSONAL_PROJECT_ID;

/**
 * @author Ivan Budaev
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
@Transactional("transactionManager")
public class LaunchRepositoryTest {

	@Autowired
	private LaunchRepository launchRepository;

	//	@BeforeClass
	//	public static void init() throws SQLException {
	//		SqlRunner.runSqlScripts("/launch/launch-down.sql", "/user/user-project-down.sql");
	//		SqlRunner.runSqlScripts("/user/user-project-up.sql", "/launch/launch-up.sql");
	//	}
	//
	//	@AfterClass
	//	public static void destroy() throws SQLException {
	//		SqlRunner.runSqlScripts("/launch/launch-down.sql", "/user/user-project-down.sql");
	//	}

	@Test
	public void deleteLaunchesByProjectIdAndModifiedBeforeTest() {
		int removedCount = launchRepository.deleteLaunchesByProjectIdModifiedBefore(SUPERADMIN_PERSONAL_PROJECT_ID,
				LocalDateTime.now().minusSeconds(Duration.ofDays(KeepLogsDelay.TWO_WEEKS.getDays() - 1).getSeconds())
		);

		Assert.assertEquals(12, removedCount);
	}

	@Test
	public void streamLaunchIdsWithStatusTest() {

		Stream<Long> stream = launchRepository.streamIdsWithStatusModifiedBefore(SUPERADMIN_PERSONAL_PROJECT_ID,
				StatusEnum.IN_PROGRESS,
				LocalDateTime.now().minusSeconds(Duration.ofDays(KeepLogsDelay.TWO_WEEKS.getDays() - 1).getSeconds())
		);

		Assert.assertNotNull(stream);
		List<Long> ids = stream.collect(Collectors.toList());
		Assert.assertTrue(CollectionUtils.isNotEmpty(ids));
		Assert.assertEquals(12L, ids.size());
	}

	@Test
	public void streamLaunchIdsTest() {

		Stream<Long> stream = launchRepository.streamIdsModifiedBefore(SUPERADMIN_PERSONAL_PROJECT_ID,
				LocalDateTime.now().minusSeconds(Duration.ofDays(KeepLogsDelay.TWO_WEEKS.getDays() - 1).getSeconds())
		);

		Assert.assertNotNull(stream);
		List<Long> ids = stream.collect(Collectors.toList());
		Assert.assertTrue(CollectionUtils.isNotEmpty(ids));
		Assert.assertEquals(12L, ids.size());
	}

	@Test
	public void testLaunches() {
		List<Launch> launches = launchRepository.findByProjectIdAndStartTimeGreaterThanAndMode(1l,
				LocalDateTime.now().minusMonths(1),
				LaunchModeEnum.DEFAULT
		);
		Assert.assertThat(launches.size(), Matchers.greaterThan(0));
	}

	@Test
	public void testLoadLaunchesHistory() {
		List<Launch> demoLaunchS = launchRepository.findLaunchesHistory(2, 2L, "Demo launch s", 2L);
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

	@Test
	public void findLaunchByFilterTest() {
		Sort sort = Sort.by(Sort.Direction.ASC, "statistics$executions$total");
		Page<Launch> filter = launchRepository.findByFilter(buildDefaultFilter(2L), PageRequest.of(0, 2, sort));
		System.out.println(filter);
		//		launches.forEach(l -> Assert.assertTrue(CollectionUtils.isNotEmpty(l.getTags())));
	}

	private Filter buildDefaultFilter(Long projectId) {
		Set<FilterCondition> conditionSet = Sets.newHashSet(new FilterCondition(Condition.EQUALS,
						false,
						String.valueOf(projectId),
						CRITERIA_PROJECT_ID
				),
				new FilterCondition(Condition.NOT_EQUALS, false, StatusEnum.IN_PROGRESS.name(), "status"),
				new FilterCondition(Condition.EQUALS, false, Mode.DEFAULT.toString(), "mode"),
				new FilterCondition(Condition.GREATER_THAN_OR_EQUALS, false, "1", "statistics$executions$passed")
		);
		return new Filter(Launch.class, conditionSet);
	}
}