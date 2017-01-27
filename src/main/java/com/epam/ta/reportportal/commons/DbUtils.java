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

package com.epam.ta.reportportal.commons;

import org.springframework.hateoas.Identifiable;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Useful utils to be able to work with mongo database
 *
 * @author Andrei Varabyeu
 */
public class DbUtils {

    private DbUtils() {

    }

    /**
     * Converts objects to IDs
     *
     * @param objects Objects to be converter
     * @return Converted list
     */
    public static List<String> toIds(Iterable<? extends Identifiable<String>> objects) {
        return StreamSupport.stream(objects.spliterator(), false).map(Identifiable::getId).collect(Collectors.toList());
    }
}
