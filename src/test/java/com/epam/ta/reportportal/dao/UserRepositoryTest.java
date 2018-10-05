package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.commons.querygen.Condition;
import com.epam.ta.reportportal.commons.querygen.Filter;
import com.epam.ta.reportportal.commons.querygen.FilterCondition;
import com.epam.ta.reportportal.config.TestConfiguration;
import com.epam.ta.reportportal.config.util.SqlRunner;
import com.epam.ta.reportportal.entity.Activity;
import com.epam.ta.reportportal.entity.JsonbObject;
import com.epam.ta.reportportal.entity.meta.MetaData;
import com.epam.ta.reportportal.entity.user.User;
import com.epam.ta.reportportal.entity.user.UserRole;
import com.epam.ta.reportportal.entity.user.UserType;
import org.assertj.core.util.Lists;
import org.assertj.core.util.Sets;
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
import org.springframework.test.annotation.Rollback;
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
	ActivityRepository activityRepository;

	@BeforeClass
	public static void init() throws SQLException, ClassNotFoundException, IOException, SqlToolError {
		Class.forName("org.hsqldb.jdbc.JDBCDriver");
		runSqlScript("/test-dropall-script.sql");
		runSqlScript("/test-create-script.sql");
		runSqlScript("/test-fill-script.sql");
	}

	@AfterClass
	public static void destroy() throws SQLException, IOException, SqlToolError {
		//		runSqlScript("/test-dropall-script.sql");
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
	public void findByDefaultProjectId() {

		Optional<User> user = userRepository.findByDefaultProjectId(2L);

		Assert.assertTrue(user.isPresent());

	}

	@Test
	public void findByEmail() {

		Optional<User> user = userRepository.findByEmail("defaultemail@domain.com");

		Assert.assertTrue(user.isPresent());
	}

	@Test
	public void findByLogin() {

		Optional<User> user = userRepository.findByLogin("default");

		Assert.assertTrue(user.isPresent());
	}

	@Test
	public void findAllByEmailIn() {

		List<String> emails = Lists.newArrayList("defaultemail@domain.com", "superadminemail@domain.com");

		List<User> users = userRepository.findAllByEmailIn(emails);

		Assert.assertNotNull(users);
		Assert.assertEquals(2, users.size());

	}

	@Test
	public void findAllByLoginIn() {

		Set<String> loginSet = Sets.newLinkedHashSet("default", "superadmin", "test");

		List<User> users = userRepository.findAllByLoginIn(loginSet);

		Assert.assertNotNull(users);
		Assert.assertEquals(2, users.size());
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

	@Rollback(false)
	@Test
	public void expireUsersLoggedOlderThan() {

		userRepository.expireUsersLoggedOlderThan(LocalDateTime.now());

	}

	@Rollback(false)
	@Test
	public void saveUserTest() throws JsonProcessingException {
		User user = userRepository.findByLogin("default").get();
		Map<String, Object> hashMap = new HashMap<>();
		hashMap.put("asd", "qwe");
		JsonbObject metaData = new MetaData(hashMap);
		user.setMetadata(metaData);

		userRepository.save(user);
	}

	@Rollback(false)
	@Test
	public void updateLastLoginDate() {
		LocalDateTime now = LocalDateTime.now();
		userRepository.updateLastLoginDate(now, "superadmin");

	}

	@Test
	public void searchForUserTest() {

		Page<User> users = userRepository.searchForUser("tes", PageRequest.of(0, 5));

		Assert.assertNotNull(users);
		Assert.assertTrue(users.getSize() >= 1);
	}

	@Rollback(false)
	@Test
	public void removeUserFromProjectTest() {

		User user = userRepository.findByLogin("default").get();

		userRepository.delete(user);
	}

	@Rollback(false)
	@Test
	public void test() {
		Activity activity = new Activity();
		activity.setProjectId(1L);
		activity.setUserId(1L);
		activity.setAction("asd");
		activity.setEntity(Activity.Entity.LAUNCH);
		activity.setCreatedAt(LocalDateTime.now());
		Map<String, Object> hashMap = new HashMap<>();
		hashMap.put("asd", "qwe");
		MetaData metaData = new MetaData(hashMap);
		activity.setDetails(metaData);

		activityRepository.save(activity);
	}

	private Filter buildDefaultUserFilter() {
		return Filter.builder()
				.withTarget(User.class)
				.withCondition(new FilterCondition(Condition.LOWER_THAN_OR_EQUALS, false, "10", "id"))
				.build();
	}
}