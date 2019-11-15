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

import com.epam.ta.reportportal.exception.ReportPortalException;
import com.epam.ta.reportportal.ws.model.ErrorType;

import java.util.Arrays;
import java.util.Optional;

public enum LogLevel {

	//@formatter:off
	ERROR(LogLevel.ERROR_INT),
	WARN(LogLevel.WARN_INT),
	INFO(LogLevel.INFO_INT), 
	DEBUG(LogLevel.DEBUG_INT), 
	TRACE(LogLevel.TRACE_INT),
	FATAL(LogLevel.FATAL_INT),
	UNKNOWN(LogLevel.UNKNOWN_INT);
	//@formatter:on

	public static final int UNKNOWN_INT = 60000;
	public static final int FATAL_INT = 50000;
	public static final int ERROR_INT = 40000;
	public static final int WARN_INT = 30000;
	public static final int INFO_INT = 20000;
	public static final int DEBUG_INT = 10000;
	public static final int TRACE_INT = 5000;

	private int intLevel;

	LogLevel(int intLevel) {
		this.intLevel = intLevel;
	}

	public int toInt() {
		return intLevel;
	}

	/**
	 * Returns <code>true</code> if this Level has a higher or equal Level than the Level passed as
	 * argument, <code>false</code> otherwise.
	 */
	public boolean isGreaterOrEqual(LogLevel r) {
		return intLevel >= r.intLevel;
	}

	/**
	 * Convert the string passed as argument to a Level. If there is no such level throws exception
	 */
	public static Optional<LogLevel> toLevel(String levelString) {
		return Arrays.stream(LogLevel.values()).filter(level -> level.name().equalsIgnoreCase(levelString)).findAny();
	}

	/**
	 * Convert the string passed as argument to a Level.
	 */
	public static int toCustomLogLevel(String levelString) {

		Optional<LogLevel> level = Arrays.stream(LogLevel.values()).filter(l -> l.name().equalsIgnoreCase(levelString)).findFirst();

		return level.map(LogLevel::toInt).orElseGet(() -> {
			try {
				int intLevel = Integer.parseInt(levelString);
				return intLevel < TRACE.toInt() ? TRACE.toInt() : intLevel;
			} catch (NumberFormatException ex) {
				return UNKNOWN_INT;
			}

		});
	}

	/**
	 * Convert the string passed as argument to a Level
	 */
	public static LogLevel toLevel(int intLevel) {

		return Arrays.stream(LogLevel.values())
				.sorted((prev, curr) -> Integer.compare(curr.toInt(), prev.toInt()))
				.filter(l -> l.toInt() <= intLevel)
				.findFirst()
				.orElseThrow(() -> new ReportPortalException(ErrorType.BAD_SAVE_LOG_REQUEST, "Wrong level = " + intLevel));

	}

	@Override
	public String toString() {
		return this.name();
	}

}