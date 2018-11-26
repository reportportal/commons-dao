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
 * @author Yauheni_Martynau
 */
@Repository
public class LaunchTagRepositoryCustomImpl implements LaunchTagRepositoryCustom {

	private final DSLContext dslContext;

	@Autowired
	public LaunchTagRepositoryCustomImpl(DSLContext dslContext) {
		this.dslContext = dslContext;
	}

	@Override
	public List<String> getTags(Long projectId, String value) {
		return dslContext.selectDistinct()
				.from(ITEM_ATTRIBUTE)
				.leftJoin(LAUNCH)
				.on(ITEM_ATTRIBUTE.LAUNCH_ID.eq(LAUNCH.ID))
				.leftJoin(PROJECT)
				.on(LAUNCH.PROJECT_ID.eq(PROJECT.ID))
				.where(PROJECT.ID.eq(projectId))
				.and(ITEM_ATTRIBUTE.VALUE.likeIgnoreCase("%" + value + "%"))
				.fetch(ITEM_ATTRIBUTE.VALUE);
	}
}
