package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.entity.cluster.Cluster;
import java.util.Set;

/**
 * @author <a href="mailto:ivan_budayeu@epam.com">Ivan Budayeu</a>
 */
public interface ClusterRepositoryCustom {

  int saveClusterTestItems(Cluster cluster, Set<Long> itemIds);
}
