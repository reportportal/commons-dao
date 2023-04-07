package com.epam.ta.reportportal.config;

import com.epam.ta.reportportal.util.FeatureFlagHandler;
import java.util.Set;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeatureFlagConfiguration {

  @Bean
  public FeatureFlagHandler featureFlagHandler(
      @Value("#{'${rp.feature.flags}'.split(',')") Set<String> featureFlagsSet) {
    return new FeatureFlagHandler(featureFlagsSet);
  }
}
