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
import com.epam.ta.reportportal.commons.querygen.*;
import com.epam.ta.reportportal.entity.enums.LaunchModeEnum;
import com.epam.ta.reportportal.entity.enums.StatusEnum;
import com.epam.ta.reportportal.entity.launch.Launch;
import com.epam.ta.reportportal.jooq.enums.JLaunchModeEnum;
import com.epam.ta.reportportal.jooq.enums.JStatusEnum;
import com.epam.ta.reportportal.ws.model.analyzer.IndexLaunch;
import com.epam.ta.reportportal.ws.model.launch.Mode;
import com.google.common.collect.Comparators;
import org.apache.commons.collections4.CollectionUtils;
import org.assertj.core.util.Lists;
import org.hamcrest.Matchers;
import org.jooq.Operator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.jdbc.Sql;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.epam.ta.reportportal.commons.querygen.constant.GeneralCriteriaConstant.*;
import static com.epam.ta.reportportal.commons.querygen.constant.ItemAttributeConstant.CRITERIA_ITEM_ATTRIBUTE_KEY;
import static com.epam.ta.reportportal.commons.querygen.constant.ItemAttributeConstant.CRITERIA_ITEM_ATTRIBUTE_VALUE;
import static com.epam.ta.reportportal.commons.querygen.constant.LaunchCriteriaConstant.CRITERIA_LAUNCH_MODE;
import static com.epam.ta.reportportal.commons.querygen.constant.LaunchCriteriaConstant.CRITERIA_LAUNCH_UUID;
import static com.epam.ta.reportportal.commons.querygen.constant.UserCriteriaConstant.CRITERIA_USER;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Ivan Budaev
 */
@Sql("/db/fill/launch/launch-fill.sql")
class LaunchRepositoryTest extends BaseTest {

	@Autowired
	private LaunchRepository launchRepository;

	@Test
	void deleteByProjectId() {
		final Long projectId = 1L;
		launchRepository.deleteByProjectId(projectId);
		final List<Launch> launches = launchRepository.findAll();
		launches.forEach(it -> assertNotEquals(projectId, it.getProjectId()));
	}

	@Test
	void findAllByName() {
		final String launchName = "launch name 1";
		final List<Launch> launches = launchRepository.findAllByName(launchName);
		assertNotNull(launches);
		assertTrue(!launches.isEmpty());
		launches.forEach(it -> assertEquals(launchName, it.getName()));
	}

	@Test
	void findByUuid() {
		final String uuid = "uuid 11";
		final Optional<Launch> launch = launchRepository.findByUuid(uuid);
		assertNotNull(launch);
		assertTrue(launch.isPresent());
		assertEquals(uuid, launch.get().getUuid());
	}

	@Test
	void findLaunchIdsByProjectId() {
		final List<Long> ids = launchRepository.findLaunchIdsByProjectId(1L);
		assertNotNull(ids);
		assertEquals(12, ids.size());
		assertThat(ids.get(0), Matchers.instanceOf(Long.class));
	}

	@Test
	void findIdsByProjectIdAndStartTimeBeforeLimit() {
		List<Long> ids = launchRepository.findIdsByProjectIdAndStartTimeBefore(1L,
				LocalDateTime.now().minusSeconds(Duration.ofDays(13).getSeconds()),
				5
		);
		assertEquals(5, ids.size());
	}

	@Test
	void findIdsByProjectIdAndStartTimeBeforeLimitWithOffset() {
		List<Long> ids = launchRepository.findIdsByProjectIdAndStartTimeBefore(1L,
				LocalDateTime.now().minusSeconds(Duration.ofDays(13).getSeconds()),
				3,
				10L
		);
		assertEquals(2, ids.size());
	}

	@Test
	void deleteAllByIds() {
		int removedCount = launchRepository.deleteAllByIdIn(Arrays.asList(1L, 2L, 3L));
		assertEquals(3, removedCount);
	}

	@Test
	void streamLaunchIdsWithStatusTest() {

		Stream<Long> stream = launchRepository.streamIdsWithStatusAndStartTimeBefore(1L,
				StatusEnum.IN_PROGRESS,
				LocalDateTime.now().minusSeconds(Duration.ofDays(13).getSeconds())
		);

		assertNotNull(stream);
		List<Long> ids = stream.collect(Collectors.toList());
		assertTrue(CollectionUtils.isNotEmpty(ids));
		assertEquals(12L, ids.size());
	}

	@Test
	void streamLaunchIdsTest() {

		Stream<Long> stream = launchRepository.streamIdsByStartTimeBefore(1L,
				LocalDateTime.now().minusSeconds(Duration.ofDays(13).getSeconds())
		);

		assertNotNull(stream);
		List<Long> ids = stream.collect(Collectors.toList());
		assertTrue(CollectionUtils.isNotEmpty(ids));
		assertEquals(12L, ids.size());
	}

	@Test
	void findByProjectIdAndStartTimeGreaterThanAndMode() {
		List<Launch> launches = launchRepository.findByProjectIdAndStartTimeGreaterThanAndMode(1L,
				LocalDateTime.now().minusMonths(1),
				LaunchModeEnum.DEFAULT
		);
		assertEquals(12, launches.size());
	}

	@Test
	void loadLaunchesHistory() {
		final String launchName = "launch name 1";
		final long projectId = 1L;
		final long startingLaunchId = 2L;
		final int historyDepth = 2;

		List<Launch> launches = launchRepository.findLaunchesHistory(historyDepth, startingLaunchId, launchName, projectId);
		assertNotNull(launches);
		assertEquals(historyDepth, launches.size());
		launches.forEach(it -> {
			assertThat(it.getName(), Matchers.equalToIgnoringCase(launchName));
			assertEquals(projectId, (long) it.getProjectId());
			assertTrue(it.getId() <= startingLaunchId);
		});
	}

	@Test
	void findAllLatestLaunchesTest() {
		Page<Launch> allLatestByFilter = launchRepository.findAllLatestByFilter(buildDefaultFilter(1L),
				PageRequest.of(0, 2, Sort.by(Sort.Direction.ASC, "number"))
		);
		assertNotNull(allLatestByFilter);
		assertEquals(2, allLatestByFilter.getNumberOfElements());
	}

	@Test
	void getLaunchNamesTest() {
		final String value = "launch";
		List<String> launchNames = launchRepository.getLaunchNamesByModeExcludedByStatus(1L,
				value,
				LaunchModeEnum.DEFAULT,
				StatusEnum.CANCELLED
		);

		assertNotNull(launchNames);
		assertTrue(CollectionUtils.isNotEmpty(launchNames));
		launchNames.forEach(it -> assertTrue(it.contains(value)));
	}

	@Test
	void findLaunchByFilterTest() {
		Sort sort = Sort.by(Sort.Direction.ASC, CRITERIA_LAST_MODIFIED);
		Page<Launch> launches = launchRepository.findByFilter(new CompositeFilter(Operator.AND,
				buildDefaultFilter(1L),
				buildDefaultFilter2()
		), PageRequest.of(0, 2, sort));
		assertNotNull(launches);
		assertEquals(1, launches.getTotalElements());
	}

	@Test
	void getOwnerNames() {
		final List<String> ownerNames = launchRepository.getOwnerNames(1L, "sup", Mode.DEFAULT.name());
		assertNotNull(ownerNames);
		assertEquals(1, ownerNames.size());
		assertTrue(ownerNames.contains("superadmin"));
	}

	@Test
	void findLastRun() {
		final Optional<Launch> lastRun = launchRepository.findLastRun(2L, Mode.DEFAULT.name());
		assertTrue(lastRun.isPresent());
	}

	@Test
	void countLaunches() {
		final Integer count = launchRepository.countLaunches(2L, Mode.DEFAULT.name(), LocalDateTime.now().minusDays(5));
		assertNotNull(count);
		assertEquals(3, (int) count);
	}

	@Test
	void countLaunchesGroupedByOwner() {
		final Map<String, Integer> map = launchRepository.countLaunchesGroupedByOwner(2L,
				Mode.DEFAULT.name(),
				LocalDateTime.now().minusDays(5)
		);
		assertNotNull(map.get("default"));
		assertEquals(3, (int) map.get("default"));
	}

	@Test
	void findIndexLaunchByProjectId() {
		final List<Long> result = launchRepository.findIdsByProjectIdAndModeAndStatusNotEq(2L,
				JLaunchModeEnum.DEFAULT,
				JStatusEnum.PASSED,
				1
		);
		assertEquals(1, result.size());

		final List<Long> secondResult = launchRepository.findIdsByProjectIdAndModeAndStatusNotEq(2L,
				JLaunchModeEnum.DEFAULT,
				JStatusEnum.PASSED,
				2
		);
		assertEquals(2, secondResult.size());
	}

	@Test
	void findIndexLaunchByProjectIdAfterId() {
		final List<Long> result = launchRepository.findIdsByProjectIdAndModeAndStatusNotEqAfterId(2L,
				JLaunchModeEnum.DEFAULT,
				JStatusEnum.PASSED,
				1L,
				3
		);
		assertEquals(3, result.size());

		final List<Long> secondResult = launchRepository.findIdsByProjectIdAndModeAndStatusNotEqAfterId(2L,
				JLaunchModeEnum.DEFAULT,
				JStatusEnum.PASSED,
				100L,
				2
		);
		assertEquals(2, secondResult.size());

		final List<Long> thirdResult = launchRepository.findIdsByProjectIdAndModeAndStatusNotEqAfterId(2L,
				JLaunchModeEnum.DEFAULT,
				JStatusEnum.PASSED,
				200L,
				2
		);
		assertEquals(1, thirdResult.size());
	}

	@Test
	void findIndexLaunchByIdsAndLogLevel() {
		final List<IndexLaunch> result = launchRepository.findIndexLaunchByIdsAndLogLevel(List.of(100L, 200L, 300L),
				0
		);
		assertEquals(2, result.size());
	}

	@Test
	void hasItemsInStatuses() {
		final boolean hasItemsInStatuses = launchRepository.hasItemsInStatuses(100L,
				Lists.newArrayList(JStatusEnum.FAILED, JStatusEnum.SKIPPED)
		);
		assertTrue(hasItemsInStatuses);
	}

	@Test
	void hasItemsWithStatusNotEqual() {
		final boolean hasItemsWithStatusNotEqual = launchRepository.hasRootItemsWithStatusNotEqual(100L, StatusEnum.PASSED.name());
		assertTrue(hasItemsWithStatusNotEqual);
		assertFalse(launchRepository.hasRootItemsWithStatusNotEqual(100L, StatusEnum.PASSED.name(), StatusEnum.FAILED.name()));
	}

	@Test
	void hasItemsWithStatusEqual() {
		boolean hasItemsWithStatusEqual = launchRepository.hasItemsWithStatusEqual(100L, StatusEnum.IN_PROGRESS);
		assertFalse(hasItemsWithStatusEqual);

		hasItemsWithStatusEqual = launchRepository.hasItemsWithStatusEqual(200L, StatusEnum.IN_PROGRESS);
		assertTrue(hasItemsWithStatusEqual);
	}

	@Test
	void hasItems() {
		boolean hasItems = launchRepository.hasItems(300L);
		assertFalse(hasItems);

		hasItems = launchRepository.hasItems(200L);
		assertTrue(hasItems);
	}

	@Test
	void hasRetries() {
		final boolean hasRetries = launchRepository.hasRetries(100L);
		assertTrue(hasRetries);

	}

	@Test
	void hasRetriesNegative() {

		final Long firstLaunchId = 1L;

		final boolean hasRetries = launchRepository.hasRetries(firstLaunchId);
		assertFalse(hasRetries);

	}

	@Test
	void sortingByJoinedColumnTest() {
		PageRequest pageRequest = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, CRITERIA_USER));
		Page<Launch> launchesPage = launchRepository.findByFilter(Filter.builder()
				.withTarget(Launch.class)
				.withCondition(FilterCondition.builder()
						.withCondition(Condition.LOWER_THAN)
						.withSearchCriteria(CRITERIA_ID)
						.withValue("100")
						.build())
				.build(), pageRequest);

		assertTrue(Comparators.isInOrder(launchesPage.getContent(), Comparator.comparing(Launch::getUserId)));
	}

	@Test
	void sortingByJoinedColumnLatestTest() {
		PageRequest pageRequest = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "statistics$defects$product_bug$pb001"));
		Page<Launch> launchesPage = launchRepository.findAllLatestByFilter(Filter.builder()
				.withTarget(Launch.class)
				.withCondition(FilterCondition.builder()
						.withCondition(Condition.LOWER_THAN)
						.withSearchCriteria(CRITERIA_ID)
						.withValue("100")
						.build())
				.build(), pageRequest);

		assertTrue(Comparators.isInOrder(launchesPage.getContent(), Comparator.comparing(Launch::getUserId)));
	}

	@Test
	void testNegativeContainConditionNullDescription() {
		List<Launch> launch = launchRepository.findByFilter(Filter.builder()
				.withTarget(Launch.class)
				.withCondition(FilterCondition.builder()
						.withCondition(Condition.CONTAINS)
						.withNegative(true)
						.withSearchCriteria(CRITERIA_DESCRIPTION)
						.withValue("description")
						.build())
				.build());
		assertThat(launch, Matchers.hasSize(3));
		assertThat(launch.get(0).getDescription(), Matchers.nullValue());
	}

	@Test
	void shouldNotFindLaunchesWithSystemAttributes() {
		List<Launch> launches = launchRepository.findByFilter(Filter.builder()
				.withTarget(Launch.class)
				.withCondition(FilterCondition.builder()
						.withCondition(Condition.HAS)
						.withSearchCriteria(CRITERIA_ITEM_ATTRIBUTE_KEY)
						.withValue("systemKey")
						.build())
				.build());

		assertTrue(launches.isEmpty());

		launches = launchRepository.findByFilter(Filter.builder()
				.withTarget(Launch.class)
				.withCondition(FilterCondition.builder()
						.withCondition(Condition.HAS)
						.withSearchCriteria(CRITERIA_ITEM_ATTRIBUTE_VALUE)
						.withValue("systemValue")
						.build())
				.build());

		assertTrue(launches.isEmpty());
	}

	private Filter buildDefaultFilter(Long projectId) {
		List<ConvertibleCondition> conditionList = Lists.newArrayList(new FilterCondition(Condition.EQUALS,
				false,
				String.valueOf(projectId),
				CRITERIA_PROJECT_ID
		), new FilterCondition(Condition.EQUALS, false, Mode.DEFAULT.toString(), CRITERIA_LAUNCH_MODE));
		return new Filter(Launch.class, conditionList);
	}

	private Filter buildDefaultFilter2() {
		return new Filter(Launch.class, Lists.newArrayList(new FilterCondition(Condition.EQUALS, false, "uuid 11", CRITERIA_LAUNCH_UUID)));
	}
}