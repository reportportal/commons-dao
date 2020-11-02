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

package com.epam.ta.reportportal.binary.impl;

import com.epam.ta.reportportal.BaseTest;
import com.epam.ta.reportportal.exception.ReportPortalException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

	@Value("${datastore.default.path:/data/store}")
	private String storageRootPath;

	private static Random random = new Random();

	@Test
	void saveLoadAndDeleteTest() throws IOException {
		InputStream inputStream = new ClassPathResource("meh.jpg").getInputStream();

		String fileId = userDataStoreService.save(random.nextLong() + "meh.jpg", inputStream);

		Optional<InputStream> loadedData = userDataStoreService.load(fileId);

		assertTrue(loadedData.isPresent());
		assertTrue(Files.exists(Paths.get(storageRootPath, userDataStoreService.dataEncoder.decode(fileId))));

		userDataStoreService.delete(fileId);

		ReportPortalException exception = assertThrows(ReportPortalException.class, () -> userDataStoreService.load(fileId));
		assertEquals("Unable to load binary data by id 'Unable to find file'", exception.getMessage());
		assertFalse(Files.exists(Paths.get(storageRootPath, userDataStoreService.dataEncoder.decode(fileId))));
	}

	@Test
	void saveLoadAndDeleteThumbnailTest() throws IOException {
		InputStream inputStream = new ClassPathResource("meh.jpg").getInputStream();

		String thumbnailId = userDataStoreService.saveThumbnail(random.nextLong() + "thmbnail.jpg", inputStream);

		Optional<InputStream> loadedData = userDataStoreService.load(thumbnailId);

		assertTrue(loadedData.isPresent());
		assertTrue(Files.exists(Paths.get(storageRootPath, userDataStoreService.dataEncoder.decode(thumbnailId))));

		userDataStoreService.delete(thumbnailId);

		ReportPortalException exception = assertThrows(ReportPortalException.class, () -> userDataStoreService.load(thumbnailId));
		assertEquals("Unable to load binary data by id 'Unable to find file'", exception.getMessage());
		assertFalse(Files.exists(Paths.get(storageRootPath, userDataStoreService.dataEncoder.decode(thumbnailId))));
	}
}