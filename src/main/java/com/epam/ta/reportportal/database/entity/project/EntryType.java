/*
 * Copyright 2016 EPAM Systems
 * 
 * 
 * This file is part of EPAM Report Portal.
 * https://github.com/epam/ReportPortal
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

package com.epam.ta.reportportal.database.entity.project;

import java.util.Arrays;

/**
 * UserType enumeration<br>
 * Used for supporting different user_account/project types processing
 *
 * @author Andrei_Ramanchuk
 * @author <a href="mailto:andrei_varabyeu@epam.com">Andrei Varabyeu</a>
 */
//TODO Exception handling
public enum EntryType {

    //@formatter:off
    PERSONAL,
    INTERNAL,
    UPSA;

    //@formatter:on
    public static EntryType getByName(String type) {
        return EntryType.valueOf(type);
    }

    public static EntryType findByName(String name) {
        return Arrays.stream(EntryType.values()).filter(type -> type.name().equals(name)).findAny().orElse(null);
    }

    public static boolean isPresent(String name) {
        return null != findByName(name);
    }
}