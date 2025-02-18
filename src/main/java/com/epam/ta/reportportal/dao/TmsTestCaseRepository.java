package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.entity.tms.TmsTestCase;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface TmsTestCaseRepository extends ReportPortalRepository<TmsTestCase, Long> {
    List<TmsTestCase> findByTestFolder_ProjectId(long projectId);
}
