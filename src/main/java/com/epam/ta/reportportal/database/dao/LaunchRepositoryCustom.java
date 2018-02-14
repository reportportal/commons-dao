package com.epam.ta.reportportal.database.dao;

import com.epam.ta.reportportal.database.entity.launch.LaunchFull;

import java.util.List;

/**
 * @author Pavel Bortnik
 */
public interface LaunchRepositoryCustom {

	/**
	 * True if the provided launch contains any items.
	 *
	 * @param launchId Checking launch id
	 * @return True if contains, false if not
	 */
	boolean hasItems(Long launchId);

	List<LaunchFull> fullLaunchWithStatistics();

}
