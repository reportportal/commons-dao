/*
 *  Copyright (C) 2018 EPAM Systems
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.epam.ta.reportportal.entity.project.email;

import java.util.Arrays;
import java.util.Optional;

/**
 * @author Pavel Bortnik
 */
public enum SendCaseType {

	RECIPIENTS("recipients"),
	LAUNCH_SEND_RULE("launch_stats_rule"),
	LAUNCH_NAME_RULE("launch_name_rule"),
	LAUNCH_TAG_RULE("launch_tag_rule");

	private final String value;

	SendCaseType(String value) {
		this.value = value;
	}

	public static Optional<SendCaseType> findByName(String name) {
		return Arrays.stream(SendCaseType.values()).filter(val -> val.getCaseTypeString().equalsIgnoreCase(name)).findAny();
	}

	public static boolean isPresent(String name) {
		return findByName(name).isPresent();
	}

	public String getCaseTypeString() {
		return value;
	}

}
