/*
 * Copyright 2018 EPAM Systems
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

package com.epam.ta.reportportal.dao.suite;

import com.epam.ta.reportportal.BaseTest;
import com.epam.ta.reportportal.dao.ItemAttributeRepository;
import com.epam.ta.reportportal.entity.ItemAttribute;
import org.flywaydb.test.annotation.FlywayTest;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

/**
 * @author <a href="mailto:ihar_kahadouski@epam.com">Ihar Kahadouski</a>
 */
public class ItemAttributeRepositoryTest extends BaseTest {

	private static final String FILL_SCRIPT_PATH = "db/fill/item-attributes";

	private static final String LAUNCH_KEY_NAME = "key1";
	private static final Long LAUNCH_ID = 1L;
	private static final String STEP_KEY = "step";

	private static final String PART_OF_ITEM_VALUE = "val";
	private static final String PART_OF_ITEM_KEY = "st";

	private static final Long PROJECT_ID = 1L;
	private static final String PART_OF_LAUNCH_KEY = "ke";

	@Autowired
	private ItemAttributeRepository repository;

	@FlywayTest(locationsForMigrate = { FILL_SCRIPT_PATH }, invokeCleanDB = false)
	@BeforeClass
	public static void setUp() {
	}

	@Test
	public void findByLaunchIdAndKeyAndSystem() {
		final Optional<ItemAttribute> optionalAttr = repository.findByLaunchIdAndKeyAndSystem(LAUNCH_ID, LAUNCH_KEY_NAME, false);
		assertTrue(optionalAttr.isPresent());
		assertEquals(LAUNCH_ID, optionalAttr.get().getId());
		assertEquals(LAUNCH_KEY_NAME, optionalAttr.get().getKey());
	}

	@Test
	public void findTestItemAttributeValues() {
		final List<String> values = repository.findTestItemAttributeValues(LAUNCH_ID, STEP_KEY, PART_OF_ITEM_VALUE, false);
		System.out.println();
		assertNotNull(values);
		assertTrue(!values.isEmpty());
		values.forEach(it -> assertTrue(it.contains(PART_OF_ITEM_VALUE)));
	}

	@Test
	public void findTestItemAttributeKeys() {
		final List<String> keys = repository.findTestItemAttributeKeys(LAUNCH_ID, PART_OF_ITEM_KEY, false);
		assertNotNull(keys);
		assertTrue(!keys.isEmpty());
		keys.forEach(it -> assertTrue(it.contains(PART_OF_ITEM_KEY)));
	}

	@Test
	public void findLaunchAttributeKeys() {
		final List<String> keys = repository.findLaunchAttributeKeys(PROJECT_ID, PART_OF_LAUNCH_KEY, false);
		assertNotNull(keys);
		assertTrue(!keys.isEmpty());
		keys.forEach(it -> assertTrue(it.contains(PART_OF_LAUNCH_KEY)));
	}

	@Test
	public void findLaunchAttributeValues() {
		final List<String> values = repository.findLaunchAttributeValues(PROJECT_ID, LAUNCH_KEY_NAME, PART_OF_ITEM_VALUE, false);
		assertNotNull(values);
		assertTrue(!values.isEmpty());
		values.forEach(it -> assertTrue(it.contains(PART_OF_ITEM_VALUE)));
	}
}
