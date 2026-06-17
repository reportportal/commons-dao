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

package com.epam.ta.reportportal.dao.util;

import com.epam.ta.reportportal.entity.widget.content.TestStabilityFlakinessContent;
import com.epam.ta.reportportal.jooq.enums.JStatusEnum;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.tuple.Pair;

/**
 * CCAAS flakiness: Flakiness Score = transitions / (M − 1). Flaky if mixed outcomes and score ≥
 * 0.4. Status sequence includes PASSED, FAILED, SKIPPED; any consecutive change counts.
 */
public final class TestStabilityFlakinessAggregator {

  public static final double FLAKY_THRESHOLD = 0.4d;
  public static final String EXPLANATION_KEY_FLAKY = "stability.explanation.flaky";
  public static final int MAX_ROWS = 200;

  public static final class TestExecutionRow {

    private final long launchId;
    private final String uniqueId;
    private final String name;
    private final JStatusEnum status;
    private final Instant launchStartTime;
    private final Instant itemStartTime;
    private final long itemId;
    /** Launch display name at execution time (may be null if not loaded). */
    private final String launchName;
    /** Launch number (same name may repeat across distinct launch IDs). */
    private final Integer launchNumber;

    public TestExecutionRow(long launchId, String uniqueId, String name, JStatusEnum status,
        Instant launchStartTime, Instant itemStartTime, long itemId, String launchName,
        Integer launchNumber) {
      this.launchId = launchId;
      this.uniqueId = uniqueId;
      this.name = name;
      this.status = status;
      this.launchStartTime = launchStartTime;
      this.itemStartTime = itemStartTime;
      this.itemId = itemId;
      this.launchName = launchName;
      this.launchNumber = launchNumber;
    }

    public String getLaunchName() {
      return launchName;
    }

    public Integer getLaunchNumber() {
      return launchNumber;
    }

    public long getLaunchId() {
      return launchId;
    }

    public String getUniqueId() {
      return uniqueId;
    }

    public String getName() {
      return name;
    }

    public JStatusEnum getStatus() {
      return status;
    }

    public Instant getLaunchStartTime() {
      return launchStartTime;
    }

    public Instant getItemStartTime() {
      return itemStartTime;
    }

    public long getItemId() {
      return itemId;
    }
  }

  private TestStabilityFlakinessAggregator() {
  }

  /**
   * @param rawRows         rows in query order (any order); will dedupe by (launch, unique) keeping
   *                        latest item by start time, then group by unique id
   * @return flaky tests only, score descending, at most {@link #MAX_ROWS}
   */
  public static List<TestStabilityFlakinessContent> aggregateFlakyOnly(
      List<TestExecutionRow> rawRows) {
    if (rawRows == null || rawRows.isEmpty()) {
      return List.of();
    }
    // Dedup: one row per (launch, unique) — keep latest test item start
    Map<Pair<Long, String>, TestExecutionRow> byLaunchTest = new LinkedHashMap<>();
    for (TestExecutionRow r : rawRows) {
      Pair<Long, String> k = Pair.of(r.getLaunchId(), r.getUniqueId());
      TestExecutionRow cur = byLaunchTest.get(k);
      if (cur == null) {
        byLaunchTest.put(k, r);
      } else {
        Instant curTs = cur.getItemStartTime();
        Instant rTs = r.getItemStartTime();
        if (rTs != null && (curTs == null || rTs.isAfter(curTs))) {
          byLaunchTest.put(k, r);
        }
      }
    }
    Map<String, List<TestExecutionRow>> byUnique = new LinkedHashMap<>();
    for (TestExecutionRow r : byLaunchTest.values()) {
      byUnique.computeIfAbsent(r.getUniqueId(), u -> new ArrayList<>()).add(r);
    }
    List<TestStabilityFlakinessContent> out = new ArrayList<>();
    for (Map.Entry<String, List<TestExecutionRow>> e : byUnique.entrySet()) {
      TestStabilityFlakinessContent c = toContent(e.getKey(), e.getValue());
      if (c != null) {
        out.add(c);
      }
    }
    out.sort(Comparator.comparingDouble(TestStabilityFlakinessContent::getFlakinessScore)
        .reversed()
        .thenComparingLong(TestStabilityFlakinessContent::getTransitions)
        .reversed());
    if (out.size() > MAX_ROWS) {
      return out.subList(0, MAX_ROWS);
    }
    return out;
  }

  private static TestStabilityFlakinessContent toContent(String uniqueId, List<TestExecutionRow> runRows) {
    if (runRows == null || runRows.isEmpty()) {
      return null;
    }
    // Order oldest → newest by launch time, then item start
    List<TestExecutionRow> oldestFirst = new ArrayList<>(runRows);
    oldestFirst.sort(Comparator
        .comparing(TestExecutionRow::getLaunchStartTime, Comparator.nullsLast(Instant::compareTo))
        .thenComparing(TestExecutionRow::getItemStartTime, Comparator.nullsLast(Instant::compareTo))
        .thenComparingLong(TestExecutionRow::getItemId));
    int m = oldestFirst.size();
    if (m < 2) {
      return null;
    }
    int transitions = 0;
    for (int i = 0; i < m - 1; i++) {
      if (oldestFirst.get(i).getStatus() != oldestFirst.get(i + 1).getStatus()) {
        transitions++;
      }
    }
    double score = (double) transitions / (m - 1);
    if (!isMixed(oldestFirst) || score < FLAKY_THRESHOLD) {
      return null;
    }
    TestExecutionRow latest = oldestFirst.get(m - 1);
    TestStabilityFlakinessContent c = new TestStabilityFlakinessContent();
    c.setUniqueId(uniqueId);
    c.setName(latest.getName());
    c.setTransitions(transitions);
    c.setFlakinessScore(Math.round(score * 1_000_000d) / 1_000_000d);
    c.setFlakinessPercent((int) Math.round(score * 100.0d));
    c.setExecutionCount(m);
    c.setStability(TestStabilityFlakinessContent.STABILITY_FLAKE);
    c.setExplanationKey(EXPLANATION_KEY_FLAKY);
    c.setItemId(latest.getItemId());
    c.setLaunchId(latest.getLaunchId());
    return c;
  }

  private static boolean isMixed(List<TestExecutionRow> oldestFirst) {
    JStatusEnum first = oldestFirst.get(0).getStatus();
    for (int i = 1; i < oldestFirst.size(); i++) {
      if (oldestFirst.get(i).getStatus() != first) {
        return true;
      }
    }
    return false;
  }
}
