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
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.epam.ta.reportportal.entity.enums;

import com.epam.ta.reportportal.entity.AnalyzeMode;
import com.epam.ta.reportportal.entity.project.ProjectAnalyzerConfig;

import java.util.Arrays;
import java.util.Optional;

/**
 * Enum with a list of basic required project parameters
 *
 * @author Andrey Plisunov
 */
public enum ProjectAttributeEnum {

	NOTIFICATIONS_ENABLED("notifications.enabled", String.valueOf(false)),

	INTERRUPT_JOB_TIME(Prefix.JOB + "interruptJobTime", InterruptionJobDelay.ONE_DAY.getValue()),
	KEEP_LAUNCHES(Prefix.JOB + "keepLaunches", KeepLaunchDelay.THREE_MONTHS.getValue()),
	KEEP_LOGS(Prefix.JOB + "keepLogs", KeepLogsDelay.THREE_MONTHS.getValue()),
	KEEP_SCREENSHOTS(Prefix.JOB + "keepScreenshots", KeepScreenshotsDelay.TWO_WEEKS.getValue()),

	MIN_SHOULD_MATCH(Prefix.ANALYZER + "minShouldMatch", String.valueOf(ProjectAnalyzerConfig.MIN_SHOULD_MATCH)),
	NUMBER_OF_LOG_LINES(Prefix.ANALYZER + "numberOfLogLines", String.valueOf(ProjectAnalyzerConfig.NUMBER_OF_LOG_LINES)),
	INDEXING_RUNNING(Prefix.ANALYZER + "indexingRunning", String.valueOf(false)),
	AUTO_PATTERN_ANALYZER_ENABLED(Prefix.ANALYZER + "isAutoPatternAnalyzerEnabled", String.valueOf(false)),
	AUTO_ANALYZER_ENABLED(Prefix.ANALYZER + "isAutoAnalyzerEnabled", String.valueOf(false)),
	AUTO_ANALYZER_MODE(Prefix.ANALYZER + "autoAnalyzerMode", AnalyzeMode.BY_LAUNCH_NAME.getValue());

	private String attribute;
	private String defaultValue;

	ProjectAttributeEnum(String attribute, String defaultValue) {
		this.attribute = attribute;
		this.defaultValue = defaultValue;
	}

	public static Optional<ProjectAttributeEnum> findByAttributeName(String attributeName) {
		return Arrays.stream(ProjectAttributeEnum.values()).filter(v -> v.getAttribute().equalsIgnoreCase(attributeName)).findAny();
	}

	public static boolean isPresent(String name) {
		return findByAttributeName(name).isPresent();
	}

	public String getAttribute() {
		return attribute;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public static class Prefix {
		public static final String ANALYZER = "analyzer.";
		public static final String JOB = "job.";
	}
}


