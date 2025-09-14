package com.epam.ta.reportportal.dao.tms;

import com.epam.ta.reportportal.dao.ReportPortalRepository;
import com.epam.ta.reportportal.entity.tms.TmsEnvironmentDataset;
import com.epam.ta.reportportal.entity.tms.TmsEnvironmentDatasetId;
import org.springframework.data.jpa.repository.Modifying;

public interface TmsEnvironmentDatasetRepository extends
    ReportPortalRepository<TmsEnvironmentDataset, TmsEnvironmentDatasetId> {

  @Modifying
  void deleteAllByDataset_Id(Long datasetId);
}
