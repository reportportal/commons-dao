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
import com.epam.ta.reportportal.dao.TestItemRepository;
import com.epam.ta.reportportal.entity.enums.StatusEnum;
import com.epam.ta.reportportal.entity.item.TestItem;
import com.epam.ta.reportportal.entity.item.issue.IssueType;
import com.google.common.collect.Comparators;
import org.apache.commons.collections.CollectionUtils;
import org.assertj.core.util.Lists;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;

/**
 * Uses script in db/fill/item
 *
 * @author Ivan Budaev
 */
public class TestItemRepositoryTest extends BaseTest {

	@Autowired
	private TestItemRepository testItemRepository;

	@Test
	public void streamItemIdsTest() {
		Stream<Long> stream = testItemRepository.streamTestItemIdsByLaunchId(1L);

		Assert.assertNotNull(stream);

		List<Long> ids = stream.collect(Collectors.toList());

		assertTrue("Ids not found", CollectionUtils.isNotEmpty(ids));
		assertEquals("Incorrect ids size", 5, ids.size());
	}

	@Test
	public void hasItemsInStatusAddedLatelyTest() {
		Duration duration = Duration.ofHours(1);
		assertTrue(testItemRepository.hasItemsInStatusAddedLately(1L, duration, StatusEnum.FAILED));
	}

	@Test
	public void hasLogsTest() {
		assertFalse(testItemRepository.hasLogs(1L, Duration.ofDays(12).plusHours(23), StatusEnum.IN_PROGRESS));
	}

	@Test
	public void selectPathNames() {
		Map<Long, String> results = testItemRepository.selectPathNames("1.2.3");
		Assert.assertThat("Incorrect class type", results.getClass(), Matchers.theInstance(LinkedHashMap.class));
		Assert.assertThat("Incorrect items size", results.size(), Matchers.equalTo(2));
	}

	@Test
	public void testLoadItemsHistory() {
		final String uniqueId = "unqIdSTEP7";

		List<TestItem> items = testItemRepository.loadItemsHistory(Lists.newArrayList(uniqueId), Lists.newArrayList(7L, 8L, 9L));
		assertEquals("Incorrect items size", 3, items.size());
		items.forEach(it -> assertTrue(it.getUniqueId().equals(uniqueId) && (it.getLaunch().getId() == 7L || it.getLaunch().getId() == 8L
				|| it.getLaunch().getId() == 9L)));
	}

	@Test
	public void findTestItemsByLaunchId() {
		final long launchId = 1L;

		List<TestItem> items = testItemRepository.findTestItemsByLaunchId(launchId);
		Assert.assertNotNull("Items should not be null", items);
		assertEquals("Incorrect items size", 5, items.size());
		items.forEach(it -> assertEquals(launchId, (long) it.getLaunch().getId()));
	}

	@Test
	public void findTestItemsByUniqueId() {
		final String uniqueId = "unqIdSTEP1";
		final List<TestItem> items = testItemRepository.findTestItemsByUniqueId(uniqueId);
		assertNotNull("Items should not be null", items);
		assertTrue("Items should not be empty", !items.isEmpty());
		items.forEach(it -> assertEquals("Incorrect uniqueId", uniqueId, it.getUniqueId()));
	}

	@Test
	public void findTestItemsByLaunchIdOrderByStartTimeAsc() {
		final Long launchId = 1L;
		final List<TestItem> items = testItemRepository.findTestItemsByLaunchIdOrderByStartTimeAsc(launchId);
		assertNotNull("Items should not be null", items);
		assertTrue("Items should not be empty", !items.isEmpty());
		assertTrue("Incorrect order", Comparators.isInOrder(items, Comparator.comparing(TestItem::getStartTime)));
	}

	@Test
	public void hasChildren() {
		assertTrue(testItemRepository.hasChildren(1L, "1"));
		assertFalse(testItemRepository.hasChildren(5L, "1.2.5"));
	}

	@Test
	public void interruptInProgressItems() {
		final Long launchId = 1L;
		testItemRepository.interruptInProgressItems(launchId);
		final List<TestItem> items = testItemRepository.findTestItemsByLaunchId(launchId);
		items.forEach(it -> assertNotEquals("Incorrect status", StatusEnum.IN_PROGRESS, it.getItemResults().getStatus()));
	}

	@Test
	public void hasStatusNotEqualsWithoutStepItem() {
		assertTrue(testItemRepository.hasStatusNotEqualsWithoutStepItem(1L, 4L, "IN_PROGRESS"));
	}

	@Test
	public void selectAllDescendants() {
		final Long itemId = 2L;
		final List<TestItem> items = testItemRepository.selectAllDescendants(itemId);
		assertNotNull("Items should not be null", items);
		assertTrue("Items should not be empty", !items.isEmpty());
		items.forEach(it -> assertEquals("Item has incorrect parent id", itemId, it.getParent().getItemId()));
	}

	@Test
	public void selectAllDescendantsWithChildren() {
		final Long itemId = 1L;
		final List<TestItem> items = testItemRepository.selectAllDescendantsWithChildren(itemId);
		assertNotNull("Items should not be null", items);
		assertTrue("Items should not be empty", !items.isEmpty());
		items.forEach(it -> assertEquals(itemId, it.getParent().getItemId()));
	}

	@Test
	public void selectAllDescendantsWithChildrenNegative() {
		final Long itemId = 2L;
		final List<TestItem> items = testItemRepository.selectAllDescendantsWithChildren(itemId);
		assertNotNull("Items should not be null", items);
		assertTrue("Items should be empty", items.isEmpty());
	}

	@Test
	public void selectItemsInStatusByLaunch() {
		final Long launchId = 1L;
		final StatusEnum failedStatus = StatusEnum.FAILED;
		final List<TestItem> items = testItemRepository.selectItemsInStatusByLaunch(launchId, failedStatus);
		assertNotNull("Items should not be null", items);
		assertTrue("Items should not be empty", !items.isEmpty());
		items.forEach(it -> {
			assertEquals("Incorrect launch id", launchId, it.getLaunch().getId());
			assertEquals("Incorrect launch status", failedStatus, it.getItemResults().getStatus());
		});
	}

	@Test
	public void selectItemsInStatusByParent() {
		final Long parentId = 2L;
		final StatusEnum failedStatus = StatusEnum.FAILED;
		final List<TestItem> items = testItemRepository.selectItemsInStatusByParent(parentId, failedStatus);
		assertNotNull("Items should not be null", items);
		assertTrue("Items should not be empty", !items.isEmpty());
		items.forEach(it -> {
			assertEquals("Incorrect parent id", parentId, it.getParent().getItemId());
			assertEquals("Incorrect launch status", failedStatus, it.getItemResults().getStatus());
		});
	}

	@Test
	public void hasItemsInStatusByLaunch() {
		assertTrue(testItemRepository.hasItemsInStatusByLaunch(1L, StatusEnum.FAILED));
	}

	@Test
	public void hasItemsInStatusByParent() {
		assertTrue(testItemRepository.hasItemsInStatusByParent(2L, StatusEnum.FAILED));
	}

	@Test
	public void selectIdsNotInIssueByLaunch() {
		final List<Long> ids = testItemRepository.selectIdsNotInIssueByLaunch(1L, "pb001");
		assertNotNull("Ids should not be null", ids);
		assertTrue("Ids should not be empty", !ids.isEmpty());
	}

	@Test
	public void selectItemsInIssueByLaunch() {
		final Long launchId = 1L;
		final String issueType = "ti001";
		final List<TestItem> items = testItemRepository.selectItemsInIssueByLaunch(launchId, issueType);
		assertNotNull("Items should not be null", items);
		assertTrue("Items should not be empty", !items.isEmpty());
		items.forEach(it -> assertEquals("Incorrect launch id", launchId, it.getLaunch().getId()));
	}

	@Test
	public void identifyStatus() {
		assertEquals("Incorrect status", StatusEnum.FAILED, testItemRepository.identifyStatus(1L));
	}

	@Test
	public void selectIssueLocatorsByProject() {
		final List<IssueType> issueTypes = testItemRepository.selectIssueLocatorsByProject(1L);
		assertNotNull("IssueTypes should not be null", issueTypes);
		assertEquals("Incorrect size", 5, issueTypes.size());
	}

	@Test
	public void selectIssueTypeByLocator() {
		final String locator = "pb001";
		final Optional<IssueType> issueType = testItemRepository.selectIssueTypeByLocator(1L, locator);
		assertTrue("IssueType should be present", issueType.isPresent());
		assertEquals("Incorrect locator", locator, issueType.get().getLocator());
	}
}