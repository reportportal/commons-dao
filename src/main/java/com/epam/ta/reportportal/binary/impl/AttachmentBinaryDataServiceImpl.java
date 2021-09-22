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
import com.epam.ta.reportportal.binary.AttachmentBinaryDataService;
import com.epam.ta.reportportal.binary.CreateLogAttachmentService;
import com.epam.ta.reportportal.binary.DataStoreService;
import com.epam.ta.reportportal.commons.BinaryDataMetaInfo;
import com.epam.ta.reportportal.commons.ReportPortalUser;
import com.epam.ta.reportportal.dao.AttachmentRepository;
import com.epam.ta.reportportal.entity.attachment.Attachment;
import com.epam.ta.reportportal.entity.attachment.AttachmentMetaInfo;
import com.epam.ta.reportportal.entity.attachment.BinaryData;
import com.epam.ta.reportportal.exception.ReportPortalException;
import com.epam.ta.reportportal.filesystem.FilePathGenerator;
import com.epam.ta.reportportal.ws.model.ErrorType;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.*;
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
public class AttachmentBinaryDataServiceImpl implements AttachmentBinaryDataService {

	private static final Logger LOGGER = LoggerFactory.getLogger(AttachmentBinaryDataServiceImpl.class);

	private final ContentTypeResolver contentTypeResolver;

	private final FilePathGenerator filePathGenerator;

	private final DataStoreService dataStoreService;

	private final AttachmentRepository attachmentRepository;

	private final CreateLogAttachmentService createLogAttachmentService;

	@Autowired
	public AttachmentBinaryDataServiceImpl(ContentTypeResolver contentTypeResolver, FilePathGenerator filePathGenerator,
			@Qualifier("attachmentDataStoreService") DataStoreService dataStoreService, AttachmentRepository attachmentRepository,
			CreateLogAttachmentService createLogAttachmentService) {
		this.contentTypeResolver = contentTypeResolver;
		this.filePathGenerator = filePathGenerator;
		this.dataStoreService = dataStoreService;
		this.attachmentRepository = attachmentRepository;
		this.createLogAttachmentService = createLogAttachmentService;
	}

	@Override
	public Optional<BinaryDataMetaInfo> saveAttachment(AttachmentMetaInfo metaInfo, MultipartFile file) {
		Optional<BinaryDataMetaInfo> result = Optional.empty();
		try (InputStream inputStream = file.getInputStream(); ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
			inputStream.transferTo(outputStream);
			String contentType = resolveContentType(file.getContentType(), outputStream);
			String fileName = resolveFileName(metaInfo, file, contentType);

			String commonPath = filePathGenerator.generate(metaInfo);
			String targetPath = Paths.get(commonPath, fileName).toString();

			String fileId;
			try (ByteArrayInputStream copy = new ByteArrayInputStream(outputStream.toByteArray())) {
				fileId = dataStoreService.save(targetPath, copy);
			}

			result = Optional.of(BinaryDataMetaInfo.BinaryDataMetaInfoBuilder.aBinaryDataMetaInfo()
					.withFileId(fileId)
					.withContentType(contentType)
					.withFileSize(file.getSize())
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

	private String resolveFileName(AttachmentMetaInfo metaInfo, MultipartFile file, String contentType) {
		String extension = resolveExtension(contentType).orElse(resolveExtension(true, file));
		return metaInfo.getLogUuid() + "-" + file.getName() + extension;
	}

	@Override
	public void saveFileAndAttachToLog(MultipartFile file, AttachmentMetaInfo attachmentMetaInfo) {
		saveAttachment(attachmentMetaInfo, file).ifPresent(it -> attachToLog(it, attachmentMetaInfo));
	}

	@Override
	public void attachToLog(BinaryDataMetaInfo binaryDataMetaInfo, AttachmentMetaInfo attachmentMetaInfo) {
		try {
			Attachment attachment = new Attachment();
			attachment.setFileId(binaryDataMetaInfo.getFileId());
			attachment.setThumbnailId(binaryDataMetaInfo.getThumbnailFileId());
			attachment.setContentType(binaryDataMetaInfo.getContentType());
			attachment.setFileSize(binaryDataMetaInfo.getFileSize());

			attachment.setProjectId(attachmentMetaInfo.getProjectId());
			attachment.setLaunchId(attachmentMetaInfo.getLaunchId());
			attachment.setItemId(attachmentMetaInfo.getItemId());
			attachment.setCreationDate(attachmentMetaInfo.getCreationDate());

			createLogAttachmentService.create(attachment, attachmentMetaInfo.getLogId());
		} catch (Exception exception) {
			LOGGER.error("Cannot save log to database, remove files ", exception);

			dataStoreService.delete(binaryDataMetaInfo.getFileId());
			dataStoreService.delete(binaryDataMetaInfo.getThumbnailFileId());
			throw exception;
		}
	}

	@Override
	public BinaryData load(Long fileId, ReportPortalUser.ProjectDetails projectDetails) {
		try {
			Attachment attachment = attachmentRepository.findById(fileId)
					.orElseThrow(() -> new ReportPortalException(ErrorType.ATTACHMENT_NOT_FOUND, fileId));
			InputStream data = dataStoreService.load(attachment.getFileId())
					.orElseThrow(() -> new ReportPortalException(ErrorType.UNABLE_TO_LOAD_BINARY_DATA, fileId));
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
			dataStoreService.delete(fileId);
			attachmentRepository.findByFileId(fileId).ifPresent(attachmentRepository::delete);
		}
	}

	private String resolveContentType(String contentType, ByteArrayOutputStream outputStream) throws IOException {
		if (isContentTypePresent(contentType)) {
			return contentType;
		}
		try (ByteArrayInputStream copy = new ByteArrayInputStream(outputStream.toByteArray())) {
			return contentTypeResolver.detectContentType(copy);
		}
	}
}
