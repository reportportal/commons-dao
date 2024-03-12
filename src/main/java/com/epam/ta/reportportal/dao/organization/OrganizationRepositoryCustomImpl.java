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

import static com.epam.ta.reportportal.dao.util.QueryUtils.collectJoinFields;
import static com.epam.ta.reportportal.dao.util.ResultFetchers.ORGANIZATION_FETCHER;
import static com.epam.ta.reportportal.dao.util.ResultFetchers.ORGANIZATION_INFO_FETCHER;
import static com.epam.ta.reportportal.jooq.Tables.ORGANIZATION;
import static com.epam.ta.reportportal.jooq.Tables.ORGANIZATION_USER;
import static com.epam.ta.reportportal.jooq.Tables.USERS;

import com.epam.ta.reportportal.commons.querygen.QueryBuilder;
import com.epam.ta.reportportal.commons.querygen.Queryable;
import com.epam.ta.reportportal.entity.organization.Organization;
import com.epam.ta.reportportal.entity.organization.OrganizationInfo;
import com.epam.ta.reportportal.entity.project.ProjectInfo;
import java.util.List;
import java.util.Optional;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

/**
 * Repository implementation class for searching and filtering organization records.
 *
 * @author Siarhei Hrabko
 */
@Repository
public class OrganizationRepositoryCustomImpl implements OrganizationRepositoryCustom {

  @Autowired
  private DSLContext dsl;

  @Override
  public List<Organization> findByFilter(Queryable filter) {
    return ORGANIZATION_FETCHER.apply(
        dsl.fetch(QueryBuilder.newBuilder(filter, collectJoinFields(filter))
            .wrap()
            .build()));
  }

  @Override
  public Page<Organization> findByFilter(Queryable filter, Pageable pageable) {
    return PageableExecutionUtils.getPage(
        ORGANIZATION_FETCHER.apply(
            dsl.fetch(QueryBuilder.newBuilder(filter, collectJoinFields(filter, pageable.getSort()))
                .with(pageable)
                .wrap()
                .withWrapperSort(pageable.getSort())
                .build())),
        pageable,
        () -> dsl.fetchCount(QueryBuilder.newBuilder(filter).build())
    );
  }


  @Override
  public List<Organization> findAllByUserLogin(String login) {
    return ORGANIZATION_FETCHER.apply(dsl.select(ORGANIZATION.fields())
        .from(ORGANIZATION)
        .join(ORGANIZATION_USER)
        .on(ORGANIZATION.ID.eq(ORGANIZATION_USER.ORGANIZATION_ID))
        .join(USERS)
        .on(ORGANIZATION_USER.USER_ID.eq(USERS.ID))
        .where(USERS.LOGIN.eq(login))
        .fetch());
  }

  @Override
  public Optional<Organization> findById(Long orgId) {
    return dsl.select()
        .from(ORGANIZATION)
        .where(ORGANIZATION.ID.eq(orgId))
        .fetchOptionalInto(Organization.class);
  }

  @Override
  public Optional<Organization> findOrganizationByName(String name) {
    return dsl.select()
        .from(ORGANIZATION)
        .where(ORGANIZATION.NAME.eq(name))
        .fetchOptionalInto(Organization.class);
  }

  @Override
  public Optional<Organization> findOrganizationBySlug(String slug) {
    return dsl.select()
        .from(ORGANIZATION)
        .where(ORGANIZATION.SLUG.eq(slug))
        .fetchOptionalInto(Organization.class);
  }

  @Override
  public List<OrganizationInfo> findOrganizationInfoByFilter(Queryable filter) {
    return dsl.fetch(QueryBuilder.newBuilder(filter, collectJoinFields(filter))
            .build())
        .into(OrganizationInfo.class);
  }

  @Override
  public Page<OrganizationInfo> findOrganizationInfoByFilter(Queryable filter, Pageable pageable) {
    return PageableExecutionUtils.getPage(
        dsl.fetch(QueryBuilder.newBuilder(filter).with(pageable).build())
            .into(OrganizationInfo.class),
        pageable,
        () -> dsl.fetchCount(QueryBuilder.newBuilder(filter).build())
    );
  }

}
