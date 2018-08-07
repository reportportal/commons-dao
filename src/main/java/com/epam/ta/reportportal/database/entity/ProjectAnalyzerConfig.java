package com.epam.ta.reportportal.database.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * @author Pavel Bortnik
 */
@Document
public class ProjectAnalyzerConfig implements Serializable {

	public static final int MIN_DOC_FREQ = 1;
	public static final int MIN_TERM_FREQ = 1;
	public static final int MIN_SHOULD_MATCH = 95;
	public static final int NUMBER_OF_LOG_LINES = -1;

	private int minDocFreq;

	private int minTermFreq;

	private int minShouldMatch;

	private int numberOfLogLines;

	private boolean indexingRunning;

	private Boolean isAutoAnalyzerEnabled;

	private AnalyzeMode analyzerMode;

	public ProjectAnalyzerConfig() {
	}

	public ProjectAnalyzerConfig(int minDocFreq, int minTermFreq, int minShouldMatch, int numberOfLogLines) {
		this.minDocFreq = minDocFreq;
		this.minTermFreq = minTermFreq;
		this.minShouldMatch = minShouldMatch;
		this.numberOfLogLines = numberOfLogLines;
	}

	public int getMinDocFreq() {
		return minDocFreq;
	}

	public void setMinDocFreq(int minDocFreq) {
		this.minDocFreq = minDocFreq;
	}

	public int getMinTermFreq() {
		return minTermFreq;
	}

	public void setMinTermFreq(int minTermFreq) {
		this.minTermFreq = minTermFreq;
	}

	public int getMinShouldMatch() {
		return minShouldMatch;
	}

	public void setMinShouldMatch(int minShouldMatch) {
		this.minShouldMatch = minShouldMatch;
	}

	public int getNumberOfLogLines() {
		return numberOfLogLines;
	}

	public void setNumberOfLogLines(int numberOfLogLines) {
		this.numberOfLogLines = numberOfLogLines;
	}

	public boolean isIndexingRunning() {
		return indexingRunning;
	}

	public void setIndexingRunning(boolean indexingRunning) {
		this.indexingRunning = indexingRunning;
	}

	public Boolean getIsAutoAnalyzerEnabled() {
		return isAutoAnalyzerEnabled;
	}

	public void setIsAutoAnalyzerEnabled(Boolean autoAnalyzerEnabled) {
		isAutoAnalyzerEnabled = autoAnalyzerEnabled;
	}

	public AnalyzeMode getAnalyzerMode() {
		return analyzerMode;
	}

	public void setAnalyzerMode(AnalyzeMode analyzerMode) {
		this.analyzerMode = analyzerMode;
	}
}
