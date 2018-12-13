/*
 * Copyright (C) 2018 EPAM Systems
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

import com.epam.ta.reportportal.entity.user.User;
import com.epam.ta.reportportal.entity.user.UserRole;
import com.epam.ta.reportportal.entity.user.UserType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author Ivan Budayeu
 */
public interface UserRepository extends ReportPortalRepository<User, Long>, UserRepositoryCustom {

	Optional<User> findByDefaultProjectId(Long projectId);

	Optional<User> findByEmail(String email);

	/**
	 * @param login user login for search
	 * @return Optional<User>
	 */
	Optional<User> findByLogin(String login);

	List<User> findAllByEmailIn(Iterable<String> mails);

	List<User> findAllByLoginIn(Set<String> loginSet);

	List<User> findAllByRole(UserRole role);

	@Query(value = "SELECT u FROM User u WHERE u.userType = :userType AND u.isExpired = :isExpired")
	Page<User> findAllByUserTypeAndExpired(@Param("userType") UserType userType, @Param("isExpired") boolean isExpired, Pageable pageable);

	@Modifying
	@Query(value = "UPDATE users SET expired = TRUE WHERE CAST(metadata->>'last_login' AS TIMESTAMP) < :lastLogin", nativeQuery = true)
	void expireUsersLoggedOlderThan(@Param("lastLogin") LocalDateTime lastLogin);

	@Modifying
	@Query(value = "UPDATE users SET metadata = jsonb_set(metadata, '{last_login}', to_jsonb(CAST (:lastLogin AS TEXT)), true ) WHERE login = :username", nativeQuery = true)
	void updateLastLoginDate(@Param("lastLogin") LocalDateTime lastLogin, @Param("username") String username);

	@Query(value = "SELECT u.login FROM users u join project_user pu ON u.id = pu.user_id WHERE pu.project_id = :projectId", nativeQuery = true)
	List<String> findNamesByProject(@Param("projectId") Long projectId);

	@Query(value = "SELECT u.login FROM users u join project_user pu ON u.id = pu.user_id WHERE pu.project_id = :projectId and u.login LIKE %:term%", nativeQuery = true)
	List<String> findNamesByProject(@Param("projectId") Long projectId, @Param("term") String term);
}
