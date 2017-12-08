package com.epam.ta.reportportal.database.entity.history.status;

import com.epam.ta.reportportal.database.entity.item.TestItem;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

/**
 *
 * Does not a db object representation. Result of
 * {@link com.epam.ta.reportportal.database.dao.TestItemRepositoryCustomImpl#findRetries(String)}
 * query
 *
 * @author Pavel Bortnik
 */
public class RetryObject {

	@Field(value = "_id")
	private String id;

	private List<TestItem> retries;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<TestItem> getRetries() {
		return retries;
	}

	public void setRetries(List<TestItem> retries) {
		this.retries = retries;
	}
}
