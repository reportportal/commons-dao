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

import static com.epam.ta.reportportal.entity.organization.OrganizationFilter.PROJECTS_QUANTITY;
import static com.epam.ta.reportportal.jooq.Tables.ORGANIZATION;
import static com.epam.ta.reportportal.jooq.Tables.ORGANIZATION_USER;
import static com.epam.ta.reportportal.jooq.tables.JUsers.USERS;

import com.epam.reportportal.api.model.OrganizationInfo.TypeEnum;
import com.epam.reportportal.api.model.OrganizationProfile;
import com.epam.reportportal.api.model.OrganizationRelationRelationships;
import com.epam.reportportal.api.model.OrganizationRelationRelationshipsLaunches;
import com.epam.reportportal.api.model.OrganizationRelationRelationshipsLaunchesMeta;
import com.epam.reportportal.api.model.OrganizationRelationRelationshipsProjects;
import com.epam.reportportal.api.model.OrganizationRelationRelationshipsProjectsMeta;
import com.epam.reportportal.api.model.OrganizationRelationRelationshipsUsers;
import com.epam.reportportal.api.model.OrganizationRelationRelationshipsUsersMeta;
import com.epam.reportportal.api.model.OrganizationUserInfo;
import com.epam.reportportal.api.model.OrganizationUserInfo.OrganizationRoleEnum;
import com.epam.reportportal.api.model.OrganizationUserRelationRelationships;
import com.epam.reportportal.api.model.OrganizationUserRelationRelationshipsProjects;
import com.epam.reportportal.api.model.OrganizationUserRelationRelationshipsProjectsMeta;
import com.epam.reportportal.api.model.UserAccountInfo.AuthProviderEnum;
import com.epam.reportportal.api.model.UserDetails.InstanceRoleEnum;
import com.epam.ta.reportportal.entity.organization.Organization;
import com.epam.ta.reportportal.entity.organization.OrganizationFilter;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import org.jooq.Record;
import org.jooq.RecordMapper;
import org.jooq.Result;
import org.json.JSONObject;

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
    OrganizationRelationRelationshipsLaunches orl = new OrganizationRelationRelationshipsLaunches();
    orl.meta(new OrganizationRelationRelationshipsLaunchesMeta()
        //.count(row.get(OrganizationFilter.LAUNCHES_QUANTITY, Integer.class))
        .lastOccurredAt(row.get(OrganizationFilter.LAST_RUN, Instant.class)));

    // set projects
    OrganizationRelationRelationshipsProjects rp = new OrganizationRelationRelationshipsProjects();
    rp.meta(new OrganizationRelationRelationshipsProjectsMeta()
        .count(row.get(PROJECTS_QUANTITY, Integer.class)));

    // set users
    OrganizationRelationRelationshipsUsersMeta usersMeta =
        new OrganizationRelationRelationshipsUsersMeta()
            .count(row.get(OrganizationFilter.USERS_QUANTITY, Integer.class));
    OrganizationRelationRelationshipsUsers oru = new OrganizationRelationRelationshipsUsers()
        .meta(usersMeta);

    OrganizationRelationRelationships organizationRelation = new OrganizationRelationRelationships()
        .launches(orl)
        .projects(rp)
        .users(oru);

    organization.setRelationships(organizationRelation);

    return organization;
  };


  public static final Function<Result<? extends Record>, List<OrganizationUserInfo>> ORGANIZATION_USERS_LIST_FETCHER = rows -> {
    List<OrganizationUserInfo> userProfiles = new ArrayList<>(rows.size());

    rows.forEach(row -> {
      OrganizationUserInfo organizationUserProfile = new OrganizationUserInfo();

      organizationUserProfile.setId(row.get(ORGANIZATION_USER.USER_ID));
      organizationUserProfile.setFullName(row.get(USERS.FULL_NAME));
      organizationUserProfile.setCreatedAt(row.get(USERS.CREATED_AT, Instant.class));
      organizationUserProfile.setUpdatedAt(row.get(USERS.UPDATED_AT, Instant.class));
      organizationUserProfile.setInstanceRole(InstanceRoleEnum.fromValue(row.get(USERS.ROLE)));
      organizationUserProfile.setOrganizationRole(
          OrganizationRoleEnum.fromValue(
              row.get(ORGANIZATION_USER.ORGANIZATION_ROLE.getName(), String.class)));
      organizationUserProfile.setAuthProvider(AuthProviderEnum.fromValue(row.get(USERS.TYPE)));
      organizationUserProfile.setEmail(row.get(USERS.EMAIL));

      Optional.ofNullable(row.field(USERS.METADATA))
          .ifPresent(meta -> {
            // TODO: refactor after switching to jooq 3.19 with jsonb processing support
            JSONObject json = new JSONObject(row.get(USERS.METADATA).data());
            Long millis = json.optJSONObject("metadata", new JSONObject()).optLong("last_login");
            organizationUserProfile.setLastLoginAt(Instant.ofEpochMilli(millis));

          });

      Optional.ofNullable(row.field(ORGANIZATION.EXTERNAL_ID))
          .ifPresent(
              extId -> organizationUserProfile.setExternalId(row.get(ORGANIZATION.EXTERNAL_ID)));

      // organizationUserProfile.setUuid(row.get(USERS.EXTERNAL_ID, UUID.class));// uncomment later

      OrganizationUserRelationRelationshipsProjects projects =
          new OrganizationUserRelationRelationshipsProjects()
              .meta(new OrganizationUserRelationRelationshipsProjectsMeta()
                  .count(row.get(PROJECTS_QUANTITY, Integer.class))
              );

      OrganizationUserRelationRelationships organizationUserRelation = new OrganizationUserRelationRelationships()
          .projects(projects);

      organizationUserProfile.setRelationships(organizationUserRelation);

      userProfiles.add(organizationUserProfile);

    });

    return userProfiles;
  };


}
