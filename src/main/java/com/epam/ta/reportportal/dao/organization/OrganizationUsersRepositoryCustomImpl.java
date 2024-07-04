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


import static com.epam.ta.reportportal.dao.util.OrganizationMapper.ORGANIZATION_USERS_LIST_FETCHER;

import com.epam.ta.reportportal.api.model.OrganizationUserProfile;
import com.epam.ta.reportportal.commons.querygen.FilterTarget;
import com.epam.ta.reportportal.commons.querygen.QueryBuilder;
import com.epam.ta.reportportal.commons.querygen.Queryable;
import java.util.List;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.SelectQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

/**
 * Repository implementation class for searching and filtering organization records.
 *
 * @author Siarhei Hrabko
 */
@Repository
public class OrganizationUsersRepositoryCustomImpl implements OrganizationUsersRepositoryCustom {

  protected final Logger LOGGER = LoggerFactory.getLogger(
      OrganizationUsersRepositoryCustomImpl.class);

  @Autowired
  private DSLContext dsl;

  @Override
  public List<OrganizationUserProfile> findByFilter(Queryable filter) {
    return ORGANIZATION_USERS_LIST_FETCHER.apply(dsl.fetch(QueryBuilder.newBuilder(filter)
        .build()));
  }

  @Override
  public Page<OrganizationUserProfile> findByFilter(Queryable filter, Pageable pageable) {
    SelectQuery<? extends Record> query = QueryBuilder.newBuilder(filter).with(pageable).build();
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("Find organization users query: {}", query);
    }
    return PageableExecutionUtils.getPage(
        ORGANIZATION_USERS_LIST_FETCHER.apply(dsl.fetch(query)),
        pageable,
        () -> dsl.fetchCount(QueryBuilder.newBuilder(FilterTarget.ORGANIZATION_USERS).build()));
  }
}
