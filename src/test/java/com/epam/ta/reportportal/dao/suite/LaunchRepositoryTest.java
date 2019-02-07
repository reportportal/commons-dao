/*
 * Copyright 2018 EPAM Systems
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
import com.epam.ta.reportportal.commons.querygen.CompositeFilter;
import com.epam.ta.reportportal.commons.querygen.Condition;
import com.epam.ta.reportportal.commons.querygen.Filter;
import com.epam.ta.reportportal.commons.querygen.FilterCondition;
import com.epam.ta.reportportal.dao.LaunchRepository;
import com.epam.ta.reportportal.entity.enums.KeepLogsDelay;
import com.epam.ta.reportportal.entity.enums.LaunchModeEnum;
import com.epam.ta.reportportal.entity.enums.StatusEnum;
import com.epam.ta.reportportal.entity.launch.Launch;
import com.epam.ta.reportportal.ws.model.launch.Mode;
import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;
import org.flywaydb.test.annotation.FlywayTest;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.epam.ta.reportportal.commons.querygen.constant.GeneralCriteriaConstant.CRITERIA_LAST_MODIFIED;
import static com.epam.ta.reportportal.commons.querygen.constant.GeneralCriteriaConstant.CRITERIA_PROJECT_ID;
import static com.epam.ta.reportportal.commons.querygen.constant.LaunchCriteriaConstant.CRITERIA_LAUNCH_MODE;
import static com.epam.ta.reportportal.commons.querygen.constant.LaunchCriteriaConstant.CRITERIA_LAUNCH_UUID;
import static org.junit.Assert.*;

/**
 * @author Ivan Budaev
 */
public class LaunchRepositoryTest extends BaseTest {

	private static final String FILL_SCRIPT_PATH = "/db/fill/launch";

	@Autowired
	private LaunchRepository launchRepository;

	@FlywayTest(locationsForMigrate = { FILL_SCRIPT_PATH }, invokeCleanDB = false)
	@BeforeClass
	public static void before() {
	}

	@Test
	public void deleteByProjectId() {
		final Long projectId = 1L;
		launchRepository.deleteByProjectId(projectId);
		final List<Launch> launches = launchRepository.findAll();
		launches.forEach(it -> assertNotEquals(projectId, it.getProjectId()));
	}

	@Test
	public void findAllByName() {
		final String launchName = "launch name 1";
		final List<Launch> launches = launchRepository.findAllByName(launchName);
		assertNotNull(launches);
		assertTrue(!launches.isEmpty());
		launches.forEach(it -> assertEquals(launchName, it.getName()));
	}

	@Test
	public void findLaunchIdsByProjectId() {
		final List<Long> ids = launchRepository.findLaunchIdsByProjectId(1L);
		assertNotNull(ids);
		assertEquals(12, ids.size());
		assertThat(ids.get(0), Matchers.instanceOf(Long.class));
	}

	@Test
	public void deleteLaunchesByProjectIdAndModifiedBeforeTest() {
		int removedCount = launchRepository.deleteLaunchesByProjectIdModifiedBefore(1L,
				LocalDateTime.now().minusSeconds(Duration.ofDays(KeepLogsDelay.TWO_WEEKS.getDays() - 1).getSeconds())
		);
		assertEquals(12, removedCount);
	}

	@Test
	public void streamLaunchIdsWithStatusTest() {

		Stream<Long> stream = launchRepository.streamIdsWithStatusModifiedBefore(1L,
				StatusEnum.IN_PROGRESS,
				LocalDateTime.now().minusSeconds(Duration.ofDays(KeepLogsDelay.TWO_WEEKS.getDays() - 1).getSeconds())
		);

		Assert.assertNotNull(stream);
		List<Long> ids = stream.collect(Collectors.toList());
		Assert.assertTrue(CollectionUtils.isNotEmpty(ids));
		assertEquals(12L, ids.size());
	}

	@Test
	public void streamLaunchIdsTest() {

		Stream<Long> stream = launchRepository.streamIdsModifiedBefore(1L,
				LocalDateTime.now().minusSeconds(Duration.ofDays(KeepLogsDelay.TWO_WEEKS.getDays() - 1).getSeconds())
		);

		Assert.assertNotNull(stream);
		List<Long> ids = stream.collect(Collectors.toList());
		Assert.assertTrue(CollectionUtils.isNotEmpty(ids));
		assertEquals(12L, ids.size());
	}

	@Test
	public void findByProjectIdAndStartTimeGreaterThanAndMode() {
		List<Launch> launches = launchRepository.findByProjectIdAndStartTimeGreaterThanAndMode(1L,
				LocalDateTime.now().minusMonths(1),
				LaunchModeEnum.DEFAULT
		);
		assertEquals(12, launches.size());
	}

	@Test
	public void loadLaunchesHistory() {
		final String launchName = "launch name 1";
		final long projectId = 1L;
		final long startingLaunchId = 2L;
		final int historyDepth = 2;

		List<Launch> launches = launchRepository.findLaunchesHistory(historyDepth, startingLaunchId, launchName, projectId);
		assertNotNull(launches);
		assertEquals(historyDepth, launches.size());
		launches.forEach(it -> {
			Assert.assertThat(it.getName(), Matchers.equalToIgnoringCase(launchName));
			assertEquals(projectId, (long) it.getProjectId());
			assertTrue(it.getId() <= startingLaunchId);
		});
	}

	@Test
	public void findAllLatestLaunchesTest() {
		Page<Launch> allLatestByFilter = launchRepository.findAllLatestByFilter(buildDefaultFilter(1L), PageRequest.of(0, 2));
		Assert.assertNotNull(allLatestByFilter);
		assertEquals(2, allLatestByFilter.getNumberOfElements());
	}

	@Test
	public void getLaunchNamesTest() {
		final String value = "launch";
		List<String> launchNames = launchRepository.getLaunchNames(1L, value, LaunchModeEnum.DEFAULT.toString());

		Assert.assertNotNull(launchNames);
		Assert.assertTrue(CollectionUtils.isNotEmpty(launchNames));
		launchNames.forEach(it -> assertTrue(it.contains(value)));
	}

	@Test
	public void findLaunchByFilterTest() {
		Sort sort = Sort.by(Sort.Direction.ASC, CRITERIA_LAST_MODIFIED);
		Page<Launch> launches = launchRepository.findByFilter(new CompositeFilter(buildDefaultFilter(1L), buildDefaultFilter2()),
				PageRequest.of(0, 2, sort)
		);
		assertNotNull(launches);
		assertEquals(1, launches.getTotalElements());
	}

	@Test
	public void getOwnerNames() {
		final List<String> ownerNames = launchRepository.getOwnerNames(1L, "sup", Mode.DEFAULT.name());
		assertNotNull(ownerNames);
		assertEquals(1, ownerNames.size());
		assertTrue(ownerNames.contains("superadmin"));
	}

	@Test
	public void findLastRun() {
		final Optional<Launch> lastRun = launchRepository.findLastRun(2L, Mode.DEFAULT.name());
		assertTrue(lastRun.isPresent());
	}

	@Test
	public void countLaunches() {
		final Integer count = launchRepository.countLaunches(2L, Mode.DEFAULT.name(), LocalDateTime.now().minusDays(5));
		assertNotNull(count);
		assertEquals(1, (int) count);
	}

	@Test
	public void countLaunchesGroupedByOwner() {
		final Map<String, Integer> map = launchRepository.countLaunchesGroupedByOwner(2L,
				Mode.DEFAULT.name(),
				LocalDateTime.now().minusDays(5)
		);
		assertNotNull(map.get("default"));
		assertEquals(1, (int) map.get("default"));
	}

	@Test
	public void identifyStatus() {
		final Boolean failed = launchRepository.identifyStatus(100L);
		assertNotNull(failed);
		assertTrue(failed);
	}

	private Filter buildDefaultFilter(Long projectId) {
		Set<FilterCondition> conditionSet = Sets.newHashSet(new FilterCondition(
						Condition.EQUALS,
						false,
						String.valueOf(projectId),
						CRITERIA_PROJECT_ID
				),
				new FilterCondition(Condition.EQUALS, false, Mode.DEFAULT.toString(), CRITERIA_LAUNCH_MODE)
		);
		return new Filter(Launch.class, conditionSet);
	}

	private Filter buildDefaultFilter2() {
		return new Filter(Launch.class, Sets.newHashSet(new FilterCondition(Condition.EQUALS, false, "uuid 11", CRITERIA_LAUNCH_UUID)));
	}
}