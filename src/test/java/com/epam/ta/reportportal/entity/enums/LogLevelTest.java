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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author <a href="mailto:ihar_kahadouski@epam.com">Ihar Kahadouski</a>
 */
class LogLevelTest {

	private Map<LogLevel, List<String>> allowedNames;
	private List<String> disallowedNames;
	private Map<LogLevel, Integer> allowedCodes;
	private List<Integer> disallowedCodes;

	@BeforeEach
	void setUp() throws Exception {
		allowedNames = Arrays.stream(LogLevel.values())
				.collect(Collectors.toMap(it -> it, it -> Arrays.asList(it.name(), it.name().toUpperCase(), it.name().toLowerCase())));
		disallowedNames = Arrays.asList("NoSuchLogLevel", "", " ", "null", "warrrn");
		allowedCodes = Arrays.stream(LogLevel.values()).collect(Collectors.toMap(it -> it, LogLevel::toInt));
		disallowedCodes = Arrays.asList(0, 1500, 999, 7);
	}

	@Test
	void isGreaterOrEqual() {
		final LogLevel warn = LogLevel.WARN;
		final LogLevel trace = LogLevel.TRACE;
		final LogLevel warnSecond = LogLevel.WARN;
		final LogLevel fatal = LogLevel.FATAL;

		assertTrue(warn.isGreaterOrEqual(trace));
		assertTrue(warn.isGreaterOrEqual(warnSecond));
		assertFalse(warn.isGreaterOrEqual(fatal));
	}

	@Test
	void toLevel() {
		allowedNames.forEach((key, value) -> value.forEach(val -> {
			final Optional<LogLevel> optional = LogLevel.toLevel(val);
			assertTrue(optional.isPresent());
			assertEquals(key, optional.get());
		}));
		disallowedNames.forEach(it -> assertFalse(LogLevel.toLevel(it).isPresent()));
	}

	@Test
	void toLevelInt() {
		allowedCodes.forEach((key, value) -> assertEquals(key, LogLevel.toLevel(value)));
	}

	@Test
	void toCustomLogLevel() {
		allowedNames.forEach((key, value) -> value.forEach(val -> assertEquals(key.toInt(), LogLevel.toCustomLogLevel(val))));
		allowedCodes.forEach((key, val) -> assertEquals(key.toInt(), LogLevel.toCustomLogLevel(val.toString())));
		disallowedCodes.forEach(it -> {
			if (it < LogLevel.TRACE_INT) {
				assertEquals(LogLevel.TRACE_INT, LogLevel.toCustomLogLevel(it.toString()));
			} else {
				assertEquals(it.intValue(), LogLevel.toCustomLogLevel(it.toString()));
			}
		});
	}

	@Test
	void toCustomLogLevelNames() {
		Collections.shuffle(disallowedNames);
		final String wrongLogName = disallowedNames.get(0);
		assertEquals(LogLevel.UNKNOWN.toInt(), LogLevel.toCustomLogLevel(wrongLogName));
	}

	@Test
	void toCustomLogLevelCodesFail() {

		final int i = LogLevel.toCustomLogLevel(disallowedCodes.get(0).toString());
		assertEquals(LogLevel.TRACE.toInt(), i);
	}

	@Test
	void toLevelIntFail() {
		Collections.shuffle(disallowedCodes);
		final Integer code = disallowedCodes.get(0);

		final ReportPortalException exception = assertThrows(ReportPortalException.class, () -> LogLevel.toLevel(code));
		assertEquals("Error in Save Log Request. Wrong level = " + code, exception.getMessage());
	}
}