package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.entity.user.User;

import java.util.Optional;

/**
 * @author Ivan Budayeu
 */
public interface UserRepository extends ReportPortalRepository<User, Long> {

	/**
	 * @param login user login for search
	 * @return Optional<User>
	 */
	Optional<User> findByLogin(String login);

}
