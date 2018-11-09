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
public enum ActivityObjectType {

	PROJECT("project"),
	DEFECT_TYPE("defect_type"),
	TEST_ITEM("testItem"),
	LAUNCH("launch"),
	EXTERNAL_SYSTEM("externalSystem"),
	DASHBOARD("dashboard"),
	USER("user"),
	WIDGET("widget"),
	USER_FILTER("userFilter");

	private String value;

	ActivityObjectType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public static Optional<ActivityObjectType> fromString(String string) {
		return Optional.ofNullable(string).flatMap(str -> Arrays.stream(values()).filter(it -> it.value.equals(str)).findAny());
	}
}
