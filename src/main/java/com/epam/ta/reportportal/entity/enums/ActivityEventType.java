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

import java.util.Arrays;
import java.util.Optional;

/**
 * @author Pavel Bortnik
 */
public enum ActivityEventType {

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
	UPDATE_DEFECT("updateDefect"),
	DELETE_DEFECT("deleteDefect"),
	CREATE_BTS("createBts"),
	UPDATE_BTS("updateBts"),
	DELETE_BTS("deleteBts"),
	START_LAUNCH("startLaunch"),
	FINISH_LAUNCH("finishLaunch"),
	DELETE_LAUNCH("deleteLaunch"),
	UPDATE_PROJECT("updateProject"),
	POST_ISSUE("postIssue"),
	ATTACH_ISSUE("attachIssue"),
	ATTACH_ISSUE_AA("attachIssueAa"),
	UPDATE_ITEM("updateItem"),
	CREATE_USER("createUser"),
	START_IMPORT("startImport"),
	FINISH_IMPORT("finishImport");

	private String value;

	ActivityEventType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public static Optional<ActivityEventType> fromString(String string) {
		return Optional.ofNullable(string).flatMap(str -> Arrays.stream(values()).filter(it -> it.value.equalsIgnoreCase(str)).findAny());
	}
}
