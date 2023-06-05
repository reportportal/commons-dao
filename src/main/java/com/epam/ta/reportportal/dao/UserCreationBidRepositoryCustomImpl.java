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

package com.epam.ta.reportportal.dao;

import static com.epam.ta.reportportal.jooq.tables.JUserCreationBid.USER_CREATION_BID;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author <a href="mailto:ihar_kahadouski@epam.com">Ihar Kahadouski</a>
 */
@Repository
public class UserCreationBidRepositoryCustomImpl implements UserCreationBidRepositoryCustom {

  @Autowired
  private DSLContext dsl;

  @Override
  public int deleteAllByEmail(String email) {
    return dsl.deleteFrom(USER_CREATION_BID).where(USER_CREATION_BID.EMAIL.eq(email)).execute();
  }
}
