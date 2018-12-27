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

package com.epam.ta.reportportal.dao.suite;

import com.epam.ta.reportportal.BaseTest;
import com.epam.ta.reportportal.commons.querygen.Condition;
import com.epam.ta.reportportal.commons.querygen.Filter;
import com.epam.ta.reportportal.commons.querygen.FilterCondition;
import com.epam.ta.reportportal.dao.ProjectRepository;
import com.epam.ta.reportportal.dao.UserRepository;
import com.epam.ta.reportportal.entity.Metadata;
import com.epam.ta.reportportal.entity.project.Project;
import com.epam.ta.reportportal.entity.project.ProjectRole;
import com.epam.ta.reportportal.entity.user.ProjectUser;
import com.epam.ta.reportportal.entity.user.User;
import com.epam.ta.reportportal.entity.user.UserRole;
import com.epam.ta.reportportal.entity.user.UserType;
import org.assertj.core.util.Sets;
import org.flywaydb.test.annotation.FlywayTest;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import static com.epam.ta.reportportal.commons.querygen.constant.GeneralCriteriaConstant.*;
import static com.epam.ta.reportportal.commons.querygen.constant.UserCriteriaConstant.CRITERIA_LAST_LOGIN;
import static com.epam.ta.reportportal.commons.querygen.constant.UserCriteriaConstant.CRITERIA_USER;
import static org.junit.Assert.*;

/**
 * @author Ivan Budaev
 */
public class UserRepositoryTest extends BaseTest {

	private static final String FILL_SCRIPT_PATH = "/db/fill/user";

	private static final Long SUPERADMIN_PERSONAL_ID = 1L;
	private static final String SUPERADMIN_PERSONAL_NAME = "superadmin_personal";
	private static final String DEFAULT_LOGIN = "default";

	private static final Long FALCON_PROJECT_ID = 3L;
	private static final String CHUBAKA_LOGIN = "chubaka";
	private static final String CHUBAKA_EMAIL = "chybaka@domain.com";
	private static final String FAKE_CHUBAKA_LOGIN = "fake_chubaka";
	private static final String HAN_SOLO_LOGIN = "han_solo";
	private static final String HAN_SOLO_EMAIL = "han_solo@domain.com";

	@FlywayTest(locationsForMigrate = { FILL_SCRIPT_PATH }, invokeCleanDB = false)
	@BeforeClass
	public static void setUp() {
	}

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ProjectRepository projectRepository;

	@Test
	public void loadUserByLastLogin() {
		//given
		long now = new Date().getTime();
		Filter filter = Filter.builder()
				.withTarget(User.class)
				.withCondition(new FilterCondition(Condition.LOWER_THAN, false, String.valueOf(now), CRITERIA_LAST_LOGIN))
				.build();
		//when
		List<User> users = userRepository.findByFilter(filter);
		//then
		Assert.assertThat("Users should exist", users.size(), Matchers.greaterThan(0));
		users.forEach(user -> Assert.assertThat(
				"Last login should be lower than in the filer",
				LocalDateTime.parse((String) user.getMetadata().getMetadata().get("last_login"))
						.atZone(ZoneId.systemDefault())
						.toInstant()
						.toEpochMilli(),
				Matchers.lessThan(now)
		));
	}

	@Test
	public void loadUserNameByProject() {
		//given
		String term = "admin";
		//when
		List<String> userNames = userRepository.findNamesByProject(SUPERADMIN_PERSONAL_ID, term);
		//then
		Assert.assertThat("User names not found", userNames, Matchers.notNullValue());
		Assert.assertThat("Incorrect size of user names", userNames, Matchers.hasSize(1));
		userNames.forEach(name -> Assert.assertThat("Name doesn't contain specified 'def' term", name, Matchers.containsString(term)));
	}

	@Test
	public void negativeLoadUserNamesByProject() {
		//given
		String term = "negative";
		//when
		List<String> userNames = userRepository.findNamesByProject(SUPERADMIN_PERSONAL_ID, term);
		//then
		Assert.assertThat("Result contains user names", userNames, Matchers.empty());
	}

	@Test
	public void loadUsersByFilterForProject() {
		//given
		Filter filter = buildDefaultUserFilter();
		filter.withCondition(new FilterCondition(Condition.EQUALS, false, String.valueOf(FALCON_PROJECT_ID), CRITERIA_PROJECT_ID));
		//when
		List<User> users = userRepository.findByFilterExcluding(filter, PageRequest.of(0, 5), "email").getContent();
		//then
		Assert.assertThat("Users not found", users, Matchers.notNullValue());
		Assert.assertThat("Incorrect size of founded users", users, Matchers.hasSize(3));
		users.forEach(it -> assertNull(it.getEmail()));
	}

	@Test
	public void findByDefaultProjectId() {
		Optional<User> user = userRepository.findByDefaultProjectId(SUPERADMIN_PERSONAL_ID);
		Assert.assertTrue("User is not present", user.isPresent());
		Assert.assertThat("Incorrect default project id", user.get().getDefaultProject().getId(), Matchers.equalTo(SUPERADMIN_PERSONAL_ID));
	}

	@Test
	public void findByEmail() {
		Optional<User> user = userRepository.findByEmail(CHUBAKA_EMAIL);
		Assert.assertTrue("User not found", user.isPresent());
		Assert.assertThat("Emails are not equal", user.get().getEmail(), Matchers.equalTo(CHUBAKA_EMAIL));
	}

	@Test
	public void findByLogin() {
		Optional<User> user = userRepository.findByLogin(HAN_SOLO_LOGIN);
		Assert.assertTrue("User not found", user.isPresent());
		Assert.assertThat("Emails are not equal", user.get().getLogin(), Matchers.equalTo(HAN_SOLO_LOGIN));
	}

	@Test
	public void findAllByEmailIn() {
		List<User> users = userRepository.findAllByEmailIn(Arrays.asList(HAN_SOLO_EMAIL, CHUBAKA_EMAIL));
		Assert.assertThat("Users not found", users, Matchers.notNullValue());
		Assert.assertThat("Incorrect size of users", users, Matchers.hasSize(2));
		Assert.assertTrue("Incorrect user email", users.stream().anyMatch(u -> u.getEmail().equalsIgnoreCase(HAN_SOLO_EMAIL)));
		Assert.assertTrue("Incorrect user email", users.stream().anyMatch(u -> u.getEmail().equalsIgnoreCase(CHUBAKA_EMAIL)));
	}

	@Test
	public void findAllByLoginIn() {
		List<User> users = userRepository.findAllByLoginIn(Sets.newHashSet(Arrays.asList(CHUBAKA_LOGIN, DEFAULT_LOGIN)));
		Assert.assertThat("Users not found", users, Matchers.notNullValue());
		Assert.assertThat("Incorrect size of users", users, Matchers.hasSize(2));
		Assert.assertTrue("Incorrect user login", users.stream().anyMatch(u -> u.getLogin().equalsIgnoreCase(CHUBAKA_LOGIN)));
		Assert.assertTrue("Incorrect user login", users.stream().anyMatch(u -> u.getLogin().equalsIgnoreCase(DEFAULT_LOGIN)));
	}

	@Test
	public void findAllByRole() {
		List<User> users = userRepository.findAllByRole(UserRole.USER);
		Assert.assertEquals(3, users.size());
		users.forEach(it -> assertEquals(UserRole.USER, it.getRole()));
	}

	@Test
	public void findAllByUserTypeAndExpired() {
		Page<User> users = userRepository.findAllByUserTypeAndExpired(UserType.INTERNAL, false, Pageable.unpaged());
		Assert.assertNotNull(users);
		Assert.assertEquals(5, users.getNumberOfElements());
	}

	@Test
	public void searchForUserTest() {
		Filter filter = Filter.builder()
				.withTarget(User.class).withCondition(new FilterCondition(Condition.CONTAINS, false, "chuba", CRITERIA_USER))
				.build();
		Page<User> users = userRepository.findByFilter(filter, PageRequest.of(0, 5));
		Assert.assertEquals(2, users.getTotalElements());
	}

	@Test
	public void usersWithProjectSort() {
		Filter filter = Filter.builder()
				.withTarget(User.class).withCondition(new FilterCondition(Condition.CONTAINS, false, "chuba", CRITERIA_USER))
				.build();
		PageRequest pageRequest = PageRequest.of(0, 5, Sort.Direction.ASC, CRITERIA_PROJECT);
		Page<User> result = userRepository.findByFilter(filter, pageRequest);
		Assert.assertEquals(2, result.getTotalElements());
	}

	@SuppressWarnings("OptionalGetWithoutIsPresent")
	@Test
	public void createUserTest() {
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

		Project defaultProject = projectRepository.findByName(SUPERADMIN_PERSONAL_NAME).get();
		Set<ProjectUser> projectUsers = defaultProject.getUsers();

		projectUsers.add(new ProjectUser().withProjectRole(ProjectRole.CUSTOMER).withUser(reg).withProject(defaultProject));
		defaultProject.setUsers(projectUsers);

		userRepository.save(reg);

		final Optional<User> created = userRepository.findByLogin("created");
		assertTrue(created.isPresent());
	}

	private Filter buildDefaultUserFilter() {
		return Filter.builder()
				.withTarget(User.class).withCondition(new FilterCondition(Condition.LOWER_THAN_OR_EQUALS, false, "10", CRITERIA_ID))
				.build();
	}
}