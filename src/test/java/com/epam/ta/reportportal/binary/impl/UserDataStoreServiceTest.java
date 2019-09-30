package com.epam.ta.reportportal.binary.impl;

import com.epam.ta.reportportal.BaseTest;
import com.epam.ta.reportportal.exception.ReportPortalException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author <a href="mailto:ihar_kahadouski@epam.com">Ihar Kahadouski</a>
 */
class UserDataStoreServiceTest extends BaseTest {

	@Autowired
	private UserDataStoreService userDataStoreService;

	@Test
	void saveLoadAndDeleteTest() throws IOException {
		InputStream inputStream = new ClassPathResource("meh.jpg").getInputStream();

		String fileId = userDataStoreService.save(new Random().nextLong() + "meh.jpg", inputStream);

		Optional<InputStream> loadedData = userDataStoreService.load(fileId);

		assertTrue(loadedData.isPresent());
		assertTrue(Files.exists(Paths.get(userDataStoreService.dataEncoder.decode(fileId))));

		userDataStoreService.delete(fileId);

		ReportPortalException exception = assertThrows(ReportPortalException.class, () -> userDataStoreService.load(fileId));
		assertEquals("Incorrect Request. Unable to find file", exception.getMessage());
		assertFalse(Files.exists(Paths.get(userDataStoreService.dataEncoder.decode(fileId))));
	}

	@Test
	void saveLoadAndDeleteThumbnailTest() throws IOException {
		InputStream inputStream = new ClassPathResource("meh.jpg").getInputStream();

		String thumbnailId = userDataStoreService.saveThumbnail(new Random().nextLong() + "thmbnail.jpg", inputStream);

		Optional<InputStream> loadedData = userDataStoreService.load(thumbnailId);

		assertTrue(loadedData.isPresent());
		assertTrue(Files.exists(Paths.get(userDataStoreService.dataEncoder.decode(thumbnailId))));

		userDataStoreService.delete(thumbnailId);

		ReportPortalException exception = assertThrows(ReportPortalException.class, () -> userDataStoreService.load(thumbnailId));
		assertEquals("Incorrect Request. Unable to find file", exception.getMessage());
		assertFalse(Files.exists(Paths.get(userDataStoreService.dataEncoder.decode(thumbnailId))));
	}
}