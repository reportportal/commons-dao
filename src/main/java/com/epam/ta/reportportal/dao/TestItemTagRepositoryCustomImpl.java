package com.epam.ta.reportportal.dao;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.epam.ta.reportportal.jooq.Tables.*;

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
		return dslContext.selectDistinct(ITEM_ATTRIBUTE.VALUE)
				.from(ITEM_ATTRIBUTE)
				.leftJoin(TEST_ITEM)
				.on(ITEM_ATTRIBUTE.ITEM_ID.eq(TEST_ITEM.ITEM_ID))
				.leftJoin(LAUNCH)
				.on(TEST_ITEM.LAUNCH_ID.eq(LAUNCH.ID))
				.where(LAUNCH.ID.eq(launchId))
				.and(ITEM_ATTRIBUTE.VALUE.likeIgnoreCase("%" + value + "%"))
				.fetch(ITEM_ATTRIBUTE.VALUE);
	}
}
