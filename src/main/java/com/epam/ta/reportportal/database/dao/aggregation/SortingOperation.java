/*
 * Copyright 2017 EPAM Systems
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
package com.epam.ta.reportportal.database.dao.aggregation;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperationContext;

/**
 * Custom sorting operations ignoring spring aggregation context
 *
 * @author Pavel Bortnik
 */
public class SortingOperation implements AggregationOperation {

	private final Sort sort;

	SortingOperation(Sort sort) {
		this.sort = sort;
	}

	@Override
	public DBObject toDBObject(AggregationOperationContext context) {
		BasicDBObject object = new BasicDBObject();
		sort.forEach(it -> object.put(it.getProperty(), it.isAscending() ? 1 : -1));
		return new BasicDBObject("$sort", object);
	}

	public static SortingOperation sorting(String field, Sort.Direction direction) {
		return new SortingOperation(new Sort(direction, field));
	}

	public SortingOperation and(Sort.Direction direction, String... fields) {
		return and(new Sort(direction, fields));
	}

	public SortingOperation and(Sort sort) {
		return new SortingOperation(this.sort.and(sort));
	}
}
