package com.epam.ta.reportportal.database.dao;

import com.epam.ta.reportportal.database.entity.item.TestItem;

import java.util.List;

/**
 * @author Pavel Bortnik
 */
public interface TestItemRepository extends ReportPortalRepository<TestItem, Long>, TestItemRepositoryCustom {

	List<TestItem> findTestItemsByUniqueId(String uniqueId);
}
