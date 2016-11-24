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

package com.epam.ta.reportportal.database.entity.filter;

import com.epam.ta.reportportal.commons.Predicates;
import com.epam.ta.reportportal.commons.validation.BusinessRule;
import com.epam.ta.reportportal.database.entity.Launch;
import com.epam.ta.reportportal.database.entity.Log;
import com.epam.ta.reportportal.database.entity.item.TestItem;
import com.epam.ta.reportportal.database.search.Filter;
import com.epam.ta.reportportal.ws.model.ErrorType;

/**
 * Describe possible object types for {@link Filter} object<br>
 * construction and contains functionality for calculating classes objects <br>
 * by appropriate ObjectType's elements.
 * 
 * @author Aliaksei_Makayed
 * 
 */
public enum ObjectType {

	Launch(Launch.class), TestItem(TestItem.class), Log(Log.class);

	private Class<?> classObject;

	ObjectType(Class<?> classObject) {
		this.classObject = classObject;
	}

	public static Class<?> getTypeByName(String name) {
		ObjectType[] values = ObjectType.values();
		for (ObjectType type : values) {
			if (type.name().equalsIgnoreCase(name)) {
				return type.classObject;
			}
		}

		/*
		 * Dirty hack. Decide how to avoid this
		 */
		BusinessRule.expect(true, Predicates.alwaysFalse()).verify(ErrorType.BAD_SAVE_USER_FILTER_REQUEST,
				"Unnown Filter object type '" + name + "'. Possible types: log, launch, testItem.");
		return null;
	}
}