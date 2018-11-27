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
import org.jooq.TableField;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.epam.ta.reportportal.jooq.Tables.*;

/**
 * @author <a href="mailto:ihar_kahadouski@epam.com">Ihar Kahadouski</a>
 */
public class ItemAttributeRepositoryCustomImpl implements ItemAttributeRepositoryCustom {

	private final DSLContext dslContext;

	@Autowired
	public ItemAttributeRepositoryCustomImpl(DSLContext dslContext) {
		this.dslContext = dslContext;
	}

	@Override
	public List<String> findKeysByProjectIdAndValue(Long projectId, String value) {
		return findByProjectIdAndValue(projectId, value, ITEM_ATTRIBUTE.KEY);
	}

	@Override
	public List<String> findValuesByProjectIdAndValue(Long projectId, String value) {
		return findByProjectIdAndValue(projectId, value, ITEM_ATTRIBUTE.VALUE);
	}

	@Override
	public List<String> findKeysByLaunchIdAndValue(Long launchId, String value) {
		return findByLaunchIdAndValue(launchId, value, ITEM_ATTRIBUTE.KEY);
	}

	@Override
	public List<String> findValuesByLaunchIdAndValue(Long launchId, String value) {
		return findByLaunchIdAndValue(launchId, value, ITEM_ATTRIBUTE.VALUE);
	}

	private List<String> findByProjectIdAndValue(Long projectId, String value, TableField field) {
		return dslContext.selectDistinct(field)
				.from(ITEM_ATTRIBUTE)
				.leftJoin(LAUNCH)
				.on(ITEM_ATTRIBUTE.LAUNCH_ID.eq(LAUNCH.ID))
				.leftJoin(PROJECT)
				.on(LAUNCH.PROJECT_ID.eq(PROJECT.ID))
				.where(PROJECT.ID.eq(projectId))
				.and(ITEM_ATTRIBUTE.SYSTEM.eq(false))
				.and(field.likeIgnoreCase("%" + value + "%"))
				.fetch(field);
	}

	private List<String> findByLaunchIdAndValue(Long launchId, String value, TableField field) {
		return dslContext.selectDistinct(field)
				.from(ITEM_ATTRIBUTE)
				.leftJoin(TEST_ITEM)
				.on(ITEM_ATTRIBUTE.ITEM_ID.eq(TEST_ITEM.ITEM_ID))
				.leftJoin(LAUNCH)
				.on(TEST_ITEM.LAUNCH_ID.eq(LAUNCH.ID))
				.where(LAUNCH.ID.eq(launchId))
				.and(ITEM_ATTRIBUTE.SYSTEM.eq(false))
				.and(field.likeIgnoreCase("%" + value + "%"))
				.fetch(field);
	}

}
