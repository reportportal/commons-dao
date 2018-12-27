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

import com.epam.ta.reportportal.entity.project.ProjectRole;
import org.junit.Before;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

/**
 * @author <a href="mailto:ihar_kahadouski@epam.com">Ihar Kahadouski</a>
 */
public class ProjectRoleConverterTest extends AttributeConverterTest {

	@Before
	public void setUp() throws Exception {
		this.converter = new ProjectRoleConverter();
		allowedValues = Arrays.stream(ProjectRole.values())
				.collect(Collectors.toMap(it -> it, it -> Arrays.asList(it.name(), it.name().toUpperCase(), it.name().toLowerCase())));
	}

	@Override
	protected void convertToColumnTest() {
		Arrays.stream(ProjectRole.values()).forEach(it -> assertEquals(it.name(), converter.convertToDatabaseColumn(it)));
	}
}