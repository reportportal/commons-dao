/*
 * Copyright 2019 EPAM Systems
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.epam.ta.reportportal.entity.widget.content;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;

/**
 * One row in "Test stability (flakiness)" widget — only tests classified as Flaky.
 */
public class TestStabilityFlakinessContent implements Serializable {

  public static final String STABILITY_FLAKE = "FLAKY";

  @JsonProperty("uniqueId")
  private String uniqueId;

  /**
   * Internal classification key (may combine launch + RP {@code unique_id} when merge-by-name is off).
   * Use for drill navigation; {@link #uniqueId} stays the ReportPortal id for deep links.
   */
  @JsonProperty("classificationKey")
  private String classificationKey;

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

  @JsonProperty("explanationKey")
  private String explanationKey;

  @JsonProperty("itemId")
  private Long itemId;

  /** Launch of the most recent execution in the window (for deep link). */
  @JsonProperty("launchId")
  private Long launchId;

  /** Distinct launches in scope that executed this test (step-level rows). */
  @JsonProperty("launchCount")
  private Integer launchCount;

  /** Pipeline name of the latest execution row (for leaf display when merge-by-name is off). */
  @JsonProperty("launchName")
  private String launchName;

  /** Launch number of the latest execution row. */
  @JsonProperty("launchNumber")
  private Integer launchNumber;

  public String getUniqueId() {
    return uniqueId;
  }

  public void setUniqueId(String uniqueId) {
    this.uniqueId = uniqueId;
  }

  public String getClassificationKey() {
    return classificationKey;
  }

  public void setClassificationKey(String classificationKey) {
    this.classificationKey = classificationKey;
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

  public String getExplanationKey() {
    return explanationKey;
  }

  public void setExplanationKey(String explanationKey) {
    this.explanationKey = explanationKey;
  }

  public Long getItemId() {
    return itemId;
  }

  public void setItemId(Long itemId) {
    this.itemId = itemId;
  }

  public Long getLaunchId() {
    return launchId;
  }

  public void setLaunchId(Long launchId) {
    this.launchId = launchId;
  }

  public Integer getLaunchCount() {
    return launchCount;
  }

  public void setLaunchCount(Integer launchCount) {
    this.launchCount = launchCount;
  }

  public String getLaunchName() {
    return launchName;
  }

  public void setLaunchName(String launchName) {
    this.launchName = launchName;
  }

  public Integer getLaunchNumber() {
    return launchNumber;
  }

  public void setLaunchNumber(Integer launchNumber) {
    this.launchNumber = launchNumber;
  }
}
