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
package com.epam.ta.reportportal.database;

import com.epam.ta.reportportal.database.entity.LogLevel;
import com.epam.ta.reportportal.database.entity.item.Activity;
import com.epam.ta.reportportal.database.entity.item.ActivityEventType;
import com.epam.ta.reportportal.database.entity.item.ActivityObjectType;
import com.epam.ta.reportportal.exception.ReportPortalException;
import com.google.common.collect.Lists;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Pavel Bortnik
 */
public class CustomMongoConvertersTest {

    @Test
    public void convertClassToWrappers() {
        Class<Activity> aClass = Activity.class;
        DBObject object = CustomMongoConverters.ClassToWrapperConverter.INSTANCE.convert(aClass);
        Assert.assertEquals(Activity.class.getName(), object.get("className"));
    }

    @Test
    public void convertWrapperToClass() {
        DBObject className = new BasicDBObject("className", "com.epam.ta.reportportal.database.entity.item.Activity");
        Class<?> convert = CustomMongoConverters.WrapperToClassConverter.INSTANCE.convert(className);
        Assert.assertEquals(Activity.class, convert);
    }

    @Test(expected = ReportPortalException.class)
    public void negativeWrapperToClass() {
        DBObject className = new BasicDBObject("className", "incorrect class");
        CustomMongoConverters.WrapperToClassConverter.INSTANCE.convert(className);
    }

    @Test
    public void convertLogLevel() {
        LogLevel[] values = LogLevel.values();
        Arrays.stream(values).forEach(it -> {
            DBObject convert = CustomMongoConverters.LogLevelToIntConverter.INSTANCE.convert(it);
            Assert.assertEquals(convert.get("log_level"), it.toInt());
        });
    }

    @Test
    public void convertIntToLogLevel() {
        List<LogLevel> values = Lists.newArrayList(LogLevel.values());
        List<BasicDBObject> objects = Arrays.stream(LogLevel.values()).map(LogLevel::toInt).map(it ->
                new BasicDBObject("log_level", it)).collect(Collectors.toList());
        List<LogLevel> levels = objects.stream()
                .map(CustomMongoConverters.IntToLogLevelConverter.INSTANCE::convert).collect(Collectors.toList());
        Assert.assertEquals(values, levels);
    }

    @Test
    public void convertActivityObject() {
        ActivityObjectType[] values = ActivityObjectType.values();
        Arrays.stream(values).forEach(it -> {
            String convert = CustomMongoConverters.ActivityObjectTypeToStringConverter.INSTANCE.convert(it);
            Assert.assertEquals(it.getValue(), convert);
        });
    }

    @Test
    public void convertStringToActivityObject() {
        List<String> strings = Arrays.stream(ActivityObjectType.values()).map(ActivityObjectType::getValue).collect(Collectors.toList());
        strings.forEach(it -> {
            ActivityObjectType convert = CustomMongoConverters.StringToActivityObjectTypeConverter.INSTANCE.convert(it);
            Optional<ActivityObjectType> type = ActivityObjectType.fromString(it);
            Assert.assertTrue(type.isPresent());
            Assert.assertEquals(type.get(), convert);
        });

    }

    @Test
    public void convertActivityEvent() {
        ActivityEventType[] values = ActivityEventType.values();
        Arrays.stream(values).forEach(it -> {
            String convert = CustomMongoConverters.ActivityEventTypeToStringConverter.INSTANCE.convert(it);
            Assert.assertEquals(it.getValue(), convert);
        });
    }

    @Test
    public void convertStringToActivityEvent() {
        List<String> strings = Arrays.stream(ActivityEventType.values()).map(ActivityEventType::getValue).collect(Collectors.toList());
        strings.forEach(it -> {
            ActivityEventType convert = CustomMongoConverters.StringToActivityEventTypeConverter.INSTANCE.convert(it);
            Optional<ActivityEventType> type = ActivityEventType.fromString(it);
            Assert.assertTrue(type.isPresent());
            Assert.assertEquals(type.get(), convert);
        });
    }

}