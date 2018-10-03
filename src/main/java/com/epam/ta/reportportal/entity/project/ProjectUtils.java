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

package com.epam.ta.reportportal.entity.project;

import com.epam.ta.reportportal.commons.SendCase;
import com.epam.ta.reportportal.entity.project.email.EmailSenderCase;
import com.epam.ta.reportportal.entity.statistics.IssueCounter;
import com.epam.ta.reportportal.entity.user.ProjectUser;
import com.epam.ta.reportportal.entity.user.User;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import static java.util.stream.StreamSupport.stream;

/**
 * Project related utility methods
 *
 * @author Andrei_Ramanchuk
 */
public class ProjectUtils {
	public static final String INIT_FROM = "reportportal@example.com";
	private static final String OWNER = "OWNER";

	private ProjectUtils() {

	}

	/**
	 * Setup default project email configuration
	 *
	 * @param project
	 * @return project object with default email config
	 */
	public static Project setDefaultEmailConfiguration(Project project) {
		EmailSenderCase defaultOne = new EmailSenderCase(Lists.newArrayList(OWNER), SendCase.ALWAYS, Sets.newHashSet(), Sets.newHashSet());
		defaultOne.setProject(project);
		project.setEmailCases(Sets.newHashSet(defaultOne));
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
		if (users != null) {
			Set<String> toExclude = stream(users.spliterator(), false).map(user -> asList(
					user.getEmail().toLowerCase(),
					user.getLogin().toLowerCase()
			)).flatMap(List::stream).collect(toSet());
			/* Current recipients of specified project */
			Set<EmailSenderCase> cases = project.getEmailCases();
			if (null != cases) {
				cases.stream().forEach(c -> {
					// saved - list of saved user emails before changes
					List<String> saved = c.getRecipients();
					c.setRecipients(saved.stream().filter(it -> !toExclude.contains(it.toLowerCase())).collect(toList()));
				});
				project.setEmailCases(cases);
			}
		}
		return project;
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
		Set<EmailSenderCase> cases = project.getEmailCases();
		if ((null != cases) && (null != oldEmail) && (null != newEmail)) {
			cases.stream().forEach(c -> {
				List<String> saved = c.getRecipients();
				if (saved.stream().anyMatch(email -> email.equalsIgnoreCase(oldEmail))) {
					c.setRecipients(saved.stream().filter(processRecipientsEmails(Lists.newArrayList(oldEmail))).collect(toList()));
					c.getRecipients().add(newEmail);
				}
			});
			project.setEmailCases(cases);
		}
		return project;
	}

	/**
	 * Checks if the user is assigned on project
	 *
	 * @param project Specified project
	 * @param user    User login
	 * @return True, if exists
	 */
	public static boolean doesHaveUser(Project project, String user) {
		return project.getUsers().stream().anyMatch(it -> user.equals(it.getUser().getLogin()));
	}

	/**
	 * Finds UserConfig for specified login. Returns null
	 * if it doesn't exists.
	 *
	 * @param user Login for search
	 * @return UserConfig for specified login
	 */
	@Nullable
	public static ProjectUser findUserConfigByLogin(Project project, String user) {
		return project.getUsers().stream().filter(it -> user.equals(it.getUser().getLogin())).findAny().orElse(null);
	}

	private static Predicate<String> processRecipientsEmails(final Iterable<String> emails) {
		return input -> stream(emails.spliterator(), false).noneMatch(email -> email.equalsIgnoreCase(input));
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