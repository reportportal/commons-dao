package com.epam.ta.reportportal.dao;

/**
 * @author Ivan Budaev
 */
public interface ServerSettingsRepositoryCustom {

	/**
	 * Delete all {@link com.epam.ta.reportportal.entity.ServerSettings} which key starts with term
	 *
	 * @param term Key restriction for {@link com.epam.ta.reportportal.entity.ServerSettings} removing
	 */
	void deleteAllByTerm(String term);
}
