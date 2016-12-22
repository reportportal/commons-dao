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
/*
 * This file is part of Report Portal.
 *
 * Report Portal is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Report Portal is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Report Portal.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.epam.ta.reportportal.database.entity.user;

import java.util.List;

import com.google.common.collect.Lists;

/**
 * @author Dzmitry_Kavalets
 */
public enum ImageFormat {

	JPEG("JPEG"), PNG("PNG"), GIF("GIF");

	private String value;

	ImageFormat(String value) {
		this.value = value;
	}

	public static ImageFormat fromValue(String value) {
		ImageFormat[] formats = ImageFormat.values();
		for (ImageFormat format : formats) {
			if (value.toUpperCase().equals(format.value)) {
				return format;
			}
		}
		return null;
	}

	public static List<String> getValues() {
		List<String> allowed = Lists.newArrayList();
		ImageFormat[] formats = ImageFormat.values();
		for (ImageFormat format : formats) {
			allowed.add(format.value);
		}
		return allowed;
	}
}