package com.epam.ta.reportportal.entity.enums;

import java.util.Arrays;
import java.util.Optional;

public enum FeatureFlag {
  SINGLE_BUCKET("singleBucket");

  private final String name;

  FeatureFlag(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public static Optional<FeatureFlag> fromString(String name) {
    return Optional.ofNullable(name).flatMap(
        str -> Arrays.stream(values()).filter(it -> it.name.equalsIgnoreCase(str)).findAny());

  }
}
