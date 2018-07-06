package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.commons.querygen.Filter;

import java.util.List;

/**
 * @author Yauheni_Martynau
 */
public interface FilterableRepository <T> {

	List<T> findByFilter(Filter filter);
}
