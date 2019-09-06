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

import com.epam.reportportal.commons.ContentTypeResolver;
import com.epam.ta.reportportal.binary.AttachmentDataStoreService;
import com.epam.ta.reportportal.binary.CreateLogAttachmentService;
import com.epam.ta.reportportal.commons.BinaryDataMetaInfo;
import com.epam.ta.reportportal.commons.ReportPortalUser;
import com.epam.ta.reportportal.dao.AttachmentRepository;
import com.epam.ta.reportportal.entity.attachment.Attachment;
import com.epam.ta.reportportal.entity.attachment.AttachmentMetaInfo;
import com.epam.ta.reportportal.entity.attachment.BinaryData;
import com.epam.ta.reportportal.exception.ReportPortalException;
import com.epam.ta.reportportal.filesystem.FilePathGenerator;
import com.epam.ta.reportportal.ws.model.ErrorType;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.function.Predicate;

import static com.epam.ta.reportportal.binary.impl.DataStoreUtils.*;
import static com.epam.ta.reportportal.commons.validation.BusinessRule.expect;
import static com.epam.ta.reportportal.commons.validation.Suppliers.formattedSupplier;

/**
 * @author <a href="mailto:ihar_kahadouski@epam.com">Ihar Kahadouski</a>
 */
@Service
public class AttachmentDataStoreServiceImpl implements AttachmentDataStoreService {

	private static final Logger LOGGER = LoggerFactory.getLogger(AttachmentDataStoreServiceImpl.class);

	private final ContentTypeResolver contentTypeResolver;

	private final FilePathGenerator filePathGenerator;

	private final DataStoreServiceImpl dataStoreServiceImpl;

	private final AttachmentRepository attachmentRepository;

	private final CreateLogAttachmentService createLogAttachmentService;

	@Autowired
	public AttachmentDataStoreServiceImpl(ContentTypeResolver contentTypeResolver, FilePathGenerator filePathGenerator,
			DataStoreServiceImpl dataStoreServiceImpl, AttachmentRepository attachmentRepository,
			CreateLogAttachmentService createLogAttachmentService) {
		this.contentTypeResolver = contentTypeResolver;
		this.filePathGenerator = filePathGenerator;
		this.dataStoreServiceImpl = dataStoreServiceImpl;
		this.attachmentRepository = attachmentRepository;
		this.createLogAttachmentService = createLogAttachmentService;
	}

	@Override
	public Optional<BinaryDataMetaInfo> saveAttachment(Long projectId, MultipartFile file) {
		Optional<BinaryDataMetaInfo> result = Optional.empty();
		try {
			String contentType = resolveContentType(file);
			String extension = resolveExtension(contentType).orElse("." + FilenameUtils.getExtension(file.getOriginalFilename()));
			String fileName = file.getName() + extension;

			String commonPath = Paths.get(projectId.toString(), filePathGenerator.generate()).toString();
			String targetPath = Paths.get(commonPath, fileName).toString();

			result = Optional.of(BinaryDataMetaInfo.BinaryDataMetaInfoBuilder.aBinaryDataMetaInfo()
					.withFileId(dataStoreServiceImpl.save(targetPath, file.getInputStream()))
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

	@Override
	public void saveFileAndAttachToLog(MultipartFile file, AttachmentMetaInfo attachmentMetaInfo) {
		saveAttachment(attachmentMetaInfo.getProjectId(), file).ifPresent(it -> attachToLog(it, attachmentMetaInfo));
	}

	@Override
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

			dataStoreServiceImpl.delete(binaryDataMetaInfo.getFileId());
			dataStoreServiceImpl.delete(binaryDataMetaInfo.getThumbnailFileId());
			throw exception;
		}
	}

	@Override
	public BinaryData load(String fileId, ReportPortalUser.ProjectDetails projectDetails) {
		try {
			InputStream data = dataStoreServiceImpl.load(fileId)
					.orElseThrow(() -> new ReportPortalException(ErrorType.UNABLE_TO_LOAD_BINARY_DATA, fileId));
			Attachment attachment = attachmentRepository.findByFileId(fileId)
					.orElseThrow(() -> new ReportPortalException(ErrorType.ATTACHMENT_NOT_FOUND, fileId));
			expect(attachment.getProjectId(), Predicate.isEqual(projectDetails.getProjectId())).verify(ErrorType.ACCESS_DENIED,
					formattedSupplier("You are not assigned to project '{}'", projectDetails.getProjectName())
			);
			return new BinaryData(attachment.getContentType(), (long) data.available(), data);
		} catch (IOException e) {
			LOGGER.error("Unable to load binary data", e);
			throw new ReportPortalException(ErrorType.UNCLASSIFIED_REPORT_PORTAL_ERROR, "Unable to load binary data");
		}
	}

	@Override
	public void delete(String fileId) {
		if (StringUtils.isNotEmpty(fileId)) {
			dataStoreServiceImpl.delete(fileId);
			attachmentRepository.findByFileId(fileId).ifPresent(attachmentRepository::delete);
		}
	}

	private String createThumbnail(MultipartFile file, String fileName, String commonPath) throws IOException {
		String thumbnailId = null;
		if (isImage(file.getContentType())) {
			thumbnailId = dataStoreServiceImpl.saveThumbnail(buildThumbnailFileName(commonPath, fileName), file.getInputStream());
		}
		return thumbnailId;
	}

	private String resolveContentType(MultipartFile file) throws IOException {
		return isContentTypePresent(file.getContentType()) ?
				file.getContentType() :
				contentTypeResolver.detectContentType(file.getInputStream());
	}

}
