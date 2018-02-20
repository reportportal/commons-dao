package com.epam.ta.reportportal.database.dao;

import com.epam.ta.reportportal.database.entity.user.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Pavel Bortnik
 */
public interface UserRepository extends JpaRepository<Users, Integer>, UserRepositoryCustom {

	Optional<Users> findByLogin(String login);

}
