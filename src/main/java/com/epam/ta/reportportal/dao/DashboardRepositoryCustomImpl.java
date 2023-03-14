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

import static com.epam.ta.reportportal.dao.util.ResultFetchers.DASHBOARD_FETCHER;

import com.epam.ta.reportportal.commons.querygen.ProjectFilter;
import com.epam.ta.reportportal.entity.dashboard.Dashboard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

/**
 * @author <a href="mailto:pavel_bortnik@epam.com">Pavel Bortnik</a>
 */
@Repository
public class DashboardRepositoryCustomImpl extends
    AbstractShareableRepositoryImpl<Dashboard> implements DashboardRepositoryCustom {

  @Override
  public Page<Dashboard> getPermitted(ProjectFilter filter, Pageable pageable, String userName) {
    return getPermitted(DASHBOARD_FETCHER, filter, pageable, userName);
  }

  @Override
  public Page<Dashboard> getOwn(ProjectFilter filter, Pageable pageable, String userName) {
    return getOwn(DASHBOARD_FETCHER, filter, pageable, userName);
  }

  @Override
  public Page<Dashboard> getShared(ProjectFilter filter, Pageable pageable, String userName) {
    return getShared(DASHBOARD_FETCHER, filter, pageable, userName);
  }
}
