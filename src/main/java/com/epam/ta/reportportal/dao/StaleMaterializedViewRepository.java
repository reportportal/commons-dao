package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.entity.materialized.StaleMaterializedView;

import java.util.Optional;

/**
 * @author <a href="mailto:ivan_budayeu@epam.com">Ivan Budayeu</a>
 */
public interface StaleMaterializedViewRepository {

	Optional<StaleMaterializedView> findById(Long id);

	StaleMaterializedView save(StaleMaterializedView view);
}
