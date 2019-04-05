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
import com.epam.ta.reportportal.commons.querygen.FilterCondition;
import com.epam.ta.reportportal.entity.enums.StatusEnum;
import com.epam.ta.reportportal.entity.log.Log;
import org.apache.commons.collections.CollectionUtils;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.jdbc.Sql;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.epam.ta.reportportal.commons.querygen.constant.LogCriteriaConstant.CRITERIA_LOG_TIME;
import static com.epam.ta.reportportal.commons.querygen.constant.LogCriteriaConstant.CRITERIA_TEST_ITEM_ID;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Ivan Budaev
 */
@Sql("/db/fill/item/items-fill.sql")
class LogRepositoryTest extends BaseTest {

	@Autowired
	private LogRepository logRepository;

	@Test
	void getPageNumberTest() {
		Filter filter = Filter.builder()
				.withTarget(Log.class)
				.withCondition(new FilterCondition(Condition.EQUALS, false, "3", CRITERIA_TEST_ITEM_ID))
				.build();

		Integer number = logRepository.getPageNumber(1L, filter, PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, CRITERIA_LOG_TIME)));
		assertEquals(1L, (long) number, "Unexpected log page number");
	}

	@Test
	void findByTestItemsAndLogLevel() {
		ArrayList<Long> ids = Lists.newArrayList(3L);
		Integer logLevel = 30000;

		List<Log> logs = logRepository.findAllByTestItemItemIdInAndLogLevelIsGreaterThanEqual(ids, logLevel);

		assertTrue(logs != null && logs.size() != 0, "Logs should be not null or empty");
		logs.forEach(log -> {
			Long itemId = log.getTestItem().getItemId();
			assertEquals(3L, (long) itemId, "Incorrect item id");
			assertTrue(log.getLogLevel() >= logLevel, "Unexpected log level");
		});
	}

	@Test
	void findLogsWithThumbnailByTestItemIdAndPeriodTest() {
		Duration duration = Duration.ofDays(6).plusHours(23);
		final Long itemId = 3L;

		List<Log> logs = logRepository.findLogsWithThumbnailByTestItemIdAndPeriod(itemId, duration);

		assertNotNull(logs, "Logs should not be null");
		assertTrue(CollectionUtils.isNotEmpty(logs), "Logs should not be empty");
		assertEquals(3, logs.size(), "Incorrect count of logs");
	}

	@Test
	void hasLogsAddedLatelyTest() {
		assertTrue(logRepository.hasLogsAddedLately(Duration.ofDays(13).plusHours(23), 1L, StatusEnum.FAILED));
	}

	@Test
	void deleteByPeriodAndTestItemIdsTest() {
		int removedLogsCount = logRepository.deleteByPeriodAndTestItemIds(Duration.ofDays(13).plusHours(20), Collections.singleton(3L));
		assertEquals(3, removedLogsCount, "Incorrect count of deleted logs");
	}

	@Test
	void hasLogsTest() {
		assertTrue(logRepository.hasLogs(3L));
		assertFalse(logRepository.hasLogs(100L));
	}

	@Test
	void findByTestItemIdWithLimitTest() {
		final long itemId = 3L;

		final List<Log> logs = logRepository.findByTestItemId(itemId, 2);

		assertNotNull(logs, "Logs should not be null");
		assertEquals(2, logs.size(), "Unexpected logs count");
		logs.forEach(it -> assertEquals(itemId, (long) it.getTestItem().getItemId(), "Log has incorrect item id"));
	}

	@Test
	void findByTestItemId() {
		final Long itemId = 3L;

		final List<Log> logs = logRepository.findByTestItemId(itemId);

		assertNotNull(logs, "Logs should not be null");
		assertTrue(!logs.isEmpty(), "Logs should not be empty");
		logs.forEach(it -> assertEquals(itemId, it.getTestItem().getItemId(), "Log has incorrect item id"));
	}

	@Test
	void findIdsByTestItemId() {
		final long itemId = 3L;

		final List<Long> logIds = logRepository.findIdsByTestItemId(itemId);

		assertNotNull(logIds, "Log ids should not be null");
		assertTrue(!logIds.isEmpty(), "Log ids should not be empty");
		assertEquals(7, logIds.size());
	}

	@Test
	void findByLaunchId() {
		List<Long> logIdsByLaunch = logRepository.findLogIdsByLaunch(1L);
		assertEquals(7, logIdsByLaunch.size());
	}

	@Test
	void findByItemIds() {
		List<Long> idsByTestItemIds = logRepository.findIdsByTestItemIds(Arrays.asList(1L, 2L, 3L));
		assertEquals(7, idsByTestItemIds.size());
	}
}