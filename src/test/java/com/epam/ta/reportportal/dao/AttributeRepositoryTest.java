/*
 *  Copyright (C) 2018 EPAM Systems
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.config.TestConfiguration;
import com.epam.ta.reportportal.config.util.SqlRunner;
import com.epam.ta.reportportal.entity.attribute.Attribute;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.Optional;

/**
 * @author Ivan Budaev
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
@Transactional("transactionManager")
public class AttributeRepositoryTest {

	@Autowired
	private AttributeRepository attributeRepository;

	@BeforeClass
	public static void init() throws SQLException {
		SqlRunner.runSqlScripts("/test-dropall-script.sql", "/test-create-script.sql", "/test-fill-script.sql");
	}

	@AfterClass
	public static void destroy() throws SQLException {
		SqlRunner.runSqlScripts("/test-dropall-script.sql");
	}

	@Test
	public void shouldFindWhenNameIsPresent() {

		//given
		String name = "present";

		//when
		Optional<Attribute> attribute = attributeRepository.findByName(name);

		//then
		Assert.assertTrue(attribute.isPresent());
	}

	@Test
	public void shouldNotFindWhenNameIsNotPresent() {

		//given
		String name = "not present";

		//when
		Optional<Attribute> attribute = attributeRepository.findByName(name);

		//then
		Assert.assertFalse(attribute.isPresent());
	}

	@Test
	public void getDefaultProjectAttributesTest() {
		attributeRepository.getDefaultProjectAttributes();
	}
}