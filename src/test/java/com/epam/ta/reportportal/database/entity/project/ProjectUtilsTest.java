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
package com.epam.ta.reportportal.database.entity.project;

import static com.epam.ta.reportportal.database.entity.project.ProjectUtils.excludeProjectRecipients;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

import java.util.List;

import com.epam.ta.reportportal.database.entity.project.email.EmailSenderCase;
import com.epam.ta.reportportal.database.entity.project.email.ProjectEmailConfig;
import org.junit.Assert;
import org.junit.Test;

import com.epam.ta.reportportal.database.entity.Project;
import com.epam.ta.reportportal.database.entity.user.User;

public class ProjectUtilsTest {

	private String aliveUser = "user3";

	@Test
	public void excludeProjectRecipientsTest() {
		Project project = project();
		excludeProjectRecipients(usersToExclude(), project);
		EmailSenderCase emailSenderCase = project.getConfiguration().getEmailConfig().getEmailSenderCases().get(0);
		Assert.assertEquals(1, emailSenderCase.getRecipients().size());
		Assert.assertEquals(aliveUser, emailSenderCase.getRecipients().get(0));
	}

	private List<User> usersToExclude() {
		User user1 = new User();
		user1.setLogin("uSer1");
		user1.setEmail("usEr1@fake.com");
		User user2 = new User();
		user2.setEmail("user2@fake.com");
		user2.setLogin("user2");
		return asList(user1, user2);
	}

	private Project project() {
		final Project project = new Project();
		final Project.Configuration configuration = new Project.Configuration();
		final ProjectEmailConfig emailConfig = new ProjectEmailConfig();
		emailConfig.setEmailSenderCases(singletonList(new EmailSenderCase(asList("user1", "user1@fake.com", "user2@fake.com", aliveUser),
				"ALWAYS", asList("launch"), asList("tag"))));
		configuration.setEmailConfig(emailConfig);
		project.setConfiguration(configuration);
		return project;
	}

}