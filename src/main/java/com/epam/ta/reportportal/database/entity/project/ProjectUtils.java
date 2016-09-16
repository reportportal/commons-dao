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

package com.epam.ta.reportportal.database.entity.project;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.epam.ta.reportportal.commons.SendCase;
import com.epam.ta.reportportal.database.entity.Project;
import com.epam.ta.reportportal.database.entity.statistics.IssueCounter;
import com.epam.ta.reportportal.database.entity.user.User;
import com.epam.ta.reportportal.ws.model.project.email.EmailSenderCase;
import com.epam.ta.reportportal.ws.model.project.email.ProjectEmailConfig;
import com.google.common.collect.Lists;

/**
 * Project related utility methods
 * 
 * @author Andrei_Ramanchuk
 *
 */
public class ProjectUtils {
	private static final String INIT_FROM = "reportportal@example.com";
	private static final String OWNER = "OWNER";

	/**
	 * Setup default project email configuration
	 * 
	 * @param project
	 * @return project object with default email config
	 */
	public static Project setDefaultEmailCofiguration(Project project) {
		EmailSenderCase defaultOne = new EmailSenderCase(Lists.newArrayList(OWNER), SendCase.ALWAYS.name(), Lists.newArrayList(),
				Lists.newArrayList());
		ProjectEmailConfig config = new ProjectEmailConfig(false, INIT_FROM, Lists.newArrayList(defaultOne));
		project.getConfiguration().setEmailConfig(config);
		return project;
	}

	/**
	 * Exclude specified project recipients
	 * 
	 * @param users
	 * @param project
	 * @return
	 */
	public static Project excludeProjectRecipients(Iterable<User> users, Project project) {
		/* Current recipients of specified project */
		List<EmailSenderCase> cases = project.getConfiguration().getEmailConfig().getEmailCases();
		if ((null != cases) && (null != users)) {
			cases.stream().forEach(c -> {
				// saved - list of saved user emails before changes
				List<String> saved = c.getRecipients();
				c.setRecipients(saved.stream().filter(processRecipients(users)).collect(Collectors.toList()));
			});
			project.getConfiguration().getEmailConfig().setEmailCases(cases);
		}
		return project;
	}

	private static Predicate<String> processRecipients(final Iterable<User> users) {
		return input -> !StreamSupport.stream(users.spliterator(), false).filter(user -> user.getEmail().equalsIgnoreCase(input))
				.findFirst().isPresent();
	}

	/**
	 * Update specified project recipient
	 * 
	 * @param oldEmail
	 * @param newEmail
	 * @param project
	 * @return
	 */
	public static Project updateProjectRecipients(String oldEmail, String newEmail, Project project) {
		List<EmailSenderCase> cases = project.getConfiguration().getEmailConfig().getEmailCases();
		if ((null != cases) && (null != oldEmail) && (null != newEmail)) {
			cases.stream().forEach(c -> {
				List<String> saved = c.getRecipients();
				if (saved.stream().filter(email -> email.equalsIgnoreCase(oldEmail)).findFirst().isPresent()) {
					c.setRecipients(
							saved.stream().filter(processRecipientsEmails(Lists.newArrayList(oldEmail))).collect(Collectors.toList()));
					c.getRecipients().add(newEmail);
				}
			});
			project.getConfiguration().getEmailConfig().setEmailCases(cases);
		}
		return project;
	}

	private static Predicate<String> processRecipientsEmails(final Iterable<String> emails) {
		return input -> !StreamSupport.stream(emails.spliterator(), false).filter(email -> email.equalsIgnoreCase(input)).findFirst()
				.isPresent();
	}

	/**
	 * Utility method for summarize IssueCounter of project items.<br>
	 * Expected that IssueCounters has access to the same project settings,
	 * means issue sub-types which are defined in project settings.
	 * 
	 * @param inputOne
	 * @param inputTwo
	 * @return IssueCounter
	 */
	public static IssueCounter sumIssueStatistics(IssueCounter inputOne, IssueCounter inputTwo) {
		Map<String, Integer> ab = new HashMap<>(inputOne.getAutomationBug());
		inputTwo.getAutomationBug().forEach((k, v) -> ab.merge(k, v, (a, b) -> a + b));

		Map<String, Integer> pb = new HashMap<>(inputOne.getProductBug());
		inputTwo.getProductBug().forEach((k, v) -> pb.merge(k, v, (a, b) -> a + b));

		Map<String, Integer> si = new HashMap<>(inputOne.getSystemIssue());
		inputTwo.getSystemIssue().forEach((k, v) -> si.merge(k, v, (a, b) -> a + b));

		Map<String, Integer> nd = new HashMap<>(inputOne.getNoDefect());
		inputTwo.getNoDefect().forEach((k, v) -> nd.merge(k, v, (a, b) -> a + b));

		Map<String, Integer> ti = new HashMap<>(inputOne.getToInvestigate());
		inputTwo.getToInvestigate().forEach((k, v) -> ti.merge(k, v, (a, b) -> a + b));

		return new IssueCounter(pb, ab, si, ti, nd);
	}

	public static String getOwner() {
		return OWNER;
	}
}