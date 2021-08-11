package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.entity.project.email.SenderCase;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

public interface SenderCaseRepository extends ReportPortalRepository<SenderCase, Long> {

	@Modifying
	@Query(value = "DELETE FROM recipients WHERE sender_case_id = :id AND recipient IN (:recipients)", nativeQuery = true)
	int deleteRecipients(@Param(value = "id") Long id, @Param(value = "recipients") Collection<String> recipients);
}
