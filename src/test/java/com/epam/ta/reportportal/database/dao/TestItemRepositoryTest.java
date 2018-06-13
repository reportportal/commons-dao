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
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

public class TestItemRepositoryTest extends BaseDaoTest {

	private static final String testItemId1 = "575eace8bd09be0d4019e81e";
	private static final String testItemId2 = "575eace8bd09be0d4019e82e";
	private static final String testItemId3 = "575eace8bd09be0d4019e83e";
	private static final String testItemId4 = "575eace8bd09be0d4019e84e";

	@Autowired
	private TestItemRepository testItemRepository;

	@Before
	public void setUp() {
		String launch = "launch";

		TestItem testItem = new TestItem();
		testItem.setLaunchRef(launch);
		testItem.setId(testItemId1);
		testItem.setStartTime(new Date());
		testItem.setName("test");
		testItem.setIssue(new TestItemIssue(TestItemIssueType.SYSTEM_ISSUE.getLocator(), null));

		TestItem testItem1 = new TestItem();
		testItem1.setId(testItemId2);
		testItem1.setName("testName");
		testItem1.setStartTime(new Date());
		testItem1.setLaunchRef(launch);
		testItem1.setType(TestItemType.SUITE);
		testItem1.setIssue(new TestItemIssue(TestItemIssueType.NO_DEFECT.getLocator(), null));

		TestItem testItem2 = new TestItem();
		testItem2.setId(testItemId3);
		testItem2.setName("test2");
		testItem2.setStartTime(new Date());
		testItem2.setIssue(new TestItemIssue("nd_custom", null));
		testItem2.setType(TestItemType.SUITE);
		testItem2.setLaunchRef(launch);

		testItemRepository.save(testItem2);
		testItemRepository.save(testItem1);

		testItemRepository.save(testItem);
		TestItem child = new TestItem();
		child.setId(testItemId4);
		child.setName("child");
		child.setStartTime(new Date());
		child.setStatus(Status.FAILED);
		child.setParent(testItem.getId());
		testItemRepository.save(child);
	}

	@Test
	public void findBla() {
		testItemRepository.getGroupedBy(null, "", "");
	}

	@Test
	public void findTestItemWithInvestigated() {
		List<TestItem> ids = testItemRepository.findInIssueTypeItems(TestItemIssueType.SYSTEM_ISSUE.getLocator(), "launch");
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
	public void findIdsWithNameByLaunchesRef() {
		Set<String> ids = testItemRepository.findIdsWithNameByLaunchesRef("testName", ImmutableSet.<String>builder().add("launch").build());
		assertThat(ids.size()).isEqualTo(1);
	}

	@Test
	public void hasChildrenWithStatus() {
		assertThat(testItemRepository.hasChildrenWithStatuses(testItemId1, Status.FAILED)).isTrue();
	}

	@Test
	public void negativeHasChildrenWithStatus() {
		assertThat(testItemRepository.hasChildrenWithStatuses(testItemId1, Status.PASSED, Status.CANCELLED)).isFalse();
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
	public void findWithoutParentByLaunchRef() {
		List<TestItem> items = testItemRepository.findWithoutParentByLaunchRef("launch");
		assertThat(items.size()).isEqualTo(3);
	}

	@Test
	public void findPathNames() {
		ImmutableList<String> ids = ImmutableList.<String>builder().add(testItemId1)
				.add(testItemId2)
				.add(testItemId3)
				.add(testItemId4)
				.build();
		Map<String, String> pathNames = testItemRepository.findPathNames(ids);
		assertThat(pathNames.size()).isEqualTo(ids.size());
		assertThat(pathNames.get(testItemId1)).isEqualTo("test");
		assertThat(pathNames.get(testItemId2)).isEqualTo("testName");
		assertThat(pathNames.get(testItemId3)).isEqualTo("test2");
		assertThat(pathNames.get(testItemId4)).isEqualTo("child");
	}

	@After
	public void cleanUp() {
		testItemRepository.deleteAll();
	}
}