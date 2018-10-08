package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.entity.user.UserCreationBid;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.Optional;

/**
 * @author Ivan Budaev
 */
public interface UserCreationBidRepository extends ReportPortalRepository<UserCreationBid, String> {

	Optional<UserCreationBid> findByEmail(String email);

	@Modifying
	@Query(value = "DELETE FROM UserCreationBid u WHERE  u.lastModified < :lastLogin")
	void expireBidsOlderThan(@Param("lastLogin") Date lastLogin);


}
