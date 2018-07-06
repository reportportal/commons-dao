package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.entity.integration.Integration;

/**
 * Repository for {@link com.epam.ta.reportportal.entity.integration.Integration} entity
 *
 * @author Yauheni_Martynau
 */
public interface IntegrationRepository extends ReportPortalRepository<Integration, Long>, IntegrationRepositoryCustom {
}
