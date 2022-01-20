package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.BaseTest;
import com.epam.ta.reportportal.entity.materialized.StaleMaterializedView;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author <a href="mailto:ivan_budayeu@epam.com">Ivan Budayeu</a>
 */
class StaleMaterializedViewRepositoryTest extends BaseTest {

	@Autowired
	private StaleMaterializedViewRepository staleMaterializedViewRepository;

	@Test
	void shouldInsertAndSetId() {

		final StaleMaterializedView staleMaterializedView = new StaleMaterializedView();
		staleMaterializedView.setName("test");
		staleMaterializedView.setCreationDate(LocalDateTime.now(ZoneOffset.UTC));

		final StaleMaterializedView result = staleMaterializedViewRepository.insert(staleMaterializedView);

		assertNotNull(staleMaterializedView.getId());
		assertNotNull(result.getId());
		assertEquals(result.getId(), staleMaterializedView.getId());

		final Optional<StaleMaterializedView> found = staleMaterializedViewRepository.findById(1L);
		assertTrue(found.isPresent());

		final Optional<StaleMaterializedView> notFound = staleMaterializedViewRepository.findById(2L);
		assertTrue(notFound.isEmpty());

	}

}