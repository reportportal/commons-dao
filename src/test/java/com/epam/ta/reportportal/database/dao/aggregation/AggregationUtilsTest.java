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

import com.epam.ta.reportportal.BaseDaoTest;
import com.epam.ta.reportportal.database.entity.Launch;
import com.epam.ta.reportportal.database.search.Filter;
import com.epam.ta.reportportal.database.search.FilterConditionUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;

/**
 * @author Pavel Bortnik
 */
public class AggregationUtilsTest extends BaseDaoTest{

    @Autowired
    private MongoOperations mongoOperations;

    @Test
    public void matchOperationFromFilter() {
        String expected = "{ \"aggregate\" : \"__collection__\" , \"pipeline\" : [ { \"$match\" : { \"$and\" : [ { \"status\" : { \"$ne\" : \"IN_PROGRESS\"}}]}}]}";
        Filter filter = Filter.builder()
                .withTarget(Launch.class)
                .withCondition(FilterConditionUtils.LAUNCH_NOT_IN_PROGRESS())
                .build();
        MatchOperation matchOperation = AggregationUtils.matchOperationFromFilter(filter, mongoOperations, Launch.class);
        Assert.assertEquals(expected, newAggregation(matchOperation).toString());
    }

}