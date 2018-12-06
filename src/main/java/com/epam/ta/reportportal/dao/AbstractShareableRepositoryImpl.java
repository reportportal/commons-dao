package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.commons.querygen.Filter;
import com.epam.ta.reportportal.commons.querygen.ProjectFilter;
import com.epam.ta.reportportal.commons.querygen.QueryBuilder;
import com.epam.ta.reportportal.entity.ShareableEntity;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.support.PageableExecutionUtils;

import java.util.List;
import java.util.function.Function;

import static com.epam.ta.reportportal.dao.util.ShareableUtils.*;

/**
 * @author <a href="mailto:ivan_budayeu@epam.com">Ivan Budayeu</a>
 */
public abstract class AbstractShareableRepositoryImpl<T extends ShareableEntity> implements ShareableRepository<T> {

	private DSLContext dsl;

	@Autowired
	public void setDsl(DSLContext dsl) {
		this.dsl = dsl;
	}

	protected Page<T> getPermitted(Function<Result<? extends Record>, List<T>> fetcher, Filter filter, Pageable pageable, String userName) {

		return PageableExecutionUtils.getPage(
				fetcher.apply(dsl.fetch(QueryBuilder.newBuilder(filter)
						.addCondition(permittedCondition(userName))
						.withWrapper(filter.getTarget())
						.build())),
				pageable,
				() -> dsl.fetchCount(QueryBuilder.newBuilder(filter).addCondition(permittedCondition(userName)).build())
		);

	}

	protected Page<T> getOwn(Function<Result<? extends Record>, List<T>> fetcher, ProjectFilter filter, Pageable pageable,
			String userName) {
		return PageableExecutionUtils.getPage(
				fetcher.apply(dsl.fetch(QueryBuilder.newBuilder(filter)
						.addCondition(ownCondition(userName))
						.withWrapper(filter.getTarget())
						.build())),
				pageable,
				() -> dsl.fetchCount(QueryBuilder.newBuilder(filter).addCondition(ownCondition(userName)).build())
		);
	}

	protected Page<T> getShared(Function<Result<? extends Record>, List<T>> fetcher, ProjectFilter filter, Pageable pageable,
			String userName) {
		return PageableExecutionUtils.getPage(
				fetcher.apply(dsl.fetch(QueryBuilder.newBuilder(filter)
						.addCondition(sharedCondition(userName))
						.withWrapper(filter.getTarget())
						.build())),
				pageable,
				() -> dsl.fetchCount(QueryBuilder.newBuilder(filter).addCondition(sharedCondition(userName)).build())
		);
	}
}
