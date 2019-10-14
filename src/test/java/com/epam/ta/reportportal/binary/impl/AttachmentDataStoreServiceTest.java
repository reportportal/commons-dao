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
class AttachmentDataStoreServiceTest extends BaseTest {

	@Autowired
	private AttachmentDataStoreService attachmentDataStoreService;

	@Test
	void saveLoadAndDeleteTest() throws IOException {
		InputStream inputStream = new ClassPathResource("meh.jpg").getInputStream();

		String fileId = attachmentDataStoreService.save(new Random().nextLong() + "meh.jpg", inputStream);

		Optional<InputStream> loadedData = attachmentDataStoreService.load(fileId);

		assertTrue(loadedData.isPresent());
		assertTrue(Files.exists(Paths.get(attachmentDataStoreService.dataEncoder.decode(fileId))));

		attachmentDataStoreService.delete(fileId);

		ReportPortalException exception = assertThrows(ReportPortalException.class, () -> attachmentDataStoreService.load(fileId));
		assertEquals("Incorrect Request. Unable to find file", exception.getMessage());
		assertFalse(Files.exists(Paths.get(attachmentDataStoreService.dataEncoder.decode(fileId))));
	}

	@Test
	void saveLoadAndDeleteThumbnailTest() throws IOException {
		InputStream inputStream = new ClassPathResource("meh.jpg").getInputStream();

		String thumbnailId = attachmentDataStoreService.saveThumbnail(new Random().nextLong() + "thumbnail.jpg", inputStream);

		Optional<InputStream> loadedData = attachmentDataStoreService.load(thumbnailId);

		assertTrue(loadedData.isPresent());
		assertTrue(Files.exists(Paths.get(attachmentDataStoreService.dataEncoder.decode(thumbnailId))));

		attachmentDataStoreService.delete(thumbnailId);

		ReportPortalException exception = assertThrows(ReportPortalException.class, () -> attachmentDataStoreService.load(thumbnailId));
		assertEquals("Incorrect Request. Unable to find file", exception.getMessage());
		assertFalse(Files.exists(Paths.get(attachmentDataStoreService.dataEncoder.decode(thumbnailId))));
	}
}