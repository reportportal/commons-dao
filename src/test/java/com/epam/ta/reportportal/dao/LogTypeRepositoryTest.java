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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.epam.ta.reportportal.BaseTest;
import com.epam.ta.reportportal.entity.log.ProjectLogType;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class LogTypeRepositoryTest extends BaseTest {

  @Autowired
  private LogTypeRepository logTypeRepository;

  @Test
  void findByProjectIdWhenProjectWithDefaultLogTypesExistsShouldReturnAllDefaultTypesWithExpectedValues() {
    // given
    final long projectId = 1L;
    final List<ProjectLogType> expectedDefaultLogTypes = List.of(
        new ProjectLogType(null, projectId, "unknown", 60000, "#E3E7EC", "#FFFFFF", "#464547",
            "normal", false, true, null, null),
        new ProjectLogType(null, projectId, "fatal", 50000, "#8B0000", "#FFFFFF", "#464547",
            "normal", true, true, null, null),
        new ProjectLogType(null, projectId, "error", 40000, "#DC5959", "#FFFFFF", "#464547",
            "normal", true, true, null, null),
        new ProjectLogType(null, projectId, "warn", 30000, "#FFBC6C", "#FFFFFF", "#464547",
            "normal", true, true, null, null),
        new ProjectLogType(null, projectId, "info", 20000, "#23A6DE", "#FFFFFF", "#464547",
            "normal", true, true, null, null),
        new ProjectLogType(null, projectId, "debug", 10000, "#C1C7D0", "#FFFFFF", "#464547",
            "normal", true, true, null, null),
        new ProjectLogType(null, projectId, "trace", 5000, "#E3E7EC", "#FFFFFF", "#464547",
            "normal", true, true, null, null)
    );

    // when
    List<ProjectLogType> actualLogTypes = logTypeRepository.findByProjectId(projectId);

    // then
    assertEquals(7, actualLogTypes.size());
    for (int i = 0; i < actualLogTypes.size(); i++) {
      ProjectLogType expected = expectedDefaultLogTypes.get(i);
      ProjectLogType actual = actualLogTypes.get(i);
      assertNotNull(actual.getId());
      assertEquals(expected.getProjectId(), actual.getProjectId());
      assertEquals(expected.getName(), actual.getName());
      assertEquals(expected.getLevel(), actual.getLevel());
      assertEquals(expected.getLabelColor(), actual.getLabelColor());
      assertEquals(expected.getBackgroundColor(), actual.getBackgroundColor());
      assertEquals(expected.getTextColor(), actual.getTextColor());
      assertEquals(expected.getTextStyle(), actual.getTextStyle());
      assertEquals(expected.isFilterable(), actual.isFilterable());
      assertEquals(expected.isSystem(), actual.isSystem());
    }
  }

  @Test
  void findByProjectIdWhenProjectDoesNotExistShouldReturnEmptyList() {
    // given
    final long projectId = 1234L;

    // when
    List<ProjectLogType> logTypes = logTypeRepository.findByProjectId(projectId);

    // then
    assertTrue(logTypes.isEmpty());
  }

  @Test
  void existsByProjectIdAndNameOrLevelWhenDuplicateByNameExistsShouldReturnTrue() {
    // given
    final long projectId = 1L;
    final String existingName = "Info";
    final int newLevel = 223445;

    // when
    boolean exists = logTypeRepository.existsByProjectIdAndNameOrLevelIgnoreCase(projectId,
        existingName, newLevel);

    // then
    assertTrue(exists);
  }

  @Test
  void existsByProjectIdAndNameOrLevelWhenDuplicateByLevelExistsShouldReturnTrue() {
    // given
    final long projectId = 1L;
    final String newName = "New name";
    final int existingLevel = 10000;

    // when
    boolean exists = logTypeRepository.existsByProjectIdAndNameOrLevelIgnoreCase(projectId, newName,
        existingLevel);

    // then
    assertTrue(exists);
  }

  @Test
  void existsByProjectIdAndNameOrLevelWhenNoDuplicateExistsShouldReturnFalse() {
    // given
    final long projectId = 1L;
    final String newName = "New name";
    final int newLevel = 1234556;

    // when
    boolean exists = logTypeRepository.existsByProjectIdAndNameOrLevelIgnoreCase(projectId, newName,
        newLevel);

    // then
    assertFalse(exists);
  }

  @Test
  void countFilterableLogTypesWhenProjectWithDefaultLogTypesShouldReturnSix() {
    // given
    final long projectId = 1L;

    // when
    long logTypesCount = logTypeRepository.countFilterableLogTypes(projectId);

    // then
    assertEquals(6, logTypesCount);
  }
}
