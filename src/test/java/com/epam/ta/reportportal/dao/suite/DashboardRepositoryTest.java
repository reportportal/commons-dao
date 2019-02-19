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
import com.epam.ta.reportportal.dao.DashboardRepository;
import com.epam.ta.reportportal.entity.dashboard.Dashboard;
import org.flywaydb.test.annotation.FlywayTest;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static com.epam.ta.reportportal.commons.querygen.constant.GeneralCriteriaConstant.CRITERIA_ID;
import static com.epam.ta.reportportal.commons.querygen.constant.GeneralCriteriaConstant.CRITERIA_NAME;
import static org.junit.Assert.*;

/**
 * @author <a href="mailto:ihar_kahadouski@epam.com">Ihar Kahadouski</a>
 */
public class DashboardRepositoryTest extends BaseTest {

	private static final String FILL_SCRIPT_PATH = "/db/fill/shareable";

	@Autowired
	private DashboardRepository repository;

	@FlywayTest(locationsForMigrate = { FILL_SCRIPT_PATH }, invokeCleanDB = false)
	@BeforeClass
	public static void before() {
	}

	@Test
	public void shouldFindByIdAndProjectIdWhenExists() {
		Optional<Dashboard> dashboard = repository.findByIdAndProjectId(13L, 1L);

		Assert.assertTrue(dashboard.isPresent());
	}

	@Test
	public void shouldNotFindByIdAndProjectIdWhenIdNotExists() {
		Optional<Dashboard> dashboard = repository.findByIdAndProjectId(55L, 1L);

		Assert.assertFalse(dashboard.isPresent());
	}

	@Test
	public void shouldNotFindByIdAndProjectIdWhenProjectIdNotExists() {
		Optional<Dashboard> dashboard = repository.findByIdAndProjectId(5L, 11L);

		Assert.assertFalse(dashboard.isPresent());
	}

	@Test
	public void shouldNotFindByIdAndProjectIdWhenIdAndProjectIdNotExist() {
		Optional<Dashboard> dashboard = repository.findByIdAndProjectId(55L, 11L);

		Assert.assertFalse(dashboard.isPresent());
	}

	@Test
	public void findAllByProjectId() {
		final long superadminProjectId = 1L;

		final List<Dashboard> dashboards = repository.findAllByProjectId(superadminProjectId);

		assertNotNull("Dashboards should not be null", dashboards);
		assertEquals("Unexpected dashboards size", 4, dashboards.size());
		dashboards.forEach(it -> assertEquals("Dashboard has incorrect project id", superadminProjectId, (long) it.getProject().getId()));
	}

	@Test
	public void existsByNameAndOwnerAndProjectId() {
		assertTrue(repository.existsByNameAndOwnerAndProjectId("test admin dashboard", "superadmin", 1L));
		assertFalse(repository.existsByNameAndOwnerAndProjectId("not exist name", "default", 2L));
	}

	@Test
	public void getPermitted() {
		final String adminLogin = "superadmin";
		final Page<Dashboard> superadminPermitted = repository.getPermitted(ProjectFilter.of(buildDefaultFilter(), 1L),
				PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, CRITERIA_NAME)),
				adminLogin
		);
		assertEquals("Unexpected permitted dashboards count", 3, superadminPermitted.getTotalElements());

		final String defaultLogin = "default";
		final Page<Dashboard> defaultPermitted = repository.getPermitted(ProjectFilter.of(buildDefaultFilter(), 2L),
				PageRequest.of(0, 3),
				defaultLogin
		);
		assertEquals("Unexpected permitted dashboards count", 2, defaultPermitted.getTotalElements());

		final String jajaLogin = "jaja_user";
		final Page<Dashboard> jajaPermitted = repository.getPermitted(ProjectFilter.of(buildDefaultFilter(), 1L),
				PageRequest.of(0, 3),
				jajaLogin
		);
		assertEquals("Unexpected permitted dashboards count", 3, jajaPermitted.getTotalElements());
	}

	@Test
	public void getOwn() {
		final String adminLogin = "superadmin";
		final Page<Dashboard> superadminOwn = repository.getOwn(ProjectFilter.of(buildDefaultFilter(), 1L),
				PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, CRITERIA_NAME)),
				adminLogin
		);
		assertEquals("Unexpected own dashboards count", 2, superadminOwn.getTotalElements());
		superadminOwn.getContent().forEach(it -> assertEquals(adminLogin, it.getOwner()));

		final String defaultLogin = "default";
		final Page<Dashboard> defaultOwn = repository.getOwn(ProjectFilter.of(buildDefaultFilter(), 2L),
				PageRequest.of(0, 3),
				defaultLogin
		);
		assertEquals("Unexpected own dashboards count", 2, defaultOwn.getTotalElements());
		defaultOwn.getContent().forEach(it -> assertEquals(defaultLogin, it.getOwner()));

		final String jajaLogin = "jaja_user";
		final Page<Dashboard> jajaOwn = repository.getOwn(ProjectFilter.of(buildDefaultFilter(), 1L), PageRequest.of(0, 3), jajaLogin);
		assertEquals("Unexpected own dashboards count", 2, jajaOwn.getTotalElements());
		jajaOwn.getContent().forEach(it -> assertEquals(jajaLogin, it.getOwner()));
	}

	@Test
	public void getShared() {
		final String adminLogin = "superadmin";
		final Page<Dashboard> superadminShared = repository.getShared(ProjectFilter.of(buildDefaultFilter(), 1L),
				PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, CRITERIA_NAME)),
				adminLogin
		);
		assertEquals("Unexpected shared dashboards count", 1, superadminShared.getTotalElements());
		superadminShared.getContent().forEach(it -> assertTrue(it.isShared()));

		final String defaultLogin = "default";
		final Page<Dashboard> defaultShared = repository.getShared(ProjectFilter.of(buildDefaultFilter(), 2L),
				PageRequest.of(0, 3),
				defaultLogin
		);
		assertEquals("Unexpected shared dashboards count", 0, defaultShared.getTotalElements());
		defaultShared.getContent().forEach(it -> assertTrue(it.isShared()));

		final String jajaLogin = "jaja_user";
		final Page<Dashboard> jajaShared = repository.getShared(ProjectFilter.of(buildDefaultFilter(), 1L),
				PageRequest.of(0, 3),
				jajaLogin
		);
		assertEquals("Unexpected shared dashboards count", 1, jajaShared.getTotalElements());
		jajaShared.getContent().forEach(it -> assertTrue(it.isShared()));
	}

	private Filter buildDefaultFilter() {
		return Filter.builder()
				.withTarget(Dashboard.class)
				.withCondition(new FilterCondition(Condition.LOWER_THAN, false, "100", CRITERIA_ID))
				.build();
	}
}
