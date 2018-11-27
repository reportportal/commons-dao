package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.jooq.tables.JItemTag;
import com.epam.ta.reportportal.jooq.tables.JLaunch;
import com.epam.ta.reportportal.jooq.tables.JTestItem;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.epam.ta.reportportal.jooq.Tables.ITEM_TAG;

/**
 * @author Ivan Budaev
 */
@Repository
public class TestItemTagRepositoryCustomImpl implements TestItemTagRepositoryCustom {

	private final DSLContext dslContext;

	@Autowired
	public TestItemTagRepositoryCustomImpl(DSLContext dslContext) {

		this.dslContext = dslContext;
	}

	@Override
	public List<String> findDistinctByLaunchIdAndValue(Long launchId, String value) {

		JTestItem i = JTestItem.TEST_ITEM.as("t");
		JItemTag it = JItemTag.ITEM_TAG.as("it");
		JLaunch l = JLaunch.LAUNCH.as("l");

		return dslContext.selectDistinct(it.VALUE)
				.from(it)
				.leftJoin(i)
				.on(it.ITEM_ID.eq(i.ITEM_ID))
				.leftJoin(l)
				.on(i.LAUNCH_ID.eq(l.ID))
				.where(l.ID.eq(launchId))
				.and(it.VALUE.like("%" + value + "%"))
				.fetch(ITEM_TAG.VALUE);
	}
}
