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

import com.epam.ta.reportportal.commons.querygen.Filter;
import com.epam.ta.reportportal.commons.querygen.FilterCondition;
import com.epam.ta.reportportal.commons.querygen.QueryBuilder;
import com.epam.ta.reportportal.entity.filter.FilterSort;
import com.epam.ta.reportportal.entity.filter.ObjectType;
import com.epam.ta.reportportal.entity.filter.UserFilter;
import com.epam.ta.reportportal.entity.project.Project;
import com.epam.ta.reportportal.jooq.tables.*;
import com.google.common.collect.Lists;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static com.epam.ta.reportportal.dao.constant.WidgetContentRepositoryConstants.*;
import static com.epam.ta.reportportal.jooq.Tables.*;
import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.name;

@Repository
public class UserFilterRepositoryCustomImpl implements UserFilterRepositoryCustom {

	private static final Function<Result<? extends Record>, List<UserFilter>> USER_FILTER_FETCHER = result -> {
		Map<Long, UserFilter> userFilterMap = new HashMap<>();
		result.forEach(r -> {
			Long userFilterID = r.get(ID, Long.class);
			UserFilter userFilter;
			if (userFilterMap.containsKey(userFilterID)) {
				userFilter = userFilterMap.get(userFilterID);
			} else {
				userFilter = new UserFilter();
				userFilter.setId(userFilterID);
				userFilter.setName(r.get(NAME, String.class));
				userFilter.setDescription(r.get(DESCRIPTION, String.class));
				userFilter.setTargetClass(ObjectType.valueOf(r.get(TARGET, String.class)));
				Project project = new Project();
				project.setId(r.get(PROJECT_ID, Long.class));
				userFilter.setProject(project);
				userFilterMap.put(userFilterID, userFilter);
			}
			userFilter.getFilterCondition().add(r.into(FilterCondition.class));
			userFilter.getFilterSorts().add(r.into(FilterSort.class));
		});
		return Lists.newArrayList(userFilterMap.values());
	};
	public static final String SHARED_FILTERS = "shared_filters";

	private final DSLContext dsl;

	@Autowired
	public UserFilterRepositoryCustomImpl(DSLContext dsl) {
		this.dsl = dsl;
	}

	@Override
	public List<UserFilter> findByFilter(Filter filter) {
		return USER_FILTER_FETCHER.apply(dsl.fetch(QueryBuilder.newBuilder(filter).withWrapper(filter.getTarget()).build()));
	}

	@Override
	public Page<UserFilter> findByFilter(Filter filter, Pageable pageable) {
		return PageableExecutionUtils.getPage(USER_FILTER_FETCHER.apply(dsl.fetch(QueryBuilder.newBuilder(filter)
				.with(pageable)
				.withWrapper(filter.getTarget())
				.with(pageable.getSort())
				.build())), pageable, () -> dsl.fetchCount(QueryBuilder.newBuilder(filter).build()));
	}

	@Override
	public Page<UserFilter> getPermittedFilters(Long projectId, Filter filter, Pageable pageable, String userName) {
		return PageableExecutionUtils.getPage(USER_FILTER_FETCHER.apply(dsl.with(SHARED_FILTERS)
				.as(selectPermittedFilters(projectId, pageable, userName))
				.select(JUserFilter.USER_FILTER.ID,
						FILTER.NAME,
						FILTER.PROJECT_ID,
						FILTER.TARGET,
						FILTER.DESCRIPTION,
						FILTER_CONDITION.SEARCH_CRITERIA,
						FILTER_CONDITION.CONDITION,
						FILTER_CONDITION.VALUE,
						FILTER_CONDITION.NEGATIVE,
						FILTER_SORT.FIELD,
						FILTER_SORT.DIRECTION
				)
				.from(JUserFilter.USER_FILTER)
				.join(SHARED_FILTERS)
				.on(JUserFilter.USER_FILTER.ID.eq(field(name(SHARED_FILTERS, "id"), Long.class)))
				.join(FILTER)
				.on(JUserFilter.USER_FILTER.ID.eq(FILTER.ID))
				.join(FILTER_CONDITION)
				.on(FILTER.ID.eq(FILTER_CONDITION.FILTER_ID))
				.join(FILTER_SORT)
				.on(FILTER.ID.eq(FILTER_SORT.FILTER_ID))
				.fetch()), pageable, () -> dsl.fetchCount(selectPermittedFilters(projectId, pageable, userName)));
	}

	@Override
	public Page<UserFilter> getOwnFilters(Long projectId, Filter filter, Pageable pageable, String userName) {
		return PageableExecutionUtils.getPage(USER_FILTER_FETCHER.apply(dsl.with(SHARED_FILTERS)
				.as(selectOwnFilters(projectId, pageable, userName))
				.select(JUserFilter.USER_FILTER.ID,
						FILTER.NAME,
						FILTER.PROJECT_ID,
						FILTER.TARGET,
						FILTER.DESCRIPTION,
						FILTER_CONDITION.SEARCH_CRITERIA,
						FILTER_CONDITION.CONDITION,
						FILTER_CONDITION.VALUE,
						FILTER_CONDITION.NEGATIVE,
						FILTER_SORT.FIELD,
						FILTER_SORT.DIRECTION
				)
				.from(JUserFilter.USER_FILTER)
				.join(SHARED_FILTERS)
				.on(JUserFilter.USER_FILTER.ID.eq(field(name(SHARED_FILTERS, "id"), Long.class)))
				.join(FILTER)
				.on(JUserFilter.USER_FILTER.ID.eq(FILTER.ID))
				.join(FILTER_CONDITION)
				.on(FILTER.ID.eq(FILTER_CONDITION.FILTER_ID))
				.join(FILTER_SORT)
				.on(FILTER.ID.eq(FILTER_SORT.FILTER_ID))
				.fetch()), pageable, () -> dsl.fetchCount(selectOwnFilters(projectId, pageable, userName)));
	}

	@Override
	public Page<UserFilter> getSharedFilters(Long projectId, Filter filter, Pageable pageable, String userName) {
		return PageableExecutionUtils.getPage(USER_FILTER_FETCHER.apply(dsl.with(SHARED_FILTERS)
				.as(selectSharedFilters(projectId, pageable, userName))
				.select(JUserFilter.USER_FILTER.ID,
						FILTER.NAME,
						FILTER.PROJECT_ID,
						FILTER.TARGET,
						FILTER.DESCRIPTION,
						FILTER_CONDITION.SEARCH_CRITERIA,
						FILTER_CONDITION.CONDITION,
						FILTER_CONDITION.VALUE,
						FILTER_CONDITION.NEGATIVE,
						FILTER_SORT.FIELD,
						FILTER_SORT.DIRECTION
				)
				.from(JUserFilter.USER_FILTER)
				.join(SHARED_FILTERS)
				.on(JUserFilter.USER_FILTER.ID.eq(field(name(SHARED_FILTERS, "id"), Long.class)))
				.join(FILTER)
				.on(JUserFilter.USER_FILTER.ID.eq(FILTER.ID))
				.join(FILTER_CONDITION)
				.on(FILTER.ID.eq(FILTER_CONDITION.FILTER_ID))
				.join(FILTER_SORT)
				.on(FILTER.ID.eq(FILTER_SORT.FILTER_ID))
				.fetch()), pageable, () -> dsl.fetchCount(selectSharedFilters(projectId, pageable, userName)));
	}

	private SelectForUpdateStep<Record3<Long, String, Long>> selectSharedFilters(Long projectId, Pageable pageable, String userName) {
		return selectFiltersJoinAcl().where(JAclClass.ACL_CLASS.CLASS.eq(UserFilter.class.getName()))
				.and(JAclEntry.ACL_ENTRY.SID.in(dsl.select(JAclSid.ACL_SID.ID)
						.from(JAclSid.ACL_SID)
						.where(JAclSid.ACL_SID.SID.eq(userName))))
				.and(JAclObjectIdentity.ACL_OBJECT_IDENTITY.OWNER_SID.notIn(dsl.select(JAclSid.ACL_SID.ID)
						.from(JAclSid.ACL_SID)
						.where(JAclSid.ACL_SID.SID.eq(userName))))
				.and(JFilter.FILTER.PROJECT_ID.eq(projectId))
				.limit(pageable.getPageSize())
				.offset(Long.valueOf(pageable.getOffset()).intValue());
	}

	private SelectForUpdateStep<Record3<Long, String, Long>> selectPermittedFilters(Long projectId, Pageable pageable, String userName) {
		return selectFiltersJoinAcl().where(JAclClass.ACL_CLASS.CLASS.eq(UserFilter.class.getName()))
				.and(JAclEntry.ACL_ENTRY.SID.in(dsl.select(JAclSid.ACL_SID.ID)
						.from(JAclSid.ACL_SID)
						.where(JAclSid.ACL_SID.SID.eq(userName))))
				.and(JFilter.FILTER.PROJECT_ID.eq(projectId))
				.limit(pageable.getPageSize())
				.offset(Long.valueOf(pageable.getOffset()).intValue());
	}

	private SelectForUpdateStep<Record3<Long, String, Long>> selectOwnFilters(Long projectId, Pageable pageable, String userName) {
		return selectFiltersJoinAcl().where(JAclObjectIdentity.ACL_OBJECT_IDENTITY.OWNER_SID.in(dsl.select(JAclSid.ACL_SID.ID)
				.from(JAclSid.ACL_SID)
				.where(JAclSid.ACL_SID.SID.eq(userName))))
				.and(JFilter.FILTER.PROJECT_ID.eq(projectId))
				.limit(pageable.getPageSize())
				.offset(Long.valueOf(pageable.getOffset()).intValue());
	}

	private SelectOnConditionStep<Record3<Long, String, Long>> selectFiltersJoinAcl() {
		return DSL.selectDistinct(JUserFilter.USER_FILTER.ID, FILTER.NAME, FILTER.PROJECT_ID)
				.from(JUserFilter.USER_FILTER)
				.join(ACL_OBJECT_IDENTITY)
				.on(JUserFilter.USER_FILTER.ID.cast(String.class).eq(ACL_OBJECT_IDENTITY.OBJECT_ID_IDENTITY))
				.join(ACL_CLASS)
				.on(ACL_CLASS.ID.eq(ACL_OBJECT_IDENTITY.OBJECT_ID_CLASS))
				.join(ACL_ENTRY)
				.on(ACL_ENTRY.ACL_OBJECT_IDENTITY.eq(ACL_OBJECT_IDENTITY.ID))
				.join(FILTER)
				.on(JUserFilter.USER_FILTER.ID.eq(FILTER.ID));
	}

}
