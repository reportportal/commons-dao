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

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.*;
import java.util.stream.Collectors;

import static com.epam.ta.reportportal.entity.enums.EnumTestHelper.permute;
import static org.junit.Assert.*;

/**
 * @author <a href="mailto:ihar_kahadouski@epam.com">Ihar Kahadouski</a>
 */
public class LogLevelTest {

	private Map<LogLevel, List<String>> allowedNames;
	private List<String> disallowedNames;
	private Map<LogLevel, Integer> allowedCodes;
	private List<Integer> disallowedCodes;

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Before
	public void setUp() throws Exception {
		allowedNames = Arrays.stream(LogLevel.values()).collect(Collectors.toMap(it -> it, it -> permute(it.name())));
		disallowedNames = Arrays.asList("NoSuchLogLevel", "", " ", "null", "warrrn");
		allowedCodes = Arrays.stream(LogLevel.values()).collect(Collectors.toMap(it -> it, LogLevel::toInt));
		disallowedCodes = Arrays.asList(0, 1500, 999, 7, 102312389);
	}

	@Test
	public void isGreaterOrEqual() {
		final LogLevel warn = LogLevel.WARN;
		final LogLevel trace = LogLevel.TRACE;
		final LogLevel warnSecond = LogLevel.WARN;
		final LogLevel fatal = LogLevel.FATAL;

		assertTrue(warn.isGreaterOrEqual(trace));
		assertTrue(warn.isGreaterOrEqual(warnSecond));
		assertFalse(warn.isGreaterOrEqual(fatal));
	}

	@Test
	public void toLevel() {
		allowedNames.forEach((key, value) -> value.forEach(val -> {
			final Optional<LogLevel> optional = LogLevel.toLevel(val);
			assertTrue(optional.isPresent());
			assertEquals(key, optional.get());
		}));
		disallowedNames.forEach(it -> assertFalse(LogLevel.toLevel(it).isPresent()));
	}

	@Test
	public void toLevelOrUnknown() {
		allowedNames.forEach((key, value) -> value.forEach(val -> assertEquals(key, LogLevel.toLevelOrUnknown(val))));
		allowedCodes.forEach((key, value) -> assertEquals(key, LogLevel.toLevelOrUnknown(value.toString())));
		disallowedNames.forEach(it -> assertEquals(LogLevel.UNKNOWN, LogLevel.toLevelOrUnknown(it)));
		disallowedCodes.stream().map(Object::toString).forEach(it -> assertEquals(LogLevel.UNKNOWN, LogLevel.toLevelOrUnknown(it)));
	}

	@Test
	public void toLevelInt() {
		allowedCodes.forEach((key, value) -> assertEquals(key, LogLevel.toLevel(value)));
	}

	@Test
	public void toLevelIntFail() {
		thrown.expect(IllegalArgumentException.class);
		Collections.shuffle(disallowedCodes);
		final Integer code = disallowedCodes.get(0);
		thrown.expectMessage("Level " + code + " is unknown.");
		LogLevel.toLevel(code);
	}
}