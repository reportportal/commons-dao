package com.epam.ta.reportportal.entity.enums;

import com.epam.ta.reportportal.entity.AnalyzeMode;
import com.epam.ta.reportportal.entity.project.ProjectAnalyzerConfig;

import java.util.Arrays;
import java.util.Optional;

import static com.epam.ta.reportportal.entity.project.ProjectUtils.INIT_FROM;

/**
 * Enum with a list of basic required project parameters
 *
 * @author Andrey Plisunov
 */
public enum ProjectAttributeEnum {

	ENTRY_TYPE("project.entryType", EntryType.INTERNAL.name()),

	INTERRUPT_JOB_TIME("job.interruptJobTime", InterruptionJobDelay.ONE_DAY.getValue()),
	KEEP_LOGS("job.keepLogs", KeepLogsDelay.THREE_MONTHS.getValue()),
	KEEP_SCREENSHOTS("job.keepScreenshots", KeepScreenshotsDelay.TWO_WEEKS.getValue()),

	MIN_DOC_FREQ("analyzer.minDocFreq", String.valueOf(ProjectAnalyzerConfig.MIN_DOC_FREQ)),
	MIN_TERM_FREQ("analyzer.minTermFreq", String.valueOf(ProjectAnalyzerConfig.MIN_TERM_FREQ)),
	MIN_SHOULD_MATCH("analyzer.minShouldMatch", String.valueOf(ProjectAnalyzerConfig.MIN_SHOULD_MATCH)),
	NUMBER_OF_LOG_LINES("analyzer.numberOfLogLines", String.valueOf(ProjectAnalyzerConfig.NUMBER_OF_LOG_LINES)),
	INDEXING_RUNNING("analyzer.indexingRunning", String.valueOf(false)),
	AUTO_ANALYZER_ENABLED("analyzer.isAutoAnalyzerEnabled", String.valueOf(false)),
	AUTO_ANALYZER_MODE("analyzer.autoAnalyzerMode", AnalyzeMode.BY_LAUNCH_NAME.getValue()),

	EMAIL_ENABLED("email.enabled", String.valueOf(false)),
	EMAIL_FROM("email.from", INIT_FROM);

	private String attribute;
	private String defaultValue;

	ProjectAttributeEnum(String attribute, String defaultValue) {
		this.attribute = attribute;
		this.defaultValue = String.valueOf(defaultValue);
	}

	public String getAttribute() {
		return attribute;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public static Optional<ProjectAttributeEnum> findByAttributeName(String attributeName) {
		return Arrays.stream(ProjectAttributeEnum.values()).filter(v -> v.getAttribute().equalsIgnoreCase(attributeName)).findAny();
	}

	public static boolean isPresent(String name) {
		return findByAttributeName(name).isPresent();
	}
}


