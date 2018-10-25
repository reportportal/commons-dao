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
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
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

	private final DSLContext dsl;

	@Autowired
	public UserFilterRepositoryCustomImpl(DSLContext dsl) {
		this.dsl = dsl;
	}

	@Override
	public List<UserFilter> findByFilter(Filter filter) {
		return USER_FILTER_FETCHER.apply(dsl.fetch(QueryBuilder.newBuilder(filter).build()));
	}

	@Override
	public Page<UserFilter> findByFilter(Filter filter, Pageable pageable) {
		return PageableExecutionUtils.getPage(
				USER_FILTER_FETCHER.apply(dsl.fetch(QueryBuilder.newBuilder(filter).with(pageable).build())),
				pageable,
				() -> dsl.fetchCount(QueryBuilder.newBuilder(filter).build())
		);
	}

	@Override
	public Page<UserFilter> getPermittedFilters(Long projectId, Filter filter, Pageable pageable, String userName) {
		return PageableExecutionUtils.getPage(USER_FILTER_FETCHER.apply(dsl.fetch(QueryBuilder.newBuilder(filter)
				.addCondition(JAclClass.ACL_CLASS.CLASS.eq(UserFilter.class.getName()))
				.addCondition(JAclEntry.ACL_ENTRY.SID.in(dsl.select(JAclSid.ACL_SID.ID)
						.from(JAclSid.ACL_SID)
						.where(JAclSid.ACL_SID.SID.eq(userName))))
				.addCondition(JFilter.FILTER.PROJECT_ID.eq(projectId))
				.with(pageable)
				.build())), pageable, () -> dsl.fetchCount(QueryBuilder.newBuilder(filter).build()));
	}

	@Override
	public Page<UserFilter> getOwnFilters(Long projectId, Filter filter, Pageable pageable, String userName) {
		return PageableExecutionUtils.getPage(USER_FILTER_FETCHER.apply(dsl.fetch(QueryBuilder.newBuilder(filter)
				.addCondition(JAclObjectIdentity.ACL_OBJECT_IDENTITY.OWNER_SID.in(dsl.select(JAclSid.ACL_SID.ID)
						.from(JAclSid.ACL_SID)
						.where(JAclSid.ACL_SID.SID.eq(userName))))
				.addCondition(JFilter.FILTER.PROJECT_ID.eq(projectId))
				.with(pageable)
				.build())), pageable, () -> dsl.fetchCount(QueryBuilder.newBuilder(filter).build()));
	}

	@Override
	public Page<UserFilter> getSharedFilters(Long projectId, Filter filter, Pageable pageable, String userName) {
		return PageableExecutionUtils.getPage(USER_FILTER_FETCHER.apply(dsl.fetch(QueryBuilder.newBuilder(filter)
				.addCondition(JAclClass.ACL_CLASS.CLASS.eq(UserFilter.class.getName()))
				.addCondition(JAclEntry.ACL_ENTRY.SID.in(dsl.select(JAclSid.ACL_SID.ID)
						.from(JAclSid.ACL_SID)
						.where(JAclSid.ACL_SID.SID.eq(userName))))
				.addCondition(JAclObjectIdentity.ACL_OBJECT_IDENTITY.OWNER_SID.notIn(dsl.select(JAclSid.ACL_SID.ID)
						.from(JAclSid.ACL_SID)
						.where(JAclSid.ACL_SID.SID.eq(userName))))
				.addCondition(JFilter.FILTER.PROJECT_ID.eq(projectId))
				.with(pageable)
				.build())), pageable, () -> dsl.fetchCount(QueryBuilder.newBuilder(filter).build()));
	}
}
