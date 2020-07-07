package com.epam.ta.reportportal.dao.widget.healthcheck.content;

import com.epam.ta.reportportal.dao.widget.WidgetContentProvider;
import com.epam.ta.reportportal.dao.widget.WidgetProviderChain;
import com.epam.ta.reportportal.dao.widget.WidgetQueryProvider;
import com.epam.ta.reportportal.entity.widget.content.healthcheck.HealthCheckTableGetParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author <a href="mailto:ivan_budayeu@epam.com">Ivan Budayeu</a>
 */
@Component
public class HealthCheckTableColumnChain implements WidgetProviderChain<HealthCheckTableGetParams, Map<String, List<String>>> {

	private final WidgetQueryProvider<HealthCheckTableGetParams> customColumnQueryProvider;
	private final WidgetContentProvider<Map<String, List<String>>> healthCheckTableColumnProvider;

	@Autowired
	public HealthCheckTableColumnChain(WidgetQueryProvider<HealthCheckTableGetParams> customColumnQueryProvider,
			WidgetContentProvider<Map<String, List<String>>> healthCheckTableColumnProvider) {
		this.customColumnQueryProvider = customColumnQueryProvider;
		this.healthCheckTableColumnProvider = healthCheckTableColumnProvider;
	}

	@Override
	public Map<String, List<String>> apply(HealthCheckTableGetParams params) {
		if (!params.isIncludeCustomColumn()) {
			return Collections.emptyMap();
		}
		return customColumnQueryProvider.andThen(healthCheckTableColumnProvider).apply(params);
	}

	@Override
	public int resolvePriority(HealthCheckTableGetParams params) {
		return customColumnQueryProvider.getSupportedSorting()
				.stream()
				.filter(sorting -> Objects.nonNull(params.getSort().getOrderFor(sorting)))
				.findAny()
				.map(it -> 1)
				.orElse(0);
	}
}
