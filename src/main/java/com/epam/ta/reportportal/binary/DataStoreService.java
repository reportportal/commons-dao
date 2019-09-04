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
import com.epam.reportportal.commons.Thumbnailator;
import com.epam.ta.reportportal.commons.BinaryDataMetaInfo;
import com.epam.ta.reportportal.commons.ReportPortalUser;
import com.epam.ta.reportportal.dao.AttachmentRepository;
import com.epam.ta.reportportal.entity.Metadata;
import com.epam.ta.reportportal.entity.attachment.Attachment;
import com.epam.ta.reportportal.entity.attachment.AttachmentMetaInfo;
import com.epam.ta.reportportal.entity.attachment.BinaryData;
import com.epam.ta.reportportal.entity.user.User;
import com.epam.ta.reportportal.exception.ReportPortalException;
import com.epam.ta.reportportal.filesystem.DataEncoder;
import com.epam.ta.reportportal.filesystem.DataStore;
import com.epam.ta.reportportal.filesystem.FilePathGenerator;
import com.epam.ta.reportportal.ws.model.ErrorType;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.function.Predicate;

import static com.epam.ta.reportportal.commons.validation.BusinessRule.expect;
import static com.epam.ta.reportportal.commons.validation.Suppliers.formattedSupplier;
import static java.util.Optional.ofNullable;

/**
 * @author Dzianis_Shybeka
 */
@Service
public class DataStoreService {

	private static final Logger LOGGER = LoggerFactory.getLogger(DataStoreService.class);

	private static final String ROOT_USER_PHOTO_DIR = "users";

	private static final String ATTACHMENT_CONTENT_TYPE = "attachmentContentType";

	private static final String THUMBNAIL_PREFIX = "thumbnail-";

	private final DataStore dataStore;

	private final Thumbnailator thumbnailator;

	private final ContentTypeResolver contentTypeResolver;

	private final FilePathGenerator filePathGenerator;

	private final DataEncoder dataEncoder;

	private final CreateLogAttachmentService createLogAttachmentService;

	private final AttachmentRepository attachmentRepository;

	@Autowired
	public DataStoreService(DataStore dataStore, Thumbnailator thumbnailator, ContentTypeResolver contentTypeResolver,
			FilePathGenerator filePathGenerator, DataEncoder dataEncoder, CreateLogAttachmentService createLogAttachmentService,
			AttachmentRepository attachmentRepository) {
		this.dataStore = dataStore;
		this.thumbnailator = thumbnailator;
		this.contentTypeResolver = contentTypeResolver;
		this.filePathGenerator = filePathGenerator;
		this.dataEncoder = dataEncoder;
		this.createLogAttachmentService = createLogAttachmentService;
		this.attachmentRepository = attachmentRepository;
	}

	public Optional<BinaryDataMetaInfo> saveFile(Long projectId, MultipartFile file) {
		Optional<BinaryDataMetaInfo> result = Optional.empty();
		try {
			BinaryData binaryData = getBinaryData(file);

			String commonPath = Paths.get(projectId.toString(), filePathGenerator.generate()).toString();
			Path targetPath = Paths.get(commonPath, file.getOriginalFilename());

			String filePath = dataStore.save(targetPath.toString(), binaryData.getInputStream());

			result = Optional.of(BinaryDataMetaInfo.BinaryDataMetaInfoBuilder.aBinaryDataMetaInfo()
					.withFileId(dataEncoder.encode(filePath))
					.withThumbnailFileId(dataEncoder.encode(saveImageThumbnail(file, commonPath)))
					.withContentType(binaryData.getContentType())
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

			delete(binaryDataMetaInfo.getFileId());
			delete(binaryDataMetaInfo.getThumbnailFileId());
			throw exception;
		}
	}

	public void saveUserPhoto(User user, MultipartFile file) {
		try {
			String fileId = dataStore.save(Paths.get(ROOT_USER_PHOTO_DIR, user.getLogin()).toString(), file.getInputStream());
			user.setAttachment(dataEncoder.encode(fileId));
			ofNullable(user.getMetadata()).orElseGet(() -> new Metadata(Maps.newHashMap()))
					.getMetadata()
					.put(ATTACHMENT_CONTENT_TYPE, file.getContentType());
		} catch (IOException e) {
			LOGGER.error("Unable to save user photo", e);
			throw new ReportPortalException(ErrorType.BINARY_DATA_CANNOT_BE_SAVED, e);
		}
	}

	public BinaryData loadFile(String fileId, ReportPortalUser.ProjectDetails projectDetails) {
		try {
			InputStream data = load(fileId).orElseThrow(() -> new ReportPortalException(ErrorType.UNABLE_TO_LOAD_BINARY_DATA, fileId));
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

	public BinaryData loadUserPhoto(User user) {
		String fileId = ofNullable(user.getAttachment()).orElseThrow(() -> new ReportPortalException(ErrorType.BAD_REQUEST_ERROR,
				formattedSupplier("User - '{}' does not have a photo.", user.getLogin())
		));
		InputStream data = load(fileId).orElseThrow(() -> new ReportPortalException(ErrorType.UNABLE_TO_LOAD_BINARY_DATA, fileId));
		try {
			return new BinaryData((String) user.getMetadata().getMetadata().get(ATTACHMENT_CONTENT_TYPE), (long) data.available(), data);
		} catch (IOException e) {
			LOGGER.error("Unable to load user photo", e);
			throw new ReportPortalException(ErrorType.UNCLASSIFIED_REPORT_PORTAL_ERROR, "Unable to load user photo");
		}
	}

	public void deleteFile(String fileId) {
		if (StringUtils.isNotEmpty(fileId)) {
			delete(fileId);
			attachmentRepository.findByFileId(fileId).ifPresent(attachmentRepository::delete);
		}
	}

	public void deleteUserPhoto(User user) {
		ofNullable(user.getAttachment()).ifPresent(fileId -> {
			delete(fileId);
			user.setAttachment(null);
			user.setAttachmentThumbnail(null);
			ofNullable(user.getMetadata()).ifPresent(metadata -> metadata.getMetadata().remove(ATTACHMENT_CONTENT_TYPE));
		});
	}

	private String saveImageThumbnail(MultipartFile file, String commonPath) {
		String thumbnailFilePath = null;
		if (isImage(file.getContentType())) {
			String fileName = StringUtils.isEmpty(file.getOriginalFilename()) ? file.getName() : file.getOriginalFilename();
			try {
				Path thumbnailTargetPath = Paths.get(commonPath, THUMBNAIL_PREFIX.concat(fileName));
				InputStream thumbnailStream = thumbnailator.createThumbnail(file.getInputStream());
				thumbnailFilePath = dataStore.save(thumbnailTargetPath.toString(), thumbnailStream);
			} catch (IOException e) {
				// do not propogate. Thumbnail is not so critical
				LOGGER.error("Thumbnail is not created for file [{}]. Error:\n{}", fileName, e);
			}
		}
		return thumbnailFilePath;
	}

	private void delete(String fileId) {
		dataStore.delete(dataEncoder.decode(fileId));
	}

	private Optional<InputStream> load(String fileId) {
		return Optional.ofNullable(dataStore.load(dataEncoder.decode(fileId)));
	}

	private BinaryData getBinaryData(MultipartFile file) throws IOException {
		String contentType = isContentTypePresent(file.getContentType()) ?
				file.getContentType() :
				contentTypeResolver.detectContentType(file.getInputStream());
		return new BinaryData(contentType, file.getSize(), file.getInputStream());
	}

	private boolean isContentTypePresent(String contentType) {
		return !Strings.isNullOrEmpty(contentType) && !MediaType.APPLICATION_OCTET_STREAM_VALUE.equals(contentType);
	}

	private boolean isImage(String contentType) {
		return contentType != null && contentType.contains("image");
	}
}