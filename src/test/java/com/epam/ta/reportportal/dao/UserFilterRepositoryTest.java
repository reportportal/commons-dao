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
import com.epam.ta.reportportal.commons.querygen.Condition;
import com.epam.ta.reportportal.commons.querygen.Filter;
import com.epam.ta.reportportal.commons.querygen.FilterCondition;
import com.epam.ta.reportportal.commons.querygen.ProjectFilter;
import com.epam.ta.reportportal.entity.filter.UserFilter;
import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static com.epam.ta.reportportal.commons.querygen.constant.GeneralCriteriaConstant.CRITERIA_ID;
import static com.epam.ta.reportportal.commons.querygen.constant.GeneralCriteriaConstant.CRITERIA_NAME;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Ivan Nikitsenka
 */
@Sql("/db/fill/shareable/shareable-fill.sql")
class UserFilterRepositoryTest extends BaseTest {

	@Autowired
	private UserFilterRepository userFilterRepository;

	@Test
	public void updateSharing() {
		userFilterRepository.updateSharingFlag(Lists.newArrayList(1L), true);
		final Optional<UserFilter> filter = userFilterRepository.findById(1L);
		assertTrue(filter.get().isShared());
	}

	@Test
	public void shouldFindByIdAndProjectIdWhenExists() {
		Optional<UserFilter> userFilter = userFilterRepository.findByIdAndProjectId(1L, 1L);

		assertTrue(userFilter.isPresent());
	}

	@Test
	public void shouldNotFindByIdAndProjectIdWhenIdNotExists() {
		Optional<UserFilter> userFilter = userFilterRepository.findByIdAndProjectId(55L, 1L);

		assertFalse(userFilter.isPresent());
	}

	@Test
	public void shouldNotFindByIdAndProjectIdWhenProjectIdNotExists() {
		Optional<UserFilter> userFilter = userFilterRepository.findByIdAndProjectId(5L, 11L);

		assertFalse(userFilter.isPresent());
	}

	@Test
	public void shouldNotFindByIdAndProjectIdWhenIdAndProjectIdNotExist() {
		Optional<UserFilter> userFilter = userFilterRepository.findByIdAndProjectId(55L, 11L);

		assertFalse(userFilter.isPresent());
	}

	@Test
	public void shouldFindByIdsAndProjectIdWhenExists() {
		List<UserFilter> userFilters = userFilterRepository.findAllByIdInAndProjectId(Lists.newArrayList(1L, 2L), 1L);

		assertNotNull(userFilters);
		assertEquals(2L, userFilters.size());
	}

	@Test
	public void shouldNotFindByIdsAndProjectIdWhenProjectIdNotExists() {
		List<UserFilter> userFilters = userFilterRepository.findAllByIdInAndProjectId(Lists.newArrayList(1L, 2L), 2L);

		assertNotNull(userFilters);
		assertTrue(userFilters.isEmpty());
	}

	@Test
	void getSharedFilters() {
		Page<UserFilter> superadminSharedFilters = userFilterRepository.getShared(ProjectFilter.of(buildDefaultFilter(), 2L),
				PageRequest.of(0, 3),
				"superadmin"
		);
		assertEquals(0, superadminSharedFilters.getTotalElements(), "Unexpected shared filters count");

		Page<UserFilter> result2 = userFilterRepository.getShared(ProjectFilter.of(buildDefaultFilter(), 2L),
				PageRequest.of(0, 3),
				"default"
		);
		assertEquals(1, result2.getTotalElements(), "Unexpected shared filters count");

		final Page<UserFilter> jajaSharedFilters = userFilterRepository.getShared(ProjectFilter.of(buildDefaultFilter(), 1L),
				PageRequest.of(0, 3),
				"jaja_user"
		);
		assertEquals(1, jajaSharedFilters.getTotalElements(), "Unexpected shared filters count");
	}

	@Test
	void getPermittedFilters() {
		Page<UserFilter> adminPermittedFilters = userFilterRepository.getPermitted(ProjectFilter.of(buildDefaultFilter(), 1L),
				PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, CRITERIA_NAME)),
				"superadmin"
		);
		assertEquals(2, adminPermittedFilters.getTotalElements(), "Unexpected shared filters count");

		Page<UserFilter> defaultPermittedFilters = userFilterRepository.getPermitted(ProjectFilter.of(buildDefaultFilter(), 2L),
				PageRequest.of(0, 3),
				"default"
		);
		assertEquals(2, defaultPermittedFilters.getTotalElements(), "Unexpected shared filters count");

		final Page<UserFilter> jajaPermittedFilters = userFilterRepository.getPermitted(ProjectFilter.of(buildDefaultFilter(), 1L),
				PageRequest.of(0, 3),
				"jaja_user"
		);
		assertEquals(1, jajaPermittedFilters.getTotalElements(), "Unexpected shared filters count");
	}

	@Test
	void getOwnFilters() {
		Page<UserFilter> superadminOwnFilters = userFilterRepository.getOwn(ProjectFilter.of(buildDefaultFilter(), 1L),
				PageRequest.of(0, 3),
				"superadmin"
		);
		assertEquals(2, superadminOwnFilters.getTotalElements());

		Page<UserFilter> defaultOwnFilters = userFilterRepository.getOwn(ProjectFilter.of(buildDefaultFilter(), 2L),
				PageRequest.of(0, 3),
				"default"
		);
		assertEquals(2, defaultOwnFilters.getTotalElements(), "Unexpected shared filters count");

		final Page<UserFilter> jajaOwnFilters = userFilterRepository.getOwn(ProjectFilter.of(buildDefaultFilter(), 1L),
				PageRequest.of(0, 3),
				"jaja_user"
		);
		assertEquals(0, jajaOwnFilters.getTotalElements(), "Unexpected shared filters count");

		final Page<UserFilter> jajaOwnFiltersOnForeingProject = userFilterRepository.getOwn(ProjectFilter.of(buildDefaultFilter(), 2L),
				PageRequest.of(0, 3),
				"jaja_user"
		);
		assertEquals(0, jajaOwnFiltersOnForeingProject.getTotalElements(), "Unexpected shared filters count");
	}

	@Test
	void existsByNameAndOwnerAndProjectIdTest() {
		assertTrue(userFilterRepository.existsByNameAndOwnerAndProjectId("Admin Filter", "superadmin", 1L));
		assertTrue(userFilterRepository.existsByNameAndOwnerAndProjectId("Default Shared Filter", "default", 2L));
		assertFalse(userFilterRepository.existsByNameAndOwnerAndProjectId("DEMO_FILTER", "yahoo", 1L));
		assertFalse(userFilterRepository.existsByNameAndOwnerAndProjectId("Admin Filter", "superadmin", 2L));
	}

	@Test
	void findAllByProjectId() {
		final Long projectId = 1L;
		final List<UserFilter> filters = userFilterRepository.findAllByProjectId(projectId);
		assertNotNull(filters, "Filters not found");
		assertTrue(!filters.isEmpty(), "Filters should not be empty");
		filters.forEach(it -> assertEquals(projectId, it.getProject().getId()));
	}

	private Filter buildDefaultFilter() {
		return Filter.builder()
				.withTarget(UserFilter.class)
				.withCondition(new FilterCondition(Condition.LOWER_THAN, false, "1000", CRITERIA_ID))
				.build();
	}
}