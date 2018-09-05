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

import com.epam.ta.reportportal.commons.Predicates;
import com.epam.ta.reportportal.commons.validation.BusinessRule;
import com.epam.ta.reportportal.ws.model.ErrorType;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperationContext;

import java.util.Arrays;
import java.util.Optional;

/**
 * Custom group operation for mongo aggregation framework
 *
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

	public static GroupingOperation build() {
		return new GroupingOperation();
	}

	@Override
	public DBObject toDBObject(AggregationOperationContext context) {
		return new BasicDBObject("$group", groupExpression.append(id, idExpression));
	}

	public GroupingOperation groupWithPeriod(GroupingPeriod period, String groupingField) {
		BusinessRule.expect(groupingField, Predicates.notNull()).verify(ErrorType.INCORRECT_REQUEST, "Grouping field shouldn't be null");
		idExpression.append(period.getValue(), new BasicDBObject(period.getOperation(), groupingField));
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

	public enum GroupingPeriod {
		BY_DAY("by_day", "$dayOfYear"),
		BY_WEEK("by_week", "$week"),
		BY_MONTH("by_month", "$month");

		private String value;

		private String operation;

		GroupingPeriod(String value, String operation) {
			this.value = value;
			this.operation = operation;
		}

		public static Optional<GroupingPeriod> getByValue(String groupingBy) {
			return Arrays.stream(GroupingPeriod.values()).filter(it -> it.getValue().equals(groupingBy)).findFirst();
		}

		public String getValue() {
			return value;
		}

		public String getOperation() {
			return operation;
		}
	}

}
