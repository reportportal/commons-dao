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

import com.epam.ta.reportportal.commons.querygen.Filter;
import com.epam.ta.reportportal.commons.querygen.QueryBuilder;
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

import static com.epam.ta.reportportal.dao.util.ResultFetchers.INTEGRATION_FETCHER;
import static com.epam.ta.reportportal.jooq.tables.JActiveDirectoryConfig.ACTIVE_DIRECTORY_CONFIG;
import static com.epam.ta.reportportal.jooq.tables.JIntegration.INTEGRATION;
import static com.epam.ta.reportportal.jooq.tables.JLdapConfig.LDAP_CONFIG;
import static com.epam.ta.reportportal.jooq.tables.JLdapSynchronizationAttributes.LDAP_SYNCHRONIZATION_ATTRIBUTES;

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
	public List<Integration> findByFilter(Filter filter) {
		return INTEGRATION_FETCHER.apply(dsl.fetch(QueryBuilder.newBuilder(filter).withWrapper(filter.getTarget()).build()));
	}

	@Override
	public Page<Integration> findByFilter(Filter filter, Pageable pageable) {
		return PageableExecutionUtils.getPage(INTEGRATION_FETCHER.apply(dsl.fetch(QueryBuilder.newBuilder(filter)
				.with(pageable)
				.withWrapper(filter.getTarget())
				.with(pageable.getSort())
				.build())), pageable, () -> dsl.fetchCount(QueryBuilder.newBuilder(filter).build()));
	}

	@Override
	public Optional<LdapConfig> findLdap() {

		return Optional.ofNullable(buildLdapSelectQuery().fetchOneInto(LdapConfig.class));
	}

	@Override
	public Optional<ActiveDirectoryConfig> findActiveDirectory() {

		return Optional.ofNullable(buildActiveDirectorySelectQuery().fetchOneInto(ActiveDirectoryConfig.class));
	}

	@Override
	public Optional<LdapConfig> findLdap(boolean enabled) {

		return Optional.ofNullable(buildLdapSelectQuery().where(INTEGRATION.ENABLED.eq(enabled)).fetchOneInto(LdapConfig.class));
	}

	@Override
	public Optional<ActiveDirectoryConfig> findActiveDirectory(boolean enabled) {

		return Optional.ofNullable(buildActiveDirectorySelectQuery().where(INTEGRATION.ENABLED.eq(enabled))
				.fetchOneInto(ActiveDirectoryConfig.class));
	}

	private SelectOnConditionStep<Record> buildLdapSelectQuery() {

		return dsl.select()
				.from(LDAP_CONFIG)
				.join(INTEGRATION)
				.on(LDAP_CONFIG.ID.eq(INTEGRATION.ID.cast(Long.class)))
				.leftJoin(LDAP_SYNCHRONIZATION_ATTRIBUTES)
				.on(LDAP_CONFIG.SYNC_ATTRIBUTES_ID.eq(LDAP_SYNCHRONIZATION_ATTRIBUTES.ID));
	}

	private SelectOnConditionStep<Record> buildActiveDirectorySelectQuery() {

		return dsl.select()
				.from(ACTIVE_DIRECTORY_CONFIG)
				.join(INTEGRATION)
				.on(LDAP_CONFIG.ID.eq(INTEGRATION.ID.cast(Long.class)))
				.leftJoin(LDAP_SYNCHRONIZATION_ATTRIBUTES)
				.on(LDAP_CONFIG.SYNC_ATTRIBUTES_ID.eq(LDAP_SYNCHRONIZATION_ATTRIBUTES.ID));
	}

}
