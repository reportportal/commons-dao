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

/**
 * @author Andrei Varabyeu
 */
public class ProjectRoleTest {

	@Test
	public void higherThan() throws Exception {
		Assert.assertTrue(ProjectRole.PROJECT_MANAGER.higherThan(ProjectRole.CUSTOMER));
	}

	@Test
	public void sameOrHigherThan() throws Exception {
		Assert.assertTrue(ProjectRole.PROJECT_MANAGER.sameOrHigherThan(ProjectRole.PROJECT_MANAGER));
	}

	@Test
	public void sameOrHigherThanNegative() throws Exception {
		Assert.assertFalse(ProjectRole.MEMBER.sameOrHigherThan(ProjectRole.PROJECT_MANAGER));
	}

	@Test
	public void forNamePositive() throws Exception {
		Assert.assertTrue(ProjectRole.forName("project_manager").isPresent());
	}

	@Test
	public void forNameNegative() throws Exception {
		Assert.assertFalse(ProjectRole.forName("non existing").isPresent());
	}

}