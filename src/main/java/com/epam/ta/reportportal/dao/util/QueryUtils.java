package com.epam.ta.reportportal.dao.util;

import com.epam.ta.reportportal.commons.querygen.QueryBuilder;
import org.jooq.impl.DSL;

import static com.epam.ta.reportportal.jooq.tables.JLaunch.LAUNCH;

/**
 * @author <a href="mailto:ivan_budayeu@epam.com">Ivan Budayeu</a>
 */
public final class QueryUtils {

	private QueryUtils() {
		//static only
	}

	public static QueryBuilder updateWithLatestLaunchOption(QueryBuilder queryBuilder, boolean isLatest) {
		if (isLatest) {
			queryBuilder.addCondition(LAUNCH.ID.in(DSL.selectDistinct(LAUNCH.ID)
					.on(LAUNCH.NAME)
					.from(LAUNCH)
					.orderBy(LAUNCH.NAME, LAUNCH.NUMBER.desc())));
		}

		return queryBuilder;

	}
}
