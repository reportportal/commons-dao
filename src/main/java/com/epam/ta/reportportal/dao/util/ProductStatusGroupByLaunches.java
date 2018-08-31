package com.epam.ta.reportportal.dao.util;

import com.epam.ta.reportportal.commons.querygen.Filter;
import com.epam.ta.reportportal.exception.ReportPortalException;
import com.epam.ta.reportportal.ws.model.ErrorType;

import java.util.List;
import java.util.Set;
import java.util.function.Function;

public class ProductStatusGroupByLaunches implements ProductStatusBuildStrategy {
	@Override
	public List<?> buildProductStatusQuery(Set<Filter> filter, List<String> contentFields, int limit) {
		return null;
	}

	private Function<Set<Filter>, Filter> GROUP_FILTERS = filters -> {
		Filter filter = filters.stream()
				.findFirst()
				.orElseThrow(() -> new ReportPortalException(ErrorType.BAD_REQUEST_ERROR, "No filters for widget"));
		filters.stream().skip(1).flatMap(f -> f.getFilterConditions().stream()).forEachOrdered(filter::withCondition);

		return filter;
	};
}
