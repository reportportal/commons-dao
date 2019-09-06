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

package com.epam.ta.reportportal.binary;

import com.epam.ta.reportportal.BaseTest;
import com.epam.ta.reportportal.dao.AttachmentRepository;
import com.epam.ta.reportportal.dao.UserRepository;
import com.epam.ta.reportportal.filesystem.DataEncoder;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author <a href="mailto:ihar_kahadouski@epam.com">Ihar Kahadouski</a>
 */
class DataStoreServiceTest extends BaseTest {

	@Autowired
	private DataStoreService dataStoreService;

	@Autowired
	private DataEncoder dataEncoder;

	@Autowired
	private AttachmentRepository attachmentRepository;

	@Autowired
	private UserRepository userRepository;

	@Test
	void saveImageWithThumbnailTest() throws IOException {
		CommonsMultipartFile multipartFile = getMultipartFile("meh.jpg");
		String fileId = dataStoreService.save(multipartFile.getOriginalFilename(), multipartFile.getInputStream());
		assertNotNull(fileId);
		assertTrue(Files.exists(Paths.get(dataEncoder.decode(fileId))));
	}

	/*@Test
	@Sql("/db/fill/data-store/data-store-fill.sql")
	void attachFileToExistLogTest() {
		String fileID = "fileID";
		String thumbnailID = "thumbnailID";
		String contentType = "content-type";

		BinaryDataMetaInfo binaryDataMetaInfo = new BinaryDataMetaInfo();
		binaryDataMetaInfo.setFileId(fileID);
		binaryDataMetaInfo.setThumbnailFileId(thumbnailID);
		binaryDataMetaInfo.setContentType(contentType);

		Long projectId = 1L;
		Long itemId = 1L;
		AttachmentMetaInfo attachmentMetaInfo = AttachmentMetaInfo.builder()
				.withProjectId(projectId)
				.withLaunchId(1L)
				.withItemId(itemId)
				.withLogId(1L)
				.build();

		dataStoreService.attachToLog(binaryDataMetaInfo, attachmentMetaInfo);

		Optional<Attachment> attachment = attachmentRepository.findByFileId(fileID);

		assertTrue(attachment.isPresent());

		assertEquals(projectId, attachment.get().getProjectId());
		assertEquals(itemId, attachment.get().getItemId());
		assertEquals(fileID, attachment.get().getFileId());
		assertEquals(thumbnailID, attachment.get().getThumbnailId());
		assertEquals(contentType, attachment.get().getContentType());
	}

	@Test
	void saveUserPhotoTest() throws IOException {
		User user = userRepository.findByLogin("superadmin").get();

		dataStoreService.saveUserPhoto(user, getMultipartFile("meh.jpg"));

		assertNotNull(user.getAttachment());
		assertTrue(Files.exists(Paths.get(dataEncoder.decode(user.getAttachment()))));
		assertEquals(IMAGE_JPEG_VALUE, (String) user.getMetadata().getMetadata().get("attachmentContentType"));
	}*/

	private static CommonsMultipartFile getMultipartFile(String path) throws IOException {
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