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

import com.epam.reportportal.commons.ContentTypeResolver;
import com.epam.ta.reportportal.commons.BinaryDataMetaInfo;
import com.epam.ta.reportportal.entity.attachment.Attachment;
import com.epam.ta.reportportal.entity.attachment.AttachmentMetaInfo;
import com.epam.ta.reportportal.filesystem.FilePathGenerator;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Optional;

import static com.epam.ta.reportportal.binary.DataStoreUtils.*;

/**
 * @author <a href="mailto:ihar_kahadouski@epam.com">Ihar Kahadouski</a>
 */
@Service
public class AttachmentDataStoreService {

	private static final Logger LOGGER = LoggerFactory.getLogger(AttachmentDataStoreService.class);

	private final ContentTypeResolver contentTypeResolver;

	private final FilePathGenerator filePathGenerator;

	private final DataStoreService dataStoreService;

	private final CreateLogAttachmentService createLogAttachmentService;

	@Autowired
	public AttachmentDataStoreService(ContentTypeResolver contentTypeResolver, FilePathGenerator filePathGenerator,
			DataStoreService dataStoreService, CreateLogAttachmentService createLogAttachmentService) {
		this.contentTypeResolver = contentTypeResolver;
		this.filePathGenerator = filePathGenerator;
		this.dataStoreService = dataStoreService;
		this.createLogAttachmentService = createLogAttachmentService;
	}

	public Optional<BinaryDataMetaInfo> saveFile(Long projectId, MultipartFile file) {
		Optional<BinaryDataMetaInfo> result = Optional.empty();
		try {
			String contentType = resolveContentType(file);
			String extension = resolveExtension(contentType).orElse("." + FilenameUtils.getExtension(file.getOriginalFilename()));
			String fileName = file.getName() + extension;

			String commonPath = Paths.get(projectId.toString(), filePathGenerator.generate()).toString();
			String targetPath = Paths.get(commonPath, fileName).toString();

			result = Optional.of(BinaryDataMetaInfo.BinaryDataMetaInfoBuilder.aBinaryDataMetaInfo()
					.withFileId(dataStoreService.save(targetPath, file.getInputStream()))
					.withThumbnailFileId(createThumbnail(file, fileName, commonPath))
					.withContentType(contentType)
					.build());
		} catch (IOException e) {
			LOGGER.error("Unable to save binary data", e);
		} finally {
			if (file instanceof CommonsMultipartFile) {
				((CommonsMultipartFile) file).getFileItem().delete();
			}
		}
		return result;
	}

	public void saveFileAndAttachToLog(MultipartFile file, AttachmentMetaInfo attachmentMetaInfo) {
		saveFile(attachmentMetaInfo.getProjectId(), file).ifPresent(it -> attachToLog(it, attachmentMetaInfo));
	}

	public void attachToLog(BinaryDataMetaInfo binaryDataMetaInfo, AttachmentMetaInfo attachmentMetaInfo) {
		try {
			Attachment attachment = new Attachment();
			attachment.setFileId(binaryDataMetaInfo.getFileId());
			attachment.setThumbnailId(binaryDataMetaInfo.getThumbnailFileId());
			attachment.setContentType(binaryDataMetaInfo.getContentType());
			attachment.setProjectId(attachmentMetaInfo.getProjectId());
			attachment.setLaunchId(attachmentMetaInfo.getLaunchId());
			attachment.setItemId(attachmentMetaInfo.getItemId());

			createLogAttachmentService.create(attachment, attachmentMetaInfo.getLogId());
		} catch (Exception exception) {
			LOGGER.error("Cannot save log to database, remove files ", exception);

			dataStoreService.delete(binaryDataMetaInfo.getFileId());
			dataStoreService.delete(binaryDataMetaInfo.getThumbnailFileId());
			throw exception;
		}
	}

	private String createThumbnail(MultipartFile file, String fileName, String commonPath) throws IOException {
		String thumbnailId = null;
		if (isImage(file.getContentType())) {
			thumbnailId = dataStoreService.saveThumbnail(buildThumbnailFileName(commonPath, fileName), file.getInputStream());
		}
		return thumbnailId;
	}

	private String resolveContentType(MultipartFile file) throws IOException {
		return isContentTypePresent(file.getContentType()) ?
				file.getContentType() :
				contentTypeResolver.detectContentType(file.getInputStream());
	}

}
