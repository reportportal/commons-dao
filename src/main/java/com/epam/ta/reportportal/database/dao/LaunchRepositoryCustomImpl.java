package com.epam.ta.reportportal.database.dao;

import com.epam.ta.reportportal.database.entity.launch.ExecutionStatistics;
import com.epam.ta.reportportal.database.entity.launch.LaunchFull;
import com.epam.ta.reportportal.jooq.enums.StatusEnum;
import com.epam.ta.reportportal.jooq.tables.Launch;
import com.epam.ta.reportportal.jooq.tables.TestItem;
import com.epam.ta.reportportal.jooq.tables.TestItemResults;
import com.epam.ta.reportportal.jooq.tables.TestItemStructure;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.epam.ta.reportportal.jooq.Tables.*;
import static org.jooq.impl.DSL.*;

/**
 * @author Pavel Bortnik
 */
@Repository
public class LaunchRepositoryCustomImpl implements LaunchRepositoryCustom {

	private DSLContext dsl;

	@Autowired
	public void setDsl(DSLContext dsl) {
		this.dsl = dsl;
	}

	@Override
	public Boolean hasItems(Long launchId) {
		return dsl.fetchExists(dsl.selectOne().from(TEST_ITEM_STRUCTURE).where(TEST_ITEM_STRUCTURE.LAUNCH_ID.eq(launchId)));
	}

	@Override
	public Boolean checkStatus(Long launchId) {
		return dsl.fetchExists(dsl.selectOne()
				.from(TEST_ITEM)
				.join(TEST_ITEM_STRUCTURE)
				.on(TEST_ITEM.ID.eq(TEST_ITEM_STRUCTURE.ITEM_ID))
				.join(TEST_ITEM_RESULTS)
				.on(TEST_ITEM.ID.eq(TEST_ITEM_RESULTS.ITEM_ID))
				.where(TEST_ITEM_STRUCTURE.LAUNCH_ID.eq(launchId)
						.and(TEST_ITEM_RESULTS.STATUS.eq(StatusEnum.FAILED).or(TEST_ITEM_RESULTS.STATUS.eq(StatusEnum.SKIPPED)))));
	}

	@Override
	public List<LaunchFull> fullLaunchWithStatistics() {
		Launch l = Launch.LAUNCH.as("l");
		TestItem ti = TEST_ITEM.as("ti");
		TestItemResults tr = TEST_ITEM_RESULTS.as("tr");
		TestItemStructure tis = TEST_ITEM_STRUCTURE.as("tis");
		return dsl.select(l.ID, l.PROJECT_ID, l.USER_ID, l.NAME, l.DESCRIPTION, l.START_TIME, l.NUMBER, l.LAST_MODIFIED, l.MODE,
				sum(when(tr.STATUS.eq(StatusEnum.PASSED), 1).otherwise(0)).as("passed"),
				sum(when(tr.STATUS.eq(StatusEnum.FAILED), 1).otherwise(0)).as("failed"),
				sum(when(tr.STATUS.eq(StatusEnum.SKIPPED), 1).otherwise(0)).as("skipped"), count(tr.STATUS).as("total")
		)
				.from(ti)
				.join(tr)
				.on(ti.ID.eq(tr.ITEM_ID))
				.join(tis)
				.on(ti.ID.eq(tis.ITEM_ID))
				.join(l)
				.on(l.ID.eq(tis.LAUNCH_ID))
				.groupBy(l.ID, l.PROJECT_ID, l.USER_ID, l.NAME, l.DESCRIPTION, l.START_TIME, l.NUMBER, l.LAST_MODIFIED, l.MODE)
				.fetch(r -> new LaunchFull(r.into(com.epam.ta.reportportal.database.entity.launch.Launch.class),
						r.into(ExecutionStatistics.class)
				));
	}
}
