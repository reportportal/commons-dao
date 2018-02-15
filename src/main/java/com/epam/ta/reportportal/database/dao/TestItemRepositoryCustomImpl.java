package com.epam.ta.reportportal.database.dao;

import com.epam.ta.reportportal.database.entity.enums.StatusEnum;
import com.epam.ta.reportportal.database.entity.item.TestItem;
import com.epam.ta.reportportal.database.entity.item.TestItemCommon;
import com.epam.ta.reportportal.database.entity.item.TestItemResults;
import com.epam.ta.reportportal.database.entity.item.TestItemStructure;
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

	private SelectOnConditionStep<Record> commonTestItemDslSelect() {
		return dsl.select()
				.from(TEST_ITEM)
				.join(TEST_ITEM_STRUCTURE)
				.on(TEST_ITEM.ID.eq(TEST_ITEM_STRUCTURE.ITEM_ID))
				.join(TEST_ITEM_RESULTS)
				.on(TEST_ITEM.ID.eq(TEST_ITEM_RESULTS.ITEM_ID));
	}

}
