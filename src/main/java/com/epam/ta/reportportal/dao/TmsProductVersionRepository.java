package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.entity.tms.TmsProductVersion;
import org.springframework.stereotype.Repository;

@Repository
public interface TmsProductVersionRepository extends ReportPortalRepository<TmsProductVersion, Long> {
    
}
