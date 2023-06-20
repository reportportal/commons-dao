package com.epam.ta.reportportal.config;

import com.epam.ta.reportportal.dao.ApiKeyRepository;
import com.epam.ta.reportportal.entity.user.ApiKey;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.CacheOperationInvocationContext;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

@Component("apiKeyCacheResolver")
public class ApiKeyCacheResolver implements CacheResolver {

  private final ApiKeyRepository apiKeyRepository;

  private final CacheManager cacheManager;

  public ApiKeyCacheResolver(ApiKeyRepository apiKeyRepository, CacheManager cacheManager) {
    this.apiKeyRepository = apiKeyRepository;
    this.cacheManager = cacheManager;
  }

  @Override
  @NonNull
  public Collection<? extends Cache> resolveCaches(CacheOperationInvocationContext<?> context) {
    Long apiKeyId = (Long) context.getArgs()[0];
    ApiKey apiKey = apiKeyRepository.findById(apiKeyId).orElse(null);

    Collection<Cache> caches = new ArrayList<>();
    if (apiKey != null && context.getOperation().getCacheNames().contains("apiKeyCache")) {
      Cache cache = cacheManager.getCache("apiKeyCache");
      if (cache != null) {
        caches.add(cache);
      }
    }
    return caches;
  }
}