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

	OLD_LINE_CHART("oldLineChart"),
	INVESTIGATED_TREND("investigatedTrend"),
	LAUNCH_STATISTICS("launchStatistics"),
	STATISTIC_TREND("statisticTrend"),
	CASES_TREND("casesTrend"),
	NOT_PASSED("notPassed"),
	OVERALL_STATISTICS("overallStatistics"),
	UNIQUE_BUG_TABLE("uniqueBugTable"),
	BUG_TREND("bugTrend"),
	ACTIVITY("activityStream"),
	LAUNCHES_COMPARISON_CHART("launchesComparisonChart"),
	LAUNCHES_DURATION_CHART("launchesDurationChart"),
	LAUNCHES_TABLE("launchesTable"),
	TOP_TEST_CASES("topTestCases"),
	FLAKY_TEST_CASES("flakyTestCases"),
	PASSING_RATE_SUMMARY("passingRateSummary"),
	PASSING_RATE_PER_LAUNCH("passingRatePerLaunch"),
	PRODUCT_STATUS("productStatus"),
	MOST_TIME_CONSUMING("mostTimeConsuming"),
	CUMULATIVE("cumulative");

	private final String type;

	WidgetType(String type) {
		this.type = type;
	}

	public String getType() {
		return this.type;
	}

	public static WidgetType getByName(String type) {
		return WidgetType.valueOf(type);
	}

	public static Optional<WidgetType> findByName(@Nullable String name) {
		return Arrays.stream(WidgetType.values()).filter(gadgetTypes -> gadgetTypes.getType().equalsIgnoreCase(name)).findAny();
	}

}
