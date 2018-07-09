package com.epam.ta.reportportal.entity.project;

import com.epam.ta.reportportal.entity.StatisticsCalculationStrategy;
import com.epam.ta.reportportal.entity.bts.BugTrackingSystem;
import com.epam.ta.reportportal.entity.enums.EntryType;
import com.epam.ta.reportportal.entity.integration.Integration;
import com.epam.ta.reportportal.entity.item.issue.TestItemIssueType;
import com.epam.ta.reportportal.entity.project.email.ProjectEmailConfig;
import com.epam.ta.reportportal.entity.statistics.StatisticSubType;
import com.google.common.base.MoreObjects;
import com.google.common.collect.Sets;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

import static com.epam.ta.reportportal.entity.item.issue.TestItemIssueType.*;
import static java.util.Collections.singletonList;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "project", schema = "public")
public class Project implements Serializable {

	private static final long serialVersionUID = -263516611;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false, precision = 64)
	private Long id;

	@Column(name = "name")
	private String name;

	@OneToMany(mappedBy = "project", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private Set<BugTrackingSystem> bugTrackingSystems = Sets.newHashSet();

	@OneToMany(mappedBy = "project", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, orphanRemoval = true)
	private Set<Integration> integrations = Sets.newHashSet();

	@Column(name = "customer")
	private String customer;

	@Column(name = "additional_info")
	private String addInfo;

	@JoinColumn(name = "configuration_id")
	private Configuration configuration;

	@OneToMany(mappedBy = "project")
	private List<UserConfig> users;

	@Column(name = "creation_date")
	private Date creationDate;

	@JoinColumn(name = "metadata_id")
	private Metadata metadata;

	public Project(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public Project() {
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public void setUsers(List<UserConfig> users) {
		this.users = users;
	}

	/*
	 * Null-safe getter
	 */
	public List<UserConfig> getUsers() {
		return users == null ? users = Collections.emptyList() : users;
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
	 * @param configuration the configuration to set
	 */
	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}

	public Metadata getMetadata() {
		return metadata;
	}

	public void setMetadata(Metadata metadata) {
		this.metadata = metadata;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Project project = (Project) o;
		return Objects.equals(name, project.name) && Objects.equals(customer, project.customer) && Objects.equals(addInfo, project.addInfo)
				&& Objects.equals(configuration, project.configuration) && Objects.equals(users, project.users) && Objects.equals(creationDate,
				project.creationDate
		) && Objects.equals(metadata, project.metadata);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, customer, addInfo, configuration, users, creationDate, metadata);
	}

	@Entity
	@Table(name = "configuration")
	public static class Configuration implements Serializable {

		private static final String AB_COLOR = "#f7d63e";
		private static final String PB_COLOR = "#ec3900";
		private static final String SI_COLOR = "#0274d1";
		private static final String ND_COLOR = "#777777";
		private static final String TI_COLOR = "#ffb743";

		private static final long serialVersionUID = 1L;
		private StatisticsCalculationStrategy statisticsCalculationStrategy;

		private List<String> externalSystem;

		@Enumerated(value = EnumType.STRING)
		private EntryType entryType;

		@Enumerated(value = EnumType.STRING)
		private ProjectSpecific projectSpecific;

		@Column(name = "interrupt_jon_time")
		private String interruptJobTime;

		@Column(name = "keep_logs")
		private String keepLogs;

		@Column(name = "keep_screenshots")
		private String keepScreenshots;
		private Map<TestItemIssueType, List<StatisticSubType>> subTypes;

		// Project Email Settings
		private ProjectEmailConfig emailConfig;

		private ProjectAnalyzerConfig analyzerConfig;

		public Configuration() {
			externalSystem = new ArrayList<>();
			this.subTypes = new HashMap<TestItemIssueType, List<StatisticSubType>>() {
				{
					put(AUTOMATION_BUG,
							singletonList(new StatisticSubType(AUTOMATION_BUG.getLocator(),
									AUTOMATION_BUG.getValue(),
									"Automation Bug",
									"AB",
									AB_COLOR
							))
					);
					put(PRODUCT_BUG,
							singletonList(new StatisticSubType(PRODUCT_BUG.getLocator(),
									PRODUCT_BUG.getValue(),
									"Product Bug",
									"PB",
									PB_COLOR
							))
					);
					put(SYSTEM_ISSUE,
							singletonList(new StatisticSubType(SYSTEM_ISSUE.getLocator(),
									SYSTEM_ISSUE.getValue(),
									"System Issue",
									"SI",
									SI_COLOR
							))
					);
					put(NO_DEFECT,
							singletonList(new StatisticSubType(NO_DEFECT.getLocator(), NO_DEFECT.getValue(), "No Defect", "ND", ND_COLOR))
					);
					put(TO_INVESTIGATE,
							singletonList(new StatisticSubType(TO_INVESTIGATE.getLocator(),
									TO_INVESTIGATE.getValue(),
									"To Investigate",
									"TI",
									TI_COLOR
							))
					);
				}
			};
			analyzerConfig = new ProjectAnalyzerConfig();
		}

		public StatisticSubType getByLocator(String locator) {
			/* If locator is predefined group */
			TestItemIssueType type = fromValue(locator);
			if (null != type) {
				Optional<StatisticSubType> typeOptional = subTypes.values()
						.stream()
						.flatMap(Collection::stream)
						.filter(one -> one.getLocator().equalsIgnoreCase(type.getLocator()))
						.findFirst();
				return typeOptional.orElse(null);
			}
			/* If not */
			Optional<StatisticSubType> exist = subTypes.values()
					.stream()
					.flatMap(Collection::stream)
					.filter(one -> one.getLocator().equalsIgnoreCase(locator))
					.findFirst();
			return exist.orElse(null);
		}

		public void setByLocator(StatisticSubType type) {
			TestItemIssueType global = fromValue(type.getLocator());
			if (null == global) {
				Optional<StatisticSubType> exist = subTypes.values()
						.stream()
						.flatMap(Collection::stream)
						.filter(one -> one.getLocator().equalsIgnoreCase(type.getLocator()))
						.findFirst();
				exist.ifPresent(statisticSubType -> {
					if (null != type.getLongName()) {
						statisticSubType.setLongName(type.getLongName());
					}
					if (null != type.getShortName()) {
						statisticSubType.setShortName(type.getShortName());
					}
					if (null != type.getHexColor()) {
						statisticSubType.setHexColor(type.getHexColor());
					}
				});
			}
		}

		public ProjectAnalyzerConfig getAnalyzerConfig() {
			return analyzerConfig;
		}

		public void setAnalyzerConfig(ProjectAnalyzerConfig analyzerConfig) {
			this.analyzerConfig = analyzerConfig;
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
		 * @param statisticsCalculationStrategy the statisticsCalculationStrategy to set
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

	@Entity
	@Table(name = "user_config")
	public static class UserConfig implements Serializable {

		private static final long serialVersionUID = 1L;

		private String login;
		private ProjectRole proposedRole;
		private ProjectRole projectRole;

		public static UserConfig newOne() {
			return new UserConfig();
		}

		public UserConfig() {

		}

		public String getLogin() {
			return login;
		}

		public void setLogin(String login) {
			this.login = login;
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

		public UserConfig withLogin(String login) {
			this.login = login;
			return this;
		}

		@Override
		public String toString() {
			return MoreObjects.toStringHelper(this).add("login", login).add("proposedRole", proposedRole).add("projectRole", projectRole).toString();
		}
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("name", name)
				.add("customer", customer)
				.add("addInfo", addInfo)
				.add("configuration", configuration)
				.add("users", users)
				.add("creationDate", creationDate)
				.add("metadata", metadata)
				.toString();
	}

	public static class Metadata implements Serializable {

		public Metadata() {
		}

		public Metadata(List<String> demoDataPostfix) {
			this.demoDataPostfix = demoDataPostfix;
		}

		private List<String> demoDataPostfix;

		public List<String> getDemoDataPostfix() {
			return demoDataPostfix;
		}

		public void setDemoDataPostfix(List<String> demoDataPostfix) {
			this.demoDataPostfix = demoDataPostfix;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) {
				return true;
			}
			if (o == null || getClass() != o.getClass()) {
				return false;
			}

			Metadata metadata = (Metadata) o;

			return demoDataPostfix != null ? demoDataPostfix.equals(metadata.demoDataPostfix) : metadata.demoDataPostfix == null;
		}

		@Override
		public int hashCode() {
			return demoDataPostfix != null ? demoDataPostfix.hashCode() : 0;
		}
	}
}
