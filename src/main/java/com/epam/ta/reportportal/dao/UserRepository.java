package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.entity.user.User;
import com.epam.ta.reportportal.entity.user.UserRole;
import com.epam.ta.reportportal.entity.user.UserType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
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

	@Query(value = "SELECT u FROM User u WHERE u.expired = :expired AND u.userType = :userType")
	Page<User> findAllByUserTypeAndExpired(@Param("userType") UserType userType, @Param("expired") boolean expired, Pageable pageable);

	//TODO find user photo

	@Query(value = "UPDATE users SET users.expired = TRUE WHERE CAST(metadata->>'last_login' AS DATE) < :lastLogin", nativeQuery = true)
	void expireUsersLoggedOlderThan(@Param("lastLogin") Date lastLogin);

	@Query(value = "UPDATE users SET metadata = jsonb_set(metadata, '{last_login}', to_jsonb(:lastLogin::text), true ) WHERE users.login = :username;", nativeQuery = true)
	void updateLastLoginDate(@Param("username") String username, @Param("lastLogin") Date date);

}
