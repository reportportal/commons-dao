package com.epam.ta.reportportal.entity.pattern;

import com.epam.ta.reportportal.entity.item.TestItem;

import javax.persistence.*;

/**
 * @author <a href="mailto:ivan_budayeu@epam.com">Ivan Budayeu</a>
 */
@Entity
@Table(name = "pattern_template_test_item")
public class PatternTemplateTestItem {

	@EmbeddedId
	private PatternTemplateTestItemId id;

	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId(value = "patternTemplateId")
	private PatternTemplate patternTemplate;

	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId(value = "testItemId")
	private TestItem testItem;

	public PatternTemplateTestItem(PatternTemplate patternTemplate, TestItem testItem) {
		this.patternTemplate = patternTemplate;
		this.testItem = testItem;
		this.id = new PatternTemplateTestItemId(patternTemplate.getId(), testItem.getItemId());
	}

	public PatternTemplateTestItemId getId() {
		return id;
	}

	public void setId(PatternTemplateTestItemId id) {
		this.id = id;
	}

	public PatternTemplate getPatternTemplate() {
		return patternTemplate;
	}

	public void setPatternTemplate(PatternTemplate patternTemplate) {
		this.patternTemplate = patternTemplate;
	}

	public PatternTemplateTestItem withPatternTemplate(PatternTemplate patternTemplate) {
		this.patternTemplate = patternTemplate;
		this.id.setTemplatePatternId(patternTemplate.getId());
		return this;
	}

	public TestItem getTestItem() {
		return testItem;
	}

	public void setTestItem(TestItem testItem) {
		this.testItem = testItem;
	}

	public PatternTemplateTestItem withTestItem(TestItem testItem) {
		this.testItem = testItem;
		this.id.setItemId(testItem.getItemId());
		return this;
	}
}
