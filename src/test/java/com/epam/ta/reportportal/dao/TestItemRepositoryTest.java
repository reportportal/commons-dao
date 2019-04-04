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

package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.BaseTest;
import com.epam.ta.reportportal.commons.querygen.Condition;
import com.epam.ta.reportportal.commons.querygen.Filter;
import com.epam.ta.reportportal.commons.querygen.FilterCondition;
import com.epam.ta.reportportal.entity.enums.StatusEnum;
import com.epam.ta.reportportal.entity.item.TestItem;
import com.epam.ta.reportportal.entity.item.issue.IssueType;
import com.google.common.collect.Comparators;
import org.apache.commons.collections.CollectionUtils;
import org.assertj.core.util.Lists;
import org.assertj.core.util.Strings;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.epam.ta.reportportal.commons.querygen.constant.GeneralCriteriaConstant.CRITERIA_LAUNCH_ID;
import static com.epam.ta.reportportal.commons.querygen.constant.TestItemCriteriaConstant.CRITERIA_HAS_RETRIES;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Ivan Budaev
 */
@Sql("/db/fill/item/items-fill.sql")
class TestItemRepositoryTest extends BaseTest {

	@Autowired
	private TestItemRepository testItemRepository;

	@Test
	void streamItemIdsTest() {
		Stream<Long> stream = testItemRepository.streamTestItemIdsByLaunchId(1L);

		assertNotNull(stream);

		List<Long> ids = stream.collect(Collectors.toList());

		assertTrue(CollectionUtils.isNotEmpty(ids), "Ids not found");
		assertEquals(5, ids.size(), "Incorrect ids size");
	}

	@Test
	void hasItemsInStatusAddedLatelyTest() {
		Duration duration = Duration.ofHours(1);
		assertTrue(testItemRepository.hasItemsInStatusAddedLately(1L, duration, StatusEnum.FAILED));
	}

	@Test
	void hasLogsTest() {
		assertFalse(testItemRepository.hasLogs(1L, Duration.ofDays(12).plusHours(23), StatusEnum.IN_PROGRESS));
	}

	@Test
	void selectPathNames() {
		Map<Long, String> results = testItemRepository.selectPathNames("1.2.3");
		assertThat("Incorrect class type", results.getClass(), Matchers.theInstance(LinkedHashMap.class));
		assertThat("Incorrect items size", results.size(), Matchers.equalTo(2));
	}

	@Test
	void testLoadItemsHistory() {
		final String uniqueId = "unqIdSTEP7";

		List<TestItem> items = testItemRepository.loadItemsHistory(Lists.newArrayList(uniqueId), Lists.newArrayList(7L, 8L, 9L));
		assertEquals(3, items.size(), "Incorrect items size");
		items.forEach(it -> assertTrue(it.getUniqueId().equals(uniqueId) && (it.getLaunch().getId() == 7L || it.getLaunch().getId() == 8L
				|| it.getLaunch().getId() == 9L)));
	}

	@Test
	void findTestItemsByLaunchId() {
		final long launchId = 1L;

		List<TestItem> items = testItemRepository.findTestItemsByLaunchId(launchId);
		assertNotNull(items, "Items should not be null");
		assertEquals(5, items.size(), "Incorrect items size");
		items.forEach(it -> assertEquals(launchId, (long) it.getLaunch().getId()));
	}

	@Test
	void findByUuid() {
		final String uuid = "uuid 1";
		final Optional<TestItem> item = testItemRepository.findTestItemByUuid(uuid);
		assertTrue(item.isPresent(), "Item should not be empty");
		assertEquals(uuid, item.get().getUuid(), "Incorrect uniqueId");
	}

	@Test
	void findTestItemsByUniqueId() {
		final String uniqueId = "unqIdSTEP1";
		final List<TestItem> items = testItemRepository.findTestItemsByUniqueId(uniqueId);
		assertNotNull(items, "Items should not be null");
		assertTrue(!items.isEmpty(), "Items should not be empty");
		items.forEach(it -> assertEquals(uniqueId, it.getUniqueId(), "Incorrect uniqueId"));
	}

	@Test
	void findTestItemsByLaunchIdOrderByStartTimeAsc() {
		final Long launchId = 1L;
		final List<TestItem> items = testItemRepository.findTestItemsByLaunchIdOrderByStartTimeAsc(launchId);
		assertNotNull(items, "Items should not be null");
		assertTrue(!items.isEmpty(), "Items should not be empty");
		assertTrue(Comparators.isInOrder(items, Comparator.comparing(TestItem::getStartTime)), "Incorrect order");
	}

	@Test
	void hasChildren() {
		assertTrue(testItemRepository.hasChildren(1L, "1"));
		assertFalse(testItemRepository.hasChildren(5L, "1.2.5"));
	}

	@Test
	void interruptInProgressItems() {
		final Long launchId = 1L;
		testItemRepository.interruptInProgressItems(launchId);
		final List<TestItem> items = testItemRepository.findTestItemsByLaunchId(launchId);
		items.forEach(it -> assertNotEquals(StatusEnum.IN_PROGRESS, it.getItemResults().getStatus(), "Incorrect status"));
	}

	@Test
	void hasStatusNotEqualsWithoutStepItem() {
		assertTrue(testItemRepository.hasStatusNotEqualsWithoutStepItem(1L, 4L, "IN_PROGRESS"));
	}

	@Test
	void selectAllDescendants() {
		final Long itemId = 2L;
		final List<TestItem> items = testItemRepository.selectAllDescendants(itemId);
		assertNotNull(items, "Items should not be null");
		assertTrue(!items.isEmpty(), "Items should not be empty");
		items.forEach(it -> assertEquals(itemId, it.getParent().getItemId(), "Item has incorrect parent id"));
	}

	@Test
	void selectAllDescendantsWithChildren() {
		final Long itemId = 1L;
		final List<TestItem> items = testItemRepository.selectAllDescendantsWithChildren(itemId);
		assertNotNull(items, "Items should not be null");
		assertTrue(!items.isEmpty(), "Items should not be empty");
		items.forEach(it -> assertEquals(itemId, it.getParent().getItemId()));
	}

	@Test
	void selectAllDescendantsWithChildrenNegative() {
		final Long itemId = 2L;
		final List<TestItem> items = testItemRepository.selectAllDescendantsWithChildren(itemId);
		assertNotNull(items, "Items should not be null");
		assertTrue(items.isEmpty(), "Items should be empty");
	}

	@Test
	void selectItemsInStatusByLaunch() {
		final Long launchId = 1L;
		final StatusEnum failedStatus = StatusEnum.FAILED;
		final List<TestItem> items = testItemRepository.selectItemsInStatusByLaunch(launchId, failedStatus);
		assertNotNull(items, "Items should not be null");
		assertTrue(!items.isEmpty(), "Items should not be empty");
		items.forEach(it -> {
			assertEquals(launchId, it.getLaunch().getId(), "Incorrect launch id");
			assertEquals(failedStatus, it.getItemResults().getStatus(), "Incorrect launch status");
		});
	}

	@Test
	void selectItemsInStatusByParent() {
		final Long parentId = 2L;
		final StatusEnum failedStatus = StatusEnum.FAILED;
		final List<TestItem> items = testItemRepository.selectItemsInStatusByParent(parentId, failedStatus);
		assertNotNull(items, "Items should not be null");
		assertTrue(!items.isEmpty(), "Items should not be empty");
		items.forEach(it -> {
			assertEquals(parentId, it.getParent().getItemId(), "Incorrect parent id");
			assertEquals(failedStatus, it.getItemResults().getStatus(), "Incorrect launch status");
		});
	}

	@Test
	void hasItemsInStatusByLaunch() {
		assertTrue(testItemRepository.hasItemsInStatusByLaunch(1L, StatusEnum.FAILED));
	}

	@Test
	void hasItemsInStatusByParent() {
		assertTrue(testItemRepository.hasItemsInStatusByParent(2L, StatusEnum.FAILED));
	}

	@Test
	void selectIdsNotInIssueByLaunch() {
		final List<TestItem> testItems = testItemRepository.selectIdsNotInIssueByLaunch(1L, "pb001");
		assertNotNull(testItems, "Ids should not be null");
		assertTrue(!testItems.isEmpty(), "Ids should not be empty");
		testItems.forEach(it -> assertThat(
				"Issue locator shouldn't be 'pb001'",
				it.getItemResults().getIssue().getIssueType().getLocator(),
				Matchers.not(Matchers.equalTo("pb001"))
		));
	}

	@Test
	void selectByAutoAnalyzedStatus() {
		List<TestItem> testItems = testItemRepository.selectByAutoAnalyzedStatus(false, 1L);
		assertNotNull(testItems);
		assertThat(testItems, Matchers.hasSize(1));
		testItems.forEach(it -> assertThat(it.getItemResults().getIssue().getAutoAnalyzed(), Matchers.is(false)));
	}

	@Test
	void selectItemsInIssueByLaunch() {
		final Long launchId = 1L;
		final String issueType = "ti001";
		final List<TestItem> items = testItemRepository.selectItemsInIssueByLaunch(launchId, issueType);
		assertNotNull(items, "Items should not be null");
		assertTrue(!items.isEmpty(), "Items should not be empty");
		items.forEach(it -> {
			assertEquals(launchId, it.getLaunch().getId(), "Incorrect launch id");
			assertEquals(it.getItemResults().getIssue().getIssueType().getId(), Long.valueOf(1L), "Incorrect item issue");
		});
	}

	@Test
	void identifyStatus() {
		assertEquals(StatusEnum.FAILED, testItemRepository.identifyStatus(1L), "Incorrect status");
	}

	@Test
	void selectIssueLocatorsByProject() {
		final List<IssueType> issueTypes = testItemRepository.selectIssueLocatorsByProject(1L);
		assertNotNull(issueTypes, "IssueTypes should not be null");
		assertEquals(5, issueTypes.size(), "Incorrect size");
	}

	@Test
	void selectIssueTypeByLocator() {
		final String locator = "pb001";
		final Optional<IssueType> issueType = testItemRepository.selectIssueTypeByLocator(1L, locator);
		assertTrue(issueType.isPresent(), "IssueType should be present");
		assertEquals(locator, issueType.get().getLocator(), "Incorrect locator");
	}

	@Test
	void retriesFetchingTest() {

		Filter filter = Filter.builder()
				.withTarget(TestItem.class)
				.withCondition(new FilterCondition(Condition.EQUALS, false, String.valueOf(12L), CRITERIA_LAUNCH_ID))
				.withCondition(new FilterCondition(Condition.EQUALS, false, String.valueOf(true), CRITERIA_HAS_RETRIES))
				.build();

		List<TestItem> items = testItemRepository.findByFilter(filter, PageRequest.of(0, 1)).getContent();

		assertNotNull(items);
		assertEquals(1L, items.size());

		TestItem retriesParent = items.get(0);
		Set<TestItem> retries = retriesParent.getRetries();

		assertEquals(3L, retries.size());

		retries.stream().map(TestItem::getLaunch).forEach(Assertions::assertNull);
		retries.stream().map(TestItem::getRetryOf).forEach(retryOf -> assertEquals(retriesParent.getItemId(), retryOf));
		retries.forEach(retry -> assertEquals(
				Strings.concat(retriesParent.getPath(), ".", String.valueOf(retry.getItemId())),
				retry.getPath()
		));
	}
}