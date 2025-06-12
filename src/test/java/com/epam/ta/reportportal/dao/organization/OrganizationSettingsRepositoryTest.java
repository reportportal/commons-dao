package com.epam.ta.reportportal.dao.organization;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.epam.ta.reportportal.BaseTest;
import com.epam.ta.reportportal.entity.organization.OrganizationSetting;
import java.util.stream.Collectors;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;

public class OrganizationSettingsRepositoryTest extends BaseTest {

  @Autowired
  private OrganizationSettingsRepository organizationSettingsRepository;

  @ParameterizedTest
  @CsvSource(value = {
      "1"
  })
  public void testSettingsMigrated(Long orgId) {
    var retentionSettings = organizationSettingsRepository.findByOrganizationId(orgId).stream()
        .filter(it -> it.getSettingKey().startsWith("retention_")).collect(Collectors.toMap(
            OrganizationSetting::getSettingKey, it -> it));
    assertEquals(3, retentionSettings.size());
    assertEquals("90", retentionSettings.get("retention_launches").getSettingValue());
    assertEquals("14", retentionSettings.get("retention_logs").getSettingValue());
    assertEquals("14", retentionSettings.get("retention_attachments").getSettingValue());
  }

}
