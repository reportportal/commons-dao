package com.epam.ta.reportportal.tms.repository;

import com.epam.ta.reportportal.dao.ReportPortalRepository;
import com.epam.ta.reportportal.tms.entity.TmsProductVersion;
import java.util.Optional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductVersionRepository extends ReportPortalRepository<TmsProductVersion, Long> {

  Optional<TmsProductVersion> findByProjectIdAndId(Long projectId, Long id);

  @Modifying
  void deleteByIdAndProjectId(Long id, Long projectId);

}
