package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.config.TestConfiguration;
import com.epam.ta.reportportal.entity.item.TestItem;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Ivan Budaev
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
@Transactional("transactionManager")
public class TestItemRepositoryTest {

	@Autowired
	private TestItemRepository testItemRepository;

	@Test
	public void findTestItemsByLaunchId() {

		List<TestItem> testItemList = testItemRepository.findTestItemsByLaunchId(1L);

		Assert.assertNotNull(testItemList);
	}
}