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
import com.epam.ta.reportportal.entity.item.issue.IssueEntity;
import org.flywaydb.test.annotation.FlywayTest;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author <a href="mailto:ihar_kahadouski@epam.com">Ihar Kahadouski</a>
 */
public class IssueEntityRepositoryTest extends BaseTest {

	private static final String FILL_SCRIPT_PATH_ITEM = "db/fill/issue-entity";

	private static final Long TO_INVESTIGATE_TYPE_ID = 1L;
	private static final int EXPECTED_SIZE = 12;

	@Autowired
	private IssueEntityRepository repository;

	@FlywayTest(locationsForMigrate = { FILL_SCRIPT_PATH_ITEM }, invokeCleanDB = false)
	@BeforeClass
	public static void setUp() {
	}

	@Test
	public void findAllByIssueId() {
		final List<IssueEntity> issueEntities = repository.findAllByIssueTypeId(TO_INVESTIGATE_TYPE_ID);
		assertEquals(EXPECTED_SIZE, issueEntities.size());
	}
}
