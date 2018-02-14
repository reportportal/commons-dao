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
import java.util.Optional;

import static com.epam.ta.reportportal.jooq.Tables.LAUNCH;
import static com.epam.ta.reportportal.jooq.Tables.PROJECT;
import static com.epam.ta.reportportal.jooq.tables.TestItemStructure.TEST_ITEM_STRUCTURE;
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
	public Optional<com.epam.ta.reportportal.database.entity.launch.Launch> getLastLaunch(String launchName, String projectName) {
		return Optional.ofNullable(dsl.select()
				.from(LAUNCH)
				.join(PROJECT)
				.on(LAUNCH.PROJECT_ID.eq(PROJECT.ID))
				.where(LAUNCH.NAME.eq(launchName))
				.and(PROJECT.NAME.eq(projectName))
				.orderBy(LAUNCH.NUMBER.desc())
				.limit(1)
				.fetchOneInto(com.epam.ta.reportportal.database.entity.launch.Launch.class));
	}

	@Override
	public List<LaunchFull> fullLaunchWithStatistics() {
		Launch l = Launch.LAUNCH.as("l");
		TestItem ti = TestItem.TEST_ITEM.as("ti");
		TestItemResults tr = TestItemResults.TEST_ITEM_RESULTS.as("tr");
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
