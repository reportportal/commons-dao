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

package com.epam.ta.reportportal.entity.enums.converter;

import com.epam.ta.reportportal.entity.enums.LogLevel;
import com.epam.ta.reportportal.exception.ReportPortalException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author <a href="mailto:ihar_kahadouski@epam.com">Ihar Kahadouski</a>
 */
class LogLevelConverterTest {

	private LogLevelConverter converter = new LogLevelConverter();
	private Map<LogLevel, Integer> allowedValues;

	@BeforeEach
	void setUp() {
		allowedValues = Arrays.stream(LogLevel.values()).collect(Collectors.toMap(it -> it, LogLevel::toInt));
	}

	@Test
	void convertToDatabaseColumn() {
		Arrays.stream(LogLevel.values()).forEach(it -> assertEquals(it.toInt(), (int) converter.convertToDatabaseColumn(it)));
	}

	@Test
	void convertToEntityAttribute() {
		allowedValues.forEach((key, value) -> assertEquals(key, converter.convertToEntityAttribute(value)));
	}

	@Test
	void convertToEntityAttributeFail() {
		assertThrows(ReportPortalException.class, () -> converter.convertToEntityAttribute(-100));
	}
}