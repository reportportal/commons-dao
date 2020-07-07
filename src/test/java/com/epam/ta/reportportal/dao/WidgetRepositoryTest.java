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
import com.epam.ta.reportportal.entity.widget.Widget;
import com.epam.ta.reportportal.entity.widget.WidgetType;
import com.google.common.collect.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.jdbc.Sql;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.epam.ta.reportportal.commons.querygen.constant.GeneralCriteriaConstant.CRITERIA_ID;
import static com.epam.ta.reportportal.commons.querygen.constant.GeneralCriteriaConstant.CRITERIA_NAME;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Uses script from
 *
 * @author <a href="mailto:ivan_budayeu@epam.com">Ivan Budayeu</a>
 */
@Sql("/db/fill/shareable/shareable-fill.sql")
public class WidgetRepositoryTest extends BaseTest {

	@Autowired
	private WidgetRepository repository;

	@Test
	void findAllByProjectId() {
		final long superadminProjectId = 1L;

		final List<Widget> widgets = repository.findAllByProjectId(superadminProjectId);

		assertNotNull(widgets, "Widgets not found");
		assertEquals(5, widgets.size(), "Unexpected widgets size");
		widgets.forEach(it -> assertEquals(superadminProjectId, (long) it.getProject().getId(), "Widget has incorrect project id"));
	}

	@Test
	public void shouldFindByIdAndProjectIdWhenExists() {
		Optional<Widget> widget = repository.findByIdAndProjectId(5L, 1L);

		assertTrue(widget.isPresent());
	}

	@Test
	public void shouldNotFindByIdAndProjectIdWhenIdNotExists() {
		Optional<Widget> widget = repository.findByIdAndProjectId(55L, 1L);

		assertFalse(widget.isPresent());
	}

	@Test
	public void shouldNotFindByIdAndProjectIdWhenProjectIdNotExists() {
		Optional<Widget> widget = repository.findByIdAndProjectId(5L, 11L);

		assertFalse(widget.isPresent());
	}

	@Test
	public void shouldNotFindByIdAndProjectIdWhenIdAndProjectIdNotExist() {
		Optional<Widget> widget = repository.findByIdAndProjectId(55L, 11L);

		assertFalse(widget.isPresent());
	}

	@Test
	void existsByNameAndOwnerAndProjectId() {
		assertTrue(repository.existsByNameAndOwnerAndProjectId("INVESTIGATED PERCENTAGE OF LAUNCHES", "superadmin", 1L));
		assertFalse(repository.existsByNameAndOwnerAndProjectId("not exist name", "default", 2L));
	}

	@Test
	void getPermitted() {
		final String adminLogin = "superadmin";
		final Page<Widget> superadminPermitted = repository.getPermitted(ProjectFilter.of(buildDefaultFilter(), 1L),
				PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, CRITERIA_NAME)),
				adminLogin
		);
		assertEquals(4, superadminPermitted.getTotalElements(), "Unexpected permitted widgets count");

		final String defaultLogin = "default";
		final Page<Widget> defaultPermitted = repository.getPermitted(ProjectFilter.of(buildDefaultFilter(), 2L),
				PageRequest.of(0, 3),
				defaultLogin
		);
		assertEquals(3, defaultPermitted.getTotalElements(), "Unexpected permitted widgets count");

		final String jajaLogin = "jaja_user";
		final Page<Widget> jajaPermitted = repository.getPermitted(ProjectFilter.of(buildDefaultFilter(), 1L),
				PageRequest.of(0, 3),
				jajaLogin
		);
		assertEquals(4, jajaPermitted.getTotalElements(), "Unexpected permitted widgets count");
	}

	@Test
	void getOwn() {
		final String adminLogin = "superadmin";
		final Page<Widget> superadminOwn = repository.getOwn(ProjectFilter.of(buildDefaultFilter(), 1L),
				PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, CRITERIA_NAME)),
				adminLogin
		);
		assertEquals(3, superadminOwn.getTotalElements(), "Unexpected own widgets count");
		superadminOwn.getContent().forEach(it -> assertEquals(adminLogin, it.getOwner()));

		final String defaultLogin = "default";
		final Page<Widget> defaultOwn = repository.getOwn(ProjectFilter.of(buildDefaultFilter(), 2L), PageRequest.of(0, 3), defaultLogin);
		assertEquals(3, defaultOwn.getTotalElements(), "Unexpected own widgets count");
		defaultOwn.getContent().forEach(it -> assertEquals(defaultLogin, it.getOwner()));

		final String jajaLogin = "jaja_user";
		final Page<Widget> jajaOwn = repository.getOwn(ProjectFilter.of(buildDefaultFilter(), 1L), PageRequest.of(0, 3), jajaLogin);
		assertEquals(2, jajaOwn.getTotalElements(), "Unexpected own widgets count");
		jajaOwn.getContent().forEach(it -> assertEquals(jajaLogin, it.getOwner()));
	}

	@Test
	void getShared() {
		final String adminLogin = "superadmin";
		final Page<Widget> superadminShared = repository.getShared(ProjectFilter.of(buildDefaultFilter(), 1L),
				PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, CRITERIA_NAME)),
				adminLogin
		);
		assertEquals(3, superadminShared.getTotalElements(), "Unexpected shared widgets count");
		superadminShared.getContent().forEach(it -> assertTrue(it.isShared()));

		final String defaultLogin = "default";
		final Page<Widget> defaultShared = repository.getShared(ProjectFilter.of(buildDefaultFilter(), 2L),
				PageRequest.of(0, 3),
				defaultLogin
		);
		assertEquals(1, defaultShared.getTotalElements(), "Unexpected shared widgets count");
		defaultShared.getContent().forEach(it -> assertTrue(it.isShared()));

		final String jajaLogin = "jaja_user";
		final Page<Widget> jajaShared = repository.getShared(ProjectFilter.of(buildDefaultFilter(), 1L), PageRequest.of(0, 3), jajaLogin);
		assertEquals(3, jajaShared.getTotalElements(), "Unexpected shared widgets count");
		jajaShared.getContent().forEach(it -> assertTrue(it.isShared()));
	}

	@Test
	void deleteRelationByFilterIdAndNotOwnerTest() {

		int removedCount = repository.deleteRelationByFilterIdAndNotOwner(2L, "superadmin");

		Assertions.assertEquals(1, removedCount);
	}

	@Test
	void findAllByProjectIdAndWidgetTypeInTest() {
		List<Widget> widgets = repository.findAllByProjectIdAndWidgetTypeIn(1L,
				Lists.newArrayList(WidgetType.LAUNCH_STATISTICS, WidgetType.CASES_TREND)
						.stream()
						.map(WidgetType::getType)
						.collect(Collectors.toList())
		);

		assertFalse(widgets.isEmpty());
		assertEquals(2, widgets.size());

		List<Widget> moreWidgets = repository.findAllByProjectIdAndWidgetTypeIn(1L,
				Collections.singletonList(WidgetType.CASES_TREND.getType())
		);

		assertFalse(moreWidgets.isEmpty());
		assertEquals(1, moreWidgets.size());
	}

	@Test
	void findAllByOwnerAndWidgetTypeInTest() {
		List<Widget> widgets = repository.findAllByOwnerAndWidgetTypeIn("superadmin",
				Lists.newArrayList(WidgetType.LAUNCH_STATISTICS, WidgetType.ACTIVITY)
						.stream()
						.map(WidgetType::getType)
						.collect(Collectors.toList())
		);

		assertFalse(widgets.isEmpty());
		assertEquals(2, widgets.size());

		List<Widget> moreWidgets = repository.findAllByOwnerAndWidgetTypeIn("superadmin",
				Collections.singletonList(WidgetType.LAUNCH_STATISTICS.getType())
		);

		assertFalse(moreWidgets.isEmpty());
		assertEquals(1, moreWidgets.size());
	}

	@Test
	void findAllByWidgetTypeInAndContentFieldsContainsTest() {
		List<Widget> widgets = repository.findAllByProjectIdAndWidgetTypeInAndContentFieldsContains(1L,
				Lists.newArrayList(WidgetType.LAUNCH_STATISTICS, WidgetType.LAUNCHES_TABLE)
						.stream()
						.map(WidgetType::getType)
						.collect(Collectors.toList()),
				"statistics$product_bug$pb001"
		);

		assertFalse(widgets.isEmpty());
		assertEquals(1, widgets.size());
	}

	@Test
	void findAllByProjectIdWidgetTypeInAndContentFieldContainingTest() {
		List<Widget> widgets = repository.findAllByProjectIdAndWidgetTypeInAndContentFieldContaining(1L,
				Lists.newArrayList(WidgetType.LAUNCH_STATISTICS, WidgetType.LAUNCHES_TABLE)
						.stream()
						.map(WidgetType::getType)
						.collect(Collectors.toList()),
				"statistics$product_bug"
		);

		assertFalse(widgets.isEmpty());
		assertEquals(1, widgets.size());
	}

	private Filter buildDefaultFilter() {
		return Filter.builder()
				.withTarget(Widget.class)
				.withCondition(new FilterCondition(Condition.LOWER_THAN, false, "100", CRITERIA_ID))
				.build();
	}
}