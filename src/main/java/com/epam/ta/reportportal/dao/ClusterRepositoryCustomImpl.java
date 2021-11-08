package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.entity.cluster.Cluster;
import com.epam.ta.reportportal.jooq.tables.records.JClustersTestItemRecord;
import org.jooq.DSLContext;
import org.jooq.InsertValuesStep2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Set;

import static com.epam.ta.reportportal.jooq.tables.JClustersTestItem.CLUSTERS_TEST_ITEM;

/**
 * @author <a href="mailto:ivan_budayeu@epam.com">Ivan Budayeu</a>
 */
@Repository
public class ClusterRepositoryCustomImpl implements ClusterRepositoryCustom {

	private final DSLContext dsl;

	@Autowired
	public ClusterRepositoryCustomImpl(DSLContext dsl) {
		this.dsl = dsl;
	}

	@Override
	public int saveClusterTestItems(Cluster cluster, Set<Long> itemIds) {
		final InsertValuesStep2<JClustersTestItemRecord, Long, Long> insertQuery = dsl.insertInto(CLUSTERS_TEST_ITEM)
				.columns(CLUSTERS_TEST_ITEM.CLUSTER_ID, CLUSTERS_TEST_ITEM.ITEM_ID);

		itemIds.forEach(itemId -> insertQuery.values(cluster.getId(), itemId));

		return insertQuery.execute();
	}
}
