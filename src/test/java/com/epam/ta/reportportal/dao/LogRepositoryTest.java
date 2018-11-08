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
import com.epam.ta.reportportal.entity.log.Log;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.epam.ta.reportportal.commons.querygen.constant.LogCriteriaConstant.CRITERIA_TEST_ITEM_ID;
import static org.junit.Assert.assertTrue;

/**
 * @author Ivan Budaev
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
@Sql("/test-log-rep-fill.sql")
@Transactional("transactionManager")
public class LogRepositoryTest {

	@Autowired
	private LogRepository logRepository;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	public void getPageNumberTest() {

		Filter filter = Filter.builder()
				.withTarget(Log.class)
				.withCondition(new FilterCondition(Condition.EQUALS, false, "1", CRITERIA_TEST_ITEM_ID))
				.build();

		Integer number = logRepository.getPageNumber(65L, filter, PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "log_time")));
		Assert.assertEquals(6L, (long) number);
	}

	@Test
	public void findByTestItemsAndLogLevel() {
		ArrayList<Long> ids = Lists.newArrayList(3L, 4L);
		Integer logLevel = 30000;

		List<Log> logs = logRepository.findAllByTestItemItemIdInAndLogLevelIsGreaterThanEqual(ids, logLevel);

		assertTrue(logs != null && logs.size() != 0);
		logs.forEach(log -> {
			Long itemId = log.getTestItem().getItemId();
			assertTrue(itemId == 3L || itemId == 4L);
			assertTrue(log.getLogLevel() >= logLevel);
		});
	}
}