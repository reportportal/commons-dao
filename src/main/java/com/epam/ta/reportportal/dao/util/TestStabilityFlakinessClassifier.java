/*
 * Copyright 2019 EPAM Systems
 */

package com.epam.ta.reportportal.dao.util;

import static com.epam.ta.reportportal.dao.util.TestStabilityFlakinessAggregator.FLAKY_THRESHOLD;

import com.epam.ta.reportportal.dao.util.TestStabilityFlakinessAggregator.TestExecutionRow;
import com.epam.ta.reportportal.entity.widget.content.TestStabilityFlakinessContent;
import com.epam.ta.reportportal.entity.widget.content.TestStabilityPerLaunchContent;
import com.epam.ta.reportportal.jooq.enums.JStatusEnum;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HexFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Stability classification: all launches matching the widget filter are considered; within each
 * logical launch (trimmed {@link TestExecutionRow#getLaunchName()}), only the last N executions of a
 * test are used; then transitions are computed on the combined timeline across logical launches.
 */
public final class TestStabilityFlakinessClassifier {

  public enum StabilityBand {
    STABLE,
    TRANSITIONAL,
    FLAKY,
    FAILED
  }

  public static final String EXPLAIN_STABLE = "stability.explanation.stable";
  public static final String EXPLAIN_TRANSITIONAL = "stability.explanation.transitional";
  public static final String EXPLAIN_FAILED = "stability.explanation.failed";

  /** Default max executions per launch considered for transitions (most recent first within launch). */
  public static final int DEFAULT_EXECUTIONS_PER_LAUNCH_WINDOW = 10;

  /** Result row for leaf level + metrics */
  public static final class ClassifiedTest {

    /** Map/drill key — may be {@code tn:…}, or {@code launchId + SEP + unique_id}. */
    private final String stabilityKey;
    /** ReportPortal {@code unique_id} for API links and display (never the compound key). */
    private final String reportPortalUniqueId;
    private final String name;
    private final long transitions;
    private final double flakinessScore;
    private final int flakinessPercent;
    private final int executionCount;
    private final StabilityBand band;
    private final String explanationKey;
    private final long latestItemId;
    private final long latestLaunchId;
    private final String latestLaunchName;
    private final Integer latestLaunchNumber;
    private final int launchCount;

    public ClassifiedTest(String stabilityKey, String reportPortalUniqueId, String name,
        long transitions, double flakinessScore,
        int flakinessPercent,
        int executionCount, StabilityBand band, String explanationKey, long latestItemId,
        long latestLaunchId, String latestLaunchName, Integer latestLaunchNumber, int launchCount) {
      this.stabilityKey = stabilityKey;
      this.reportPortalUniqueId = reportPortalUniqueId;
      this.name = name;
      this.transitions = transitions;
      this.flakinessScore = flakinessScore;
      this.flakinessPercent = flakinessPercent;
      this.executionCount = executionCount;
      this.band = band;
      this.explanationKey = explanationKey;
      this.latestItemId = latestItemId;
      this.latestLaunchId = latestLaunchId;
      this.latestLaunchName = latestLaunchName;
      this.latestLaunchNumber = latestLaunchNumber;
      this.launchCount = launchCount;
    }

    public StabilityBand getBand() {
      return band;
    }

    public long getLatestItemId() {
      return latestItemId;
    }

    public long getTransitions() {
      return transitions;
    }

    public int getFlakinessPercent() {
      return flakinessPercent;
    }

    public int getLaunchCount() {
      return launchCount;
    }

    public String getName() {
      return name;
    }

    public TestStabilityFlakinessContent toContent() {
      TestStabilityFlakinessContent c = new TestStabilityFlakinessContent();
      c.setUniqueId(reportPortalUniqueId);
      c.setClassificationKey(stabilityKey);
      c.setName(name);
      c.setTransitions(transitions);
      c.setFlakinessPercent(flakinessPercent);
      c.setFlakinessScore(
          Math.round(flakinessScore * 1_000_000d) / 1_000_000d);
      c.setExecutionCount(executionCount);
      c.setStability(band.name());
      c.setExplanationKey(explanationKey);
      c.setItemId(latestItemId);
      c.setLaunchId(latestLaunchId);
      c.setLaunchName(latestLaunchName);
      c.setLaunchNumber(latestLaunchNumber);
      c.setLaunchCount(launchCount);
      return c;
    }
  }

  private TestStabilityFlakinessClassifier() {
  }

  /** Prefix for synthetic ids when merging multiple RP {@code unique_id} rows by test name. */
  public static final String TEST_NAME_MERGE_PREFIX = "tn:";

  /**
   * Separates launch id and RP {@code unique_id} in {@link #classify} keys when merge-by-name is off
   * (one stability row per launch × case). Not used in URLs directly; JSON-escaped in responses.
   */
  public static final String STABILITY_KEY_LAUNCH_UID_SEP = "\u0001";

  /**
   * One entry per distinct unique id in raw rows (no merge-by-name).
   */
  public static Map<String, ClassifiedTest> classify(List<TestExecutionRow> rawRows) {
    return classify(rawRows, DEFAULT_EXECUTIONS_PER_LAUNCH_WINDOW, false);
  }

  public static Map<String, ClassifiedTest> classify(List<TestExecutionRow> rawRows,
      int executionsPerLaunchWindow) {
    return classify(rawRows, executionsPerLaunchWindow, false);
  }

  /**
   * @param aggregateByTestName if true, bucket by normalized {@link TestExecutionRow#getName()}
   *                            (stable hash key {@link #testNameMergeKey(TestExecutionRow)});
   *                            otherwise one bucket per physical launch and RP {@code unique_id}
   *                            ({@link #stabilityKeyPerLaunch(TestExecutionRow)}) so the same case
   *                            in multiple launches is counted separately (totals align with summing
   *                            per-launch item counts on the Launches page).
   */
  public static Map<String, ClassifiedTest> classify(List<TestExecutionRow> rawRows,
      int executionsPerLaunchWindow, boolean aggregateByTestName) {
    if (rawRows == null || rawRows.isEmpty()) {
      return Map.of();
    }
    Map<String, List<TestExecutionRow>> buckets = new LinkedHashMap<>();
    for (TestExecutionRow r : rawRows) {
      String key =
          aggregateByTestName ? testNameMergeKey(r) : stabilityKeyPerLaunch(r);
      buckets.computeIfAbsent(key, u -> new ArrayList<>()).add(r);
    }
    Map<String, ClassifiedTest> out = new LinkedHashMap<>();
    for (Map.Entry<String, List<TestExecutionRow>> e : buckets.entrySet()) {
      ClassifiedTest ct =
          classifyUnique(e.getKey(), e.getValue(), executionsPerLaunchWindow, aggregateByTestName);
      if (ct != null) {
        out.put(e.getKey(), ct);
      }
    }
    return out;
  }

  /**
   * Classify using a full execution history while restricting output rows to tests present in the
   * membership snapshot (e.g. latest launch per pipeline name). Transitions are computed from the
   * last N runs per logical launch in {@code historyRows}; row keys and counts come from
   * {@code membershipRows}.
   */
  public static Map<String, ClassifiedTest> classifyWithMembershipScope(
      List<TestExecutionRow> historyRows,
      List<TestExecutionRow> membershipRows,
      int executionsPerLaunchWindow,
      boolean aggregateByTestName) {
    if (membershipRows == null || membershipRows.isEmpty()) {
      return Map.of();
    }
    if (historyRows == null || historyRows.isEmpty()) {
      return Map.of();
    }
    Map<String, TestExecutionRow> membershipByKey =
        dedupeMembershipRows(membershipRows, aggregateByTestName);
    Map<String, ClassifiedTest> out = new LinkedHashMap<>();
    if (aggregateByTestName) {
      Map<String, List<TestExecutionRow>> historyByMergeKey = new LinkedHashMap<>();
      for (TestExecutionRow r : historyRows) {
        historyByMergeKey.computeIfAbsent(testNameMergeKey(r), k -> new ArrayList<>()).add(r);
      }
      for (Map.Entry<String, TestExecutionRow> e : membershipByKey.entrySet()) {
        List<TestExecutionRow> hist = historyByMergeKey.get(e.getKey());
        if (hist == null || hist.isEmpty()) {
          continue;
        }
        ClassifiedTest ct =
            classifyUnique(e.getKey(), hist, executionsPerLaunchWindow, true);
        if (ct != null) {
          out.put(e.getKey(), ct);
        }
      }
    } else {
      for (Map.Entry<String, TestExecutionRow> e : membershipByKey.entrySet()) {
        TestExecutionRow mem = e.getValue();
        String logical = logicalLaunchKey(mem);
        String uid = mem.getUniqueId();
        List<TestExecutionRow> hist = historyRows.stream()
            .filter(r -> logicalLaunchKey(r).equals(logical) && Objects.equals(uid, r.getUniqueId()))
            .collect(Collectors.toList());
        if (hist.isEmpty()) {
          continue;
        }
        ClassifiedTest ct =
            classifyUnique(e.getKey(), hist, executionsPerLaunchWindow, false);
        if (ct != null) {
          out.put(e.getKey(), ct);
        }
      }
    }
    return out;
  }

  private static Map<String, TestExecutionRow> dedupeMembershipRows(
      List<TestExecutionRow> membershipRows, boolean aggregateByTestName) {
    Map<String, TestExecutionRow> map = new LinkedHashMap<>();
    Comparator<TestExecutionRow> latestFirst = Comparator
        .comparing(TestExecutionRow::getItemStartTime, Comparator.nullsFirst(Instant::compareTo))
        .thenComparingLong(TestExecutionRow::getItemId);
    for (TestExecutionRow r : membershipRows) {
      String key = aggregateByTestName ? testNameMergeKey(r) : stabilityKeyPerLaunch(r);
      map.merge(key, r, (a, b) -> latestFirst.compare(a, b) >= 0 ? a : b);
    }
    return map;
  }

  static String stabilityKeyPerLaunch(TestExecutionRow r) {
    return r.getLaunchId() + STABILITY_KEY_LAUNCH_UID_SEP + r.getUniqueId();
  }

  /**
   * Normalized test name (trim + collapse whitespace) for grouping.
   */
  public static String normalizeTestName(String name) {
    if (name == null || name.isBlank()) {
      return "";
    }
    return name.trim().replaceAll("\\s+", " ");
  }

  /**
   * Stable key for merge-by-name: {@code tn:} + sha256-hex(normalized name), or raw {@code uniqueId}
   * when name is blank.
   */
  public static String testNameMergeKey(TestExecutionRow r) {
    String n = normalizeTestName(r.getName());
    if (n.isEmpty()) {
      return r.getUniqueId();
    }
    return TEST_NAME_MERGE_PREFIX + sha256Hex(n);
  }

  private static String sha256Hex(String s) {
    try {
      MessageDigest md = MessageDigest.getInstance("SHA-256");
      byte[] digest = md.digest(s.getBytes(StandardCharsets.UTF_8));
      return HexFormat.of().formatHex(digest);
    } catch (NoSuchAlgorithmException e) {
      throw new IllegalStateException(e);
    }
  }

  /** Whether a raw row belongs to a classification key from {@link #classify(List, int, boolean)}. */
  public static boolean rowBelongsToStabilityKey(String stabilityKey, TestExecutionRow r,
      boolean aggregateByTestName) {
    if (aggregateByTestName) {
      return stabilityKey != null && stabilityKey.equals(testNameMergeKey(r));
    }
    if (stabilityKey == null) {
      return false;
    }
    String sep = STABILITY_KEY_LAUNCH_UID_SEP;
    int idx = stabilityKey.indexOf(sep);
    if (idx <= 0 || idx + sep.length() > stabilityKey.length()) {
      return false;
    }
    try {
      long lid = Long.parseLong(stabilityKey.substring(0, idx));
      String uid = stabilityKey.substring(idx + sep.length());
      return lid == r.getLaunchId() && Objects.equals(uid, r.getUniqueId());
    } catch (NumberFormatException e) {
      return false;
    }
  }

  /**
   * Groups reruns that share the same launch name (e.g. {@code my_launch #8} and {@code #9})
   * into one logical launch — matches how users interpret "unique launch" vs execution number.
   */
  public static String logicalLaunchKey(TestExecutionRow r) {
    String name = r.getLaunchName();
    if (name != null && !name.isBlank()) {
      return name.trim();
    }
    return "launchId:" + r.getLaunchId();
  }

  /**
   * Drill-down rows: one row per logical launch. Caller passes only rows already scoped to the test
   * (one {@code unique_id} or merged-by-name group).
   */
  public static List<TestStabilityPerLaunchContent> perLaunchBreakdown(
      List<TestExecutionRow> rowsInScope, int executionsPerLaunchWindow) {
    if (rowsInScope == null || rowsInScope.isEmpty()) {
      return List.of();
    }
    Map<String, List<TestExecutionRow>> byLogicalLaunch = new LinkedHashMap<>();
    for (TestExecutionRow r : rowsInScope) {
      byLogicalLaunch.computeIfAbsent(logicalLaunchKey(r), x -> new ArrayList<>()).add(r);
    }
    List<TestStabilityPerLaunchContent> rows = new ArrayList<>();
    for (Map.Entry<String, List<TestExecutionRow>> e : byLogicalLaunch.entrySet()) {
      List<TestExecutionRow> sorted = new ArrayList<>(e.getValue());
      sorted.sort(Comparator
          .comparing(TestExecutionRow::getItemStartTime, Comparator.nullsFirst(Instant::compareTo))
          .thenComparingLong(TestExecutionRow::getItemId));
      int from = Math.max(0, sorted.size() - executionsPerLaunchWindow);
      List<TestExecutionRow> window = sorted.subList(from, sorted.size());
      BandDecision bd = assignBandForWindow(window);
      TestExecutionRow latest = window.get(window.size() - 1);
      TestStabilityPerLaunchContent pl = new TestStabilityPerLaunchContent();
      pl.setLaunchId(latest.getLaunchId());
      pl.setLaunchName(latest.getLaunchName());
      pl.setUniqueId(latest.getUniqueId());
      pl.setName(latest.getName());
      pl.setTransitions(bd.transitions);
      pl.setFlakinessPercent((int) Math.round(bd.score * 100.0d));
      pl.setFlakinessScore(Math.round(bd.score * 1_000_000d) / 1_000_000d);
      pl.setExecutionCount(window.size());
      pl.setStability(bd.band.name());
      pl.setItemId(latest.getItemId());
      rows.add(pl);
    }
    rows.sort(Comparator.comparing(TestStabilityPerLaunchContent::getLaunchName,
        Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER)));
    return rows;
  }

  private static ClassifiedTest classifyUnique(String stabilityKey, List<TestExecutionRow> rowsForUid,
      int execWindow, boolean aggregateByTestName) {
    if (rowsForUid == null || rowsForUid.isEmpty()) {
      return null;
    }
    Map<String, List<TestExecutionRow>> byLogicalLaunch = new LinkedHashMap<>();
    for (TestExecutionRow r : rowsForUid) {
      byLogicalLaunch.computeIfAbsent(logicalLaunchKey(r), x -> new ArrayList<>()).add(r);
    }
    /*
     * Launches column = distinct logical launches (trimmed launch name; fallback launch id when name
     * blank) — aligns with drill-down rows and per-launch windows.
     */
    int launchCount =
        (int) rowsForUid.stream().map(TestStabilityFlakinessClassifier::logicalLaunchKey).distinct()
            .count();

    List<TestExecutionRow> globalTimeline = new ArrayList<>();
    List<JStatusEnum> statusesForMixed = new ArrayList<>();

    TestExecutionRow latestOverall = rowsForUid.stream()
        .max(Comparator
            .comparing(TestExecutionRow::getItemStartTime, Comparator.nullsFirst(Instant::compareTo))
            .thenComparingLong(TestExecutionRow::getItemId))
        .orElse(rowsForUid.get(0));

    for (List<TestExecutionRow> launchRows : byLogicalLaunch.values()) {
      launchRows.sort(Comparator
          .comparing(TestExecutionRow::getItemStartTime, Comparator.nullsFirst(Instant::compareTo))
          .thenComparingLong(TestExecutionRow::getItemId));
      int from = Math.max(0, launchRows.size() - execWindow);
      List<TestExecutionRow> window = launchRows.subList(from, launchRows.size());
      globalTimeline.addAll(window);
      for (TestExecutionRow x : window) {
        statusesForMixed.add(x.getStatus());
      }
    }

    globalTimeline.sort(Comparator
        .comparing(TestExecutionRow::getItemStartTime, Comparator.nullsFirst(Instant::compareTo))
        .thenComparingLong(TestExecutionRow::getItemId));

    long totalTransitions = 0;
    long totalPairs = globalTimeline.size() >= 2 ? globalTimeline.size() - 1 : 0;
    for (int i = 0; i < globalTimeline.size() - 1; i++) {
      if (globalTimeline.get(i).getStatus() != globalTimeline.get(i + 1).getStatus()) {
        totalTransitions++;
      }
    }
    int totalExecInWindows = globalTimeline.size();

    boolean mixed = mixedOutcomes(statusesForMixed);
    double score = totalPairs > 0 ? (double) totalTransitions / totalPairs : 0d;
    int flPct = (int) Math.round(score * 100.0d);

    StabilityBand band;
    String explain;
    if (mixed && score >= FLAKY_THRESHOLD) {
      band = StabilityBand.FLAKY;
      explain = TestStabilityFlakinessAggregator.EXPLANATION_KEY_FLAKY;
    } else if (mixed && totalTransitions >= 1) {
      band = StabilityBand.TRANSITIONAL;
      explain = EXPLAIN_TRANSITIONAL;
    } else if (!mixed && latestOverall.getStatus() == JStatusEnum.FAILED) {
      band = StabilityBand.FAILED;
      explain = EXPLAIN_FAILED;
    } else {
      band = StabilityBand.STABLE;
      explain = EXPLAIN_STABLE;
    }

    String rpUid =
        reportPortalUniqueIdForOutput(stabilityKey, aggregateByTestName, rowsForUid);
    return new ClassifiedTest(
        stabilityKey,
        rpUid,
        latestOverall.getName(),
        totalTransitions,
        score,
        flPct,
        totalExecInWindows,
        band,
        explain,
        latestOverall.getItemId(),
        latestOverall.getLaunchId(),
        latestOverall.getLaunchName(),
        latestOverall.getLaunchNumber(),
        launchCount);
  }

  private static String reportPortalUniqueIdForOutput(String stabilityKey,
      boolean aggregateByTestName,
      List<TestExecutionRow> rowsForUid) {
    if (aggregateByTestName) {
      return stabilityKey;
    }
    String sep = STABILITY_KEY_LAUNCH_UID_SEP;
    int idx = stabilityKey.indexOf(sep);
    if (idx > 0 && idx < stabilityKey.length() - sep.length()) {
      return stabilityKey.substring(idx + sep.length());
    }
    return rowsForUid.get(0).getUniqueId();
  }

  private static final class BandDecision {
    final StabilityBand band;
    final long transitions;
    final double score;

    BandDecision(StabilityBand band, long transitions, double score) {
      this.band = band;
      this.transitions = transitions;
      this.score = score;
    }
  }

  private static BandDecision assignBandForWindow(List<TestExecutionRow> window) {
    if (window.isEmpty()) {
      return new BandDecision(StabilityBand.STABLE, 0, 0d);
    }
    long transitions = 0;
    if (window.size() >= 2) {
      for (int i = 0; i < window.size() - 1; i++) {
        if (window.get(i).getStatus() != window.get(i + 1).getStatus()) {
          transitions++;
        }
      }
    }
    long pairs = window.size() >= 2 ? window.size() - 1 : 0;
    double score = pairs > 0 ? (double) transitions / pairs : 0d;
    List<JStatusEnum> st = window.stream().map(TestExecutionRow::getStatus).collect(Collectors.toList());
    boolean windowMixed = mixedOutcomes(st);
    JStatusEnum last = window.get(window.size() - 1).getStatus();
    StabilityBand band;
    if (windowMixed && score >= FLAKY_THRESHOLD) {
      band = StabilityBand.FLAKY;
    } else if (windowMixed && transitions >= 1) {
      band = StabilityBand.TRANSITIONAL;
    } else if (!windowMixed && last == JStatusEnum.FAILED) {
      band = StabilityBand.FAILED;
    } else {
      band = StabilityBand.STABLE;
    }
    return new BandDecision(band, transitions, score);
  }

  private static boolean mixedOutcomes(List<JStatusEnum> statuses) {
    if (statuses == null || statuses.size() < 2) {
      return false;
    }
    JStatusEnum first = statuses.get(0);
    for (int i = 1; i < statuses.size(); i++) {
      if (statuses.get(i) != first) {
        return true;
      }
    }
    return false;
  }
}
