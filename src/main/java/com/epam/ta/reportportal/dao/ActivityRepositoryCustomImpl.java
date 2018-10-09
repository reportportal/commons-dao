package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.commons.querygen.Condition;
import com.epam.ta.reportportal.commons.querygen.Filter;
import com.epam.ta.reportportal.commons.querygen.FilterCondition;
import com.epam.ta.reportportal.commons.querygen.QueryBuilder;
import com.epam.ta.reportportal.entity.Activity;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static com.epam.ta.reportportal.dao.util.RecordMappers.ACTIVITY_MAPPER;
import static com.epam.ta.reportportal.jooq.tables.JActivity.ACTIVITY;

@Repository
public class ActivityRepositoryCustomImpl implements ActivityRepositoryCustom {

	private static final String PROJECT_ID_COLUMN = "project_id";
	private static final String CREATION_DATE_COLUMN = "creation_date";
	private static final String OBJECT_ID_COLUMN = "object_id";

	private DSLContext dsl;

	@Autowired
	public ActivityRepositoryCustomImpl(DSLContext dsl) {
		this.dsl = dsl;
	}

	private final Function<Result<? extends Record>, List<Activity>> ACTIVITY_FETCHER = r -> {
		Map<Long, Activity> activityMap = Maps.newHashMap();
		r.forEach(res -> {
			Long activityId = res.get(ACTIVITY.ID);
			if (!activityMap.containsKey(activityId)) {
				activityMap.put(activityId, ACTIVITY_MAPPER.map(res));
			}
		});

		return Lists.newArrayList(activityMap.values());
	};

	@Override
	public List<Activity> findActivitiesByTestItemId(Long testItemId, Filter filter, Pageable pageable) {
		Sort sort = new Sort(Sort.Direction.DESC, CREATION_DATE_COLUMN);
		FilterCondition testItemIdCondition = FilterCondition.builder()
				.withCondition(Condition.EQUALS)
				.withSearchCriteria(OBJECT_ID_COLUMN)
				.withValue(testItemId.toString())
				.build();
		return ACTIVITY_FETCHER.apply(dsl.fetch(QueryBuilder.newBuilder(filter.withCondition(testItemIdCondition)).with(sort).build()));
	}

	@Override
	public List<Activity> findActivitiesByProjectId(Long projectId, Filter filter, Pageable pageable) {
		FilterCondition projectIdCondition = FilterCondition.builder()
				.withCondition(Condition.EQUALS)
				.withSearchCriteria(PROJECT_ID_COLUMN)
				.withValue(projectId.toString())
				.build();
		return ACTIVITY_FETCHER.apply(dsl.fetch(QueryBuilder.newBuilder(filter.withCondition(projectIdCondition)).with(pageable).build()));
	}

	@Override
	public void deleteModifiedLaterAgo(Long projectId, Duration period) {
		LocalDateTime bound = LocalDateTime.now().minus(period);
		dsl.delete(ACTIVITY).where(ACTIVITY.PROJECT_ID.eq(projectId)).and(ACTIVITY.CREATION_DATE.lt(Timestamp.valueOf(bound))).execute();
	}

	@Override
	public List<Activity> findByFilterWithSortingAndLimit(Filter filter, Sort sort, int limit) {
		return ACTIVITY_FETCHER.apply(dsl.fetch(QueryBuilder.newBuilder(filter).with(sort).with(limit).build()));
	}

	@Override
	public List<Activity> findByFilter(Filter filter) {
		return ACTIVITY_FETCHER.apply(dsl.fetch(QueryBuilder.newBuilder(filter).build()));
	}

	@Override
	public Page<Activity> findByFilter(Filter filter, Pageable pageable) {
		QueryBuilder queryBuilder = QueryBuilder.newBuilder(filter);
		return PageableExecutionUtils.getPage(dsl.fetch(queryBuilder.with(pageable).build()).map(ACTIVITY_MAPPER),
				pageable,
				() -> dsl.fetchCount(queryBuilder.build())
		);
	}
}