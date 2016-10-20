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