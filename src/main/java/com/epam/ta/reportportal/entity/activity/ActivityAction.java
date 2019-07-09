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

package com.epam.ta.reportportal.entity.activity;

import java.util.Arrays;
import java.util.Optional;

/**
 * @author <a href="mailto:ihar_kahadouski@epam.com">Ihar Kahadouski</a>
 */
public enum ActivityAction {

	CREATE_DASHBOARD("createDashboard"),
	UPDATE_DASHBOARD("updateDashboard"),
	DELETE_DASHBOARD("deleteDashboard"),
	CREATE_WIDGET("createWidget"),
	UPDATE_WIDGET("updateWidget"),
	DELETE_WIDGET("deleteWidget"),
	CREATE_FILTER("createFilter"),
	UPDATE_FILTER("updateFilter"),
	DELETE_FILTER("deleteFilter"),
	ANALYZE_ITEM("analyzeItem"),
	CREATE_DEFECT("createDefect"),
	UPDATE_DEFECT("updateDefect"),
	DELETE_DEFECT("deleteDefect"),
	CREATE_INTEGRATION("createIntegration"),
	UPDATE_INTEGRATION("updateIntegration"),
	DELETE_INTEGRATION("deleteIntegration"),
	START_LAUNCH("startLaunch"),
	FINISH_LAUNCH("finishLaunch"),
	DELETE_LAUNCH("deleteLaunch"),
	UPDATE_PROJECT("updateProject"),
	UPDATE_ANALYZER("updateAnalyzer"),
	POST_ISSUE("postIssue"),
	LINK_ISSUE("linkIssue"),
	LINK_ISSUE_AA("linkIssueAa"),
	UNLINK_ISSUE("unlinkIssue"),
	UPDATE_ITEM("updateItem"),
	CREATE_USER("createUser"),
	DELETE_INDEX("deleteIndex"),
	GENERATE_INDEX("generateIndex"),
	START_IMPORT("startImport"),
	FINISH_IMPORT("finishImport"),
	CREATE_PATTERN("createPattern"),
	UPDATE_PATTERN("updatePattern"),
	DELETE_PATTERN("deletePattern"),
	PATTERN_MATCHED("patternMatched");

	private String value;

	ActivityAction(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public static Optional<ActivityAction> fromString(String string) {
		return Optional.ofNullable(string).flatMap(str -> Arrays.stream(values()).filter(it -> it.value.equalsIgnoreCase(str)).findAny());
	}
}