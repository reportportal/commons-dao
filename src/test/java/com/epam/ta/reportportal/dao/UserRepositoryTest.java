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

import static com.epam.ta.reportportal.commons.querygen.constant.GeneralCriteriaConstant.CRITERIA_ID;
import static com.epam.ta.reportportal.commons.querygen.constant.GeneralCriteriaConstant.CRITERIA_PROJECT;
import static com.epam.ta.reportportal.commons.querygen.constant.GeneralCriteriaConstant.CRITERIA_PROJECT_ID;
import static com.epam.ta.reportportal.commons.querygen.constant.ProjectCriteriaConstant.CRITERIA_PROJECT_KEY;
import static com.epam.ta.reportportal.commons.querygen.constant.UserCriteriaConstant.CRITERIA_EMAIL;
import static com.epam.ta.reportportal.commons.querygen.constant.UserCriteriaConstant.CRITERIA_FULL_NAME;
import static com.epam.ta.reportportal.commons.querygen.constant.UserCriteriaConstant.CRITERIA_LAST_LOGIN;
import static com.epam.ta.reportportal.commons.querygen.constant.UserCriteriaConstant.CRITERIA_USER;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.epam.ta.reportportal.BaseTest;
import com.epam.ta.reportportal.commons.ReportPortalUser;
import com.epam.ta.reportportal.commons.querygen.CompositeFilterCondition;
import com.epam.ta.reportportal.commons.querygen.Condition;
import com.epam.ta.reportportal.commons.querygen.Filter;
import com.epam.ta.reportportal.commons.querygen.FilterCondition;
import com.epam.ta.reportportal.dao.organization.OrganizationRepositoryCustom;
import com.epam.ta.reportportal.entity.Metadata;
import com.epam.ta.reportportal.entity.organization.OrganizationRole;
import com.epam.ta.reportportal.entity.project.Project;
import com.epam.ta.reportportal.entity.project.ProjectRole;
import com.epam.ta.reportportal.entity.user.ProjectUser;
import com.epam.ta.reportportal.entity.user.User;
import com.epam.ta.reportportal.entity.user.UserRole;
import com.epam.ta.reportportal.entity.user.UserType;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.assertj.core.util.Lists;
import org.assertj.core.util.Sets;
import org.hamcrest.Matchers;
import org.jooq.Operator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.jdbc.Sql;

/**
 * @author Ivan Budaev
 */
@Sql({"/db/fill/user/user-fill.sql"})
class UserRepositoryTest extends BaseTest {

  @Autowired
  private UserRepository userRepository;
  @Autowired
  OrganizationRepositoryCustom organizationRepositoryCustom;
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
    users.forEach(user -> assertThat("Last login should be lower than in the filer",
        Long.parseLong((String) user.getMetadata().getMetadata().get("last_login")),
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
    userNames.forEach(name -> assertThat("Name doesn't contain specified 'admin' term", name,
        Matchers.containsString(term)));
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
    List<User> users = userRepository.findByFilterExcluding(filter, PageRequest.of(0, 5), "email")
        .getContent();
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
  }

  @Test
  void findUserDetailsInfoByLogin() {
    Optional<ReportPortalUser> chubaka = userRepository.findUserDetails("chubaka");
    assertTrue(chubaka.isPresent(), "User not found");
    assertThat(chubaka.get().getUsername(), Matchers.equalTo("chubaka"));
    assertThat(chubaka.get().getUserId(), Matchers.notNullValue());
    assertThat(chubaka.get().getPassword(), Matchers.equalTo("601c4731aeff3b84f76672ad024bb2a0"));
    assertThat(chubaka.get().getEmail(), Matchers.equalTo("chybaka@domain.com"));
    assertThat(chubaka.get().getUserRole(), Matchers.equalTo(UserRole.USER));

    var orgDetails = chubaka.get().getOrganizationDetails().get("Test organization");
    assertNotNull(orgDetails);

    var projectDetails = orgDetails.getProjectDetails().get("millennium_falcon");
    assertThat(projectDetails.getProjectId(), Matchers.equalTo(3L));
    assertThat(projectDetails.getProjectKey(), Matchers.equalTo("millennium_falcon"));
    assertThat(projectDetails.getProjectRole(), Matchers.equalTo(ProjectRole.VIEWER));
  }

  @Test
  void shouldFindReportPortalUserByLogin() {
    Optional<ReportPortalUser> chubaka = userRepository.findReportPortalUser("chubaka");
    assertTrue(chubaka.isPresent(), "User not found");
    assertThat(chubaka.get().getUsername(), Matchers.equalTo("chubaka"));
    assertThat(chubaka.get().getUserId(), Matchers.notNullValue());
    assertThat(chubaka.get().getPassword(), Matchers.equalTo("601c4731aeff3b84f76672ad024bb2a0"));
    assertThat(chubaka.get().getEmail(), Matchers.equalTo("chybaka@domain.com"));
    assertThat(chubaka.get().getUserRole(), Matchers.equalTo(UserRole.USER));
  }

  @Test
  void shouldNotFindReportPortalUserByLoginWhenNotExists() {
    Optional<ReportPortalUser> user = userRepository.findReportPortalUser("not existing user");
    assertFalse(user.isPresent(), "User found");
  }

  @Test
  void findByLogin() {
    final String login = "han_solo";

    Optional<User> user = userRepository.findByLogin(login);

    assertTrue(user.isPresent(), "User not found");
    assertThat("Emails are not equal", user.get().getLogin(), Matchers.equalTo(login));
  }

  @Test
  void findByUuid() {
    final UUID uuid = userRepository.findByLogin("han_solo")
        .map(User::getUuid)
        .orElseThrow(() -> new IllegalStateException("User not found"));

    Optional<User> user = userRepository.findByUuid(uuid);

    assertTrue(user.isPresent(), "User not found");
    assertThat("UUIDs are not equal", user.get().getUuid(), Matchers.equalTo(uuid));
  }

  @Test
  void findByExternalId() {
    final String externalId = "external_id_1";

    Optional<User> user = userRepository.findByExternalId(externalId);

    assertTrue(user.isPresent(), "User not found");
    assertThat("External IDs are not equal", user.get().getExternalId(), Matchers.equalTo(externalId));
  }

  @Test
  void findAllByEmailIn() {
    List<String> emails = Arrays.asList("han_solo@domain.com", "chybaka@domain.com");

    List<User> users = userRepository.findAllByEmailIn(emails);

    assertThat("Users not found", users, Matchers.notNullValue());
    assertThat("Incorrect size of users", users, Matchers.hasSize(2));
    assertTrue(users.stream().anyMatch(u -> u.getEmail().equalsIgnoreCase(emails.get(0))),
        "Incorrect user email");
    assertTrue(users.stream().anyMatch(u -> u.getEmail().equalsIgnoreCase(emails.get(1))),
        "Incorrect user email");
  }

  @Test
  void findAllByLoginIn() {
    final String hanLogin = "han_solo";
    final String defaultLogin = "default@reportportal.internal";
    Set<String> logins = Sets.newHashSet(Arrays.asList(hanLogin, defaultLogin));

    List<User> users = userRepository.findAllByLoginIn(logins);

    assertThat("Users not found", users, Matchers.notNullValue());
    assertThat("Incorrect size of users", users, Matchers.hasSize(2));
    assertTrue(users.stream().anyMatch(u -> u.getLogin().equalsIgnoreCase(hanLogin)),
        "Incorrect user login");
    assertTrue(users.stream().anyMatch(u -> u.getLogin().equalsIgnoreCase(defaultLogin)),
        "Incorrect user login");
  }

  @Test
  void findAllByRole() {
    List<User> users = userRepository.findAllByRole(UserRole.USER);

    assertEquals(4, users.size());
    users.forEach(it -> assertEquals(UserRole.USER, it.getRole()));
  }

  @Test
  void findAllByUserTypeAndExpired() {
    Page<User> users = userRepository.findAllByUserTypeAndExpired(UserType.INTERNAL, false,
        Pageable.unpaged());

    assertNotNull(users);
    assertEquals(6, users.getNumberOfElements());
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

  @Test
  void findByFilterExcludingProjects() {
    final CompositeFilterCondition userCondition = new CompositeFilterCondition(
        List.of(new FilterCondition(Operator.OR,
                Condition.CONTAINS,
                false,
                "ch",
                CRITERIA_USER
            ),
            new FilterCondition(Operator.OR, Condition.CONTAINS, false, "ch", CRITERIA_FULL_NAME),
            new FilterCondition(Operator.OR, Condition.CONTAINS, false, "ch", CRITERIA_EMAIL)
        ), Operator.AND);

    Filter filter = Filter.builder()
        .withTarget(User.class)
        .withCondition(userCondition)
        .withCondition(new FilterCondition(Operator.AND, Condition.ANY, true, "superadmin_personal",
            CRITERIA_PROJECT))
        .build();

    Page<User> users = userRepository.findByFilterExcludingProjects(filter, PageRequest.of(0, 5));
    assertEquals(3, users.getTotalElements());
  }

  @Test
  void shouldNotFindByFilterExcludingProjects() {
    final CompositeFilterCondition userCondition = new CompositeFilterCondition(
        List.of(new FilterCondition(Operator.OR,
                Condition.CONTAINS,
                false,
                "ch",
                CRITERIA_USER
            ),
            new FilterCondition(Operator.OR, Condition.CONTAINS, false, "ch", CRITERIA_FULL_NAME),
            new FilterCondition(Operator.OR, Condition.CONTAINS, false, "ch", CRITERIA_EMAIL)
        ), Operator.AND);

    Filter filter = Filter.builder()
        .withTarget(User.class)
        .withCondition(userCondition)
        .withCondition(new FilterCondition(Operator.AND, Condition.ANY, true, "millennium_falcon",
            CRITERIA_PROJECT))
        .build();

    Page<User> users = userRepository.findByFilterExcludingProjects(filter, PageRequest.of(0, 5));
    assertEquals(1, users.getTotalElements());
  }

  @Test
  void shouldFindRawById() {
    final Optional<User> user = userRepository.findRawById(1L);
    assertTrue(user.isPresent());
    assertEquals(1L, user.get().getId());
    assertEquals("admin@reportportal.internal", user.get().getLogin());
    assertTrue(user.get().getProjects().isEmpty());
  }

  @Test
  void shouldNotFindRawById() {
    final Optional<User> user = userRepository.findRawById(123L);
    assertTrue(user.isEmpty());
  }

  @SuppressWarnings("OptionalGetWithoutIsPresent")
  @Test
  void createUserTest() {
    User reg = new User();

    reg.setEmail("email.com");
    reg.setUuid(UUID.randomUUID());
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

    projectUsers.add(new ProjectUser().withProjectRole(ProjectRole.EDITOR).withUser(reg)
        .withProject(defaultProject));
    defaultProject.setUsers(projectUsers);

    userRepository.save(reg);

    final Optional<User> created = userRepository.findByLogin("created");
    assertTrue(created.isPresent());
  }

  @Test
  void findUsernamesWithProjectRolesByProjectIdTest() {

    Map<String, ProjectRole> usernamesWithProjectRoles = userRepository.findUsernamesWithProjectRolesByProjectId(
        3L);

    assertNotNull(usernamesWithProjectRoles);
    assertFalse(usernamesWithProjectRoles.isEmpty());
    assertEquals(3L, usernamesWithProjectRoles.size());

    usernamesWithProjectRoles.values().forEach(Assertions::assertNotNull);
  }

  @Test
  void findAllMembersByProjectManagerRole() {
    List<String> emails = userRepository.findEmailsByProjectAndRole(1L, ProjectRole.EDITOR);

    assertFalse(emails.isEmpty());

    emails.forEach(e -> {
      User user = userRepository.findByEmail(e).get();
      assertEquals(ProjectRole.EDITOR,
          user.getProjects()
              .stream()
              .filter(it -> it.getId().getProjectId().equals(1L))
              .map(ProjectUser::getProjectRole)
              .findFirst()
              .get()
      );
    });
  }

  @Test
  void findAllMembersByViewerRole() {
    List<String> emails = userRepository.findEmailsByProjectAndRole(1L, ProjectRole.VIEWER);

    assertTrue(emails.isEmpty());
  }

  @Test
  void findAllMembersByProject() {
    List<String> emails = userRepository.findEmailsByProject(1L);

    assertFalse(emails.isEmpty());
    assertEquals(1, emails.size());
  }

  @Test
  void findEmailsByOrganization() {
    List<String> emails = userRepository.findEmailsByOrganization(1L);

    assertFalse(emails.isEmpty());
    assertEquals(2, emails.size());
  }

  @Test
  void findEmailsByOrganizationAndRole() {
    List<String> emails = userRepository.findEmailsByOrganizationAndRole(1L, OrganizationRole.MEMBER);

    assertFalse(emails.isEmpty());
    assertEquals(2, emails.size());
  }


  @Test
  void findProjectUsers() {
    PageRequest pageRequest = PageRequest.of(0, 300);
    Filter filter = new Filter(User.class, Lists.newArrayList());
    filter.withCondition(
        new FilterCondition(Condition.EQUALS, false, "millennium_falcon", CRITERIA_PROJECT_KEY));
    Page<User> result = userRepository.findProjectUsersByFilterExcluding("millennium_falcon",
        filter, pageRequest, "email");
    assertEquals(3, result.getTotalElements());

    result.getContent().forEach(user -> assertFalse(user.getOrganizationUsers().isEmpty()));

    result.getContent().stream().flatMap(user -> user.getOrganizationUsers().stream())
        .forEach(orgUser -> {
          assertNotNull(orgUser.getOrganizationRole());
        });

    result.getContent().forEach(user -> assertNull(user.getEmail()));
  }


  private Filter buildDefaultUserFilter() {
    return Filter.builder()
        .withTarget(User.class)
        .withCondition(
            new FilterCondition(Condition.LOWER_THAN_OR_EQUALS, false, "1000", CRITERIA_ID))
        .build();
  }
}
