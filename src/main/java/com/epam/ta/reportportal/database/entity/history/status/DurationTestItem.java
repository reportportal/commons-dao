package com.epam.ta.reportportal.database.entity.history.status;

import com.epam.ta.reportportal.database.entity.Status;
import com.epam.ta.reportportal.database.entity.item.TestItemType;

/**
 * @author Pavel Bortnik
 */
public interface DurationTestItem {
	String getId();

	String getName();

	String getUniqueId();

	Long getDuration();

	Status getStatus();

	TestItemType getType();
}
