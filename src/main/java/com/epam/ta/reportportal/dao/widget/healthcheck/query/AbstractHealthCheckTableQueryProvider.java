package com.epam.ta.reportportal.dao.widget.healthcheck.query;

import static com.epam.ta.reportportal.dao.constant.WidgetContentRepositoryConstants.ITEM_ID;
import static com.epam.ta.reportportal.dao.constant.WidgetContentRepositoryConstants.KEY;
import static com.epam.ta.reportportal.dao.constant.WidgetContentRepositoryConstants.VALUE;
import static com.epam.ta.reportportal.dao.util.JooqFieldNameTransformer.fieldName;

import com.epam.ta.reportportal.dao.widget.WidgetQueryProvider;
import com.epam.ta.reportportal.entity.widget.content.healthcheck.HealthCheckTableGetParams;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.jooq.Condition;
import org.jooq.Record;
import org.jooq.Select;
import org.jooq.impl.DSL;

/**
 * @author <a href="mailto:ivan_budayeu@epam.com">Ivan Budayeu</a>
 */
public abstract class AbstractHealthCheckTableQueryProvider implements
    WidgetQueryProvider<HealthCheckTableGetParams> {

  private final Set<String> supportedSorting;

  protected AbstractHealthCheckTableQueryProvider(Set<String> supportedSorting) {
    this.supportedSorting = supportedSorting;
  }

  protected abstract Select<? extends Record> contentQuery(HealthCheckTableGetParams params,
      List<Condition> levelConditions);

  @Override
  public Select<? extends Record> apply(HealthCheckTableGetParams params) {

    List<Condition> levelConditions = params.getPreviousLevels()
        .stream()
        .map(levelEntry -> fieldName(params.getViewName(), ITEM_ID).cast(Long.class)
            .in(DSL.selectDistinct(fieldName(ITEM_ID).cast(Long.class))
                .from(params.getViewName())
                .where(fieldName(KEY).cast(String.class)
                    .eq(levelEntry.getKey())
                    .and(fieldName(VALUE).cast(String.class).eq(levelEntry.getValue())))))
        .collect(Collectors.toList());

    return contentQuery(params, levelConditions);
  }

  @Override
  public Set<String> getSupportedSorting() {
    return supportedSorting;
  }
}
