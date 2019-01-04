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
import com.epam.ta.reportportal.dao.UserPreferenceRepository;
import com.epam.ta.reportportal.entity.preference.UserPreference;
import org.flywaydb.test.annotation.FlywayTest;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author <a href="mailto:ihar_kahadouski@epam.com">Ihar Kahadouski</a>
 */
public class UserPreferenceRepositoryTest extends BaseTest {

	private static final String FILL_SCRIPT_PATH = "/db/fill/user-preference";

	@Autowired
	private UserPreferenceRepository repository;

	@FlywayTest(locationsForMigrate = { FILL_SCRIPT_PATH }, invokeCleanDB = false)
	@BeforeClass
	public static void before() {
	}

	@Test
	public void findByProjectIdAndUserId() {
		final Long adminProjectId = 1L;
		final Long adminId = 1L;
		final List<UserPreference> adminPreferences = repository.findByProjectIdAndUserId(adminProjectId, adminId);
		assertNotNull(adminPreferences);
		assertTrue(!adminPreferences.isEmpty());
		adminPreferences.forEach(it -> {
			assertEquals(adminId, it.getUser().getId());
			assertEquals(adminProjectId, it.getProject().getId());
		});

		final Long defaultId = 2L;
		final Long defaultProjectId = 2L;
		final List<UserPreference> defaultPreferences = repository.findByProjectIdAndUserId(defaultProjectId, defaultId);
		assertNotNull(defaultPreferences);
		assertTrue(!defaultPreferences.isEmpty());
		defaultPreferences.forEach(it -> {
			assertEquals(defaultId, it.getUser().getId());
			assertEquals(defaultProjectId, it.getProject().getId());
		});
	}

	@Test
	public void removeByProjectIdAndUserId() {
		final Long defaultId = 2L;
		final Long defaultProjectId = 2L;
		repository.removeByProjectIdAndUserId(defaultProjectId, defaultId);

		final List<UserPreference> defaultPreferences = repository.findByProjectIdAndUserId(defaultProjectId, defaultId);
		assertTrue(defaultPreferences.isEmpty());
	}
}
