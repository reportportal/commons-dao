package com.epam.ta.reportportal.entity.enums;

import java.util.Arrays;
import java.util.Optional;

public enum StatusEnum {

  //@formatter:off
  IN_PROGRESS("", false),
  PASSED("passed", true),
  FAILED("failed", false),
  STOPPED("stopped", false), //status for manually stopped launches
  SKIPPED("skipped", false),
  INTERRUPTED("failed", false),
  //RESETED("reseted"), //status for items with deleted descendants
  CANCELLED("cancelled", false), //soupUI specific status
  INFO("info", true),
  WARN("warn", true),
  TO_RUN("to_run", false); // New status for manual test execution
  //@formatter:on

  private final String executionCounterField;

  private final boolean positive;

  StatusEnum(String executionCounterField, boolean isPositive) {
    this.executionCounterField = executionCounterField;
    this.positive = isPositive;
  }

  public static Optional<StatusEnum> fromValue(String value) {
    return Arrays.stream(StatusEnum.values())
        .filter(status -> status.name().equalsIgnoreCase(value)).findAny();
  }

  public static boolean isPresent(String name) {
    return fromValue(name).isPresent();
  }

  public String getExecutionCounterField() {
    return executionCounterField;
  }

  public boolean isPositive() {
    return positive;
  }
}
