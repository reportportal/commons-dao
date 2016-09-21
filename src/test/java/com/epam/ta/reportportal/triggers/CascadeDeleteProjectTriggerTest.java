/*
 * Copyright 2016 EPAM Systems
 *
 *
 * This file is part of EPAM Report Portal.
 * https://github.com/epam/ReportPortal
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
package com.epam.ta.reportportal.triggers;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import com.epam.ta.reportportal.database.entity.Project;
import com.mongodb.BasicDBObject;
import org.junit.Before;
import org.junit.Test;

import com.epam.ta.reportportal.database.dao.LaunchRepository;
import com.epam.ta.reportportal.database.dao.ProjectRepository;
import com.epam.ta.reportportal.database.dao.TestItemRepository;
import com.epam.ta.reportportal.database.dao.UserRepository;
import com.epam.ta.reportportal.database.support.RepositoryProvider;
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent;

public class CascadeDeleteProjectTriggerTest {

	private CascadeDeleteProjectTrigger cascadeDeleteProjectTrigger;
	private ProjectRepository projectRepository;

	@Before
	public void setUp() {
		this.projectRepository = mock(ProjectRepository.class);
		this.cascadeDeleteProjectTrigger = new CascadeDeleteProjectTrigger(mock(LaunchRepository.class), mock(RepositoryProvider.class),
				mock(UserRepository.class), projectRepository, mock(TestItemRepository.class));
	}

	@Test
	public void eventUnexpectedQuery() {
		cascadeDeleteProjectTrigger.onBeforeDelete(new BeforeDeleteEvent<>(new BasicDBObject(), Project.class, "project"));
		verify(projectRepository, never()).findOne(anyString());
	}
}