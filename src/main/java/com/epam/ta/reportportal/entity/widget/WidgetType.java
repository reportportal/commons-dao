/*
 * Copyright (C) 2018 EPAM Systems
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.epam.ta.reportportal.entity.widget;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Optional;

/**
 * @author Pavel Bortnik
 */
public enum WidgetType {

	OLD_LINE_CHART("oldLineChart", false),
	INVESTIGATED_TREND("investigatedTrend", false),
	LAUNCH_STATISTICS("launchStatistics", false),
	STATISTIC_TREND("statisticTrend", false),
	CASES_TREND("casesTrend", false),
	NOT_PASSED("notPassed", false),
	OVERALL_STATISTICS("overallStatistics", false),
	UNIQUE_BUG_TABLE("uniqueBugTable", false),
	BUG_TREND("bugTrend", false),
	ACTIVITY("activityStream", false),
	LAUNCHES_COMPARISON_CHART("launchesComparisonChart", false),
	LAUNCHES_DURATION_CHART("launchesDurationChart", false),
	LAUNCHES_TABLE("launchesTable", false),
	TOP_TEST_CASES("topTestCases", false),
	FLAKY_TEST_CASES("flakyTestCases", false),
	PASSING_RATE_SUMMARY("passingRateSummary", false),
	PASSING_RATE_PER_LAUNCH("passingRatePerLaunch", false),
	PRODUCT_STATUS("productStatus", false),
	MOST_TIME_CONSUMING("mostTimeConsuming", false),

	CUMULATIVE("cumulative", true);

	private final String type;

	private final boolean supportMultilevelStructure;

	WidgetType(String type, boolean supportMultilevelStructure) {
		this.type = type;
		this.supportMultilevelStructure = supportMultilevelStructure;
	}

	public String getType() {
		return this.type;
	}

	public boolean isSupportMultilevelStructure() {
		return supportMultilevelStructure;
	}

	public static WidgetType getByName(String type) {
		return WidgetType.valueOf(type);
	}

	public static Optional<WidgetType> findByName(@Nullable String name) {
		return Arrays.stream(WidgetType.values()).filter(gadgetTypes -> gadgetTypes.getType().equalsIgnoreCase(name)).findAny();
	}

}
