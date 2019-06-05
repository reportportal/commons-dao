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

import com.google.common.collect.Maps;
import org.apache.commons.lang3.tuple.Pair;
import org.jooq.Condition;
import org.jooq.Operator;
import org.jooq.impl.DSL;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:ivan_budayeu@epam.com">Ivan Budayeu</a>
 */
public class CompositeFilterCondition implements ConvertibleCondition {

	private List<Pair<Operator, List<ConvertibleCondition>>> conditions;

	private Operator operator;

	public CompositeFilterCondition(List<Pair<Operator, List<ConvertibleCondition>>> conditions, Operator operator) {
		this.conditions = conditions;
		this.operator = operator;
	}

	@Override
	public Map<ConditionType, Condition> toCondition(FilterTarget filterTarget) {

		Map<ConditionType, Condition> result = Maps.newHashMapWithExpectedSize(ConditionType.values().length);

		conditions.forEach(pair -> pair.getRight().forEach(c -> {
			Map<ConditionType, Condition> conditionMap = c.toCondition(filterTarget);
			conditionMap.forEach((key, value) -> {
				Condition condition = result.getOrDefault(key, DSL.noCondition());
				result.put(key, DSL.condition(pair.getLeft(), condition, value));
			});
		}));

		return result;
	}

	public List<Pair<Operator, List<ConvertibleCondition>>> getConditions() {
		return conditions;
	}

	public void setConditions(List<Pair<Operator, List<ConvertibleCondition>>> conditions) {
		this.conditions = conditions;
	}

	@Override
	public Operator getOperator() {
		return operator;
	}

	public void setOperator(Operator operator) {
		this.operator = operator;
	}
}
