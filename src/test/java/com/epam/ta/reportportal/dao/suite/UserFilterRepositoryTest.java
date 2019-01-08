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
import com.epam.ta.reportportal.commons.querygen.Condition;
import com.epam.ta.reportportal.commons.querygen.Filter;
import com.epam.ta.reportportal.commons.querygen.FilterCondition;
import com.epam.ta.reportportal.commons.querygen.ProjectFilter;
import com.epam.ta.reportportal.dao.UserFilterRepository;
import com.epam.ta.reportportal.entity.filter.UserFilter;
import org.flywaydb.test.annotation.FlywayTest;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

import static com.epam.ta.reportportal.commons.querygen.constant.GeneralCriteriaConstant.CRITERIA_ID;
import static com.epam.ta.reportportal.commons.querygen.constant.GeneralCriteriaConstant.CRITERIA_NAME;
import static org.junit.Assert.*;

/**
 * @author Ivan Nikitsenka
 */
public class UserFilterRepositoryTest extends BaseTest {

	private static final String FILL_SCRIPT_PATH = "db/fill/filter";

	@Autowired
	private UserFilterRepository userFilterRepository;

	@FlywayTest(locationsForMigrate = { FILL_SCRIPT_PATH }, invokeCleanDB = false)
	@BeforeClass
	public static void before() {
	}

	@Test
	public void getSharedFilters() {
		Page<UserFilter> superadminSharedFilters = userFilterRepository.getShared(
				ProjectFilter.of(buildDefaultFilter(), 2L),
				PageRequest.of(0, 3),
				"superadmin"
		);
		assertEquals("Unexpected shared filters count", 0, superadminSharedFilters.getTotalElements());

		Page<UserFilter> result2 = userFilterRepository.getShared(ProjectFilter.of(buildDefaultFilter(), 2L),
				PageRequest.of(0, 3),
				"default"
		);
		assertEquals("Unexpected shared filters count", 0, result2.getTotalElements());

		final Page<UserFilter> jajaSharedFilters = userFilterRepository.getShared(ProjectFilter.of(buildDefaultFilter(), 1L),
				PageRequest.of(0, 3),
				"jaja_user"
		);
		assertEquals("Unexpected shared filters count", 1, jajaSharedFilters.getTotalElements());
	}

	@Test
	public void getPermittedFilters() {
		Page<UserFilter> adminPermittedFilters = userFilterRepository.getPermitted(ProjectFilter.of(buildDefaultFilter(), 1L),
				PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, CRITERIA_NAME)),
				"superadmin"
		);
		assertEquals("Unexpected shared filters count", 2, adminPermittedFilters.getTotalElements());

		Page<UserFilter> defaultPermittedFilters = userFilterRepository.getPermitted(
				ProjectFilter.of(buildDefaultFilter(), 2L),
				PageRequest.of(0, 3),
				"default"
		);
		assertEquals("Unexpected shared filters count", 2, defaultPermittedFilters.getTotalElements());

		final Page<UserFilter> jajaPermittedFilters = userFilterRepository.getPermitted(ProjectFilter.of(buildDefaultFilter(), 1L),
				PageRequest.of(0, 3),
				"jaja_user"
		);
		assertEquals("Unexpected shared filters count", 1, jajaPermittedFilters.getTotalElements());
	}

	@Test
	public void getOwnFilters() {
		Page<UserFilter> superadminOwnFilters = userFilterRepository.getOwn(
				ProjectFilter.of(buildDefaultFilter(), 1L),
				PageRequest.of(0, 3),
				"superadmin"
		);
		assertEquals(2, superadminOwnFilters.getTotalElements());

		Page<UserFilter> defaultOwnFilters = userFilterRepository.getOwn(ProjectFilter.of(buildDefaultFilter(), 2L),
				PageRequest.of(0, 3),
				"default"
		);
		assertEquals("Unexpected shared filters count", 2, defaultOwnFilters.getTotalElements());

		final Page<UserFilter> jajaOwnFilters = userFilterRepository.getOwn(ProjectFilter.of(buildDefaultFilter(), 1L),
				PageRequest.of(0, 3),
				"jaja_user"
		);
		assertEquals("Unexpected shared filters count", 0, jajaOwnFilters.getTotalElements());

		final Page<UserFilter> jajaOwnFiltersOnForeingProject = userFilterRepository.getOwn(ProjectFilter.of(buildDefaultFilter(), 2L),
				PageRequest.of(0, 3),
				"jaja_user"
		);
		assertEquals("Unexpected shared filters count", 0, jajaOwnFiltersOnForeingProject.getTotalElements());
	}

	@Test
	public void existsByNameAndOwnerAndProjectIdTest() {
		Assert.assertTrue(userFilterRepository.existsByNameAndOwnerAndProjectId("Admin Filter", "superadmin", 1L));
		Assert.assertTrue(userFilterRepository.existsByNameAndOwnerAndProjectId("Default shared Filter", "default", 2L));
		Assert.assertFalse(userFilterRepository.existsByNameAndOwnerAndProjectId("DEMO_FILTER", "yahoo", 1L));
		Assert.assertFalse(userFilterRepository.existsByNameAndOwnerAndProjectId("Admin Filter", "superadmin", 2L));
	}

	@Test
	public void findAllByProjectId() {
		final Long projectId = 1L;
		final List<UserFilter> filters = userFilterRepository.findAllByProjectId(projectId);
		assertNotNull("Filters not found", filters);
		assertTrue("Filters should not be empty", !filters.isEmpty());
		filters.forEach(it -> assertEquals(projectId, it.getProject().getId()));
	}

	private Filter buildDefaultFilter() {
		return Filter.builder()
				.withTarget(UserFilter.class)
				.withCondition(new FilterCondition(Condition.LOWER_THAN, false, "1000", CRITERIA_ID))
				.build();
	}
}