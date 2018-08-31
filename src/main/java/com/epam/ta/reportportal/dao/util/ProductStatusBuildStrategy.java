package com.epam.ta.reportportal.dao.util;

import com.epam.ta.reportportal.commons.querygen.Filter;

import java.util.List;
import java.util.Set;

public interface ProductStatusBuildStrategy {

	List<?> buildProductStatusQuery(Set<Filter> filter, List<String> contentFields, int limit);
}
