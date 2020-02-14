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
import com.epam.ta.reportportal.entity.attachment.Attachment;
import com.epam.ta.reportportal.entity.enums.LogLevel;
import com.epam.ta.reportportal.entity.enums.StatusEnum;
import com.epam.ta.reportportal.entity.log.Log;
import org.apache.commons.collections.CollectionUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.jdbc.Sql;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.epam.ta.reportportal.commons.querygen.constant.LogCriteriaConstant.*;
import static com.epam.ta.reportportal.commons.querygen.constant.TestItemCriteriaConstant.CRITERIA_STATUS;
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
	void findItemLogIdsByLaunchId() {
		List<Long> logIdsByLaunch = logRepository.findItemLogIdsByLaunchIdAndLogLevelGte(1L, LogLevel.DEBUG.toInt());
		assertEquals(7, logIdsByLaunch.size());
	}

	@Test
	void findItemLogIdsByLaunchIds() {
		List<Long> logIds = logRepository.findItemLogIdsByLaunchIdsAndLogLevelGte(Arrays.asList(1L, 2L), LogLevel.DEBUG.toInt());
		assertEquals(7, logIds.size());
	}

	@Test
	void findIdsByItemIds() {
		List<Long> errorIds = logRepository.findIdsUnderTestItemByLaunchIdAndTestItemIdsAndLogLevelGte(1L,
				Arrays.asList(1L, 2L, 3L),
				LogLevel.DEBUG.toInt()
		);
		List<Log> errorLogs = logRepository.findAllById(errorIds);
		errorLogs.forEach(log -> assertEquals(40000, log.getLogLevel()));
		assertEquals(7, errorIds.size());
		assertEquals(7, errorLogs.size());

		List<Long> ids = logRepository.findIdsByTestItemIdsAndLogLevelGte(Arrays.asList(1L, 2L, 3L), LogLevel.FATAL.toInt());
		assertEquals(0, ids.size());
	}

	@Test
	void findIdsByItemIdsAndLogLevelGte() {
		List<Long> errorIds = logRepository.findIdsByTestItemIdsAndLogLevelGte(Arrays.asList(1L, 2L, 3L), LogLevel.DEBUG.toInt());
		List<Log> errorLogs = logRepository.findAllById(errorIds);
		errorLogs.forEach(log -> assertEquals(40000, log.getLogLevel()));
		assertEquals(7, errorIds.size());
		assertEquals(7, errorLogs.size());

		List<Long> ids = logRepository.findIdsByTestItemIdsAndLogLevelGte(Arrays.asList(1L, 2L, 3L), LogLevel.FATAL.toInt());
		assertEquals(0, ids.size());
	}

	@Test
	void findAllUnderTestItemByLaunchIdAndTestItemIdsAndLogLevelGte() {

		int logLevel = LogLevel.WARN_INT;

		List<Long> itemIds = Arrays.asList(1L, 2L, 3L);
		List<Log> logs = logRepository.findAllUnderTestItemByLaunchIdAndTestItemIdsAndLogLevelGte(1L, itemIds, logLevel);

		assertTrue(logs != null && logs.size() != 0, "Logs should be not null or empty");
		logs.forEach(log -> {
			Long itemId = log.getTestItem().getItemId();
			assertTrue(itemIds.contains(itemId), "Incorrect item id");
			assertTrue(log.getLogLevel() >= logLevel, "Unexpected log level");
		});
	}

	@Test
	void findNestedItemsTest() {

		Filter filter = Filter.builder()
				.withTarget(Log.class)
				.withCondition(new FilterCondition(Condition.EQUALS, false, "2", CRITERIA_TEST_ITEM_ID))
				.withCondition(new FilterCondition(Condition.IN, false, "FAILED, PASSED", CRITERIA_STATUS))
				.build();

		logRepository.findNestedItems(2L, false, false, filter, PageRequest.of(2, 1));
	}

	@Test
	void findIdsByFilter() {

		Filter failedStatusFilter = Filter.builder()
				.withTarget(Log.class)
				.withCondition(FilterCondition.builder().eq(CRITERIA_STATUS, "FAILED").build())
				.build();

		List<Long> ids = logRepository.findIdsByFilter(failedStatusFilter);

		assertEquals(7, ids.size());
	}

	@Test
	void findAllWithAttachment() {
		Filter logWithAttachmentsFilter = Filter.builder()
				.withTarget(Log.class)
				.withCondition(FilterCondition.builder()
						.withCondition(Condition.EXISTS)
						.withSearchCriteria(CRITERIA_LOG_BINARY_CONTENT)
						.withValue("1")
						.build())
				.build();

		Page<Log> logPage = logRepository.findByFilter(logWithAttachmentsFilter, PageRequest.of(0, 10));

		List<Log> logs = logPage.getContent();
		assertFalse(logs.isEmpty());

		logs.forEach(log -> {
			Attachment attachment = log.getAttachment();
			assertNotNull(attachment);
			assertNotNull(attachment.getId());
			assertNotNull(attachment.getFileId());
			assertNotNull(attachment.getContentType());
			assertNotNull(attachment.getThumbnailId());
		});

		assertEquals(10, logs.size());
	}

	@Sql("/db/fill/item/items-with-nested-steps.sql")
	@Test
	void findLogMessagesByItemIdAndLogLevelNestedTest() {
		List<String> messagesByItemIdAndLevelGte = logRepository.findMessagesByItemIdAndLevelGte(132L, LogLevel.ERROR.toInt());
		assertTrue(CollectionUtils.isNotEmpty(messagesByItemIdAndLevelGte));
		assertEquals(1, messagesByItemIdAndLevelGte.size());
		assertEquals("java.lang.NullPointerException: Oops\n"
				+ "\tat java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n"
				+ "\tat java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\n"
				+ "\tat java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n"
				+ "\tat java.base/java.lang.reflect.Method.invoke(Method.java:566)\n", messagesByItemIdAndLevelGte.get(0));
	}

	@Test
	void findLogMessagesByItemIdAndLoLevelGteTest() {
		List<String> messagesByItemIdAndLevelGte = logRepository.findMessagesByItemIdAndLevelGte(3L, LogLevel.WARN.toInt());
		assertTrue(CollectionUtils.isNotEmpty(messagesByItemIdAndLevelGte));
		assertEquals(7, messagesByItemIdAndLevelGte.size());
		messagesByItemIdAndLevelGte.forEach(it -> assertEquals("log", it));
	}
}