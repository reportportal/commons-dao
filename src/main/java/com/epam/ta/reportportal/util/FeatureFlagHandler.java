package com.epam.ta.reportportal.util;

import com.epam.ta.reportportal.entity.enums.FeatureFlag;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FeatureFlagHandler {

  private final Set<FeatureFlag> enabledFeatureFlagsSet = new HashSet<>();

  public FeatureFlagHandler(@Value("#{'${rp.feature.flags}'.split(',')}") Set<String> featureFlags) {

    if (!CollectionUtils.isEmpty(featureFlags)) {
      featureFlags.stream().map(FeatureFlag::fromString).filter(Optional::isPresent)
          .map(Optional::get).forEach(enabledFeatureFlagsSet::add);
    }
  }

  public boolean isEnabled(FeatureFlag featureFlag) {
    return enabledFeatureFlagsSet.contains(featureFlag);
  }
}
