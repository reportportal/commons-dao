package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.entity.user.ApiKey;
import java.time.LocalDate;

public interface ApiKeyRepositoryCustom {

  /**
   * Update lastUsedAt for apiKey
   *
   * @param id         id of the ApiKey to update
   * @param hash       hash of ApiKey to update
   * @param lastUsedAt {@link LocalDate}
   * @return updated version of {@link ApiKey}
   */
  ApiKey updateLastUsedAt(Long id, String hash, LocalDate lastUsedAt);
}
