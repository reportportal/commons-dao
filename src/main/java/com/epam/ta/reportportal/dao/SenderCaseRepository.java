package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.entity.project.email.SenderCase;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface SenderCaseRepository extends ReportPortalRepository<SenderCase, Long> {

	@Query(value = "SELECT sc FROM SenderCase sc WHERE sc.project.id = :projectId")
	List<SenderCase> findAllByProjectId(@Param(value = "projectId") Long projectId);

	@Modifying
	@Query(value = "DELETE FROM recipients WHERE sender_case_id = :id AND recipient IN (:recipients)", nativeQuery = true)
	int deleteRecipients(@Param(value = "id") Long id, @Param(value = "recipients") Collection<String> recipients);
}
