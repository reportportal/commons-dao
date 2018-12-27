/*
 * Copyright (C) 2018 EPAM Systems
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.commons.querygen.QueryBuilder;
import com.epam.ta.reportportal.commons.querygen.Queryable;
import com.epam.ta.reportportal.entity.integration.Integration;
import com.epam.ta.reportportal.entity.ldap.ActiveDirectoryConfig;
import com.epam.ta.reportportal.entity.ldap.LdapConfig;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.SelectOnConditionStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.epam.ta.reportportal.dao.util.RecordMappers.*;
import static com.epam.ta.reportportal.dao.util.ResultFetchers.INTEGRATION_FETCHER;
import static com.epam.ta.reportportal.jooq.tables.JActiveDirectoryConfig.ACTIVE_DIRECTORY_CONFIG;
import static com.epam.ta.reportportal.jooq.tables.JIntegration.INTEGRATION;
import static com.epam.ta.reportportal.jooq.tables.JIntegrationType.INTEGRATION_TYPE;
import static com.epam.ta.reportportal.jooq.tables.JLdapConfig.LDAP_CONFIG;
import static com.epam.ta.reportportal.jooq.tables.JLdapSynchronizationAttributes.LDAP_SYNCHRONIZATION_ATTRIBUTES;
import static java.util.Optional.ofNullable;

/**
 * @author Yauheni_Martynau
 */
@Repository
public class IntegrationRepositoryCustomImpl implements IntegrationRepositoryCustom {

	private final DSLContext dsl;

	@Autowired
	public IntegrationRepositoryCustomImpl(DSLContext dsl) {
		this.dsl = dsl;
	}

	@Override
	public List<Integration> findByFilter(Queryable filter) {
		return INTEGRATION_FETCHER.apply(dsl.fetch(QueryBuilder.newBuilder(filter).wrap().build()));
	}

	@Override
	public Page<Integration> findByFilter(Queryable filter, Pageable pageable) {
		return PageableExecutionUtils.getPage(INTEGRATION_FETCHER.apply(dsl.fetch(QueryBuilder.newBuilder(filter)
				.with(pageable)
				.wrap()
				.withWrapperSort(pageable.getSort())
				.build())), pageable, () -> dsl.fetchCount(QueryBuilder.newBuilder(filter).build()));
	}

	@Override
	public Optional<LdapConfig> findLdap() {

		return ofNullable(buildLdapSelectQuery().fetchAny(LDAP_CONFIG_MAPPER));
	}

	@Override
	public Optional<ActiveDirectoryConfig> findActiveDirectory() {

		return ofNullable(buildActiveDirectorySelectQuery().fetchAny(ACTIVE_DIRECTORY_CONFIG_MAPPER));
	}

	@Override
	public Optional<LdapConfig> findLdap(boolean enabled) {

		return ofNullable(buildLdapSelectQuery().where(INTEGRATION.ENABLED.eq(enabled)).fetchAny(LDAP_CONFIG_MAPPER));
	}

	@Override
	public Optional<ActiveDirectoryConfig> findActiveDirectory(boolean enabled) {

		return ofNullable(buildActiveDirectorySelectQuery().where(INTEGRATION.ENABLED.eq(enabled))
				.fetchAny(ACTIVE_DIRECTORY_CONFIG_MAPPER));
	}

	@Override
	public Optional<Integration> getGlobalIntegrationById(Long integrationId) {
		return ofNullable(dsl.select()
				.from(INTEGRATION)
				.join(INTEGRATION_TYPE)
				.on(INTEGRATION.TYPE.eq(INTEGRATION_TYPE.ID))
				.where(INTEGRATION.ID.eq(integrationId.intValue()).and(INTEGRATION.PROJECT_ID.isNull()))
				.fetchAny(GLOBAL_INTEGRATION_RECORD_MAPPER));
	}

	private SelectOnConditionStep<Record> buildLdapSelectQuery() {

		return dsl.select()
				.from(LDAP_CONFIG)
				.join(INTEGRATION)
				.on(LDAP_CONFIG.ID.eq(INTEGRATION.ID.cast(Long.class)))
				.join(INTEGRATION_TYPE)
				.on(INTEGRATION.TYPE.eq(INTEGRATION_TYPE.ID))
				.leftJoin(LDAP_SYNCHRONIZATION_ATTRIBUTES)
				.on(LDAP_CONFIG.SYNC_ATTRIBUTES_ID.eq(LDAP_SYNCHRONIZATION_ATTRIBUTES.ID));
	}

	private SelectOnConditionStep<Record> buildActiveDirectorySelectQuery() {

		return dsl.select()
				.from(ACTIVE_DIRECTORY_CONFIG)
				.join(INTEGRATION)
				.on(ACTIVE_DIRECTORY_CONFIG.ID.eq(INTEGRATION.ID.cast(Long.class)))
				.join(INTEGRATION_TYPE)
				.on(INTEGRATION.TYPE.eq(INTEGRATION_TYPE.ID))
				.leftJoin(LDAP_SYNCHRONIZATION_ATTRIBUTES)
				.on(ACTIVE_DIRECTORY_CONFIG.SYNC_ATTRIBUTES_ID.eq(LDAP_SYNCHRONIZATION_ATTRIBUTES.ID));
	}

}
