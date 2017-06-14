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

package com.epam.ta.reportportal.database.entity;

import java.util.Arrays;
import java.util.Optional;

/**
 * Project types in accordance with testing way<br>
 * Could be DEFAULT or BDD (not sure if it is required)
 * 
 * @author Andrei_Ramanchuk
 */
// TODO Exceptions handling
public enum ProjectSpecific {

	DEFAULT, BDD;

	public static ProjectSpecific getByName(String type) {
		return ProjectSpecific.valueOf(type);
	}

	public static Optional<ProjectSpecific> findByName(String name) {
		return Arrays.stream(ProjectSpecific.values()).filter(type -> type.name()
				.equalsIgnoreCase(name)).findAny();
	}

	public static boolean isPresent(String name) {
		return findByName(name).isPresent();
	}
}