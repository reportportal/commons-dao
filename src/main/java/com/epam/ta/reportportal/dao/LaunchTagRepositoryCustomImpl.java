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

import com.epam.ta.reportportal.jooq.tables.JLaunch;
import com.epam.ta.reportportal.jooq.tables.JLaunchTag;
import com.epam.ta.reportportal.jooq.tables.JProject;
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

		JLaunch l = LAUNCH.as("l");
		JProject p = PROJECT.as("p");
		JLaunchTag lt = LAUNCH_TAG.as("lt");

		return dslContext.select()
				.from(lt)
				.leftJoin(l).on(lt.LAUNCH_ID.eq(l.ID))
				.leftJoin(p).on(l.PROJECT_ID.eq(p.ID))
				.where(p.ID.eq(projectId))
				.and(lt.VALUE.like("%" + value + "%"))
				.fetch(LAUNCH_TAG.VALUE);
	}
}
