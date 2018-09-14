package com.epam.ta.reportportal.entity.enums;

/**
 * Enum with a list of basic required project parameters
 *
 * @author Andrey Plisunov
 */
public enum ProjectAttributeEnum {

    ENTRY_TYPE("entryType"),
    INTERRUPT_JOB_TIME("interruptJobTime"),
    KEEP_LOGS("keepLogs"),
    KEEP_SCREENSHOTS("keepScreenshots"),
    MIN_DOC_FREQ("minDocFreq"),
    MIN_TERM_FREQ("minTermFreq"),
    MIN_SHOULD_MATCH("minShouldMatch"),
    NUMBER_OF_LOG_LINES("numberOfLogLines"),
    INDEXING_RUNNING("indexingRunning"),
    AUTO_ANALYZER_ENABLED("isAutoAnalyzerEnabled"),
    EMAIL_ENABLED("emailEnabled"),
    EMAIL_FROM("emailFrom");

    private String value;

    ProjectAttributeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}


