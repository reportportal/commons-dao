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
 * Keep Launches Delay project parameter enumerator<br>
 * Describe possible values for 'Keep launches' parameter on UI project page
 *
 * @author Andrei_Ramanchuk
 */
public enum KeepLogsDelay {

	//@formatter:off
	TWO_WEEKS("2 weeks", 14L),
	ONE_MONTH("1 month", 30L),
	THREE_MONTHS("3 months", 91L),
	SIX_MONTHS("6 months", 183L),
	FOREVER("forever", 0);
	//@formatter:on

	private String value;

	private long days;

	public String getValue() {
		return value;
	}

	public long getDays() {
		return days;
	}

	KeepLogsDelay(String delay, long period) {
		this.value = delay;
		this.days = period;
	}

	public static Optional<KeepLogsDelay> findByName(String name) {
		return Arrays.stream(KeepLogsDelay.values()).filter(delay -> delay.getValue().equalsIgnoreCase(name)).findAny();
	}

	public static boolean isPresent(String name) {
		return findByName(name).isPresent();
	}
}