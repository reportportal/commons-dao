package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.entity.user.ApiKey;
import java.time.LocalDate;
import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.transaction.annotation.Transactional;

public class ApiKeyRepositoryCustomImpl implements ApiKeyRepositoryCustom{

  @Autowired
  private EntityManager entityManager;

  @Transactional
  @Override
  @CachePut(value = "apiKeyCache", key = "#hash")
  public ApiKey updateLastUsedAt(Long id, String hash, LocalDate lastUsedAt) {
    ApiKey apiKey = entityManager.find(ApiKey.class, id);

    if (apiKey != null) {
      apiKey.setLastUsedAt(lastUsedAt);
      entityManager.merge(apiKey);
    }

    return apiKey;
  }
}
