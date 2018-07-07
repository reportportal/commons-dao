package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.entity.user.User;

import java.util.Optional;

public interface UserRepository extends ReportPortalRepository<User, Long> {

	Optional<User> findByLogin(String login);
}
