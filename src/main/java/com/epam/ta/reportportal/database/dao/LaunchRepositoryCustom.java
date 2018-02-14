package com.epam.ta.reportportal.database.dao;

import com.epam.ta.reportportal.database.entity.launch.Launch;
import com.epam.ta.reportportal.database.entity.launch.LaunchFull;

import java.util.List;
import java.util.Optional;

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
	Boolean hasItems(Long launchId);

	/**
	 * Returns last launch for specified name and project. If it doesn't exists
	 * then returns empty Optional.
	 *
	 * @param launchName  Launch name
	 * @param projectName Project Name
	 * @return Last launch or empty Optional
	 */
	Optional<Launch> getLastLaunch(String launchName, String projectName);

	List<LaunchFull> fullLaunchWithStatistics();

}
