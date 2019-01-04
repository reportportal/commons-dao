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
import com.epam.ta.reportportal.commons.querygen.Condition;
import com.epam.ta.reportportal.commons.querygen.Filter;
import com.epam.ta.reportportal.commons.querygen.FilterCondition;
import com.epam.ta.reportportal.dao.LogRepository;
import com.epam.ta.reportportal.entity.enums.StatusEnum;
import com.epam.ta.reportportal.entity.log.Log;
import org.apache.commons.collections.CollectionUtils;
import org.assertj.core.util.Lists;
import org.flywaydb.test.annotation.FlywayTest;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.epam.ta.reportportal.commons.querygen.constant.LogCriteriaConstant.CRITERIA_TEST_ITEM_ID;
import static org.junit.Assert.*;

/**
 * @author Ivan Budaev
 */
public class LogRepositoryTest extends BaseTest {

	private static final String FILL_SCRIPT_PATH = "db/fill/log";

	@Autowired
	private LogRepository logRepository;

	@FlywayTest(locationsForMigrate = { FILL_SCRIPT_PATH }, invokeCleanDB = false)
	@BeforeClass
	public static void before() {
	}

	@Test
	public void getPageNumberTest() {
		Filter filter = Filter.builder()
				.withTarget(Log.class)
				.withCondition(new FilterCondition(Condition.EQUALS, false, "1", CRITERIA_TEST_ITEM_ID))
				.build();

		Integer number = logRepository.getPageNumber(1L, filter, PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "log.log_time")));
		Assert.assertEquals(1L, (long) number);
	}

	@Test
	public void findByTestItemsAndLogLevel() {
		ArrayList<Long> ids = Lists.newArrayList(1L);
		Integer logLevel = 30000;

		List<Log> logs = logRepository.findAllByTestItemItemIdInAndLogLevelIsGreaterThanEqual(ids, logLevel);

		assertTrue(logs != null && logs.size() != 0);
		logs.forEach(log -> {
			Long itemId = log.getTestItem().getItemId();
			assertEquals(1L, (long) itemId);
			assertTrue(log.getLogLevel() >= logLevel);
		});
	}

	@Test
	public void findLogsWithThumbnailByTestItemIdAndPeriodTest() {
		Duration duration = Duration.ofDays(6).plusHours(23);
		final Long itemId = 1L;

		List<Log> logs = logRepository.findLogsWithThumbnailByTestItemIdAndPeriod(itemId, duration);

		Assert.assertNotNull(logs);
		Assert.assertTrue(CollectionUtils.isNotEmpty(logs));
		Assert.assertEquals(3, logs.size());
	}

	@Test
	public void hasLogsAddedLatelyTest() {
		Assert.assertTrue(logRepository.hasLogsAddedLately(Duration.ofDays(13).plusHours(23), 1L, StatusEnum.IN_PROGRESS));
	}

	@Test
	public void clearLogsAttachmentsAndThumbnailsTest() {
		ArrayList<Long> logIds = Lists.newArrayList(1L, 2L, 3L);
		logRepository.clearLogsAttachmentsAndThumbnails(logIds);

		List<Log> logs = logRepository.findAllById(logIds);

		logs.forEach(log -> {
			Assert.assertNull(log.getAttachment());
			Assert.assertNull(log.getAttachmentThumbnail());
		});
	}

	@Test
	public void deleteByPeriodAndTestItemIdsTest() {
		int removedLogsCount = logRepository.deleteByPeriodAndTestItemIds(Duration.ofDays(13).plusHours(20), Collections.singleton(1L));
		Assert.assertEquals(3, removedLogsCount);
	}

	@Test
	public void hasLogsTest() {
		assertTrue(logRepository.hasLogs(1L));
		assertFalse(logRepository.hasLogs(100L));
	}

	@Test
	public void findByTestItemIdWithLimitTest() {
		final List<Log> logs = logRepository.findByTestItemId(1L, 2);
		assertNotNull(logs);
		assertEquals(2, logs.size());
		logs.forEach(it -> assertEquals(1L, (long) it.getTestItem().getItemId()));
	}

	@Test
	public void findByTestItemId() {
		final Long itemId = 1L;
		final List<Log> logs = logRepository.findByTestItemId(itemId);
		assertNotNull(logs);
		assertTrue(!logs.isEmpty());
		logs.forEach(it -> assertEquals(itemId, it.getTestItem().getItemId()));
	}
}