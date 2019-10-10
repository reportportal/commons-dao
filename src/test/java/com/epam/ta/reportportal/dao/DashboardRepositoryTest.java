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
import com.epam.ta.reportportal.entity.dashboard.Dashboard;
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
 * @author <a href="mailto:ihar_kahadouski@epam.com">Ihar Kahadouski</a>
 */
@Sql("/db/fill/shareable/shareable-fill.sql")
class DashboardRepositoryTest extends BaseTest {

	@Autowired
	private DashboardRepository repository;

	@Test
	public void shouldFindByIdAndProjectIdWhenExists() {
		Optional<Dashboard> dashboard = repository.findByIdAndProjectId(13L, 1L);

		assertTrue(dashboard.isPresent());
	}

	@Test
	public void shouldNotFindByIdAndProjectIdWhenIdNotExists() {
		Optional<Dashboard> dashboard = repository.findByIdAndProjectId(55L, 1L);

		assertFalse(dashboard.isPresent());
	}

	@Test
	public void shouldNotFindByIdAndProjectIdWhenProjectIdNotExists() {
		Optional<Dashboard> dashboard = repository.findByIdAndProjectId(5L, 11L);

		assertFalse(dashboard.isPresent());
	}

	@Test
	public void shouldNotFindByIdAndProjectIdWhenIdAndProjectIdNotExist() {
		Optional<Dashboard> dashboard = repository.findByIdAndProjectId(55L, 11L);

		assertFalse(dashboard.isPresent());
	}

	@Test
	void findAllByProjectId() {
		final long superadminProjectId = 1L;

		final List<Dashboard> dashboards = repository.findAllByProjectId(superadminProjectId);

		assertNotNull(dashboards, "Dashboards should not be null");
		assertEquals(4, dashboards.size(), "Unexpected dashboards size");
		dashboards.forEach(it -> assertEquals(superadminProjectId, (long) it.getProject().getId(), "Dashboard has incorrect project id"));
	}

	@Test
	void existsByNameAndOwnerAndProjectId() {
		assertTrue(repository.existsByNameAndOwnerAndProjectId("test admin dashboard", "superadmin", 1L));
		assertFalse(repository.existsByNameAndOwnerAndProjectId("not exist name", "default", 2L));
	}

	@Test
	void getPermitted() {
		final String adminLogin = "superadmin";
		final Page<Dashboard> superadminPermitted = repository.getPermitted(ProjectFilter.of(buildDefaultFilter(), 1L),
				PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, CRITERIA_NAME)),
				adminLogin
		);
		assertEquals(3, superadminPermitted.getTotalElements(), "Unexpected permitted dashboards count");

		final String defaultLogin = "default";
		final Page<Dashboard> defaultPermitted = repository.getPermitted(ProjectFilter.of(buildDefaultFilter(), 2L),
				PageRequest.of(0, 3),
				defaultLogin
		);
		assertEquals(2, defaultPermitted.getTotalElements(), "Unexpected permitted dashboards count");

		final String jajaLogin = "jaja_user";
		final Page<Dashboard> jajaPermitted = repository.getPermitted(ProjectFilter.of(buildDefaultFilter(), 1L),
				PageRequest.of(0, 3),
				jajaLogin
		);
		assertEquals(3, jajaPermitted.getTotalElements(), "Unexpected permitted dashboards count");
	}

	@Test
	void getOwn() {
		final String adminLogin = "superadmin";
		final Page<Dashboard> superadminOwn = repository.getOwn(ProjectFilter.of(buildDefaultFilter(), 1L),
				PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, CRITERIA_NAME)),
				adminLogin
		);
		assertEquals(2, superadminOwn.getTotalElements(), "Unexpected own dashboards count");
		superadminOwn.getContent().forEach(it -> assertEquals(adminLogin, it.getOwner()));

		final String defaultLogin = "default";
		final Page<Dashboard> defaultOwn = repository.getOwn(ProjectFilter.of(buildDefaultFilter(), 2L),
				PageRequest.of(0, 3),
				defaultLogin
		);
		assertEquals(2, defaultOwn.getTotalElements(), "Unexpected own dashboards count");
		defaultOwn.getContent().forEach(it -> assertEquals(defaultLogin, it.getOwner()));

		final String jajaLogin = "jaja_user";
		final Page<Dashboard> jajaOwn = repository.getOwn(ProjectFilter.of(buildDefaultFilter(), 1L), PageRequest.of(0, 3), jajaLogin);
		assertEquals(2, jajaOwn.getTotalElements(), "Unexpected own dashboards count");
		jajaOwn.getContent().forEach(it -> assertEquals(jajaLogin, it.getOwner()));
	}

	@Test
	void getShared() {
		final String adminLogin = "superadmin";
		final Page<Dashboard> superadminShared = repository.getShared(ProjectFilter.of(buildDefaultFilter(), 1L),
				PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, CRITERIA_NAME)),
				adminLogin
		);
		assertEquals(2, superadminShared.getTotalElements(), "Unexpected shared dashboards count");
		superadminShared.getContent().forEach(it -> assertTrue(it.isShared()));

		final String defaultLogin = "default";
		final Page<Dashboard> defaultShared = repository.getShared(ProjectFilter.of(buildDefaultFilter(), 2L),
				PageRequest.of(0, 3),
				defaultLogin
		);
		assertEquals(1, defaultShared.getTotalElements(), "Unexpected shared dashboards count");
		defaultShared.getContent().forEach(it -> assertTrue(it.isShared()));

		final String jajaLogin = "jaja_user";
		final Page<Dashboard> jajaShared = repository.getShared(ProjectFilter.of(buildDefaultFilter(), 1L),
				PageRequest.of(0, 3),
				jajaLogin
		);
		assertEquals(2, jajaShared.getTotalElements(), "Unexpected shared dashboards count");
		jajaShared.getContent().forEach(it -> assertTrue(it.isShared()));
	}

	@Test
	void shouldFindBySpecifiedNameAndProjectId() {
		assertTrue(repository.existsByNameAndProjectId("test admin dashboard", 1L));
	}

	private Filter buildDefaultFilter() {
		return Filter.builder()
				.withTarget(Dashboard.class)
				.withCondition(new FilterCondition(Condition.LOWER_THAN, false, "100", CRITERIA_ID))
				.build();
	}
}
