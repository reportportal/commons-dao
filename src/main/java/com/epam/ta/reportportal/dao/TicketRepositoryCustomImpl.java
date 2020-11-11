/*
 * Copyright 2019 EPAM Systems
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
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static com.epam.ta.reportportal.jooq.Tables.*;

/**
 * @author <a href="mailto:pavel_bortnik@epam.com">Pavel Bortnik</a>
 */
public class TicketRepositoryCustomImpl implements TicketRepositoryCustom {

	@Autowired
	private DSLContext dsl;

	@Override
	public List<String> findByLaunchIdAndTerm(Long launchId, String term) {
		return dsl.select(TICKET.TICKET_ID)
				.from(TICKET)
				.join(ISSUE_TICKET)
				.on(TICKET.ID.eq(ISSUE_TICKET.TICKET_ID))
				.join(ISSUE)
				.on(ISSUE_TICKET.ISSUE_ID.eq(ISSUE.ISSUE_ID))
				.join(TEST_ITEM)
				.on(ISSUE.ISSUE_ID.eq(TEST_ITEM.ITEM_ID))
				.where(TICKET.TICKET_ID.likeIgnoreCase("%" + DSL.escape(term, '\\') + "%"))
				.and(TEST_ITEM.LAUNCH_ID.eq(launchId))
				.fetchInto(String.class);
	}

	@Override
	public List<String> findByProjectIdAndTerm(Long projectId, String term) {
		return dsl.selectDistinct(TICKET.TICKET_ID)
				.from(TICKET)
				.join(ISSUE_TICKET)
				.on(TICKET.ID.eq(ISSUE_TICKET.TICKET_ID))
				.join(ISSUE)
				.on(ISSUE_TICKET.ISSUE_ID.eq(ISSUE.ISSUE_ID))
				.join(TEST_ITEM_RESULTS)
				.on(ISSUE.ISSUE_ID.eq(TEST_ITEM_RESULTS.RESULT_ID))
				.join(TEST_ITEM)
				.on(TEST_ITEM_RESULTS.RESULT_ID.eq(TEST_ITEM.ITEM_ID))
				.join(LAUNCH)
				.on(TEST_ITEM.LAUNCH_ID.eq(LAUNCH.ID))
				.where(TICKET.TICKET_ID.likeIgnoreCase("%" + DSL.escape(term, '\\') + "%"))
				.and(LAUNCH.PROJECT_ID.eq(projectId))
				.fetchInto(String.class);
	}

	@Override
	public Integer findUniqueCountByProjectBefore(Long projectId, LocalDateTime from) {
		return dsl.fetchCount(dsl.selectDistinct(TICKET.TICKET_ID)
				.from(TICKET)
				.join(ISSUE_TICKET)
				.on(TICKET.ID.eq(ISSUE_TICKET.TICKET_ID))
				.join(ISSUE)
				.on(ISSUE_TICKET.ISSUE_ID.eq(ISSUE.ISSUE_ID))
				.join(TEST_ITEM)
				.on(ISSUE.ISSUE_ID.eq(TEST_ITEM.ITEM_ID))
				.join(LAUNCH)
				.on(TEST_ITEM.LAUNCH_ID.eq(LAUNCH.ID))
				.where(LAUNCH.PROJECT_ID.eq(projectId))
				.and(TICKET.SUBMIT_DATE.greaterOrEqual(Timestamp.valueOf(from))));
	}
}
