package com.epam.ta.reportportal.entity.widget.content;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import java.util.List;
import java.util.Map;

import static com.epam.ta.reportportal.dao.constant.WidgetContentRepositoryConstants.FILTER_NAME;
import static com.epam.ta.reportportal.dao.constant.WidgetContentRepositoryConstants.TAG_VALUE;

/**
 * @author Ivan Budayeu
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LaunchesStatisticsContent extends AbstractLaunchStatisticsContent {

	@Column(name = "tag_values")
	@JsonProperty(value = "tags")
	private List<String> tags;

	@JsonProperty(value = "values")
	private Map<String, String> values;

	@JsonIgnore
	@Column(name = TAG_VALUE)
	private String tagValue;

	@JsonIgnore
	@Column(name = FILTER_NAME)
	private String filterName;

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public Map<String, String> getValues() {
		return values;
	}

	public void setValues(Map<String, String> values) {
		this.values = values;
	}

	public String getTagValue() {
		return tagValue;
	}

	public void setTagValue(String tagValue) {
		this.tagValue = tagValue;
	}

	public String getFilterName() {
		return filterName;
	}

	public void setFilterName(String filterName) {
		this.filterName = filterName;
	}
}
