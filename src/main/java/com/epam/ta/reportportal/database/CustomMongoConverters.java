/*
 * Copyright 2016 EPAM Systems
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
 
package com.epam.ta.reportportal.database;

import com.epam.ta.reportportal.database.entity.LogLevel;
import com.epam.ta.reportportal.database.entity.item.ActivityEventType;
import com.epam.ta.reportportal.database.entity.item.ActivityObjectType;
import com.epam.ta.reportportal.exception.ReportPortalException;
import com.epam.ta.reportportal.ws.model.ErrorType;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

public class CustomMongoConverters {

	private CustomMongoConverters() {
		//statics only
	}
	public enum LogLevelToIntConverter implements Converter<LogLevel, DBObject> {
		INSTANCE;

		@Override
		public DBObject convert(LogLevel source) {
			DBObject dbObject = new BasicDBObject();
			dbObject.put("log_level", source.toInt());
			return dbObject;
		}
	}

	public enum IntToLogLevelConverter implements Converter<DBObject, LogLevel> {
		INSTANCE;

		@Override
		public LogLevel convert(DBObject source) {
			return LogLevel.toLevel((Integer) source.get("log_level"));
		}

	}

	public enum ClassToWrapperConverter implements Converter<Class<?>, DBObject> {
		INSTANCE;

		@Override
		public DBObject convert(Class<?> source) {

			DBObject dbo = new BasicDBObject();
			dbo.put("className", source.getName());
			return dbo;
		}
	}

	public enum WrapperToClassConverter implements Converter<DBObject, Class<?>> {
		INSTANCE;

		@Override
		public Class<?> convert(DBObject source) {
			String className = (String) source.get("className");
			try {
				return Class.forName(className);
			} catch (ClassNotFoundException e) {
				// TODO Add custom exception
				throw new ReportPortalException("Unable convert string '" + className + "' to Class object", e);
			}

		}
	}

	public enum ActivityEventTypeToStringConverter implements Converter<ActivityEventType, String> {
		INSTANCE;

		@Override
		public String convert(ActivityEventType source) {
			return null == source ? null : source.getValue();
		}
	}

	@ReadingConverter
	public enum StringToActivityEventTypeConverter implements Converter<String, ActivityEventType> {
		INSTANCE;

		@Override
		public ActivityEventType convert(String source) {
			return null == source ? null : ActivityEventType.fromString(source)
					.orElseThrow(() -> new ReportPortalException(ErrorType.UNCLASSIFIED_ERROR));
		}
	}

	public enum ActivityObjectTypeToStringConverter implements Converter<ActivityObjectType, String> {
		INSTANCE;

		@Override
		public String convert(ActivityObjectType source) {
			return null == source ? null : source.getValue();
		}
	}

	@ReadingConverter
	public enum StringToActivityObjectTypeConverter implements Converter<String, ActivityObjectType> {
		INSTANCE;

		@Override
		public ActivityObjectType convert(String source) {
			return null == source ? null : ActivityObjectType.fromString(source)
					.orElseThrow(() -> new ReportPortalException(ErrorType.UNCLASSIFIED_ERROR));
		}
	}
}