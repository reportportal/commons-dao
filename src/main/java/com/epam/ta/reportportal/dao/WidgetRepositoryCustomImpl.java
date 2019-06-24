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

import com.epam.ta.reportportal.commons.querygen.ProjectFilter;
import com.epam.ta.reportportal.entity.widget.Widget;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import static com.epam.ta.reportportal.dao.util.ResultFetchers.WIDGET_FETCHER;
import static com.epam.ta.reportportal.jooq.tables.JShareableEntity.SHAREABLE_ENTITY;
import static com.epam.ta.reportportal.jooq.tables.JWidget.WIDGET;
import static com.epam.ta.reportportal.jooq.tables.JWidgetFilter.WIDGET_FILTER;

/**
 * @author <a href="mailto:pavel_bortnik@epam.com">Pavel Bortnik</a>
 */
@Repository
public class WidgetRepositoryCustomImpl extends AbstractShareableRepositoryImpl<Widget> implements WidgetRepositoryCustom {

	@Override
	public Page<Widget> getPermitted(ProjectFilter filter, Pageable pageable, String userName) {
		return getPermitted(WIDGET_FETCHER, filter, pageable, userName);
	}

	@Override
	public Page<Widget> getOwn(ProjectFilter filter, Pageable pageable, String userName) {
		return getOwn(WIDGET_FETCHER, filter, pageable, userName);
	}

	@Override
	public Page<Widget> getShared(ProjectFilter filter, Pageable pageable, String userName) {
		return getShared(WIDGET_FETCHER, filter, pageable, userName);
	}

	@Override
	public int deleteRelationByFilterIdAndNotOwner(Long filterId, String owner) {
		return dsl.deleteFrom(WIDGET_FILTER)
				.where(WIDGET_FILTER.WIDGET_ID.in(dsl.select(WIDGET.ID)
						.from(WIDGET)
						.join(WIDGET_FILTER)
						.on(WIDGET.ID.eq(WIDGET_FILTER.WIDGET_ID))
						.join(SHAREABLE_ENTITY)
						.on(WIDGET.ID.eq(SHAREABLE_ENTITY.ID))
						.where(WIDGET_FILTER.FILTER_ID.eq(filterId))
						.and(SHAREABLE_ENTITY.OWNER.notEqual(owner))))
				.execute();
	}
}
