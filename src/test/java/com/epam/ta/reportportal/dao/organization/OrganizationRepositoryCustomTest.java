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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertTrue;

import com.epam.ta.reportportal.BaseTest;
import com.epam.ta.reportportal.commons.querygen.Condition;
import com.epam.ta.reportportal.commons.querygen.Filter;
import com.epam.ta.reportportal.commons.querygen.FilterCondition;
import com.epam.ta.reportportal.entity.organization.Organization;
import com.epam.ta.reportportal.entity.organization.OrganizationFilter;
import com.epam.ta.reportportal.entity.organization.OrganizationProfile;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
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
    assertEquals(name, organization.get().getName());
  }

  @Test
  void findBySlugTest() {
    String slug = "my-organization";
    Optional<Organization> organization = organizationRepositoryCustom.findOrganizationBySlug(slug);
    assertTrue("Organization not found", organization.isPresent());
    assertEquals(slug, organization.get().getSlug());
  }

  @Test
  void findByIdTest() {
    Long id = 1L;
    Optional<Organization> organization = organizationRepositoryCustom.findById(id);
    assertTrue("Organization not found", organization.isPresent());
    assertEquals(id, organization.get().getId());
  }

  @Test
  void organizationByFilterNotFound() {
    final List<OrganizationProfile> orgs = organizationRepositoryCustom.findByFilter(
        new Filter(OrganizationFilter.class,
            Condition.EQUALS,
            false,
            "wubba-lubba-dub-dub",
            "slug"
        ));
    assertEquals(0, orgs.size());
  }

  @Disabled
  @ParameterizedTest
  @CsvSource(value = {
      "slug|eq|my-organization|1",
      "slug|eq|notexists|0",
      "users|eq|1|1",
      "users|eq|845|0",
      "launches|gt|-1|1",
      "launches|gt|999|0",
      "projects|eq|2|1",
      "projects|eq|999|0"
  }, delimiter = '|')
  void findOrganizationByFilterWithUser(String field, String condition, String value, int rows) {
    Filter filter = new Filter(OrganizationFilter.class,
        Condition.findByMarker(condition).get(),
        false,
        value,
        field);

    filter.withCondition(new FilterCondition(Condition.EQUALS, false, "2", "org_user_id"));

    final List<OrganizationProfile> orgs = organizationRepositoryCustom.findByFilter(filter);
    assertEquals(rows, orgs.size());
  }


  @Disabled
  @ParameterizedTest
  @CsvSource(value = {
      "name|eq|My organization|1",
      "slug|eq|my-organization|1",
      "slug|eq|notexists|0",
      "users|eq|2|1",
      "users|eq|845|0",
      "org_user_id|eq|1|1",
      "org_user_id|eq|3|0",
      "launches|gt|-1|1",
      "launches|gt|999|0",
      "projects|eq|2|1",
      "projects|eq|999|0",
      "user|eq|admin@reportportal.internal|1",
      "user|eq|notexists|0",
      "created_at|gt|2024-08-01T12:42:30.758055Z|1",
      "updated_at|gt|2024-08-01T12:42:30.758055Z|1",
      "last_launch_occurred|lt|2024-08-01T12:42:30.758055Z|0"
  }, delimiter = '|')
  void findOrganizationByFilter(String field, String condition, String value, int rows) {
    final List<OrganizationProfile> orgs = organizationRepositoryCustom.findByFilter(
        new Filter(OrganizationFilter.class,
            Condition.findByMarker(condition).get(),
            false,
            value,
            field
        ));
    assertEquals(rows, orgs.size());
  }
}
