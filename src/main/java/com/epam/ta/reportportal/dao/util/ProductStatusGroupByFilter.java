package com.epam.ta.reportportal.dao.util;

import com.epam.ta.reportportal.commons.querygen.Filter;

import java.util.List;
import java.util.Set;

public class ProductStatusGroupByFilter implements ProductStatusBuildStrategy {
	@Override
	public List<?> buildProductStatusQuery(Set<Filter> filter, List<String> contentFields, int limit) {
		return null;
	}
}
