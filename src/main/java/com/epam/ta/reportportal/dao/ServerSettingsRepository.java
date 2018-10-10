package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.entity.ServerSettings;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author Ivan Budaev
 */
public interface ServerSettingsRepository extends ReportPortalRepository<ServerSettings, Long> {

	@Query("from ServerSettings ss")
	Stream<ServerSettings> streamAll();

	Optional<ServerSettings> findByKey(String key);
}
