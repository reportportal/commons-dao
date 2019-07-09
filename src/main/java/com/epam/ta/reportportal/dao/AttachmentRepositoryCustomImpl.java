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

import com.epam.ta.reportportal.commons.querygen.QueryBuilder;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.Collection;

import static com.epam.ta.reportportal.jooq.tables.JAttachment.ATTACHMENT;

/**
 * @author <a href="mailto:ivan_budayeu@epam.com">Ivan Budayeu</a>
 */
@Repository
public class AttachmentRepositoryCustomImpl implements AttachmentRepositoryCustom {

	@Autowired
	private DSLContext dsl;

	@Override
	public Page<Long> findIdsByProjectId(Long projectId, Pageable pageable) {
		return PageableExecutionUtils.getPage(
				dsl.select(ATTACHMENT.ID)
						.from(ATTACHMENT)
						.where(ATTACHMENT.PROJECT_ID.eq(projectId))
						.limit(pageable.getPageSize())
						.offset(QueryBuilder.retrieveOffsetAndApplyBoundaries(pageable))
						.fetchInto(Long.class),
				pageable,
				() -> dsl.fetchCount(dsl.select(ATTACHMENT.ID).from(ATTACHMENT).where(ATTACHMENT.PROJECT_ID.eq(projectId)))
		);
	}

	@Override
	public Page<Long> findIdsByLaunchId(Long launchId, Pageable pageable) {
		return PageableExecutionUtils.getPage(
				dsl.select(ATTACHMENT.ID)
						.from(ATTACHMENT)
						.where(ATTACHMENT.LAUNCH_ID.eq(launchId))
						.limit(pageable.getPageSize())
						.offset(QueryBuilder.retrieveOffsetAndApplyBoundaries(pageable))
						.fetchInto(Long.class),
				pageable,
				() -> dsl.fetchCount(dsl.select(ATTACHMENT.ID).from(ATTACHMENT).where(ATTACHMENT.LAUNCH_ID.eq(launchId)))
		);
	}

	@Override
	public Page<Long> findIdsByTestItemId(Long itemId, Pageable pageable) {
		return PageableExecutionUtils.getPage(
				dsl.select(ATTACHMENT.ID)
						.from(ATTACHMENT)
						.where(ATTACHMENT.ITEM_ID.eq(itemId))
						.limit(pageable.getPageSize())
						.offset(QueryBuilder.retrieveOffsetAndApplyBoundaries(pageable))
						.fetchInto(Long.class),
				pageable,
				() -> dsl.fetchCount(dsl.select(ATTACHMENT.ID).from(ATTACHMENT).where(ATTACHMENT.ITEM_ID.eq(itemId)))
		);
	}

	@Override
	public int deleteAllByIds(Collection<Long> ids) {
		return dsl.deleteFrom(ATTACHMENT).where(ATTACHMENT.ID.in(ids)).execute();
	}
}
