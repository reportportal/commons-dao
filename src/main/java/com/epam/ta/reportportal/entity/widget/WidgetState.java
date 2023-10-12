package com.epam.ta.reportportal.entity.widget;

import java.util.Arrays;
import java.util.Optional;
import javax.annotation.Nullable;

/**
 * @author <a href="mailto:ivan_budayeu@epam.com">Ivan Budayeu</a>
 */
public enum WidgetState {

  CREATED("created"),

  RENDERING("rendering"),

  READY("ready"),

  FAILED("failed");

  private final String value;

  WidgetState(String value) {
    this.value = value;
  }

  public static Optional<WidgetState> findByName(@Nullable String name) {
    return Arrays.stream(WidgetState.values())
        .filter(state -> state.getValue().equalsIgnoreCase(name)).findAny();
  }

  public String getValue() {
    return value;
  }
}
