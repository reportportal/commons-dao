package com.epam.ta.reportportal.entity.widget.content;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import static com.epam.ta.reportportal.dao.constant.WidgetContentRepositoryConstants.*;

/**
 * @author <a href="mailto:ivan_budayeu@epam.com">Ivan Budayeu</a>
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductStatusStatisticsContent extends AbstractLaunchStatisticsContent {

	@Column(name = "status")
	@JsonProperty(value = "status")
	private String status;

	@Column(name = TAG_VALUES)
	@JsonProperty(value = "tags")
	private Map<String, Set<String>> tags;

	@JsonProperty(value = "values")
	private Map<String, String> values = new LinkedHashMap<>();

	@Column(name = SUM)
	@JsonProperty(value = SUM)
	private Map<String, Integer> totalStatistics = new LinkedHashMap<>();

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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Map<String, Set<String>> getTags() {
		return tags;
	}

	public void setTags(Map<String, Set<String>> tags) {
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
