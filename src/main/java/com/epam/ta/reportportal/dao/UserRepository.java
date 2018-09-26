package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.entity.user.User;
import com.epam.ta.reportportal.entity.user.UserRole;
import com.epam.ta.reportportal.entity.user.UserType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

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

	@Transactional
	@Modifying
	@Query(value = "UPDATE users SET metadata = jsonb_set(metadata, '{last_login}', to_jsonb(CAST (:lastLogin AS TEXT)), true ) WHERE login = :username", nativeQuery = true)
	void updateLastLoginDate(@Param("lastLogin") LocalDateTime lastLogin, @Param("username") String username);

}
