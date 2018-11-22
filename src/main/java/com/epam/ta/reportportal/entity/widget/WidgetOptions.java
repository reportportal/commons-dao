package com.epam.ta.reportportal.entity.widget;

import com.epam.ta.reportportal.commons.JsonbUserType;

import java.io.Serializable;
import java.util.Map;

/**
 * @author <a href="mailto:ivan_budayeu@epam.com">Ivan Budayeu</a>
 */
public class WidgetOptions extends JsonbUserType implements Serializable {
	@Override
	public Class<?> returnedClass() {
		return WidgetOptions.class;
	}

	private Map<String, Object> options;

	public WidgetOptions() {
	}

	public WidgetOptions(Map<String, Object> options) {
		this.options = options;
	}

	public Map<String, Object> getOptions() {
		return options;
	}

	public void setOptions(Map<String, Object> options) {
		this.options = options;
	}
}
