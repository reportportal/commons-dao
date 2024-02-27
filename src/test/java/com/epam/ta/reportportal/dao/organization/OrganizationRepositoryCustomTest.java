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

import static com.epam.ta.reportportal.entity.project.ProjectInfo.USERS_QUANTITY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertTrue;

import com.epam.ta.reportportal.BaseTest;
import com.epam.ta.reportportal.commons.querygen.Condition;
import com.epam.ta.reportportal.commons.querygen.Filter;
import com.epam.ta.reportportal.entity.organization.Organization;
import com.epam.ta.reportportal.entity.project.ProjectInfo;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Siarhei Hrabko
 */
class OrganizationRepositoryCustomTest extends BaseTest {

  @Autowired
  private OrganizationRepositoryCustom organizationRepositoryCustom;

  @Test
  void findByNameTest() {
    String name = "My organization";
    Optional<Organization> organization = organizationRepositoryCustom.findOrganizationByName(name);
    assertTrue("Organization not found", organization.isPresent());
  }

  @Test
  void findBySlugTest() {
    String slug = "my-organization";
    Optional<Organization> organization = organizationRepositoryCustom.findOrganizationBySlug(slug);
    assertTrue("Organization not found", organization.isPresent());
  }

  @Test
  void findByIdTest() {
    Optional<Organization> organization = organizationRepositoryCustom.findById(1L);
    assertTrue("Organization not found", organization.isPresent());
  }

  @Test
  void organizationByFilterNotFound() {
    final List<Organization> orgs = organizationRepositoryCustom.findByFilter(
        new Filter(Organization.class,
            Condition.EQUALS,
            false,
            "wubba-lubba-dub-dub",
            "slug"
        ));
    assertEquals(0, orgs.size());
  }

  @Test
  void findOrganizationByFilter() {
    final List<Organization> orgs = organizationRepositoryCustom.findByFilter(
        new Filter(Organization.class,
            Condition.EQUALS,
            false,
            "my-organization",
            "slug"
        ));
    assertEquals(1, orgs.size());
  }

}
