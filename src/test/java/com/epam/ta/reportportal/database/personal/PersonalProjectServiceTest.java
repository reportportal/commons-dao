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
package com.epam.ta.reportportal.database.personal;

import com.epam.ta.reportportal.database.dao.ProjectRepository;
import com.epam.ta.reportportal.database.entity.Project;
import com.epam.ta.reportportal.database.entity.ProjectRole;
import com.epam.ta.reportportal.database.entity.project.EntryType;
import com.epam.ta.reportportal.database.entity.user.User;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.mockito.Mockito.when;

/**
 * Tests for {@link PersonalProjectService}
 *
 * @author <a href="mailto:andrei_varabyeu@epam.com">Andrei Varabyeu</a>
 */
public class PersonalProjectServiceTest {

	@Test
	public void personalProjectNameExisting() throws Exception {
		ProjectRepository repo = Mockito.mock(ProjectRepository.class);
		String userName = "John";
		when(repo.exists(Mockito.anyString())).then(invocation -> "john_personal".equals(invocation.getArguments()[0]));

		Assert.assertThat("Generated personal space name is incorrect", new PersonalProjectService(repo).generatePersonalProjectName(userName),
				Matchers.is("john_personal_1".toLowerCase()));
	}

	@Test
	public void personalProjectNameFree() throws Exception {
		String userName = "John";

		Assert.assertThat("Generated personal space name is incorrect", new PersonalProjectService(mockProjectRepo()).generatePersonalProjectName(userName),
				Matchers.is("john_personal".toLowerCase()));
	}

	@Test
	public void generatePersonalProject() throws Exception {
		User user = new User();
		String login = "johnny";
		user.setLogin(login);
		user.setFullName("John");

		Project project = new PersonalProjectService(mockProjectRepo()).generatePersonalProject(user);
		Assert.assertThat("Project doesn't have user", project.getUsers(), Matchers.hasKey(login));
		Assert.assertThat("Incorrect role", project.getUsers().get(login).getProjectRole(), Matchers.is(ProjectRole.PROJECT_MANAGER));
		Assert.assertThat("Incorrect role", project.getUsers().get(login).getProposedRole(), Matchers.is(ProjectRole.PROJECT_MANAGER));

		Assert.assertThat("Incorrect date", project.getCreationDate(), Matchers.notNullValue());
		Assert.assertThat("Incorrect configuration", project.getConfiguration(), Matchers.notNullValue());
		Assert.assertThat("Incorrect additional info", project.getAddInfo(), Matchers.notNullValue());

	}

	@Test
	public void defaultConfiguration() throws Exception {
		Project.Configuration configuration = PersonalProjectService.defaultConfiguration();

		Assert.assertThat("Incorrect project type", configuration.getEntryType(), Matchers.is(EntryType.PERSONAL));
		Assert.assertThat("Incorrect keep screenshots config", configuration.getKeepScreenshots(), Matchers.notNullValue());
		Assert.assertThat("Incorrect auto analysis config", configuration.getIsAutoAnalyzerEnabled(), Matchers.is(false));
		Assert.assertThat("Incorrect interrupt config", configuration.getInterruptJobTime(), Matchers.notNullValue());
		Assert.assertThat("Incorrect keep logs config", configuration.getKeepLogs(), Matchers.notNullValue());

	}

	private ProjectRepository mockProjectRepo(){
		ProjectRepository repo = Mockito.mock(ProjectRepository.class);
		when(repo.exists(Mockito.anyString())).thenReturn(false);
		return repo;
	}

}