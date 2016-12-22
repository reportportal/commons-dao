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

package com.epam.ta.reportportal.database.entity.project;

import java.io.Serializable;

/**
 * Project custom statistic sub-type object representation<br>
 * It could contain user configured project specific sub-types, which referenced
 * to global predefined statistics issue type from
 * {@see com.epam.ta.reportportal.database.entity.statistics.IssueCounter}<br>
 * 
 * except 'toInvestigate' field (for a while, may be sub-types for
 * to_investigate could be used as assignments to project users for
 * investigations).
 * 
 * @author Andrei_Ramanchuk
 */
public class StatisticSubType implements Serializable {
	private static final long serialVersionUID = 711270499291613354L;

	/**
	 * ID of created sub-type
	 */
	private String locator;

	/**
	 * Reference on global issue type
	 */
	private String typeRef;

	/**
	 * Long sub-type name in user-friendly view
	 */
	private String longName;

	/**
	 * Short representation of long name
	 */
	private String shortName;

	/**
	 * Sub-type color badge in hex format for UI
	 */
	private String hexColor;

	public StatisticSubType() {

	}

	public StatisticSubType(String locator, String ref, String longName, String shortName, String color) {
		this.locator = locator;
		this.typeRef = ref;
		this.longName = longName;
		this.shortName = shortName;
		this.hexColor = color;
	}

	public void setLocator(String locator) {
		this.locator = locator;
	}

	public String getLocator() {
		return locator;
	}

	public void setTypeRef(String type) {
		this.typeRef = type;
	}

	public String getTypeRef() {
		return typeRef;
	}

	public void setLongName(String longName) {
		this.longName = longName;
	}

	public String getLongName() {
		return longName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getShortName() {
		return shortName;
	}

	public void setHexColor(String hexColor) {
		this.hexColor = hexColor;
	}

	public String getHexColor() {
		return hexColor;
	}

	@Override
	public String toString() {
		return "StatisticSubType [locator=" + locator + ", typeRef=" + typeRef + ", longName=" + longName + ", shortName=" + shortName
				+ ", hexColor=" + hexColor + "]";
	}
}