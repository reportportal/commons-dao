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
import com.epam.ta.reportportal.entity.enums.LogLevel;
import com.epam.ta.reportportal.entity.enums.StatusEnum;
import com.epam.ta.reportportal.entity.enums.TestItemIssueGroup;
import com.epam.ta.reportportal.entity.enums.TestItemTypeEnum;
import com.epam.ta.reportportal.entity.item.*;
import com.epam.ta.reportportal.entity.item.history.TestItemHistory;
import com.epam.ta.reportportal.entity.item.issue.IssueEntity;
import com.epam.ta.reportportal.entity.item.issue.IssueType;
import com.epam.ta.reportportal.entity.launch.Launch;
import com.epam.ta.reportportal.entity.log.Log;
import com.epam.ta.reportportal.entity.statistics.Statistics;
import com.epam.ta.reportportal.exception.ReportPortalException;
import com.epam.ta.reportportal.jooq.enums.JStatusEnum;
import com.epam.ta.reportportal.jooq.enums.JTestItemTypeEnum;
import com.epam.ta.reportportal.ws.model.ErrorType;
import com.epam.ta.reportportal.ws.model.analyzer.IndexTestItem;
import com.google.common.collect.Comparators;
import org.apache.commons.collections4.CollectionUtils;
import org.assertj.core.util.Lists;
import org.assertj.core.util.Sets;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.util.Pair;
import org.springframework.test.context.jdbc.Sql;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

import static com.epam.ta.reportportal.commons.querygen.constant.GeneralCriteriaConstant.*;
import static com.epam.ta.reportportal.commons.querygen.constant.IssueCriteriaConstant.CRITERIA_ISSUE_ID;
import static com.epam.ta.reportportal.commons.querygen.constant.LaunchCriteriaConstant.CRITERIA_LAUNCH_MODE;
import static com.epam.ta.reportportal.commons.querygen.constant.LogCriteriaConstant.CRITERIA_LOG_MESSAGE;
import static com.epam.ta.reportportal.commons.querygen.constant.LogCriteriaConstant.CRITERIA_TEST_ITEM_ID;
import static com.epam.ta.reportportal.commons.querygen.constant.TestItemCriteriaConstant.*;
import static com.epam.ta.reportportal.dao.constant.WidgetContentRepositoryConstants.ID;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Ivan Budaev
 */
@Sql({ "/db/fill/item/items-fill.sql", "/db/fill/issue/issue-fill.sql" })
class TestItemRepositoryTest extends BaseTest {

	@Autowired
	private TestItemRepository testItemRepository;

	@Autowired
	private TicketRepository ticketRepository;

	@Test
	void findTicketsByTerm() {
		List<String> tickets = ticketRepository.findByLaunchIdAndTerm(1L, "ticket");
		Assertions.assertFalse(tickets.isEmpty());
	}

	@Test
	void findTicketsByTermNegative() {
		List<String> tickets = ticketRepository.findByLaunchIdAndTerm(1L, "unknown");
		Assertions.assertTrue(tickets.isEmpty());
	}

	@Test
	void findTicketsByProjectIdAndTerm() {
		List<String> tickets = ticketRepository.findByProjectIdAndTerm(1L, "ticket");
		Assertions.assertFalse(tickets.isEmpty());
	}

	@Test
	void findTicketsByProjectIdAndTermNegative() {
		List<String> tickets = ticketRepository.findByProjectIdAndTerm(1L, "unknown");
		Assertions.assertTrue(tickets.isEmpty());
	}

	@Test
	void findTestItemIdsByLaunchId() {

		List<Long> ids = testItemRepository.findTestItemIdsByLaunchId(12L, PageRequest.of(0, 14));

		assertTrue(CollectionUtils.isNotEmpty(ids), "Ids not found");
		assertEquals(14, ids.size(), "Incorrect ids size");
		assertEquals(91, ids.get(0));
		assertEquals(102, ids.get(11));

		List<TestItem> retries = testItemRepository.findAllById(Lists.newArrayList(ids.get(12), ids.get(13)));
		assertEquals(2, retries.size(), "Incorrect ids size");

		retries.stream().map(TestItem::getRetryOf).forEach(Assertions::assertNotNull);
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

	private void assertGroupedItems(Long launchId, Map<Long, List<TestItem>> itemsGroupedByLaunch, int itemsLimitPerLaunch) {
		List<TestItem> items = itemsGroupedByLaunch.get(launchId);
		assertEquals(itemsLimitPerLaunch, items.size());

		items.forEach(item -> assertEquals(launchId, item.getLaunchId()));
	}

	@Test
	void findTestItemsByLaunchId() {
		final long launchId = 1L;

		List<TestItem> items = testItemRepository.findTestItemsByLaunchId(launchId);
		assertNotNull(items, "Items should not be null");
		assertEquals(6, items.size(), "Incorrect items size");
		items.forEach(it -> assertEquals(launchId, (long) it.getLaunchId()));
	}

	@Test
	void findByUuid() {
		final String uuid = "uuid 1_1";
		final Optional<TestItem> item = testItemRepository.findByUuid(uuid);
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
		assertFalse(testItemRepository.hasChildren(3L, "1.2.3"));
	}

	@Test
	void hasChildrenWithStats() {
		assertTrue(testItemRepository.hasChildrenWithStats(1L));
		assertFalse(testItemRepository.hasChildrenWithStats(3L));
	}

	@Test
	void selectPathName() {
		final Optional<Pair<Long, String>> pathName = testItemRepository.selectPath("uuid 1_1");
		assertTrue(pathName.isPresent());
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
		assertTrue(testItemRepository.hasDescendantsNotInStatusExcludingById(1L, 4L, StatusEnum.IN_PROGRESS.name()));
	}

	@Test
	void findByPath() {
		TestItem testItem = testItemRepository.findById(1L).orElseThrow(() -> new ReportPortalException(ErrorType.TEST_ITEM_NOT_FOUND, 1L));
		assertTrue(testItemRepository.findByPath(testItem.getPath()).isPresent());
	}

	@Test
	void findLatestByUniqueIdAndLaunchIdAndParentId() {
		final Optional<Long> latestItem = testItemRepository.findLatestIdByUniqueIdAndLaunchIdAndParentId("unqIdSTEP_R12", 12L, 101L);
		assertTrue(latestItem.isPresent());
	}

	@Test
	void findLatestIdByUniqueIdAndLaunchIdAndParentIdAndItemIdNotEqual() {
		final Optional<Long> latestItem = testItemRepository.findLatestIdByUniqueIdAndLaunchIdAndParentIdAndItemIdNotEqual(
				"unqIdSTEP_R12",
				12L,
				101L,
				100L
		);
		assertTrue(latestItem.isPresent());
	}

	@Test
	void selectIdsByFilter() {
		Filter filter = Filter.builder()
				.withTarget(TestItem.class)
				.withCondition(new FilterCondition(Condition.EQUALS, false, "1", CRITERIA_LAUNCH_ID))
				.withCondition(new FilterCondition(Condition.EQUALS, false, "1", CRITERIA_ISSUE_GROUP_ID))
				.build();

		List<Long> itemIds = testItemRepository.selectIdsByFilter(1L, filter, 1, 0);

		Assertions.assertEquals(1, itemIds.size());
	}

	@Sql("/db/fill/item/items-with-nested-steps.sql")
	@Test
	void selectIdsByHasDescendants() {
		final List<Long> itemIds = testItemRepository.selectIdsByHasDescendants(List.of(130L, 131L, 132L, 133L));
		Assertions.assertEquals(3, itemIds.size());
	}

	@Sql("/db/fill/item/items-with-nested-steps.sql")
	@Test
	void selectIdsByStringLogMessage() {
		final List<Long> result = testItemRepository.selectIdsByStringLogMessage(List.of(130L, 132L), LogLevel.ERROR_INT, "NullPointer");
		Assertions.assertEquals(1, result.size());
		Assertions.assertEquals(130L, result.get(0));
	}

	@Sql("/db/fill/item/items-with-nested-steps.sql")
	@Test
	void selectIdsByPatternLogMessage() {
		final List<Long> result = testItemRepository.selectIdsByRegexLogMessage(List.of(130L, 132L), LogLevel.ERROR_INT, "[A-Za-z]*");
		Assertions.assertEquals(1, result.size());
		Assertions.assertEquals(130L, result.get(0));
	}

	@Sql("/db/fill/item/items-with-nested-steps.sql")
	@Test
	void selectIdsUnderByStringLogMessage() {
		final List<Long> result = testItemRepository.selectIdsUnderByStringLogMessage(
				10L,
				List.of(132L, 133L),
				LogLevel.ERROR_INT,
				"NullPointer"
		);
		Assertions.assertEquals(1, result.size());
		Assertions.assertEquals(132L, result.get(0));
	}

	@Sql("/db/fill/item/items-with-nested-steps.sql")
	@Test
	void selectIdsUnderByRegexLogMessage() {
		final List<Long> result = testItemRepository.selectIdsUnderByRegexLogMessage(
				10L,
				List.of(132L, 133L),
				LogLevel.ERROR_INT,
				"[A-Za-z]*"
		);
		Assertions.assertEquals(1, result.size());
		Assertions.assertEquals(132L, result.get(0));
	}

	@Test
	void selectAllDescendants() {
		final Long itemId = 2L;
		final List<TestItem> items = testItemRepository.selectAllDescendants(itemId);
		assertNotNull(items, "Items should not be null");
		assertTrue(!items.isEmpty(), "Items should not be empty");
		items.forEach(it -> assertEquals(itemId, it.getParentId(), "Item has incorrect parent id"));
	}

	@Test
	void deleteByIdTest() {
		testItemRepository.deleteById(1L);
	}

	@Test
	void selectAllDescendantsWithChildren() {
		final Long itemId = 1L;
		final List<TestItem> items = testItemRepository.selectAllDescendantsWithChildren(itemId);
		assertNotNull(items, "Items should not be null");
		assertTrue(!items.isEmpty(), "Items should not be empty");
		items.forEach(it -> assertEquals(itemId, it.getParentId()));
	}

	@Test
	void selectAllDescendantsWithChildrenNegative() {
		final Long itemId = 3L;
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
			assertEquals(launchId, it.getLaunchId(), "Incorrect launch id");
			assertEquals(failedStatus, it.getItemResults().getStatus(), "Incorrect launch status");
		});
	}

	@Test
	void selectItemsInStatusByParent() {
		final Long parentId = 2L;
		final StatusEnum failedStatus = StatusEnum.FAILED;
		final List<TestItem> items = testItemRepository.selectItemsInStatusByParent(parentId, failedStatus);
		assertNotNull(items, "Items should not be null");
		assertFalse(items.isEmpty(), "Items should not be empty");
		items.forEach(it -> {
			assertEquals(parentId, it.getParentId(), "Incorrect parent id");
			assertEquals(failedStatus, it.getItemResults().getStatus(), "Incorrect launch status");
		});
	}

	@Test
	void hasItemsInStatusByLaunch() {
		assertTrue(testItemRepository.hasItemsInStatusByLaunch(1L, StatusEnum.FAILED));
	}

	@Test
	void hasItemsWithIssueByLaunch() {
		assertTrue(testItemRepository.hasItemsWithIssueByLaunch(1L));
	}

	@Test
	void hasItemsInStatusByParent() {
		assertTrue(testItemRepository.hasItemsInStatusByParent(2L, "1.2", StatusEnum.FAILED.name()));
	}

	@Test
	void hasItemsInStatusByParentNegative() {
		assertFalse(testItemRepository.hasItemsInStatusByParent(2L, "1.2", StatusEnum.SKIPPED.name(), StatusEnum.PASSED.name()));
	}

	@Test
	void findAllNotInIssueByLaunch() {
		final List<TestItem> testItems = testItemRepository.findAllNotInIssueByLaunch(1L, "pb001");
		assertNotNull(testItems, "Ids should not be null");
		assertTrue(!testItems.isEmpty(), "Ids should not be empty");
		testItems.forEach(it -> assertThat("Issue locator shouldn't be 'pb001'",
				it.getItemResults().getIssue().getIssueType().getLocator(),
				Matchers.not(Matchers.equalTo("pb001"))
		));
	}

	@Test
	void selectIdsNotInIssueByLaunch() {
		final List<Long> itemIds = testItemRepository.selectIdsNotInIssueByLaunch(1L, "pb001");
		assertNotNull(itemIds, "Ids should not be null");
		assertTrue(!itemIds.isEmpty(), "Ids should not be empty");
	}

	@Test
	void selectByAutoAnalyzedStatus() {
		List<Long> itemIds = testItemRepository.selectIdsByAnalyzedWithLevelGte(false, 1L, LogLevel.ERROR.toInt());
		assertNotNull(itemIds);
		assertThat(itemIds, Matchers.hasSize(1));
	}

	@Test
	void streamIdsByNotHasChildrenAndLaunchIdAndStatus() {

		List<Long> itemIds = testItemRepository.findIdsByNotHasChildrenAndLaunchIdAndStatus(1L, StatusEnum.FAILED, 1, 0L);
		Assertions.assertEquals(1, itemIds.size());

		itemIds = testItemRepository.findIdsByNotHasChildrenAndLaunchIdAndStatus(1L, StatusEnum.FAILED, 1, 1L);
		Assertions.assertEquals(1, itemIds.size());

		itemIds = testItemRepository.findIdsByNotHasChildrenAndLaunchIdAndStatus(1L, StatusEnum.FAILED, 2, 0L);
		Assertions.assertEquals(2, itemIds.size());
	}

	@Test
	void streamIdsByHasChildrenAndLaunchIdAndStatusOrderedByPathLevel() {

		List<Long> itemIds = testItemRepository.findIdsByHasChildrenAndLaunchIdAndStatusOrderedByPathLevel(1L, StatusEnum.FAILED, 1, 0L);
		Assertions.assertEquals(1, itemIds.size());

		itemIds = testItemRepository.findIdsByHasChildrenAndLaunchIdAndStatusOrderedByPathLevel(1L, StatusEnum.FAILED, 3, 1L);
		Assertions.assertEquals(2, itemIds.size());

		itemIds = testItemRepository.findIdsByHasChildrenAndLaunchIdAndStatusOrderedByPathLevel(1L, StatusEnum.FAILED, 3, 0L);
		Assertions.assertEquals(3, itemIds.size());
	}

	@Test
	void streamIdsByNotHasChildrenAndParentPathAndStatus() {

		List<Long> itemIds = testItemRepository.findIdsByNotHasChildrenAndParentPathAndStatus("1.2", StatusEnum.FAILED, 1, 0L);
		Assertions.assertEquals(1, itemIds.size());

		itemIds = testItemRepository.findIdsByNotHasChildrenAndParentPathAndStatus("1.2", StatusEnum.FAILED, 1, 1L);
		Assertions.assertEquals(1, itemIds.size());

		itemIds = testItemRepository.findIdsByNotHasChildrenAndParentPathAndStatus("1.2", StatusEnum.FAILED, 2, 0L);
		Assertions.assertEquals(2, itemIds.size());
	}

	@Test
	void streamIdsByHasChildrenAndParentPathAndStatusOrderedByPathLevel() {

		List<Long> itemIds = testItemRepository.findIdsByHasChildrenAndParentPathAndStatusOrderedByPathLevel("1", StatusEnum.FAILED, 1, 0L);
		Assertions.assertEquals(1, itemIds.size());

		itemIds = testItemRepository.findIdsByHasChildrenAndParentPathAndStatusOrderedByPathLevel("1", StatusEnum.FAILED, 1, 1L);
		Assertions.assertEquals(1, itemIds.size());

		itemIds = testItemRepository.findIdsByHasChildrenAndParentPathAndStatusOrderedByPathLevel("1", StatusEnum.FAILED, 2, 0L);
		Assertions.assertEquals(2, itemIds.size());
	}

	@Test
	void selectItemsInIssueByLaunch() {
		final Long launchId = 1L;
		final String issueType = "ti001";
		final List<TestItem> items = testItemRepository.selectItemsInIssueByLaunch(launchId, issueType);
		assertNotNull(items, "Items should not be null");
		assertTrue(!items.isEmpty(), "Items should not be empty");
		items.forEach(it -> {
			assertEquals(launchId, it.getLaunchId(), "Incorrect launch id");
			assertEquals(it.getItemResults().getIssue().getIssueType().getId(), Long.valueOf(1L), "Incorrect item issue");
		});
	}

	@Test
	void hasDescendantsWithStatusNotEqual() {
		assertTrue(testItemRepository.hasDescendantsNotInStatus(1L, StatusEnum.PASSED.name()), "Incorrect status");
		assertFalse(testItemRepository.hasDescendantsNotInStatus(1L, StatusEnum.FAILED.name(), StatusEnum.PASSED.name()),
				"Incorrect status"
		);
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
	void selectRetriesTest() {

		Filter filter = Filter.builder()
				.withTarget(TestItem.class)
				.withCondition(new FilterCondition(Condition.EQUALS, false, String.valueOf(12L), CRITERIA_LAUNCH_ID))
				.withCondition(new FilterCondition(Condition.EQUALS, false, String.valueOf(true), CRITERIA_HAS_RETRIES))
				.build();

		List<TestItem> items = testItemRepository.findByFilter(filter, PageRequest.of(0, 1)).getContent();

		TestItem item = items.get(0);

		List<TestItem> retries = testItemRepository.selectRetries(Lists.newArrayList(item.getItemId()));
		assertEquals(3, retries.size());
		retries.forEach(retry -> {
			assertNotNull(retry.getRetryOf());
			assertEquals(item.getItemId(), retry.getRetryOf());
			assertFalse(retry.getParameters().isEmpty());
			assertEquals(3, retry.getParameters().size());
		});
	}

	@Test
	void updateStatusAndEndTimeAndDurationById() {

		int result = testItemRepository.updateStatusAndEndTimeById(1L, JStatusEnum.CANCELLED, LocalDateTime.now());

		Assertions.assertEquals(1, result);

		Assertions.assertEquals(StatusEnum.CANCELLED, testItemRepository.findById(1L).get().getItemResults().getStatus());
	}

	@Test
	void updateStatusAndEndTimeByRetryOfId() {

		final LocalDateTime endTime = LocalDateTime.now();
		int passedUpdated = testItemRepository.updateStatusAndEndTimeByRetryOfId(102L, JStatusEnum.PASSED, JStatusEnum.FAILED, endTime);
		int inProgressUpdated = testItemRepository.updateStatusAndEndTimeByRetryOfId(102L,
				JStatusEnum.IN_PROGRESS,
				JStatusEnum.FAILED,
				endTime
		);

		Assertions.assertEquals(0, passedUpdated);
		Assertions.assertEquals(3, inProgressUpdated);

		final List<TestItem> retries = testItemRepository.selectRetries(Collections.singletonList(102L));
		assertFalse(retries.isEmpty());
		retries.forEach(retry -> assertEquals(StatusEnum.FAILED, retry.getItemResults().getStatus()));
	}

	@Test
	void getStatusByItemId() {

		TestItemTypeEnum type = testItemRepository.getTypeByItemId(1L);

		Assertions.assertEquals(TestItemTypeEnum.SUITE, type);
	}

	@Test
	void findOrderedByStatus() {
		Filter filter = Filter.builder()
				.withTarget(TestItem.class)
				.withCondition(new FilterCondition(Condition.EQUALS, false, "3", CRITERIA_LAUNCH_ID))
				.build();

		Sort sort = Sort.by(Lists.newArrayList(new Sort.Order(Sort.Direction.DESC, CRITERIA_STATUS)));

		List<TestItem> testItems = testItemRepository.findByFilter(filter, PageRequest.of(0, 20, sort)).getContent();

		assertThat(testItems.get(0).getItemResults().getStatus().name(),
				Matchers.greaterThan(testItems.get(testItems.size() - 1).getItemResults().getStatus().name())
		);
	}

	@Test
	void hasParentWithStatus() {

		boolean hasParentWithStatus = testItemRepository.hasParentWithStatus(3L, "1.2.3", StatusEnum.FAILED);

		Assertions.assertTrue(hasParentWithStatus);
	}

	@Test
	void findAllNotInIssueGroupByLaunch() {
		List<TestItem> withoutProductBug = testItemRepository.findAllNotInIssueGroupByLaunch(3L, TestItemIssueGroup.PRODUCT_BUG);
		withoutProductBug.forEach(it -> assertNotEquals(TestItemIssueGroup.PRODUCT_BUG,
				it.getItemResults().getIssue().getIssueType().getIssueGroup().getTestItemIssueGroup()
		));

		List<TestItem> withoutAutomationBug = testItemRepository.findAllNotInIssueGroupByLaunch(3L, TestItemIssueGroup.AUTOMATION_BUG);
		withoutAutomationBug.forEach(it -> assertNotEquals(TestItemIssueGroup.AUTOMATION_BUG,
				it.getItemResults().getIssue().getIssueType().getIssueGroup().getTestItemIssueGroup()
		));

		List<TestItem> withoutSystemIssue = testItemRepository.findAllNotInIssueGroupByLaunch(3L, TestItemIssueGroup.SYSTEM_ISSUE);
		withoutSystemIssue.forEach(it -> assertNotEquals(TestItemIssueGroup.SYSTEM_ISSUE,
				it.getItemResults().getIssue().getIssueType().getIssueGroup().getTestItemIssueGroup()
		));

		List<TestItem> withoutToInvestigate = testItemRepository.findAllNotInIssueGroupByLaunch(3L, TestItemIssueGroup.TO_INVESTIGATE);
		withoutToInvestigate.forEach(it -> assertNotEquals(TestItemIssueGroup.TO_INVESTIGATE,
				it.getItemResults().getIssue().getIssueType().getIssueGroup().getTestItemIssueGroup()
		));

		List<TestItem> withoutNoDefect = testItemRepository.findAllNotInIssueGroupByLaunch(3L, TestItemIssueGroup.NO_DEFECT);
		withoutNoDefect.forEach(it -> assertNotEquals(TestItemIssueGroup.NO_DEFECT,
				it.getItemResults().getIssue().getIssueType().getIssueGroup().getTestItemIssueGroup()
		));
	}

	@Test
	void selectIdsNotInIssueGroupByLaunch() {
		List<Long> withoutProductBug = testItemRepository.selectIdsNotInIssueGroupByLaunch(3L, TestItemIssueGroup.PRODUCT_BUG);
		testItemRepository.findAllById(withoutProductBug)
				.forEach(it -> assertNotEquals(TestItemIssueGroup.PRODUCT_BUG,
						it.getItemResults().getIssue().getIssueType().getIssueGroup().getTestItemIssueGroup()
				));

		List<Long> withoutAutomationBug = testItemRepository.selectIdsNotInIssueGroupByLaunch(3L, TestItemIssueGroup.AUTOMATION_BUG);
		testItemRepository.findAllById(withoutAutomationBug)
				.forEach(it -> assertNotEquals(TestItemIssueGroup.AUTOMATION_BUG,
						it.getItemResults().getIssue().getIssueType().getIssueGroup().getTestItemIssueGroup()
				));

		List<Long> withoutSystemIssue = testItemRepository.selectIdsNotInIssueGroupByLaunch(3L, TestItemIssueGroup.SYSTEM_ISSUE);
		testItemRepository.findAllById(withoutSystemIssue)
				.forEach(it -> assertNotEquals(TestItemIssueGroup.SYSTEM_ISSUE,
						it.getItemResults().getIssue().getIssueType().getIssueGroup().getTestItemIssueGroup()
				));

		List<Long> withoutToInvestigate = testItemRepository.selectIdsNotInIssueGroupByLaunch(3L, TestItemIssueGroup.TO_INVESTIGATE);
		testItemRepository.findAllById(withoutToInvestigate)
				.forEach(it -> assertNotEquals(TestItemIssueGroup.TO_INVESTIGATE,
						it.getItemResults().getIssue().getIssueType().getIssueGroup().getTestItemIssueGroup()
				));

		List<Long> withoutNoDefect = testItemRepository.selectIdsNotInIssueGroupByLaunch(3L, TestItemIssueGroup.NO_DEFECT);
		testItemRepository.findAllById(withoutNoDefect)
				.forEach(it -> assertNotEquals(TestItemIssueGroup.NO_DEFECT,
						it.getItemResults().getIssue().getIssueType().getIssueGroup().getTestItemIssueGroup()
				));
	}

	@Test
	void selectIdsWithIssueByLaunchTest() {
		final long launchId = 1L;

		List<Long> ids = testItemRepository.selectIdsWithIssueByLaunch(launchId);

		assertFalse(ids.isEmpty());

		Set<Long> distinctIds = new HashSet<>(ids);
		assertEquals(ids.size(), distinctIds.size());

		testItemRepository.findAllById(ids).forEach(item -> assertNotNull(item.getItemResults().getIssue()));

		List<TestItem> itemsWithIssue = testItemRepository.findTestItemsByLaunchId(launchId)
				.stream()
				.filter(item -> Objects.nonNull(item.getItemResults().getIssue()))
				.collect(toList());

		assertEquals(itemsWithIssue.size(), ids.size());
	}

	@Test
	void findAllInIssueGroupByLaunch() {
		List<TestItem> withToInvestigate = testItemRepository.findAllInIssueGroupByLaunch(3L, TestItemIssueGroup.TO_INVESTIGATE);
		withToInvestigate.forEach(it -> assertEquals(TestItemIssueGroup.TO_INVESTIGATE,
				it.getItemResults().getIssue().getIssueType().getIssueGroup().getTestItemIssueGroup()
		));

		List<TestItem> withProductBug = testItemRepository.findAllInIssueGroupByLaunch(3L, TestItemIssueGroup.PRODUCT_BUG);
		withProductBug.forEach(it -> assertEquals(TestItemIssueGroup.PRODUCT_BUG,
				it.getItemResults().getIssue().getIssueType().getIssueGroup().getTestItemIssueGroup()
		));
	}

	@Test
	void searchByPatternNameTest() {

		Filter filter = Filter.builder()
				.withTarget(TestItem.class)
				.withCondition(new FilterCondition(Condition.ANY, false, "name2, name3, name4", CRITERIA_PATTERN_TEMPLATE_NAME))
				.build();

		Sort sort = Sort.by(Lists.newArrayList(new Sort.Order(Sort.Direction.ASC, CRITERIA_PATTERN_TEMPLATE_NAME)));

		List<TestItem> items = testItemRepository.findByFilter(filter, PageRequest.of(0, 20, sort)).getContent();

		assertNotNull(items);
		assertEquals(20L, items.size());

	}

	@Test
	void patternTemplateFilteringTest() {

		List<TestItem> collect = testItemRepository.findAll()
				.stream()
				.filter(i -> CollectionUtils.isNotEmpty(i.getPatternTemplateTestItems()))
				.collect(toList());

		Assertions.assertTrue(CollectionUtils.isNotEmpty(collect));
	}

	@Test
	void findParentByChildIdTest() {
		Optional<TestItem> parent = testItemRepository.findParentByChildId(2L);

		Assertions.assertTrue(parent.isPresent());
		Assertions.assertEquals(1L, (long) parent.get().getItemId());
	}

	@Test
	void searchTicket() {
		Filter filter = Filter.builder()
				.withTarget(TestItem.class)
				.withCondition(new FilterCondition(Condition.ANY, false, "ticket_id_3", CRITERIA_TICKET_ID))
				.build();
		List<TestItem> items = testItemRepository.findByFilter(filter);
		assertNotNull(items);
		assertEquals(1L, items.size());

	}

	@Test
	void accumulateStatisticsByFilter() {
		Filter itemFilter = Filter.builder()
				.withTarget(TestItem.class)
				.withCondition(new FilterCondition(Condition.EQUALS, false, "FAILED", CRITERIA_STATUS))
				.build();
		final Set<Statistics> statistics = testItemRepository.accumulateStatisticsByFilter(itemFilter);
		assertNotNull(statistics);
	}

	@Test
	void findAllNestedStepsByIds() {

		Filter logFilter = Filter.builder()
				.withTarget(Log.class)
				.withCondition(new FilterCondition(Condition.IN, false, "1,2,3", CRITERIA_TEST_ITEM_ID))
				.withCondition(new FilterCondition(Condition.CONTAINS, false, "a", CRITERIA_LOG_MESSAGE))
				.build();

		List<NestedStep> allNestedStepsByIds = testItemRepository.findAllNestedStepsByIds(Lists.newArrayList(1L, 2L, 3L), logFilter, false);
		assertNotNull(allNestedStepsByIds);
		assertFalse(allNestedStepsByIds.isEmpty());
		assertEquals(3, allNestedStepsByIds.size());
	}

	@Test
	void findIndexTestItemByLaunchId() {
		final List<IndexTestItem> items = testItemRepository.findIndexTestItemByLaunchId(1L, List.of(JTestItemTypeEnum.STEP));
		assertEquals(3, items.size());
		items.forEach(it -> assertNotNull(it.getTestItemName()));
	}

	@Test
	void findOneByFilterTest() {
		final Filter itemFilter = Filter.builder()
				.withTarget(TestItem.class)
				.withCondition(new FilterCondition(Condition.EQUALS, false, "1", CRITERIA_TEST_CASE_HASH))
				.withCondition(new FilterCondition(Condition.EQUALS, false, "1", CRITERIA_LAUNCH_ID))
				.withCondition(new FilterCondition(Condition.EXISTS, true, "1", CRITERIA_PARENT_ID))
				.build();

		final Sort sort = Sort.by(Sort.Order.desc(CRITERIA_START_TIME));

		final Optional<Long> foundId = testItemRepository.findIdByFilter(itemFilter, sort);

		assertTrue(foundId.isPresent());

	}

	@Test
	void findByLaunchAndTestItemFiltersTest() {

		Filter launchFilter = Filter.builder()
				.withTarget(Launch.class)
				.withCondition(new FilterCondition(Condition.EQUALS, false, "1", CRITERIA_ID))
				.build();

		Filter itemFilter = Filter.builder()
				.withTarget(TestItem.class)
				.withCondition(new FilterCondition(Condition.EQUALS, false, "FAILED", CRITERIA_STATUS))
				.build();

		Page<TestItem> testItems = testItemRepository.findByFilter(false,
				launchFilter,
				itemFilter,
				PageRequest.of(0, 1),
				PageRequest.of(0, 100)
		);

		List<TestItem> content = testItems.getContent();

		Assertions.assertFalse(content.isEmpty());
		Assertions.assertEquals(5, content.size());
	}

	@Test
	void testItemHistoryPage() {
		Filter itemFilter = Filter.builder()
				.withTarget(TestItem.class)
				.withCondition(new FilterCondition(Condition.EQUALS, false, "FAILED", CRITERIA_STATUS))
				.build();

		Sort sort = Sort.by(Lists.newArrayList(new Sort.Order(Sort.Direction.ASC, CRITERIA_START_TIME)));

		Page<TestItemHistory> testItemHistories = testItemRepository.loadItemsHistoryPage(itemFilter,
				PageRequest.of(0, 2, sort),
				1L,
				5,
				true
		);

		assertFalse(testItemHistories.isEmpty());

		testItemHistories = testItemRepository.loadItemsHistoryPage(itemFilter, PageRequest.of(0, 2, sort), 1L, 5, false);

		assertFalse(testItemHistories.isEmpty());
	}

	@Test
	void testItemHistoryEmptyPage() {
		Filter itemFilter = Filter.builder()
				.withTarget(TestItem.class)
				.withCondition(new FilterCondition(Condition.EQUALS, false, "28933", CRITERIA_PARENT_ID))
				.withCondition(new FilterCondition(Condition.EQUALS, false, "DEFAULT", CRITERIA_LAUNCH_MODE))
				.withCondition(new FilterCondition(Condition.EQUALS, false, "1", CRITERIA_PROJECT_ID))
				.build();

		Sort sort = Sort.by(Lists.newArrayList(new Sort.Order(Sort.Direction.ASC, ID)));

		Page<TestItemHistory> testItemHistories = testItemRepository.loadItemsHistoryPage(itemFilter,
				PageRequest.of(0, 20, sort),
				1L,
				5,
				true
		);

		assertTrue(testItemHistories.isEmpty());

		testItemHistories = testItemRepository.loadItemsHistoryPage(itemFilter, PageRequest.of(0, 20, sort), 1L, 5, false);

		assertTrue(testItemHistories.isEmpty());
	}

	@Test
	void testItemHistoryPageWithLaunchName() {
		Filter itemFilter = Filter.builder()
				.withTarget(TestItem.class)
				.withCondition(new FilterCondition(Condition.EQUALS, false, "FAILED", CRITERIA_STATUS))
				.build();

		Sort sort = Sort.by(Lists.newArrayList(new Sort.Order(Sort.Direction.ASC, CRITERIA_START_TIME)));

		Page<TestItemHistory> testItemHistories = testItemRepository.loadItemsHistoryPage(itemFilter,
				PageRequest.of(0, 2, sort),
				1L,
				"launch name 1",
				5,
				true
		);

		assertTrue(testItemHistories.isEmpty());

		testItemHistories = testItemRepository.loadItemsHistoryPage(itemFilter, PageRequest.of(0, 2, sort), 1L, "launch name 1", 5, false);

		assertTrue(testItemHistories.isEmpty());
	}

	@Test
	void testItemHistoryPageWithLaunchIds() {
		Filter itemFilter = Filter.builder()
				.withTarget(TestItem.class)
				.withCondition(new FilterCondition(Condition.EQUALS, false, "FAILED", CRITERIA_STATUS))
				.build();

		Sort sort = Sort.by(Lists.newArrayList(new Sort.Order(Sort.Direction.ASC, CRITERIA_START_TIME)));

		Page<TestItemHistory> testItemHistories = testItemRepository.loadItemsHistoryPage(itemFilter,
				PageRequest.of(0, 2, sort),
				1L,
				com.google.common.collect.Lists.newArrayList(1L, 2L, 3L),
				5,
				true
		);

		assertFalse(testItemHistories.isEmpty());

		testItemHistories = testItemRepository.loadItemsHistoryPage(itemFilter,
				PageRequest.of(0, 2, sort),
				1L,
				com.google.common.collect.Lists.newArrayList(1L, 2L, 3L),
				5,
				false
		);

		assertFalse(testItemHistories.isEmpty());
	}

	@Test
	void testItemHistoryPageWithLaunchFilter() {
		Filter itemFilter = Filter.builder()
				.withTarget(TestItem.class)
				.withCondition(new FilterCondition(Condition.EQUALS, false, "FAILED", CRITERIA_STATUS))
				.build();

		Filter launchFilter = Filter.builder()
				.withTarget(Launch.class)
				.withCondition(new FilterCondition(Condition.EQUALS, false, "FAILED", CRITERIA_STATUS))
				.build();

		Sort sort = Sort.by(Lists.newArrayList(new Sort.Order(Sort.Direction.ASC, CRITERIA_START_TIME)));

		Page<TestItemHistory> testItemHistories = testItemRepository.loadItemsHistoryPage(false,
				launchFilter,
				itemFilter,
				PageRequest.of(0, 5),
				PageRequest.of(0, 2, sort),
				1L,
				5,
				true
		);

		assertTrue(testItemHistories.isEmpty());

		testItemHistories = testItemRepository.loadItemsHistoryPage(false,
				launchFilter,
				itemFilter,
				PageRequest.of(0, 5),
				PageRequest.of(0, 2, sort),
				1L,
				5,
				false
		);

		assertTrue(testItemHistories.isEmpty());
	}

	@Test
	void testItemHistoryPageWithLaunchFilterAndLaunchName() {
		Filter itemFilter = Filter.builder()
				.withTarget(TestItem.class)
				.withCondition(new FilterCondition(Condition.EQUALS, false, "FAILED", CRITERIA_STATUS))
				.build();

		Filter launchFilter = Filter.builder()
				.withTarget(Launch.class)
				.withCondition(new FilterCondition(Condition.EQUALS, false, "FAILED", CRITERIA_STATUS))
				.build();

		Sort sort = Sort.by(Lists.newArrayList(new Sort.Order(Sort.Direction.ASC, CRITERIA_START_TIME)));

		Page<TestItemHistory> testItemHistories = testItemRepository.loadItemsHistoryPage(false,
				launchFilter,
				itemFilter,
				PageRequest.of(0, 5),
				PageRequest.of(0, 2, sort),
				1L,
				"launch name 1",
				5,
				true
		);

		assertTrue(testItemHistories.isEmpty());

		testItemHistories = testItemRepository.loadItemsHistoryPage(false,
				launchFilter,
				itemFilter,
				PageRequest.of(0, 5),
				PageRequest.of(0, 2, sort),
				1L,
				"launch name 1",
				5,
				false
		);

		assertTrue(testItemHistories.isEmpty());
	}

	@Autowired
	private TestItemRepositoryCustomImpl custom;

	@Test
	void stepHistoryWithUniqueIdTest() {

		List<ConvertibleCondition> commonConditions = Lists.newArrayList(FilterCondition.builder().eq(CRITERIA_HAS_STATS, "true").build(),
				FilterCondition.builder().eq(CRITERIA_HAS_CHILDREN, "false").build(),
				FilterCondition.builder().eq(CRITERIA_TYPE, "STEP").build(),
				FilterCondition.builder().eq(CRITERIA_LAUNCH_ID, "1").build()
		);

		Filter baseFilter = new Filter(FilterTarget.TEST_ITEM_TARGET.getClazz(), commonConditions);

		PageRequest pageable = PageRequest.of(0, 20, Sort.by(CRITERIA_ID));
		List<TestItemHistory> content = custom.loadItemsHistoryPage(baseFilter, pageable, 1L, 30, false).getContent();

		assertFalse(content.isEmpty());

	}

	@Test
	void stepHistoryWithTestCaseHashTest() {

		List<ConvertibleCondition> commonConditions = Lists.newArrayList(FilterCondition.builder().eq(CRITERIA_HAS_STATS, "true").build(),
				FilterCondition.builder().eq(CRITERIA_HAS_CHILDREN, "false").build(),
				FilterCondition.builder().eq(CRITERIA_TYPE, "STEP").build(),
				FilterCondition.builder().eq(CRITERIA_LAUNCH_ID, "1").build()
		);

		Filter baseFilter = new Filter(FilterTarget.TEST_ITEM_TARGET.getClazz(), commonConditions);

		PageRequest pageable = PageRequest.of(0, 20, Sort.by(CRITERIA_ID));
		List<TestItemHistory> content = custom.loadItemsHistoryPage(baseFilter, pageable, 1L, 30, true).getContent();

		assertFalse(content.isEmpty());
	}

	@Test
	void findByFilterShouldReturnItemsWithIssueAndWithoutTicketsWhenIssueExistsAndTicketsNotExistFiltersAreSelected() {
		//GIVEN
		Filter filter = Filter.builder()
				.withTarget(TestItem.class)
				.withCondition(new FilterCondition(Condition.EXISTS, false, "TRUE", CRITERIA_ISSUE_ID))
				.withCondition(new FilterCondition(Condition.EXISTS, false, "FALSE", CRITERIA_TICKET_ID))
				.build();

		Sort sort = Sort.by(Lists.newArrayList(new Sort.Order(Sort.Direction.ASC, CRITERIA_START_TIME)));

		//WHEN
		List<TestItem> testItems = testItemRepository.findByFilter(filter, PageRequest.of(0, 20, sort)).getContent();

		//THEN
		assertEquals(2, testItems.size());

		TestItem firstTestItem = testItems.get(0);
		TestItem secondTestItem = testItems.get(1);

		Long expectedFirstTestItemId = 5L;
		assertIssueExistsAndTicketsEmpty(firstTestItem, expectedFirstTestItemId);
		Long expectedSecondTestItemId = 106L;
		assertIssueExistsAndTicketsEmpty(secondTestItem, expectedSecondTestItemId);
	}

	@Test
	void selectAllDescendantsIdsTest() {
		TestItem item = testItemRepository.findById(1L).get();
		List<Long> ids = testItemRepository.selectAllDescendantsIds(item.getPath());

		testItemRepository.findAllById(ids).stream().map(TestItem::getPath).forEach(it -> assertTrue(it.startsWith("1")));
	}

	@Test
	void deleteAllByItemIdTest() {
		ArrayList<Long> ids = Lists.newArrayList(1L, 10L);
		testItemRepository.deleteAllByItemIdIn(ids);

		assertEquals(0, testItemRepository.findAllById(ids).size());
	}

	@Test
	void saveItemWith700CharsParam() {
		String longParam = "pQJlVldHAf4vmEhm9PemBRGjHUCHixdkCfaSpzsPJKWUS29W0wygKgVjiuvu9xe3G4mBcUjjNeOUBqe1ZvM5A9GXYp15NcoVJDrgSBaIJoBdeZId2EEkxGKh0GrL7WMkCAZ36QlzA4JQg52sQgv2S9gdxCc0RteMuau1lxLdzvP8GqRldpvhsYHEBzKhhnes4KcmkLP20zV6nIIj7hdxGRZEPsqKI8vZWcX23P6FQxKtJN3OPVG8wxNekaCAD9e4aOV7XQhHgMk7mx3QCFK4u4KjQv5QF7BKUB4isQM1pMX0gysu6tj5Ss0eWI8Mg6JVb88bm61ByS08indxu7hqefBcLwL3CX6zTAEmeNn2c0BxI06RUFBwZxoa6durIomVhie4JwarzA5dB3qQ9H4UEH6lWqKO95FDH7yYH5CoMDdMCMXwoBnd8Fu61t9KIKrTk06IW1zSaPAPFq00bq2J2cEZk3ybaraMqaNepHX3huw4u7sYxCAXVZnb4COMkXwsFQ5V7ptCiuG4k7ZVgRg1vtQ7WmqbArL86tjGkUSh0f49wkcg2N6eYdBcGC1QNZZoGDQWJzIwydfnoRmGi4Utzt05erQeHa5XpKC05Iii6ZrT6Ib4sZ0QdhCUy8SEuKFxOzcGv7CRenv44Nhv0SdPjEuZ5BEKgAPkIuBknokoOgXAtdL7BFtMwu0IzH7U";
		TestItem item = new TestItem();
		item.setStartTime(LocalDateTime.now());
		item.setName("item");
		item.setPath("1.2.3");
		item.setUniqueId("uniqueID");
		item.setUuid("uuid");
		item.setTestCaseHash(123);
		TestItemResults itemResults = new TestItemResults();
		itemResults.setTestItem(item);
		itemResults.setStatus(StatusEnum.IN_PROGRESS);
		item.setItemResults(itemResults);
		item.setLaunchId(1L);
		item.setType(TestItemTypeEnum.STEP);
		Parameter parameter = new Parameter();
		parameter.setKey(longParam);
		parameter.setValue(longParam);
		item.setParameters(Sets.newLinkedHashSet(parameter));

		testItemRepository.save(item);
	}

	@Test
	void saveItemWith700CharsTestCaseId() {
		String longParam = "pQJlVldHAf4vmEhm9PemBRGjHUCHixdkCfaSpzsPJKWUS29W0wygKgVjiuvu9xe3G4mBcUjjNeOUBqe1ZvM5A9GXYp15NcoVJDrgSBaIJoBdeZId2EEkxGKh0GrL7WMkCAZ36QlzA4JQg52sQgv2S9gdxCc0RteMuau1lxLdzvP8GqRldpvhsYHEBzKhhnes4KcmkLP20zV6nIIj7hdxGRZEPsqKI8vZWcX23P6FQxKtJN3OPVG8wxNekaCAD9e4aOV7XQhHgMk7mx3QCFK4u4KjQv5QF7BKUB4isQM1pMX0gysu6tj5Ss0eWI8Mg6JVb88bm61ByS08indxu7hqefBcLwL3CX6zTAEmeNn2c0BxI06RUFBwZxoa6durIomVhie4JwarzA5dB3qQ9H4UEH6lWqKO95FDH7yYH5CoMDdMCMXwoBnd8Fu61t9KIKrTk06IW1zSaPAPFq00bq2J2cEZk3ybaraMqaNepHX3huw4u7sYxCAXVZnb4COMkXwsFQ5V7ptCiuG4k7ZVgRg1vtQ7WmqbArL86tjGkUSh0f49wkcg2N6eYdBcGC1QNZZoGDQWJzIwydfnoRmGi4Utzt05erQeHa5XpKC05Iii6ZrT6Ib4sZ0QdhCUy8SEuKFxOzcGv7CRenv44Nhv0SdPjEuZ5BEKgAPkIuBknokoOgXAtdL7BFtMwu0IzH7U";
		TestItem item = new TestItem();
		item.setStartTime(LocalDateTime.now());
		item.setName("item");
		item.setPath("1.2.3");
		item.setUniqueId("uniqueID");
		item.setUuid("uuid");
		item.setTestCaseHash(123);
		item.setTestCaseId(longParam);
		TestItemResults itemResults = new TestItemResults();
		itemResults.setTestItem(item);
		itemResults.setStatus(StatusEnum.IN_PROGRESS);
		item.setItemResults(itemResults);
		item.setLaunchId(1L);
		item.setType(TestItemTypeEnum.STEP);
		Parameter parameter = new Parameter();
		item.setParameters(Sets.newLinkedHashSet(parameter));

		testItemRepository.save(item);
	}

	private void assertIssueExistsAndTicketsEmpty(TestItem testItem, Long expectedId) {
		assertEquals(expectedId, testItem.getItemId());

		IssueEntity issue = testItem.getItemResults().getIssue();
		assertNotEquals(null, issue);
		assertEquals(0, issue.getTickets().size());
	}
}