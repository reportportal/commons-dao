package com.epam.ta.reportportal.database.dao;

import com.epam.ta.reportportal.database.entity.launch.LaunchTag;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Pavel Bortnik
 */
public interface LaunchTagRepository extends JpaRepository<LaunchTag, Long> {
}
