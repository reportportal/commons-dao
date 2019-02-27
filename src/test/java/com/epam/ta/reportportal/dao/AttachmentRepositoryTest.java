/*
 * Copyright 2018 EPAM Systems
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

package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.BaseTest;
import com.epam.ta.reportportal.entity.attachment.Attachment;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

/**
 * @author <a href="mailto:ivan_budayeu@epam.com">Ivan Budayeu</a>
 */
@Sql("/db/fill/item/items-fill.sql")
class AttachmentRepositoryTest extends BaseTest {

	@Autowired
	private AttachmentRepository attachmentRepository;

	@Test
	void findAllByProjectId() {

		List<Attachment> attachments = attachmentRepository.findAllByProjectId(1L);

		Assertions.assertFalse(attachments.isEmpty());
	}

	@Test
	void findAllByLaunchId() {

		List<Attachment> attachments = attachmentRepository.findAllByLaunchId(1L);

		Assertions.assertFalse(attachments.isEmpty());
	}

	@Test
	void findAllByItemId() {

		List<Attachment> attachments = attachmentRepository.findAllByItemId(3L);

		Assertions.assertFalse(attachments.isEmpty());
	}
}