package com.epam.ta.reportportal.database.dao;

import com.epam.ta.reportportal.BaseDaoTest;
import com.epam.ta.reportportal.database.entity.Status;
import com.epam.ta.reportportal.database.entity.item.TestItem;
import com.epam.ta.reportportal.database.entity.item.TestItemType;
import com.epam.ta.reportportal.database.entity.statistics.ExecutionCounter;
import com.epam.ta.reportportal.database.entity.statistics.IssueCounter;
import com.epam.ta.reportportal.database.entity.statistics.Statistics;
import com.google.common.collect.ImmutableSet;
import org.junit.After;
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
		TestItem testItem = new TestItem();
		String launch = "launch";
		testItem.setLaunchRef(launch);
		TestItem testItem1 = new TestItem();
		testItem1.setLaunchRef(launch);
		testItem1.setType(TestItemType.SUITE);
		testItem1.setName("testName");
		TestItem testItem2 = new TestItem();
		testItem2.setType(TestItemType.SUITE);
		testItem2.setLaunchRef(launch);
		testItemRepository.save(testItem2);
		testItemRepository.save(testItem1);
		testItemRepository.save(testItem);
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

	@After
	public void cleanUp() {
		testItemRepository.deleteAll();
	}
}