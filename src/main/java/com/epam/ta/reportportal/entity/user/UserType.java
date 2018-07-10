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

package com.epam.ta.reportportal.entity.user;

import java.util.Arrays;
import java.util.Optional;

/**
 * User Type enumeration<br>
 * Used for supporting different project types processing
 *
 * @author <a href="mailto:andrei_varabyeu@epam.com">Andrei Varabyeu</a>
 */
public enum UserType {

	//@formatter:off
    INTERNAL,
    UPSA,
    GITHUB,
    LDAP;
    //@formatter:on

	public static Optional<UserType> forName(final String name) {
		return Arrays.stream(UserType.values()).filter(role -> role.name().equalsIgnoreCase(name)).findAny();
	}
}