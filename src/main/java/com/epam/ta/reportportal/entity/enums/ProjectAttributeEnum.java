package com.epam.ta.reportportal.entity.enums;

import static com.epam.ta.reportportal.entity.enums.ProjectAttributeType.ANALYZER_CONFIGURATION;
import static com.epam.ta.reportportal.entity.enums.ProjectAttributeType.PROJECT_CONFIGURATION;

/**
 * @author Andrey Plisunov
 */
public enum ProjectAttributeEnum {

    ENTRY_TYPE("entryType", PROJECT_CONFIGURATION),
    INTERRUPT_JOB_TIME("interruptJobTime", PROJECT_CONFIGURATION),
    KEEP_LOGS("keepLogs", PROJECT_CONFIGURATION),
    KEEP_SCREENSHOTS("keepScreenshots", PROJECT_CONFIGURATION),
    MIN_DOC_FREQ("minDocFreq", ANALYZER_CONFIGURATION),
    MIN_TERM_FREQ("minTermFreq", ANALYZER_CONFIGURATION),
    MIN_SHOULD_MATCH("minShouldMatch", ANALYZER_CONFIGURATION),
    NUMBER_OF_LOG_LINES("numberOfLogLines", ANALYZER_CONFIGURATION),
    INDEXING_RUNNING("indexingRunning", ANALYZER_CONFIGURATION),
    AUTO_ANALYZER_ENABLED("isAutoAnalyzerEnabled", ANALYZER_CONFIGURATION);

    private String value;

    private ProjectAttributeType optionType;

    ProjectAttributeEnum(String value, ProjectAttributeType optionType) {
        this.value = value;
        this.optionType = optionType;
    }

    public String getValue() {
        return value;
    }

    public ProjectAttributeType getOptionType() {
        return optionType;
    }
}


