package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.entity.item.TestItemTag;

import java.util.List;

/**
 * @author Ivan Budaev
 */
public interface TestItemTagRepository extends ReportPortalRepository<TestItemTag, Long> {

	List<TestItemTag> findAllByItemId(Long itemId);
}
