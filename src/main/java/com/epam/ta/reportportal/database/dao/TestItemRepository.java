package com.epam.ta.reportportal.database.dao;

import com.epam.ta.reportportal.database.entity.item.TestItem;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Pavel Bortnik
 */
public interface TestItemRepository extends JpaRepository<TestItem, Long>, TestItemRepositoryCustom {
}
