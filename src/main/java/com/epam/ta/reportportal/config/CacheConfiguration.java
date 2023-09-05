package com.epam.ta.reportportal.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import java.util.concurrent.TimeUnit;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CacheConfiguration {

  @Bean("caffeineCacheManager")
  public CacheManager cacheManager() {
    CaffeineCacheManager cacheManager = new CaffeineCacheManager();
    cacheManager.setCaffeine(cacheProperties());
    return cacheManager;
  }

  private Caffeine<Object, Object> cacheProperties() {
    return Caffeine.newBuilder().expireAfterWrite(30, TimeUnit.SECONDS);
  }
}
