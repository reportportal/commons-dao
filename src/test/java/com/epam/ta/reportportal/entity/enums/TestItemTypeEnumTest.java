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
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

/**
 * @author <a href="mailto:ihar_kahadouski@epam.com">Ihar Kahadouski</a>
 */
public class TestItemTypeEnumTest {

	private Map<TestItemTypeEnum, List<String>> allowed;
	private List<String> disallowed;

	@Before
	public void setUp() throws Exception {
		allowed = Arrays.stream(TestItemTypeEnum.values())
				.collect(Collectors.toMap(it -> it, it -> Arrays.asList(it.name(), it.name().toUpperCase(), it.name().toLowerCase())));
		disallowed = Arrays.asList("noSuchIssueGroup", "", " ", null);
	}

	@Test
	public void fromValue() {
		allowed.forEach((key, value) -> value.forEach(val -> {
			final Optional<TestItemTypeEnum> optional = TestItemTypeEnum.fromValue(val);
			assertTrue(optional.isPresent());
			assertEquals(key, optional.get());
		}));
		disallowed.forEach(it -> assertFalse(TestItemTypeEnum.fromValue(it).isPresent()));
	}

	@Test
	public void sameLevel() {
		final TestItemTypeEnum suite = TestItemTypeEnum.SUITE;
		final TestItemTypeEnum story = TestItemTypeEnum.STORY;
		final TestItemTypeEnum scenario = TestItemTypeEnum.SCENARIO;
		final TestItemTypeEnum step = TestItemTypeEnum.STEP;
		assertTrue(suite.sameLevel(story));
		assertFalse(suite.sameLevel(scenario));
		assertFalse(suite.sameLevel(step));

	}

	@Test
	public void higherThan() {
		final TestItemTypeEnum suite = TestItemTypeEnum.SUITE;
		final TestItemTypeEnum story = TestItemTypeEnum.STORY;
		final TestItemTypeEnum scenario = TestItemTypeEnum.SCENARIO;
		final TestItemTypeEnum step = TestItemTypeEnum.STEP;
		assertTrue(suite.higherThan(step));
		assertTrue(scenario.higherThan(step));
		assertFalse(suite.higherThan(story));
		assertFalse(step.higherThan(scenario));
		assertFalse(scenario.higherThan(story));
	}

	@Test
	public void lowerThan() {
		final TestItemTypeEnum suite = TestItemTypeEnum.SUITE;
		final TestItemTypeEnum story = TestItemTypeEnum.STORY;
		final TestItemTypeEnum scenario = TestItemTypeEnum.SCENARIO;
		final TestItemTypeEnum step = TestItemTypeEnum.STEP;
		assertTrue(step.lowerThan(scenario));
		assertTrue(scenario.lowerThan(story));
		assertFalse(story.lowerThan(suite));
		assertFalse(scenario.lowerThan(step));
	}
}