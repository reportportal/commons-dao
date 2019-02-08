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

package com.epam.ta.reportportal.entity.project.email;

import com.epam.ta.reportportal.commons.SendCase;
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
public class SendCaseTest {

	private Map<SendCase, List<String>> allowed;
	private List<String> disallowed;

	@Before
	public void setUp() throws Exception {
		allowed = Arrays.stream(SendCase.values())
				.collect(Collectors.toMap(it -> it,
						it -> Arrays.asList(it.getCaseString(),
								it.getCaseString().toUpperCase(),
								it.getCaseString().toLowerCase()
						)
				));
		disallowed = Arrays.asList("noSuchSendCase", "", " ", null);
	}

	@Test
	public void findByName() {
		allowed.forEach((key, value) -> value.forEach(val -> {
			final Optional<SendCase> optional = SendCase.findByName(val);
			assertTrue(optional.isPresent());
			assertEquals(key, optional.get());
		}));
		disallowed.forEach(it -> assertFalse(SendCase.findByName(it).isPresent()));
	}

	@Test
	public void isPresent() {
		allowed.entrySet().stream().flatMap(it -> it.getValue().stream()).forEach(it -> assertTrue(SendCase.isPresent(it)));
		disallowed.forEach(it -> assertFalse(SendCase.isPresent(it)));
	}
}