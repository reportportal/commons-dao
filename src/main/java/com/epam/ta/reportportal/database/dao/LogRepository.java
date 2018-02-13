package com.epam.ta.reportportal.database.dao;

import com.epam.ta.reportportal.database.entity.log.Log;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Pavel Bortnik
 */
public interface LogRepository extends JpaRepository<Log, Long>, LogRepositoryCustom {
}
