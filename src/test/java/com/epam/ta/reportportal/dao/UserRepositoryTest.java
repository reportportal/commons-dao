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

package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.commons.querygen.Condition;
import com.epam.ta.reportportal.commons.querygen.Filter;
import com.epam.ta.reportportal.commons.querygen.FilterCondition;
import com.epam.ta.reportportal.config.TestConfiguration;
import com.epam.ta.reportportal.config.util.SqlRunner;
import com.epam.ta.reportportal.entity.Metadata;
import com.epam.ta.reportportal.entity.project.Project;
import com.epam.ta.reportportal.entity.project.ProjectRole;
import com.epam.ta.reportportal.entity.user.ProjectUser;
import com.epam.ta.reportportal.entity.user.User;
import com.epam.ta.reportportal.entity.user.UserRole;
import com.epam.ta.reportportal.entity.user.UserType;
import org.assertj.core.util.Lists;
import org.assertj.core.util.Sets;
import org.hamcrest.Matchers;
import org.hsqldb.cmdline.SqlToolError;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;

import static com.epam.ta.reportportal.commons.querygen.constant.GeneralCriteriaConstant.CRITERIA_PROJECT_ID;

/**
 * @author Ivan Budaev
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
@Transactional("transactionManager")
public class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private ActivityRepository activityRepository;

	@BeforeClass
	public static void init() throws SQLException, ClassNotFoundException, IOException, SqlToolError {
		Class.forName("org.hsqldb.jdbc.JDBCDriver");
		runSqlScript("/test-dropall-script.sql");
		runSqlScript("/test-create-script.sql");
		runSqlScript("/user/users-projects-fill.sql");
	}

	@AfterClass
	public static void destroy() throws SQLException, IOException, SqlToolError {
		runSqlScript("/test-dropall-script.sql");
	}

	private static void runSqlScript(String scriptPath) throws SQLException, IOException, SqlToolError {
		try (Connection connection = getConnection()) {
			new SqlRunner().runSqlScript(connection, scriptPath);
		}
	}

	private static Connection getConnection() throws SQLException {
		return DriverManager.getConnection("jdbc:postgresql://localhost:5432/reportportal", "rpuser", "rppass");
	}

	@Test
	public void loadUserNameByProject() {
		//given
		long projectId = 3L;
		String term = "2";
		//when
		List<String> userNames = userRepository.findNamesByProject(projectId, term);
		//then
		Assert.assertThat("User names not found", userNames, Matchers.notNullValue());
		Assert.assertThat("Incorrect size of user names", userNames, Matchers.hasSize(1));
		userNames.forEach(name -> Assert.assertThat("Name doesn't contain specified '2' term", name, Matchers.containsString(term)));
	}

	@Test
	public void loadUserNamesByProject() {
		//given
		long projectId = 3L;
		String term = "test";
		//when
		List<String> userNames = userRepository.findNamesByProject(projectId, term);
		//then
		Assert.assertThat("User names not found", userNames, Matchers.notNullValue());
		Assert.assertThat("Incorrect size of user names", userNames, Matchers.hasSize(2));
		userNames.forEach(name -> Assert.assertThat("Name doesn't contain specified 'test' term", name, Matchers.containsString(term)));
	}

	@Test
	public void negativeLoadUserNamesByProject() {
		//given
		long projectId = 3L;
		String term = "negative";
		//when
		List<String> userNames = userRepository.findNamesByProject(projectId, term);
		//then
		Assert.assertThat("Result contains user names", userNames, Matchers.empty());
	}

	@Test
	public void loadUsersByFilterForProject() {
		//given
		Filter filter = buildDefaultUserFilter();
		filter.withCondition(new FilterCondition(Condition.EQUALS, false, "3", CRITERIA_PROJECT_ID));
		//when
		List<User> users = userRepository.findByFilterExcluding(filter, PageRequest.of(0, 5), "email").getContent();
		//then
		Assert.assertThat("Users not found", users, Matchers.notNullValue());
		Assert.assertThat("Incorrect size of founded users", users, Matchers.hasSize(2));
	}

	@Test
	public void findByDefaultProjectId() {
		//given
		long projectId = 1L;
		//when
		Optional<User> user = userRepository.findByDefaultProjectId(projectId);
		//then
		Assert.assertTrue("User is not present", user.isPresent());
		Assert.assertThat("Incorrect default project id", user.get().getDefaultProject().getId(), Matchers.equalTo(projectId));
	}

	@Test
	public void findByEmail() {
		//given
		String email = "testemail@domain.com";
		//when
		Optional<User> user = userRepository.findByEmail(email);
		//then
		Assert.assertTrue("User not found", user.isPresent());
		Assert.assertThat("Emails are not equal", user.get().getEmail(), Matchers.equalTo(email));
	}

	@Test
	public void findByLogin() {
		//given
		String login = "test_user_1";
		//when
		Optional<User> user = userRepository.findByLogin(login);
		//then
		Assert.assertTrue(user.isPresent());
		Assert.assertThat("Emails are not equal", user.get().getEmail(), Matchers.equalTo(login));
	}

	@Test
	public void findAllByEmailIn() {
		//given
		String firstEmail = "testemail@domain.com";
		String secondEmail = "testemail2@domain.com";
		//when
		List<String> emails = Lists.newArrayList(firstEmail, secondEmail);
		List<User> users = userRepository.findAllByEmailIn(emails);
		//then
		Assert.assertThat("Users not found", users, Matchers.notNullValue());
		Assert.assertThat("Incorrect size of users", users, Matchers.hasSize(2));
		Assert.assertTrue("Incorrect user email", users.stream().anyMatch(u -> u.getEmail().equalsIgnoreCase(firstEmail)));
		Assert.assertTrue("Incorrect user email", users.stream().anyMatch(u -> u.getEmail().equalsIgnoreCase(secondEmail)));
	}

	@Test
	public void findAllByLoginIn() {
		//given
		String loginFirst = "test_user_1";
		String loginSecond = "test_user_2";
		//when
		Set<String> loginSet = Sets.newLinkedHashSet(loginFirst, loginSecond);
		List<User> users = userRepository.findAllByLoginIn(loginSet);
		//then
		Assert.assertThat("Users not found", users, Matchers.notNullValue());
		Assert.assertThat("Incorrect size of users", users, Matchers.hasSize(2));
		Assert.assertTrue("Incorrect user login", users.stream().anyMatch(u -> u.getLogin().equalsIgnoreCase(loginFirst)));
		Assert.assertTrue("Incorrect user login", users.stream().anyMatch(u -> u.getLogin().equalsIgnoreCase(loginSecond)));
	}

	@Test
	public void findAllByRole() {

		List<User> users = userRepository.findAllByRole(UserRole.USER);

		Assert.assertEquals(1, users.size());
	}

	@Test
	public void findAllByUserTypeAndExpired() {

		Page<User> users = userRepository.findAllByUserTypeAndExpired(UserType.INTERNAL, false, Pageable.unpaged());

		Assert.assertNotNull(users);
		Assert.assertEquals(2, users.getNumberOfElements());
	}

	@Test
	public void findByLoginTest() {
		String login = "default";
		Optional<User> user = userRepository.findByLogin(login);

		Assert.assertTrue(user.isPresent());
		Assert.assertEquals(login, user.get().getLogin());
	}

	@Test
	public void findByFilterExcludingTest() {
		Page<User> users = userRepository.findByFilterExcluding(buildDefaultUserFilter(), PageRequest.of(0, 5), "email");

		Assert.assertNotNull(users);

		users.forEach(u -> Assert.assertNull(u.getEmail()));
	}

	@Test
	public void expireUsersLoggedOlderThan() {

		userRepository.expireUsersLoggedOlderThan(LocalDateTime.now());

	}

	@Test
	public void saveUserTest() throws JsonProcessingException {
		User user = userRepository.findByLogin("default").get();
		Map<String, Object> hashMap = new HashMap<>();
		hashMap.put("asd", "qwe");

		userRepository.save(user);
	}

	@Test
	public void updateLastLoginDate() {
		LocalDateTime now = LocalDateTime.now();
		userRepository.updateLastLoginDate(now, "superadmin");

	}

	@Test
	public void searchForUserTest() {
/*
		Page<User> users = userRepository.searchForUser("tes", PageRequest.of(0, 5));

		Assert.assertNotNull(users);
		Assert.assertTrue(users.getSize() >= 1);*/
	}

	@Test
	public void removeUserFromProjectTest() {

		User user = userRepository.findByLogin("default").get();

		userRepository.delete(user);
	}

	@Test
	public void createUserTest() {

		Project defaultProject = projectRepository.findByName("superadmin_personal").get();

		User reg = new User();

		reg.setEmail("email1.com");
		reg.setFullName("test");
		reg.setLogin("new1");
		reg.setPassword("new");
		reg.setUserType(UserType.INTERNAL);
		reg.setRole(UserRole.USER);

		Map<String, Object> map = new HashMap<>();
		map.put("last_login", new Date());
		reg.setMetadata(new Metadata(map));

		Set<ProjectUser> projectUsers = defaultProject.getUsers();
		//noinspection ConstantConditions
		projectUsers.add(new ProjectUser().withProjectRole(ProjectRole.CUSTOMER).withUser(reg).withProject(defaultProject));
		defaultProject.setUsers(projectUsers);

		userRepository.save(reg);

		projectRepository.existsByName("superadmin_personal");
	}

	private Filter buildDefaultUserFilter() {
		return Filter.builder()
				.withTarget(User.class)
				.withCondition(new FilterCondition(Condition.LOWER_THAN_OR_EQUALS, false, "10", "id"))
				.build();
	}
}