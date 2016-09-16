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
 
package com.epam.ta.reportportal.database.entity.item;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.epam.ta.reportportal.database.entity.Launch;
import com.epam.ta.reportportal.database.search.FilterCriteria;

/**
 * Temporary storage document for failed/skipped<br>
 * items {@link TestItem} for processing after<br>
 * launch {@link Launch} finishing.<br>
 * 
 * @author Andrei_Ramanchuk
 * 
 */
@Document(collection = "failReferences")
public class FailReferenceResource {

	private static final String ITEM_REF = "testItemRef";
	private static final String LAUNCH_REF = "launchRef";
	public static final String PROJECT = "projectName";

	/*
	 * Unique index for record
	 */
	@Id
	private String id;

	/*
	 * TestItem reference
	 */
	@FilterCriteria(ITEM_REF)
	@Indexed(background = true)
	private String testItemRef;

	/*
	 * Launch reference
	 */
	@FilterCriteria(LAUNCH_REF)
	@Indexed(background = true)
	private String launchRef;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTestItemRef() {
		return testItemRef;
	}

	public void setTestItemRef(String value) {
		this.testItemRef = value;
	}

	public String getLaunchRef() {
		return launchRef;
	}

	public void setLaunchRef(String value) {
		this.launchRef = value;
	}
}