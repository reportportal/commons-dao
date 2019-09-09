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

import com.epam.ta.reportportal.binary.DataStoreService;
import com.epam.ta.reportportal.binary.UserDataStoreService;
import com.epam.ta.reportportal.entity.Metadata;
import com.epam.ta.reportportal.entity.attachment.BinaryData;
import com.epam.ta.reportportal.entity.user.User;
import com.epam.ta.reportportal.exception.ReportPortalException;
import com.epam.ta.reportportal.ws.model.ErrorType;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.Optional;

import static com.epam.ta.reportportal.binary.impl.DataStoreUtils.*;
import static com.epam.ta.reportportal.commons.validation.Suppliers.formattedSupplier;
import static java.util.Optional.ofNullable;

/**
 * @author <a href="mailto:ihar_kahadouski@epam.com">Ihar Kahadouski</a>
 */
@Service
public class UserDataStoreServiceImpl implements UserDataStoreService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserDataStoreServiceImpl.class);

	private DataStoreService dataStoreService;

	@Autowired
	public UserDataStoreServiceImpl(DataStoreService dataStoreService) {
		this.dataStoreService = dataStoreService;
	}

	@Override
	public void saveUserPhoto(User user, MultipartFile file) {
		try {
			saveUserPhoto(user, file.getInputStream(), file.getContentType());
		} catch (IOException e) {
			LOGGER.error("Unable to save user photo", e);
			throw new ReportPortalException(ErrorType.BINARY_DATA_CANNOT_BE_SAVED, e);
		}
	}

	@Override
	public void saveUserPhoto(User user, BinaryData binaryData) {
		saveUserPhoto(user, binaryData.getInputStream(), binaryData.getContentType());
	}

	@Override
	public void saveUserPhoto(User user, InputStream inputStream, String contentType) {
		try {
			byte[] data = StreamUtils.copyToByteArray(inputStream);
			try (InputStream userPhotoCopy = new ByteArrayInputStream(data); InputStream thumbnailCopy = new ByteArrayInputStream(data)) {
				user.setAttachment(dataStoreService.save(Paths.get(ROOT_USER_PHOTO_DIR, user.getLogin()).toString(), userPhotoCopy));
				user.setAttachmentThumbnail(dataStoreService.saveThumbnail(buildThumbnailFileName(ROOT_USER_PHOTO_DIR, user.getLogin()),
						thumbnailCopy
				));
			}
			ofNullable(user.getMetadata()).orElseGet(() -> new Metadata(Maps.newHashMap()))
					.getMetadata()
					.put(ATTACHMENT_CONTENT_TYPE, contentType);
		} catch (IOException e) {
			LOGGER.error("Unable to save user photo", e);
		}
	}

	@Override
	public BinaryData loadUserPhoto(User user, boolean loadThumbnail) {
		String fileId = ofNullable(loadThumbnail ?
				user.getAttachmentThumbnail() :
				user.getAttachment()).orElseThrow(() -> new ReportPortalException(ErrorType.BAD_REQUEST_ERROR,
				formattedSupplier("User - '{}' does not have a photo.", user.getLogin())
		));
		InputStream data = dataStoreService.load(fileId)
				.orElseThrow(() -> new ReportPortalException(ErrorType.UNABLE_TO_LOAD_BINARY_DATA, fileId));
		try {
			return new BinaryData((String) user.getMetadata().getMetadata().get(ATTACHMENT_CONTENT_TYPE), (long) data.available(), data);
		} catch (IOException e) {
			LOGGER.error("Unable to load user photo", e);
			throw new ReportPortalException(ErrorType.UNCLASSIFIED_REPORT_PORTAL_ERROR, "Unable to load user photo");
		}
	}

	@Override
	public void deleteUserPhoto(User user) {
		ofNullable(user.getAttachment()).ifPresent(fileId -> {
			dataStoreService.delete(fileId);
			user.setAttachment(null);
			Optional.ofNullable(user.getAttachmentThumbnail()).ifPresent(thumbnailId -> {
				dataStoreService.delete(thumbnailId);
				user.setAttachmentThumbnail(null);
			});
			ofNullable(user.getMetadata()).ifPresent(metadata -> metadata.getMetadata().remove(ATTACHMENT_CONTENT_TYPE));
		});
	}
}
