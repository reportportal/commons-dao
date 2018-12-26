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

package com.epam.ta.reportportal.dao.suite;

import com.epam.ta.reportportal.BaseTest;
import com.epam.ta.reportportal.dao.IssueGroupRepository;
import com.epam.ta.reportportal.entity.enums.TestItemIssueGroup;
import com.epam.ta.reportportal.entity.item.issue.IssueGroup;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author <a href="mailto:ihar_kahadouski@epam.com">Ihar Kahadouski</a>
 */
public class IssueGroupRepositoryTest extends BaseTest {

	@Autowired
	private IssueGroupRepository repository;

	@Test
	public void findByTestItemIssueGroup() {
		Arrays.stream(TestItemIssueGroup.values()).filter(it -> !it.equals(TestItemIssueGroup.NOT_ISSUE_FLAG)).forEach(it -> {
			final IssueGroup issueGroup = repository.findByTestItemIssueGroup(it);
			assertEquals(it, issueGroup.getTestItemIssueGroup());
			assertNotNull(issueGroup.getId());
		});
	}
}
