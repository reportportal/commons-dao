package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.BaseTest;
import com.epam.ta.reportportal.entity.ShareableEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author <a href="mailto:ivan_budayeu@epam.com">Ivan Budayeu</a>
 */
class ShareableEntityRepositoryTest extends BaseTest {

	@Autowired
	private ShareableEntityRepository shareableEntityRepository;

	@Test
	@Sql("/db/fill/shareable/shareable-fill.sql")
	void findAllByProjectIdAndShared() {
		List<ShareableEntity> shared = shareableEntityRepository.findAllByProjectIdAndShared(1L, true);
		List<ShareableEntity> notShared = shareableEntityRepository.findAllByProjectIdAndShared(1L, false);

		Assertions.assertEquals(6, shared.size());
		Assertions.assertEquals(5, notShared.size());
	}
}