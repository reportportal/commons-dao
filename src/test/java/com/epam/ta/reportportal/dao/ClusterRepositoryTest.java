package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.BaseTest;
import com.epam.ta.reportportal.entity.cluster.Cluster;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static org.junit.jupiter.api.Assertions.*;

@Sql("/db/fill/launch/launch-fill.sql")
class ClusterRepositoryTest extends BaseTest {

	private static final long CLUSTER_ID_START_VALUE = 1L;
	private static final long CLUSTER_ID_END_VALUE = 4L;
	private static final long LAUNCH_ID = 1L;

	@Autowired
	private ClusterRepository clusterRepository;

	@BeforeEach
	void insertClusters() {
		final List<Cluster> clusters = LongStream.range(CLUSTER_ID_START_VALUE, CLUSTER_ID_END_VALUE).mapToObj(id -> {
			final Cluster cluster = new Cluster();
			cluster.setId(id);
			cluster.setLaunchId(LAUNCH_ID);
			cluster.setMessage("Message");
			return cluster;
		}).collect(Collectors.toList());
		clusterRepository.saveAll(clusters);
	}

	@AfterEach
	void removeClusters() {
		LongStream.range(CLUSTER_ID_START_VALUE, CLUSTER_ID_END_VALUE)
				.mapToObj(clusterRepository::findById)
				.forEach(cluster -> cluster.ifPresent(clusterRepository::delete));
	}

	@Test
	void shouldFindByLaunchId() {
		final List<Cluster> clusters = clusterRepository.findAllByLaunchIdOrderById(LAUNCH_ID);
		assertFalse(clusters.isEmpty());
		assertEquals(3, clusters.size());
		clusters.forEach(cluster -> assertEquals(LAUNCH_ID, cluster.getLaunchId()));
	}

	@Test
	void shouldDeleteByLaunchId() {
		final int removed = clusterRepository.deleteAllByLaunchId(LAUNCH_ID);
		assertEquals(3, removed);

		final List<Cluster> clusters = clusterRepository.findAllByLaunchIdOrderById(LAUNCH_ID);
		assertTrue(clusters.isEmpty());
	}

}