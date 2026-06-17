/*
 * Copyright 2019 EPAM Systems
 */

package com.epam.ta.reportportal.entity.widget.content;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;

/** Per-launch slice for test stability drill-down when a test appears in multiple launches. */
public class TestStabilityPerLaunchContent implements Serializable {

  @JsonProperty("launchId")
  private Long launchId;

  @JsonProperty("launchName")
  private String launchName;

  @JsonProperty("uniqueId")
  private String uniqueId;

  @JsonProperty("name")
  private String name;

  @JsonProperty("transitions")
  private long transitions;

  @JsonProperty("flakinessPercent")
  private int flakinessPercent;

  @JsonProperty("flakinessScore")
  private double flakinessScore;

  @JsonProperty("executionCount")
  private int executionCount;

  @JsonProperty("stability")
  private String stability;

  @JsonProperty("itemId")
  private Long itemId;

  public Long getLaunchId() {
    return launchId;
  }

  public void setLaunchId(Long launchId) {
    this.launchId = launchId;
  }

  public String getLaunchName() {
    return launchName;
  }

  public void setLaunchName(String launchName) {
    this.launchName = launchName;
  }

  public String getUniqueId() {
    return uniqueId;
  }

  public void setUniqueId(String uniqueId) {
    this.uniqueId = uniqueId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public long getTransitions() {
    return transitions;
  }

  public void setTransitions(long transitions) {
    this.transitions = transitions;
  }

  public int getFlakinessPercent() {
    return flakinessPercent;
  }

  public void setFlakinessPercent(int flakinessPercent) {
    this.flakinessPercent = flakinessPercent;
  }

  public double getFlakinessScore() {
    return flakinessScore;
  }

  public void setFlakinessScore(double flakinessScore) {
    this.flakinessScore = flakinessScore;
  }

  public int getExecutionCount() {
    return executionCount;
  }

  public void setExecutionCount(int executionCount) {
    this.executionCount = executionCount;
  }

  public String getStability() {
    return stability;
  }

  public void setStability(String stability) {
    this.stability = stability;
  }

  public Long getItemId() {
    return itemId;
  }

  public void setItemId(Long itemId) {
    this.itemId = itemId;
  }
}
