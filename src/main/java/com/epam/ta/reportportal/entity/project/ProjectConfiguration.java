package com.epam.ta.reportportal.entity.project;

import com.epam.ta.reportportal.entity.enums.EntryType;
import com.epam.ta.reportportal.entity.enums.ProjectAttributeEnum;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

import static com.epam.ta.reportportal.entity.enums.ProjectAttributeEnum.*;

/**
 * @author Ivan Budayeu
 */
public class ProjectConfiguration implements Serializable {

	private static final long serialVersionUID = 1L;

	private EntryType entryType;

	private String interruptJobTime;

	private String keepLogs;

	private String keepScreenshots;

	private ProjectAnalyzerConfig analyzerConfig;

	public ProjectConfiguration() {
		this.analyzerConfig = new ProjectAnalyzerConfig();
	}

	public ProjectConfiguration(Set<ProjectAttribute> projectAttributes) {
		if (CollectionUtils.isNotEmpty(projectAttributes)) {
			Map<ProjectAttributeEnum, String> attributes = projectAttributes.stream().collect(Collectors.toMap(ProjectAttribute::getName, ProjectAttribute::getValue));
			this.entryType = EntryType.getByName(attributes.get(ENTRY_TYPE));
			this.interruptJobTime = attributes.get(INTERRUPT_JOB_TIME);
			this.keepLogs = attributes.get(KEEP_LOGS);
			this.keepScreenshots = attributes.get(KEEP_SCREENSHOTS);
			this.analyzerConfig =  new ProjectAnalyzerConfig(getOptionValueOrDefault(attributes.get(MIN_DOC_FREQ), ProjectAnalyzerConfig.MIN_DOC_FREQ),
															 getOptionValueOrDefault(attributes.get(MIN_TERM_FREQ), ProjectAnalyzerConfig.MIN_TERM_FREQ),
															 getOptionValueOrDefault(attributes.get(MIN_SHOULD_MATCH), ProjectAnalyzerConfig.MIN_SHOULD_MATCH),
															 getOptionValueOrDefault(attributes.get(NUMBER_OF_LOG_LINES), ProjectAnalyzerConfig.NUMBER_OF_LOG_LINES),
															 BooleanUtils.toBoolean(attributes.get(INDEXING_RUNNING)),
															 BooleanUtils.toBooleanObject(attributes.get(AUTO_ANALYZER_ENABLED)));
		}
	}


	public ProjectAnalyzerConfig getAnalyzerConfig() {
		return analyzerConfig;
	}

	public void setAnalyzerConfig(ProjectAnalyzerConfig analyzerConfig) {
		this.analyzerConfig = analyzerConfig;
	}

	public void setEntryType(EntryType value) {
		this.entryType = value;
	}

	public EntryType getEntryType() {
		return entryType;
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

	private int getOptionValueOrDefault(String optionValue, int defaultValue) {
		try {
			return Integer.parseInt(optionValue);
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}
}