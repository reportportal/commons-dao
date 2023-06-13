/*
 * Copyright 2023 EPAM Systems
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

import com.epam.ta.reportportal.entity.user.ApiKey;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * ApiKey Repository
 *
 * @author Andrei Piankouski
 */
public interface ApiKeyRepository extends ReportPortalRepository<ApiKey, Long> {

  /**
   * @param hash hash of api key
   * @return {@link ApiKey}
   */
  ApiKey findByHash(String hash);

  /**
   * @param name   name of user Api key
   * @param userId {@link com.epam.ta.reportportal.entity.user.User#id}
   * @return if exists 'true' else 'false'
   */
  boolean existsByNameAndUserId(String name, Long userId);

  /**
   * @param userId {@link com.epam.ta.reportportal.entity.user.User#id}
   * @return list of user api keys
   */
  List<ApiKey> findByUserId(Long userId);

  /**
   * Update lastUsedAt for apiKey
   *
   * @param id         id of the ApiKey to update
   * @param lastUsedAt {@link LocalDate}
   */
  @Modifying
  @Query("UPDATE ApiKey ak SET ak.lastUsedAt = :lastUsedAt WHERE ak.id = :id")
  void updateLastUsedAt(@Param("id") Long id, @Param("lastUsedAt") LocalDate lastUsedAt);
}
