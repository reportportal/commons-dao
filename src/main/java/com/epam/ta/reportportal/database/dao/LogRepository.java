package com.epam.ta.reportportal.database.dao;

import com.epam.ta.reportportal.database.entity.log.Log;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author Pavel Bortnik
 */
public interface LogRepository extends JpaRepository<Log, Long>, LogRepositoryCustom {

	List<Log> findLogsByLogTime(Timestamp timestamp);

	List<Log> findLogsByItemId(Long itemId);

}
