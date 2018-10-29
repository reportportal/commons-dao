package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.entity.statistics.StatisticsField;

/**
 * @author <a href="mailto:ihar_kahadouski@epam.com">Ihar Kahadouski</a>
 */
public interface StatisticsFieldRepository extends ReportPortalRepository<StatisticsField, Long> {

	void deleteByName(String name);
}
