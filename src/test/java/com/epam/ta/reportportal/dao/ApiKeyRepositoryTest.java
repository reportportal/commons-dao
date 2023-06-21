/*
 * Copyright 2022 EPAM Systems
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

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.epam.ta.reportportal.BaseTest;
import com.epam.ta.reportportal.entity.user.ApiKey;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Andrei Piankouski
 */
public class ApiKeyRepositoryTest extends BaseTest {

  @Autowired
  private ApiKeyRepository apiKeyRepository;

  @Test
  void shouldInsertAndSetId() {
    final ApiKey apiKey = new ApiKey();
    apiKey.setName("ApiKey");
    apiKey.setHash("8743b52063cd84097a65d1633f5c74f5");
    apiKey.setCreatedAt(LocalDateTime.now());
    apiKey.setUserId(1L);
    apiKey.setLastUsedAt(LocalDate.now());

    ApiKey saved = apiKeyRepository.save(apiKey);

    assertNotNull(saved.getId());
  }

}
