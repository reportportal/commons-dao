package com.epam.ta.reportportal.entity.widget.content;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import java.util.List;
import java.util.Map;

import static com.epam.ta.reportportal.dao.constant.WidgetContentRepositoryConstants.*;

/**
 * @author Ivan Budayeu
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LaunchesStatisticsContent extends AbstractLaunchStatisticsContent {

	@Column(name = TAG_VALUES)
	@JsonProperty(value = "tags")
	private List<String> tags;

	@JsonProperty(value = "values")
	private Map<String, String> values;

	@Column(name = SUM)
	@JsonProperty(value = SUM)
	private Map<String, Integer> totalStatistics;

	@Column(name = DURATION)
	@JsonProperty(value = DURATION)
	private Long duration;

	@Column(name = PASSING_RATE)
	@JsonProperty(value = PASSING_RATE)
	private Double passingRate;

	@JsonProperty(value = AVERAGE_PASSING_RATE)
	private Double averagePassingRate;

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

	public Map<String, Integer> getTotalStatistics() {
		return totalStatistics;
	}

	public void setTotalStatistics(Map<String, Integer> totalStatistics) {
		this.totalStatistics = totalStatistics;
	}

	public Long getDuration() {
		return duration;
	}

	public void setDuration(Long duration) {
		this.duration = duration;
	}

	public Double getPassingRate() {
		return passingRate;
	}

	public void setPassingRate(Double passingRate) {
		this.passingRate = passingRate;
	}

	public Double getAveragePassingRate() {
		return averagePassingRate;
	}

	public void setAveragePassingRate(Double averagePassingRate) {
		this.averagePassingRate = averagePassingRate;
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
