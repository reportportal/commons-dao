package com.epam.ta.reportportal.database.entity.item;

import java.util.Objects;

/**
 * @author Pavel Bortnik
 */
public class TestItemCommon {

	private TestItem testItem;

	private TestItemResults testItemResults;

	private TestItemStructure testItemStructure;

	public TestItem getTestItem() {
		return testItem;
	}

	public void setTestItem(TestItem testItem) {
		this.testItem = testItem;
	}

	public TestItemResults getTestItemResults() {
		return testItemResults;
	}

	public void setTestItemResults(TestItemResults testItemResults) {
		this.testItemResults = testItemResults;
	}

	public TestItemStructure getTestItemStructure() {
		return testItemStructure;
	}

	public void setTestItemStructure(TestItemStructure testItemStructure) {
		this.testItemStructure = testItemStructure;
	}

	@Override
	public String toString() {
		return "TestItemCommon{" + "testItem=" + testItem + ", testItemResults=" + testItemResults + ", testItemStructure="
				+ testItemStructure + '}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		TestItemCommon that = (TestItemCommon) o;
		return Objects.equals(testItem, that.testItem) && Objects.equals(testItemResults, that.testItemResults) && Objects.equals(
				testItemStructure, that.testItemStructure);
	}

	@Override
	public int hashCode() {
		return Objects.hash(testItem, testItemResults, testItemStructure);
	}
}
