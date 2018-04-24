package com.epam.ta.reportportal.database.entity;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Pavel Bortnik
 */
@Document
public class ProjectAnalyzerConfig {

	private int minDocFreq;

	private int minTermFreq;

	private int minShouldMatch;

	private int numberOfLogLines;

	private boolean analyzeRunning;

	private boolean indexingRunning;

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

	public boolean isAnalyzeRunning() {
		return analyzeRunning;
	}

	public void setAnalyzeRunning(boolean analyzeRunning) {
		this.analyzeRunning = analyzeRunning;
	}

	public boolean isIndexingRunning() {
		return indexingRunning;
	}

	public void setIndexingRunning(boolean indexingRunning) {
		this.indexingRunning = indexingRunning;
	}
}
