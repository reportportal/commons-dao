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
package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.BaseTest;
import com.epam.ta.reportportal.commons.querygen.Filter;
import com.epam.ta.reportportal.commons.querygen.FilterCondition;
import com.epam.ta.reportportal.entity.filter.UserFilter;
import com.google.common.collect.Sets;
import org.flywaydb.test.annotation.FlywayTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;

import java.util.Set;

/**
 * @author Ivan Nikitsenka
 */
@FlywayTest
@Sql("/test-user-filters-fill.sql")
public class UserFilterRepositoryTest extends BaseTest {

	@Autowired
	private UserFilterRepositoryCustom userFilterRepository;

	@Test
	public void getSharedFilters() {
		Page<UserFilter> result1 = userFilterRepository.getSharedFilters(1l, buildDefaultFilter(), PageRequest.of(0, 3), "superadmin");

		Assert.assertEquals(2l, result1.getTotalElements());
		Assert.assertEquals(Long.valueOf(1l), result1.get().findFirst().get().getId());

		Page<UserFilter> result2 = userFilterRepository.getSharedFilters(1l, buildDefaultFilter(), PageRequest.of(0, 3), "default");

		Assert.assertEquals(0l, result2.getTotalElements());
	}

	@Test
	public void getPermittedFilters() {
		Page<UserFilter> result1 = userFilterRepository.getPermittedFilters(1l, buildDefaultFilter(), PageRequest.of(0, 3), "superadmin");

		Assert.assertEquals(2l, result1.getTotalElements());
		Assert.assertEquals(Long.valueOf(1l), result1.get().findFirst().get().getId());

		Page<UserFilter> result2 = userFilterRepository.getPermittedFilters(1l, buildDefaultFilter(), PageRequest.of(0, 3), "default");

		Assert.assertEquals(3l, result2.getTotalElements());
		Assert.assertEquals(Long.valueOf(1l), result2.get().findFirst().get().getId());
	}

	@Test
	public void getOwnFilters() {
		Page<UserFilter> result1 = userFilterRepository.getOwnFilters(1l, buildDefaultFilter(), PageRequest.of(0, 3), "superadmin");

		Assert.assertEquals(0l, result1.getTotalElements());

		Page<UserFilter> result2 = userFilterRepository.getOwnFilters(1l, buildDefaultFilter(), PageRequest.of(0, 3), "default");

		Assert.assertEquals(3l, result2.getTotalElements());
		Assert.assertEquals(Long.valueOf(1l), result2.get().findFirst().get().getId());
	}

	@Test
	public void getSharedFiltersPaging() {
		Page<UserFilter> result1 = userFilterRepository.getSharedFilters(1l, buildDefaultFilter(), PageRequest.of(0, 1), "superadmin");

		Assert.assertEquals(1l, result1.getTotalElements());
		Assert.assertEquals(Long.valueOf(1l), result1.get().findFirst().get().getId());
	}

	private Filter buildDefaultFilter() {
		Set<FilterCondition> conditionSet = Sets.newHashSet();
		return new Filter(2L, UserFilter.class, conditionSet);
	}

}