/*
 * Copyright 2016 EPAM Systems
 * 
 * 
 * This file is part of EPAM Report Portal.
 * https://github.com/reportportal/commons-dao
 * 
 * Report Portal is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Report Portal is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Report Portal.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.epam.ta.reportportal.database.search;

import com.epam.ta.reportportal.database.entity.HasStatus;
import com.epam.ta.reportportal.database.entity.Modifiable;
import com.epam.ta.reportportal.database.entity.Status;
import com.epam.ta.reportportal.database.entity.item.TestItem;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;

/**
 * Modifiable query builder
 *
 * @author Andrei Varabyeu
 */
public class ModifiableQueryBuilder {

	private static final String METADATA = "metadata.project";

	private static final String TESTITEM_REF = "testItemRef";

	private ModifiableQueryBuilder() {

	}

	/**
	 * Query for entities modified later than provided date
	 *
	 * @param date
	 * @return
	 */
	public static Query findModifiedLaterThan(final Date date) {
		return Query.query(Criteria.where(Modifiable.LAST_MODIFIED).lt(date));
	}

	/**
	 * Query for entities modified later than provided time period
	 *
	 * @param period
	 * @return
	 */
	public static Query findModifiedLaterThanPeriod(final Duration period) {
		return findModifiedLaterThan(Date.from(Instant.now().minusSeconds(period.getSeconds())));
	}

	/**
	 * Finds entities with provided status and modified later than provided time period
	 *
	 * @param period
	 * @param status
	 * @return
	 */
	public static Query findModifiedLaterThanPeriod(final Duration period, final Status status) {
		return findModifiedLaterThanPeriod(period).addCriteria(Criteria.where(HasStatus.STATUS).is(status.name()));
	}

	public static Query findModifiedInPeriod(final Duration from, final Duration to) {
		return Query.query(Criteria.where(Modifiable.LAST_MODIFIED)
				.gte(Date.from(Instant.now().minusSeconds(from.getSeconds())))
				.lte((Date.from(Instant.now().minusSeconds(to.getSeconds())))));
	}

	/**
	 * Finds files with provided project and uploaded later than provided time period
	 *
	 * @param period
	 * @param project
	 * @return
	 */
	public static Query findModifiedLaterThanPeriod(final Duration period, final String project) {
		Query query = Query.query(Criteria.where(Modifiable.UPLOADED).lt(Date.from(Instant.now().minusSeconds(period.getSeconds()))));
		return query.addCriteria(Criteria.where(METADATA).is(project));
	}

	/**
	 * Find entities modified lately
	 *
	 * @param period
	 * @param testItem
	 * @return
	 */
	public static Query findModifiedLately(final Duration period, final TestItem testItem) {
		return findModifiedLately(period).addCriteria(Criteria.where(TESTITEM_REF).is(testItem.getId()));
	}

	/**
	 * Find entities modified lately
	 *
	 * @param period
	 * @return
	 */
	public static Query findModifiedLately(final Duration period) {
		return Query.query(Criteria.where(Modifiable.LAST_MODIFIED).gt(Date.from(Instant.now().minusSeconds(period.getSeconds()))));
	}
}