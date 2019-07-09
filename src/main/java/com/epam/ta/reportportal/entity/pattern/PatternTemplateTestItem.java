package com.epam.ta.reportportal.entity.pattern;

import com.epam.ta.reportportal.entity.item.TestItem;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author <a href="mailto:ivan_budayeu@epam.com">Ivan Budayeu</a>
 */
@Entity
@Table(name = "pattern_template_test_item")
@IdClass(value = PatternTemplateTestItemKey.class)
public class PatternTemplateTestItem implements Serializable {

	@Id
	@ManyToOne
	@JoinColumn(name = "pattern_id")
	private PatternTemplate patternTemplate;

	@Id
	@ManyToOne
	@JoinColumn(name = "item_id")
	private TestItem testItem;

	public PatternTemplateTestItem() {
	}

	public PatternTemplateTestItem(PatternTemplate patternTemplate, TestItem testItem) {
		this.patternTemplate = patternTemplate;
		this.testItem = testItem;
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
		PatternTemplateTestItem that = (PatternTemplateTestItem) o;
		return Objects.equals(patternTemplate, that.patternTemplate) && Objects.equals(testItem, that.testItem);
	}

	@Override
	public int hashCode() {
		return Objects.hash(patternTemplate, testItem);
	}
}
