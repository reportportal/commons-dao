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

package com.epam.ta.reportportal.commons.querygen;

import org.apache.commons.lang3.tuple.Pair;
import org.jooq.Operator;
import org.jooq.impl.DSL;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:ivan_budayeu@epam.com">Ivan Budayeu</a>
 */
public class CompositeFilterCondition implements ConvertibleCondition {

	private List<Pair<Operator, List<ConvertibleCondition>>> conditions;

	private final Operator operator;

	public CompositeFilterCondition(List<Pair<Operator, List<ConvertibleCondition>>> conditions, Operator operator) {
		this.conditions = conditions;
		this.operator = operator;
	}

	@Override
	public org.jooq.Condition toCondition(CriteriaHolder criteriaHolder) {
		return DSL.condition(operator,
				conditions.stream()
						.map(pair -> DSL.condition(pair.getLeft(),
								pair.getRight().stream().map(c -> c.toCondition(criteriaHolder)).collect(Collectors.toList())
						))
						.collect(Collectors.toList())
		);
	}
}
