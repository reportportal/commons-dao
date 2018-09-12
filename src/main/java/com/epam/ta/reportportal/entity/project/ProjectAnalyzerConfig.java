package com.epam.ta.reportportal.entity.project;

import com.epam.ta.reportportal.entity.AnalyzeMode;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Pavel Bortnik
 */
public class ProjectAnalyzerConfig implements Serializable {

	public static final int MIN_DOC_FREQ = 7;
	public static final int MIN_TERM_FREQ = 1;
	public static final int MIN_SHOULD_MATCH = 80;
	public static final int NUMBER_OF_LOG_LINES = 2;

	private int minDocFreq;

	private int minTermFreq;

	private int minShouldMatch;

	private int numberOfLogLines;

	private boolean indexingRunning;

	private Boolean isAutoAnalyzerEnabled;

	private AnalyzeMode analyzerMode;

	public ProjectAnalyzerConfig() {
	}

	public ProjectAnalyzerConfig(int minDocFreq, int minTermFreq, int minShouldMatch, int numberOfLogLines, boolean indexingRunning, Boolean isAutoAnalyzerEnabled) {
		this.minDocFreq = minDocFreq;
		this.minTermFreq = minTermFreq;
		this.minShouldMatch = minShouldMatch;
		this.numberOfLogLines = numberOfLogLines;
		this.indexingRunning = indexingRunning;
		this.isAutoAnalyzerEnabled = isAutoAnalyzerEnabled;
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

    public static class ProjectAnalyzerConfigBuilder {

        private int minDocFreq;

        private int minTermFreq;

        private int minShouldMatch;

        private int numberOfLogLines;

        private boolean indexingRunning;

        private Boolean isAutoAnalyzerEnabled;

        public ProjectAnalyzerConfigBuilder minDocFreq(int minDocFreq) {
            this.minDocFreq = minDocFreq;
            return this;
        }

        public ProjectAnalyzerConfigBuilder minTermFreq(int minTermFreq) {
            this.minTermFreq = minTermFreq;
            return this;
        }

        public ProjectAnalyzerConfigBuilder minShouldMatch(int minShouldMatch) {
            this.minShouldMatch = minShouldMatch;
            return this;
        }

        public ProjectAnalyzerConfigBuilder numberOfLogLines(int numberOfLogLines) {
            this.numberOfLogLines = numberOfLogLines;
            return this;
        }

        public ProjectAnalyzerConfigBuilder indexingRunning(boolean indexingRunning) {
            this.indexingRunning = indexingRunning;
            return this;
        }

        public ProjectAnalyzerConfigBuilder isAutoAnalyzerEnabled(Boolean isAutoAnalyzerEnabled) {
            this.isAutoAnalyzerEnabled = isAutoAnalyzerEnabled;
            return this;
        }

        public ProjectAnalyzerConfig build() {
            return new ProjectAnalyzerConfig(minDocFreq, minTermFreq, minShouldMatch, numberOfLogLines, indexingRunning, isAutoAnalyzerEnabled);
        }

    }

}
