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

import com.epam.reportportal.commons.Thumbnailator;
import com.epam.ta.reportportal.BaseTest;
import com.epam.ta.reportportal.binary.UserBinaryDataService;
import com.epam.ta.reportportal.dao.UserRepository;
import com.epam.ta.reportportal.entity.attachment.BinaryData;
import com.epam.ta.reportportal.entity.user.User;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.IOException;

import static com.epam.ta.reportportal.binary.impl.CommonDataStoreServiceTest.getMultipartFile;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="mailto:pavel_bortnik@epam.com">Pavel Bortnik</a>
 */
class UserCommonDataStoreServiceTest extends BaseTest {

	@Autowired
	private UserBinaryDataService userDataStoreService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	@Qualifier("userPhotoThumbnailator")
	private Thumbnailator thumbnailator;

	@Test
	void saveUserPhoto() throws IOException {
		CommonsMultipartFile multipartFile = getMultipartFile("meh.jpg");
		User user = userRepository.findByLogin("default").get();

		userDataStoreService.saveUserPhoto(user, multipartFile);
		user = userRepository.findByLogin("default").get();

		BinaryData binaryData = userDataStoreService.loadUserPhoto(user, false);
		assertThat(IOUtils.contentEquals(multipartFile.getInputStream(), binaryData.getInputStream())).isTrue();

		BinaryData binaryDataThumbnail = userDataStoreService.loadUserPhoto(user, true);
		assertThat(IOUtils.contentEquals(thumbnailator.createThumbnail(multipartFile.getInputStream()),
				binaryDataThumbnail.getInputStream()
		)).isTrue();
	}

}