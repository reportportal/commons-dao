package com.epam.ta.reportportal.dao;

import java.util.List;

/**
 * @author Ivan Budaev
 */
public interface TestItemTagRepositoryCustom {

	List<String> findDistinctByLaunchIdAndValue(Long launchId, String value);
}
