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
import com.epam.ta.reportportal.database.entity.user.User;
import com.epam.ta.reportportal.database.entity.user.UserType;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;

import java.io.IOException;

import static com.epam.ta.reportportal.database.dao.UserUtils.photoFilename;

/**
 * @author Andrei Varabyeu
 */
public class UserRepositoryTest extends BaseDaoTest {

	@Autowired
	private UserRepository userRepository;

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

}