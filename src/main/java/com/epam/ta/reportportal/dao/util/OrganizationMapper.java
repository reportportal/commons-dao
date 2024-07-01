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

package com.epam.ta.reportportal.dao.util;

import static com.epam.ta.reportportal.jooq.Tables.ORGANIZATION;

import com.epam.ta.reportportal.entity.organization.Organization;
import com.epam.ta.reportportal.entity.organization.OrganizationFilter;
import com.epam.ta.reportportal.api.model.OrganizationInfo.TypeEnum;
import com.epam.ta.reportportal.api.model.OrganizationProfile;
import com.epam.ta.reportportal.api.model.OrganizationRelation;
import com.epam.ta.reportportal.api.model.OrganizationRelationLaunches;
import com.epam.ta.reportportal.api.model.OrganizationRelationLaunchesMeta;
import com.epam.ta.reportportal.api.model.OrganizationRelationProjects;
import com.epam.ta.reportportal.api.model.OrganizationRelationProjectsMeta;
import com.epam.ta.reportportal.api.model.OrganizationRelationUsers;
import com.epam.ta.reportportal.api.model.OrganizationRelationUsersMeta;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.Instant;
import org.jooq.Record;
import org.jooq.RecordMapper;

/**
 * Set of record mappers that helps to convert the result of jooq queries into Java objects
 *
 * @author Pavel Bortnik
 */
public class OrganizationMapper {

  private static final ObjectMapper objectMapper;

  static {
    objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
  }

  private OrganizationMapper() {
  }

  /**
   * Maps record into {@link Organization} object
   */
  public static final RecordMapper<? super Record, OrganizationProfile> ORGANIZATION_MAPPER = row -> {
    OrganizationProfile organization = row.into(OrganizationProfile.class);

    organization.setId(row.get(ORGANIZATION.ID, Long.class));
    organization.setCreatedAt(row.get(ORGANIZATION.CREATED_AT, Instant.class));
    organization.setUpdatedAt(row.get(ORGANIZATION.UPDATED_AT, Instant.class));
    organization.setName(row.get(ORGANIZATION.NAME, String.class));
    organization.setSlug(row.get(ORGANIZATION.SLUG, String.class));
    organization.setExternalId(row.get(ORGANIZATION.EXTERNAL_ID, String.class));
    organization.setType(TypeEnum.valueOf(row.get(ORGANIZATION.ORGANIZATION_TYPE)));

    // set launches
    OrganizationRelationLaunches orl = new OrganizationRelationLaunches();
    orl.meta(new OrganizationRelationLaunchesMeta()
        //.count(row.get(OrganizationFilter.LAUNCHES_QUANTITY, Integer.class))
        .lastOccurredAt(row.get(OrganizationFilter.LAST_RUN, Instant.class)));

    // set projects
    OrganizationRelationProjects rp = new OrganizationRelationProjects();
    rp.meta(new OrganizationRelationProjectsMeta()
        .count(row.get(OrganizationFilter.PROJECTS_QUANTITY, Integer.class)));

    // set users
    OrganizationRelationUsersMeta usersMeta = new OrganizationRelationUsersMeta()
        .count(row.get(OrganizationFilter.USERS_QUANTITY, Integer.class));
    OrganizationRelationUsers oru = new OrganizationRelationUsers()
        .meta(usersMeta);

    OrganizationRelation organizationRelation = new OrganizationRelation()
        .launches(orl)
        .projects(rp)
        .users(oru);

    organization.setRelationships(organizationRelation);

    return organization;
  };

}
