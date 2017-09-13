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

	private final String field;

	private final Sort.Direction direction;

	private SortingOperation(String field, Sort.Direction direction) {
		this.field = field;
		this.direction = direction;
	}

	@Override
	public DBObject toDBObject(AggregationOperationContext context) {
		BasicDBObject object = new BasicDBObject(field, direction.isAscending() ? 1 : -1);
		return new BasicDBObject("$sorting", object);
	}

	public static SortingOperation sorting(String field, Sort.Direction direction) {
		return new SortingOperation(field, direction);
	}
}
