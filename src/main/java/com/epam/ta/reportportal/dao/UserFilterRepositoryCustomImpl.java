package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.commons.querygen.Filter;
import com.epam.ta.reportportal.commons.querygen.FilterCondition;
import com.epam.ta.reportportal.commons.querygen.QueryBuilder;
import com.epam.ta.reportportal.entity.filter.FilterSort;
import com.epam.ta.reportportal.entity.filter.UserFilter;
import com.epam.ta.reportportal.entity.project.Project;
import com.epam.ta.reportportal.exception.ReportPortalException;
import com.epam.ta.reportportal.ws.model.ErrorType;
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
				try {
					userFilter.setTargetClass(Class.forName(r.get(TARGET, String.class)));
				} catch (ClassNotFoundException e) {
					throw new ReportPortalException(ErrorType.UNCLASSIFIED_REPORT_PORTAL_ERROR);
				}
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
}
