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

package com.epam.ta.reportportal.entity.project.email;

import java.util.Arrays;
import java.util.Optional;

/**
 * Email notification cases enumerator for project settings
 *
 * @author Andrei_Ramanchuk
 */
public enum LaunchStatsRule {

	//@formatter:off
	ALWAYS("always"),
	FAILED("failed"),
	TO_INVESTIGATE("to_investigate"),
	MORE_10("more_10"),
	MORE_20("more_20"),
	MORE_50("more_50");
	//@formatter:on

	private final String value;

	LaunchStatsRule(String value) {
		this.value = value;
	}

	public static Optional<LaunchStatsRule> findByName(String name) {
		return Arrays.stream(LaunchStatsRule.values()).filter(val -> val.getRuleString().equalsIgnoreCase(name)).findAny();
	}

	public static boolean isPresent(String name) {
		return findByName(name).isPresent();
	}

	public String getRuleString() {
		return value;
	}
}