/*
 * This file is part of Report Portal.
 *
 * Report Portal is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Report Portal is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Report Portal.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.epam.ta.reportportal.database.dao;

import com.epam.ta.reportportal.BaseDaoTest;
import com.epam.ta.reportportal.database.BinaryData;
import com.epam.ta.reportportal.database.DataStorage;
import com.epam.ta.reportportal.database.entity.Project;
import com.epam.ta.reportportal.database.entity.ProjectRole;
import com.epam.ta.reportportal.database.entity.UserRoleDetails;
import com.epam.ta.reportportal.database.entity.user.User;
import com.epam.ta.reportportal.database.entity.user.UserType;
import org.apache.commons.lang3.RandomStringUtils;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

import static com.epam.ta.reportportal.database.dao.UserUtils.photoFilename;
import static java.util.Collections.singletonList;

/**
 * @author Andrei Varabyeu
 */
public class UserRepositoryTest extends BaseDaoTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserRepositoryTest.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private DataStorage dataStorage;

	@Autowired
	@Value("classpath:meh.jpg")
	private Resource photoResource;

	@Test
	public void replaceUserTest() throws IOException {
		String username = RandomStringUtils.randomAlphabetic(5);
		User user = new User();
		user.setLogin(username);
		user.setEmail(username + "@epam.com");
		user.setType(UserType.INTERNAL);

		userRepository.save(user);
		BinaryData binaryData = new BinaryData(MediaType.IMAGE_JPEG_VALUE, photoResource.getFile().length(),
				photoResource.getInputStream());
		userRepository.replaceUserPhoto(user.getLogin(), binaryData);

		/* add one more data with such filename */
		dataStorage.saveData(binaryData, photoFilename(username));

		Assert.assertEquals("Incorrect count of files", 2, dataStorage.findByFilename(photoFilename(username)).size());

		/* replace photo again */
		userRepository.replaceUserPhoto(user.getLogin(), binaryData);

		/* make sure duplicates are removed */
		Assert.assertEquals("Incorrect count of files", 1, dataStorage.findByFilename(photoFilename(username)).size());
	}

	@Test
	public void testUsernamesSearch() throws IOException {

		User user1 = new User();
		user1.setLogin("usernameSearchUser");
		user1.setEmail(user1.getLogin() + "@epam.com");
		user1.setType(UserType.INTERNAL);

		User user2 = new User();
		user2.setLogin("usernameSearchUser2");
		user2.setEmail(user2.getLogin() + "@epam.com");
		user2.setType(UserType.INTERNAL);

		userRepository.save(Arrays.asList(user1, user2));

		Page<User> users = userRepository.searchForUserLogin("search", null);
		Assert.assertThat("Incorrect search user name query!", users.getTotalElements(), Matchers.equalTo(2L));

	}


	/* TEMPORALY DISABLE DUE TO FONGO DOES NOT SUPPORT AGGREGATIONS YET
	@Test
	public void testAggregateUserProjects() throws IOException {

		String login = "usernameAggregateTest";

		User user = new User();
		user.setLogin(login);
		user.setEmail(user.getLogin() + "@epam.com");
		user.setType(UserType.INTERNAL);
		userRepository.save(user);

		Project.UserConfig userConfig = new Project.UserConfig();
		userConfig.setProjectRole(ProjectRole.PROJECT_MANAGER);
		userConfig.setProposedRole(ProjectRole.PROJECT_MANAGER);
		userConfig.setLogin(login);



		Project p1 = new Project();
		p1.setName(login + "_project1");
		p1.setUsers(singletonList(userConfig));
		projectRepository.save(p1);

		Project p2 = new Project();
		p2.setName(login + "_project2");
		p2.setUsers(singletonList(userConfig));
		projectRepository.save(p2);

		System.out.println(projectRepository.findAll());
		UserRoleDetails userRoles = userRepository.aggregateUserProjects(login);
		LOGGER.info(userRoles.toString());
		//assert user roles

	}*/

}