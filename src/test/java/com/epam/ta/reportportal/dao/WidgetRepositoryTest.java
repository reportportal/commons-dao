package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.config.TestConfiguration;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

/**
 * @author <a href="mailto:ivan_budayeu@epam.com">Ivan Budayeu</a>
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
@Transactional("transactionManager")
public class WidgetRepositoryTest {

	@Autowired
	private WidgetRepository widgetRepository;

	@Test
	public void existsByNameAndOwnerAndProjectId() {

		Assert.assertTrue(widgetRepository.existsByNameAndOwnerAndProjectId("LAUNCH TABLE", "superadmin", 1L));
		Assert.assertFalse(widgetRepository.existsByNameAndOwnerAndProjectId("LAUNCH TABLE", "yahoo", 1L));
		Assert.assertFalse(widgetRepository.existsByNameAndOwnerAndProjectId("LAUNCH TABLE", "superadmin", 2L));
	}
}