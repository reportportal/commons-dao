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
import com.epam.ta.reportportal.entity.user.RestorePasswordBid;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author <a href="mailto:pavel_bortnik@epam.com">Pavel Bortnik</a>
 */
class RestorePasswordBidRepositoryTest extends BaseTest {

	@Autowired
	private RestorePasswordBidRepository restorePasswordBidRepository;

	@Test
	void existsByEmail() {
		boolean b = restorePasswordBidRepository.existsByEmail("notexisted@email.com");
		assertFalse(b);
	}

	@Test
	void existsByEmailNegative() {
		RestorePasswordBid restorePasswordBid = new RestorePasswordBid();
		restorePasswordBid.setUuid("uuid");
		restorePasswordBid.setEmail("existed@email.com");
		restorePasswordBid.setLastModifiedDate(new Date());
		restorePasswordBidRepository.save(restorePasswordBid);
		boolean b = restorePasswordBidRepository.existsByEmail("existed@email.com");
		assertTrue(b);
	}
}