package com.epam.ta.reportportal.entity.pattern;

import com.epam.ta.reportportal.entity.item.TestItem;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author <a href="mailto:ivan_budayeu@epam.com">Ivan Budayeu</a>
 */
public class PatternTemplateTestItemKey implements Serializable {

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "pattern_id")
	private PatternTemplate patternTemplate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "item_id")
	private TestItem testItem;

	public PatternTemplateTestItemKey() {
	}

	public PatternTemplate getPatternTemplate() {
		return patternTemplate;
	}

	public void setPatternTemplate(PatternTemplate patternTemplate) {
		this.patternTemplate = patternTemplate;
	}

	public TestItem getTestItem() {
		return testItem;
	}

	public void setTestItem(TestItem testItem) {
		this.testItem = testItem;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		PatternTemplateTestItemKey that = (PatternTemplateTestItemKey) o;
		return Objects.equals(patternTemplate, that.patternTemplate) && Objects.equals(testItem, that.testItem);
	}

	@Override
	public int hashCode() {
		return Objects.hash(patternTemplate, testItem);
	}
}
