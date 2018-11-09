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

package com.epam.ta.reportportal.entity;

import java.util.Arrays;
import java.util.Optional;

public enum Status implements StatisticsAwareness {

	//@formatter:off
	IN_PROGRESS(""),
	PASSED("passed"),
	FAILED("failed"),
	STOPPED("stopped"), //status for manually stopped launches
	SKIPPED("skipped"),
	INTERRUPTED("failed"),
	RESETED("reseted"), //status for items with deleted descendants
	CANCELLED("cancelled"); //soupUI specific status
	//@formatter:on

	private final String executionCounterField;

	Status(String executionCounterField) {
		this.executionCounterField = executionCounterField;
	}

	public static Optional<Status> fromValue(String value) {
		return Arrays.stream(Status.values()).filter(status -> status.name().equalsIgnoreCase(value)).findAny();
	}

	@Override
	public String awareStatisticsField() {
		return executionCounterField;
	}

}