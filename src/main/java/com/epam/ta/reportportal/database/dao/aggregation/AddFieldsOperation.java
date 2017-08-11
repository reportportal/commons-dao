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
import org.springframework.data.mongodb.core.aggregation.*;

import java.util.Map;

/**
 *  Encapsulates the aggregation framework {@code $addFields}-operation.
 *  Simple resolution while waiting implementation in aggregation framework.
 */

public class AddFieldsOperation implements AggregationOperation {

    private Field field;

    private Map<String, Object> expression;

    public AddFieldsOperation(String field, Map<String, Object> expression) {
        this.field = Fields.field(field);
        this.expression = expression;
    }

    @Override
    public DBObject toDBObject(AggregationOperationContext context) {
        BasicDBObject fieldObject = new BasicDBObject();
        fieldObject.put(field.getName(), new BasicDBObject(expression));
        return new BasicDBObject("$addFields", fieldObject);
    }

    public static AggregationOperation addFields(String field, Map<String, Object> expression) {
        return new AddFieldsOperation(field, expression);
    }

}
