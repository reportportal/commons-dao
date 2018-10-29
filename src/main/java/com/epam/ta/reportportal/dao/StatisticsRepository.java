package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.entity.statistics.Statistics;

/**
 * @author <a href="mailto:ihar_kahadouski@epam.com">Ihar Kahadouski</a>
 */
public interface StatisticsRepository extends ReportPortalRepository<Statistics, Long> {

	void deleteByName(String name);
}
