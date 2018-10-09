package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.commons.querygen.Filter;
import com.epam.ta.reportportal.entity.Activity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.Duration;
import java.util.List;

public interface ActivityRepositoryCustom extends FilterableRepository<Activity> {

	List<Activity> findActivitiesByTestItemId(Long testItemId, Filter filter, Pageable pageable);

	List<Activity> findActivitiesByProjectId(Long projectId, Filter filter, Pageable pageable);

	void deleteModifiedLaterAgo(Long projectId, Duration period);

	List<Activity> findByFilterWithSortingAndLimit(Filter filter, Sort sort, int limit);
}