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

import com.epam.reportportal.api.model.OrganizationInfo;
import com.epam.reportportal.api.model.OrganizationInfo.TypeEnum;
import com.epam.reportportal.api.model.OrganizationStatsRelationships;
import com.epam.reportportal.api.model.OrganizationStatsRelationshipsLaunches;
import com.epam.reportportal.api.model.OrganizationStatsRelationshipsLaunchesMeta;
import com.epam.reportportal.api.model.OrganizationStatsRelationshipsProjects;
import com.epam.reportportal.api.model.OrganizationStatsRelationshipsProjectsMeta;
import com.epam.reportportal.api.model.OrganizationStatsRelationshipsUsers;
import com.epam.reportportal.api.model.OrganizationStatsRelationshipsUsersMeta;
import com.epam.reportportal.api.model.OrganizationUser.OrgRoleEnum;
import com.epam.reportportal.api.model.OrganizationUserAllOfStats;
import com.epam.reportportal.api.model.OrganizationUserAllOfStatsProjectStats;
import com.epam.reportportal.api.model.UserBase;
import com.epam.reportportal.api.model.UserBase.AccountTypeEnum;
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
import java.util.stream.Collectors;
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
  public static final RecordMapper<? super Record, OrganizationInfo> ORGANIZATION_MAPPER = row ->
      row.into(OrganizationInfo.class)
          .id(row.get(ORGANIZATION.ID, Long.class))
          .createdAt(row.get(ORGANIZATION.CREATED_AT, Instant.class))
          .updatedAt(row.get(ORGANIZATION.UPDATED_AT, Instant.class))
          .name(row.get(ORGANIZATION.NAME, String.class))
          .slug(row.get(ORGANIZATION.SLUG, String.class))
          .externalId(row.get(ORGANIZATION.EXTERNAL_ID, String.class))
          .type(TypeEnum.valueOf(row.get(ORGANIZATION.ORGANIZATION_TYPE)))
          .relationships(new OrganizationStatsRelationships()
              .billing(null)
              .launches(new OrganizationStatsRelationshipsLaunches()
                  .meta(new OrganizationStatsRelationshipsLaunchesMeta()
                      .count(row.get(OrganizationFilter.LAUNCHES_QUANTITY, Integer.class))
                      .lastOccurredAt(row.get(OrganizationFilter.LAST_RUN, Instant.class))))
              .projects(new OrganizationStatsRelationshipsProjects()
                  .meta(new OrganizationStatsRelationshipsProjectsMeta()
                      .count(row.get(PROJECTS_QUANTITY, Integer.class))))
              .users(new OrganizationStatsRelationshipsUsers()
                  .meta(new OrganizationStatsRelationshipsUsersMeta().count(
                      row.get(OrganizationFilter.USERS_QUANTITY, Integer.class)))));


  public static final Function<Result<? extends Record>, List<com.epam.reportportal.api.model.OrganizationUser>> ORGANIZATION_USERS_LIST_FETCHER = rows -> {
    List<com.epam.reportportal.api.model.OrganizationUser> users = new ArrayList<>(rows.size());

    return rows.stream().map(row -> {
          var organizationUserInfo = new com.epam.reportportal.api.model.OrganizationUser()
              .id(row.get(ORGANIZATION_USER.USER_ID))
              .fullName(row.get(USERS.FULL_NAME))
              .createdAt(row.get(USERS.CREATED_AT, Instant.class))
              .updatedAt(row.get(USERS.UPDATED_AT, Instant.class))
              .instanceRole(UserBase.InstanceRoleEnum.fromValue(row.get(USERS.ROLE)))
              .orgRole(OrgRoleEnum.fromValue(
                  row.get(ORGANIZATION_USER.ORGANIZATION_ROLE.getName(), String.class)))
              .accountType(AccountTypeEnum.fromValue(row.get(USERS.TYPE)))
              .email(row.get(USERS.EMAIL))
              //.uuid(row.get(USERS.EXTERNAL_ID, UUID.class))
              .stats(new OrganizationUserAllOfStats()
                  .projectStats(new OrganizationUserAllOfStatsProjectStats()
                      .totalCount(row.get(PROJECTS_QUANTITY, Integer.class))));

          Optional.ofNullable(row.field(USERS.METADATA))
              .ifPresent(meta -> {
                // TODO: refactor after switching to jooq 3.19 with jsonb processing support
                JSONObject json = new JSONObject(row.get(USERS.METADATA).data());
                Long millis = json.optJSONObject("metadata", new JSONObject()).optLong("last_login");
                organizationUserInfo.setLastLoginAt(Instant.ofEpochMilli(millis));
              });

          Optional.ofNullable(row.field(ORGANIZATION.EXTERNAL_ID))
              .ifPresent(
                  extId -> organizationUserInfo.setExternalId(row.get(ORGANIZATION.EXTERNAL_ID)));
          Optional.ofNullable(row.field(ORGANIZATION.EXTERNAL_ID))
              .ifPresent(extId -> organizationUserInfo.setUuid(row.get(USERS.UUID)));

          return organizationUserInfo;
        }
    ).collect(Collectors.toList());

  };


}
