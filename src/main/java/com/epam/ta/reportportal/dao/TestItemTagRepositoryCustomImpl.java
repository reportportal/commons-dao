/*
 * Copyright (C) 2018 EPAM Systems
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
