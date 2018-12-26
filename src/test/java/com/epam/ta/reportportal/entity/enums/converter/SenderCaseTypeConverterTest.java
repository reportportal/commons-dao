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

package com.epam.ta.reportportal.entity.enums.converter;

import com.epam.ta.reportportal.commons.SendCase;
import org.junit.Before;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

/**
 * @author <a href="mailto:ihar_kahadouski@epam.com">Ihar Kahadouski</a>
 */
public class SenderCaseTypeConverterTest extends AttributeConverterTest {

	@Before
	public void setUp() throws Exception {
		this.converter = new SendCaseConverter();
		allowedValues = Arrays.stream(SendCase.values())
				.collect(Collectors.toMap(it -> it,
						it -> Arrays.asList(it.getCaseString(),
								it.getCaseString().toUpperCase(),
								it.getCaseString().toLowerCase()
						)
				));
	}

	@Override
	protected void convertToColumnTest() {
		Arrays.stream(SendCase.values()).forEach(it -> assertEquals(it.getCaseString(), converter.convertToDatabaseColumn(it)));
	}
}