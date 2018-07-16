package com.epam.ta.reportportal.database.dao;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.io.Serializable;

/**
 * @author Pavel Bortnik
 */
public class ReportPortalRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID>
		implements ReportPortalRepository<T, ID> {

	private EntityManager entityManager;

	public ReportPortalRepositoryImpl(JpaEntityInformation entityInformation, EntityManager entityManager) {
		super(entityInformation, entityManager);
		this.entityManager = entityManager;
	}

	@Override
	@Transactional
	public void refresh(T t) {
		entityManager.refresh(t);
	}
}
