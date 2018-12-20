package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.BaseTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author <a href="mailto:ivan_budayeu@epam.com">Ivan Budayeu</a>
 */
public class WidgetRepositoryTest extends BaseTest {

	@Autowired
	private WidgetRepository widgetRepository;

	@Test
	public void existsByNameAndOwnerAndProjectId() {

		Assert.assertTrue(widgetRepository.existsByNameAndOwnerAndProjectId("LAUNCH TABLE", "superadmin", 1L));
		Assert.assertFalse(widgetRepository.existsByNameAndOwnerAndProjectId("LAUNCH TABLE", "yahoo", 1L));
		Assert.assertFalse(widgetRepository.existsByNameAndOwnerAndProjectId("LAUNCH TABLE", "superadmin", 2L));
	}
}