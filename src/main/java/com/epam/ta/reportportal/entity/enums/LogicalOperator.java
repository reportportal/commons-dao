/*
 * Copyright 2022 EPAM Systems
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
 * @author Andrei Piankouski
 */
public enum LogicalOperator {

	AND("AND"),
	OR("OR");

	private final String value;

	LogicalOperator(String value) {
		this.value = value;
	}

	public static Optional<LogicalOperator> findByName(String name) {
		return Arrays.stream(LogicalOperator.values()).filter(val -> val.getOperator().equalsIgnoreCase(name)).findAny();
	}

	public static boolean isPresent(String name) {
		return findByName(name).isPresent();
	}

	public String getOperator() {
		return value;
	}
}
