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

package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.BaseTest;
import com.epam.ta.reportportal.commons.querygen.Filter;
import com.epam.ta.reportportal.commons.querygen.FilterCondition;
import com.epam.ta.reportportal.entity.ItemAttribute;
import com.epam.ta.reportportal.entity.launch.Launch;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static com.epam.ta.reportportal.commons.querygen.constant.GeneralCriteriaConstant.CRITERIA_PROJECT_ID;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author <a href="mailto:ihar_kahadouski@epam.com">Ihar Kahadouski</a>
 */
@Sql("/db/fill/item/items-fill.sql")
class ItemAttributeRepositoryTest extends BaseTest {

	@Autowired
	private ItemAttributeRepository repository;

	@Test
	void findAttributesByProjectId() {

		Filter filter = Filter.builder()
				.withTarget(Launch.class)
				.withCondition(FilterCondition.builder().eq(CRITERIA_PROJECT_ID, "1").build())
				.build();
		List<String> keys = repository.findAllKeysByLaunchFilter(filter, PageRequest.of(0, 600), false, "step", false);

		assertFalse(keys.isEmpty());
	}

	@Test
	void findByLaunchIdAndKeyAndSystem() {
		final String launchKeyName = "key1";
		final Long launchId = 1L;

		final Optional<ItemAttribute> optionalAttr = repository.findByLaunchIdAndKeyAndSystem(launchId, launchKeyName, false);
		assertTrue(optionalAttr.isPresent(), "Should be present");
		assertEquals(launchId, optionalAttr.get().getLaunch().getId(), "Unexpected launch id");
		assertEquals(launchKeyName, optionalAttr.get().getKey(), "Unexpected key");
	}

	@Test
	void findTestItemAttributeValues() {
		final Long launchId = 1L;
		final String stepKey = "step";
		final String partOfItemValue = "val";

		final List<String> values = repository.findTestItemAttributeValues(launchId, stepKey, partOfItemValue, false);
		System.out.println();
		assertNotNull(values, "Should not be null");
		assertTrue(!values.isEmpty(), "Should not be empty");
		values.forEach(it -> assertTrue(it.contains(partOfItemValue), "Value not matches"));
	}

	@Test
	void findTestItemKeysByProjectId() {
		final Long projectId = 1L;
		final String launchName = "name 1";
		final String partOfItemKey = "st";

		final List<String> keys = repository.findTestItemKeysByProjectIdAndLaunchName(projectId, launchName, partOfItemKey, false);
		assertNotNull(keys, "Should not be null");
		assertTrue(!keys.isEmpty(), "Should not be empty");
		keys.forEach(it -> assertTrue(it.contains(partOfItemKey), "Key not matches"));
	}

	@Test
	void findTestItemValuesByProjectId() {
		final Long projectId = 1L;
		final String launchName = "name 1";
		final String stepKey = "step";
		final String partOfItemValue = "val";

		final List<String> values = repository.findTestItemValuesByProjectIdAndLaunchName(projectId, launchName, stepKey, partOfItemValue, false);
		assertNotNull(values, "Should not be null");
		assertTrue(!values.isEmpty(), "Should not be empty");
		values.forEach(it -> assertTrue(it.contains(partOfItemValue), "Value not matches"));
	}

	@Test
	void findTestItemAttributeKeys() {
		final Long launchId = 1L;
		final String partOfItemKey = "st";

		final List<String> keys = repository.findTestItemAttributeKeys(launchId, partOfItemKey, false);
		assertNotNull(keys, "Should not be null");
		assertTrue(!keys.isEmpty(), "Should not be empty");
		keys.forEach(it -> assertTrue(it.contains(partOfItemKey), "Key not matches"));
	}

	@Test
	void findLaunchAttributeKeys() {
		final Long projectId = 1L;
		final String partOfLaunchKey = "ke";

		final List<String> keys = repository.findLaunchAttributeKeys(projectId, partOfLaunchKey, false);
		assertNotNull(keys, "Should not be null");
		assertTrue(!keys.isEmpty(), "Should not be empty");
		keys.forEach(it -> assertTrue(it.contains(partOfLaunchKey), "Key not matches"));
	}

	@Test
	void findLaunchAttributeValues() {
		final Long projectId = 1L;
		final String launchKeyName = "key1";
		final String partOfItemValue = "val";

		final List<String> values = repository.findLaunchAttributeValues(projectId, launchKeyName, partOfItemValue, false);
		assertNotNull(values, "Should not be null");
		assertTrue(!values.isEmpty(), "Should not be empty");
		values.forEach(it -> assertTrue(it.contains(partOfItemValue), "Value not matches"));
	}

	@Test
	void saveItemAttributeByItemId() {
		int result = repository.saveByItemId(1L, "new key", "new value", false);

		Assertions.assertEquals(1, result);

		List<String> attributeKeys = repository.findTestItemAttributeKeys(1L, "new", false);

		Assertions.assertNotNull(attributeKeys);
		Assertions.assertFalse(attributeKeys.isEmpty());
		Assertions.assertTrue(attributeKeys.stream().anyMatch(k -> k.equals("new key")));
	}

	@Test
	void saveItemAttributeByLaunchId() {
		int result = repository.saveByLaunchId(1L, "new", "new value", false);

		Assertions.assertEquals(1, result);

		final Optional<ItemAttribute> attribute = repository.findByLaunchIdAndKeyAndSystem(1L, "new", false);

		Assertions.assertTrue(attribute.isPresent());
		Assertions.assertEquals("new", attribute.get().getKey());
		Assertions.assertEquals("new value", attribute.get().getValue());
		Assertions.assertEquals(false, attribute.get().isSystem());
	}
}
