/*
 * Copyright 2019 EPAM Systems
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
package com.epam.ta.reportportal.entity.ldap.validation;

import com.epam.ta.reportportal.entity.integration.Integration;
import org.apache.commons.lang3.BooleanUtils;
import org.hibernate.validator.spi.group.DefaultGroupSequenceProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract validation sequence provider
 *
 * @param <T> Type of Auth Config
 * @author Andrei Varabyeu
 */
public class AbstractAuthSequenceProvider<T extends Integration> implements DefaultGroupSequenceProvider<T> {

	private final Class<T> modelType;

	public AbstractAuthSequenceProvider(Class<T> modelType) {
		this.modelType = modelType;
	}

	@Override
	public List<Class<?>> getValidationGroups(Integration authConfig) {
		List<Class<?>> defaultGroupSequence = new ArrayList<>();
		defaultGroupSequence.add(modelType);

		if (authConfig != null && BooleanUtils.isTrue(authConfig.isEnabled())) {
			defaultGroupSequence.add(IfEnabled.class);
		}

		return defaultGroupSequence;
	}
}

