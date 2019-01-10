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
import com.epam.ta.reportportal.dao.WidgetRepository;
import com.epam.ta.reportportal.entity.widget.Widget;
import org.flywaydb.test.annotation.FlywayTest;
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
 * @author <a href="mailto:ivan_budayeu@epam.com">Ivan Budayeu</a>
 */
public class WidgetRepositoryTest extends BaseTest {

	private static final String FILL_SCRIPT_PATH = "/db/fill/shareable";

	@Autowired
	private WidgetRepository repository;

	@FlywayTest(locationsForMigrate = { FILL_SCRIPT_PATH }, invokeCleanDB = false)
	@BeforeClass
	public static void before() {
	}

	@Test
	public void findAllByProjectId() {
		final long superadminProjectId = 1L;

		final List<Widget> widgets = repository.findAllByProjectId(superadminProjectId);

		assertNotNull("Widgets not found", widgets);
		assertEquals("Unexpected widgets size", 5, widgets.size());
		widgets.forEach(it -> assertEquals("Widget has incorrect project id", superadminProjectId, (long) it.getProject().getId()));
	}

	@Test
	public void existsByNameAndOwnerAndProjectId() {
		assertTrue(repository.existsByNameAndOwnerAndProjectId("INVESTIGATED PERCENTAGE OF LAUNCHES", "superadmin", 1L));
		assertFalse(repository.existsByNameAndOwnerAndProjectId("not exist name", "default", 2L));
	}

	@Test
	public void getPermitted() {
		final String adminLogin = "superadmin";
		final Page<Widget> superadminPermitted = repository.getPermitted(ProjectFilter.of(buildDefaultFilter(), 1L),
				PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, CRITERIA_NAME)),
				adminLogin
		);
		assertEquals("Unexpected permitted widgets count", 4, superadminPermitted.getTotalElements());

		final String defaultLogin = "default";
		final Page<Widget> defaultPermitted = repository.getPermitted(ProjectFilter.of(buildDefaultFilter(), 2L),
				PageRequest.of(0, 3),
				defaultLogin
		);
		assertEquals("Unexpected permitted widgets count", 3, defaultPermitted.getTotalElements());

		final String jajaLogin = "jaja_user";
		final Page<Widget> jajaPermitted = repository.getPermitted(ProjectFilter.of(buildDefaultFilter(), 1L),
				PageRequest.of(0, 3),
				jajaLogin
		);
		assertEquals("Unexpected permitted widgets count", 4, jajaPermitted.getTotalElements());
	}

	@Test
	public void getOwn() {
		final String adminLogin = "superadmin";
		final Page<Widget> superadminOwn = repository.getOwn(ProjectFilter.of(buildDefaultFilter(), 1L),
				PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, CRITERIA_NAME)),
				adminLogin
		);
		assertEquals("Unexpected own widgets count", 3, superadminOwn.getTotalElements());
		superadminOwn.getContent().forEach(it -> assertEquals(adminLogin, it.getOwner()));

		final String defaultLogin = "default";
		final Page<Widget> defaultOwn = repository.getOwn(ProjectFilter.of(buildDefaultFilter(), 2L), PageRequest.of(0, 3), defaultLogin);
		assertEquals("Unexpected own widgets count", 3, defaultOwn.getTotalElements());
		defaultOwn.getContent().forEach(it -> assertEquals(defaultLogin, it.getOwner()));

		final String jajaLogin = "jaja_user";
		final Page<Widget> jajaOwn = repository.getOwn(ProjectFilter.of(buildDefaultFilter(), 1L), PageRequest.of(0, 3), jajaLogin);
		assertEquals("Unexpected own widgets count", 2, jajaOwn.getTotalElements());
		jajaOwn.getContent().forEach(it -> assertEquals(jajaLogin, it.getOwner()));
	}

	@Test
	public void getShared() {
		final String adminLogin = "superadmin";
		final Page<Widget> superadminShared = repository.getShared(ProjectFilter.of(buildDefaultFilter(), 1L),
				PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, CRITERIA_NAME)),
				adminLogin
		);
		assertEquals("Unexpected shared widgets count", 1, superadminShared.getTotalElements());
		superadminShared.getContent().forEach(it -> assertTrue(it.isShared()));

		final String defaultLogin = "default";
		final Page<Widget> defaultShared = repository.getShared(ProjectFilter.of(buildDefaultFilter(), 2L),
				PageRequest.of(0, 3),
				defaultLogin
		);
		assertEquals("Unexpected shared widgets count", 0, defaultShared.getTotalElements());
		defaultShared.getContent().forEach(it -> assertTrue(it.isShared()));

		final String jajaLogin = "jaja_user";
		final Page<Widget> jajaShared = repository.getShared(ProjectFilter.of(buildDefaultFilter(), 1L), PageRequest.of(0, 3), jajaLogin);
		assertEquals("Unexpected shared widgets count", 2, jajaShared.getTotalElements());
		jajaShared.getContent().forEach(it -> assertTrue(it.isShared()));
	}

	private Filter buildDefaultFilter() {
		return Filter.builder()
				.withTarget(Widget.class)
				.withCondition(new FilterCondition(Condition.LOWER_THAN, false, "100", CRITERIA_ID))
				.build();
	}
}