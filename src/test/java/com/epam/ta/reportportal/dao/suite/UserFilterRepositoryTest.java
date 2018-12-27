/*
 * Copyright (C) 2018 EPAM Systems
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
import com.epam.ta.reportportal.commons.querygen.Filter;
import com.epam.ta.reportportal.commons.querygen.FilterCondition;
import com.epam.ta.reportportal.commons.querygen.ProjectFilter;
import com.epam.ta.reportportal.dao.UserFilterRepository;
import com.epam.ta.reportportal.entity.filter.UserFilter;
import com.epam.ta.reportportal.jooq.tables.JFilter;
import com.google.common.collect.Sets;
import org.flywaydb.test.annotation.FlywayTest;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Set;

/**
 * @author Ivan Nikitsenka
 */
public class UserFilterRepositoryTest extends BaseTest {

	@Autowired
	private UserFilterRepository userFilterRepository;

	@FlywayTest(locationsForMigrate = { "db/fill/filter" }, invokeCleanDB = false)
	@BeforeClass
	public static void before() {
	}

	@Test
	public void getSharedFilters() {
		Page<UserFilter> result1 = userFilterRepository.getShared(ProjectFilter.of(buildDefaultFilter(), 2L),
				PageRequest.of(0, 3),
				"superadmin"
		);

		Assert.assertEquals(2l, result1.getTotalElements());
		Assert.assertEquals(Long.valueOf(1l), result1.get().findFirst().get().getId());

		Page<UserFilter> result2 = userFilterRepository.getShared(ProjectFilter.of(buildDefaultFilter(), 2L),
				PageRequest.of(0, 3),
				"default"
		);

		Assert.assertEquals(0l, result2.getTotalElements());
	}

	@Test
	public void getPermittedFilters() {
		PageRequest of = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, JFilter.FILTER.NAME.getQualifiedName().toString()));
		Page<UserFilter> result1 = userFilterRepository.getPermitted(ProjectFilter.of(buildDefaultFilter(), 1L), of, "superadmin");

		Assert.assertEquals(2l, result1.getTotalElements());
		Assert.assertEquals(Long.valueOf(1l), result1.get().findFirst().get().getId());

		Page<UserFilter> result2 = userFilterRepository.getPermitted(ProjectFilter.of(buildDefaultFilter(), 2L),
				PageRequest.of(0, 3),
				"default"
		);

		Assert.assertEquals(3l, result2.getTotalElements());
		Assert.assertEquals(Long.valueOf(1l), result2.get().findFirst().get().getId());
	}

	@Test
	public void getOwnFilters() {
		Page<UserFilter> result1 = userFilterRepository.getOwn(ProjectFilter.of(buildDefaultFilter(), 2L),
				PageRequest.of(0, 3),
				"superadmin"
		);

		Assert.assertEquals(0l, result1.getTotalElements());

		Page<UserFilter> result2 = userFilterRepository.getOwn(ProjectFilter.of(buildDefaultFilter(), 2L), PageRequest.of(0, 3), "default");

		Assert.assertEquals(3l, result2.getTotalElements());
		Assert.assertEquals(Long.valueOf(1l), result2.get().findFirst().get().getId());
	}

	@Test
	public void getSharedFiltersPaging() {
		Page<UserFilter> result1 = userFilterRepository.getShared(ProjectFilter.of(buildDefaultFilter(), 2L),
				PageRequest.of(0, 1),
				"superadmin"
		);

		Assert.assertEquals(1l, result1.getTotalElements());
		Assert.assertEquals(Long.valueOf(1l), result1.get().findFirst().get().getId());
	}

	private Filter buildDefaultFilter() {
		Set<FilterCondition> conditionSet = Sets.newHashSet();
		return new Filter(UserFilter.class, conditionSet);
	}

	@Test
	public void existsByNameAndOwnerAndProjectIdTest() {
		Assert.assertTrue(userFilterRepository.existsByNameAndOwnerAndProjectId("DEMO_FILTER", "superadmin", 1L));
		Assert.assertFalse(userFilterRepository.existsByNameAndOwnerAndProjectId("DEMO_FILTER", "yahoo", 1L));
		Assert.assertFalse(userFilterRepository.existsByNameAndOwnerAndProjectId("DEMO_FILTER", "superadmin", 2L));
	}

}