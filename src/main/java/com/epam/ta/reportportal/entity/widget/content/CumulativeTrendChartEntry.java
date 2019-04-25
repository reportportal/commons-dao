package com.epam.ta.reportportal.entity.widget.content;

import com.google.common.collect.Sets;

import java.util.Collection;
import java.util.Set;

/**
 * @author <a href="mailto:ihar_kahadouski@epam.com">Ihar Kahadouski</a>
 */
public class CumulativeTrendChartEntry {

	private String attributeValue;

	private Collection<CumulativeTrendChartContent> content;

	private Set<String> tooltipContent = Sets.newHashSet();

	public CumulativeTrendChartEntry() {
	}

	public CumulativeTrendChartEntry(String attributeValue, Collection<CumulativeTrendChartContent> content) {
		this.attributeValue = attributeValue;
		this.content = content;
	}

	public String getAttributeValue() {
		return attributeValue;
	}

	public void setAttributeValue(String attributeValue) {
		this.attributeValue = attributeValue;
	}

	public Collection<CumulativeTrendChartContent> getContent() {
		return content;
	}

	public void setContent(Collection<CumulativeTrendChartContent> content) {
		this.content = content;
	}

	public Set<String> getTooltipContent() {
		return tooltipContent;
	}

	public void setTooltipContent(Set<String> tooltipContent) {
		this.tooltipContent = tooltipContent;
	}
}
