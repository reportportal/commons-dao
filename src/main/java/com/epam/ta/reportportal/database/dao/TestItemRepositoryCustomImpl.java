package com.epam.ta.reportportal.database.dao;

import com.epam.ta.reportportal.database.entity.enums.StatusEnum;
import com.epam.ta.reportportal.database.entity.item.TestItem;
import com.epam.ta.reportportal.database.entity.item.TestItemCommon;
import com.epam.ta.reportportal.database.entity.item.TestItemResults;
import com.epam.ta.reportportal.database.entity.item.TestItemStructure;
import com.epam.ta.reportportal.database.entity.item.issue.Issue;
import com.epam.ta.reportportal.database.entity.item.issue.IssueType;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.SelectOnConditionStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.epam.ta.reportportal.jooq.Tables.*;

/**
 * @author Pavel Bortnik
 */
@Repository
public class TestItemRepositoryCustomImpl implements TestItemRepositoryCustom {

	private DSLContext dsl;

	@Autowired
	public void setDsl(DSLContext dsl) {
		this.dsl = dsl;
	}

	@Override
	public List<TestItemCommon> selectItemsInStatusByLaunch(Long launchId, StatusEnum status) {
		com.epam.ta.reportportal.jooq.enums.StatusEnum statusEnum = com.epam.ta.reportportal.jooq.enums.StatusEnum.valueOf(status.name());
		return commonTestItemDslSelect().where(TEST_ITEM_STRUCTURE.LAUNCH_ID.eq(launchId).and(TEST_ITEM_RESULTS.STATUS.eq(statusEnum)))
				.fetch(r -> {
					TestItemCommon testItemCommon = new TestItemCommon();
					testItemCommon.setTestItem(r.into(TestItem.class));
					testItemCommon.setTestItemStructure(r.into(TestItemStructure.class));
					testItemCommon.setTestItemResults(r.into(TestItemResults.class));
					return testItemCommon;
				});
	}

	@Override
	public List<Long> selectIdsNotInIssueByLaunch(Long launchId, String issueType) {
		return dsl.select(TEST_ITEM.ID)
				.from(TEST_ITEM)
				.join(TEST_ITEM_STRUCTURE)
				.on(TEST_ITEM.ID.eq(TEST_ITEM_STRUCTURE.ITEM_ID))
				.join(TEST_ITEM_RESULTS)
				.on(TEST_ITEM.ID.eq(TEST_ITEM_RESULTS.ITEM_ID))
				.join(ISSUE)
				.on(ISSUE.TEST_ITEM_RESULTS_ID.eq(TEST_ITEM_RESULTS.ID))
				.join(ISSUE_TYPE)
				.on(ISSUE.ISSUE_TYPE.eq(ISSUE_TYPE.ID))
				.where(TEST_ITEM_STRUCTURE.LAUNCH_ID.eq(launchId))
				.and(ISSUE_TYPE.LOCATOR.ne(issueType))
				.fetchInto(Long.class);
	}

	@Override
	public List<TestItemCommon> selectItemsInIssueByLaunch(Long launchId, String issueType) {
		return commonTestItemDslSelect().join(ISSUE)
				.on(ISSUE.TEST_ITEM_RESULTS_ID.eq(TEST_ITEM_RESULTS.ID))
				.join(ISSUE_TYPE)
				.on(ISSUE.ISSUE_TYPE.eq(ISSUE_TYPE.ID))
				.where(TEST_ITEM_STRUCTURE.LAUNCH_ID.eq(launchId))
				.and(ISSUE_TYPE.LOCATOR.eq(issueType))
				.fetch(r -> new TestItemCommon(r.into(TestItem.class), r.into(TestItemResults.class), r.into(TestItemStructure.class),
						r.into(Issue.class), r.into(IssueType.class)
				));
	}

	private SelectOnConditionStep<Record> commonTestItemDslSelect() {
		return dsl.select()
				.from(TEST_ITEM)
				.join(TEST_ITEM_STRUCTURE)
				.on(TEST_ITEM.ID.eq(TEST_ITEM_STRUCTURE.ITEM_ID))
				.join(TEST_ITEM_RESULTS)
				.on(TEST_ITEM.ID.eq(TEST_ITEM_RESULTS.ITEM_ID));
	}

}
