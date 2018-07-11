package com.epam.ta.reportportal.entity.project;

import com.epam.ta.reportportal.entity.StatisticsCalculationStrategy;
import com.epam.ta.reportportal.entity.enums.EntryType;
import com.epam.ta.reportportal.entity.enums.TestItemIssueGroup;
import com.epam.ta.reportportal.entity.item.issue.IssueGroup;
import com.epam.ta.reportportal.entity.item.issue.IssueType;
import com.epam.ta.reportportal.entity.project.email.ProjectEmailConfig;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.epam.ta.reportportal.entity.enums.TestItemIssueGroup.*;

@Entity
@Table(name = "project_configuration")
public class ProjectConfiguration implements Serializable {

	private static final String AB_COLOR = "#f7d63e";
	private static final String PB_COLOR = "#ec3900";
	private static final String SI_COLOR = "#0274d1";
	private static final String ND_COLOR = "#777777";
	private static final String TI_COLOR = "#ffb743";

	private static final long serialVersionUID = 1L;
	private StatisticsCalculationStrategy statisticsCalculationStrategy;

	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "project_type")
	@Enumerated(value = EnumType.STRING)
	private EntryType entryType;

	@Column(name = "project_specific")
	@Enumerated(value = EnumType.STRING)
	private ProjectSpecific projectSpecific;

	@Column(name = "interrupt_timeout")
	private String interruptJobTime;

	@Column(name = "keep_logs_interval")
	private String keepLogs;

	@Column(name = "keep_screenshots_interval")
	private String keepScreenshots;

	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "issue_type_project_configuration", joinColumns = { @JoinColumn(name = "configuration_id") }, inverseJoinColumns = {
			@JoinColumn(name = "issue_type_id") })
	private List<IssueType> issueTypes;

	// Project Email Settings
	@ManyToOne
	@JoinColumn(name = "project_email_config_id")
	private ProjectEmailConfig emailConfig;

	@ManyToOne
	@JoinColumn(name = "project_analyzer_config_id")
	private ProjectAnalyzerConfig analyzerConfig;

	public ProjectConfiguration() {
		this.issueTypes = new ArrayList<IssueType>() {
			{
				add(new IssueType(new IssueGroup(AUTOMATION_BUG), AUTOMATION_BUG.getLocator(), "Automation Bug", "AB", AB_COLOR));
				add(new IssueType(new IssueGroup(PRODUCT_BUG), PRODUCT_BUG.getLocator(), "Product Bug", "PB", PB_COLOR));
				add(new IssueType(new IssueGroup(SYSTEM_ISSUE), SYSTEM_ISSUE.getLocator(), "System Issue", "SI", SI_COLOR));
				add(new IssueType(new IssueGroup(NO_DEFECT), NO_DEFECT.getLocator(), "No Defect", "ND", ND_COLOR));
				add(new IssueType(new IssueGroup(TO_INVESTIGATE), TO_INVESTIGATE.getLocator(), "To Investigate", "TI", TI_COLOR));

			}
		};
		analyzerConfig = new ProjectAnalyzerConfig();
	}

	public IssueType getByLocator(String locator) {
		/* If locator is predefined group */
		TestItemIssueGroup type = fromValue(locator);
		if (null != type) {
			Optional<IssueType> typeOptional = issueTypes.stream()
					.filter(one -> one.getLocator().equalsIgnoreCase(type.getLocator()))
					.findFirst();
			return typeOptional.orElse(null);
		}
		/* If not */
		Optional<IssueType> exist = issueTypes.stream().filter(one -> one.getLocator().equalsIgnoreCase(locator)).findFirst();
		return exist.orElse(null);
	}

	public void setByLocator(IssueType type) {
		TestItemIssueGroup global = fromValue(type.getLocator());
		if (null == global) {
			Optional<IssueType> exist = issueTypes.stream().filter(one -> one.getLocator().equalsIgnoreCase(type.getLocator())).findFirst();
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ProjectAnalyzerConfig getAnalyzerConfig() {
		return analyzerConfig;
	}

	public void setAnalyzerConfig(ProjectAnalyzerConfig analyzerConfig) {
		this.analyzerConfig = analyzerConfig;
	}

	public List<IssueType> getIssueTypes() {
		return issueTypes;
	}

	public void setIssueTypes(List<IssueType> issueTypes) {
		this.issueTypes = issueTypes;
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

}