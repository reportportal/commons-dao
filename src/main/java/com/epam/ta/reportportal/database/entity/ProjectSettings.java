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

package com.epam.ta.reportportal.database.entity;

import static com.epam.ta.reportportal.database.entity.item.issue.TestItemIssueType.*;
import static java.util.Collections.singletonList;

import java.io.Serializable;
import java.util.*;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.hateoas.Identifiable;

import com.epam.ta.reportportal.database.entity.item.issue.TestItemIssueType;
import com.epam.ta.reportportal.database.entity.statistics.StatisticSubType;

/**
 * Specific project settings representation object<br>
 * NOTES:<br>
 * <b>ID</b> of document should be relevant with according project name
 *
 * @author Andrei_Ramanchuk
 */
@Document
public class ProjectSettings implements Serializable, Identifiable<String> {
	private static final long serialVersionUID = -7084687278354359149L;
	private static final String AB_COLOR = "#f7d63e";
	private static final String PB_COLOR = "#ec3900";
	private static final String SI_COLOR = "#0274d1";
	private static final String ND_COLOR = "#777777";
	private static final String TI_COLOR = "#ffb743";

	@Id
	private String name;

	private Map<TestItemIssueType, List<StatisticSubType>> subTypes;

	public ProjectSettings() {
		this.subTypes = new HashMap<TestItemIssueType, List<StatisticSubType>>() {
			{
				put(AUTOMATION_BUG, singletonList(
						new StatisticSubType(AUTOMATION_BUG.getLocator(), AUTOMATION_BUG.getValue(), "Automation Bug", "AB", AB_COLOR)));
				put(PRODUCT_BUG, singletonList(
						new StatisticSubType(PRODUCT_BUG.getLocator(), PRODUCT_BUG.getValue(), "Product Bug", "PB", PB_COLOR)));
				put(SYSTEM_ISSUE, singletonList(
						new StatisticSubType(SYSTEM_ISSUE.getLocator(), SYSTEM_ISSUE.getValue(), "System Issue", "SI", SI_COLOR)));
				put(NO_DEFECT,
						singletonList(new StatisticSubType(NO_DEFECT.getLocator(), NO_DEFECT.getValue(), "No Defect", "ND", ND_COLOR)));
				put(TO_INVESTIGATE, singletonList(
						new StatisticSubType(TO_INVESTIGATE.getLocator(), TO_INVESTIGATE.getValue(), "To Investigate", "TI", TI_COLOR)));
			}
		};
	}

	public ProjectSettings(String name) {
		this();
		this.name = name;
	}

	@Override
	public String getId() {
		return this.name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSubTypes(Map<TestItemIssueType, List<StatisticSubType>> subTypes) {
		this.subTypes = subTypes;
	}

	public Map<TestItemIssueType, List<StatisticSubType>> getSubTypes() {
		return subTypes;
	}

	public StatisticSubType getByLocator(String locator) {
		/* If locator is predefined group */
		TestItemIssueType type = fromValue(locator);
		if (null != type) {
			Optional<StatisticSubType> typeOptional = subTypes.values().stream().flatMap(Collection::stream)
					.filter(one -> one.getLocator().equalsIgnoreCase(type.getLocator())).findFirst();
			return typeOptional.isPresent() ? typeOptional.get() : null;
		}
		/* If not */
		Optional<StatisticSubType> exist = subTypes.values().stream().flatMap(Collection::stream)
				.filter(one -> one.getLocator().equalsIgnoreCase(locator)).findFirst();
		return exist.isPresent() ? exist.get() : null;
	}

	// Global access for existing IssueSubType update
	public void setByLocator(StatisticSubType type) {
		TestItemIssueType global = fromValue(type.getLocator());
		if (null == global) {
			Optional<StatisticSubType> exist = subTypes.values().stream().flatMap(Collection::stream)
					.filter(one -> one.getLocator().equalsIgnoreCase(type.getLocator())).findFirst();
			if (exist.isPresent()) {
				if (null != type.getLongName())
					exist.get().setLongName(type.getLongName());
				if (null != type.getShortName())
					exist.get().setShortName(type.getShortName());
				if (null != type.getHexColor())
					exist.get().setHexColor(type.getHexColor());
			}
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((subTypes == null) ? 0 : subTypes.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProjectSettings other = (ProjectSettings) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (subTypes == null) {
			if (other.subTypes != null)
				return false;
		} else if (!subTypes.equals(other.subTypes))
			return false;
		return true;
	}
}