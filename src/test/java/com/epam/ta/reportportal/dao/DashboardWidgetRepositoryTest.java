package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

/**
 * @author <a href="mailto:ivan_budayeu@epam.com">Ivan Budayeu</a>
 */
@Sql("/db/fill/dashboard-widget/dashboard-widget-fill.sql")
class DashboardWidgetRepositoryTest extends BaseTest {

  @Autowired
  private DashboardWidgetRepository dashboardWidgetRepository;

  @Test
  void countAllByWidgetId() {
    Assertions.assertEquals(3, dashboardWidgetRepository.countAllByWidgetId(5L));
    Assertions.assertEquals(2, dashboardWidgetRepository.countAllByWidgetId(6L));
  }

}