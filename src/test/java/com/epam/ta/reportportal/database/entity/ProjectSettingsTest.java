/*
 * Copyright 2016 EPAM Systems
 *
 *
 * This file is part of EPAM Report Portal.
 * https://github.com/reportportal/service-dao
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
package com.epam.ta.reportportal.database.entity;

import org.junit.Assert;
import org.junit.Test;

import com.epam.ta.reportportal.database.entity.statistics.StatisticSubType;

public class ProjectSettingsTest {

	@Test
	public void notIssue() {
		final Project.Configuration projectSettings = new Project.Configuration();
		final StatisticSubType not_issue = projectSettings.getByLocator("NOT_IsSUE");
		Assert.assertNull(not_issue);
	}

	@Test
	public void toInvestigate() {
		final Project.Configuration projectSettings = new Project.Configuration();
		final StatisticSubType subType = projectSettings.getByLocator("Ti001");
		Assert.assertNotNull(subType);
		Assert.assertEquals("TO_INVESTIGATE", subType.getTypeRef());
	}

	@Test
	public void incorrectLocator() {
		final Project.Configuration projectSettings = new Project.Configuration();
		final StatisticSubType incorrect = projectSettings.getByLocator("incorrect");
		Assert.assertNull(incorrect);
	}

}