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
package com.epam.ta.reportportal.entity.enums;

import java.util.Arrays;
import java.util.Optional;

/**
 * @author Pavel Bortnik
 */
public enum ActivityEventType {

	CREATE_DASHBOARD("create_dashboard"),
	UPDATE_DASHBOARD("update_dashboard"),
	DELETE_DASHBOARD("delete_dashboard"),
	CREATE_WIDGET("create_widget"),
	UPDATE_WIDGET("update_widget"),
	DELETE_WIDGET("delete_widget"),
	CREATE_FILTER("create_filter"),
	UPDATE_FILTER("update_filter"),
	DELETE_FILTER("delete_filter"),
	ANALYZE_ITEM("analyze_item"),
	UPDATE_DEFECT("update_defect"),
	DELETE_DEFECT("delete_defect"),
	CREATE_BTS("create_bts"),
	UPDATE_BTS("update_bts"),
	DELETE_BTS("delete_bts"),
	START_LAUNCH("start_launch"),
	FINISH_LAUNCH("finish_launch"),
	DELETE_LAUNCH("delete_launch"),
	UPDATE_PROJECT("update_project"),
	POST_ISSUE("post_issue"),
	ATTACH_ISSUE("attach_issue"),
	ATTACH_ISSUE_AA("attach_issue_aa"),
	UPDATE_ITEM("update_item"),
	CREATE_USER("create_user"),
	START_IMPORT("start_import"),
	FINISH_IMPORT("finish_import");

	private String value;

	ActivityEventType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public static Optional<ActivityEventType> fromString(String string) {
		return Optional.ofNullable(string).flatMap(str -> Arrays.stream(values()).filter(it -> it.value.equals(str)).findAny());
	}
}
