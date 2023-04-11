package com.epam.ta.reportportal.entity.enums;

import java.util.Arrays;
import java.util.Optional;

/**
 * @author <a href="mailto:ivan_kustau@epam.com">Ivan Kustau</a>
 * Enumeration of current feature flags
 */
public enum FeatureFlag {
  SINGLE_BUCKET("singleBucket");

  private final String name;

  FeatureFlag(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  /**
   * Returns {@link Optional} of {@link FeatureFlag} by string
   * @param name Name of feature flag
   * @return {@link Optional} of {@link FeatureFlag} by string
   */
  public static Optional<FeatureFlag> fromString(String name) {
    return Optional.ofNullable(name).flatMap(
        str -> Arrays.stream(values()).filter(it -> it.name.equalsIgnoreCase(str)).findAny());

  }
}
