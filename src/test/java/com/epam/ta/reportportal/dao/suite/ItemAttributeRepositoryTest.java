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

	private static final String FILL_SCRIPT_PATH = "db/fill/item";

	@Autowired
	private ItemAttributeRepository repository;

	@FlywayTest(locationsForMigrate = { FILL_SCRIPT_PATH }, invokeCleanDB = false)
	@BeforeClass
	public static void setUp() {
	}

	@Test
	public void findByLaunchIdAndKeyAndSystem() {
		final String launchKeyName = "key1";
		final Long launchId = 1L;

		final Optional<ItemAttribute> optionalAttr = repository.findByLaunchIdAndKeyAndSystem(launchId, launchKeyName, false);
		assertTrue("Should be present", optionalAttr.isPresent());
		assertEquals("Unexpected id", launchId, optionalAttr.get().getId());
		assertEquals("Unexpected key", launchKeyName, optionalAttr.get().getKey());
	}

	@Test
	public void findTestItemAttributeValues() {
		final Long launchId = 1L;
		final String stepKey = "step";
		final String partOfItemValue = "val";

		final List<String> values = repository.findTestItemAttributeValues(launchId, stepKey, partOfItemValue, false);
		System.out.println();
		assertNotNull("Should not be null", values);
		assertTrue("Should not be empty", !values.isEmpty());
		values.forEach(it -> assertTrue("Value not matches", it.contains(partOfItemValue)));
	}

	@Test
	public void findTestItemAttributeKeys() {
		final Long launchId = 1L;
		final String partOfItemKey = "st";

		final List<String> keys = repository.findTestItemAttributeKeys(launchId, partOfItemKey, false);
		assertNotNull("Should not be null", keys);
		assertTrue("Should not be empty", !keys.isEmpty());
		keys.forEach(it -> assertTrue("Key not matches", it.contains(partOfItemKey)));
	}

	@Test
	public void findLaunchAttributeKeys() {
		final Long projectId = 1L;
		final String partOfLaunchKey = "ke";

		final List<String> keys = repository.findLaunchAttributeKeys(projectId, partOfLaunchKey, false);
		assertNotNull("Should not be null", keys);
		assertTrue("Should not be empty", !keys.isEmpty());
		keys.forEach(it -> assertTrue("Key not matches", it.contains(partOfLaunchKey)));
	}

	@Test
	public void findLaunchAttributeValues() {
		final Long projectId = 1L;
		final String launchKeyName = "key1";
		final String partOfItemValue = "val";

		final List<String> values = repository.findLaunchAttributeValues(projectId, launchKeyName, partOfItemValue, false);
		assertNotNull("Should not be null", values);
		assertTrue("Should not be empty", !values.isEmpty());
		values.forEach(it -> assertTrue("Value not matches", it.contains(partOfItemValue)));
	}
}
