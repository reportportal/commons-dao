package com.epam.ta.reportportal.tms.repository;

import com.epam.ta.reportportal.dao.ReportPortalRepository;
import com.epam.ta.reportportal.tms.entity.TmsAttribute;
import org.springframework.stereotype.Repository;

@Repository
public interface TmsAttributeRepository extends ReportPortalRepository<TmsAttribute, Long> {

  boolean existsByKey(String key);
}
