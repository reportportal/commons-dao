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
 * Interruption job delay parameters enumerator<br>
 * User for supporting UI types of project parameter
 *
 * @author Andrei_Ramanchuk
 */
public enum InterruptionJobDelay {

	//@formatter:off
	ONE_HOUR("1 hour", 1L),
	THREE_HOURS("3 hours", 3L),
	SIX_HOURS("6 hours", 6L),
	TWELVE_HOURS("12 hours", 12L),
	ONE_DAY("1 day", 24L),
	ONE_WEEK("1 week", 168L);
	//@formatter:on

	private String value;

	private long period;

	public String getValue() {
		return value;
	}

	public long getPeriod() {
		return period;
	}

	InterruptionJobDelay(String delay, long time) {
		this.value = delay;
		this.period = time;
	}

	public static Optional<InterruptionJobDelay> findByName(String name) {
		return Arrays.stream(InterruptionJobDelay.values()).filter(delay -> delay.getValue().equalsIgnoreCase(name)).findAny();
	}

	public static boolean isPresent(String name) {
		return findByName(name).isPresent();
	}
}