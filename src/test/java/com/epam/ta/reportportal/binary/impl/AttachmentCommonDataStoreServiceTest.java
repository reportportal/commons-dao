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
import com.epam.ta.reportportal.binary.AttachmentBinaryDataService;
import com.epam.ta.reportportal.commons.BinaryDataMetaInfo;
import com.epam.ta.reportportal.dao.AttachmentRepository;
import com.epam.ta.reportportal.entity.attachment.Attachment;
import com.epam.ta.reportportal.entity.attachment.AttachmentMetaInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author <a href="mailto:ihar_kahadouski@epam.com">Ihar Kahadouski</a>
 */
class AttachmentCommonDataStoreServiceTest extends BaseTest {

	@Autowired
	private AttachmentBinaryDataService attachmentBinaryDataService;

	@Autowired
	private AttachmentRepository attachmentRepository;

	@Test
	@Sql("/db/fill/data-store/data-store-fill.sql")
	void AttachFileToExistingLogTest() {
		String fileID = "fileID";
		String thumbnailID = "thumbnailID";
		String contentType = "content-type";
		long fileSize = 1024;
		final LocalDateTime creationDate = LocalDateTime.of(2020, Month.JANUARY, 1, 1, 1);

		BinaryDataMetaInfo binaryDataMetaInfo = new BinaryDataMetaInfo();
		binaryDataMetaInfo.setFileId(fileID);
		binaryDataMetaInfo.setThumbnailFileId(thumbnailID);
		binaryDataMetaInfo.setContentType(contentType);
		binaryDataMetaInfo.setFileSize(fileSize);

		Long projectId = 1L;
		Long itemId = 1L;
		AttachmentMetaInfo attachmentMetaInfo = AttachmentMetaInfo.builder()
				.withProjectId(projectId)
				.withLaunchId(1L)
				.withItemId(itemId)
				.withLogId(1L)
				.withCreationDate(creationDate)
				.build();

		attachmentBinaryDataService.attachToLog(binaryDataMetaInfo, attachmentMetaInfo);

		Optional<Attachment> attachment = attachmentRepository.findByFileId(fileID);

		assertTrue(attachment.isPresent());

		assertEquals(projectId, attachment.get().getProjectId());
		assertEquals(itemId, attachment.get().getItemId());
		assertEquals(fileID, attachment.get().getFileId());
		assertEquals(thumbnailID, attachment.get().getThumbnailId());
		assertEquals(contentType, attachment.get().getContentType());
		assertEquals(fileSize, attachment.get().getFileSize());
		assertEquals(creationDate, attachment.get().getCreationDate());
	}
}