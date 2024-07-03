package com.epam.ta.reportportal.dao;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.epam.ta.reportportal.BaseTest;
import com.epam.ta.reportportal.entity.Metadata;
import com.epam.ta.reportportal.entity.analytics.AnalyticsData;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class AnalyticsDataRepositoryTest extends BaseTest {

  @Autowired
  private AnalyticsDataRepository analyticsDataRepository;


  @Test
  void persistAnalyticsDataSuccess() {
    AnalyticsData analyticsData = new AnalyticsData();
    analyticsData.setType("ANALYZER_MANUAL_START");

    Map<String, Object> map = new HashMap<>();
    map.put("version", 5.11);
    analyticsData.setMetadata(new Metadata(map));
    analyticsData.setCreatedAt(Instant.now());
    AnalyticsData asd = analyticsDataRepository.save(analyticsData);

    AnalyticsData persistedRecord = analyticsDataRepository.getById(asd.getId());

    assertNotNull(persistedRecord.getCreatedAt());
    assertNotNull(persistedRecord.getType());
  }
}
