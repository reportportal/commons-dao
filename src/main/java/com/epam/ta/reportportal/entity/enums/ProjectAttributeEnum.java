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

	ENTRY_TYPE("entryType", EntryType.INTERNAL.name()),
	INTERRUPT_JOB_TIME("interruptJobTime", InterruptionJobDelay.ONE_DAY.getValue()),
	KEEP_LOGS("keepLogs", KeepLogsDelay.THREE_MONTHS.getValue()),
	KEEP_SCREENSHOTS("keepScreenshots", KeepScreenshotsDelay.TWO_WEEKS.getValue()),
	MIN_DOC_FREQ("minDocFreq", String.valueOf(ProjectAnalyzerConfig.MIN_DOC_FREQ)),
	MIN_TERM_FREQ("minTermFreq", String.valueOf(ProjectAnalyzerConfig.MIN_TERM_FREQ)),
	MIN_SHOULD_MATCH("minShouldMatch", String.valueOf(ProjectAnalyzerConfig.MIN_SHOULD_MATCH)),
	NUMBER_OF_LOG_LINES("numberOfLogLines", String.valueOf(ProjectAnalyzerConfig.NUMBER_OF_LOG_LINES)),
	INDEXING_RUNNING("indexingRunning", String.valueOf(false)),
	AUTO_ANALYZER_ENABLED("isAutoAnalyzerEnabled", String.valueOf(false)),
	AUTO_ANALYZER_MODE("autoAnalyzerMode", AnalyzeMode.BY_LAUNCH_NAME.getValue()),
	EMAIL_ENABLED("emailEnabled", String.valueOf(false)),
	EMAIL_FROM("emailFrom", INIT_FROM);

	private String attribute;
	private String defaultValue;

	ProjectAttributeEnum(String attribute, String object) {
		this.attribute = attribute;
		this.defaultValue = String.valueOf(object);
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


