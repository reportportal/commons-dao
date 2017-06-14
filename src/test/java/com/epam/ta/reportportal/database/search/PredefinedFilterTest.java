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
package com.epam.ta.reportportal.database.search;

import com.epam.ta.reportportal.database.entity.Launch;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.Collections;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

/**
 * @author Andrei Varabyeu
 */
public class PredefinedFilterTest {

    @Test
    public void testPredefined() {
        final Criteria criteria = Criteria.where("x").exists(true);
        PredefinedFilter f = new PredefinedFilter(Launch.class, criteria);

        Assert.assertThat("Incorrect filter criteria", f.toCriteria(), hasSize(1));
        Assert.assertThat("Incorrect filter criteria", f.toCriteria().get(0), equalTo(criteria));
        Assert.assertThat("Incorrect filter target", f.getTarget(), equalTo(Launch.class));

    }

    @Test
    public void testPredefinedEmptyOk() {
        PredefinedFilter f = new PredefinedFilter(Launch.class, Collections.emptyList());
        Assert.assertThat("Incorrect filter criteria", f.toCriteria(), hasSize(0));

    }

    @Test(expected = IllegalArgumentException.class)
    public void testPredefinedNoTarget() {
        final Criteria criteria = Criteria.where("x").exists(true);
        new PredefinedFilter(null, criteria);

    }

    @Test
    public void testPredefinedNoCriteria() {
        Criteria c = null;
        PredefinedFilter f = new PredefinedFilter(Launch.class, c);
        Assert.assertThat("Incorrect filter criteria", f.toCriteria(), hasSize(0));


    }

}
