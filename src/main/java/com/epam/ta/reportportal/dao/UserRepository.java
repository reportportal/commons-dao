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

import com.epam.ta.reportportal.entity.organization.OrganizationRole;
import com.epam.ta.reportportal.entity.project.ProjectRole;
import com.epam.ta.reportportal.entity.user.User;
import com.epam.ta.reportportal.entity.user.UserRole;
import com.epam.ta.reportportal.entity.user.UserType;
import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author Ivan Budayeu
 */
public interface UserRepository extends ReportPortalRepository<User, Long>, UserRepositoryCustom {

  @Query(value = "SELECT id FROM users WHERE users.login = :username FOR UPDATE", nativeQuery = true)
  Optional<Long> findIdByLoginForUpdate(@Param("username") String login);

  Optional<User> findByEmail(String email);

  /**
   * @param login user login for search
   * @return {@link Optional} of {@link User}
   */
  Optional<User> findByLogin(String login);

  /**
   * @param uuid user uuid for search
   * @return {@link Optional} of {@link User}
   */
  Optional<User> findByUuid(UUID uuid);

  /**
   * @param externalId user external id for search
   * @return {@link Optional} of {@link User}
   */
  Optional<User> findByExternalId(String externalId);

  List<User> findAllByEmailIn(Collection<String> mails);

  List<User> findAllByLoginIn(Set<String> loginSet);

  List<User> findAllByRole(UserRole role);

  @Query(value = "SELECT u FROM User u WHERE u.userType = :userType AND u.isExpired = :isExpired")
  Page<User> findAllByUserTypeAndExpired(@Param("userType") UserType userType,
      @Param("isExpired") boolean isExpired, Pageable pageable);

  @Modifying(clearAutomatically = true)
  @Query(value = "UPDATE users SET expired = TRUE WHERE CAST(metadata-> 'metadata' ->> 'last_login' AS DOUBLE PRECISION) < (extract(EPOCH FROM CAST (:lastLogin AS TIMESTAMP)) * 1000);", nativeQuery = true)
  void expireUsersLoggedOlderThan(@Param("lastLogin") Instant lastLogin);

  /**
   * Updates user's last login value
   *
   * @param username User
   */
  @Modifying(clearAutomatically = true)
  @Query(value = "UPDATE users SET metadata = jsonb_set(metadata, '{metadata,last_login}', to_jsonb(round(extract(EPOCH from clock_timestamp()) * 1000)), TRUE ) WHERE login = :username", nativeQuery = true)
  void updateLastLoginDate(@Param("username") String username);

  @Query(value = "SELECT u.login FROM users u JOIN project_user pu ON u.id = pu.user_id WHERE pu.project_id = :projectId", nativeQuery = true)
  List<String> findNamesByProject(@Param("projectId") Long projectId);

  @Query(value = "SELECT u.email FROM users u JOIN project_user pu ON u.id = pu.user_id WHERE pu.project_id = :projectId", nativeQuery = true)
  List<String> findEmailsByProject(@Param("projectId") Long projectId);

  @Query(value = "SELECT u.email FROM users u JOIN organization_user ou ON u.id = ou.user_id WHERE ou.organization_id = :organizationId", nativeQuery = true)
  List<String> findEmailsByOrganization(@Param("organizationId") Long organizationId);

  @Query(value = """
      SELECT u.email FROM users u
            JOIN organization_user ou ON u.id = ou.user_id 
            WHERE ou.organization_id = :organizationId 
            AND ou.organization_role = cast(:#{#organizationRole.name()} AS organization_role_enum)
      """, nativeQuery = true)
  List<String> findEmailsByOrganizationAndRole(@Param("organizationId") Long organizationId,
      @Param("organizationRole") OrganizationRole organizationRole);

  @Query(value = "SELECT u.email FROM users u JOIN project_user pu ON u.id = pu.user_id WHERE pu.project_id = :projectId AND pu.project_role = cast(:#{#projectRole.name()} AS PROJECT_ROLE_ENUM)", nativeQuery = true)
  List<String> findEmailsByProjectAndRole(@Param("projectId") Long projectId,
      @Param("projectRole") ProjectRole projectRole);

  @Query(value = "SELECT u.login FROM users u JOIN project_user pu ON u.id = pu.user_id WHERE pu.project_id = :projectId AND u.login LIKE %:term%", nativeQuery = true)
  List<String> findNamesByProject(@Param("projectId") Long projectId, @Param("term") String term);

  @Query(value = "SELECT users.login FROM users WHERE users.id = :id", nativeQuery = true)
  Optional<String> findLoginById(@Param("id") Long id);

}
