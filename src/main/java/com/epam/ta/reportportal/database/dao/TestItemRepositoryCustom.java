package com.epam.ta.reportportal.database.dao;

import com.epam.ta.reportportal.database.entity.enums.StatusEnum;
import com.epam.ta.reportportal.database.entity.item.TestItemCommon;

import java.util.List;

/**
 * @author Pavel Bortnik
 */
public interface TestItemRepositoryCustom {

	List<TestItemCommon> selectItemsInStatusByLaunch(Long launchId, StatusEnum status);

}
