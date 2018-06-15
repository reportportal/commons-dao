package com.epam.ta.reportportal.database.dao.aggregation;

import com.epam.ta.reportportal.commons.Predicates;
import com.epam.ta.reportportal.commons.validation.BusinessRule;
import com.epam.ta.reportportal.exception.ReportPortalException;
import com.epam.ta.reportportal.ws.model.ErrorType;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperationContext;

import java.util.Arrays;

/**
 * @author Pavel Bortnik
 */
public class GroupingOperation implements AggregationOperation {

	private String id = "_id";

	private BasicDBObject idExpression;

	private BasicDBObject groupExpression;

	public GroupingOperation() {
		idExpression = new BasicDBObject();
		groupExpression = new BasicDBObject();
	}

	@Override
	public DBObject toDBObject(AggregationOperationContext context) {
		return new BasicDBObject("$group", groupExpression.append(id, idExpression));
	}

	public static GroupingOperation group() {
		return new GroupingOperation();
	}

	public GroupingOperation withPeriodId(String period, String groupingField) {
		BusinessRule.expect(groupingField, Predicates.notNull()).verify(ErrorType.INCORRECT_REQUEST, "Grouping field shouldn't be null");
		GroupingBy groupBy = GroupingBy.getByValue(period);
		idExpression.append(groupBy.getValue(), new BasicDBObject(groupBy.getOperation(), groupingField));
		return this;
	}

	public GroupingOperation withFieldId(String alias, Object value) {
		idExpression.append(alias, value);
		return this;
	}

	public GroupingOperation push(String alias, Object value) {
		groupExpression.append(alias, new BasicDBObject("$push", value));
		return this;
	}

	public GroupingOperation sum(String alias, String contentField) {
		groupExpression.append(alias, new BasicDBObject("$sum", contentField));
		return this;
	}

	public GroupingOperation first(String alias, String field) {
		groupExpression.append(alias, new BasicDBObject("$first", field));
		return this;
	}

	public enum GroupingBy {
		BY_DAY("by_day", "$dayOfYear"),
		BY_WEEK("by_week", "$week"),
		BY_MONTH("by_month", "$month");

		private String value;

		private String operation;

		GroupingBy(String value, String operation) {
			this.value = value;
			this.operation = operation;
		}

		public String getValue() {
			return value;
		}

		public String getOperation() {
			return operation;
		}

		public static GroupingBy getByValue(String groupingBy) {
			return Arrays.stream(GroupingBy.values())
					.filter(it -> it.getValue().equals(groupingBy))
					.findFirst()
					.orElseThrow(
							() -> new ReportPortalException(ErrorType.INCORRECT_REQUEST, groupingBy + " type of grouping is unsupported"));
		}
	}

}
