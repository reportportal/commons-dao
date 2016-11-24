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

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import com.epam.ta.reportportal.database.entity.item.TestItem;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.epam.ta.reportportal.database.Time;
import com.epam.ta.reportportal.database.entity.HasStatus;
import com.epam.ta.reportportal.database.entity.Modifiable;
import com.epam.ta.reportportal.database.entity.Status;

/**
 * Modifiable query builder
 * 
 * @author Andrei Varabyeu
 * 
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
	public static Query findModifiedLaterThanPeriod(final Time period) {
		return findModifiedLaterThan(DateUtils.addSeconds(Calendar.getInstance().getTime(), (int) (-1 * period.in(TimeUnit.SECONDS))));
	}

	/**
	 * Finds entities with provided status and modified later than provided time period
	 * 
	 * @param period
	 * @param status
	 * @return
	 */
	public static Query findModifiedLaterThanPeriod(final Time period, final Status status) {
		return findModifiedLaterThanPeriod(period).addCriteria(Criteria.where(HasStatus.STATUS).is(status.name()));
	}

	/**
	 * Finds files with provided project and uploaded later than provided time period
	 * 
	 * @param period
	 * @param project
	 * @return
	 */
	public static Query findModifiedLaterThanPeriod(final Time period, final String project) {
		Query query = Query.query(Criteria.where(Modifiable.UPLOADED)
				.lt(DateUtils.addSeconds(Calendar.getInstance().getTime(), (int) (-1 * period.in(TimeUnit.SECONDS)))));
		return query.addCriteria(Criteria.where(METADATA).is(project));
	}

	/**
	 * Find entities modified lately
	 * 
	 * @param period
	 * @param testItem
	 * @return
	 */
	public static Query findModifiedLately(final Time period, final TestItem testItem) {
		return findModifiedLately(period).addCriteria(Criteria.where(TESTITEM_REF).is(testItem.getId()));
	}

	/**
	 * Find entities modified lately
	 * 
	 * @param period
	 * @return
	 */
	public static Query findModifiedLately(final Time period) {
		return Query.query(Criteria.where(Modifiable.LAST_MODIFIED)
				.gt(DateUtils.addSeconds(Calendar.getInstance().getTime(), (int) (-1 * period.in(TimeUnit.SECONDS)))));
	}
}