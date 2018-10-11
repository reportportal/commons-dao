package com.epam.ta.reportportal.dao;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import static com.epam.ta.reportportal.jooq.tables.JServerSettings.SERVER_SETTINGS;

/**
 * @author Ivan Budaev
 */
@Repository
public class ServerSettingsRepositoryCustomImpl implements ServerSettingsRepositoryCustom {

	private final DSLContext dsl;

	@Autowired
	public ServerSettingsRepositoryCustomImpl(DSLContext dsl) {
		this.dsl = dsl;
	}

	@Override
	public void deleteAllByTerm(String term) {
		dsl.deleteFrom(SERVER_SETTINGS).where(SERVER_SETTINGS.KEY.like(term + "%")).execute();
	}
}
