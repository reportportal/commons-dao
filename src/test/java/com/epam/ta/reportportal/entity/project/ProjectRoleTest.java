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

package com.epam.ta.reportportal.entity.project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author <a href="mailto:ihar_kahadouski@epam.com">Ihar Kahadouski</a>
 */
class ProjectRoleTest {

  private final ProjectRole OPERATOR = ProjectRole.OPERATOR;
  private final ProjectRole CUSTOMER = ProjectRole.CUSTOMER;
  private final ProjectRole MEMBER = ProjectRole.MEMBER;
  private final ProjectRole PROJECT_MANAGER = ProjectRole.PROJECT_MANAGER;
  private Map<ProjectRole, List<String>> allowed;
  private List<String> disallowed;

  @BeforeEach
  void setUp() {
    allowed = Arrays.stream(ProjectRole.values())
        .collect(Collectors.toMap(it -> it,
            it -> Arrays.asList(it.name(), it.name().toUpperCase(), it.name().toLowerCase())));
    disallowed = Arrays.asList("noSuchObjectType", "", " ", null);
  }

  @Test
  void higherThan() {
    assertFalse(PROJECT_MANAGER.higherThan(PROJECT_MANAGER));
    assertTrue(PROJECT_MANAGER.higherThan(MEMBER));
    assertFalse(OPERATOR.higherThan(CUSTOMER));
  }

  @Test
  void lowerThan() {
    assertFalse(PROJECT_MANAGER.lowerThan(PROJECT_MANAGER));
    assertTrue(MEMBER.lowerThan(PROJECT_MANAGER));
    assertTrue(CUSTOMER.lowerThan(PROJECT_MANAGER));
  }

  @Test
  void sameOrHigherThan() {
    assertTrue(PROJECT_MANAGER.sameOrHigherThan(PROJECT_MANAGER));
    assertTrue(MEMBER.sameOrHigherThan(CUSTOMER));
    assertFalse(MEMBER.sameOrHigherThan(PROJECT_MANAGER));
  }

  @Test
  void sameOrLowerThan() {
    assertTrue(PROJECT_MANAGER.sameOrLowerThan(PROJECT_MANAGER));
    assertTrue(CUSTOMER.sameOrLowerThan(MEMBER));
    assertFalse(PROJECT_MANAGER.sameOrLowerThan(MEMBER));
  }

  @Test
  void forName() {
    allowed.forEach((key, value) -> value.forEach(val -> {
      final Optional<ProjectRole> optional = ProjectRole.forName(val);
      assertTrue(optional.isPresent());
      assertEquals(key, optional.get());
    }));
    disallowed.forEach(it -> assertFalse(ProjectRole.forName(it).isPresent()));
  }
}