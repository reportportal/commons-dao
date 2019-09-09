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

import com.epam.ta.reportportal.binary.impl.DataStoreUtils;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.ByteArrayInputStream;

import static com.epam.ta.reportportal.binary.DataStoreServiceImplTest.getMultipartFile;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="mailto:pavel_bortnik@epam.com">Pavel Bortnik</a>
 */
class DataStoreUtilsTest {

	@Test
	void convertToBytes() throws Exception {
		CommonsMultipartFile multipartFile = getMultipartFile("meh.jpg");
		byte[] bytes = DataStoreUtils.convertToBytes(multipartFile.getInputStream());
		assertThat(IOUtils.contentEquals(multipartFile.getInputStream(), new ByteArrayInputStream(bytes))).isTrue();
	}
}