/*
 * Copyright 2019 EPAM Systems
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

package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.BaseTest;
import com.epam.ta.reportportal.commons.querygen.Condition;
import com.epam.ta.reportportal.commons.querygen.Filter;
import com.epam.ta.reportportal.commons.querygen.FilterCondition;
import com.epam.ta.reportportal.entity.Metadata;
import com.epam.ta.reportportal.entity.project.Project;
import com.epam.ta.reportportal.entity.project.ProjectRole;
import com.epam.ta.reportportal.entity.user.ProjectUser;
import com.epam.ta.reportportal.entity.user.User;
import com.epam.ta.reportportal.entity.user.UserRole;
import com.epam.ta.reportportal.entity.user.UserType;
import org.assertj.core.util.Sets;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.jdbc.Sql;

import java.util.*;

import static com.epam.ta.reportportal.commons.querygen.constant.GeneralCriteriaConstant.*;
import static com.epam.ta.reportportal.commons.querygen.constant.UserCriteriaConstant.CRITERIA_LAST_LOGIN;
import static com.epam.ta.reportportal.commons.querygen.constant.UserCriteriaConstant.CRITERIA_USER;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Ivan Budaev
 */
@Sql("/db/fill/user/user-fill.sql")
class UserRepositoryTest extends BaseTest {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ProjectRepository projectRepository;

	@Test
	void loadUserByLastLogin() {
		//given
		long now = new Date().getTime();
		Filter filter = Filter.builder()
				.withTarget(User.class)
				.withCondition(FilterCondition.builder()
						.withCondition(Condition.LOWER_THAN)
						.withSearchCriteria(CRITERIA_LAST_LOGIN)
						.withValue(String.valueOf(now))
						.build())
				.withCondition(FilterCondition.builder().eq(CRITERIA_PROJECT_ID, "3").build())
				.build();
		//when
		List<User> users = userRepository.findByFilter(filter);
		//then
		assertThat("Users should exist", users.size(), Matchers.greaterThan(0));
		users.forEach(user -> assertThat(
				"Last login should be lower than in the filer", Long.parseLong((String) user.getMetadata().getMetadata().get("last_login")),
				Matchers.lessThan(now)
		));
	}

	@Test
	void loadUserNameByProject() {
		//given
		String term = "admin";
		//when
		List<String> userNames = userRepository.findNamesByProject(1L, term);
		//then
		assertThat("User names not found", userNames, Matchers.notNullValue());
		assertThat("Incorrect size of user names", userNames, Matchers.hasSize(1));
		userNames.forEach(name -> assertThat("Name doesn't contain specified 'admin' term", name, Matchers.containsString(term)));
	}

	@Test
	void negativeLoadUserNamesByProject() {
		//given
		String term = "negative";
		//when
		List<String> userNames = userRepository.findNamesByProject(1L, term);
		//then
		assertThat("Result contains user names", userNames, Matchers.empty());
	}

	@Test
	void loadUsersByFilterForProject() {
		//given
		Filter filter = buildDefaultUserFilter();
		filter.withCondition(new FilterCondition(Condition.EQUALS, false, "3", CRITERIA_PROJECT_ID));
		//when
		List<User> users = userRepository.findByFilterExcluding(filter, PageRequest.of(0, 5), "email").getContent();
		//then
		assertThat("Users not found", users, Matchers.notNullValue());
		assertThat("Incorrect size of founded users", users, Matchers.hasSize(3));
		users.forEach(it -> assertNull(it.getEmail()));
	}

	@Test
	void findByEmail() {
		final String email = "chybaka@domain.com";

		Optional<User> user = userRepository.findByEmail(email);

		assertTrue(user.isPresent(), "User not found");
		assertThat("Emails are not equal", user.get().getEmail(), Matchers.equalTo(email));
	}

	@Test
	void findIdByLogin() {

		Optional<Long> userId = userRepository.findIdByLoginForUpdate("han_solo");
		assertTrue(userId.isPresent(), "User not found");
		assertThat("Ids are not equal", userId.get(), Matchers.equalTo(27L));
	}

	@Test
	void findByLogin() {
		final String login = "han_solo";

		Optional<User> user = userRepository.findByLogin(login);

		assertTrue(user.isPresent(), "User not found");
		assertThat("Emails are not equal", user.get().getLogin(), Matchers.equalTo(login));
	}

	@Test
	void findAllByEmailIn() {
		List<String> emails = Arrays.asList("han_solo@domain.com", "chybaka@domain.com");

		List<User> users = userRepository.findAllByEmailIn(emails);

		assertThat("Users not found", users, Matchers.notNullValue());
		assertThat("Incorrect size of users", users, Matchers.hasSize(2));
		assertTrue(users.stream().anyMatch(u -> u.getEmail().equalsIgnoreCase(emails.get(0))), "Incorrect user email");
		assertTrue(users.stream().anyMatch(u -> u.getEmail().equalsIgnoreCase(emails.get(1))), "Incorrect user email");
	}

	@Test
	void findAllByLoginIn() {
		final String hanLogin = "han_solo";
		final String defaultLogin = "default";
		Set<String> logins = Sets.newHashSet(Arrays.asList(hanLogin, defaultLogin));

		List<User> users = userRepository.findAllByLoginIn(logins);

		assertThat("Users not found", users, Matchers.notNullValue());
		assertThat("Incorrect size of users", users, Matchers.hasSize(2));
		assertTrue(users.stream().anyMatch(u -> u.getLogin().equalsIgnoreCase(hanLogin)), "Incorrect user login");
		assertTrue(users.stream().anyMatch(u -> u.getLogin().equalsIgnoreCase(defaultLogin)), "Incorrect user login");
	}

	@Test
	void findAllByRole() {
		List<User> users = userRepository.findAllByRole(UserRole.USER);

		assertEquals(3, users.size());
		users.forEach(it -> assertEquals(UserRole.USER, it.getRole()));
	}

	@Test
	void findAllByUserTypeAndExpired() {
		Page<User> users = userRepository.findAllByUserTypeAndExpired(UserType.INTERNAL, false, Pageable.unpaged());

		assertNotNull(users);
		assertEquals(5, users.getNumberOfElements());
	}

	@Test
	void searchForUserTest() {
		Filter filter = Filter.builder()
				.withTarget(User.class)
				.withCondition(new FilterCondition(Condition.CONTAINS, false, "chuba", CRITERIA_USER))
				.build();
		Page<User> users = userRepository.findByFilter(filter, PageRequest.of(0, 5));
		assertEquals(2, users.getTotalElements());
	}

	@Test
	void searchForUserTestWithNoResults() {
		Filter filter = Filter.builder()
				.withTarget(User.class)
				.withCondition(new FilterCondition(Condition.CONTAINS, false, "_ub", CRITERIA_USER))
				.build();
		Page<User> users = userRepository.findByFilter(filter, PageRequest.of(0, 5));
		assertEquals(0, users.getTotalElements());
	}

	@Test
	void usersWithProjectSort() {
		Filter filter = Filter.builder()
				.withTarget(User.class)
				.withCondition(new FilterCondition(Condition.CONTAINS, false, "chuba", CRITERIA_USER))
				.build();
		PageRequest pageRequest = PageRequest.of(0, 5, Sort.Direction.ASC, CRITERIA_PROJECT);
		Page<User> result = userRepository.findByFilter(filter, pageRequest);
		assertEquals(2, result.getTotalElements());
	}

	@SuppressWarnings("OptionalGetWithoutIsPresent")
	@Test
	void createUserTest() {
		User reg = new User();

		reg.setEmail("email.com");
		reg.setFullName("test");
		reg.setLogin("created");
		reg.setPassword("new");
		reg.setUserType(UserType.INTERNAL);
		reg.setRole(UserRole.USER);

		Map<String, Object> map = new HashMap<>();
		map.put("last_login", new Date());
		reg.setMetadata(new Metadata(map));

		Project defaultProject = projectRepository.findByName("superadmin_personal").get();
		Set<ProjectUser> projectUsers = defaultProject.getUsers();

		projectUsers.add(new ProjectUser().withProjectRole(ProjectRole.CUSTOMER).withUser(reg).withProject(defaultProject));
		defaultProject.setUsers(projectUsers);

		userRepository.save(reg);

		final Optional<User> created = userRepository.findByLogin("created");
		assertTrue(created.isPresent());
	}

	@Test
	void findUsernamesWithProjectRolesByProjectIdTest() {

		Map<String, ProjectRole> usernamesWithProjectRoles = userRepository.findUsernamesWithProjectRolesByProjectId(3L);

		assertNotNull(usernamesWithProjectRoles);
		assertFalse(usernamesWithProjectRoles.isEmpty());
		assertEquals(3L, usernamesWithProjectRoles.size());

		usernamesWithProjectRoles.values().forEach(Assertions::assertNotNull);
	}

	private Filter buildDefaultUserFilter() {
		return Filter.builder()
				.withTarget(User.class)
				.withCondition(new FilterCondition(Condition.LOWER_THAN_OR_EQUALS, false, "1000", CRITERIA_ID))
				.build();
	}
}