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

package com.epam.ta.reportportal.commons.querygen.query;

import com.google.common.collect.Lists;
import org.jooq.*;

import java.util.List;
import java.util.function.Supplier;

/**
 * @author <a href="mailto:ivan_budayeu@epam.com">Ivan Budayeu</a>
 */
public class LazyJoinSelect implements Supplier<SelectQuery<? extends Record>> {

	private final SelectQuery<? extends Record> selectQuery;
	private final List<JoinEntity> joinEntities;

	public LazyJoinSelect(SelectQuery<? extends Record> selectQuery, List<JoinEntity> joinEntities) {
		this.selectQuery = selectQuery;
		this.joinEntities = joinEntities;
	}

	public LazyJoinSelect(SelectQuery<? extends Record> selectQuery) {
		this.selectQuery = selectQuery;
		this.joinEntities = Lists.newArrayList();
	}

	public void addJoin(TableLike<?> table, JoinType joinType, Condition condition) {
		addJoinToEnd(JoinEntity.of(table, joinType, condition));
	}

	public void addJoinToStart(JoinEntity joinEntity) {
		joinEntities.add(0, joinEntity);
	}

	public void addJoinToEnd(JoinEntity joinEntity) {
		joinEntities.add(joinEntity);
	}

	public boolean addJoin(int index, JoinEntity joinEntity) {
		if (index >= 0 && index <= joinEntities.size()) {
			joinEntities.add(index, joinEntity);
			return true;
		} else {
			return false;
		}

	}

	@Override
	public SelectQuery<? extends Record> get() {
		joinEntities.forEach(join -> selectQuery.addJoin(join.getTable(), join.getJoinType(), join.getJoinCondition()));
		return selectQuery;
	}

	public void addSelect(Field<?> as) {
		selectQuery.addSelect(as);

	}

	public void addOrderBy(SortField<?> sortField) {
		selectQuery.addOrderBy(sortField);
	}

	public void addLimit(int limit) {
		selectQuery.addLimit(limit);
	}

	public void addOffset(int offset) {
		selectQuery.addOffset(offset);
	}

	public void addCondition(Condition condition) {
		selectQuery.addConditions(condition);
	}

	public void addHaving(Condition condition) {
		selectQuery.addHaving(condition);
	}
}
