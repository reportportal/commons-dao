package com.epam.ta.reportportal.database.dao;

import com.epam.ta.reportportal.database.entity.launch.Launch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Pavel Bortnik
 */
public interface LaunchRepository extends JpaRepository<Launch, Long>, LaunchRepositoryCustom {

	void deleteByProjectId(Long projectId);

	List<Launch> findAllByName(String name);

}
