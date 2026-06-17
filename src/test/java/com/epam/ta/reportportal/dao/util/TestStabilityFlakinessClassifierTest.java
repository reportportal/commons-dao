/*
 * Copyright 2019 EPAM Systems
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 */

package com.epam.ta.reportportal.dao.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.epam.ta.reportportal.dao.util.TestStabilityFlakinessAggregator.TestExecutionRow;
import com.epam.ta.reportportal.dao.util.TestStabilityFlakinessClassifier.ClassifiedTest;
import com.epam.ta.reportportal.dao.util.TestStabilityFlakinessClassifier.StabilityBand;
import com.epam.ta.reportportal.jooq.enums.JStatusEnum;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class TestStabilityFlakinessClassifierTest {

  private static String lp(long launchId, String uid) {
    return launchId + TestStabilityFlakinessClassifier.STABILITY_KEY_LAUNCH_UID_SEP + uid;
  }

  private static TestExecutionRow row(long launchId, String uid, long itemId, Instant itemStart,
      JStatusEnum status) {
    return new TestExecutionRow(launchId, uid, "Test", status, itemStart, itemStart, itemId,
        "launch-" + launchId, 1);
  }

  private static TestExecutionRow rowNamed(long launchId, String launchName, String uid, long itemId,
      Instant itemStart, JStatusEnum status) {
    return new TestExecutionRow(launchId, uid, "Test", status, itemStart, itemStart, itemId,
        launchName, 1);
  }

  @Test
  void transitionalRequiresAtLeastOneChronologicalTransition_crossLaunchPassFail() {
    Instant t1 = Instant.parse("2025-01-01T10:00:00Z");
    Instant t2 = Instant.parse("2025-01-02T10:00:00Z");
    Instant t3 = Instant.parse("2025-01-03T10:00:00Z");
    Instant t4 = Instant.parse("2025-01-04T10:00:00Z");
    List<TestExecutionRow> rows = new ArrayList<>();
    rows.add(row(100L, "u1", 1L, t1, JStatusEnum.PASSED));
    rows.add(row(100L, "u1", 2L, t2, JStatusEnum.PASSED));
    rows.add(row(100L, "u1", 3L, t3, JStatusEnum.PASSED));
    rows.add(row(100L, "u1", 4L, t4, JStatusEnum.FAILED));
    Map<String, ClassifiedTest> m = TestStabilityFlakinessClassifier.classify(rows, 10);
    ClassifiedTest ct = m.get(lp(100L, "u1"));
    assertEquals(1, ct.getLaunchCount());
    assertEquals(1L, ct.getTransitions());
    assertEquals(StabilityBand.TRANSITIONAL, ct.getBand());
  }

  @Test
  void twoLaunchesSameStatus_noTransitions_notTransitionalEvenWhenMultipleLaunches() {
    Instant t1 = Instant.parse("2025-01-01T10:00:00Z");
    Instant t2 = Instant.parse("2025-01-02T10:00:00Z");
    List<TestExecutionRow> rows = new ArrayList<>();
    rows.add(row(100L, "u1", 1L, t1, JStatusEnum.PASSED));
    rows.add(row(100L, "u1", 2L, t2, JStatusEnum.PASSED));
    Map<String, ClassifiedTest> m = TestStabilityFlakinessClassifier.classify(rows, 10);
    ClassifiedTest ct = m.get(lp(100L, "u1"));
    assertEquals(1, ct.getLaunchCount());
    assertEquals(0L, ct.getTransitions());
    assertEquals(StabilityBand.STABLE, ct.getBand());
  }

  @Test
  void aggregateByTestName_mergesDistinctRpUniqueIdsWithSameTitle() {
    Instant t1 = Instant.parse("2025-01-01T10:00:00Z");
    Instant t2 = Instant.parse("2025-01-02T10:00:00Z");
    List<TestExecutionRow> rows = new ArrayList<>();
    rows.add(new TestExecutionRow(10L, "auto:hashaaa", "After customer ends the call",
        JStatusEnum.PASSED, t1, t1, 101L, "launch-a", 1));
    rows.add(new TestExecutionRow(20L, "auto:hashbbb", "After customer ends the call",
        JStatusEnum.PASSED, t2, t2, 102L, "launch-b", 1));
    Map<String, ClassifiedTest> merged =
        TestStabilityFlakinessClassifier.classify(rows, 10, true);
    Map<String, ClassifiedTest> separate =
        TestStabilityFlakinessClassifier.classify(rows, 10, false);
    assertEquals(1, merged.size());
    assertEquals(2, separate.size());
    assertEquals(2, merged.values().iterator().next().getLaunchCount());
  }

  @Test
  void sameLaunchNameDifferentLaunchIds_countsSeparatePerPhysicalLaunch() {
    Instant t1 = Instant.parse("2025-01-01T10:00:00Z");
    Instant t2 = Instant.parse("2025-01-02T10:00:00Z");
    List<TestExecutionRow> rows = new ArrayList<>();
    rows.add(rowNamed(100L, "service_campaign", "u1", 1L, t1, JStatusEnum.PASSED));
    rows.add(rowNamed(200L, "service_campaign", "u1", 2L, t2, JStatusEnum.PASSED));
    Map<String, ClassifiedTest> m = TestStabilityFlakinessClassifier.classify(rows, 10);
    assertEquals(2, m.size());
    assertEquals(1, m.get(lp(100L, "u1")).getLaunchCount());
    assertEquals(1, m.get(lp(200L, "u1")).getLaunchCount());
  }

  @Test
  void globalTimelineCountsCrossLaunchAdjacentStatuses() {
    Instant t1 = Instant.parse("2025-01-01T10:00:00Z");
    Instant t2 = Instant.parse("2025-01-01T11:00:00Z");
    List<TestExecutionRow> rows = new ArrayList<>();
    rows.add(row(100L, "u1", 2L, t2, JStatusEnum.FAILED));
    rows.add(row(100L, "u1", 1L, t1, JStatusEnum.PASSED));
    Map<String, ClassifiedTest> m = TestStabilityFlakinessClassifier.classify(rows, 10);
    assertEquals(1L, m.get(lp(100L, "u1")).getTransitions());
  }

  @Test
  void membershipScopeUsesHistoryAcrossLaunchInstancesNotLatestSnapshotOnly() {
    String pipeline = "sev1/service_voice_callback-settings_ui_critical";
    Instant base = Instant.parse("2026-04-01T00:00:00Z");
    List<TestExecutionRow> history = new ArrayList<>();
    JStatusEnum[] statuses = {
        JStatusEnum.PASSED, JStatusEnum.FAILED, JStatusEnum.PASSED, JStatusEnum.PASSED,
        JStatusEnum.FAILED, JStatusEnum.PASSED, JStatusEnum.PASSED, JStatusEnum.PASSED,
        JStatusEnum.SKIPPED, JStatusEnum.SKIPPED
    };
    for (int i = 0; i < statuses.length; i++) {
      long launchId = 1000L + i;
      history.add(rowNamed(launchId, pipeline, "uid-callback", 10_000L + i,
          base.plusSeconds(i * 3600L), statuses[i]));
    }
    TestExecutionRow latestMembership = history.get(history.size() - 1);
    List<TestExecutionRow> membership = List.of(latestMembership);

    Map<String, ClassifiedTest> scoped = TestStabilityFlakinessClassifier.classifyWithMembershipScope(
        history, membership, 10, false);
    ClassifiedTest ct = scoped.get(lp(latestMembership.getLaunchId(), "uid-callback"));
    assertEquals(5L, ct.getTransitions(),
        "last 10 pipeline runs with P/F/S changes should yield 5 transitions");
    assertEquals(1, ct.getLaunchCount());
  }
}
