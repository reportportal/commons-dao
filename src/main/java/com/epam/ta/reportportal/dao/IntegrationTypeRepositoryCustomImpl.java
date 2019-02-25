/*
 * Copyright 2018 EPAM Systems
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

import com.epam.ta.reportportal.dao.util.RecordMappers;
import com.epam.ta.reportportal.entity.enums.IntegrationGroupEnum;
import com.epam.ta.reportportal.entity.integration.IntegrationType;
import com.epam.ta.reportportal.jooq.enums.JIntegrationGroupEnum;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.epam.ta.reportportal.jooq.tables.JIntegrationType.INTEGRATION_TYPE;
import static java.util.Optional.ofNullable;

/**
 * @author <a href="mailto:ivan_budayeu@epam.com">Ivan Budayeu</a>
 */
@Repository
public class IntegrationTypeRepositoryCustomImpl implements IntegrationTypeRepositoryCustom {

	@Autowired
	private DSLContext dsl;

	@Override
	public Optional<IntegrationType> findByName(String name) {
		return ofNullable(dsl.select()
				.from(INTEGRATION_TYPE)
				.where(INTEGRATION_TYPE.NAME.likeIgnoreCase(name))
				.fetchAny(RecordMappers.INTEGRATION_TYPE_MAPPER));
	}

	@Override
	public Optional<IntegrationType> findByNameAndIntegrationGroup(String name, IntegrationGroupEnum groupType) {
		return ofNullable(dsl.select()
				.from(INTEGRATION_TYPE)
				.where(INTEGRATION_TYPE.NAME.likeIgnoreCase(name))
				.and(INTEGRATION_TYPE.GROUP_TYPE.eq(JIntegrationGroupEnum.valueOf(groupType.name())))
				.fetchAny(RecordMappers.INTEGRATION_TYPE_MAPPER));
	}
}
