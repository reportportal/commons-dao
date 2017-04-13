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

package com.epam.ta.reportportal.database.entity;

import static com.epam.ta.reportportal.database.entity.item.issue.TestItemIssueType.*;
import static java.util.Collections.singletonList;

import java.io.Serializable;
import java.util.*;

import com.epam.ta.reportportal.database.entity.project.email.ProjectEmailConfig;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.epam.ta.reportportal.database.entity.item.issue.TestItemIssueType;
import com.epam.ta.reportportal.database.entity.project.EntryType;
import com.epam.ta.reportportal.database.entity.statistics.StatisticSubType;
import com.epam.ta.reportportal.database.search.FilterCriteria;

/**
 * Initial representation of Project object
 * 
 * @author Andrei_Kliashchonak
 * 
 */
@Document
public class Project implements Serializable {
	private static final long serialVersionUID = -7944375232686172158L;

	public static final String USERS = "users";
	public static final String PROJECT = "project";
	public static final String CREATION_DATE = "creationDate";

	@Id
	@FilterCriteria("name")
	private String name;

	@Indexed
	private String customer;

	private String addInfo;

	@FilterCriteria("configuration")
	private Configuration configuration;

	// @Indexed
	@FilterCriteria(USERS)
	private Map<String, UserConfig> users;

	@FilterCriteria("creationDate")
	private Date creationDate;

	public Project() {
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getId() {
		return this.name;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public String getAddInfo() {
		return addInfo;
	}

	public void setAddInfo(String addInfo) {
		this.addInfo = addInfo;
	}

	/*
	 * Null-safe getter
	 */
	public Map<String, UserConfig> getUsers() {
		return users == null ? users = new HashMap<>() : users;
	}

	public void setUsers(Map<String, UserConfig> users) {
		this.users = users;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * NULL-safe getter
	 * 
	 * @return the configuration
	 */
	public Configuration getConfiguration() {
		return configuration == null ? configuration = new Configuration() : configuration;
	}

	/**
	 * @param configuration
	 *            the configuration to set
	 */
	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((addInfo == null) ? 0 : addInfo.hashCode());
		result = prime * result + ((customer == null) ? 0 : customer.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((users == null) ? 0 : users.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Project))
			return false;
		Project other = (Project) obj;
		if (addInfo == null) {
			if (other.addInfo != null)
				return false;
		} else if (!addInfo.equals(other.addInfo))
			return false;
		if (customer == null) {
			if (other.customer != null)
				return false;
		} else if (!customer.equals(other.customer))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (users == null) {
			if (other.users != null)
				return false;
		} else if (!users.equals(other.users))
			return false;
		return true;
	}

	public static class Configuration implements Serializable {

		private static final String AB_COLOR = "#f7d63e";
		private static final String PB_COLOR = "#ec3900";
		private static final String SI_COLOR = "#0274d1";
		private static final String ND_COLOR = "#777777";
		private static final String TI_COLOR = "#ffb743";

		private static final long serialVersionUID = 1L;
		private StatisticsCalculationStrategy statisticsCalculationStrategy;
		private List<String> externalSystem;
		@FilterCriteria("entryType")
		private EntryType entryType;
		private ProjectSpecific projectSpecific;
		private String interruptJobTime;
		private String keepLogs;
		private String keepScreenshots;
		private Boolean isAutoAnalyzerEnabled;
		private Map<TestItemIssueType, List<StatisticSubType>> subTypes;

		// Project Email Settings
		private ProjectEmailConfig emailConfig;

		public Configuration() {
			externalSystem = new ArrayList<>();
			this.subTypes = new HashMap<TestItemIssueType, List<StatisticSubType>>() {
				{
					put(AUTOMATION_BUG, singletonList(new StatisticSubType(AUTOMATION_BUG.getLocator(), AUTOMATION_BUG.getValue(),
							"Automation Bug", "AB", AB_COLOR)));
					put(PRODUCT_BUG, singletonList(
							new StatisticSubType(PRODUCT_BUG.getLocator(), PRODUCT_BUG.getValue(), "Product Bug", "PB", PB_COLOR)));
					put(SYSTEM_ISSUE, singletonList(
							new StatisticSubType(SYSTEM_ISSUE.getLocator(), SYSTEM_ISSUE.getValue(), "System Issue", "SI", SI_COLOR)));
					put(NO_DEFECT,
							singletonList(new StatisticSubType(NO_DEFECT.getLocator(), NO_DEFECT.getValue(), "No Defect", "ND", ND_COLOR)));
					put(TO_INVESTIGATE, singletonList(new StatisticSubType(TO_INVESTIGATE.getLocator(), TO_INVESTIGATE.getValue(),
							"To Investigate", "TI", TI_COLOR)));
				}
			};
		}

		public StatisticSubType getByLocator(String locator) {
			/* If locator is predefined group */
			TestItemIssueType type = fromValue(locator);
			if (null != type) {
				Optional<StatisticSubType> typeOptional = subTypes.values().stream().flatMap(Collection::stream)
						.filter(one -> one.getLocator().equalsIgnoreCase(type.getLocator())).findFirst();
				return typeOptional.orElse(null);
			}
			/* If not */
			Optional<StatisticSubType> exist = subTypes.values().stream().flatMap(Collection::stream)
					.filter(one -> one.getLocator().equalsIgnoreCase(locator)).findFirst();
			return exist.orElse(null);
		}

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

		public void setSubTypes(Map<TestItemIssueType, List<StatisticSubType>> subTypes) {
			this.subTypes = subTypes;
		}

		public Map<TestItemIssueType, List<StatisticSubType>> getSubTypes() {
			return subTypes;
		}

		public void setEntryType(EntryType value) {
			this.entryType = value;
		}

		public EntryType getEntryType() {
			return entryType;
		}

		public void setProjectSpecific(ProjectSpecific value) {
			this.projectSpecific = value;
		}

		public ProjectSpecific getProjectSpecific() {
			return projectSpecific;
		}

		public void setInterruptJobTime(String value) {
			this.interruptJobTime = value;
		}

		public String getInterruptJobTime() {
			return interruptJobTime;
		}

		public void setKeepLogs(String value) {
			this.keepLogs = value;
		}

		public String getKeepLogs() {
			return keepLogs;
		}

		public void setKeepScreenshots(String value) {
			this.keepScreenshots = value;
		}

		public String getKeepScreenshots() {
			return keepScreenshots;
		}

		public void setIsAutoAnalyzerEnabled(boolean enabled) {
			this.isAutoAnalyzerEnabled = enabled;
		}

		public Boolean getIsAutoAnalyzerEnabled() {
			return isAutoAnalyzerEnabled;
		}

		public void setEmailConfig(ProjectEmailConfig config) {
			this.emailConfig = config;
		}

		public ProjectEmailConfig getEmailConfig() {
			return emailConfig;
		}

		/**
		 * @return the statisticsCalculationStrategy
		 */
		public StatisticsCalculationStrategy getStatisticsCalculationStrategy() {
			return statisticsCalculationStrategy;
		}

		/**
		 * @param statisticsCalculationStrategy
		 *            the statisticsCalculationStrategy to set
		 */
		public void setStatisticsCalculationStrategy(StatisticsCalculationStrategy statisticsCalculationStrategy) {
			this.statisticsCalculationStrategy = statisticsCalculationStrategy;
		}

		public void setExternalSystem(List<String> externalSystemIds) {
			this.externalSystem = externalSystemIds;
		}

		public List<String> getExternalSystem() {
			return externalSystem;
		}
	}

	public static class UserConfig implements Serializable {

		private static final long serialVersionUID = 1L;
		private ProjectRole proposedRole;
		private ProjectRole projectRole;

		public static UserConfig newOne() {
			return new UserConfig();
		}

		public UserConfig() {

		}

		public void setProjectRole(ProjectRole projectRole) {
			this.projectRole = projectRole;
		}

		public void setProposedRole(ProjectRole proposedRole) {
			this.proposedRole = proposedRole;
		}

		public ProjectRole getProjectRole() {
			return projectRole;
		}

		public ProjectRole getProposedRole() {
			return proposedRole;
		}

		public UserConfig withProposedRole(ProjectRole proposedRole) {
			this.proposedRole = proposedRole;
			return this;
		}

		public UserConfig withProjectRole(ProjectRole projectRole) {
			this.projectRole = projectRole;
			return this;
		}
	}

	@Override
	public String toString() {
		return new StringBuilder("Project [name=").append(name).append(", customer=").append(customer).append(", addInfo=").append(addInfo)
				.append(", configuration=").append(configuration).append(", users=").append(users).append("]").toString();
	}
}
