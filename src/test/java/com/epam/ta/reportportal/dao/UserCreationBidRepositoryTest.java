/*
 * Copyright 2019 EPAM Systems
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
import com.epam.ta.reportportal.entity.user.UserCreationBid;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author <a href="mailto:ihar_kahadouski@epam.com">Ihar Kahadouski</a>
 */
@Sql("/db/fill/user-bid/user-bid-fill.sql")
class UserCreationBidRepositoryTest extends BaseTest {

	@Autowired
	private UserCreationBidRepository repository;

	@Test
	void findByEmail() {
		final String email = "superadminemail@domain.com";

		final Optional<UserCreationBid> userBid = repository.findByEmail(email);

		assertTrue(userBid.isPresent(), "User bid should exists");
		assertEquals(email, userBid.get().getEmail(), "Incorrect email");
	}

	@Test
	void expireBidsOlderThan() {
		final java.util.Date date = Date.from(LocalDateTime.now().minusDays(20).atZone(ZoneId.systemDefault()).toInstant());

		int deletedCount = repository.expireBidsOlderThan(date);
		final List<UserCreationBid> bids = repository.findAll();

		assertEquals(1, deletedCount);
		bids.forEach(it -> assertTrue(it.getLastModified().after(date), "Incorrect date"));
	}

	@Test
	void findById() {
		final String adminUuid = "0647cf8f-02e3-4acd-ba3e-f74ec9d2c5cb";

		final Optional<UserCreationBid> bid = repository.findById(adminUuid);

		assertTrue(bid.isPresent(), "User bid should exists");
		assertEquals(adminUuid, bid.get().getUuid(), "Incorrect uuid");
		assertEquals("superadminemail@domain.com", bid.get().getEmail(), "Incorrect email");
	}

	@Test
	void deleteAllByEmail() {
		int deletedCount = repository.deleteAllByEmail("defaultemail@domain.com");
		assertEquals(2, deletedCount);
	}
}
