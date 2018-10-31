/*
 * Copyright 2017 EPAM Systems
 *
 *
 * This file is part of EPAM Report Portal.
 * https://github.com/reportportal/service-api
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

package com.epam.ta.reportportal.entity;

import java.util.Arrays;
import java.util.Optional;

/**
 * @author Pavel Bortnik
 */
public enum AnalyzeMode {

	ALL_LAUNCHES("ALL"),
	BY_LAUNCH_NAME("LAUNCH_NAME"),
	CURRENT_LAUNCH("CURRENT_LAUNCH");

	private String value;

	AnalyzeMode(String value) {
		this.value = value;
	}

	public static Optional<AnalyzeMode> fromString(String mode) {
		return Arrays.stream(AnalyzeMode.values()).filter(it -> it.getValue().equalsIgnoreCase(mode)).findFirst();
	}

	public String getValue() {
		return value;
	}
}
