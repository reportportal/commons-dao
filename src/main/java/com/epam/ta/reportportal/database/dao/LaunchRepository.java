package com.epam.ta.reportportal.database.dao;

import com.epam.ta.reportportal.database.entity.launch.Launch;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Pavel Bortnik
 */
public interface LaunchRepository extends JpaRepository<Launch, Long> {
}
