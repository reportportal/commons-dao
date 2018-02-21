package com.epam.ta.reportportal.database.dao;

import com.epam.ta.reportportal.database.entity.enums.StatusEnum;
import com.epam.ta.reportportal.database.entity.item.TestItemCommon;

import java.util.List;

/**
 * @author Pavel Bortnik
 */
public interface TestItemRepositoryCustom {

	/**
	 * Select common items object that have provided status for
	 * specified launch
	 *
	 * @param launchId Launch
	 * @param status   Status
	 * @return List of items
	 */
	List<TestItemCommon> selectItemsInStatusByLaunch(Long launchId, StatusEnum status);

	/**
	 * Select ids of items that has different issue from provided for
	 * specified launch
	 *
	 * @param launchId  Launch
	 * @param issueType Issue type locator
	 * @return List of item ids.
	 */
	List<Long> selectIdsNotInIssueByLaunch(Long launchId, String issueType);

	/**
	 * Select test items that has issue with provided issue type for
	 * specified launch
	 *
	 * @param launchId  Launch id
	 * @param issueType Issue type
	 * @return List of items
	 */
	List<TestItemCommon> selectItemsInIssueByLaunch(Long launchId, String issueType);

}
