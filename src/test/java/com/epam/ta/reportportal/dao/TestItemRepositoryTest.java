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
import com.epam.ta.reportportal.entity.ItemAttribute;
import com.epam.ta.reportportal.entity.enums.StatusEnum;
import com.epam.ta.reportportal.entity.enums.TestItemIssueGroup;
import com.epam.ta.reportportal.entity.enums.TestItemTypeEnum;
import com.epam.ta.reportportal.entity.item.TestItem;
import com.epam.ta.reportportal.entity.item.issue.IssueType;
import com.epam.ta.reportportal.jooq.enums.JStatusEnum;
import com.google.common.collect.Comparators;
import org.apache.commons.collections.CollectionUtils;
import org.assertj.core.util.Lists;
import org.assertj.core.util.Strings;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigInteger;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Stream;

import static com.epam.ta.reportportal.commons.querygen.constant.GeneralCriteriaConstant.CRITERIA_LAUNCH_ID;
import static com.epam.ta.reportportal.commons.querygen.constant.ItemAttributeConstant.CRITERIA_ITEM_ATTRIBUTE_SYSTEM;
import static com.epam.ta.reportportal.commons.querygen.constant.TestItemCriteriaConstant.*;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Ivan Budaev
 */
@Sql("/db/fill/item/items-fill.sql")
class TestItemRepositoryTest extends BaseTest {

	@Autowired
	private TestItemRepository testItemRepository;

	@Autowired
	private TicketRepository ticketRepository;

	@Test
	void findTicketsByTerm() {
		List<String> tickets = ticketRepository.findByTerm(1l, "ticket");
		Assertions.assertFalse(tickets.isEmpty());
	}

	@Test
	void findTicketsByTermNegative() {
		List<String> tickets = ticketRepository.findByTerm(1l, "unknown");
		Assertions.assertTrue(tickets.isEmpty());
	}

	@Test
	void streamItemIdsTest() {
		Stream<Long> stream = testItemRepository.streamTestItemIdsByLaunchId(1L);

		assertNotNull(stream);

		List<Long> ids = stream.collect(toList());

		assertTrue(CollectionUtils.isNotEmpty(ids), "Ids not found");
		assertEquals(6, ids.size(), "Incorrect ids size");
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
		assertEquals(7, items.size(), "Incorrect items size");
		items.forEach(it -> assertTrue(it.getUniqueId().equals(uniqueId) && (it.getLaunch().getId() == 7L || it.getLaunch().getId() == 8L
				|| it.getLaunch().getId() == 9L)));
	}

	@Test
	void findTestItemsByLaunchId() {
		final long launchId = 1L;

		List<TestItem> items = testItemRepository.findTestItemsByLaunchId(launchId);
		assertNotNull(items, "Items should not be null");
		assertEquals(6, items.size(), "Incorrect items size");
		items.forEach(it -> assertEquals(launchId, (long) it.getLaunch().getId()));
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
	void interruptInProgressItems() {
		final Long launchId = 1L;
		testItemRepository.interruptInProgressItems(launchId);
		final List<TestItem> items = testItemRepository.findTestItemsByLaunchId(launchId);
		items.forEach(it -> assertNotEquals(StatusEnum.IN_PROGRESS, it.getItemResults().getStatus(), "Incorrect status"));
	}

	@Test
	void hasStatusNotEqualsWithoutStepItem() {
		assertTrue(testItemRepository.hasStatusNotEqualsWithoutStepItem(1L, 4L, StatusEnum.IN_PROGRESS));
	}

	@Test
	void selectIdsByStringPatternMatchedLogMessage() {
		List<Long> itemIds = testItemRepository.selectIdsByStringPatternMatchedLogMessage(1L, 1, 40000, "o");

		Assertions.assertEquals(1, itemIds.size());
	}

	@Test
	void selectIdsByRegexPatternMatchedLogMessage() {
		List<Long> itemIds = testItemRepository.selectIdsByRegexPatternMatchedLogMessage(1L, 1, 40000, "[a-z]{3,3}");

		Assertions.assertEquals(1, itemIds.size());
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
	void deleteByIdTest() {
		testItemRepository.deleteById(1L);
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
		assertTrue(testItemRepository.hasItemsInStatusByParent(2L, "1.2", StatusEnum.FAILED));
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
		List<Long> itemIds = testItemRepository.selectIdsByAutoAnalyzedStatus(false, 1L);
		assertNotNull(itemIds);
		assertThat(itemIds, Matchers.hasSize(1));
	}

	@Test
	void streamIdsByNotHasChildrenAndLaunchIdAndStatus() {

		List<Long> itemIds = testItemRepository.streamIdsByNotHasChildrenAndLaunchIdAndStatus(1L, StatusEnum.FAILED)
				.map(BigInteger::longValue)
				.collect(toList());

		Assertions.assertEquals(2, itemIds.size());
	}

	@Test
	void streamIdsByHasChildrenAndLaunchIdAndStatusOrderedByPathLevel() {

		List<Long> itemIds = testItemRepository.streamIdsByHasChildrenAndLaunchIdAndStatusOrderedByPathLevel(1L, StatusEnum.FAILED)
				.map(BigInteger::longValue)
				.collect(toList());

		Assertions.assertEquals(3, itemIds.size());
	}

	@Test
	void streamIdsByNotHasChildrenAndParentPathAndStatus() {

		List<Long> itemIds = testItemRepository.streamIdsByNotHasChildrenAndParentPathAndStatus("1.2", StatusEnum.FAILED)
				.map(BigInteger::longValue)
				.collect(toList());

		Assertions.assertEquals(2, itemIds.size());
	}

	@Test
	void streamIdsByHasChildrenAndParentPathAndStatusOrderedByPathLevel() {

		List<Long> itemIds = testItemRepository.streamIdsByHasChildrenAndParentPathAndStatusOrderedByPathLevel("1", StatusEnum.FAILED)
				.map(BigInteger::longValue)
				.collect(toList());

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
			assertEquals(launchId, it.getLaunch().getId(), "Incorrect launch id");
			assertEquals(it.getItemResults().getIssue().getIssueType().getId(), Long.valueOf(1L), "Incorrect item issue");
		});
	}

	@Test
	void hasDescendantsWithStatusNotEqual() {
		assertTrue(testItemRepository.hasDescendantsWithStatusNotEqual(1L, JStatusEnum.PASSED), "Incorrect status");
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
		retries.forEach(retry -> assertEquals(Strings.concat(retriesParent.getPath(), ".", String.valueOf(retry.getItemId())),
				retry.getPath()
		));
	}

	@Test
	void updateStatusAndEndTimeAndDurationById() {

		int result = testItemRepository.updateStatusAndEndTimeById(1L, JStatusEnum.CANCELLED, LocalDateTime.now());

		Assertions.assertEquals(1, result);

		Assertions.assertEquals(StatusEnum.CANCELLED, testItemRepository.findById(1L).get().getItemResults().getStatus());
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
				Matchers.lessThan(testItems.get(testItems.size() - 1).getItemResults().getStatus().name())
		);
	}

	@Test
	void findWithUserAttributes() {
		List<TestItem> withoutSystemAttrs = testItemRepository.findByFilter(Filter.builder()
				.withTarget(TestItem.class)
				.withCondition(FilterCondition.builder().eq(CRITERIA_ITEM_ATTRIBUTE_SYSTEM, Boolean.FALSE.toString()).build())
				.build());
		assertTrue(CollectionUtils.isNotEmpty(withoutSystemAttrs));
		withoutSystemAttrs.forEach(it -> assertFalse(it.getAttributes().stream().anyMatch(ItemAttribute::isSystem)));
	}

	@Test
	void findWithSystemAttributes() {
		List<TestItem> withoutUserAttrs = testItemRepository.findByFilter(Filter.builder()
				.withTarget(TestItem.class)
				.withCondition(FilterCondition.builder().eq(CRITERIA_ITEM_ATTRIBUTE_SYSTEM, Boolean.TRUE.toString()).build())
				.build());
		assertTrue(CollectionUtils.isNotEmpty(withoutUserAttrs));
		withoutUserAttrs.forEach(it -> assertTrue(it.getAttributes().stream().anyMatch(ItemAttribute::isSystem)));
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
}