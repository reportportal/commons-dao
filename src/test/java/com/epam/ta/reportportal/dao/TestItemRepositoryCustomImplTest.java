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

/**
 * @author Ivan Budaev
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
@Transactional("transactionManager")
public class TestItemRepositoryCustomImplTest {

	@Autowired
	private TestItemRepository testItemRepository;

	@Test
	public void selectAllDescendantsWithChildren() {

		List<TestItem> testItems = testItemRepository.selectAllDescendantsWithChildren(33L);

		Assert.assertNotNull(testItems);
	}
}