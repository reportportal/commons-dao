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

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;

import java.util.concurrent.TimeUnit;

/**
 * Represents an immutable moment of time. <br>
 * Grabbed from WebDriver implementation
 * 
 * @author Andrei Varabyeu
 * 
 */
public class Time {

	private final long time;
	private final TimeUnit unit;

	/**
	 * @param time
	 *            The amount of time.
	 * @param unit
	 *            The unit of time.
	 */
	public Time(long time, TimeUnit unit) {
		Preconditions.checkArgument(time >= 0, "Time < 0: %d", time);
		Preconditions.checkNotNull(unit);
		this.time = time;
		this.unit = unit;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Time) {
			Time other = (Time) o;
			return this.time == other.time && this.unit == other.unit;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(time, unit);
	}

	@Override
	public String toString() {
		return String.format("%d %s", time, unit);
	}

	/**
	 * Converts this duration to the given unit of time.
	 * 
	 * @param unit
	 *            The time unit to convert to.
	 * @return The value of this duration in the specified unit of time.
	 */
	public long in(TimeUnit unit) {
		return unit.convert(time, this.unit);
	}

	public static Time minutes(long time) {
		return new Time(time, TimeUnit.MINUTES);
	}

	public static Time seconds(long time) {
		return new Time(time, TimeUnit.SECONDS);
	}

	public static Time hours(long time) {
		return new Time(time, TimeUnit.HOURS);
	}
	
	public static Time days(long time) {
		return new Time(time, TimeUnit.DAYS);
	}

	public static Time milliseconds(long time) {
		return new Time(time, TimeUnit.MILLISECONDS);
	}
}