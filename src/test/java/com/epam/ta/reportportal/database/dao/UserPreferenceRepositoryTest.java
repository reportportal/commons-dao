/*
 * Copyright 2017 EPAM Systems
 *
 *
 * This file is part of EPAM Report Portal.
 * https://github.com/reportportal/commons-dao
 *
 * Report Portal is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Report Portal is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Report Portal.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.epam.ta.reportportal.database.dao;

import com.epam.ta.reportportal.BaseDaoTest;
import com.epam.ta.reportportal.database.entity.UserPreference;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.UUID;

/**
 * @author Andrei Varabyeu
 */
public class UserPreferenceRepositoryTest extends BaseDaoTest {

	@Autowired
	private UserPreferenceRepository userPreferenceRepository;

	private String upID = UUID.randomUUID().toString();

	@Before
	public void setUp() {
		UserPreference up = new UserPreference();
		up.setId(upID);
		up.setProjectRef("someProject");
		up.setUserRef("someUser");

		UserPreference.LaunchTabs launchTabs = new UserPreference.LaunchTabs();
		launchTabs.setActive("true");
		launchTabs.setFilters(Arrays.asList("filter1", "filter2"));
		up.setLaunchTabs(launchTabs);

		userPreferenceRepository.save(up);
	}

	@Test
	public void testRemovingUnsharedFilter() {
		userPreferenceRepository.deleteUnsharedFilters("someUser1", "someProject", "filter1");
	}
}