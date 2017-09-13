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
