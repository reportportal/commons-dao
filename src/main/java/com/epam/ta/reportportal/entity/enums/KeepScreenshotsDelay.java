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
 * Keep Screenshots Delay values enumerator
 * Describe possible values of 'Keep screenshots' parameter on UI project page
 *
 * @author Andrei_Ramanchuk
 */
public enum KeepScreenshotsDelay {

	//@formatter:off
	ONE_WEEK("1 week", 7L),
	TWO_WEEKS("2 weeks", 14L),
	THREE_WEEKS("3 weeks", 21L),
	ONE_MONTH("1 month", 30L),
	THREE_MONTHS("3 months", 91L),
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

	KeepScreenshotsDelay(String delay, long days) {
		this.value = delay;
		this.days = days;
	}

	public static Optional<KeepScreenshotsDelay> findByName(String name) {
		return Arrays.stream(KeepScreenshotsDelay.values()).filter(delay -> delay.getValue().equalsIgnoreCase(name)).findAny();
	}

	public static boolean isPresent(String name) {
		return findByName(name).isPresent();
	}
}