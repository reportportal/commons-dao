package com.epam.ta.reportportal.database.dao;

import com.epam.ta.reportportal.database.entity.launch.Launch;

import java.util.List;

/**
 * @author Pavel Bortnik
 */
public interface LaunchRepository extends ReportPortalRepository<Launch, Long>, LaunchRepositoryCustom {

	void deleteByProjectId(Long projectId);

	List<Launch> findAllByName(String name);

}
