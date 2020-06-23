package com.epam.ta.reportportal.dao.widget.healthcheck.content;

import com.epam.ta.reportportal.dao.widget.WidgetContentProvider;
import com.epam.ta.reportportal.dao.widget.WidgetProviderChain;
import com.epam.ta.reportportal.dao.widget.WidgetQueryProvider;
import com.epam.ta.reportportal.entity.widget.content.healthcheck.HealthCheckTableGetParams;
import com.epam.ta.reportportal.entity.widget.content.healthcheck.HealthCheckTableStatisticsContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

/**
 * @author <a href="mailto:ivan_budayeu@epam.com">Ivan Budayeu</a>
 */
@Component
public class HealthCheckTableStatisticsChain
		implements WidgetProviderChain<HealthCheckTableGetParams, Map<String, HealthCheckTableStatisticsContent>> {

	private final WidgetQueryProvider<HealthCheckTableGetParams> statisticsQueryProvider;
	private final WidgetContentProvider<Map<String, HealthCheckTableStatisticsContent>> healthCheckTableStatisticsProvider;

	@Autowired
	public HealthCheckTableStatisticsChain(WidgetQueryProvider<HealthCheckTableGetParams> statisticsQueryProvider,
			WidgetContentProvider<Map<String, HealthCheckTableStatisticsContent>> healthCheckTableStatisticsProvider) {
		this.statisticsQueryProvider = statisticsQueryProvider;
		this.healthCheckTableStatisticsProvider = healthCheckTableStatisticsProvider;
	}

	@Override
	public Map<String, HealthCheckTableStatisticsContent> apply(HealthCheckTableGetParams params) {
		return statisticsQueryProvider.andThen(healthCheckTableStatisticsProvider).apply(params);
	}

	@Override
	public int resolvePriority(HealthCheckTableGetParams input) {
		return statisticsQueryProvider.getSupportedSorting()
				.stream()
				.filter(sorting -> Objects.nonNull(input.getSort().getOrderFor(sorting)))
				.findAny()
				.map(it -> 1)
				.orElse(0);
	}
}
