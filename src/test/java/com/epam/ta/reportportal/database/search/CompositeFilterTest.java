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
import com.epam.ta.reportportal.database.entity.item.TestItem;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

/**
 * @author Andrei Varabyeu
 */
public class CompositeFilterTest {

    @Test
    public void testComposite() {
        final Filter f1 = Filter
                .builder()
                .withCondition(FilterCondition.builder().eq("name", "test1").build())
                .withTarget(TestItem.class)
                .build();

        final Filter f2 = Filter
                .builder()
                .withCondition(FilterCondition.builder().eq("name", "test2").build())
                .withTarget(TestItem.class)
                .build();

        CompositeFilter compositeFilter = new CompositeFilter(f1, f2);
        Assert.assertThat("Incorrect filter criteria", compositeFilter.toCriteria(), hasSize(2));
        Assert.assertThat("Incorrect filter target", compositeFilter.getTarget(), equalTo(TestItem.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDiffTargets() {
        final Filter f1 = Filter
                .builder()
                .withCondition(FilterCondition.builder().eq("name", "test1").build())
                .withTarget(TestItem.class)
                .build();

        final Filter f2 = Filter
                .builder()
                .withCondition(FilterCondition.builder().eq("name", "test2").build())
                .withTarget(Launch.class)
                .build();

        new CompositeFilter(f1, f2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmpty() {
        new CompositeFilter(Collections.emptyList());
    }
}
