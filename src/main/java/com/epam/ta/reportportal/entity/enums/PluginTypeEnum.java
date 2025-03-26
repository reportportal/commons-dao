package com.epam.ta.reportportal.entity.enums;

import java.util.Arrays;
import java.util.Optional;
import lombok.Getter;

@Getter
public enum PluginTypeEnum {

  BUILT_IN("BUILT_IN"),
  EXTENSION("EXTENSION"),
  REMOTE("REMOTE");

  private final String value;

  PluginTypeEnum(String value) {
    this.value = value;
  }

  public static Optional<PluginTypeEnum> fromString(String value) {
    return Arrays.stream(PluginTypeEnum.values()).filter(it -> it.value.equalsIgnoreCase(value)).findAny();
  }
}
