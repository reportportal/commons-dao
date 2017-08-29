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

import com.epam.ta.reportportal.database.search.Queryable;
import com.mongodb.DBObject;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.AggregationOperationContext;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.TypeBasedAggregationOperationContext;
import org.springframework.data.mongodb.core.convert.QueryMapper;
import org.springframework.data.mongodb.core.query.Criteria;

import static com.google.common.collect.Iterables.toArray;

/**
 * @author Andrei_Varabyeu
 * @author Pavel Bortnik
 */
public final class AggregationUtils {

    private AggregationUtils() {
        //static only
    }

    /**
     * Creates match operation for aggregation from filter
     *
     * @param filter            filter
     * @param mongoOperations   MongoOperations
     * @param entityType        entity type
     * @return                  MatchOperation
     */
    public static <T> MatchOperation matchOperationFromFilter(Queryable filter, MongoOperations mongoOperations, Class<T> entityType) {
        return new MatchOperation(new Criteria().andOperator(toArray(filter.toCriteria(), Criteria.class))) {
            @Override
            public DBObject toDBObject(AggregationOperationContext context) {
                return super.toDBObject(new TypeBasedAggregationOperationContext(entityType,
                        mongoOperations.getConverter().getMappingContext(), new QueryMapper(mongoOperations.getConverter())));
            }
        };
    }

}
