package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.entity.item.TestItemResults;
import com.epam.ta.reportportal.entity.launch.Launch;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;
import java.util.Optional;

public interface TestItemResultsRepository extends ReportPortalRepository<TestItemResults, Long> {

	@Query(value = "SELECT tir FROM TestItemResults tir WHERE tir.itemId = :id")
	@Lock(value = LockModeType.PESSIMISTIC_WRITE)
	Optional<TestItemResults> findByIdForUpdate(@Param("id") Long id);
}
