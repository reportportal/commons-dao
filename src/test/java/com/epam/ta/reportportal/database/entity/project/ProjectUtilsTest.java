package com.epam.ta.reportportal.database.entity.project;

import static com.epam.ta.reportportal.database.entity.project.ProjectUtils.excludeProjectRecipients;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.epam.ta.reportportal.database.entity.Project;
import com.epam.ta.reportportal.database.entity.user.User;
import com.epam.ta.reportportal.ws.model.project.email.EmailSenderCase;
import com.epam.ta.reportportal.ws.model.project.email.ProjectEmailConfig;

public class ProjectUtilsTest {

	private String aliveUser = "user3";

	@Test
	public void excludeProjectRecipientsTest() {
		Project project = project();
		excludeProjectRecipients(usersToExclude(), project);
		EmailSenderCase emailSenderCase = project.getConfiguration().getEmailConfig().getEmailCases().get(0);
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
		emailConfig.setEmailCases(singletonList(new EmailSenderCase(asList("user1", "user1@fake.com", "user2@fake.com", aliveUser),
				"ALWAYS", asList("launch"), asList("tag"))));
		configuration.setEmailConfig(emailConfig);
		project.setConfiguration(configuration);
		return project;
	}

}