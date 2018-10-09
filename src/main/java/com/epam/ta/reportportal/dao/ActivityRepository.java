package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.entity.Activity;

/**
 * Repository for {@link com.epam.ta.reportportal.entity.Activity} entity
 *
 * @author Andrei Varabyeu
 */
public interface ActivityRepository extends ReportPortalRepository<Activity, Long>, ActivityRepositoryCustom {

}
