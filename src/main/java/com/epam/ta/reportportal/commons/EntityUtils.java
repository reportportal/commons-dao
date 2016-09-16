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

package com.epam.ta.reportportal.commons;

import com.google.common.base.Preconditions;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Some useful utils for working with entities<br>
 * For example: usernames, project names, tags, etc.
 * 
 * @author Andrei Varabyeu
 * 
 */
public class EntityUtils {

	private EntityUtils() {

	}

	/**
	 * Normalize any ID for database ID fields, for example
	 * 
	 * @param id
	 * @return String
	 */
	public static String normalizeId(String id) {
		return Preconditions.checkNotNull(id, "Provided value shouldn't be null").toLowerCase();
	}

	/**
	 * Normalized provided user name
	 * 
	 * @param username
	 * @return String
	 */
	public static String normalizeUsername(String username) {
		return Preconditions.checkNotNull(username, "Username shouldn't be null").toLowerCase();
	}

	/**
	 * Normalized provided project name
	 * 
	 * @param projectName
	 * @return String
	 */
	public static String normalizeProjectName(String projectName) {
		return Preconditions.checkNotNull(projectName, "Project name shouldn't be null").toLowerCase();
	}

	/**
	 * Normalized provided email address
	 * 
	 * @param email
	 * @return String
	 */
	public static String normalizeEmail(String email) {
		return Preconditions.checkNotNull(email, "Email shouldn't be null").toLowerCase();
	}

	/**
	 * Remove leading and trailing spaces from list of string
	 * 
	 * @param strings
	 * @return String
	 */
	public static Iterable<String> trimStrings(Iterable<String> strings) {
		Preconditions.checkNotNull(strings, "List of strings shouldn't be null");
		return StreamSupport.stream(strings.spliterator(), false).filter(string -> !string.isEmpty()).map(String::trim)
				.collect(Collectors.toList());
	}

	/**
	 * Convert declined symbols on allowed for WS and UI
	 * 
	 * @param input
	 * @return Iterable<String>
	 */
	public static Iterable<String> update(Iterable<String> input) {
		final String oldSeparator = ",";
		final String newSeparator = "_";
		final String emptyString = "";
		final String percentSymbol = "%";
		final String andSymbol = "&";
		final String slashSymbol = "/";
		final String backSlashSymbol = "\\";
		return StreamSupport.stream(input.spliterator(), false)
				.map(string -> string.replace(oldSeparator, newSeparator).replace(percentSymbol, emptyString)
						.replace(andSymbol, emptyString).replace(slashSymbol, emptyString).replace(backSlashSymbol, emptyString))
				.collect(Collectors.toList());
	}
}