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

package com.epam.ta.reportportal.entity;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.epam.ta.reportportal.entity.enums.EnumTestHelper.permute;
import static org.junit.Assert.*;

/**
 * @author <a href="mailto:ihar_kahadouski@epam.com">Ihar Kahadouski</a>
 */
public class ActivityEntityTypeTest {

	private Map<Activity.ActivityEntityType, List<String>> allowed;
	private List<String> disallowed;

	@Before
	public void setUp() throws Exception {
		allowed = Arrays.stream(Activity.ActivityEntityType.values()).collect(Collectors.toMap(it -> it, it -> permute(it.getValue())));
		disallowed = Arrays.asList("noSuchMode", "", " ", null);
	}

	@Test
	public void fromStringTest() {
		allowed.forEach((key, value) -> value.forEach(val -> {
			final Optional<Activity.ActivityEntityType> optional = Activity.ActivityEntityType.fromString(val);
			assertTrue(optional.isPresent());
			assertEquals(key, optional.get());
		}));
		disallowed.forEach(it -> assertFalse(Activity.ActivityEntityType.fromString(it).isPresent()));
	}
}