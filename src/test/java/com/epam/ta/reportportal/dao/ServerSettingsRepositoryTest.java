/*
 * Copyright 2018 EPAM Systems
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

import com.epam.ta.reportportal.BaseTest;
import com.epam.ta.reportportal.entity.ServerSettings;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author <a href="mailto:ihar_kahadouski@epam.com">Ihar Kahadouski</a>
 */
@Sql("/db/fill/server-settings/server-settings-fill.sql")
class ServerSettingsRepositoryTest extends BaseTest {

	@Autowired
	private ServerSettingsRepository repository;

	@Test
	void findByKeyPositive() {
		final String key = "server.analytics.all";
		final Optional<ServerSettings> serverSettingsOptional = repository.findByKey(key);

		assertTrue(serverSettingsOptional.isPresent());
		assertEquals(key, serverSettingsOptional.get().getKey());
	}

	@Test
	void findByKeyNegative() {
		assertFalse(repository.findByKey("no_such_key").isPresent());
	}

	@Test
	void streamAll() {
		final Stream<ServerSettings> serverSettingsStream = repository.streamAll();
		assertEquals(14, serverSettingsStream.collect(Collectors.toList()).size());
	}

	@Test
	void name() {
		repository.deleteAllByTerm("key");
		assertEquals(11, repository.findAll().size());
	}
}
