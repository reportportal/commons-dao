/*
 * Copyright 2024 EPAM Systems
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

package com.epam.ta.reportportal.dao.organization;

import static org.springframework.test.util.AssertionErrors.assertTrue;

import com.epam.ta.reportportal.BaseTest;
import com.epam.ta.reportportal.entity.organization.Organization;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Siarhei Hrabko
 */
class OrganizationRepositoryTest extends BaseTest {

	@Autowired
	private OrganizationRepository organizationRepository;

	@Test
	void findByNameTest() {
		//given
		String name = "My organization";

		//when
		Optional<Organization> organization = organizationRepository.findByName(name);

		//then
		assertTrue("Organization found", organization.isPresent());
	}

  @Test
  void findBySlugTest() {
    //given
    String slug = "my-organization";

    //when
    Optional<Organization> organization = organizationRepository.findBySlug(slug);

    //then
    assertTrue("Organization found", organization.isPresent());
  }
}
