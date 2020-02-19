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
import com.epam.ta.reportportal.binary.DataStoreService;
import com.epam.ta.reportportal.filesystem.DataEncoder;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.FileInputStream;
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
class CommonDataStoreServiceTest extends BaseTest {

	@Autowired
	@Qualifier("userDataStoreService")
	private DataStoreService dataStoreService;

	@Autowired
	private DataEncoder dataEncoder;

	@Value("${datastore.default.path:/data/store}")
	private String storageRootPath;

	@Test
	void saveTest() throws IOException {
		CommonsMultipartFile multipartFile = getMultipartFile("meh.jpg");
		String fileId = dataStoreService.save(multipartFile.getOriginalFilename(), multipartFile.getInputStream());
		assertNotNull(fileId);
		assertTrue(Files.exists(Paths.get(storageRootPath, dataEncoder.decode(fileId))));
		dataStoreService.delete(fileId);
	}

	@Test
	void saveThumbnailTest() throws IOException {
		CommonsMultipartFile multipartFile = getMultipartFile("meh.jpg");
		String fileId = dataStoreService.saveThumbnail(multipartFile.getOriginalFilename(), multipartFile.getInputStream());
		assertNotNull(fileId);
		assertTrue(Files.exists(Paths.get(storageRootPath, dataEncoder.decode(fileId))));
		dataStoreService.delete(fileId);
	}

	@Test
	void saveAndLoadTest() throws IOException {
		CommonsMultipartFile multipartFile = getMultipartFile("meh.jpg");
		String fileId = dataStoreService.saveThumbnail(multipartFile.getOriginalFilename(), multipartFile.getInputStream());

		Optional<InputStream> content = dataStoreService.load(fileId);

		assertTrue(content.isPresent());
		dataStoreService.delete(fileId);
	}

	@Test
	void saveAndDeleteTest() throws IOException {
		CommonsMultipartFile multipartFile = getMultipartFile("meh.jpg");
		Random random = new Random();
		String fileId = dataStoreService.save(random.nextLong() + "/" + multipartFile.getOriginalFilename(),
				multipartFile.getInputStream()
		);

		dataStoreService.delete(fileId);

		assertFalse(Files.exists(Paths.get(dataEncoder.decode(fileId))));
	}

	public static CommonsMultipartFile getMultipartFile(String path) throws IOException {
		File file = new ClassPathResource(path).getFile();
		FileItem fileItem = new DiskFileItem("mainFile",
				Files.probeContentType(file.toPath()),
				false,
				file.getName(),
				(int) file.length(),
				file.getParentFile()
		);
		IOUtils.copy(new FileInputStream(file), fileItem.getOutputStream());
		return new CommonsMultipartFile(fileItem);
	}
}