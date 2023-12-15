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

import static com.epam.ta.reportportal.commons.querygen.constant.GeneralCriteriaConstant.CRITERIA_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.epam.ta.reportportal.BaseTest;
import com.epam.ta.reportportal.commons.querygen.Condition;
import com.epam.ta.reportportal.commons.querygen.Filter;
import com.epam.ta.reportportal.commons.querygen.FilterCondition;
import com.epam.ta.reportportal.entity.dashboard.Dashboard;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

/**
 * @author <a href="mailto:ihar_kahadouski@epam.com">Ihar Kahadouski</a>
 */
@Sql("/db/fill/shareable/shareable-fill.sql")
class DashboardRepositoryTest extends BaseTest {

  @Autowired
  private DashboardRepository repository;

  @Test
  public void shouldFindByIdAndProjectIdWhenExists() {
    Optional<Dashboard> dashboard = repository.findByIdAndProjectId(13L, 1L);

    assertTrue(dashboard.isPresent());
  }

  @Test
  public void shouldNotFindByIdAndProjectIdWhenIdNotExists() {
    Optional<Dashboard> dashboard = repository.findByIdAndProjectId(55L, 1L);

    assertFalse(dashboard.isPresent());
  }

  @Test
  public void shouldNotFindByIdAndProjectIdWhenProjectIdNotExists() {
    Optional<Dashboard> dashboard = repository.findByIdAndProjectId(5L, 11L);

    assertFalse(dashboard.isPresent());
  }

  @Test
  public void shouldNotFindByIdAndProjectIdWhenIdAndProjectIdNotExist() {
    Optional<Dashboard> dashboard = repository.findByIdAndProjectId(55L, 11L);

    assertFalse(dashboard.isPresent());
  }

  @Test
  void findAllByProjectId() {
    final long superadminProjectId = 1L;

    final List<Dashboard> dashboards = repository.findAllByProjectId(superadminProjectId);

    assertNotNull(dashboards, "Dashboards should not be null");
    assertEquals(4, dashboards.size(), "Unexpected dashboards size");
    dashboards.forEach(it -> assertEquals(superadminProjectId, (long) it.getProject().getId(),
        "Dashboard has incorrect project id"));
  }

  @Test
  void shouldFindBySpecifiedNameAndProjectId() {
    assertTrue(repository.existsByNameAndProjectId("test admin dashboard", 1L));
  }

  private Filter buildDefaultFilter() {
    return Filter.builder()
        .withTarget(Dashboard.class)
        .withCondition(new FilterCondition(Condition.LOWER_THAN, false, "100", CRITERIA_ID))
        .build();
  }
}
