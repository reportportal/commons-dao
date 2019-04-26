package com.epam.ta.reportportal.entity.widget.content;

/**
 * @author <a href="mailto:ihar_kahadouski@epam.com">Ihar Kahadouski</a>
 */
public class CumulativeTrendChartEntry {

	private String attributeValue;

	private CumulativeTrendChartContent content;

	public CumulativeTrendChartEntry() {
	}

	public CumulativeTrendChartEntry(String attributeValue, CumulativeTrendChartContent content) {
		this.attributeValue = attributeValue;
		this.content = content;
	}

	public String getAttributeValue() {
		return attributeValue;
	}

	public void setAttributeValue(String attributeValue) {
		this.attributeValue = attributeValue;
	}

	public CumulativeTrendChartContent getContent() {
		return content;
	}

	public void setContent(CumulativeTrendChartContent content) {
		this.content = content;
	}
}
