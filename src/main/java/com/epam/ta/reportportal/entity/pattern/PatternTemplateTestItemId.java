package com.epam.ta.reportportal.entity.pattern;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author <a href="mailto:ivan_budayeu@epam.com">Ivan Budayeu</a>
 */
@Embeddable
public class PatternTemplateTestItemId implements Serializable {

	@Column(name = "pattern_id")
	private Long patternId;

	@Column(name = "item_id")
	private Long itemId;

	public PatternTemplateTestItemId() {
	}

	public PatternTemplateTestItemId(Long itemId, Long patternId) {
		this.itemId = itemId;
		this.patternId = patternId;
	}

	public Long getPatternId() {
		return patternId;
	}

	public void setPatternId(Long patternId) {
		this.patternId = patternId;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		PatternTemplateTestItemId that = (PatternTemplateTestItemId) o;
		return Objects.equals(patternId, that.patternId) && Objects.equals(itemId, that.itemId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(patternId, itemId);
	}
}
