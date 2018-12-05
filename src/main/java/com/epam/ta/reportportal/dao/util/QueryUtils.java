package com.epam.ta.reportportal.dao.util;

import com.epam.ta.reportportal.commons.querygen.Filter;
import com.epam.ta.reportportal.commons.querygen.QueryBuilder;
import org.jooq.impl.DSL;

import java.util.stream.Collectors;

import static com.epam.ta.reportportal.jooq.tables.JLaunch.LAUNCH;

/**
 * @author <a href="mailto:ivan_budayeu@epam.com">Ivan Budayeu</a>
 */
public final class QueryUtils {

	private QueryUtils() {
		//static only
	}

	public static QueryBuilder createQueryBuilderWithLatestLaunchesOption(Filter filter, boolean isLatest) {

		QueryBuilder queryBuilder = QueryBuilder.newBuilder(filter.getTarget());

		if (isLatest) {
			queryBuilder.with(LAUNCH.NUMBER.desc())
					.addCondition(LAUNCH.ID.in(DSL.selectDistinct(LAUNCH.ID)
							.on(LAUNCH.NAME)
							.from(LAUNCH)
							.where(filter.getFilterConditions()
									.stream()
									.map(fc -> QueryBuilder.filterConverter(filter.getTarget()).apply(fc))
									.collect(Collectors.toSet()))
							.orderBy(LAUNCH.NAME, LAUNCH.NUMBER.desc())));
		}

		return queryBuilder;

	}

}
