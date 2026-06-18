/*
 * Copyright 2019 EPAM Systems
 */

package com.epam.ta.reportportal.entity.widget.content;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;

/**
 * Aggregated counts for one grouping bucket in stability multilevel mode.
 */
public class TestStabilityGroupBucket implements Serializable {

  @JsonProperty("groupLabel")
  private String groupLabel;

  @JsonProperty("transitional")
  private int transitional;

  @JsonProperty("stable")
  private int stable;

  @JsonProperty("flaky")
  private int flaky;

  @JsonProperty("failed")
  private int failed;

  /** Tests in this bucket (= stable + flaky + transitional + failed). */
  @JsonProperty("totalTests")
  private int totalTests;

  /** Average flakiness % over tests in the bucket */
  @JsonProperty("avgFlakinessPercent")
  private double avgFlakinessPercent;

  @JsonProperty("avgTransitions")
  private double avgTransitions;

  public String getGroupLabel() {
    return groupLabel;
  }

  public void setGroupLabel(String groupLabel) {
    this.groupLabel = groupLabel;
  }

  public int getTransitional() {
    return transitional;
  }

  public void setTransitional(int transitional) {
    this.transitional = transitional;
  }

  public int getStable() {
    return stable;
  }

  public void setStable(int stable) {
    this.stable = stable;
  }

  public int getFlaky() {
    return flaky;
  }

  public void setFlaky(int flaky) {
    this.flaky = flaky;
  }

  public int getFailed() {
    return failed;
  }

  public void setFailed(int failed) {
    this.failed = failed;
  }

  public int getTotalTests() {
    return totalTests;
  }

  public void setTotalTests(int totalTests) {
    this.totalTests = totalTests;
  }

  public double getAvgFlakinessPercent() {
    return avgFlakinessPercent;
  }

  public void setAvgFlakinessPercent(double avgFlakinessPercent) {
    this.avgFlakinessPercent = avgFlakinessPercent;
  }

  public double getAvgTransitions() {
    return avgTransitions;
  }

  public void setAvgTransitions(double avgTransitions) {
    this.avgTransitions = avgTransitions;
  }
}
