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
import com.epam.ta.reportportal.dao.IssueTypeRepository;
import com.epam.ta.reportportal.entity.enums.TestItemIssueGroup;
import com.epam.ta.reportportal.entity.item.issue.IssueType;
import org.flywaydb.test.annotation.FlywayTest;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

/**
 * @author <a href="mailto:ihar_kahadouski@epam.com">Ihar Kahadouski</a>
 */
public class IssueTypeRepositoryTest extends BaseTest {

	private static final String FILL_SCRIPT_PATH = "db/fill/issue-type";

	private static final Long CUSTOM_ID = 100L;
	private static final String CUSTOM_LOCATOR = "pb_ajf7d5d";
	private static final String CUSTOM_LONG_NAME = "Custom";
	private static final String CUSTOM_SHORT_NAME = "CS";
	private static final String CUSTOM_HEX_COLOR = "#a3847e";
	private static final int DEFAULT_ISSUE_TYPES_COUNT = TestItemIssueGroup.values().length - 1;

	@Autowired
	private IssueTypeRepository repository;

	@FlywayTest(locationsForMigrate = { FILL_SCRIPT_PATH }, invokeCleanDB = false)
	@BeforeClass
	public static void setUp() throws Exception {
	}

	@Test
	public void findByLocator() {
		final IssueType customIssueType = repository.findByLocator(CUSTOM_LOCATOR);
		assertNotNull(customIssueType);
		assertIssueType(customIssueType);
	}

	@Test
	public void findById() {
		final Optional<IssueType> issueTypeOptional = repository.findById(CUSTOM_ID);
		assertTrue(issueTypeOptional.isPresent());
		assertIssueType(issueTypeOptional.get());
	}

	@Test
	public void defaultIssueTypes() {
		final List<IssueType> defaultIssueTypes = repository.getDefaultIssueTypes();
		assertEquals(DEFAULT_ISSUE_TYPES_COUNT, defaultIssueTypes.size());
		defaultIssueTypes.forEach(Assert::assertNotNull);
	}

	private static void assertIssueType(IssueType customIssueType) {
		assertEquals(CUSTOM_LOCATOR, customIssueType.getLocator());
		assertEquals(CUSTOM_LONG_NAME, customIssueType.getLongName());
		assertEquals(CUSTOM_SHORT_NAME, customIssueType.getShortName());
		assertEquals(CUSTOM_HEX_COLOR, customIssueType.getHexColor());
		assertEquals(TestItemIssueGroup.PRODUCT_BUG, customIssueType.getIssueGroup().getTestItemIssueGroup());
	}
}
