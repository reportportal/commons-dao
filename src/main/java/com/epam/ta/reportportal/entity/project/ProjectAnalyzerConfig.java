package com.epam.ta.reportportal.entity.project;

import java.io.Serializable;

/**
 * @author Pavel Bortnik
 */
public class ProjectAnalyzerConfig implements Serializable {

	public static final int MIN_DOC_FREQ = 7;
	public static final int MIN_TERM_FREQ = 1;
	public static final int MIN_SHOULD_MATCH = 80;
	public static final int NUMBER_OF_LOG_LINES = 2;

}
