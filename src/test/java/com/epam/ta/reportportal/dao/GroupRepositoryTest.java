package com.epam.ta.reportportal.dao;

import static java.time.Instant.now;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.epam.ta.reportportal.BaseTest;
import com.epam.ta.reportportal.entity.group.Group;
import com.epam.ta.reportportal.entity.user.User;
import com.epam.ta.reportportal.entity.user.UserRole;
import com.epam.ta.reportportal.entity.user.UserType;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

class GroupRepositoryTest extends BaseTest {

  @Autowired
  private GroupRepository groupRepository;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private ProjectRepository projectRepository;
  @Autowired
  private GroupUserRepository groupUserRepository;
  @Autowired
  private GroupProjectRepository groupProjectRepository;

  private Group group;
  private User user;

  @BeforeEach
  void setUp() {
    group = new Group();
    group.setSlug("test-group");
    group.setName("Test Group");
    group.setCreatedBy(1L);
    group.setCreatedAt(now());
    groupRepository.save(group);

    assertNotNull(group.getId());

    user = new User();
    user.setUserType(UserType.INTERNAL);
    user.setLogin("test-user");
    user.setEmail("test-user@example.com");
    user.setFullName("Test User");
    user.setPassword("test-password");
    user.setRole(UserRole.USER);
    userRepository.save(user);

    assertNotNull(user.getId());
  }
}