package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.entity.cluster.Cluster;

import java.util.List;

public interface ClusterRepository extends ReportPortalRepository<Cluster, Long> {

	List<Cluster> findAllByLaunchIdOrderById(Long launchId);

	int deleteAllByLaunchId(Long launchId);
}
