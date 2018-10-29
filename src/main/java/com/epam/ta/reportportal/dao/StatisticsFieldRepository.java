package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.entity.statistics.StatisticsField;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author <a href="mailto:ihar_kahadouski@epam.com">Ihar Kahadouski</a>
 */
public interface StatisticsFieldRepository extends ReportPortalRepository<StatisticsField, Long> {

	@Query(value = "DELETE FROM statistics_field WHERE name=:name", nativeQuery = true)
	void deleteByName(@Param("name") String name);
}
