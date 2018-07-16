package com.epam.ta.reportportal.database.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/**
 * @author Pavel Bortnik
 */
@NoRepositoryBean
public interface ReportPortalRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {

	void refresh(T t);

}
