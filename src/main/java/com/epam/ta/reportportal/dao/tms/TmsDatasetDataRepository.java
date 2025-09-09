package com.epam.ta.reportportal.dao.tms;

import com.epam.ta.reportportal.dao.ReportPortalRepository;
import com.epam.ta.reportportal.entity.tms.TmsDatasetData;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

@Repository
public interface TmsDatasetDataRepository extends
    ReportPortalRepository<TmsDatasetData, Long> {

  @Modifying
  void deleteAllByDataset_Id(Long datasetId);
}
