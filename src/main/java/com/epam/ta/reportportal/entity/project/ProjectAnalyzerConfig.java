package com.epam.ta.reportportal.entity.project;

import com.epam.ta.reportportal.entity.AnalyzeMode;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Pavel Bortnik
 */
@Entity
@Table(name = "project_analyzer_config")
public class ProjectAnalyzerConfig implements Serializable {

	public static final int MIN_DOC_FREQ = 7;
	public static final int MIN_TERM_FREQ = 1;
	public static final int MIN_SHOULD_MATCH = 80;
	public static final int NUMBER_OF_LOG_LINES = 2;

	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "min_doc_freq")
	private int minDocFreq;

	@Column(name = "min_term_freq")
	private int minTermFreq;

	@Column(name = "min_should_match")
	private int minShouldMatch;

	@Column(name = "number_of_log_lines")
	private int numberOfLogLines;

	@Column(name = "indexing_running")
	private boolean indexingRunning;

	@Column(name = "auto_analyzer_enabled")
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getAutoAnalyzerEnabled() {
		return isAutoAnalyzerEnabled;
	}

	public void setAutoAnalyzerEnabled(Boolean autoAnalyzerEnabled) {
		isAutoAnalyzerEnabled = autoAnalyzerEnabled;
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
