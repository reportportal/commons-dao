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

import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.TableField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.epam.ta.reportportal.jooq.Tables.*;

/**
 * @author <a href="mailto:ihar_kahadouski@epam.com">Ihar Kahadouski</a>
 */
@Repository
public class ItemAttributeRepositoryCustomImpl implements ItemAttributeRepositoryCustom {

	private final DSLContext dslContext;

	@Autowired
	public ItemAttributeRepositoryCustomImpl(DSLContext dslContext) {
		this.dslContext = dslContext;
	}

	@Override
	public List<String> findLaunchAttributeKeys(Long projectId, String value, boolean system) {
		return dslContext.selectDistinct(ITEM_ATTRIBUTE.KEY)
				.from(ITEM_ATTRIBUTE)
				.leftJoin(LAUNCH)
				.on(ITEM_ATTRIBUTE.LAUNCH_ID.eq(LAUNCH.ID))
				.leftJoin(PROJECT)
				.on(LAUNCH.PROJECT_ID.eq(PROJECT.ID))
				.where(PROJECT.ID.eq(projectId))
				.and(ITEM_ATTRIBUTE.SYSTEM.eq(system))
				.and(ITEM_ATTRIBUTE.KEY.likeIgnoreCase("%" + value + "%"))
				.fetch(ITEM_ATTRIBUTE.KEY);
	}

	@Override
	public List<String> findLaunchAttributeValues(Long projectId, String key, String value, boolean system) {
		Condition condition = prepareFetchingValuesCondition(PROJECT.ID, projectId, key, value, system);
		return dslContext.selectDistinct(ITEM_ATTRIBUTE.VALUE)
				.from(ITEM_ATTRIBUTE)
				.leftJoin(LAUNCH)
				.on(ITEM_ATTRIBUTE.LAUNCH_ID.eq(LAUNCH.ID))
				.leftJoin(PROJECT)
				.on(LAUNCH.PROJECT_ID.eq(PROJECT.ID))
				.where(condition)
				.fetch(ITEM_ATTRIBUTE.VALUE);
	}

	@Override
	public List<String> findTestItemAttributeKeys(Long launchId, String value, boolean system) {
		return dslContext.selectDistinct(ITEM_ATTRIBUTE.KEY)
				.from(ITEM_ATTRIBUTE)
				.leftJoin(TEST_ITEM)
				.on(ITEM_ATTRIBUTE.ITEM_ID.eq(TEST_ITEM.ITEM_ID))
				.leftJoin(LAUNCH)
				.on(TEST_ITEM.LAUNCH_ID.eq(LAUNCH.ID))
				.where(LAUNCH.ID.eq(launchId))
				.and(ITEM_ATTRIBUTE.SYSTEM.eq(system))
				.and(ITEM_ATTRIBUTE.KEY.likeIgnoreCase("%" + value + "%"))
				.fetch(ITEM_ATTRIBUTE.KEY);
	}

	@Override
	public List<String> findTestItemAttributeValues(Long launchId, String key, String value, boolean system) {
		Condition condition = prepareFetchingValuesCondition(LAUNCH.ID, launchId, key, value, system);
		return dslContext.selectDistinct(ITEM_ATTRIBUTE.VALUE)
				.from(ITEM_ATTRIBUTE)
				.leftJoin(TEST_ITEM)
				.on(ITEM_ATTRIBUTE.ITEM_ID.eq(TEST_ITEM.ITEM_ID))
				.leftJoin(LAUNCH)
				.on(TEST_ITEM.LAUNCH_ID.eq(LAUNCH.ID))
				.where(condition)
				.fetch(ITEM_ATTRIBUTE.VALUE);
	}

	@Override
	public int saveByItemId(Long itemId, String key, String value, boolean isSystem) {
		return dslContext.insertInto(ITEM_ATTRIBUTE)
				.columns(ITEM_ATTRIBUTE.KEY, ITEM_ATTRIBUTE.VALUE, ITEM_ATTRIBUTE.ITEM_ID, ITEM_ATTRIBUTE.SYSTEM)
				.values(key, value, itemId, isSystem)
				.execute();
	}

	private Condition prepareFetchingValuesCondition(TableField<? extends Record, Long> field, Long id, String key, String value,
			boolean system) {
		Condition condition = field.eq(id)
				.and(ITEM_ATTRIBUTE.SYSTEM.eq(system))
				.and(ITEM_ATTRIBUTE.VALUE.likeIgnoreCase("%" + (value == null ? "" : value) + "%"));
		if (key != null) {
			condition = condition.and(ITEM_ATTRIBUTE.KEY.eq(key));
		}
		return condition;
	}

}