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
