package com.epam.ta.reportportal.dao.widget.healthcheck.query;

import com.epam.ta.reportportal.entity.widget.content.healthcheck.HealthCheckTableGetParams;
import com.google.common.collect.Sets;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static com.epam.ta.reportportal.dao.constant.WidgetContentRepositoryConstants.*;
import static com.epam.ta.reportportal.dao.util.JooqFieldNameTransformer.fieldName;

/**
 * @author <a href="mailto:ivan_budayeu@epam.com">Ivan Budayeu</a>
 */
@Component
public class CustomColumnQueryProvider extends AbstractHealthCheckTableQueryProvider {

	public static final String UNNESTED_ARRAY = "unnested_array";

	public CustomColumnQueryProvider() {
		super(Sets.newHashSet(CUSTOM_COLUMN_SORTING));
	}

	@Override
	protected Select<? extends Record> contentQuery(HealthCheckTableGetParams params, List<Condition> levelConditions) {
		SelectHavingStep<?> selectQuery = DSL.select(DSL.arrayAggDistinct(fieldName(UNNESTED_ARRAY))
				.filterWhere(fieldName(UNNESTED_ARRAY).isNotNull())
				.as(AGGREGATED_VALUES), fieldName(VALUE))
				.from(DSL.table(params.getViewName()), DSL.table(DSL.sql("unnest(?)", fieldName(CUSTOM_COLUMN))).as(UNNESTED_ARRAY))
				.where(fieldName(KEY).cast(String.class)
						.eq(params.getCurrentLevelKey())
						.and(levelConditions.stream().reduce(DSL.noCondition(), Condition::and)))
				.groupBy(fieldName(VALUE));

		Optional<Sort.Order> resolvedSort = params.getSort()
				.get()
				.filter(order -> getSupportedSorting().contains(order.getProperty()))
				.findFirst();

		if (resolvedSort.isPresent()) {
			return selectQuery.orderBy(resolvedSort.get().isAscending() ?
					fieldName(AGGREGATED_VALUES).sort(SortOrder.ASC) :
					fieldName(AGGREGATED_VALUES).sort(SortOrder.DESC));
		}
		return selectQuery;
	}
}
