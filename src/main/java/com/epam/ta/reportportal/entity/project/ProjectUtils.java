/*
 * Copyright (C) 2018 EPAM Systems
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.epam.ta.reportportal.entity.project;

import com.epam.ta.reportportal.entity.attribute.Attribute;
import com.epam.ta.reportportal.entity.enums.ProjectAttributeEnum;
import com.epam.ta.reportportal.entity.enums.ProjectType;
import com.epam.ta.reportportal.entity.enums.SendCase;
import com.epam.ta.reportportal.entity.enums.TestItemIssueGroup;
import com.epam.ta.reportportal.entity.item.issue.IssueType;
import com.epam.ta.reportportal.entity.project.email.SenderCase;
import com.epam.ta.reportportal.entity.user.ProjectUser;
import com.epam.ta.reportportal.entity.user.User;
import com.epam.ta.reportportal.exception.ReportPortalException;
import com.epam.ta.reportportal.ws.model.ErrorType;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.sun.javafx.binding.StringFormatter;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toSet;
import static java.util.stream.StreamSupport.stream;

/**
 * Project related utility methods
 *
 * @author Andrei_Ramanchuk
 */
public class ProjectUtils {
	public static final String INIT_FROM = "reportportal@example.com";
	public static final String PERSONAL_PROJECT_POSTFIX_REGEX = "_personal[0-9]*$";
	public static final String LINE_START_SYMBOL = "^";

	private static final String OWNER = "OWNER";

	private ProjectUtils() {

	}

	/**
	 * @return Generated default project configuration
	 */
	public static Set<ProjectAttribute> defaultProjectAttributes(Project project, Set<Attribute> defaultAttributes) {

		Map<String, Attribute> attributes = defaultAttributes.stream().collect(Collectors.toMap(Attribute::getName, a -> a));

		Set<ProjectAttribute> projectAttributes = new HashSet<>(defaultAttributes.size());

		Arrays.stream(ProjectAttributeEnum.values())
				.map(ProjectAttributeEnum::getAttribute)
				.forEach(pa -> ofNullable(attributes.get(pa)).ifPresent(attr -> {
					ProjectAttribute projectAttribute = new ProjectAttribute();
					projectAttribute.setAttribute(attr);
					projectAttribute.setProject(project);

					projectAttribute.setValue(ProjectAttributeEnum.findByAttributeName(pa)
							.orElseThrow(() -> new ReportPortalException(ErrorType.BAD_REQUEST_ERROR,
									StringFormatter.format("Attribute - {} was not found", pa)
							))
							.getDefaultValue());

					projectAttributes.add(projectAttribute);
				}));

		return projectAttributes;

	}

	public static Set<ProjectIssueType> defaultIssueTypes(Project project, List<IssueType> defaultIssueTypes) {

		Map<String, IssueType> issueTypes = defaultIssueTypes.stream().collect(Collectors.toMap(IssueType::getLocator, i -> i));

		Set<ProjectIssueType> projectIssueTypes = new HashSet<>(defaultIssueTypes.size());
		Arrays.stream(TestItemIssueGroup.values())
				.map(TestItemIssueGroup::getLocator)
				.forEach(loc -> ofNullable(issueTypes.get(loc)).ifPresent(it -> {
					ProjectIssueType projectIssueType = new ProjectIssueType();
					projectIssueType.setIssueType(it);
					projectIssueType.setProject(project);
					projectIssueTypes.add(projectIssueType);
				}));
		return projectIssueTypes;
	}

	/**
	 * Setup default project notification configuration
	 *
	 * @param project {@link Project}
	 * @return project object with default notification configuration
	 */
	public static Project setDefaultNotificationConfiguration(Project project) {
		SenderCase defaultSenderCase = new SenderCase(Sets.newHashSet(OWNER), Sets.newHashSet(), Sets.newHashSet(), SendCase.ALWAYS);
		defaultSenderCase.setProject(project);
		project.setSenderCases(Sets.newHashSet(defaultSenderCase));
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
			Set<String> toExclude = stream(users.spliterator(), false).map(user -> asList(user.getEmail().toLowerCase(),
					user.getLogin().toLowerCase()
			)).flatMap(List::stream).collect(toSet());
			/* Current recipients of specified project */
			Set<SenderCase> cases = project.getSenderCases();
			if (null != cases) {
				cases.stream().forEach(c -> {
					// saved - list of saved user emails before changes
					Set<String> saved = c.getRecipients();
					c.setRecipients(saved.stream().filter(it -> !toExclude.contains(it.toLowerCase())).collect(Collectors.toSet()));
				});
				project.setSenderCases(cases);
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
		Set<SenderCase> cases = project.getSenderCases();
		if ((null != cases) && (null != oldEmail) && (null != newEmail)) {
			cases.stream().forEach(c -> {
				Set<String> saved = c.getRecipients();
				if (saved.stream().anyMatch(email -> email.equalsIgnoreCase(oldEmail))) {
					c.setRecipients(saved.stream()
							.filter(processRecipientsEmails(Lists.newArrayList(oldEmail)))
							.collect(Collectors.toSet()));
					c.getRecipients().add(newEmail);
				}
			});
			project.setSenderCases(cases);
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
		return project.getUsers().stream().filter(it -> user.equalsIgnoreCase(it.getUser().getLogin())).findAny().orElse(null);
	}

	public static Map<String, String> getConfigParameters(Set<ProjectAttribute> projectAttributes) {
		return ofNullable(projectAttributes).map(attributes -> attributes.stream()
				.collect(Collectors.toMap(pa -> pa.getAttribute().getName(), ProjectAttribute::getValue))).orElseGet(Collections::emptyMap);
	}

	public static boolean isPersonalForUser(ProjectType projectType, String projectName, String username) {
		return projectType == ProjectType.PERSONAL && Pattern.compile(LINE_START_SYMBOL + username + PERSONAL_PROJECT_POSTFIX_REGEX)
				.matcher(projectName)
				.matches();
	}

	private static Predicate<String> processRecipientsEmails(final Iterable<String> emails) {
		return input -> stream(emails.spliterator(), false).noneMatch(email -> email.equalsIgnoreCase(input));
	}

	public static String getOwner() {
		return OWNER;
	}
}