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
import com.epam.ta.reportportal.dao.TicketRepository;
import com.epam.ta.reportportal.entity.bts.Ticket;
import org.flywaydb.test.annotation.FlywayTest;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

/**
 * @author <a href="mailto:ihar_kahadouski@epam.com">Ihar Kahadouski</a>
 */
public class TicketRepositoryTest extends BaseTest {

	private static final String FILL_SCRIPT_PATH = "/db/fill/ticket";

	@Autowired
	private TicketRepository repository;

	@FlywayTest(locationsForMigrate = { FILL_SCRIPT_PATH }, invokeCleanDB = false)
	@BeforeClass
	public static void before() {
	}

	@Test
	public void findByTicketId() {
		final String ticketId = "ticket_id_1";

		final Optional<Ticket> ticketOptional = repository.findByTicketId(ticketId);

		assertTrue("Ticket not found", ticketOptional.isPresent());
		assertEquals("Incorrect ticket id", ticketId, ticketOptional.get().getTicketId());
	}

	@Test
	public void findByTicketIdIn() {
		List<String> ids = Arrays.asList("ticket_id_1", "ticket_id_3");

		final List<Ticket> tickets = repository.findByTicketIdIn(ids);

		assertNotNull("Tickets not found", tickets);
		assertEquals("Incorrect tickets count", 2, tickets.size());
		assertThat(tickets.stream().map(Ticket::getTicketId).collect(Collectors.toList())).containsExactlyInAnyOrder(ids.get(0),
				ids.get(1)
		);
	}

	@Test
	public void deleteById() {
		repository.deleteById(2L);
		final List<Ticket> tickets = repository.findAll();

		assertEquals(2, tickets.size());
	}
}
