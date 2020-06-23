package com.epam.ta.reportportal.entity.widget.content.healthcheck;

import org.jooq.Condition;

import javax.annotation.Nullable;
import java.util.List;

/**
 * @author <a href="mailto:ivan_budayeu@epam.com">Ivan Budayeu</a>
 */
public class HealthCheckTableInitParams {

	private final String viewName;
	private final List<Condition> itemConditions;
	private final List<String> attributeKeys;

	@Nullable
	private String customKey;

	private HealthCheckTableInitParams(String viewName, List<Condition> itemConditions, List<String> attributeKeys) {
		this.viewName = viewName;
		this.itemConditions = itemConditions;
		this.attributeKeys = attributeKeys;
	}

	private HealthCheckTableInitParams(String viewName, List<Condition> itemConditions, List<String> attributeKeys,
			@Nullable String customKey) {
		this.viewName = viewName;
		this.itemConditions = itemConditions;
		this.attributeKeys = attributeKeys;
		this.customKey = customKey;
	}

	public static HealthCheckTableInitParams of(String viewName, List<Condition> itemConditions, List<String> attributeKeys) {
		return new HealthCheckTableInitParams(viewName, itemConditions, attributeKeys);
	}

	public static HealthCheckTableInitParams of(String viewName, List<Condition> itemConditions, List<String> attributeKeys,
			@Nullable String customKey) {
		return new HealthCheckTableInitParams(viewName, itemConditions, attributeKeys, customKey);
	}

	public String getViewName() {
		return viewName;
	}

	public List<Condition> getItemConditions() {
		return itemConditions;
	}

	public List<String> getAttributeKeys() {
		return attributeKeys;
	}

	@Nullable
	public String getCustomKey() {
		return customKey;
	}

	public void setCustomKey(@Nullable String customKey) {
		this.customKey = customKey;
	}
}
