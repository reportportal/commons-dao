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

package com.epam.ta.reportportal.dao.suite;

import com.epam.ta.reportportal.BaseTest;
import com.epam.ta.reportportal.dao.UserCreationBidRepository;
import com.epam.ta.reportportal.entity.user.UserCreationBid;
import org.flywaydb.test.annotation.FlywayTest;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author <a href="mailto:ihar_kahadouski@epam.com">Ihar Kahadouski</a>
 */
public class UserCreationBidRepositoryTest extends BaseTest {

	private static final String FILL_SCRIPT_PATH = "/db/fill/user-bid";

	@Autowired
	private UserCreationBidRepository repository;

	@FlywayTest(locationsForMigrate = { FILL_SCRIPT_PATH }, invokeCleanDB = false)
	@BeforeClass
	public static void before() {
	}

	@Test
	public void findByEmail() {
		final String email = "superadminemail@domain.com";
		final Optional<UserCreationBid> userBid = repository.findByEmail(email);
		assertTrue(userBid.isPresent());
		assertEquals(email, userBid.get().getEmail());
	}

	@Test
	public void expireBidsOlderThan() {
		final java.util.Date date = Date.from(LocalDateTime.now().minusDays(20).atZone(ZoneId.systemDefault()).toInstant());
		repository.expireBidsOlderThan(date);

		final List<UserCreationBid> bids = repository.findAll();
		bids.forEach(it -> assertTrue(it.getLastModified().after(date)));
	}

	@Test
	public void findById() {
		final String adminUuid = "0647cf8f-02e3-4acd-ba3e-f74ec9d2c5cb";
		final Optional<UserCreationBid> bid = repository.findById(adminUuid);
		assertTrue(bid.isPresent());
		assertEquals(adminUuid, bid.get().getUuid());
		assertEquals("superadminemail@domain.com", bid.get().getEmail());
	}
}
