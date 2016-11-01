/*
 * Copyright 2016 EPAM Systems
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

package com.epam.ta.reportportal.database.search;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import com.epam.ta.reportportal.database.entity.Project;

public class CriteriaMapTest {

	@Test
	public void projectCriteria() {
		CriteriaMap<Project> projectCriteriaMap = new CriteriaMap<>(Project.class);
		assertThat(projectCriteriaMap.getCriteriaHolder("configuration").getQueryCriteria()).isEqualTo("configuration");
		assertThat(projectCriteriaMap.getCriteriaHolder("name").getQueryCriteria()).isEqualTo("name");
		assertThat(projectCriteriaMap.getCriteriaHolder("configuration$entryType").getQueryCriteria()).isEqualTo("configuration.entryType");
		assertThat(projectCriteriaMap.getCriteriaHolder("users").getQueryCriteria()).isEqualTo("users");
		assertThat(projectCriteriaMap.getCriteriaHolder("creationDate").getQueryCriteria()).isEqualTo("creationDate");
	}
}