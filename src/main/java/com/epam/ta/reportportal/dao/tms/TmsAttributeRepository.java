package com.epam.ta.reportportal.dao.tms;

import com.epam.ta.reportportal.entity.tms.TmsAttribute;
import com.epam.ta.reportportal.dao.ReportPortalRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TmsAttributeRepository extends ReportPortalRepository<TmsAttribute, Long> {

  boolean existsByKey(String key);
}
