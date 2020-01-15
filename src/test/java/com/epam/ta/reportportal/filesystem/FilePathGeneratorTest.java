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

package com.epam.ta.reportportal.filesystem;

import com.epam.ta.reportportal.entity.attachment.AttachmentMetaInfo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

class FilePathGeneratorTest {

	private static final String SEPARATOR = "\\" + File.separator;

	@Test
	void generate_different_even_for_same_date() {

		//		given:
		AttachmentMetaInfo metaInfo = AttachmentMetaInfo.builder().withProjectId(1L).withLaunchId(2L).withItemId(3L).withLogId(4L).build();
		//

		//		when:
		String pathOne = new FilePathGenerator().generate(metaInfo);
		String pathTwo = new FilePathGenerator().generate(metaInfo);

		//		then:
		assertNotEquals(pathOne, pathTwo);

		final String regex = "^" + "1" + SEPARATOR + "2" + SEPARATOR + "3" + SEPARATOR + "4" + SEPARATOR + ".*$";

		Assertions.assertThat(pathOne).matches(regex);
		Assertions.assertThat(pathTwo).matches(regex);
	}
}
