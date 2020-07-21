package com.epam.ta.reportportal.entity.widget.content.healthcheck;

import com.google.common.collect.Lists;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * @author <a href="mailto:ivan_budayeu@epam.com">Ivan Budayeu</a>
 */
public class HealthCheckTableGetParams {

	private final String viewName;
	private final String currentLevelKey;
	private final Sort sort;

	private final boolean includeCustomColumn;
	private final List<LevelEntry> previousLevels;

	private HealthCheckTableGetParams(String viewName, String currentLevelKey, Sort sort, boolean includeCustomColumn) {
		this.viewName = viewName;
		this.currentLevelKey = currentLevelKey;
		this.sort = sort;
		this.includeCustomColumn = includeCustomColumn;
		this.previousLevels = Lists.newArrayList();
	}

	private HealthCheckTableGetParams(String viewName, String currentLevelKey, Sort sort, boolean includeCustomColumn,
			List<LevelEntry> previousLevels) {
		this.viewName = viewName;
		this.currentLevelKey = currentLevelKey;
		this.sort = sort;
		this.includeCustomColumn = includeCustomColumn;
		this.previousLevels = previousLevels;
	}

	public static HealthCheckTableGetParams of(String viewName, String currentLevelKey, Sort sort, boolean includeCustomColumn) {
		return new HealthCheckTableGetParams(viewName, currentLevelKey, sort, includeCustomColumn);
	}

	public static HealthCheckTableGetParams of(String viewName, String currentLevelKey, Sort sort, boolean includeCustomColumn,
			List<LevelEntry> previousLevels) {
		return new HealthCheckTableGetParams(viewName, currentLevelKey, sort, includeCustomColumn, previousLevels);
	}

	public String getViewName() {
		return viewName;
	}

	public String getCurrentLevelKey() {
		return currentLevelKey;
	}

	public Sort getSort() {
		return sort;
	}

	public boolean isIncludeCustomColumn() {
		return includeCustomColumn;
	}

	public List<LevelEntry> getPreviousLevels() {
		return previousLevels;
	}
}
