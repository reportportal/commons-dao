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

import org.junit.Assert;
import org.junit.Test;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;

/**
 * @author Pavel Bortnik
 */
public class AddFieldsOperationTest {

    @Test
    public void addFields() throws Exception {
        String expected = "{ \"aggregate\" : \"__collection__\" , \"pipeline\" : [ { \"$addFields\" : { \"totalHomework\" : { \"$sum\" : \"$homework\"}}}]}";
        Map<String, Object> map = new HashMap<>();
        map.put("$sum", "$homework");
        AggregationOperation totalHomework = AddFieldsOperation.addFields("totalHomework", map);
        Assert.assertEquals(expected, newAggregation(totalHomework).toString());
    }

}