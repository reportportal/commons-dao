package com.epam.ta.reportportal.dao.widget.healthcheck.query;

import static com.epam.ta.reportportal.dao.constant.WidgetContentRepositoryConstants.EXECUTIONS_FAILED;
import static com.epam.ta.reportportal.dao.constant.WidgetContentRepositoryConstants.EXECUTIONS_TOTAL;
import static com.epam.ta.reportportal.dao.constant.WidgetContentRepositoryConstants.ITEM_ID;
import static com.epam.ta.reportportal.dao.constant.WidgetContentRepositoryConstants.KEY;
import static com.epam.ta.reportportal.dao.constant.WidgetContentRepositoryConstants.SUM;
import static com.epam.ta.reportportal.dao.constant.WidgetContentRepositoryConstants.VALUE;
import static com.epam.ta.reportportal.dao.util.JooqFieldNameTransformer.fieldName;
import static com.epam.ta.reportportal.jooq.Tables.STATISTICS;
import static com.epam.ta.reportportal.jooq.Tables.STATISTICS_FIELD;

import com.epam.ta.reportportal.entity.widget.content.healthcheck.HealthCheckTableGetParams;
import com.google.common.collect.Sets;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.jooq.Condition;
import org.jooq.Record;
import org.jooq.Record3;
import org.jooq.Select;
import org.jooq.SelectHavingStep;
import org.jooq.SortOrder;
import org.jooq.impl.DSL;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

/**
 * @author <a href="mailto:ivan_budayeu@epam.com">Ivan Budayeu</a>
 */
@Component
public class StatisticsQueryProvider extends AbstractHealthCheckTableQueryProvider {

  public StatisticsQueryProvider() {
    super(Sets.newHashSet(EXECUTIONS_TOTAL, EXECUTIONS_FAILED));
  }

  @Override
  protected Select<? extends Record> contentQuery(HealthCheckTableGetParams params,
      List<Condition> levelConditions) {

    SelectHavingStep<? extends Record3<String, BigDecimal, ?>> selectQuery = DSL.select(
            STATISTICS_FIELD.NAME,
            DSL.sum(STATISTICS.S_COUNTER).as(SUM),
            fieldName(VALUE)
        )
        .from(params.getViewName())
        .join(STATISTICS)
        .on(fieldName(params.getViewName(), ITEM_ID).cast(Long.class).eq(STATISTICS.ITEM_ID))
        .join(STATISTICS_FIELD)
        .on(STATISTICS.STATISTICS_FIELD_ID.eq(STATISTICS_FIELD.SF_ID))
        .where(fieldName(KEY).cast(String.class)
            .eq(params.getCurrentLevelKey())
            .and(levelConditions.stream().reduce(DSL.noCondition(), Condition::and)))
        .groupBy(fieldName(VALUE), STATISTICS_FIELD.NAME);

    Optional<Sort.Order> resolvedSort = params.getSort()
        .get()
        .filter(order -> getSupportedSorting().contains(order.getProperty()))
        .findFirst();
    if (resolvedSort.isPresent()) {
      return selectQuery.orderBy(
          DSL.when(STATISTICS_FIELD.NAME.eq(resolvedSort.get().getProperty()),
              STATISTICS_FIELD.NAME),
          resolvedSort.get().isAscending() ?
              DSL.sum(STATISTICS.S_COUNTER).sort(SortOrder.ASC) :
              DSL.sum(STATISTICS.S_COUNTER).sort(SortOrder.DESC)
      );
    }
    return selectQuery;
  }
}
