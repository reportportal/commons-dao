package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.commons.querygen.Filter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author Yauheni_Martynau
 */
public interface FilterableRepository <T> {

	/**
	 * Executes query built for given filter
	 *
	 * @param filter Filter to build a query
	 * @return List of mapped entries found
	 */
	List<T> findByFilter(Filter filter);

	/**
	 * Executes query built for given filter and maps result for given page
	 *
	 * @param filter Filter to build a query
	 * @param pageable {@link Pageable}
	 * @return List of mapped entries found
	 */
	Page<T> findByFilter(Filter filter, Pageable pageable);
}
