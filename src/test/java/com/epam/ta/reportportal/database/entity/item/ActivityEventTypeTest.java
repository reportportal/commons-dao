/*
 * Copyright 2017 EPAM Systems
 *
 *
 * This file is part of EPAM Report Portal.
 * https://github.com/reportportal/service-dao
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
package com.epam.ta.reportportal.database.entity.item;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Pavel Bortnik
 */
public class ActivityEventTypeTest {

    @Test
    public void fromString() {
        List<ActivityEventType> values = Arrays.stream(ActivityEventType.values()).collect(Collectors.toList());
        List<String> strings = values.stream().map(ActivityEventType::getValue).collect(Collectors.toList());
        Assert.assertEquals(values.size(), strings.size());
        for (int i = 0; i < strings.size(); i++) {
            Optional<ActivityEventType> type = ActivityEventType.fromString(strings.get(i));
            Assert.assertTrue(type.isPresent());
            Assert.assertEquals(type.get(), values.get(i));
        }
        Assert.assertFalse(ActivityEventType.fromString("no_such_activity").isPresent());
    }

}