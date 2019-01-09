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
import com.epam.ta.reportportal.dao.IssueEntityRepository;
import com.epam.ta.reportportal.entity.enums.TestItemIssueGroup;
import com.epam.ta.reportportal.entity.item.issue.IssueEntity;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Uses script in db/fill/item-attribute
 *
 * @author <a href="mailto:ihar_kahadouski@epam.com">Ihar Kahadouski</a>
 */
public class IssueEntityRepositoryTest extends BaseTest {

	@Autowired
	private IssueEntityRepository repository;

	@Test
	public void findAllByIssueId() {
		final Long toInvestigateTypeId = 1L;
		final int expectedSize = 12;

		final List<IssueEntity> issueEntities = repository.findAllByIssueTypeId(toInvestigateTypeId);
		assertEquals("Incorrect size of issue entities", expectedSize, issueEntities.size());
		issueEntities.forEach(it -> assertEquals(
				"Issue entities should be int 'to investigate' group",
				TestItemIssueGroup.TO_INVESTIGATE,
				it.getIssueType().getIssueGroup().getTestItemIssueGroup()
		));

	}
}
