package com.epam.ta.reportportal.database.search;

import static com.epam.ta.reportportal.database.entity.Status.FAILED;
import static com.epam.ta.reportportal.database.search.UpdateStatisticsQueryBuilder.fromItemStatusAware;

import org.junit.Test;
import org.springframework.data.mongodb.core.query.Update;

import com.epam.ta.reportportal.database.entity.Status;
import com.epam.ta.reportportal.database.entity.item.TestItem;
import com.epam.ta.reportportal.database.entity.statistics.ExecutionCounter;
import com.epam.ta.reportportal.database.entity.statistics.IssueCounter;
import com.epam.ta.reportportal.database.entity.statistics.Statistics;

public class UpdateStatisticsQueryBuilderTest {
	@Test
	public void fromIssueTypeAware() throws Exception {
		final TestItem item = new TestItem();
		item.setStatus(Status.PASSED);
		item.setStatistics(new Statistics(new ExecutionCounter(3, 1, 1, 1), new IssueCounter()));
		final Update update = fromItemStatusAware(item, false);
		System.out.println(update);

	}

	@Test
	public void test() {
		final Update update = fromItemStatusAware(FAILED, 1, 1);
		System.out.println(update);
	}
}