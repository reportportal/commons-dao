package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.commons.querygen.Filter;
import com.epam.ta.reportportal.commons.querygen.QueryBuilder;
import com.epam.ta.reportportal.entity.integration.Integration;
import com.epam.ta.reportportal.entity.integration.IntegrationType;
import com.epam.ta.reportportal.entity.ldap.ActiveDirectoryConfig;
import com.epam.ta.reportportal.entity.ldap.LdapConfig;
import com.epam.ta.reportportal.entity.project.Project;
import com.epam.ta.reportportal.mapper.JsonbMapper;
import com.epam.ta.reportportal.jooq.tables.JIntegration;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.RecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @author Yauheni_Martynau
 */
@Repository
public class IntegrationRepositoryCustomImpl implements IntegrationRepositoryCustom {

	private static final RecordMapper<? super Record, Integration> INTEGRATION_MAPPER = r -> new Integration(r.get(
			JIntegration.INTEGRATION.ID,
			Long.class
	),
			r.into(Project.class),
			r.into(IntegrationType.class),
			JsonbMapper.getJsonb(r.getValue(JIntegration.INTEGRATION.PARAMS)),
			r.get(JIntegration.INTEGRATION.CREATION_DATE, LocalDateTime.class)
	);

	@Autowired
	private DSLContext dsl;

	@Override
	public List<Integration> findByFilter(Filter filter) {

		return dsl.fetch(QueryBuilder.newBuilder(filter).build()).map(INTEGRATION_MAPPER);
	}

	private final EntityManager entityManager;

	@Autowired
	public IntegrationRepositoryCustomImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Transactional
	@Override
	public void deleteSettings(Integration integration) {
		entityManager.remove(integration);
	}

	@Transactional
	@Override
	public void updateLdap(LdapConfig ldapConfig) {
		entityManager.merge(ldapConfig);

	}

	@Transactional
	@Override
	public void updateActiveDirectory(ActiveDirectoryConfig adConfig) {
		entityManager.merge(adConfig);
	}

	@Override
	public Optional<LdapConfig> findLdap(boolean enabled) {
		TypedQuery<LdapConfig> ldapConfigTypedQuery = entityManager.createQuery(
				"SELECT l FROM LdapConfig l JOIN Integration i ON l.id = i.id WHERE i.id = :id AND i.enabled = :enabled",
				LdapConfig.class
		);

		ldapConfigTypedQuery.setParameter("id", 1L);    //should be custom id, that was received from user
		ldapConfigTypedQuery.setParameter("enabled", enabled);

		return ldapConfigTypedQuery.getResultList().stream().findFirst();
	}

	@Override
	public Optional<ActiveDirectoryConfig> findActiveDirectory(boolean enabled) {
		TypedQuery<ActiveDirectoryConfig> activeDirectoryConfigTypedQuery = entityManager.createQuery(
				"SELECT a FROM ActiveDirectoryConfig a JOIN Integration i ON a.id = i.id WHERE i.id = :id AND i.enabled = :enabled",
				ActiveDirectoryConfig.class
		);

		activeDirectoryConfigTypedQuery.setParameter("id", 1L);   //should be custom id, that was received from user
		activeDirectoryConfigTypedQuery.setParameter("enabled", enabled);

		return activeDirectoryConfigTypedQuery.getResultList().stream().findFirst();
	}

	//    private Update updateExisting(Object object) {
	//        try {
	//            Update update = new Update();
	//            PropertyUtils.describe(object).entrySet().stream().filter(e -> null != e.getValue())
	//                    .forEach(it -> update.set(it.getKey(), it.getValue()));
	//            return update;
	//        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
	//            throw new ReportPortalException("Error during auth config update", e);
	//        }
	//    }
}
