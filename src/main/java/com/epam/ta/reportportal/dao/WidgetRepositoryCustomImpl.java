/*
 *  Copyright (C) 2018 EPAM Systems
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.commons.querygen.Filter;
import com.epam.ta.reportportal.entity.widget.Widget;
import com.epam.ta.reportportal.ws.model.SharedEntity;
import com.epam.ta.reportportal.ws.model.widget.WidgetPreviewRQ;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import static com.epam.ta.reportportal.dao.constant.WidgetRepositoryConstants.*;
import static com.epam.ta.reportportal.dao.util.JooqFieldNameTransformer.fieldName;
import static com.epam.ta.reportportal.dao.util.RecordMappers.SHARED_ENTITY_MAPPER;
import static com.epam.ta.reportportal.dao.util.RecordMappers.WIDGET_FETCHER;
import static com.epam.ta.reportportal.jooq.tables.JAclEntry.ACL_ENTRY;
import static com.epam.ta.reportportal.jooq.tables.JAclObjectIdentity.ACL_OBJECT_IDENTITY;
import static com.epam.ta.reportportal.jooq.tables.JAclSid.ACL_SID;
import static com.epam.ta.reportportal.jooq.tables.JUserFilter.USER_FILTER;
import static com.epam.ta.reportportal.jooq.tables.JWidget.WIDGET;
import static com.epam.ta.reportportal.jooq.tables.JWidgetFilter.WIDGET_FILTER;
import static com.epam.ta.reportportal.jooq.tables.JWidgetOption.WIDGET_OPTION;

/**
 * @author <a href="mailto:ivan_budayeu@epam.com">Ivan Budayeu</a>
 */
@Repository
public class WidgetRepositoryCustomImpl implements WidgetRepositoryCustom {

	@Autowired
	private DSLContext dsl;

	@Override
	public Page<SharedEntity> getSharedWidgetNames(String username, Long projectId, Pageable pageable) {

		Condition condition = fieldName(SHARED, SID).cast(String.class)
				.eq(username)
				.and(fieldName(OWNER, SID).cast(String.class).ne(username))
				.and(WIDGET.PROJECT_ID.cast(Long.class).eq(projectId));

		return PageableExecutionUtils.getPage(
				dsl.fetch(dsl.with(WIDGET_SUBQUERY)
						.as(buildDistinctSubQuery(condition, pageable))
						.select(WIDGET.ID, WIDGET.NAME, WIDGET.DESCRIPTION, fieldName(SID).as(OWNER))
						.from(WIDGET)
						.join(WIDGET_SUBQUERY)
						.on(fieldName(WIDGET_SUBQUERY, ID).cast(Long.class).eq(WIDGET.ID))).map(SHARED_ENTITY_MAPPER),
				pageable,
				() -> dsl.fetchCount(buildDistinctSubQuery(condition))
		);

	}

	@Override
	public Page<Widget> getSharedWidgetsList(String username, Long projectId, Pageable pageable) {
		Condition condition = fieldName(SHARED, SID).cast(String.class)
				.eq(username)
				.and(fieldName(OWNER, SID).cast(String.class).ne(username))
				.and(WIDGET.PROJECT_ID.cast(Long.class).eq(projectId));

		return PageableExecutionUtils.getPage(WIDGET_FETCHER.apply(dsl.with(WIDGET_SUBQUERY)
				.as(buildDistinctSubQuery(condition, pageable))
				.select(
						WIDGET.ID,
						WIDGET.NAME,
						WIDGET.DESCRIPTION,
						fieldName(SID).as(OWNER),
						WIDGET_OPTION.OPTION,
						WIDGET_OPTION.VALUE,
						WIDGET.ITEMS_COUNT,
						USER_FILTER.ID
				)
				.from(WIDGET)
				.join(WIDGET_SUBQUERY)
				.on(fieldName(WIDGET_SUBQUERY, ID).cast(Long.class).eq(WIDGET.ID))
				.leftJoin(WIDGET_OPTION)
				.on(WIDGET.ID.eq(WIDGET_OPTION.WIDGET_ID))
				.leftJoin(WIDGET_FILTER)
				.on(WIDGET.ID.eq(WIDGET_FILTER.WIDGET_ID))
				.leftJoin(USER_FILTER)
				.on(WIDGET_FILTER.FILTER_ID.eq(USER_FILTER.ID))
				.fetch()), pageable, () -> dsl.fetchCount(buildDistinctSubQuery(condition)));
	}

	@Override
	public List<String> getWidgetNames(String userName, Filter filter) {
		return null;
	}

	@Override
	public Map<String, ?> getWidgetPreview(String userName, Filter filter, WidgetPreviewRQ previewRQ) {
		return null;
	}

	@Override
	public Page<Widget> searchSharedWidgets(String term, Filter filter, Pageable pageable) {
		return null;
	}

	private SelectLimitStep<? extends Record> buildDistinctSubQuery(Condition condition) {
		return DSL.select(WIDGET.ID, fieldName(OWNER, SID))
				.distinctOn(WIDGET.ID, fieldName(OWNER, SID))
				.from(WIDGET)
				.join(ACL_OBJECT_IDENTITY)
				.on(WIDGET.ID.cast(String.class).eq(ACL_OBJECT_IDENTITY.OBJECT_ID_IDENTITY))
				.join(ACL_ENTRY)
				.on(ACL_ENTRY.ACL_OBJECT_IDENTITY.eq(ACL_OBJECT_IDENTITY.ID))
				.join(ACL_SID.asTable(SHARED))
				.on(ACL_ENTRY.SID.eq(fieldName(SHARED, ID).cast(Long.class)))
				.join(ACL_SID.asTable(OWNER))
				.on(ACL_OBJECT_IDENTITY.OWNER_SID.eq(fieldName(OWNER, ID).cast(Long.class)))
				.where(condition)
				.orderBy(fieldName(OWNER, SID));
	}

	private Select<? extends Record> buildDistinctSubQuery(Condition condition, Pageable pageable) {
		return buildDistinctSubQuery(condition).limit(pageable.getPageSize()).offset(Long.valueOf(pageable.getOffset()).intValue());
	}
}
