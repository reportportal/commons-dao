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

	OLD_LINE_CHART("old_line_chart"),
	INVESTIGATED_TREND("investigated_trend"),
	LAUNCH_STATISTICS("launch_statistics"),
	STATISTIC_TREND("statistic_trend"),
	CASES_TREND("cases_trend"),
	NOT_PASSED("not_passed"),
	OVERALL_STATISTICS("overall_statistics"),
	UNIQUE_BUG_TABLE("unique_bug_table"),
	BUG_TREND("bug_trend"),
	ACTIVITY("activity_stream"),
	LAUNCHES_COMPARISON_CHART("launches_comparison_chart"),
	LAUNCHES_DURATION_CHART("launches_duration_chart"),
	LAUNCHES_TABLE("launches_table"),
	TOP_TEST_CASES("top_test_cases"),
	FLAKY_TEST_CASES("flaky_test_cases"),
	PASSING_RATE_SUMMARY("passing_rate_summary"),
	PASSING_RATE_PER_LAUNCH("passing_rate_per_launch"),
	PRODUCT_STATUS("product_status"),
	MOST_TIME_CONSUMING("most_time_consuming"),
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
