package com.epam.ta.reportportal.database.dao;

import com.epam.ta.reportportal.BaseDaoTest;
import com.epam.ta.reportportal.database.entity.Status;
import com.epam.ta.reportportal.database.entity.item.TestItem;
import com.epam.ta.reportportal.database.entity.item.TestItemType;
import com.epam.ta.reportportal.database.entity.item.issue.TestItemIssue;
import com.epam.ta.reportportal.database.entity.item.issue.TestItemIssueType;
import com.epam.ta.reportportal.database.entity.statistics.ExecutionCounter;
import com.epam.ta.reportportal.database.entity.statistics.IssueCounter;
import com.epam.ta.reportportal.database.entity.statistics.Statistics;
import com.google.common.collect.ImmutableSet;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

public class TestItemRepositoryTest extends BaseDaoTest {

	@Autowired
	private TestItemRepository testItemRepository;

	@Before
	public void setUp() {
		String launch = "launch";
		TestItem testItem = new TestItem();
		testItem.setLaunchRef(launch);
		testItem.setId("testItem");
		testItem.setIssue(new TestItemIssue(TestItemIssueType.SYSTEM_ISSUE.getLocator(), null));
		TestItem testItem1 = new TestItem();
		testItem1.setLaunchRef(launch);
		testItem1.setType(TestItemType.SUITE);
		testItem1.setIssue(new TestItemIssue(TestItemIssueType.NO_DEFECT.getLocator(), null));
		testItem1.setName("testName");
		TestItem testItem2 = new TestItem();
		testItem2.setIssue(new TestItemIssue("nd_custom", null));
		testItem2.setType(TestItemType.SUITE);
		testItem2.setLaunchRef(launch);
		testItemRepository.save(testItem2);
		testItemRepository.save(testItem1);
		testItemRepository.save(testItem);
		TestItem child = new TestItem();
		child.setStatus(Status.FAILED);
		child.setParent(testItem.getId());
		testItemRepository.save(child);
	}

	@Test
	public void findTestItemWithInvestigated(){
		List<TestItem> ids = testItemRepository.findTestItemWithInvestigated("launch");
		assertThat(ids.size()).isEqualTo(1);
	}

	@Test
	public void findItemIdsByLaunchRef() {
		List<String> ids = testItemRepository.findItemIdsByLaunchRef(singletonList("launch"));
		assertThat(ids.size()).isEqualTo(3);
	}

	@Test
	public void findItemsWithType() {
		List<TestItem> items = testItemRepository.findItemsWithType("launch", TestItemType.SUITE);
		assertThat(items.size()).isEqualTo(2);
	}

	@Test
	public void findIdsWithNameByLaunchesRef(){
		Set<String> ids = testItemRepository
                .findIdsWithNameByLaunchesRef("testName", ImmutableSet.<String>builder().add("launch").build());
		assertThat(ids.size()).isEqualTo(1);
	}

	@Test
	public void hasChildrenWithStatus(){
		assertThat(testItemRepository.hasChildrenWithStatuses("testItem", Status.FAILED)).isTrue();
	}

	@Test
	public void negativeHasChildrenWithStatus(){
		assertThat(testItemRepository.hasChildrenWithStatuses("testItem", Status.PASSED, Status.CANCELLED)).isFalse();
	}

	@Test
	public void updateExecutionStatisticsTest() {
		final TestItem testItem = new TestItem();
		testItem.setStatistics(new Statistics(new ExecutionCounter(2, 2, 0, 0), new IssueCounter()));
		testItem.setStatus(Status.PASSED);
		testItemRepository.save(testItem);
		testItemRepository.updateExecutionStatistics(testItem);
		final TestItem item = testItemRepository.findOne(testItem.getId());
		final ExecutionCounter executionCounter = item.getStatistics().getExecutionCounter();
		assertThat(executionCounter.getTotal()).isEqualTo(3);
		assertThat(executionCounter.getFailed()).isEqualTo(0);
		assertThat(executionCounter.getPassed()).isEqualTo(3);
		assertThat(executionCounter.getSkipped()).isEqualTo(0);
	}

    @Test
    public void findWithoutParentByLaunchRef () {
        List<TestItem> items = testItemRepository.findWithoutParentByLaunchRef("launch");
        assertThat(items.size()).isEqualTo(3);
    }

	@After
	public void cleanUp() {
		testItemRepository.deleteAll();
	}
}