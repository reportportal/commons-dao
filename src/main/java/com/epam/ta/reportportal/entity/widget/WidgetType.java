/*
 * Copyright 2017 EPAM Systems
 *
 *
 * This file is part of EPAM Report Portal.
 * https://github.com/reportportal/service-api
 *
 * Report Portal is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Report Portal is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Report Portal.  If not, see <http://www.gnu.org/licenses/>.
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
