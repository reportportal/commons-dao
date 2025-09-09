package com.epam.ta.reportportal.tms.repository;

import com.epam.ta.reportportal.dao.ReportPortalRepository;
import com.epam.ta.reportportal.tms.entity.TmsEnvironmentDataset;
import com.epam.ta.reportportal.tms.entity.TmsEnvironmentDatasetId;
import org.springframework.data.jpa.repository.Modifying;

public interface TmsEnvironmentDatasetRepository extends
    ReportPortalRepository<TmsEnvironmentDataset, TmsEnvironmentDatasetId> {

  @Modifying
  void deleteAllByDataset_Id(Long datasetId);
}
