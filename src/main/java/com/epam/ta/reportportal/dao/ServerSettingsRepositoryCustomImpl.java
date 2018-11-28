/*
 * Copyright (C) 2018 EPAM Systems
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
