package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.BaseTest;
import com.epam.ta.reportportal.entity.project.email.SenderCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.Collections;
import java.util.List;

@Sql("/db/fill/sendercase/sender-case-fill.sql")
public class SenderCaseRepositoryTest extends BaseTest {

	@Autowired
	private SenderCaseRepository senderCaseRepository;

	@Test
	void findAllByProjectId() {
		final List<SenderCase> senderCases = senderCaseRepository.findAllByProjectId(1L);
		Assertions.assertFalse(senderCases.isEmpty());
	}

	@Test
	void deleteRecipients() {
		final int removed = senderCaseRepository.deleteRecipients(1L, Collections.singleton("first"));
		Assertions.assertEquals(1, removed);

		final SenderCase updated = senderCaseRepository.findById(1L).get();
		Assertions.assertEquals(1, updated.getRecipients().size());
		Assertions.assertFalse(updated.getRecipients().contains("first"));
	}
}
